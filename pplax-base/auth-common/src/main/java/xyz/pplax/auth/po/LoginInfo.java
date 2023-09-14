package xyz.pplax.auth.po;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * login_info数据表的实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "login_info数据表的实体类")
public class LoginInfo implements Serializable {

private static final long serialVersionUID = 13247652346523L;

	/**
	 * uid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "")
	private Long uid;

	/**
	 * 登录的用户名
	 */
	@Schema(title = "登录的用户名")
	private String username;

	/**
	 * 登录地
	 */
	@Schema(title = "登录地")
	private String loginLocation;

	/**
	 * 登录ip地址
	 */
	@Schema(title = "登录ip地址")
	private String loginIp;

	/**
	 * 登录的操作系统
	 */
	@Schema(title = "登录的操作系统")
	private String operationSystemInfo;

	/**
	 * 创建时间
	 */
	@Schema(title = "创建时间")
	private String createTime;

	/**
	 * 最后更新时间
	 */
	@Schema(title = "最后更新时间")
	private String updateTime;

	/**
	 * 登录的状态 1：登录成功
	 */
	@Schema(title = "登录的状态 1：登录成功")
	private Boolean status;

	/**
	 * 登录消息，记录登录异常等信息
	 */
	@Schema(title = "登录消息，记录登录异常等信息")
	private String message;

}