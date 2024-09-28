package xyz.pplax.pplaxblog.commons.constants;

import java.util.regex.Pattern;

/**
 * 正则字符串常量
 */
public class BaseRegexConstants {

    // 数字校验
    public static final String NUMBER = "^[0-9]*$";
    public static final String N_DIGIT_NUMBER = "^\\d{n}$"; // n位数字
    public static final String AT_LEAST_N_DIGIT_NUMBER = "^\\d{n,}$"; // 至少n位数字
    public static final String M_TO_N_DIGIT_NUMBER = "^\\d{m,n}$"; // m-n位数字
    public static final String ZERO_OR_NONZERO_START = "^(0|[1-9][0-9]*)$"; // 零和非零开头的数字
    public static final String NONZERO_WITH_TWO_DECIMALS = "^([1-9][0-9]*)+(\\.[0-9]{1,2})?$"; // 非零最多两位小数
    public static final String POSITIVE_NEGATIVE_DECIMAL = "^(\\-)?\\d+(\\.\\d{1,2})?$"; // 1-2位小数的正负数
    public static final String SIGNED_DECIMAL = "^(\\-|\\+)?.\\d+(\\.\\d+)?$"; // 正负数和小数
    public static final String TWO_DECIMAL_POSITIVE = "^[0-9]+(\\.[0-9]{2})?$"; // 有两位小数的正实数
    public static final String ONE_TO_THREE_DECIMAL_POSITIVE = "^[0-9]+(\\.[0-9]{1,3})?$"; // 有1-3位小数的正实数
    public static final String NONZERO_POSITIVE_INTEGER = "^[1-9]\\d*$"; // 非零的正整数
    public static final String NONZERO_NEGATIVE_INTEGER = "^\\-[1-9]\\d*$"; // 非零的负整数
    public static final String NON_NEGATIVE_INTEGER = "^\\d+$"; // 非负整数
    public static final String NON_POSITIVE_INTEGER = "^((\\-\\d+)|(0+))$"; // 非正整数
    public static final String NON_NEGATIVE_FLOAT = "^\\d+(\\.\\d+)?$"; // 非负浮点数
    public static final String NON_POSITIVE_FLOAT = "^((\\-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$"; // 非正浮点数
    public static final String POSITIVE_FLOAT = "^[1-9]\\d*(\\.\\d+)?$"; // 正浮点数
    public static final String NEGATIVE_FLOAT = "^\\-([1-9]\\d*(\\.\\d+)?|0\\.\\d*[1-9]\\d*)$"; // 负浮点数
    public static final String FLOAT = "^(-?\\d+)(\\.\\d+)?$"; // 浮点数

    // 字符校验
    public static final String CHINESE = "^[\\u4e00-\\u9fa5]{0,}$"; // 汉字
    public static final String ALPHANUMERIC = "^[A-Za-z0-9]+$"; // 英文或数字
    public static final String LENGTH_3_TO_20 = "^.{3,20}$"; // 长度为3-20的所有字符
    public static final String ALPHABET = "^[A-Za-z]+$"; // 由26个英文字母组成
    public static final String UPPERCASE_ALPHABET = "^[A-Z]+$"; // 由26个大写英文字母组成
    public static final String LOWERCASE_ALPHABET = "^[a-z]+$"; // 由26个小写英文字母组成
    public static final String ALPHANUMERIC_WITH_UNDERSCORE = "^\\w+$"; // 由数字和26个英文字母组成
    public static final String ALPHANUMERIC_UNDERSCORE_REQUIRED = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*_)[A-Za-z0-9_]+$"; // 必须包含字母、数字和下划线
    public static final String ALPHANUMERIC_UNDERSCORE_REQUIRED_CASE = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*_)[A-Za-z0-9_]+$"; // 必须包含大小写字母、数字和下划线
    public static final String ALPHANUMERIC_SPECIAL_REQUIRED_CASE = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+$"; // 必须包含大小写字母、数字和特殊字符
    public static final String CHINESE_ENGLISH_NUMBER_UNDERSCORE = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$"; // 中文、英文、数字包括下划线
    public static final String CHINESE_ENGLISH_NUMBER_NO_UNDERSCORE = "^[\\u4E00-\\u9FA5A-Za-z0-9]+$"; // 中文、英文、数字不包括下划线
    public static final String ALLOW_SPECIAL_CHARACTERS = "[^%&',;=?$\\x22]+"; // 允许特殊字符
    public static final String DISALLOW_TILDE = "[^~\\x22]+"; // 禁止~字符

    // 特殊需求
    public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"; // Email
    public static final String DOMAIN_NAME = "[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(/\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+/?"; // 域名
    public static final String INTERNET_URL = "[a-zA-z]+://[^\\s]*"; // Internet URL
    public static final String PHONE_NUMBER = "^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])\\d{8}$"; // 手机号码
    public static final String LANDLINE_NUMBER = "^(\\(\\d{3,4}-)|\\d{3,4}-)?\\d{7,8}$"; // 电话号码
    public static final String ID_CARD = "^((\\d{18})|([0-9x]{18})|([0-9X]{18}))$"; // 身份证号码
    public static final String VALID_ACCOUNT = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$"; // 账号合法性
    public static final String PASSWORD = "^[a-zA-Z]\\w{5,17}$"; // 密码
    public static final String STRONG_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$"; // 强密码
    public static final String DATE_FORMAT = "^\\d{4}-\\d{1,2}-\\d{1,2}$"; // 日期格式
    public static final String MONTH = "^(0?[1-9]|1[0-2])$"; // 月份
    public static final String DAY = "^((0?[1-9])|((1|2)[0-9])|30|31)$"; // 日期
    public static final String MONEY_FORMAT = "^[0-9]{1,3}(,[0-9]{3})*(\\.[0-9]{1,2})?$"; // 钱的输入格式
    public static final String XML_FILE = "^([a-zA-Z]+-?)+[a-zA-Z0-9]+\\.[x|X][m|M][l|L]$"; // XML文件
    public static final String DOUBLE_BYTE_CHAR = "[^\\x00-\\xff]"; // 双字节字符
    public static final String EMPTY_LINE = "\\n\\s*\\r"; // 空白行
    public static final String HTML_TAG = "<(\\S*?)[^>]*>.*?</\\1>|<.*? />"; // HTML标记
    public static final String WHITESPACE = "^\\s*|\\s*$"; // 首尾空白字符
    public static final String QQ_NUMBER = "[1-9][0-9]{4,}"; // QQ号
    public static final String POSTAL_CODE = "[1-9]\\d{5}(?!\\d)"; // 邮政编码
    public static final String IP_ADDRESS = "\\d+\\.\\d+\\.\\d+\\.\\d+"; // IP地址

    public static void main(String[] args) {
        System.out.println(Pattern.matches(PHONE_NUMBER, "1373121446"));
    }

}
