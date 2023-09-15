package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.admin.dao.PPLAXRolePermissionDao;
import xyz.pplax.admin.po.RolePermissionRelationship;
import xyz.pplax.service.base.BaseService;

/**
 * role_permission 表Service层
 */
@Service
public class PPLAXRolePermissionService extends BaseService<RolePermissionRelationship> {
	@SuppressWarnings("unused")
	private PPLAXRolePermissionDao rolePermissionDao;
	
	@Autowired
    public void setInfoDao(PPLAXRolePermissionDao rolePermissionDao) {
        super.setBaseDao(rolePermissionDao);
        this.rolePermissionDao = rolePermissionDao;
    }
}