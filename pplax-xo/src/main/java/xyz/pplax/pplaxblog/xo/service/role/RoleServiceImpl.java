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
    public Role getById(Serializable id) {

        Role role = super.getById(id);

        // 检查role是否正常
        if (role == null || role.getStatus() != EStatus.ENABLE.getStatus()) {
            return null;
        }

        // 获得菜单
        List<Menu> menuList = new ArrayList<>();
        Map<String, Menu> menuMap = new HashMap<>();
        String[] menuUids = role.getMenuUids().split(CharacterConstants.SYMBOL_COMMA);
        // 封装到map中
        for (String menuUid : menuUids) {
            Menu menu = menuService.getById(menuUids);
            menuMap.put(menu.getUid(), menu);
        }
        // 将父子关系整理起来
        for (String menuUid : menuMap.keySet()) {
            Menu menu = menuMap.get(menuUid);
            if (!StringUtils.isEmpty(menu.getParentUid()) && menuMap.get(menu.getParentUid()) != null) {
                // 如果本次遍历的菜单有父级菜单的uid并且map里能查到，就将这个放到父级菜单里
                menuMap.get(menu.getParentUid()).getChildMenuList().add(menu);
                // 移除这个
                menuMap.remove(menuUid);
            }
        }
        for (String menuUid : menuMap.keySet()) {
            menuList.add(menuMap.get(menuUid));
        }

        role.setMenuList(menuList);
        return role;
    }
}
