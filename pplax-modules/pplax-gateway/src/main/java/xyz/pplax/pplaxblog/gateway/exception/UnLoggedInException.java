package xyz.pplax.pplaxblog.gateway.exception;

/**
 * 定义未登录异常
 */
public class UnLoggedInException extends RuntimeException {
    public UnLoggedInException(String msg, Throwable t) {
        super(msg, t);
    }

    public UnLoggedInException(String msg) {
        super(msg);
    }
}
