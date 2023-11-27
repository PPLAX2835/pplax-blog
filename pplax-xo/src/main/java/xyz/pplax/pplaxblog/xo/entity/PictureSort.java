package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import xyz.pplax.pplaxblog.base.entity.SuperEntity;

/**
 * 相册分类实体类
 */
@TableName("t_picture_sort")
public class PictureSort extends SuperEntity<PictureSort> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3454006152368184626L;

	private String parentUid; // 父ID
	
	private String name; //分类名
	
	private String fileUid;//分类图片Uid

	public String getParentUid() {
		return parentUid;
	}

	public void setParentUid(String parentUid) {
		this.parentUid = parentUid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileUid() {
		return fileUid;
	}

	public void setFileUid(String fileUid) {
		this.fileUid = fileUid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
