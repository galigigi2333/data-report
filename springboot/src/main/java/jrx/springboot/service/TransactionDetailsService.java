package jrx.springboot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jrx.springboot.entity.TransactionDetails;
import com.baomidou.mybatisplus.extension.service.IService;
import jrx.springboot.util.DetailsQueryVo;

/**
 * <p>
 * 交易明细表 服务类
 * </p>
 *
 * @author galigigi
 * @since 2020-06-03
 */
public interface TransactionDetailsService extends IService<TransactionDetails> {

    IPage<TransactionDetails> getPageList(Page<TransactionDetails> infoPage, DetailsQueryVo queryVo);
}
