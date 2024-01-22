package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.base.entity.SuperEntity;

/**
 * @description 博客内容表
 * @author PPLAX
 * @date 2023-11-28
 */
@TableName("t_blog_content")
@Data
@EqualsAndHashCode(callSuper = true)
public class BlogContent extends SuperEntity<BlogContent> {
    private static final long serialVersionUID = 1L;

    /**
     * 博客内容
     */
    private String content;

    public BlogContent() {}
}
