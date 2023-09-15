package xyz.pplax.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.message.dao.PPLAXEmailDao;
import xyz.pplax.message.po.Email;
import xyz.pplax.service.base.BaseService;

/**
 * email 数据表Service层
 */
@Service
public class PPLAXEmailService extends BaseService<Email> {
	@SuppressWarnings("unused")
	private PPLAXEmailDao emailDao;
	
	@Autowired
    public void setInfoDao(PPLAXEmailDao emailDao) {
        super.setBaseDao(emailDao);
        this.emailDao = emailDao;
    }
}