package jrx.springboot.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询客户条件对象
 */
@Data
public class CustomerQueryVo {
    @ApiModelProperty(value = "客户号")
    @TableId(value = "cust_id", type = IdType.AUTO)
    private Integer custId;

    @ApiModelProperty(value = "客户姓名")
    private String surname;
}
