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
     * 成员1uid，用于私聊
     */
    private String member1Uid;

    /**
     * 成员1uid，用于私聊
     */
    private String member2Uid;

    /**
     * 类型 0公共 1私聊
     */
    private Integer  type;

    /**
     * 用户
     */
    @TableField(exist = false)
    private User member1;

    @TableField(exist = false)
    private User member2;

    public ChatRoom() {}
}
