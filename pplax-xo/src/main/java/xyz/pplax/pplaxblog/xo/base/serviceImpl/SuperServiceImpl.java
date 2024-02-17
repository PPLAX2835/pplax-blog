package xyz.pplax.pplaxblog.xo.base.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.starter.redis.constants.BaseRedisConstants;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.starter.redis.utils.RedisKeyUtils;
import xyz.pplax.pplaxblog.xo.base.mapper.SuperMapper;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.constants.redis.RoleRedisConstants;
import xyz.pplax.pplaxblog.xo.entity.Role;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;


/**
 * SuperService 实现类（ 泛型：M 是  mapper(dao) 对象，T 是实体 ）
 * @param <T>
 */

public class SuperServiceImpl<M extends SuperMapper<T>, T> extends ServiceImpl<M, T>  implements SuperService<T> {

    @Autowired
    private RedisService redisService;

    @Override
    @SuppressWarnings("unchecked")
    public T getById(Serializable id) {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<T> entityClass = (Class<T>) superClass.getActualTypeArguments()[1];
        String redisKey = RedisKeyUtils.getRedisKey(entityClass.getSimpleName()) + id;

        // 先从缓存中找
        T t = JSONObject.toJavaObject(redisService.getCacheObject(redisKey), entityClass);

        if (t == null) {
            // 缓存中没有，从数据库里查
            t = super.getById(id);

            // 检查t是否为空
            if (t == null) {
                return null;
            }

            // 存到缓存中
            redisService.setCacheObject(redisKey, t);

        }

        return t;
    }
}
