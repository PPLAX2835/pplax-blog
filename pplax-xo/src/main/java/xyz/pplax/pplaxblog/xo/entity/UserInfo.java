package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.entity.SuperEntity;

import java.util.Date;

/**
 * @description 用户信息表
 * @author PPLAX
 * @date 2024-1-26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_user_info")
public class UserInfo extends SuperEntity<UserInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别(1:男2:女)
     */
    private Integer gender;

    /**
     * 个人头像 图片uid
     */
    private String avatarPictureUid;

    /**
     * 出生年月日
     */
    private Date birthday;

    /**
     * 自我简介最多150字
     */
    private String summary;

    public UserInfo() {}

}