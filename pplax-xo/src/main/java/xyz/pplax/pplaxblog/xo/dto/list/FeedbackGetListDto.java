package xyz.pplax.pplaxblog.xo.dto.list;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.dto.BaseDto;
import xyz.pplax.pplaxblog.xo.base.dto.PageDto;

/**
 * 获得标签列表用到的dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FeedbackGetListDto extends BaseDto {

    @ApiModelProperty(example = "false", notes = "类型", required = false)
    private Integer type;
}
