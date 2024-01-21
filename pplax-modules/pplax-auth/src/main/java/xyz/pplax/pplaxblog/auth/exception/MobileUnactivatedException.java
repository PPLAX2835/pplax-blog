package xyz.pplax.pplaxblog.auth.exception;

/**
 * 手机未激活异常
 */
public class MobileUnactivatedException extends RuntimeException {

    public MobileUnactivatedException(String msg) {
        super(msg);
    }

}