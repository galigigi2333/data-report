package jrx.springbatch.config;

import jrx.springbatch.entity.SummaryStatistics;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SummaryWriter  {
@Autowired
private DataSource dataSource;

    @Bean
    public JdbcBatchItemWriter<SummaryStatistics> jdbcBatchItemWriter(){
        JdbcBatchItemWriter<SummaryStatistics> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("insert into jrx_summary_statistics(s_index,cust_id,update_time,trans_date,surname,tran_max_amt,pay_amt,tran_cnt,pay_cnt,tran_amt) values"+
                "(:sIndex,:custId,:updateTime,:transDate,:surname,:tranMaxAmt,:payAmt,:tranCnt,:payCnt,:tranAmt)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return itemWriter;
    }
}
