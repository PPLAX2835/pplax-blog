package xyz.pplax.pplaxblog.starter.security.exception;

import org.springframework.security.core.AuthenticationException;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;

/**
 * 用户不存在异常
 */
public class AccountIsNotRegisteredException extends AuthenticationException {

    public AccountIsNotRegisteredException() {
        super(HttpStatus.ACCOUNT_IS_NOT_REGISTERED.getMessage());
    }

    public AccountIsNotRegisteredException(String msg) {
        super(msg);
    }

}