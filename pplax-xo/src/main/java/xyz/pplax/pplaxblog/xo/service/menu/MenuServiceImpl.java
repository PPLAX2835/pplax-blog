package xyz.pplax.pplaxblog.xo.service.menu;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.MenuSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.edit.MenuEditDto;
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
        menuQueryWrapper.ne(MenuSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        menuQueryWrapper.orderByAsc(MenuSQLConstants.SORT_NO);
        return organizeMenus(list(menuQueryWrapper));
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
    public ResponseResult removeById(String menuUid) {
        // 检查是否还有子菜单
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.eq(MenuSQLConstants.PARENT_UID, menuUid);
        menuQueryWrapper.ne(MenuSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        int count = count(menuQueryWrapper);
        if (count > 0) {
            return new ResponseResult(HttpStatus.CHILDREN_UNDER_THIS_MENU);
        }

        boolean res = super.removeById(menuUid);
        if (!res) {
            throw new RuntimeException();
        }

        return ResponseResult.success(HttpStatus.DELETE_SUCCESS);
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
