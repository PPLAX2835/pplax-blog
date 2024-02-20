package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;

/**
 * @description 菜单表
 * @author PPLAX
 * @date 2024-2-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_menu")
public class Menu extends SuperEntity<Menu> {

    private static final long serialVersionUID = 1L;

    /**
     * 父级菜单uid
     */
    private String parentUid;

    /**
     * 类型 button、menu
     */
    private String type;

    /**
     * 路由地址，menu使用
     */
    private String route;

    /**
     * 访问端口，button使用
     */
    private String endpoint;

    /**
     * 标题
     */
    private String title;

    /**
     * 菜单级别
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer sortNo;

    /**
     * 图标
     */
    private String icon;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 是否隐藏
     */
    private Boolean hidden;


    public Menu() {}
}
