package xyz.pplax.pplaxblog.auth.exception;

/**
 * 用户名不存在异常
 */
public class UsernameNullException extends RuntimeException {

    public UsernameNullException(String msg) {
        super(msg);
    }

}