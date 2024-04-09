package xyz.pplax.pplaxblog.xo.dto.edit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.validator.annotion.BooleanNotNULL;
import xyz.pplax.pplaxblog.commons.validator.annotion.IdValid;
import xyz.pplax.pplaxblog.commons.validator.annotion.IntegerNotNull;
import xyz.pplax.pplaxblog.commons.validator.annotion.Range;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.commons.validator.group.Update;
import xyz.pplax.pplaxblog.xo.base.dto.BaseDto;

/**
 * 说说编辑参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "说说编辑参数")
public class SayEditDto extends BaseDto<SayEditDto> {

    @ApiModelProperty(example = "", notes = "用户uid", required = false)
    private String userUid;

    @ApiModelProperty(example = "", notes = "图片uids", required = false)
    private String imageUids;

    @ApiModelProperty(example = "true", notes = "是否开放", required = true)
    @BooleanNotNULL(groups = {Update.class, Insert.class}, message = "请选择是否开放")
    private Boolean isPublic;

    @ApiModelProperty(example = "这是说说", notes = "内容", required = true)
    @Range(min = 1, max = 200, groups = {Update.class, Insert.class}, message = "长度限制在1到200之间")
    private String content;

    @ApiModelProperty(example = "北京", notes = "地址", required = true)
    @Range(min = 1, max = 100, groups = {Update.class, Insert.class}, message = "长度限制在1到100之间")
    private String address;
}
