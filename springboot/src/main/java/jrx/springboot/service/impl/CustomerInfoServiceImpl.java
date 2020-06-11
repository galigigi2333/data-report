package jrx.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jrx.springboot.entity.CustomerInfo;
import jrx.springboot.mapper.CustomerInfoMapper;
import jrx.springboot.service.CustomerInfoService;
import jrx.springboot.util.CustomerQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 客户信息表 服务实现类
 * </p>
 *
 * @author galigigi
 * @since 2020-06-03
 */
@Service
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoMapper, CustomerInfo> implements CustomerInfoService {
    @Autowired
    private CustomerInfoMapper infoMapper;

    @Override
    public IPage<CustomerInfo> getPageList(Page<CustomerInfo> infoPage, CustomerQueryVo customerQueryVo) {
        //如果查询条件为空
        if (customerQueryVo==null){
            IPage<CustomerInfo> iPage = infoMapper.selectPage(infoPage, null);
            return iPage;
        }
        QueryWrapper<CustomerInfo> wrapper = new QueryWrapper<>();
        //存在查询条件
        Integer custId = customerQueryVo.getCustId();//客户号
        String surname = customerQueryVo.getSurname();//客户姓名
        if (custId!=null){
            wrapper.eq("cust_id",custId);
        }
        if (!StringUtils.isEmpty(surname)){
            wrapper.eq("surname",surname);
        }
        wrapper.orderByAsc("cust_id");
        IPage<CustomerInfo> iPage = infoMapper.selectPage(infoPage, wrapper);


        return iPage;
    }
}
