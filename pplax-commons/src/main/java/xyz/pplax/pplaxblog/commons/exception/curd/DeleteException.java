package xyz.pplax.pplaxblog.commons.exception.curd;

import xyz.pplax.pplaxblog.commons.enums.HttpStatus;

/**
 * 删除异常
 */
public class DeleteException extends CurdException {
    public DeleteException() {
        super(HttpStatus.DELETE_FAIL);
    }

    public DeleteException(String msg) {
        super(msg);
    }

    public DeleteException(HttpStatus httpStatus) {
        super(httpStatus);
    }
}
