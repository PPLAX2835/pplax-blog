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
 * 博客分类参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "博客分类参数")
public class BlogSortEditDto extends BaseDto<BlogSortEditDto> {

    @ApiModelProperty(example = "el-icon-knife-fork", notes = "图标", required = false)
    @Range(required = false, min = 0, max = 100, groups = {Update.class, Insert.class}, message = "图标参数异常")
    private String icon;

    @ApiModelProperty(example = "怪物猎人", notes = "分类名", required = false)
    @Range(min = 1, max = 10, groups = {Update.class, Insert.class}, message = "分类名长度在1到10之间")
    private String sortName;

    @ApiModelProperty(example = "这是一条介绍", notes = "介绍", required = false)
    @Range(required = false, min = 0, max = 100, groups = {Update.class, Insert.class}, message = "介绍长度在0到100之间")
    private String content;

    @ApiModelProperty(example = "", notes = "父级分类uid", required = false)
    @IntegerNotNull(groups = {Update.class, Insert.class}, message = "排序不能为空")
    private Integer sortNo;

}
