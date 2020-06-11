package jrx.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jrx.springboot.entity.TransactionDetails;
import jrx.springboot.mapper.TransactionDetailsMapper;
import jrx.springboot.service.TransactionDetailsService;
import jrx.springboot.util.DetailsQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * <p>
 * 交易明细表 服务实现类
 * </p>
 *
 * @author galigigi
 * @since 2020-06-03
 */
@Service
public class TransactionDetailsServiceImpl extends ServiceImpl<TransactionDetailsMapper, TransactionDetails> implements TransactionDetailsService {

    @Override
    public IPage<TransactionDetails> getPageList(Page<TransactionDetails> infoPage, DetailsQueryVo queryVo) {

        if (queryVo==null){
            return baseMapper.selectPage(infoPage,null);
        }

        Integer custId = queryVo.getCustId();//客户号
        String transType = queryVo.getTransType();//交易类型
        Date datetime = queryVo.getTxnDatetime();//交易时间
        QueryWrapper<TransactionDetails> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(transType)){
            wrapper.eq("trans_type",transType);
        }
        if (datetime!=null){
            wrapper.eq("txn_datetime",datetime);
        }

        if (custId!=null){
            wrapper.eq("cust_id",custId);
        }


        wrapper.orderByDesc("txn_datetime");
       return baseMapper.selectPage(infoPage,wrapper);

    }
}
