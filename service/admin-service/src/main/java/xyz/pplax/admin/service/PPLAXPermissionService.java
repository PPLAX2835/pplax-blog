package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.admin.dao.PPLAXPermissionDao;
import xyz.pplax.admin.po.Permission;
import xyz.pplax.service.base.BaseService;

/**
 * permission 表Service层
 */
@Service
public class PPLAXPermissionService extends BaseService<Permission> {
	@SuppressWarnings("unused")
	private PPLAXPermissionDao permissionDao;
	
	@Autowired
    public void setInfoDao(PPLAXPermissionDao permissionDao) {
        super.setBaseDao(permissionDao);
        this.permissionDao = permissionDao;
    }
}