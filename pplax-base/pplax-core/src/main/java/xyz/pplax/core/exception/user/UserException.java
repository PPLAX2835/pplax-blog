package xyz.pplax.core.exception.user;

import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.PPLAXException;

/**
 * 用户相关异常
 */
public class UserException extends PPLAXException {

    public UserException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public UserException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public UserException(String message) {
        super(message);
    }
}
