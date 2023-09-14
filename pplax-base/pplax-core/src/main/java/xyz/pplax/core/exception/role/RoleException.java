package xyz.pplax.core.exception.role;

import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.PPLAXException;

/**
 * 角色相关异常
 */
public class RoleException extends PPLAXException {

    public RoleException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public RoleException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public RoleException(String message) {
        super(message);
    }
}
