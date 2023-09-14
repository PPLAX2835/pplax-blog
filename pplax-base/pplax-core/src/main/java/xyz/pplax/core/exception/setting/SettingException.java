package xyz.pplax.core.exception.setting;

import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.PPLAXException;

/**
 * 设置相关异常
 */
public class SettingException extends PPLAXException {

    public SettingException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public SettingException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public SettingException(String message) {
        super(message);
    }
}
