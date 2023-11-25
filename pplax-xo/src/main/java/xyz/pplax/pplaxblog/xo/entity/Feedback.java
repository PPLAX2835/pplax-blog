package xyz.pplax.pplaxblog.xo.entity;

import xyz.pplax.pplaxblog.base.entity.BaseEntity;

import javax.persistence.Entity;

/**
 * 反馈实体类
 */
@Entity
public class Feedback extends BaseEntity {

	private static final long serialVersionUID = -6514316435269586923L;
	private String useruid; //用户uid
	private String content; //反馈内容
	
	//以下字段不存入数据库，封装为更好使用
	private String username;
	
	public String getUseruid() {
		return useruid;
	}
	public void setUseruid(String useruid) {
		this.useruid = useruid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}