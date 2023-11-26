package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import xyz.pplax.pplaxblog.base.entity.SuperEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 评论表
 * </p>
 */
@TableName("t_comment")
public class Comment extends SuperEntity<Comment> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一uid
     */
    @TableId(value = "uid", type = IdType.UUID)
    private String uid;

    /**
     * 用户uid
     */
    private String useruid;

    /**
     * 回复某条评论的uid
     */
    private String touid;

    /**
     * 回复某个人的uid
     */
    private String touseruid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 博客uid
     */
    private String bloguid;

    /**
     * 状态
     */
    private int status;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;
    

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUseruid() {
        return useruid;
    }

    public void setUseruid(String useruid) {
        this.useruid = useruid;
    }

    public String getTouid() {
        return touid;
    }

    public void setTouid(String touid) {
        this.touid = touid;
    }

    public String getTouseruid() {
        return touseruid;
    }

    public void setTouseruid(String touseruid) {
        this.touseruid = touseruid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBloguid() {
        return bloguid;
    }

    public void setBloguid(String bloguid) {
        this.bloguid = bloguid;
    }

    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
    protected Serializable pkVal() {
        return this.uid;
    }

    @Override
    public String toString() {
        return "Comment{" +
        ", uid=" + uid +
        ", useruid=" + useruid +
        ", touid=" + touid +
        ", touseruid=" + touseruid +
        ", username=" + username +
        ", content=" + content +
        ", bloguid=" + bloguid +
        ", status=" + status +
        ", createtime=" + createtime +
        ", updatetime=" + updatetime +
        "}";
    }
}
