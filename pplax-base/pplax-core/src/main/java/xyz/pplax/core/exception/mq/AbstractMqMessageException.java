package xyz.pplax.core.exception.mq;

import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.PPLAXException;

/**
 * 和消息中间件相关的抽象异常类
 */
public abstract class AbstractMqMessageException extends PPLAXException {

    public AbstractMqMessageException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public AbstractMqMessageException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public AbstractMqMessageException(String message) {
        super(message);
    }
}
