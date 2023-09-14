package xyz.pplax.core.exception.email;

import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.PPLAXException;

/**
 * 邮件相关异常
 */
public class EmailException extends PPLAXException {

    public EmailException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public EmailException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public EmailException(String message) {
        super(message);
    }

}
