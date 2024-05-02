package xyz.pplax.pplaxblog.xo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.exception.DeleteFailException;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.constants.redis.RoleRedisConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.MenuSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.RoleSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.edit.RoleEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.RoleGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Menu;
import xyz.pplax.pplaxblog.xo.entity.Role;
import xyz.pplax.pplaxblog.xo.entity.Tag;
import xyz.pplax.pplaxblog.xo.mapper.RoleMapper;
import xyz.pplax.pplaxblog.xo.service.MenuService;
import xyz.pplax.pplaxblog.xo.service.RoleService;

import java.util.*;

/**
 * 角色权限表 服务实现类
 */
@Service
public class RoleServiceImpl extends SuperServiceImpl<RoleMapper, Role> implements RoleService {

    private static final Logger log = LogManager.getLogger(RoleServiceImpl.class);

    @Autowired
    private MenuService menuService;

    @Autowired
    private RedisService redisService;

    @Override
    public IPage<Role> list(RoleGetListDto roleGetListDto) {
        PQueryWrapper<Role> rolePQueryWrapper = new PQueryWrapper<>();
        if (!StringUtils.isEmpty(roleGetListDto.getKeyword())) {
            rolePQueryWrapper.like(RoleSQLConstants.ROLE_AME, "%" + roleGetListDto.getKeyword() + "%");
        }

        //分页
        Page<Role> page = new Page<>();
        page.setCurrent(roleGetListDto.getCurrentPage());
        page.setSize(roleGetListDto.getPageSize());

        IPage<Tag> pageList = null;

        List<Role> roleList = new ArrayList<>();
        Page<Role> rolePage = page(page, rolePQueryWrapper);
        for (Role role : rolePage.getRecords()) {
            roleList.add(setMenu(role));
        }
        rolePage.setRecords(roleList);

        return rolePage;
    }

    @Override
    public Boolean save(RoleEditDto roleEditDto) {
        Role role = new Role();
        role.setRoleName(roleEditDto.getRoleName());
        role.setMenuUids(roleEditDto.getMenuUids());
        role.setSummary(roleEditDto.getSummary());
        return save(role);
    }

    @Override
    public Boolean updateById(RoleEditDto roleEditDto) {
        Role role = new Role();
        role.setUid(roleEditDto.getUid());
        role.setRoleName(roleEditDto.getRoleName());
        role.setMenuUids(roleEditDto.getMenuUids());
        role.setSummary(roleEditDto.getSummary());
        return updateById(role);
    }

    @Override
    public ResponseResult removeById(String roleUid) {
        Role role = getById(roleUid);
        String[] menuUids = role.getMenuUids().split(",");

        PQueryWrapper<Menu> menuPQueryWrapper = new PQueryWrapper<>();
        menuPQueryWrapper.in(MenuSQLConstants.UID, menuUids);

        int count = menuService.count(menuPQueryWrapper);

        if (count > 0) {
            return new ResponseResult(HttpStatus.MENU_UNDER_THIS_ROLE);
        }

        boolean res = super.removeById(roleUid);
        if (!res) {
            throw new RuntimeException();
        }

        return ResponseResult.success(HttpStatus.DELETE_SUCCESS);
    }

    @Override
    @Transactional
    public ResponseResult removeByIds(List<String> roleUidList) {
        List<Role> roleList = listByIds(roleUidList);

        for (Role role : roleList) {
            // 批量删除出问题就回滚
            ResponseResult responseResult = removeById(role.getUid());
            if (!Objects.equals(responseResult.getCode(), HttpStatus.OK.getCode())) {
                throw new DeleteFailException(responseResult.getMessage());
            }
        }

        return ResponseResult.success(HttpStatus.DELETE_SUCCESS);
    }

    /**
     * 数据预热
     */
    @Override
    public void preheat() {
        log.info("角色信息数据预热");

        List<Role> roleList = list();

        for (Role role : roleList) {
            redisService.setCacheObject(RoleRedisConstants.ROLE + RoleRedisConstants.SEGMENTATION + role.getUid(), role);
        }

        log.info("角色信息数据预热完毕");
    }

    @Override
    public List<Role> list() {

        PQueryWrapper<Role> rolePQueryWrapper = new PQueryWrapper<>();

        List<Role> roleList = super.list(rolePQueryWrapper);
        List<Role> result = new ArrayList<>();

        for (Role role : roleList) {
            result.add(setMenu(role));
        }

        return result;
    }

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
        PQueryWrapper<Menu> menuPQueryWrapper = new PQueryWrapper<>();
        menuPQueryWrapper.in(MenuSQLConstants.C_UID, menuUids);
        menuPQueryWrapper.orderByAsc(MenuSQLConstants.SORT_NO);
        List<Menu> menuList = menuService.list(menuPQueryWrapper);

        role.setMenuList(menuService.organizeMenus(menuList));

        return role;
    }

}
