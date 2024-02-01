package xyz.pplax.pplaxblog.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 手机未激活异常
 */
public class MobileUnactivatedException extends AuthenticationException {

    public MobileUnactivatedException(String msg) {
        super(msg);
    }

}