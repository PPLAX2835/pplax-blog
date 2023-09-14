package xyz.pplax.core.constant;

/**
 * redis相关常量
 */
public class RedisConstant {

    /** 存储验证账户的前缀 **/
    public static final String STORAGE_VERIFY_ACCOUNT_PREFIX = "verify_account_";

    public static final int STORAGE_VERIFY_ACCOUNT_EXPIRATION_TIME = 120000;
    public static final String STORAGE_ALL_PERMISSION = "adminPermissionLoadAllRolePermission";

    /** redis中存储所有角色名和路径权限的key **/
    public static final String STORAGE_ROLE_PERMISSION_INFO = "role_permission_info";

    /** redis中存储所有白名单的key **/
    public static final String STORAGE_WHITE_URL_INFO = "role_white_url_info";

    public static final String STORAGE_ARTICLE_LIKE_NUMBER = "article_like_num:";
    public static final String LOCK_ARTICLE_LIKE_NUMBER = "lock_like_num:";
}
