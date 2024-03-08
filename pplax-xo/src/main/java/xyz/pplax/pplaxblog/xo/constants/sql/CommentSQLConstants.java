package xyz.pplax.pplaxblog.xo.constants.sql;

import xyz.pplax.pplaxblog.commons.constants.BaseSQLConstants;

/**
 * comment表的sql字段常量
 */
public class CommentSQLConstants extends BaseSQLConstants {

    public final static String UID = "t_comment.uid";
    public final static String CREATE_TIME = "t_comment.create_time";
    public final static String UPDATE_TIME = "t_comment.update_time";
    public final static String STATUS = "t_comment.status";

    public final static String USER_UID = "t_comment.user_uid";
    public final static String TO_UID = "t_comment.to_uid";
    public final static String TO_USER_UID = "t_comment.to_user_uid";
    public final static String CONTENT = "t_comment.content";
    public final static String BLOG_UID = "t_comment.blog_uid";
}
