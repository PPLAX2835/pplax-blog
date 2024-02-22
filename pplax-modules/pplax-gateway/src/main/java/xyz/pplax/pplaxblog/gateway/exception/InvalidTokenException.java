package xyz.pplax.pplaxblog.gateway.exception;


/**
 * 定义token无效异常
 */
public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String msg, Throwable t) {
        super(msg, t);
    }

    public InvalidTokenException(String msg) {
        super(msg);
    }
}
