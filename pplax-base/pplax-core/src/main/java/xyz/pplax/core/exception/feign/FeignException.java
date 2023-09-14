package xyz.pplax.core.exception.feign;

import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.PPLAXException;

/**
 * Feign相关异常
 */
public class FeignException extends PPLAXException {

    public FeignException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public FeignException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public FeignException(String message) {
        super(message);
    }
}
