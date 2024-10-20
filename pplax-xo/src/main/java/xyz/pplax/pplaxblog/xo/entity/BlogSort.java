package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;

/**
 * @description 博客分类表
 * @author PPLAX
 * @date 2023-11-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_blog_sort")
public class BlogSort extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分类名
     */
    private String sortName;

    /**
     * 分类内容
     */
    private String content;

    /**
     * 分类图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sortNo;

    /**
     * 点击数
     */
    private Integer clickCount;

    /**
     * 引用量
     */
    @TableField(exist = false)
    private Integer cites;

    public BlogSort() {}
}