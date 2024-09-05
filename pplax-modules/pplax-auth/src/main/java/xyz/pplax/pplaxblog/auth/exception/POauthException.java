package xyz.pplax.pplaxblog.auth.exception;

import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.exception.BaseException;

public class POauthException extends BaseException {

    public POauthException() {
        super(HttpStatus.UNAUTHORIZED);
    }

    public POauthException(String msg) {
        super(msg);
    }

    public POauthException(HttpStatus httpStatus) {
        super(httpStatus);
    }

}
