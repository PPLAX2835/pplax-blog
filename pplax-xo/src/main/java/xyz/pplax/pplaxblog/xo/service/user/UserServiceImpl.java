package xyz.pplax.pplaxblog.xo.service.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.starter.redis.constants.BaseRedisConstants;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.xo.entity.Role;
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
