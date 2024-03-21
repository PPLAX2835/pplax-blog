package xyz.pplax.pplaxblog.xo.service.role;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.MenuSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.RoleSQLConstants;
import xyz.pplax.pplaxblog.xo.entity.Menu;
import xyz.pplax.pplaxblog.xo.entity.Role;
import xyz.pplax.pplaxblog.xo.mapper.RoleMapper;
import xyz.pplax.pplaxblog.xo.service.menu.MenuService;
import xyz.pplax.pplaxblog.xo.service.user.UserServiceImpl;

import java.io.Serializable;
import java.util.*;

/**
 * 角色权限表 服务实现类
 */
@Service
public class RoleServiceImpl extends SuperServiceImpl<RoleMapper, Role> implements RoleService {

    private static final Logger log = LogManager.getLogger(RoleServiceImpl.class);

    @Autowired
    private MenuService menuService;

    @Override
    public Role setMenu(Role role) {
        if (role == null) {
            log.warn("参数为空");
            return null;
        }

        // 获得菜单
        if (StringUtils.isEmpty(role.getMenuUids())) {
            return role;
        }

        String[] menuUids = role.getMenuUids().split(CharacterConstants.SYMBOL_COMMA);

        // 查询
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.in(MenuSQLConstants.C_UID, menuUids);
        menuQueryWrapper.orderByAsc(MenuSQLConstants.SORT_NO);
        List<Menu> menuList = menuService.list(menuQueryWrapper);

        role.setMenuList(menuService.organizeMenus(menuList));

        return role;
    }

    @Override
    public List<Role> list() {

        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.ne(RoleSQLConstants.STATUS, EStatus.DISABLED);

        List<Role> roleList = super.list(roleQueryWrapper);
        List<Role> result = new ArrayList<>();

        for (Role role : roleList) {
            result.add(setMenu(role));
        }

        return result;
    }
}
