package xyz.pplax.core.exception.common;

import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.PPLAXException;

/**
 * 一般异常
 */
public class CommonException extends PPLAXException {

    public CommonException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public CommonException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public CommonException(String message) {
        super(message);
    }
}
