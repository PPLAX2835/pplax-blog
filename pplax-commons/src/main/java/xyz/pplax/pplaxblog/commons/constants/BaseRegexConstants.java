package xyz.pplax.pplaxblog.commons.constants;

/**
 * 正则字符串常量
 */
public class BaseRegexConstants {

    /**
     * 手机正则
     */
    public static final String MOBILE_REGEX = "0?(13|14|15|18|17)[0-9]{9}";

    /**
     * 邮箱正则
     */
    public static final String EMAIL_REGEX = "[a-zA-Z0-9_]+@[a-zA-Z0-9]+(\\.[a-zA-Z]+)+";

    /**
     * 变量合法性正则，用于验证是否由字母、数字、下划线组成
     */
    public static final String VARIABLE_LEGALITY_REGEX = "^(?![A-Za-z]+$)(?![A-Z0-9_\\W]+$)(?![a-z0-9_\\W]+$)[\\w\\W]{8,}$";
}
