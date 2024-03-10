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
public class TagEditDto extends BaseDto<TagEditDto> {


    @ApiModelProperty(example = "0", notes = "推荐等级", required = true)
    @IntegerNotNull(groups = {Update.class, Insert.class}, message = "请输入推荐等级")
    private Integer level;

    @ApiModelProperty(example = "java", notes = "标签名", required = true)
    @Range(min = 1, max = 20, groups = {Update.class, Insert.class}, message = "标签名长度限制在1到20之间")
    private String name;

}
