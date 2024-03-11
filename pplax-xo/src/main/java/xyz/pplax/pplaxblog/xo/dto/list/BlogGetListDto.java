package xyz.pplax.pplaxblog.xo.dto.list;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.validator.annotion.IdValid;
import xyz.pplax.pplaxblog.xo.base.dto.BaseDto;
import xyz.pplax.pplaxblog.xo.base.dto.PageDto;

/**
 * 获得分类列表用到的dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogGetListDto extends BaseDto {

    @ApiModelProperty(example = "false", notes = "博客名", required = false)
    private String blogTitle;

    @ApiModelProperty(example = "false", notes = "分类uid", required = false)
    private String blogSortUid;

    @ApiModelProperty(example = "false", notes = "标签uid", required = false)
    private String tagUid;

}
