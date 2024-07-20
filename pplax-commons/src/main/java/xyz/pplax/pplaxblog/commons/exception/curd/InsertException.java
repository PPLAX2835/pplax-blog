package xyz.pplax.pplaxblog.commons.exception.curd;

import xyz.pplax.pplaxblog.commons.enums.HttpStatus;

/**
 * 插入异常
 */
public class InsertException extends CurdException {
    public InsertException() {
        super();
    }

    public InsertException(String msg) {
        super(msg);
    }
}
