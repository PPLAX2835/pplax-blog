package xyz.pplax.core.exception;

import lombok.Getter;
import lombok.Setter;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;

/**
 * 全局异常父类 加入一些额外参数
 */
public abstract class PPLAXException extends RuntimeException {
    /** 响应码 **/
    @Getter
    @Setter
    public Integer statusCode;

    public PPLAXException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public PPLAXException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo.getMessage());
        this.statusCode = responseCodeInfo.getCode();
    }

    public PPLAXException(String message) {
        super(message);
        this.statusCode = ResponseStatusCodeEnum.UNKNOWN.getCode();
    }
}
