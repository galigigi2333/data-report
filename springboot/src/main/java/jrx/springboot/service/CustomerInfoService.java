package jrx.springboot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jrx.springboot.entity.CustomerInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import jrx.springboot.util.CustomerQueryVo;

/**
 * <p>
 * 客户信息表 服务类
 * </p>
 *
 * @author galigigi
 * @since 2020-06-03
 */
public interface CustomerInfoService extends IService<CustomerInfo> {

    IPage<CustomerInfo> getPageList(Page<CustomerInfo> infoPage, CustomerQueryVo customerQueryVo);
}
