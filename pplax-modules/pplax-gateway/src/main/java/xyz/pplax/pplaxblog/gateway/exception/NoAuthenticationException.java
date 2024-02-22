package xyz.pplax.pplaxblog.gateway.exception;


/**
 * 定义无授权信息异常
 */
public class NoAuthenticationException extends RuntimeException {

    public NoAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public NoAuthenticationException(String msg) {
        super(msg);
    }
}
