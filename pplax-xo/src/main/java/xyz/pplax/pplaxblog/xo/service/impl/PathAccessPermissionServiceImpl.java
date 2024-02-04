package xyz.pplax.pplaxblog.xo.service.impl;

import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.PathAccessPermission;
import xyz.pplax.pplaxblog.xo.mapper.PathAccessPermissionMapper;
import xyz.pplax.pplaxblog.xo.service.PathAccessPermissionService;

/**
 * 路径访问权限表 服务实现类
 */
@Service
public class PathAccessPermissionServiceImpl extends SuperServiceImpl<PathAccessPermissionMapper, PathAccessPermission> implements PathAccessPermissionService {

}
