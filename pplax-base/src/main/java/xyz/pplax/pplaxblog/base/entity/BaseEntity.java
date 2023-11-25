package xyz.pplax.pplaxblog.base.entity;

import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;


/**
 * Entity基类
 */
@Entity
public class BaseEntity implements Serializable{

	private static final long serialVersionUID = -5461194286312295203L;
	private String uid; // 唯一uid
	private int oid; // oid
	private int status; // 0 失效  1 生效
	private Timestamp createtime; //创建时间
	private Timestamp updatetime; //更新时间
	
	public BaseEntity() {
		this.uid = getUUID();
		this.status = 1;
		this.createtime = new Timestamp(System.currentTimeMillis()); 
		this.updatetime = new Timestamp(System.currentTimeMillis()); 
	}
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public int getOid() {
		return oid;
	}
	
	public void setOid(int oid) {
		this.oid = oid;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}

}