package jrx.springbatch.config;

import jrx.springbatch.entity.SummaryStatistics;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FilterSummary implements ItemProcessor<SummaryStatistics,SummaryStatistics> {
    @Override
    public SummaryStatistics process(SummaryStatistics summaryStatistics) throws Exception {
        if (summaryStatistics.getPayCnt()==null){
            summaryStatistics.setPayCnt(0);
        }
        if (summaryStatistics.getPayAmt()==null){
            summaryStatistics.setPayAmt(new BigDecimal(0));
        }
        if (summaryStatistics.getTranCnt()==null){
            summaryStatistics.setTranCnt(0);
        }
        return summaryStatistics;
    }
}
