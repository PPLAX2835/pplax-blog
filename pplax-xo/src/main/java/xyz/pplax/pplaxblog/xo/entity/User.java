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
 * @description 用户表
 * @author PPLAX
 * @date 2023-11-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_user")
public class User extends SuperEntity<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别(1:男2:女)
     */
    private Integer gender;

    /**
     * 个人头像 图片uid
     */
    private String pictureUid;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 邮箱是否激活（0:不是 1：是）
     */
    private Integer isEmailEnabled;

    /**
     * 出生年月日
     */
    private Date birthday;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 手机是否激活（0:不是 1：是）
     */
    private Integer isMobileEnabled;

    /**
     * 自我简介最多150字
     */
    private String summary;

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

    /**
     * 移除敏感数据
     */
    public void sensitiveDataRemove () {
        this.passWord = null;
        this.mobile = null;
        this.loginCount = null;
        this.lastLoginIp = null;
        this.email = null;
    }
}