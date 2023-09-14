package xyz.pplax.core.exception.oauth;

import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.PPLAXException;

/**
 * Oauth相关异常
 */
public class OauthException extends PPLAXException {

    public OauthException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public OauthException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public OauthException(String message) {
        super(message);
    }
}
