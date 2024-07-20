package xyz.pplax.pplaxblog.xo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.RoleEditDto;
import xyz.pplax.pplaxblog.xo.entity.Role;

import java.util.List;

/**
 * 角色 服务类
 */
public interface RoleService extends SuperService<Role> {

    Page<Role> page(String keyword, Long currentPage, Long pageSize);

    Boolean save(RoleEditDto roleEditDto);

    Boolean updateById(RoleEditDto roleEditDto);

    Boolean removeById(String roleUid);

    Boolean removeByIds(List<String> roleUidList);

    void preheat();

    Role setMenu(Role role);

}
