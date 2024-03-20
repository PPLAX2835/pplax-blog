package xyz.pplax.pplaxblog.xo.service.menu;

import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.entity.Menu;

import java.util.List;

/**
 * 菜单 服务类
 */
public interface MenuService extends SuperService<Menu> {

    List<Menu> tree();

    List<Menu> organizeMenus(List<Menu> menuList);

}
