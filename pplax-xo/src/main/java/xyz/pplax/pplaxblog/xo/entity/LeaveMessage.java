package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;


/**
 * @description 留言表
 * @author PPLAX
 * @date 2024-4-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_leave_message")
public class LeaveMessage extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 发言用户uid
     */
    private String userUid;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 留言ip
     */
    private String ip;

    /**
     * 留言地址
     */
    private String address;

    /**
     * 用户信息
     */
    @TableField(exist = false)
    private UserInfo userInfo;

    public LeaveMessage() {}
}
