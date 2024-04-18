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
 * 消息参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "消息参数")
public class MessageEditDto extends BaseDto<MessageEditDto> {

    @ApiModelProperty(example = "这是说说", notes = "内容", required = true)
    @IdValid(required = false, groups = {Update.class, Insert.class})
    @Range(min = 1, max = 200, groups = {Update.class, Insert.class}, message = "长度限制在1到200之间")
    private String content;

}
