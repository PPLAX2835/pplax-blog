package xyz.pplax.pplaxblog.starter.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 邮箱未激活异常
 */
public class EmailUnactivatedException extends AuthenticationException {

    public EmailUnactivatedException(String msg) {
        super(msg);
    }

}