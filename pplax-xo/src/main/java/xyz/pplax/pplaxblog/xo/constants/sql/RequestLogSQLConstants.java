package xyz.pplax.pplaxblog.xo.constants.sql;

import xyz.pplax.pplaxblog.commons.constants.BaseSQLConstants;

/**
* @description request_log表SQL字段常量
* @author PPLAX
* @date Sat Aug 24 10:02:22 CST 2024
*/
public final class RequestLogSQLConstants extends BaseSQLConstants {

	public final static String C_UID = "request_log.uid";
	public final static String C_CREATE_TIME = "request_log.create_time";
	public final static String C_UPDATE_TIME = "request_log.update_time";
	public final static String C_STATUS = "request_log.status";

	/**
	* 唯一uid
	*/
	public final static String UID = "request_log.uid";

	/**
	* 操作用户uid
	*/
	public final static String USER_UID = "request_log.user_uid";

	/**
	* 请求接口uid
	*/
	public final static String API_UID = "request_log.api_uid";

	/**
	* ip
	*/
	public final static String IP = "request_log.ip";

	/**
	* 地址
	*/
	public final static String ADDRESS = "request_log.address";

	/**
	* 请求接口耗时
	*/
	public final static String SPEND_TIME = "request_log.spend_time";

	/**
	* 请求参数
	*/
	public final static String PARAMS_JSON = "request_log.params_json";

	/**
	* 浏览器
	*/
	public final static String BROWSER = "request_log.browser";

	/**
	* 操作系统
	*/
	public final static String ACCESS_OS = "request_log.access_os";

	/**
	* 类名
	*/
	public final static String CLASS_NAME = "request_log.class_name";

	/**
	* 方法名
	*/
	public final static String METHOD_NAME = "request_log.method_name";

	/**
	* 接口类型 0 管理后台接口 | 1 前台接口
	*/
	public final static String TYPE = "request_log.type";

	/**
	* 状态
	*/
	public final static String STATUS = "request_log.status";

	/**
	* 创建时间
	*/
	public final static String CREATE_TIME = "request_log.create_time";

	/**
	* 更新时间
	*/
	public final static String UPDATE_TIME = "request_log.update_time";


}
