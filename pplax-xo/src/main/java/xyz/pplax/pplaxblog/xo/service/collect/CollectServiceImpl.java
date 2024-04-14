package xyz.pplax.pplaxblog.xo.service.collect;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.CollectSQLConstants;
import xyz.pplax.pplaxblog.xo.entity.Collect;
import xyz.pplax.pplaxblog.xo.mapper.CollectMapper;

/**
 * 收藏表 服务实现类
 */
@Service
public class CollectServiceImpl extends SuperServiceImpl<CollectMapper, Collect> implements CollectService {

    @Override
    public Boolean save(String blogUid, String userUid) {
        QueryWrapper<Collect> collectQueryWrapper = new QueryWrapper<>();
        collectQueryWrapper.ne(CollectSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        collectQueryWrapper.eq(CollectSQLConstants.USER_UID, userUid);
        collectQueryWrapper.eq(CollectSQLConstants.BLOG_UID, blogUid);

        Collect record = getOne(collectQueryWrapper);
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
