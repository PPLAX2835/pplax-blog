package xyz.pplax.pplaxblog.commons.exception.curd;

import xyz.pplax.pplaxblog.commons.enums.HttpStatus;

/**
 * 更新异常
 */
public class UpdateException extends CurdException {
    public UpdateException() {
        super(HttpStatus.UPDATE_FAIL);
    }

    public UpdateException(String msg) {
        super(msg);
    }

    public UpdateException(HttpStatus httpStatus) {
        super(httpStatus);
    }
}
