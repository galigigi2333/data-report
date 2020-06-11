package jrx.springboot.util;

import lombok.Getter;
import lombok.ToString;

/**
 * @author ls
 * @since 2020/06/04
 */
@Getter
@ToString
public enum ResultCodeEnum {

    SUCCESS(true, 200,"成功"),
    UNKNOWN_REASON(false, 500, "未知错误");

    private Boolean success;

    private Integer code;

    private String message;

    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
