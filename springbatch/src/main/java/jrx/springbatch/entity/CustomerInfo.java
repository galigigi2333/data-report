package jrx.springbatch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 客户信息表
 * </p>
 *
 * @author galigigi
 * @since 2020-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jrx_customer_info")
@ApiModel(value="CustomerInfo对象", description="客户信息表")
public class CustomerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户号")
    @TableId(value = "cust_id", type = IdType.AUTO)
    private Integer custId;

    @ApiModelProperty(value = "客户姓名")
    private String surname;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "教育状况")
    private String educaDes;

    @ApiModelProperty(value = "婚姻状况")
    private String marDes;

    @ApiModelProperty(value = "生日")
    private Integer birthday;

    @ApiModelProperty(value = "住址")
    private String address;


}
