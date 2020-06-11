package jrx.springboot.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jrx.springboot.entity.CustomerInfo;
import jrx.springboot.service.CustomerInfoService;
import jrx.springboot.util.CustomerQueryVo;
import jrx.springboot.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 客户信息表 前端控制器
 * </p>
 *
 * @author galigigi
 * @since 2020-06-03
 */
@RestController
@RequestMapping("/customer-info")
@Api(value = "客户信息")
@CrossOrigin
public class CustomerInfoController {
    @Autowired
    private  CustomerInfoService infoService;
//        private Logger logger= LoggerContext.getContext().getLogger("CustomerInfoController");
    /**
     * 查询，分页展示,按客户号升序
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("get-list/{page}/{limit}")
    public R getPageList(
            @ApiParam(name = "page" ,value = "页数",required = true)
            @PathVariable("page") Integer page,
            @ApiParam(name = "limit" ,value = "每页记录数",required = true)
            @PathVariable("limit") Integer limit,
            @ApiParam(name = "customerQueryVo" ,value = "查询条件",required = false)
             CustomerQueryVo customerQueryVo
    ){
        Page<CustomerInfo> infoPage = new Page<>(page,limit);
        IPage<CustomerInfo> iPage=infoService.getPageList(infoPage,customerQueryVo);
        return R.ok().data("items",iPage.getRecords()).data("total",infoPage.getTotal());
    }

    /**
     * 保存客户信息
     * @param customerInfo
     * @return
     */
    @ApiOperation(value = "保存客户信息")
    @PostMapping("save")
    public R saveCustomer(
            @ApiParam(name = "customerInfo",value = "客户信息")
            @RequestBody CustomerInfo customerInfo
    ){

        infoService.save(customerInfo);
        return R.ok().message("保存成功");
    }

    /**
     * 更新客户信息
     * @param customerInfo
     * @return
     */
    @ApiOperation(value = "更新客户信息")
    @PutMapping("update")
    public R updateCustomerById(
            @ApiParam(name = "customerInfo",value = "客户信息")
            @RequestBody CustomerInfo customerInfo
    ){
        infoService.updateById(customerInfo);
        return R.ok().message("更新成功");
    }

    /**
     * 根据id删除客户信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据Id删除客户信息")
    @DeleteMapping("delete/{id}")
    public R deleteCustomerById(
            @ApiParam(name = "id",value = "客户信息id")
            @PathVariable ("id") Integer id
    ){
        infoService.removeById(id);
        return R.ok().message("删除成功");
    }
}

