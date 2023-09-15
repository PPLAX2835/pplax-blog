package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.admin.dao.PPLAXRouterPermissionDao;
import xyz.pplax.admin.po.RouterPermission;
import xyz.pplax.service.base.BaseService;

/**
 * router_permission 数据表Service层
 */
@Service
public class PPLAXRouterPermissionService extends BaseService<RouterPermission> {
	@SuppressWarnings("unused")
	private PPLAXRouterPermissionDao pplaxRouterPermissionDao;

	@Autowired
    public void setInfoDao(PPLAXRouterPermissionDao pplaxRouterPermissionDao) {
        super.setBaseDao(pplaxRouterPermissionDao);
        this.pplaxRouterPermissionDao = pplaxRouterPermissionDao;
    }
}