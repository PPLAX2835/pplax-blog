package xyz.pplax.pplaxblog.commons.exception.request;

import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.exception.BaseException;

/**
 * 请求异常
 */
public class RequestException extends BaseException {
    public RequestException() {
        super(HttpStatus.BAD_REQUEST);
    }

    public RequestException(String msg) {
        super(msg);
    }

    public RequestException(HttpStatus httpStatus) {
        super(httpStatus);
    }
}
