package xyz.pplax.pplaxblog.admin.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "登录的dto")
public class LoginDto {

    @ApiModelProperty(name = "username", notes = "用户名", required = true)
    private String username;

    @ApiModelProperty(name = "password", notes = "密码", required = true)
    private String password;

}
