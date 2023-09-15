package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.admin.dao.AdminRouterDao;
import xyz.pplax.admin.po.AdminRouter;
import xyz.pplax.service.base.BaseService;

/**
 * admin_router 表Service层
 */
@Service
public class PPLAXAdminRouterService extends BaseService<AdminRouter> {
	@SuppressWarnings("unused")
	private AdminRouterDao adminRouterDao;
	
	@Autowired
    public void setInfoDao(AdminRouterDao adminRouterDao) {
        super.setBaseDao(adminRouterDao);
        this.adminRouterDao = adminRouterDao;
    }
}