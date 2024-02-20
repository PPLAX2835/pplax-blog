package xyz.pplax.pplaxblog.xo.base.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import xyz.pplax.pplaxblog.commons.constants.BaseSysConstants;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.starter.redis.utils.RedisUtils;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;
import xyz.pplax.pplaxblog.xo.base.mapper.SuperMapper;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;


/**
 * SuperService 实现类（ 泛型：M 是  mapper(dao) 对象，T 是实体 ）
 * @param <T>
 */

public class SuperServiceImpl<M extends SuperMapper<T>, T extends SuperEntity> extends ServiceImpl<M, T>  implements SuperService<T> {

    private static final Logger log = LogManager.getLogger(SuperServiceImpl.class);

    @Autowired
    private RedisService redisService;

    // 默认300秒过期
    @Value(("${spring.redis.expire:300}"))
    private Long expire;

    @Override
    @SuppressWarnings("unchecked")
    public T getById(Serializable id) {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<T> entityClass = (Class<T>) superClass.getActualTypeArguments()[1];
        String redisKey = RedisUtils.getRedisKey(entityClass.getSimpleName()) + id;

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
            redisService.setCacheObject(redisKey, t, RedisUtils.getRandomExpire(expire), TimeUnit.SECONDS);

        }
        log.info("查询完毕");

        return t;
    }

    @Override
    public boolean updateById(T entity) {
        String redisKeyName = null;
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<T> entityClass = (Class<T>) superClass.getActualTypeArguments()[1];


        // 获取uid字段和key
        redisKeyName = RedisUtils.getRedisKey(entityClass.getSimpleName()) + entity.getUid();
        log.info("redis key:" + redisKeyName);

        boolean result = super.updateById(entity);
        if (result) {
            log.info("更新成功，更新缓存");
            redisService.setCacheObject(redisKeyName, entity, RedisUtils.getRandomExpire(expire), TimeUnit.SECONDS);
        }

        return result;
    }

}
