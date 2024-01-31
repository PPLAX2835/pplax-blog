package xyz.pplax.pplaxblog.xo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.dto.BaseDto;


/**
 * 登录参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "登录参数")
public class LoginDto extends BaseDto {

    @ApiModelProperty(example = "用户名", notes = "用户名", required = false)
    private String username;

    @ApiModelProperty(example = "密码", notes = "密码", required = false)
    private String password;
}
