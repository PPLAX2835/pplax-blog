package xyz.pplax.pplaxblog.xo.service.menu;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.MenuSQLConstants;
import xyz.pplax.pplaxblog.xo.entity.Menu;
import xyz.pplax.pplaxblog.xo.mapper.MenuMapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单 服务实现类
 */
@Service
public class MenuServiceImpl extends SuperServiceImpl<MenuMapper, Menu> implements MenuService {
    /**
     * 获得菜单树
     * @return
     */
    @Override
    public List<Menu> tree() {
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.orderByAsc(MenuSQLConstants.SORT_NO);
        return organizeMenus(list(menuQueryWrapper));
    }


    /**
     *  递归整理列表，使其呈现父子关系
     * @param menuList
     * @return
     */
    public List<Menu> organizeMenus(List<Menu> menuList) {
        List<Menu> organizedList = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getLevel() == 1) {
                organizedList.add(menu);
                organizeChildMenus(menu, menuList);
            }
        }
        return organizedList;
    }
    private void organizeChildMenus(Menu parentMenu, List<Menu> menuList) {
        List<Menu> childMenus = new ArrayList<>();
        for (Menu menu : menuList) {
            if (!StringUtils.isEmpty(menu.getParentUid()) && menu.getParentUid().equals(parentMenu.getUid())) {
                childMenus.add(menu);
                organizeChildMenus(menu, menuList);
            }
        }
        parentMenu.setChildren(childMenus);
    }
}
