package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.base.entity.SuperEntity;

/**
 * @description 图片分类表
 * @author PPLAX
 * @date 2023-11-28
 */
@EqualsAndHashCode(callSuper = true)
@TableName("t_picture_sort")
@Data
public class PictureSort extends SuperEntity<PictureSort> {

	private static final long serialVersionUID = 1L;

	/**
	 * 文件uid
	 */
	private String fileUid;

	/**
	 * 分类名
	 */
	private String sortName;

	/**
	 * 父级分类uid
	 */
	private String parentUid;

	public PictureSort() {}

}
