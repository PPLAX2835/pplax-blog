package xyz.pplax.pplaxblog.xo.constants.sql;

import xyz.pplax.pplaxblog.commons.constants.BaseSQLConstants;

/**
* @description ${tableName}表SQL字段常量
* @author ${author}
* @date ${date}
*/
public final class ${className}SQLConstants extends BaseSQLConstants {

	public final static String C_UID = "${tableName}.uid";
	public final static String C_CREATE_TIME = "${tableName}.create_time";
	public final static String C_UPDATE_TIME = "${tableName}.update_time";
	public final static String C_STATUS = "${tableName}.status";

	<#list attributes as attribute>
	/**
	* ${attribute.attributeComment}
	*/
	public final static String ${attribute.columnVariableName} = "${tableName}.${attribute.columnName}";

	</#list>

}
