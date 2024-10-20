package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;

/**
 * @description 标签表
 * @author PPLAX
 * @date 2023-11-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_tag")
public class Tag extends SuperEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 标签内容
	 */
	private String name;

	/**
	 * 推荐等级
	 */
	private Integer level;

	/**
	 * 标签简介
	 */
	private Integer clickCount;

	/**
	 * 引用量
	 */
	@TableField(exist = false)
	private Integer cites;

	public Tag() {}
}