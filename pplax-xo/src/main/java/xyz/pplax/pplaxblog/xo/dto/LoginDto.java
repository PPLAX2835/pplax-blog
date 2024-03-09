package xyz.pplax.pplaxblog.xo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.validator.annotion.NotBlank;
import xyz.pplax.pplaxblog.commons.validator.annotion.Range;
import xyz.pplax.pplaxblog.commons.validator.group.GetOne;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.commons.validator.group.Update;
import xyz.pplax.pplaxblog.xo.base.dto.BaseDto;


/**
 * 登录参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "登录参数")
public class LoginDto extends BaseDto {

    @ApiModelProperty(example = "用户名", notes = "用户名", required = true)
    @NotBlank(groups = {GetOne.class}, message = "用户名username不能为空")
    @Range(min = 3, max = 30, groups = {GetOne.class})
    private String username;

    @ApiModelProperty(example = "密码", notes = "密码", required = true)
    @NotBlank(groups = {GetOne.class}, message = "密码password不能为空")
    private String password;

    @ApiModelProperty(example = "验证码nonceStr", notes = "验证码nonceStr", required = true)
    @NotBlank(groups = {GetOne.class}, message = "验证码nonceStr不能为空")
    private String nonceStr;

    @ApiModelProperty(example = "验证码value", notes = "验证码value", required = true)
    @NotBlank(groups = {GetOne.class}, message = "验证码value不能为空")
    private String value;
}
