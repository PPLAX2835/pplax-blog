package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 博主表
 * </p>
 */
@TableName("t_visitor")
public class Visitor extends Model<Visitor> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一uid
     */
    @TableId(value = "uid", type = IdType.UUID)
    private String uid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 登录次数
     */
    private Integer logincount;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastlogintime;

    /**
     * 最后登录IP
     */
    private String lastloginip;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 创建时间
     */
    private LocalDateTime createtime;

    /**
     * 更新时间
     */
    private LocalDateTime updatetime;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLogincount() {
        return logincount;
    }

    public void setLogincount(Integer logincount) {
        this.logincount = logincount;
    }

    public LocalDateTime getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(LocalDateTime lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public String getLastloginip() {
        return lastloginip;
    }

    public void setLastloginip(String lastloginip) {
        this.lastloginip = lastloginip;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public LocalDateTime getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(LocalDateTime updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    protected Serializable pkVal() {
        return this.uid;
    }

    @Override
    public String toString() {
        return "Visitor{" +
        ", uid=" + uid +
        ", username=" + username +
        ", email=" + email +
        ", logincount=" + logincount +
        ", lastlogintime=" + lastlogintime +
        ", lastloginip=" + lastloginip +
        ", status=" + status +
        ", createtime=" + createtime +
        ", updatetime=" + updatetime +
        "}";
    }
}
