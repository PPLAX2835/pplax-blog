package xyz.pplax.core.exception.mq;


import xyz.pplax.core.enums.ResponseStatusCodeEnum;


public class RabbitMQException extends AbstractMqMessageException {

    public RabbitMQException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public RabbitMQException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public RabbitMQException(String message) {
        super(message);
    }
}
