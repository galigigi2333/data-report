package jrx.springbatch.service.impl;

import jrx.springbatch.entity.CustomerInfo;
import jrx.springbatch.mapper.CustomerInfoMapper;
import jrx.springbatch.service.CustomerInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户信息表 服务实现类
 * </p>
 *
 * @author galigigi
 * @since 2020-06-04
 */
@Service
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoMapper, CustomerInfo> implements CustomerInfoService {

}
