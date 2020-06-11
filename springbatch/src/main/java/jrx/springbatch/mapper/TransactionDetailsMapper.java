package jrx.springbatch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jrx.springbatch.entity.SummaryStatistics;
import jrx.springbatch.entity.TransactionDetails;

import java.util.List;

/**
 * <p>
 * 交易明细表 Mapper 接口
 * </p>
 *
 * @author galigigi
 * @since 2020-06-04
 */
public interface TransactionDetailsMapper extends BaseMapper<TransactionDetails> {
    List<SummaryStatistics> selectMaxAmt(String  date);
    List<SummaryStatistics> selectPay(String date);
    List<SummaryStatistics> selectTran(String date);
}
