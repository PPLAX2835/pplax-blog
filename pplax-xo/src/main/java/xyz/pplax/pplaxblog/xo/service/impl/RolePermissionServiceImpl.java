package xyz.pplax.pplaxblog.xo.service.impl;

import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.Role;
import xyz.pplax.pplaxblog.xo.entity.RolePermission;
import xyz.pplax.pplaxblog.xo.mapper.RoleMapper;
import xyz.pplax.pplaxblog.xo.mapper.RolePermissionMapper;
import xyz.pplax.pplaxblog.xo.service.RolePermissionService;
import xyz.pplax.pplaxblog.xo.service.RoleService;

/**
 * 图片类型表 服务实现类
 */
@Service
public class RolePermissionServiceImpl extends SuperServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

}
