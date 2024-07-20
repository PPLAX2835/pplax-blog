package xyz.pplax.pplaxblog.commons.exception.curd;

import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.exception.BaseException;

/**
 * 删除异常
 */
public class CurdException extends BaseException {
    public CurdException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR.getMessage());
    }

    public CurdException(String msg) {
        super(msg);
    }
}
