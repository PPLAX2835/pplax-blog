package xyz.pplax.pplaxblog.commons.exception.curd;

import xyz.pplax.pplaxblog.commons.enums.HttpStatus;

/**
 * 删除异常
 */
public class DeleteException extends CurdException {
    public DeleteException() {
        super();
    }

    public DeleteException(String msg) {
        super(msg);
    }
}
