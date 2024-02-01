package xyz.pplax.pplaxblog.starter.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 手机未激活异常
 */
public class MobileUnactivatedException extends AuthenticationException {

    public MobileUnactivatedException(String msg) {
        super(msg);
    }

}