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
 * 标签参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "标签参数")
public class FeedbackEditDto extends BaseDto<FeedbackEditDto> {


    @ApiModelProperty(example = "0", notes = "类型", required = true)
    @IntegerNotNull(groups = {Update.class, Insert.class}, message = "请输入类型")
    private Integer type;

}
