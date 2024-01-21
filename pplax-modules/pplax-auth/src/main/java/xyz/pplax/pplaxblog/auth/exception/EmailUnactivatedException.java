package xyz.pplax.pplaxblog.auth.exception;

/**
 * 邮箱未激活异常
 */
public class EmailUnactivatedException extends RuntimeException {

    public EmailUnactivatedException(String msg) {
        super(msg);
    }

}