package xyz.pplax.pplaxblog.xo.constants.sql;

import xyz.pplax.pplaxblog.commons.constants.BaseSQLConstants;

/**
* @description t_exception_log表SQL字段常量
* @author PPLAX
* @date Wed Aug 28 20:48:20 CST 2024
*/
public final class ExceptionLogSQLConstants extends BaseSQLConstants {

	public final static String C_UID = "t_exception_log.uid";
	public final static String C_CREATE_TIME = "t_exception_log.create_time";
	public final static String C_UPDATE_TIME = "t_exception_log.update_time";
	public final static String C_STATUS = "t_exception_log.status";

	/**
	* 唯一uid
	*/
	public final static String UID = "t_exception_log.uid";

	/**
	* 操作用户uid
	*/
	public final static String USER_UID = "t_exception_log.user_uid";

	/**
	* 请求接口uid
	*/
	public final static String MENU_UID = "t_exception_log.menu_uid";

	/**
	* 请求端点
	*/
	public final static String ENDPOINT = "t_exception_log.endpoint";

	/**
	* ip
	*/
	public final static String IP = "t_exception_log.ip";

	/**
	* 地址
	*/
	public final static String ADDRESS = "t_exception_log.address";

	/**
	* 参数
	*/
	public final static String PARAMS_JSON = "t_exception_log.params_json";

	/**
	* 发生异常的类名
	*/
	public final static String CLASS_NAME = "t_exception_log.class_name";

	/**
	* 发生异常的方法名
	*/
	public final static String METHOD_NAME = "t_exception_log.method_name";

	/**
	* 异常对象json格式
	*/
	public final static String EXCEPTION_JSON = "t_exception_log.exception_json";

	/**
	* 异常简单信息,等同于e.getMessage
	*/
	public final static String EXCEPTION_MESSAGE = "t_exception_log.exception_message";

	/**
	* 状态
	*/
	public final static String STATUS = "t_exception_log.status";

	/**
	* 创建时间
	*/
	public final static String CREATE_TIME = "t_exception_log.create_time";

	/**
	* 更新时间
	*/
	public final static String UPDATE_TIME = "t_exception_log.update_time";


}
