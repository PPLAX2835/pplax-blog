package xyz.pplax.pplaxblog.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 用户名不存在异常
 */
public class UsernameNullException extends AuthenticationException {

    public UsernameNullException(String msg) {
        super(msg);
    }

}