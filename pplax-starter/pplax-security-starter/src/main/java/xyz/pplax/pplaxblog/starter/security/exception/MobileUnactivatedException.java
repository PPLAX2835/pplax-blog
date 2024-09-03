package xyz.pplax.pplaxblog.starter.security.exception;

import org.springframework.security.core.AuthenticationException;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;

/**
 * 手机未激活异常
 */
public class MobileUnactivatedException extends AuthenticationException {

    public MobileUnactivatedException() {
        super(HttpStatus.EMAIL_UNACTIVATED.getMessage());
    }

    public MobileUnactivatedException(String msg) {
        super(msg);
    }

}