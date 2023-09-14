package xyz.pplax.admin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import xyz.pplax.admin.enums.GenderEnum;
import xyz.pplax.core.constant.FieldLengthConstant;
import xyz.pplax.core.valid.Delete;
import xyz.pplax.core.valid.Insert;
import xyz.pplax.core.valid.Update;
import xyz.pplax.core.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * user数据表的POJO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPojo {

    /**
     * 用户唯一id
     */
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * 用户简介
     */
    @Length(max = FieldLengthConstant.SUMMARY,message = "用户-简介不能超过{max}")
    private String userSummary;

    /**
     * 是否删除
     */
    private Boolean delete;

    /**
     * 账户是否被锁住
     */
    private Boolean accountLock;

    /**
     * 是否验证了邮箱
     */
    private Boolean verifyEmail;

    /**
     * 用户昵称
     */
    @Length(max = FieldLengthConstant.NICKNAME,groups = {Insert.class})
    private String nickname;

    /**
     * 用户性别
     */
    private GenderEnum gender;

    /**
     * 该用户对应的网站设置的uid
     */
    private Long siteUid;

    /**
     * 用户头像
     */
    @Length(max = FieldLengthConstant.URL)
    private String avatar;

    /**
     * 密码，使用md5加密
     */
    @ValidateString(value = "用户-密码",max = FieldLengthConstant.PASSWORD, groups = Insert.class)
    private String password;

    /**
     * 用户名（和用户昵称不同，用户名唯一，昵称只要符合要求都行）
     */
    @ValidateString(value = "用户-用户名",max = FieldLengthConstant.USERNAME, groups = Insert.class)
    private String username;

    /**
     * 专业
     */
    @Length(max = FieldLengthConstant.PROFESSION,message = "用户-专业不能超过{max}")
    private String profession;

    /**
     * 该用户对应的邮箱设置uid
     */
    private Long emailUid;

    /**
     * 用户登录记录的uid
     */
    private Long loginUid;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 原始密码
     */
    private String originPwd;

    /**
     * 新密码
     */
    private String newPwd;

    /**
     * 邮箱号
     */
    private String emailNumber;

}