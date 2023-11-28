package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.base.entity.SuperEntity;

/**
 * @description 图片表
 * @author PPLAX
 * @date 2023-11-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_picture")
public class Picture extends SuperEntity<Picture> {

	private static final long serialVersionUID = 1L;

	/**
	 * 文件uid
	 */
	private String fileUid;

	/**
	 * 图片名
	 */
	private String pictureName;

	/**
	 * 分类uid
	 */
	private String pictureSortUid;

	public Picture() {}
}