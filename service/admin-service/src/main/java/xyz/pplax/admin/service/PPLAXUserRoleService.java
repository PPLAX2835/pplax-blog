package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.admin.dao.PPLAXUserRoleDao;
import xyz.pplax.admin.po.UserRoleRelationship;
import xyz.pplax.service.base.BaseService;

/**
 * user_role 表Service层
 */
@Service
public class PPLAXUserRoleService extends BaseService<UserRoleRelationship> {
	@SuppressWarnings("unused")
	private PPLAXUserRoleDao userRoleDao;
	
	@Autowired
    public void setInfoDao(PPLAXUserRoleDao userRoleDao) {
        super.setBaseDao(userRoleDao);
        this.userRoleDao = userRoleDao;
    }
}