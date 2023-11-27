package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import xyz.pplax.pplaxblog.base.entity.SuperEntity;

/**
 * 图片实体类
 */

@TableName("t_picture")
public class Picture extends SuperEntity<Picture> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2646201532621057453L;
	

	private String fileUid; //图片的UID

	private String picName; //图片名称

	private String pictureSortUid; //所属相册分类id


	public String getFileUid() {
		return fileUid;
	}

	public void setFileUid(String fileUid) {
		this.fileUid = fileUid;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getPictureSortUid() {
		return pictureSortUid;
	}

	public void setPictureSortUid(String pictureSortUid) {
		this.pictureSortUid = pictureSortUid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
