package xyz.pplax.pplaxblog.xo.dto.edit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.validator.annotion.*;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.commons.validator.group.Update;
import xyz.pplax.pplaxblog.xo.base.dto.BaseDto;

/**
 * 博客分类参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "博客分类参数")
public class BlogEditDto extends BaseDto<BlogEditDto> {

    @ApiModelProperty(example = "", notes = "作者uid", required = true)
    private String userUid;

    @ApiModelProperty(example = "这是博客内容", notes = "内容", required = true)
    @NotBlank(groups = {Update.class, Insert.class}, message = "文章内容不能为空")
    private String content;

    @ApiModelProperty(example = "", notes = "分类uid", required = true)
    @IdValid(groups = {Update.class, Insert.class}, message = "分类uid不能为空")
    private String blogSortUid;

    @ApiModelProperty(example = "true", notes = "是否原创", required = true)
    @BooleanNotNULL(groups = {Update.class, Insert.class}, message = "创作类型不能为空")
    private Boolean isOriginal;

    @ApiModelProperty(example = "", notes = "封面图片uid", required = false)
    @IdValid(required = false, groups = {Update.class, Insert.class}, message = "封面图片uid非法")
    private String coverImageUid;

    @ApiModelProperty(example = "", notes = "转载地址", required = false)
    private String articlesPart;

    @ApiModelProperty(example = "这是一条简介", notes = "简介", required = false)
    @Range(required = false, min = 0, max = 50, message = "简介限制在0到50之间")
    private String summary;

    @ApiModelProperty(example = "这是一条标题", notes = "标题", required = true)
    @Range(min = 1, max = 20, message = "标题长度限制在1到20之间")
    private String title;

    @ApiModelProperty(example = "0", notes = "推荐等级", required = true)
    @IntegerNotNull(groups = {Update.class, Insert.class}, message = "请选择推荐等级")
    private Integer level;

    @ApiModelProperty(example = "", notes = "标签uids", required = true)
    @NotBlank(groups = {Update.class, Insert.class}, message = "请选择标签")
    private String tagUids;
}
