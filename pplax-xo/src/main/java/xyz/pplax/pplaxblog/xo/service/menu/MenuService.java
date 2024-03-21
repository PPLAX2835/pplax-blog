package xyz.pplax.pplaxblog.xo.service.menu;

import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.MenuEditDto;
import xyz.pplax.pplaxblog.xo.dto.edit.TagEditDto;
import xyz.pplax.pplaxblog.xo.entity.Menu;

import java.util.List;

/**
 * 菜单 服务类
 */
public interface MenuService extends SuperService<Menu> {

    List<Menu> tree();

    Boolean save(MenuEditDto menuEditDto);

    Boolean updateById(MenuEditDto menuEditDto);

    ResponseResult removeById(String menuUid);

    List<Menu> organizeMenus(List<Menu> menuList);
}
