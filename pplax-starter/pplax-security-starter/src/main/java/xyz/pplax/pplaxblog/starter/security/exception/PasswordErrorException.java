package xyz.pplax.pplaxblog.starter.security.exception;

import org.springframework.security.core.AuthenticationException;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;

/**
 * 密码错误异常
 */
public class PasswordErrorException extends AuthenticationException {

    public PasswordErrorException() {
        super(HttpStatus.ERROR_PASSWORD.getMessage());
    }

    public PasswordErrorException(String msg) {
        super(msg);
    }

}