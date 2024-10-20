package xyz.pplax.pplaxblog.xo.service;

import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.entity.Collect;

/**
 * 收藏表 服务类
 */
public interface CollectService extends SuperService<Collect> {

    Boolean save(String blogUid, String userUid);

}
