package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;


/**
 * @description 聊天记录表
 * @author PPLAX
 * @date 2024-4-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_chat_message")
public class ChatMessage extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 发言用户uid
     */
    private String userUid;

    /**
     * 聊天室uid
     */
    private String chatRoomUid;

    /**
     * 留言内容
     */
    private String content;


    /**
     * 用户信息
     */
    @TableField(exist = false)
    private UserInfo userInfo;

    /**
     * 聊天室
     */
    @TableField(exist = false)
    private ChatRoom chatRoom;

    public ChatMessage() {}
}
