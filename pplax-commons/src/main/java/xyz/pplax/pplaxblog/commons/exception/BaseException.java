package xyz.pplax.pplaxblog.commons.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;

/**
 * 异常基类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {

    private HttpStatus httpStatus;

    public BaseException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR.getMessage());
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public BaseException(HttpStatus httpStatus) {
        super(httpStatus.getMessage());
        this.httpStatus = httpStatus;
    }

    public BaseException(String msg) {
        super(msg);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
