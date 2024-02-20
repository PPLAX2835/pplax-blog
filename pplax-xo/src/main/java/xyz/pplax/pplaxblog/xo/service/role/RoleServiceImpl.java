package xyz.pplax.pplaxblog.xo.service.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.Menu;
import xyz.pplax.pplaxblog.xo.entity.Role;
import xyz.pplax.pplaxblog.xo.mapper.RoleMapper;
import xyz.pplax.pplaxblog.xo.service.menu.MenuService;

import java.io.Serializable;
import java.util.*;

/**
 * 角色权限表 服务实现类
 */
@Service
public class RoleServiceImpl extends SuperServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private MenuService menuService;

    @Override
    public Role setMenu(Role role) {
        // 获得菜单
        List<Menu> menuList = new ArrayList<>();

        if (StringUtils.isEmpty(role.getMenuUids())) {
            return role;
        }

        String[] menuUids = role.getMenuUids().split(CharacterConstants.SYMBOL_COMMA);

        // 封装到list中
        for (String menuUid : menuUids) {
            Menu menu = menuService.getById(menuUid);
            menuList.add(menu);
        }

        role.setMenuList(organizeMenus(menuList));

        return role;
    }

    @Override
    public List<Role> list() {
        List<Role> roleList = super.list();
        List<Role> result = new ArrayList<>();

        for (Role role : roleList) {
            result.add(setMenu(role));
        }

        return result;
    }









    /**
     *  递归整理列表，使其呈现父子关系
     * @param menuList
     * @return
     */
    public static List<Menu> organizeMenus(List<Menu> menuList) {
        List<Menu> organizedList = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getLevel() == 1) {
                organizedList.add(menu);
                organizeChildMenus(menu, menuList);
            }
        }
        return organizedList;
    }
    private static void organizeChildMenus(Menu parentMenu, List<Menu> menuList) {
        List<Menu> childMenus = new ArrayList<>();
        for (Menu menu : menuList) {
            if (!StringUtils.isEmpty(menu.getParentUid()) && menu.getParentUid().equals(parentMenu.getUid())) {
                childMenus.add(menu);
                organizeChildMenus(menu, menuList);
            }
        }
        parentMenu.setChildMenuList(childMenus);
    }
}
