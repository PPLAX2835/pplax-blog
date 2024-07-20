package xyz.pplax.pplaxblog.commons.exception.curd;

import xyz.pplax.pplaxblog.commons.enums.HttpStatus;

/**
 * 查询异常
 */
public class SelectException extends CurdException {
    public SelectException() {
        super(HttpStatus.SELECT_FAIL);
    }

    public SelectException(String msg) {
        super(msg);
    }

    public SelectException(HttpStatus httpStatus) {
        super(httpStatus);
    }
}
