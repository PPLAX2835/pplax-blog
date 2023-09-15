package xyz.pplax.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.message.dao.PPLAXEmailLogDao;
import xyz.pplax.message.po.EmailLog;
import xyz.pplax.service.base.BaseService;

/**
 * email_log 数据表Service层
 */
@Service
public class PPLAXEmailLogService extends BaseService<EmailLog> {
	@SuppressWarnings("unused")
	private PPLAXEmailLogDao emailLogDao;
	
	@Autowired
    public void setInfoDao(PPLAXEmailLogDao emailLogDao) {
        super.setBaseDao(emailLogDao);
        this.emailLogDao = emailLogDao;
    }
}