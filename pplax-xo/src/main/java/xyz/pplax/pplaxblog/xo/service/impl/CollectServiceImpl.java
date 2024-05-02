package xyz.pplax.pplaxblog.xo.service.impl;

import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.constants.sql.CollectSQLConstants;
import xyz.pplax.pplaxblog.xo.entity.Collect;
import xyz.pplax.pplaxblog.xo.mapper.CollectMapper;
import xyz.pplax.pplaxblog.xo.service.CollectService;

/**
 * 收藏表 服务实现类
 */
@Service
public class CollectServiceImpl extends SuperServiceImpl<CollectMapper, Collect> implements CollectService {

    @Override
    public Boolean save(String blogUid, String userUid) {
        PQueryWrapper<Collect> collectPQueryWrapper = new PQueryWrapper<>();
        collectPQueryWrapper.eq(CollectSQLConstants.USER_UID, userUid);
        collectPQueryWrapper.eq(CollectSQLConstants.BLOG_UID, blogUid);

        Collect record = getOne(collectPQueryWrapper);
        if (record != null) {
           return removeById(record.getUid());
        }

        Collect collect = new Collect();
        collect.setUid(StringUtils.getUUID());
        collect.setBlogUid(blogUid);
        collect.setUserUid(userUid);
        return save(collect);
    }
}
