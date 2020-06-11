package jrx.springboot.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 日汇总表
 * </p>
 *
 * @author galigigi
 * @since 2020-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jrx_summary_statistics")
@ApiModel(value="SummaryStatistics对象", description="日汇总表")
public class SummaryStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "索引项")
    private String sIndex;

    @ApiModelProperty(value = "客户号")
    private Integer custId;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "日期")
    private Date transDate;

    @ApiModelProperty(value = "客户姓名")
    private String surname;

    @ApiModelProperty(value = "最大单笔交易金额")
    private BigDecimal tranMaxAmt;

    @ApiModelProperty(value = "当天还款总金额")
    private BigDecimal payAmt;

    @ApiModelProperty(value = "当天消费笔数")
    private Integer tranCnt;

    @ApiModelProperty(value = "当天还款笔数")
    private Integer payCnt;

    @ApiModelProperty(value = "当天交易总金额")
    private BigDecimal tranAmt;


}
