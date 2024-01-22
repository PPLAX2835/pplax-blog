package xyz.pplax.pplaxblog.xo.service.impl;

import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.Role;
import xyz.pplax.pplaxblog.xo.mapper.RoleMapper;
import xyz.pplax.pplaxblog.xo.service.RoleService;

/**
 * 角色权限表 服务实现类
 */
@Service
public class RoleServiceImpl extends SuperServiceImpl<RoleMapper, Role> implements RoleService {

}
