package xyz.pplax.pplaxblog.xo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.dto.BaseDto;

import javax.validation.constraints.NotBlank;


/**
 * 登录参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "登录参数")
public class LoginDto extends BaseDto {

    @ApiModelProperty(example = "用户名", notes = "用户名", required = true)
    @NotBlank(message = "验证码nonceStr不能为空")
    private String username;

    @ApiModelProperty(example = "密码", notes = "密码", required = true)
    @NotBlank(message = "验证码nonceStr不能为空")
    private String password;

    @ApiModelProperty(example = "验证码nonceStr", notes = "验证码nonceStr", required = true)
    @NotBlank(message = "验证码nonceStr不能为空")
    private String nonceStr;

    @ApiModelProperty(example = "验证码value", notes = "验证码value", required = true)
    @NotBlank(message = "验证码value不能为空")
    private String value;
}
