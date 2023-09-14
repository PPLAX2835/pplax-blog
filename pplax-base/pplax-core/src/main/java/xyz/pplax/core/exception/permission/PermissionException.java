package xyz.pplax.core.exception.permission;

import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.PPLAXException;

/**
 * 权限相关异常
 */
public class PermissionException extends PPLAXException {

    public PermissionException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public PermissionException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public PermissionException(String message) {
        super(message);
    }
}
