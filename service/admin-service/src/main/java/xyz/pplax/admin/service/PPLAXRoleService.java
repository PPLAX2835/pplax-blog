package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.admin.dao.PPLAXRoleDao;
import xyz.pplax.admin.po.Role;
import xyz.pplax.service.base.BaseService;

/**
 * role 表Service层
 */
@Service
public class PPLAXRoleService extends BaseService<Role> {
	@SuppressWarnings("unused")
	private PPLAXRoleDao roleDao;
	
	@Autowired
    public void setInfoDao(PPLAXRoleDao roleDao) {
        super.setBaseDao(roleDao);
        this.roleDao = roleDao;
    }
}