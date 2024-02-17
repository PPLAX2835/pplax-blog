package xyz.pplax.pplaxblog.xo.service.user;

import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.mapper.UserMapper;

/**
 * 用户表 服务实现类
 */
@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements UserService {

//    @Autowired
//    private RedisService redisService;
//
//    public User getOne(QueryWrapper<User> queryWrapper) {
//
//        // 先从缓存中找
//        Role role = JSONObject.toJavaObject(redisService.getCacheObject(BaseRedisConstants.ROLE + BaseRedisConstants.SEGMENTATION + id), Role.class);
//
//    }

}
