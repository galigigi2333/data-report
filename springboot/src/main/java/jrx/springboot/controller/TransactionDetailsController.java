package jrx.springboot.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jrx.springboot.entity.TransactionDetails;
import jrx.springboot.service.TransactionDetailsService;
import jrx.springboot.util.DetailsQueryVo;
import jrx.springboot.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 交易明细表 前端控制器
 * </p>
 *
 * @author galigigi
 * @since 2020-06-03
 */
@RestController
@RequestMapping("/transaction-details")
@Api(value = "交易明细")
@CrossOrigin
public class TransactionDetailsController {

    @Autowired
    private TransactionDetailsService detailsService;

    /**
     * 查询分页展示,按照交易日期降序
     */
    @ApiOperation(value = "交易明细分页查询")
    @GetMapping("get-list/{page}/{limit}")
    public R getPageList(
            @ApiParam(name = "page" ,value = "页数")
            @PathVariable("page")Integer page,
            @ApiParam(name = "limit" ,value = "每页记录数")
            @PathVariable("limit")Integer limit,
            @ApiParam(name = "queryVo" ,value = "查询条件")
                    DetailsQueryVo queryVo
    ){
        Page<TransactionDetails> infoPage = new Page<>(page,limit);
     IPage<TransactionDetails> iPage= detailsService.getPageList(infoPage,queryVo);

     return R.ok().data("items",iPage.getRecords()).data("total",iPage.getTotal());
    }

    /**
     * 增加交易明细信息
     * @param details
     * @return
     */
    @PostMapping("save")
    public R saveDetails(
            @ApiParam(name = "details",value = "交易明细")
            @RequestBody TransactionDetails details
    ){
        detailsService.save(details);
        return R.ok().message("增加成功");
    }

    /**
     * 更新交易明细
     * @param details
     * @return
     */
    @PutMapping("update")
    public R updateDetails(
            @ApiParam(name = "details",value = "交易明细")
            @RequestBody TransactionDetails details
    ){
        detailsService.updateById(details);
        return R.ok().message("更新成功");
    }

    @DeleteMapping("delete/{id}")
    public R deleteDetails(
            @ApiParam(name = "id",value = "交易明细id")
            @PathVariable("id")Integer id

    ){
        detailsService.removeById(id);
        return R.ok().message("删除成功");
    }

}

