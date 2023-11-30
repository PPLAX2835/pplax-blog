package xyz.pplax.pplaxblog.admin.dto.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "添加博客分类的dto")
public class BlogSortAddDto {

    @ApiModelProperty(example = "", notes = "唯一标识符", required = false)
    private String uid;

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
