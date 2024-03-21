package xyz.pplax.pplaxblog.xo.dto.edit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.validator.annotion.*;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.commons.validator.group.Update;
import xyz.pplax.pplaxblog.xo.base.dto.BaseDto;

/**
 * 菜单编辑参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "菜单编辑参数")
public class MenuEditDto extends BaseDto<MenuEditDto> {

    @ApiModelProperty(example = "", notes = "父级菜单uid", required = true)
    @IdValid(required = false, groups = {Update.class, Insert.class}, message = "uid不合法")
    private String parentUid;

    @ApiModelProperty(example = "menu", notes = "类型", required = true)
    @NotBlank(groups = {Update.class, Insert.class}, message = "菜单类型不能为空")
    private String type;

    @ApiModelProperty(example = "", notes = "路由", required = true)
    @Range(required = false, min = 1, max = 255, groups = {Update.class, Insert.class}, message = "长度范围在1到255之间")
    private String route;

    @ApiModelProperty(example = "", notes = "请求端点", required = true)
    @Range(required = false, min = 1, max = 258, groups = {Update.class, Insert.class}, message = "长度范围在1到255之间")
    private String endpoint;

    @ApiModelProperty(example = "这是菜单名", notes = "菜单名", required = true)
    @NotBlank(groups = {Update.class, Insert.class}, message = "菜单名不能为空")
    private String title;

    @ApiModelProperty(example = "0", notes = "菜单级别", required = true)
    @IntegerNotNull(groups = {Update.class, Insert.class}, message = "菜单级别不能为空")
    private Integer level;

    @ApiModelProperty(example = "0", notes = "排序", required = true)
    @IntegerNotNull(required = false, groups = {Update.class, Insert.class}, message = "排序类型不合法")
    private Integer sortNo;

    @ApiModelProperty(example = "", notes = "图标", required = true)
    @Range(required = false, min = 0, max = 100, groups = {Update.class, Insert.class}, message = "图标参数异常")
    private String icon;

    @ApiModelProperty(example = "这是备注", notes = "备注", required = true)
    @Range(required = false, min = 0, max = 20, groups = {Update.class, Insert.class}, message = "长度范围在0到20之间")
    private String remarks;

    @ApiModelProperty(example = "0", notes = "是否隐藏", required = true)
    @BooleanNotNULL(groups = {Update.class, Insert.class}, message = "是否隐藏不能为空")
    private Boolean hidden;

}
