package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;


/**
 * @description 聊天室表
 * @author PPLAX
 * @date 2024-4-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_chat_room")
public class ChatRoom extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 群主uid
     */
    private String ownerUid;

    /**
     * 群聊名
     */
    private String name;

    /**
     * 成员uids，逗号分割
     */
    private String memberUids;

    /**
     * 头像uid
     */
    private String avatarUid;

    /**
     * 类型 0公共群聊 1群聊 2私聊
     */
    private Integer  type;

    /**
     * 头像
     */
    @TableField(exist = false)
    private FileStorage avatar;

    @TableField(exist = false)
    private User owner;

    public ChatRoom() {}
}
