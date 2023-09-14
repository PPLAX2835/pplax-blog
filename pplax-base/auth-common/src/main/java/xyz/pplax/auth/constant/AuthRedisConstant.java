package xyz.pplax.auth.constant;

/**
 * 认证中心中，将信息存入redis中的键名
 */
public class AuthRedisConstant {
    /** redis中存储用户登录失败次数的键名前缀 **/
    public static final String USER_LOGIN_FAILURE_NUMBER_PREFIX = "login_failure_number:";

    /** 存在在redis中的UserDetails缓存的键名前缀 **/
    public static final String USER_DETAILS_CACHE_PREFIX = "UserDetailsCache:";

    /** redis中存储jwt黑名单的前缀 **/
    public static final String STORAGE_JWT_BLACKLIST_PREFIX = "jwt_blackList:";
}
