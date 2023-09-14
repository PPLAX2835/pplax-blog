package xyz.pplax.file.enums;

/**
 * token相关常量
 */
public class TokenEnum {
    /** 生成token时使用的盐 **/
    public static final String JWT_SECRET_KEY = "PPLAXHATETHEWORLD";

    /** 生成token时的subject **/
    public static final String JWT_SUBJECT = "PPLAXHATETHEWORLD";

    /** 将token放置在请求头中的名称 **/
    public static final String JWT_HEADER_TOKEN_NAME = "authentication_token";
}
