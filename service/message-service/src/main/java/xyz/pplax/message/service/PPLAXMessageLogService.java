package xyz.pplax.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.message.dao.PPLAXMessageLogDao;
import xyz.pplax.message.po.MessageLog;
import xyz.pplax.service.base.BaseService;

/**
 * message_log 数据表Service层
 */
@Service
public class PPLAXMessageLogService extends BaseService<MessageLog> {
	@SuppressWarnings("unused")
	private PPLAXMessageLogDao messageLogDao;
	
	@Autowired
    public void setInfoDao(PPLAXMessageLogDao messageLogDao) {
        super.setBaseDao(messageLogDao);
        this.messageLogDao = messageLogDao;
    }
}