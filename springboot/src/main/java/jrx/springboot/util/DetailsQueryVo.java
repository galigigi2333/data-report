package jrx.springboot.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 查询明细条件对象
 */
@Data
public class DetailsQueryVo {

    @ApiModelProperty(value = "客户号")
    private Integer custId;



    @ApiModelProperty(value = "交易日期")
    private Date txnDatetime;

    @ApiModelProperty(value = "交易类型")
    private String transType;
}
