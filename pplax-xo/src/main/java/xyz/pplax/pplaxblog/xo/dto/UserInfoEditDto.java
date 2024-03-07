package xyz.pplax.pplaxblog.xo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.dto.BaseDto;

import java.util.Date;

/**
 * 编辑用户信息时用到的dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInfoEditDto extends BaseDto<BlogSortDto> {

    @ApiModelProperty(example = "", notes = "头像文件uid", required = false)
    private String avatarPictureUid;

    @ApiModelProperty(example = "普拉克斯", notes = "昵称", required = false)
    private String nickname;

    @ApiModelProperty(example = "", notes = "角色uid", required = false)
    private String roleUid;

    @ApiModelProperty(example = "pplax", notes = "用户名", required = false)
    private String username;

    @ApiModelProperty(example = "password123456", notes = "密码", required = false)
    private String password;

    @ApiModelProperty(example = "2018-09-20", notes = "生日", required = false)
    private Date birthday;

    @ApiModelProperty(example = "这个人很懒，后边忘了", notes = "个性签名", required = false)
    private String summary;

}
