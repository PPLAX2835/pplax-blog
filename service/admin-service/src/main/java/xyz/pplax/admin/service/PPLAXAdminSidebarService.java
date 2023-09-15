package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.admin.dao.PPLAXAdminSidebarDao;
import xyz.pplax.admin.po.AdminSidebar;
import xyz.pplax.service.base.BaseService;

/**
 * admin_sidebar 数据表Service层
 */
@Service
public class PPLAXAdminSidebarService extends BaseService<AdminSidebar> {
	@SuppressWarnings("unused")
	private PPLAXAdminSidebarDao adminSidebarDao;
	
	@Autowired
    public void setInfoDao(PPLAXAdminSidebarDao adminSidebarDao) {
        super.setBaseDao(adminSidebarDao);
        this.adminSidebarDao = adminSidebarDao;
    }
}