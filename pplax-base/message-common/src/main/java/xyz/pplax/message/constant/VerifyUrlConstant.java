package xyz.pplax.message.constant;

/**
 * 验证地址常量
 */
public class VerifyUrlConstant {

    /** 向邮箱中发送验证地址的时候，将key和value进行拼接的字符 **/
    public static final String VERIFY_URL_CONCAT_KEY_VALUE = ":";

    /** redis中存储验证地址的值得前缀 **/
    public static final String REDIS_STORAGE_VERIFY_URL_KEY_PREFIX = "verify_url:";

}
