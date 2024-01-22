package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.base.entity.SuperEntity;

/**
 * @description 标签表
 * @author PPLAX
 * @date 2023-11-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
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
	private Integer clickCount;

	public Tag() {}
}