package xyz.pplax.pplaxblog.xo.dto.list;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.dto.PageDto;

/**
 * 获得消息列表用到的dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageGetListDto extends PageDto {

    @ApiModelProperty(example = "0", notes = "类型", required = false)
    private Integer type;

    @ApiModelProperty(example = "", notes = "聊天室uid", required = false)
    private String chatRoomUid;
}
