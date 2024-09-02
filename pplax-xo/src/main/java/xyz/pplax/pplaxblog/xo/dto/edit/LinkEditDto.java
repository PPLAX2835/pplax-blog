package xyz.pplax.pplaxblog.xo.dto.edit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.validator.annotion.IdValid;
import xyz.pplax.pplaxblog.commons.validator.annotion.Range;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.commons.validator.group.Update;
import xyz.pplax.pplaxblog.xo.base.dto.BaseDto;

/**
 * 友链编辑参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "标题编辑参数")
public class LinkEditDto extends BaseDto<LinkEditDto> {

    @ApiModelProperty(example = "PPLAX", notes = "标题", required = true)
    @Range(min = 1, max = 20, groups = {Update.class, Insert.class}, message = "标题长度限制在1到20之间")
    private String title;

    @ApiModelProperty(example = "这是友链介绍", notes = "介绍", required = true)
    @Range(required = false, min = 1, max = 50, groups = {Update.class, Insert.class}, message = "介绍长度限制在1到50之间")
    private String summary;

    @ApiModelProperty(example = "pplax.xyz", notes = "url", required = true)
    @Range(max = 255, groups = {Update.class, Insert.class}, message = "介绍长度最大不超过255")
    private String url;

    @ApiModelProperty(example = "", notes = "图标文件uid", required = true)
    @IdValid(groups = {Update.class, Insert.class}, message = "图标文件uid不能为空")
    private String iconImageUid;
}
