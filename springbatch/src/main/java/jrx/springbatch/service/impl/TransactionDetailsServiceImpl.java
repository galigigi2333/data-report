package jrx.springbatch.service.impl;

import jrx.springbatch.entity.TransactionDetails;
import jrx.springbatch.mapper.TransactionDetailsMapper;
import jrx.springbatch.service.TransactionDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 交易明细表 服务实现类
 * </p>
 *
 * @author galigigi
 * @since 2020-06-04
 */
@Service
public class TransactionDetailsServiceImpl extends ServiceImpl<TransactionDetailsMapper, TransactionDetails> implements TransactionDetailsService {

}
