package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.base.entity.SuperEntity;

/**
 * @description 友情链接表
 * @author PPLAX
 * @date 2023-11-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_link")
public class Link extends SuperEntity<Link> {

    private static final long serialVersionUID = 1L;

    /**
     * 友情链接标题
     */
    private String title;

    /**
     * 友情链接介绍
     */
    private String summary;

    /**
     * 友情链接URL
     */
    private String url;

    /**
     * 点击数
     */
    private Integer clickCount;

    public Link() {}
}