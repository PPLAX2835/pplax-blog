package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;

/**
 * @description 评论表
 * @author PPLAX
 * @date 2023-11-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_comment")
public class Comment extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户uid
     */
    private String userUid;

    /**
     * 回复某条评论的uid
     */
    private String toUid;

    /**
     * 回复某个人的uid
     */
    private String toUserUid;

    /**
     * 类型 0博客评论 1博客点赞 2说说评论 3说说点赞
     */
    private Integer type;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论所属ip
     */
    private String ip;

    /**
     * 评论地址
     */
    private String address;

    /**
     * 原文uid  博客/说说 uid
     */
    private String originalUid;

    @TableField(exist = false)
    private User commentator;           // 评论人

    @TableField(exist = false)
    private User targetUser;            // 被评论人

    @TableField(exist = false)
    private Blog blog;                  // 被评论博客

    @TableField(exist = false)
    private Say say;                  // 被评论说说


    public Comment() {}
}