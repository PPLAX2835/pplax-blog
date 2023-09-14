package xyz.pplax.core.exception.link;

import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.PPLAXException;

/**
 * 友情链接异常
 */
public class LinkException extends PPLAXException {

    public LinkException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public LinkException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public LinkException(String message) {
        super(message);
    }
}
