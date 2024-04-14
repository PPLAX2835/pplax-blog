package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;

import java.util.List;

/**
 * @description 说说表
 * @author PPLAX
 * @date 2024-4-8
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_say")
public class Say extends SuperEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户uid
	 */
	private String userUid;

	/**
	 * 图片uid 逗号分隔  最多九张
	 */
	private String imageUids;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 发表地址。可输入或者地图插件选择
	 */
	private String address;

	/**
	 * 是否开放查看  0未开放 1开放
	 */
	private Boolean isPublic;

	@TableField(exist = false)
	private List<FileStorage> imageList;

	@TableField(exist = false)
	private User user;

	@TableField(exist = false)
	private List<Comment> commentList;

	@TableField(exist = false)
	private List<Comment> likeList;

	@TableField(exist = false)
	private Boolean isLike;

	public Say() {}
}