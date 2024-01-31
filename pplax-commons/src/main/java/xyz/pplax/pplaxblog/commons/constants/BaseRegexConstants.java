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
     * json正则
     */
    public static final String JSON_REGEX = "\\{\\s*\"(?:[^\"\\\\]|\\\\.)*\"\\s*:\\s*(?:\"(?:[^\"\\\\]|\\\\.)*\"|(?:-?\\d+(?:\\.\\d*)?(?:[eE][+-]?\\d+)?|true|false|null)\\s*)(?:,\\s*\"(?:[^\"\\\\]|\\\\.)*\"\\s*:\\s*(?:\"(?:[^\"\\\\]|\\\\.)*\"|(?:-?\\d+(?:\\.\\d*)?(?:[eE][+-]?\\d+)?|true|false|null)\\s*))*\\s*\\}";
}
