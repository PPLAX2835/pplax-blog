package xyz.pplax.pplaxblog.xo.dto.edit;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.validator.annotion.IdValid;
import xyz.pplax.pplaxblog.commons.validator.annotion.NotBlank;
import xyz.pplax.pplaxblog.commons.validator.annotion.Range;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.commons.validator.group.Update;
import xyz.pplax.pplaxblog.xo.base.dto.BaseDto;

import java.util.Date;

/**
 * 编辑用户信息时用到的dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInfoEditDto extends BaseDto<UserInfoEditDto> {

    @ApiModelProperty(example = "", notes = "头像文件uid", required = false)
    @IdValid(required = false, groups = {Update.class})
    private String avatarPictureUid;

    @ApiModelProperty(example = "", notes = "空间背景图文件uid", required = false)
    @IdValid(required = false, groups = {Update.class})
    private String spaceBackgroundPictureUid;

    @ApiModelProperty(example = "pplax", notes = "用户名", required = true)
    @Range(min = 3, max = 30, groups = {Update.class, Insert.class}, message = "用户名长度限制在3到30之间")
    @NotBlank(groups = {Update.class, Insert.class})
    private String username;

    @ApiModelProperty(example = "普拉克斯", notes = "昵称", required = true)
    @Range(min = 1, max = 50, groups = {Update.class, Insert.class}, message = "昵称长度限制在1到50之间")
    @NotBlank(groups = {Update.class, Insert.class}, message = "昵称不能为空")
    private String nickname;

    @ApiModelProperty(example = "", notes = "角色uid", required = true)
    @IdValid(groups = {Update.class, Insert.class})
    private String roleUid;

    @ApiModelProperty(example = "password123456", notes = "密码", required = true)
    @Range(min = 8, max = 16, groups = {Insert.class})
    @NotBlank(groups = {Insert.class})
    private String password;

    @ApiModelProperty(example = "2018-09-20", notes = "生日", required = false)
    private Date birthday;

    @ApiModelProperty(example = "这个人很懒，后边忘了", notes = "个性签名", required = false)
    @Range(max = 100, groups = {Update.class, Insert.class})
    private String summary;

    @ApiModelProperty(example = "普拉克斯", notes = "昵称", required = true)
    @Range(min = 1, max = 50, groups = {Update.class, Insert.class}, message = "长度限制在1到50之间")
    private String email;
}
