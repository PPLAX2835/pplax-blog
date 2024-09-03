package xyz.pplax.pplaxblog.starter.security.exception;

import org.springframework.security.core.AuthenticationException;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;

/**
 * 邮箱未激活异常
 */
public class EmailUnactivatedException extends AuthenticationException {

    public EmailUnactivatedException() {
        super(HttpStatus.EMAIL_UNACTIVATED.getMessage());
    }

    public EmailUnactivatedException(String msg) {
        super(msg);
    }

}