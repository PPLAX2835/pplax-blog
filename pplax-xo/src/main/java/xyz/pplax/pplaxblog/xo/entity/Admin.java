package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.base.entity.SuperEntity;

import java.util.Date;

/**
 * @description 管理员表
 * @author PPLAX
 * @date 2023-11-28
 */
@TableName("t_admin")
@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends SuperEntity<Admin> {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String passWord;

	/**
	 * 性别(1:男2:女)
	 */
	private Integer gender;

	/**
	 * 个人头像 图片uid
	 */
	private String pictureUid;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 出生年月日
	 */
	private Date birthday;

	/**
	 * 手机
	 */
	private String mobile;

	/**
	 * 邮箱验证码
	 */
	private String validCode;

	/**
	 * 自我简介最多150字
	 */
	private String summary;

	/**
	 * 登录次数
	 */
	private Integer loginCount;

	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 最后登录IP
	 */
	private String lastLoginIp;

	public Admin() {}
}