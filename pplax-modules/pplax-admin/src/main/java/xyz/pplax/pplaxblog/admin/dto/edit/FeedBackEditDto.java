package xyz.pplax.pplaxblog.admin.dto.edit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "编辑反馈的dto")
public class FeedBackEditDto {

    @ApiModelProperty(example = "", notes = "反馈用户uid", required = true)
    private String userUid;

    @ApiModelProperty(example = "你那个界面一点退出怎么嵌套起来了", notes = "内容", required = true)
    private String content;

    @ApiModelProperty(example = "1", notes = "状态", required = true)
    private Integer status;
}
