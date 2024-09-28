package xyz.pplax.pplaxblog.xo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.constants.BaseRegexConstants;
import xyz.pplax.pplaxblog.commons.validator.annotion.NotBlank;
import xyz.pplax.pplaxblog.commons.validator.annotion.Range;
import xyz.pplax.pplaxblog.commons.validator.annotion.Regex;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.xo.base.dto.BaseDto;


/**
 * 注册参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "注册参数")
public class RegisterDto extends BaseDto {

    @ApiModelProperty(example = "邮箱", notes = "邮箱", required = true)
    @NotBlank(groups = {Insert.class}, message = "邮箱不能为空")
    @Regex(groups = {Insert.class}, value = BaseRegexConstants.EMAIL, message = "邮箱不合法")
    private String email;

    @ApiModelProperty(example = "用户名", notes = "用户名", required = true)
    @NotBlank(groups = {Insert.class}, message = "用户名username不能为空")
    @Regex(groups = {Insert.class}, value = BaseRegexConstants.ALPHANUMERIC, message = "必须为英文或数字")
    @Range(min = 3, max = 30, groups = {Insert.class}, message = "长度在3到30个字符")
    private String username;

    @ApiModelProperty(example = "密码", notes = "密码", required = true)
    @NotBlank(groups = {Insert.class}, message = "密码password不能为空")
    @Regex(groups = {Insert.class}, value = BaseRegexConstants.ALPHANUMERIC_SPECIAL_REQUIRED_CASE, message = "必须为字母、数字或下划线")
    @Range(min = 8, max = 16, groups = {Insert.class}, message = "长度在8到16个字符")
    private String password;

    @ApiModelProperty(example = "昵称", notes = "昵称", required = true)
    @NotBlank(groups = {Insert.class}, message = "昵称不能为空")
    @Range(min = 1, max = 10, groups = {Insert.class}, message = "长度在1到20个字符")
    private String nickname;

    @ApiModelProperty(example = "验证码", notes = "验证码", required = true)
    @NotBlank(groups = {Insert.class}, message = "验证码不能为空")
    private String code;
}
