package xyz.pplax.core.exception.login;

import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.PPLAXException;

/**
 * 登录相关异常
 */
public class LoginException extends PPLAXException {

    public LoginException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public LoginException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public LoginException(String message) {
        super(message);
    }
}
