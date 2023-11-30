package xyz.pplax.pplaxblog.admin.dto.edit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "编辑博客分类的dto")
public class BlogSortEditDto {

    @ApiModelProperty(example = "怪物猎人", notes = "分类名", required = true)
    private String sortName;

    @ApiModelProperty(example = "怪物猎人的简介", notes = "简介", required = false)
    private String summary;

    @ApiModelProperty(example = "玩太刀玩的", notes = "内容", required = false)
    private String content;

    @ApiModelProperty(example = "1", notes = "状态", required = true)
    private Integer status;

    @ApiModelProperty(example = "", notes = "父级分类uid", required = false)
    private String parentBlogSortUid;

}
