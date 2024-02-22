package xyz.pplax.pplaxblog.gateway.exception;

/**
 * 定义token过期异常
 */
public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException(String msg, Throwable t) {
        super(msg, t);
    }

    public ExpiredTokenException(String msg) {
        super(msg);
    }
}
