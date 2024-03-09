package xyz.pplax.pplaxblog.xo.constants.sql;

import xyz.pplax.pplaxblog.commons.constants.BaseSQLConstants;

/**
 * 用户表字段常量
 */
public class UserSQLConstants extends BaseSQLConstants {


    public final static String C_UID = "t_user.uid";
    public final static String C_CREATE_TIME = "t_user.create_time";
    public final static String C_UPDATE_TIME = "t_user.update_time";
    public final static String C_STATUS = "t_user.status";

    public final static String ROLE_UID = "t_user.role_uid";
    public final static String USERNAME = "t_user.username";
    public final static String PASSWORD = "t_user.password";
    public final static String SALT = "t_user.salt1";
    public final static String NICKNAME = "t_user.nickname";
    public final static String GENDER = "t_user.gender";
    public final static String AVATAR_PICTURE_UID = "t_user.avatar_picture_uid";
    public final static String EMAIL = "t_user.email";
    public final static String IS_EMAIL_ACTIVATED = "t_user.is_email_activated";
    public final static String BIRTHDAY = "t_user.birthday";
    public final static String MOBILE = "t_user.mobile";
    public final static String IS_MOBILE_ACTIVATED = "is_mobile_activated";
    public final static String SUMMARY = "t_user.summary";
    public final static String LOGIN_COUNT = "t_user.login_count";
    public final static String LAST_LOGIN_TIME = "t_user.last_login_time";
    public final static String LAST_LOGIN_IP = "t_user.last_login_ip";
    public final static String LAST_LOGIN_ADDRESS = "t_user.last_login_address";

}
