package xyz.pplax.pplaxblog.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 用户不存在异常
 */
public class AccountIsNotRegisteredException extends AuthenticationException {

    public AccountIsNotRegisteredException(String msg) {
        super(msg);
    }

}