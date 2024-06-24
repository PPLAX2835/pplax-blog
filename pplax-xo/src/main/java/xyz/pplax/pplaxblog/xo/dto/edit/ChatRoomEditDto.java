package xyz.pplax.pplaxblog.xo.dto.edit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.validator.annotion.IdValid;
import xyz.pplax.pplaxblog.commons.validator.annotion.IntegerNotNull;
import xyz.pplax.pplaxblog.commons.validator.annotion.Range;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.commons.validator.group.Update;
import xyz.pplax.pplaxblog.xo.base.dto.BaseDto;

/**
 * 聊天室参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "聊天室参数")
public class ChatRoomEditDto extends BaseDto<ChatRoomEditDto> {

    @ApiModelProperty(example = "怪物猎人", notes = "分类名", required = false)
    @Range(required = false, min = 1, max = 15, groups = {Update.class, Insert.class}, message = "长度在1到15之间")
    private String name;

    @ApiModelProperty(example = "群主uid", notes = "群主uid", required = false)
    private String ownerUid;

    @ApiModelProperty(example = "成员uids", notes = "成员uids", required = false)
    @Range(required = false, min = 32, max = 1650, groups = {Update.class, Insert.class}, message = "成员最多50人")
    private String memberUids;

    @ApiModelProperty(example = "头像uid", notes = "头像uid", required = false)
    private String avatarUid;

    @ApiModelProperty(example = "1", notes = "类型，0 公共群聊，1 群聊，2 私聊", required = true)
    private Integer type;
}
