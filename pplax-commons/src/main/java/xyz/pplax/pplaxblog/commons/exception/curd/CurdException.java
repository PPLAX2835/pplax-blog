package xyz.pplax.pplaxblog.commons.exception.curd;

import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.exception.BaseException;

/**
 * 删除异常
 */
public class CurdException extends BaseException {
    public CurdException() {
        super();
    }

    public CurdException(String msg) {
        super(msg);
    }

    public CurdException(HttpStatus httpStatus) {
        super(httpStatus);
    }
}
