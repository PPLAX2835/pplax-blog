package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;

import java.util.List;


/**
 * @description 消息表
 * @author PPLAX
 * @date 2024-4-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_message")
public class Message extends SuperEntity {

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
     * 已读用户uids
     */
    private String readUserUids;

    /**
     * 留言ip
     */
    private String ip;

    /**
     * 类型 类型 0留言消息 1聊天消息
     */
    private Integer type;

    /**
     * 留言地址
     */
    private String address;

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

    /**
     * 已读用户
     */
    @TableField(exist = false)
    private List<User> readUserList;

    public Message() {}
}
