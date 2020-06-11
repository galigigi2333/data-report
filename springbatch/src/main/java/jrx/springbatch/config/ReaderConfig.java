package jrx.springbatch.config;

import jrx.springbatch.entity.SummaryStatistics;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.context.annotation.Configuration;

import java.util.Iterator;
import java.util.List;


@Configuration
public class ReaderConfig implements ItemReader<SummaryStatistics> {

    private final Iterator<SummaryStatistics> itearator;

    public ReaderConfig(List<SummaryStatistics> list){
            this.itearator=list.iterator();
        }
    @Override
    public SummaryStatistics read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (itearator.hasNext()){
            return itearator.next();
        }else {
            return null;
        }

}
}
