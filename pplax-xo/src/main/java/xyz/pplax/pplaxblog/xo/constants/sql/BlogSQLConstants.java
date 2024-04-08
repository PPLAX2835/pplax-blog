package xyz.pplax.pplaxblog.xo.constants.sql;


import xyz.pplax.pplaxblog.commons.constants.BaseSQLConstants;

/**
 * Blog表SQL字段常量
 */
public final class BlogSQLConstants extends BaseSQLConstants {

	public final static String C_UID = "t_blog.uid";
	public final static String C_CREATE_TIME = "t_blog.create_time";
	public final static String C_UPDATE_TIME = "t_blog.update_time";
	public final static String C_STATUS = "t_blog.status";

	public final static String TITLE = "t_blog.title";
	public final static String SUMMARY = "t_blog.summary";
	public final static String BLOG_CONTENT_UID = "t_blog.blog_content_uid";
	public final static String TAG_UIDS = "t_blog.tag_uids";
	public final static String CLICK_COUNT = "t_blog.click_count";
	public final static String QUANTITY = "t_blog.quantity";
	public final static String COLLECT_COUNT = "t_blog.collect_count";
	public final static String COVER_IMAGE_UID = "t_blog.cover_image_uid";
	public final static String IS_PUBLISH = "t_blog.is_publish";
	public final static String USER_UID = "t_blog.user_uid";
	public final static String IS_ORIGINAL = "t_blog.is_original";
	public final static String ARTICLES_PART = "t_blog.articles_part";
	public final static String BLOG_SORT_UID = "t_blog.blog_sort_uid";
	public final static String LEVEL = "t_blog.level";

}
