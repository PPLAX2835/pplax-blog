package xyz.pplax.pplaxblog.xo.serviceImpl;

import xyz.pplax.pplaxblog.xo.dao.VisitorDao;
import xyz.pplax.pplaxblog.xo.entity.Visitor;
import xyz.pplax.pplaxblog.xo.service.VisitorSO;
import xyz.pplax.pplaxblog.base.enums.EStatus;
import xyz.pplax.pplaxblog.base.global.SysConf;
import xyz.pplax.pplaxblog.base.serviceImpl.BaseSOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 游客Service实现
 */
@Service
public class VisitorSOImpl extends BaseSOImpl<Visitor> implements VisitorSO{
	
	@Autowired
	VisitorDao dao;
	
	/**
	 * 通过name获取专辑uid
	 */
	public String getSpecialUidByName(String specialname) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SysConf.status, EStatus.ENABLE);
		map.put(SysConf.specialname , specialname);
		Visitor special = dao.getOne(map);
		if(special != null) {
			return special.getUid();
		}
		return null;
	}
}
