package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.admin.dao.PPLAXUserDao;
import xyz.pplax.admin.po.User;
import xyz.pplax.service.base.BaseService;

/**
 * user 表Service层
 */
@Service
public class PPLAXUserService extends BaseService<User> {
	@SuppressWarnings("unused")
	private PPLAXUserDao userDao;
	
	@Autowired
    public void setInfoDao(PPLAXUserDao userDao) {
        super.setBaseDao(userDao);
        this.userDao = userDao;
    }
}