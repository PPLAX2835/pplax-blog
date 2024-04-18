package xyz.pplax.pplaxblog.xo.dto.edit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.validator.annotion.IdValid;
import xyz.pplax.pplaxblog.commons.validator.annotion.IntegerNotNull;
import xyz.pplax.pplaxblog.commons.validator.annotion.Range;
import xyz.pplax.pplaxblog.commons.validator.group.*;
import xyz.pplax.pplaxblog.xo.base.dto.BaseDto;

/**
 * 评论参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "评论参数")
public class CommentEditDto extends BaseDto<CommentEditDto> {

    @ApiModelProperty(example = "这是说说", notes = "内容", required = true)
    @IdValid(required = false, groups = {Update.class, Insert.class})
    @Range(min = 1, max = 200, groups = {Update.class, Insert.class}, message = "长度限制在1到200之间")
    private String content;

    @ApiModelProperty(example = "", notes = "被评论的uid", required = false)
    @IdValid(required = false, groups = {Update.class, Insert.class})
    private String toUid;

    @ApiModelProperty(example = "", notes = "被回复用户uid", required = false)
    @IdValid(required = false, groups = {Update.class, Insert.class})
    private String toUserUid;

}
