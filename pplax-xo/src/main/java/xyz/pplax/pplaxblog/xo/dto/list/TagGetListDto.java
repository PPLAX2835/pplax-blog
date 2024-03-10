package xyz.pplax.pplaxblog.xo.dto.list;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.dto.PageDto;

/**
 * 获得标签列表用到的dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TagGetListDto extends PageDto {

    @ApiModelProperty(example = "false", notes = "根据点击量排序", required = false)
    private Boolean sortByClickCount;

    @ApiModelProperty(example = "false", notes = "根据引用量排序", required = false)
    private Boolean sortByCites;
}
