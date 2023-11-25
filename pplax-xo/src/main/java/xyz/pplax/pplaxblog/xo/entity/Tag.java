package xyz.pplax.pplaxblog.xo.entity;


import xyz.pplax.pplaxblog.base.entity.BaseEntity;

import javax.persistence.Entity;

/**
 * 标签实体类
 */
@Entity
public class Tag extends BaseEntity {

	private static final long serialVersionUID = -1177787058917755230L;
	private String content; //标签名
	private int clickcount; //点击数
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getClickcount() {
		return clickcount;
	}
	public void setClickcount(int clickcount) {
		this.clickcount = clickcount;
	}
}
