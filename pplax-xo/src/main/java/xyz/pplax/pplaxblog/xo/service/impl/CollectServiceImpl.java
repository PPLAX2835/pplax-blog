package xyz.pplax.pplaxblog.xo.service.impl;

import xyz.pplax.pplaxblog.xo.entity.Collect;
import xyz.pplax.pplaxblog.xo.mapper.CollectMapper;
import xyz.pplax.pplaxblog.xo.service.CollectService;
import xyz.pplax.pplaxblog.commons.base.serviceImpl.SuperServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 收藏表 服务实现类
 */
@Service
public class CollectServiceImpl extends SuperServiceImpl<CollectMapper, Collect> implements CollectService {

}
