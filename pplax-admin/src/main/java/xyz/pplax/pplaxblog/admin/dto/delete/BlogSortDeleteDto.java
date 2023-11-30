package xyz.pplax.pplaxblog.admin.dto.delete;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "删除博客分类的dto")
public class BlogSortDeleteDto {

    @ApiModelProperty(example = "", notes = "唯一标识符", required = false)
    private String uid;

}
