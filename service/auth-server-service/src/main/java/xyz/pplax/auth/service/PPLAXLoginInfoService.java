package xyz.pplax.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.auth.dao.PPLAXLoginInfoDao;
import xyz.pplax.auth.po.LoginInfo;
import xyz.pplax.service.base.BaseService;

/**
 * login_info表Service层
 */
@Service
public class PPLAXLoginInfoService extends BaseService<LoginInfo> {
	@SuppressWarnings("unused")
	private PPLAXLoginInfoDao loginInfoDao;
	
	@Autowired
    public void setInfoDao(PPLAXLoginInfoDao loginInfoDao) {
        super.setBaseDao(loginInfoDao);
        this.loginInfoDao = loginInfoDao;
    }
}