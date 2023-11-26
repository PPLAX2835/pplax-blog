package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import xyz.pplax.pplaxblog.base.entity.SuperEntity;

/**
 * <p>
 * 标签表
 * </p>
 */
@TableName("t_tag")
public class Tag extends SuperEntity<Tag> {

    private static final long serialVersionUID = 1L;


    /**
     * 标签内容
     */
    private String content;


    /**
     * 标签简介
     */
    private  int clickCount;


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getClickCount() {
		return clickCount;
	}


	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
