package xyz.pplax.pplaxblog.commons.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;


/**
 * Entity基类
 */
@SuppressWarnings("rawtypes")
public class SuperEntity<T extends Model> extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = -4851055162892178225L;

	@TableId(value = "uid", type = IdType.UUID)
	private String uid; // 唯一uid	

	/**
	 * 状态
	 */
	private int status; // 0 失效  1 生效

	/**
	 * @TableField 配置需要填充的字段
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date createTime; //创建时间

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime; //更新时间

	public SuperEntity() {

		this.status = 1;
		this.createTime = new Date();
		this.updateTime = new Date();
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.uid;
	}

}