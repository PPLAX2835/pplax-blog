package xyz.pplax.pplaxblog.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.exception.curd.DeleteException;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.constants.sql.MenuSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.edit.MenuEditDto;
import xyz.pplax.pplaxblog.xo.entity.Menu;
import xyz.pplax.pplaxblog.xo.mapper.MenuMapper;
import xyz.pplax.pplaxblog.xo.service.MenuService;

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
        PQueryWrapper<Menu> menuPQueryWrapper = new PQueryWrapper<>();
        menuPQueryWrapper.orderByAsc(MenuSQLConstants.SORT_NO);
        return organizeMenus(list(menuPQueryWrapper));
    }


    /**
     * 添加菜单
     * @param menuEditDto
     * @return
     */
    @Override
    public Boolean save(MenuEditDto menuEditDto) {
        Menu menu = new Menu();
        menu.setParentUid(menuEditDto.getParentUid());
        menu.setType(menuEditDto.getType());
        menu.setRoute(menuEditDto.getRoute());
        menu.setEndpoint(menuEditDto.getEndpoint());
        menu.setTitle(menuEditDto.getTitle());
        menu.setLevel(menuEditDto.getLevel());
        menu.setSortNo(menuEditDto.getSortNo());
        menu.setIcon(menuEditDto.getIcon());
        menu.setRemarks(menuEditDto.getRemarks());
        menu.setHidden(menuEditDto.getHidden());

        return save(menu);
    }

    /**
     * 更新菜单
     * @param menuEditDto
     * @return
     */
    @Override
    public Boolean updateById(MenuEditDto menuEditDto) {
        Menu menu = new Menu();
        menu.setUid(menuEditDto.getUid());
        menu.setParentUid(menuEditDto.getParentUid());
        menu.setType(menuEditDto.getType());
        menu.setRoute(menuEditDto.getRoute());
        menu.setEndpoint(menuEditDto.getEndpoint());
        menu.setTitle(menuEditDto.getTitle());
        menu.setLevel(menuEditDto.getLevel());
        menu.setSortNo(menuEditDto.getSortNo());
        menu.setIcon(menuEditDto.getIcon());
        menu.setRemarks(menuEditDto.getRemarks());
        menu.setHidden(menuEditDto.getHidden());

        return updateById(menu);
    }

    /**
     * 通过id删除菜单
     * @param menuUid
     * @return
     */
    @Override
    public Boolean removeById(String menuUid) {
        // 检查是否还有子菜单
        PQueryWrapper<Menu> menuPQueryWrapper = new PQueryWrapper<>();
        menuPQueryWrapper.eq(MenuSQLConstants.PARENT_UID, menuUid);
        int count = count(menuPQueryWrapper);
        if (count > 0) {
            throw new DeleteException(HttpStatus.CHILDREN_UNDER_THIS_MENU);
        }

        boolean res = super.removeById(menuUid);
        if (!res) {
            throw new RuntimeException();
        }

        return true;
    }

    /**
     *  递归整理列表，使其呈现父子关系
     * @param menuList
     * @return
     */
    @Override
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
