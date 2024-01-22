package xyz.pplax.pplaxblog.xo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.base.dto.BaseDto;

/**
 * 博客分类参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "博客分类参数")
public class BlogSortDto extends BaseDto<BlogSortDto> {

    /**
     * 增改相关参数
     */
    @ApiModelProperty(example = "怪物猎人", notes = "分类名", required = false)
    private String sortName;

    @ApiModelProperty(example = "怪物猎人的简介", notes = "简介", required = false)
    private String summary;

    @ApiModelProperty(example = "玩太刀玩的", notes = "内容", required = false)
    private String content;

    @ApiModelProperty(example = "", notes = "父级分类uid", required = false)
    private String parentBlogSortUid;


    /**
     * 查询相关参数
     */

    @ApiModelProperty(example = "false", notes = "根据点击量排序", required = false)
    private Boolean sortByClickCount;

    @ApiModelProperty(example = "false", notes = "根据引用量排序", required = false)
    private Boolean sortByCites;

}
