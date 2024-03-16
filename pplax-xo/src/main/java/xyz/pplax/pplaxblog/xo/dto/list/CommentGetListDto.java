package xyz.pplax.pplaxblog.xo.dto.list;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.dto.PageDto;

/**
 * 获得评论列表用到的dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentGetListDto extends PageDto {

    @ApiModelProperty(example = "false", notes = "评论者的昵称", required = false)
    private String nickname;


    @ApiModelProperty(example = "false", notes = "评论类型", required = false)
    private Integer type;

}
