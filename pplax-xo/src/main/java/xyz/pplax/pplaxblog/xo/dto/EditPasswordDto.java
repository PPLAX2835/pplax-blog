package xyz.pplax.pplaxblog.xo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.pplax.pplaxblog.commons.validator.annotion.NotBlank;
import xyz.pplax.pplaxblog.commons.validator.annotion.Range;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.commons.validator.group.Update;

@Data
public class EditPasswordDto {

    @ApiModelProperty(example = "旧密码", notes = "旧密码", required = true)
    @NotBlank(groups = {Update.class}, message = "旧密码oldPassword不能为空")
    @Range(min = 8, max = 16, groups = {Update.class}, message = "密码长度限制在8到16之间")
    private String oldPassword;

    @ApiModelProperty(example = "新密码", notes = "新密码", required = true)
    @NotBlank(groups = {Update.class}, message = "新密码newPassword不能为空")
    @Range(min = 8, max = 16, groups = {Update.class}, message = "密码长度限制在8到16之间")
    private String newPassword;

    @ApiModelProperty(example = "密码", notes = "密码", required = true)
    @NotBlank(groups = {Update.class}, message = "密码不能为空")
    @Range(min = 8, max = 16, groups = {Update.class}, message = "密码长度限制在8到16之间")
    private String password;

    @ApiModelProperty(example = "确认密码", notes = "确认密码", required = true)
    @NotBlank(groups = {Update.class}, message = "确认密码confirmPassword不能为空")
    @Range(min = 8, max = 16, groups = {Update.class}, message = "密码长度限制在8到16之间")
    private String confirmPassword;

    @ApiModelProperty(example = "邮箱", notes = "邮箱", required = true)
    @NotBlank(groups = {Insert.class}, message = "邮箱不能为空")
    private String email;

    @ApiModelProperty(example = "验证码", notes = "验证码", required = true)
    @NotBlank(groups = {Insert.class}, message = "验证码不能为空")
    private String code;
}
