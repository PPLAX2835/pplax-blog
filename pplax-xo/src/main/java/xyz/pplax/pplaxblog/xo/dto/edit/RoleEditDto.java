package xyz.pplax.pplaxblog.xo.dto.edit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.validator.annotion.IntegerNotNull;
import xyz.pplax.pplaxblog.commons.validator.annotion.Range;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.commons.validator.group.Update;
import xyz.pplax.pplaxblog.xo.base.dto.BaseDto;

/**
 * 角色编辑参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "角色编辑参数")
public class RoleEditDto extends BaseDto<RoleEditDto> {

    @ApiModelProperty(example = "", notes = "菜单uid", required = true)
    private String menuUids;

    @ApiModelProperty(example = "游客", notes = "角色名", required = true)
    @Range(min = 1, max = 20, groups = {Update.class, Insert.class}, message = "角色名长度限制在1到20之间")
    private String roleName;

    @ApiModelProperty(example = "这是角色介绍", notes = "介绍", required = true)
    @Range(required = false, min = 1, max = 50, groups = {Update.class, Insert.class}, message = "介绍长度限制在1到50之间")
    private String summary;
}
