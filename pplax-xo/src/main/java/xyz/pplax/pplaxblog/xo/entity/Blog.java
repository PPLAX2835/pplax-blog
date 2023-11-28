package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.base.entity.SuperEntity;

import java.util.List;

/**
 * @description 博客表
 * @author PPLAX
 * @date 2023-11-28
 */
@TableName("t_blog")
@Data
@EqualsAndHashCode(callSuper = true)
public class Blog extends SuperEntity<Blog>{

	private static final long serialVersionUID = 1L;

	/**
	 * 博客标题
	 */
	private String title;

	/**
	 * 博客简介
	 */
	private String summary;

	/**
	 * 博客内容uid
	 */
	private String blogContentUid;

	/**
	 * 标签uid
	 */
	private String tagUid;

	/**
	 * 博客点击数
	 */
	private Integer clickCount;

	/**
	 * 博客收藏数
	 */
	private Integer collectCount;

	/**
	 * 标题图片uid
	 */
	private String pictureUid;

	/**
	 * 用户uid
	 */
	private String userUid;

	/**
	 * 是否原创（0:不是 1：是）
	 */
	private String isOriginal;

	/**
	 * 作者
	 */
	private String author;

	/**
	 * 文章出处
	 */
	private String articlesPart;

	/**
	 * 博客分类UID
	 */
	private String blogSortUid;

	/**
	 * 推荐等级(0:正常)
	 */
	private Integer level;

	/**
	 * 以下字段不存入数据库，封装为了方便使用
	 */
	@TableField(exist = false)
	private List<Tag> tagList; //标签,一篇博客对应多个标签

	@TableField(exist = false)
	private String blogContent; // 内容，不主动查这个默认应该空，提升效率

	public Blog() {}
}