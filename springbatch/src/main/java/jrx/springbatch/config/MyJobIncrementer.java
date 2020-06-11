package jrx.springbatch.config;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyJobIncrementer implements JobParametersIncrementer {

    /**
     * 由于测试为10秒一次调度，所以这里用了固定日期
     * @param jobParameters
     * @return
     */
    @Override
    public JobParameters getNext(JobParameters jobParameters) {
        JobParameters parameters=(jobParameters==null)? new JobParameters() :jobParameters;
        String date="2020-06-10";
        return new JobParametersBuilder(parameters).addString("parameter",date).addDate("date",new Date()).toJobParameters();

    }
}
