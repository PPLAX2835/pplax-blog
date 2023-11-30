package xyz.pplax.pplaxblog.admin.dto.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "添加反馈的dto")
public class FeedBackAddDto {

    @ApiModelProperty(example = "", notes = "唯一标识符", required = false)
    private String uid;

    @ApiModelProperty(example = "", notes = "反馈用户uid", required = false)
    private String userUid;

    @ApiModelProperty(example = "你那个界面一点退出怎么嵌套起来了", notes = "内容", required = true)
    private String content;

    @ApiModelProperty(example = "1", notes = "状态", required = true)
    private Integer status;
}
