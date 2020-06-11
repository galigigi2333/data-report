package jrx.springbatch.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jrx.springbatch.entity.CustomerInfo;
import jrx.springbatch.entity.SummaryStatistics;
import jrx.springbatch.mapper.CustomerInfoMapper;
import jrx.springbatch.mapper.TransactionDetailsMapper;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobParametersNotFoundException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


@Configuration
public class JobConfig implements ApplicationContextAware ,StepExecutionListener{
    @Autowired
    private ReaderConfig reader;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private TransactionDetailsMapper detailsMapper;
    @Autowired
    private CustomerInfoMapper infoMapper;
    @Autowired
    private JdbcBatchItemWriter writer;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobExplorer jobExplorer;
    @Autowired
    private JobRegistry jobRegistry;
    @Autowired
    private JobParametersIncrementer myJobIncrementer;
    @Autowired
    private ItemProcessor<SummaryStatistics, SummaryStatistics> filterSummary;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    private Map<String,JobParameter> parameters;
    private ApplicationContext context;

    /**
     * 每隔10秒调度一次Job
     * @throws JobInstanceAlreadyCompleteException
     * @throws JobExecutionAlreadyRunningException
     * @throws JobParametersInvalidException
     * @throws JobRestartException
     * @throws JobParametersNotFoundException
     * @throws NoSuchJobException
     */
    @Scheduled(fixedDelay = 10000)
    public void scheduled() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, JobParametersNotFoundException, NoSuchJobException {
        jobOperator().startNextInstance("itemJob");
    }


    /**
     * 创建Job
     * @return
     */
    @Bean
    public Job itemJob(){
        return jobBuilderFactory.get("itemJob")
                .incrementer(myJobIncrementer)
                .start(chunkStep())
                .build();
    }

    @Bean
    public Step chunkStep(){
        return stepBuilderFactory.get("chunkStep")
                .listener(this)
                .<SummaryStatistics,SummaryStatistics>chunk(2)
                .reader(jdbcReader())
                .processor(itemProcessor())
                .writer(writer)
                .build();
    }

    @Bean
    public CompositeItemProcessor<SummaryStatistics,SummaryStatistics> itemProcessor(){
        CompositeItemProcessor<SummaryStatistics, SummaryStatistics> processor = new CompositeItemProcessor<>();
        List<ItemProcessor<SummaryStatistics,SummaryStatistics>> filterList=new ArrayList<>();
        filterList.add(filterSummary);
        processor.setDelegates(filterList);
        return processor;
    }

    /**
     * 因为查询三次数据库 每次查询互不干扰，所以使用异步编排优化了查询效率
     * 测试数据为20万条 常规业务逻辑 测试汇总时间约为 200ms
     *                  异步编排 测试汇总时间为 70ms
     * @return
     */
    @Bean
    @StepScope
    public ItemReader<? extends SummaryStatistics> jdbcReader()  {
        String parameter = parameters.get("parameter").getValue().toString();
        CompletableFuture<List<SummaryStatistics>> summaryCompletable = CompletableFuture.supplyAsync(() -> {
            List<SummaryStatistics> summaryList = detailsMapper.selectMaxAmt(parameter);//当天交易
            return summaryList;
        }, threadPoolExecutor);
        CompletableFuture<List<SummaryStatistics>> payCompletable = CompletableFuture.supplyAsync(() -> {
            List<SummaryStatistics> payList = detailsMapper.selectPay(parameter);//当天还款
            return payList;
        }, threadPoolExecutor);
        CompletableFuture<List<SummaryStatistics>> tranCompletable = CompletableFuture.supplyAsync(() -> {
            List<SummaryStatistics> tranList = detailsMapper.selectTran(parameter);//当天消费
            return tranList;
        }, threadPoolExecutor);
        try {
            List<SummaryStatistics> summarys = summaryCompletable.get();
            List<SummaryStatistics> pays = payCompletable.get();
            List<SummaryStatistics> trans = tranCompletable.get();
            CompletableFuture.allOf(payCompletable,summaryCompletable,tranCompletable).join();
            List<SummaryStatistics> list = getSummarys(summarys, pays, trans);
            return  new ReaderConfig(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 业务组装汇总集合
     * @param summaryList 交易信息集合
     * @param payList  还款信息集合
     * @param tranList 消费信息集合
     * @return 日汇总集合
     */
    private List<SummaryStatistics> getSummarys(List<SummaryStatistics>summaryList,List<SummaryStatistics> payList,List<SummaryStatistics> tranList){
        List<SummaryStatistics> list = summaryList.stream().map(summaryStatistics -> {
            SummaryStatistics statistics = new SummaryStatistics();
            String index = UUID.randomUUID().toString().replace("-", "").substring(0, 29);
            statistics.setSIndex(index);
            statistics.setUpdateTime(new Date());//当前时间
            statistics.setCustId(summaryStatistics.getCustId());//客户id
            QueryWrapper<CustomerInfo> wrapper = new QueryWrapper<>();
            CustomerInfo customerInfo = infoMapper.selectOne(wrapper.eq("cust_id", summaryStatistics.getCustId()));
            statistics.setSurname(customerInfo.getSurname());//客户姓名
            statistics.setTranMaxAmt(summaryStatistics.getTranMaxAmt());//当天单笔交易最大金额
            statistics.setTransDate(summaryStatistics.getTransDate());//交易日期
            statistics.setTranAmt(summaryStatistics.getTranAmt());//当天交易总金额
            if (!CollectionUtils.isEmpty(payList)){
                for (SummaryStatistics pay: payList) {
                    if (pay.getCustId()==summaryStatistics.getCustId()){
                        statistics.setPayCnt(pay.getPayCnt());//当天还款笔数
                        statistics.setPayAmt(pay.getPayAmt());//当天还款总金额
                        break;
                    }
                }
            }
            if (!CollectionUtils.isEmpty(tranList)){
                for (SummaryStatistics tran:tranList) {
                    if (tran.getCustId()==summaryStatistics.getCustId()){
                        statistics.setTranCnt(tran.getTranCnt());//当天消费笔数
                        break;
                    }
                }
            }
            return statistics;
        }).collect(Collectors.toList());
        return list;
    }
    @Bean
    public JobRegistryBeanPostProcessor postProcessor() throws Exception {
        JobRegistryBeanPostProcessor processor = new JobRegistryBeanPostProcessor();
        processor.setJobRegistry(jobRegistry);
        processor.setBeanFactory(context.getAutowireCapableBeanFactory());
        processor.afterPropertiesSet();
        return processor;
    }

    @Bean
    public JobOperator jobOperator(){
        SimpleJobOperator operator = new SimpleJobOperator();
        operator.setJobLauncher(jobLauncher);
        operator.setJobParametersConverter(new DefaultJobParametersConverter());
        operator.setJobRepository(jobRepository);
        operator.setJobExplorer(jobExplorer);
        operator.setJobRegistry(jobRegistry);
        return operator;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context=applicationContext;
    }
    @Override
    public void beforeStep(StepExecution stepExecution) {
        parameters = stepExecution.getJobParameters().getParameters();
    }
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}

