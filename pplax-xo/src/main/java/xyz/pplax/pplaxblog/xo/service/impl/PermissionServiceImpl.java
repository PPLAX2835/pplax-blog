package xyz.pplax.pplaxblog.xo.service.impl;

import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.Link;
import xyz.pplax.pplaxblog.xo.entity.Permission;
import xyz.pplax.pplaxblog.xo.mapper.LinkMapper;
import xyz.pplax.pplaxblog.xo.mapper.PermissionMapper;
import xyz.pplax.pplaxblog.xo.service.LinkService;
import xyz.pplax.pplaxblog.xo.service.PermissionService;

/**
 * 权限表 服务实现类
 */
@Service
public class PermissionServiceImpl extends SuperServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
