package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;

/**
 * @description 反馈表
 * @author PPLAX
 * @date 2023-11-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_feedback")
public class Feedback extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户uid
     */
    private String userUid;

    /**
     * 标题
     */
    private String title;

    /**
     * 反馈的内容
     */
    private String content;

    /**
     * 附件图
     */
    private String pictureUid;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 反馈用户
     */
    @TableField(exist = false)
    private User user;

    /**
     * 附件图
     */
    @TableField(exist = false)
    private FileStorage picture;

    public Feedback() {}
}