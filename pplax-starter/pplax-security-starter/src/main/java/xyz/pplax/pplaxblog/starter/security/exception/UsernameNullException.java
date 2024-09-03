package xyz.pplax.pplaxblog.starter.security.exception;

import org.springframework.security.core.AuthenticationException;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;

/**
 * 用户名不存在异常
 */
public class UsernameNullException extends AuthenticationException {

    public UsernameNullException() {
        super(HttpStatus.USERNAME_IS_NULL.getMessage());
    }

    public UsernameNullException(String msg) {
        super(msg);
    }

}