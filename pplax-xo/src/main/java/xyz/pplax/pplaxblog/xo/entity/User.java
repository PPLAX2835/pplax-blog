package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;

import java.util.Date;

/**
 * @description 用户表
 * @author PPLAX
 * @date 2023-11-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_user")
public class User extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色uid
     */
    private String roleUid;

    /**
     * 用户信息uid
     */
    private String userInfoUid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 邮箱是否激活（0:不是 1：是）
     */
    private Boolean isEmailActivated;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 手机是否激活（0:不是 1：是）
     */
    private Boolean isMobileActivated;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    public User() {}

    @TableField(exist = false)
    private Role role; // 角色

    @TableField(exist = false)
    private UserInfo userInfo; // 用户

    /**
     * 移除敏感数据
     */
    public void sensitiveDataRemove () {
        this.password = null;
        this.salt = null;
        this.mobile = null;
        this.loginCount = null;
        this.lastLoginIp = null;
        this.email = null;
    }
}