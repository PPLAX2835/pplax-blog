package xyz.pplax.pplaxblog.xo.base.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import xyz.pplax.pplaxblog.commons.constants.BaseSysConstants;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.starter.redis.utils.RedisKeyUtils;
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

public class SuperServiceImpl<M extends SuperMapper<T>, T> extends ServiceImpl<M, T>  implements SuperService<T> {

    private static final Logger log = LogManager.getLogger(SuperServiceImpl.class);

    @Autowired
    private RedisService redisService;

    // 默认300秒过期
    @Value(("${spring.redis.expire:10}"))
    private Long expire;

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
            redisService.setCacheObject(redisKey, t, getRandomExpire(), TimeUnit.SECONDS);

        }
        log.info("查询完毕");

        return t;
    }

    @Override
    public boolean updateById(T entity) {
        String redisKeyName = null;
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<T> entityClass = (Class<T>) superClass.getActualTypeArguments()[1];

        Field field = null;
        Class<?> currentClass = entity.getClass();

        // 获取uid字段
        while (currentClass != null) {
            try {
                field = currentClass.getDeclaredField(BaseSysConstants.UID);
                break;
            } catch  (NoSuchFieldException e) {
                // 当前类没找到，从父类中找
                currentClass = currentClass.getSuperclass();
            }
        }

        if (field != null) {
            field.setAccessible(true); // 设置可以访问私有变量
            try {
                redisKeyName = RedisKeyUtils.getRedisKey(entityClass.getSimpleName()) + field.get(entity);
            } catch (IllegalAccessException e) {
                log.error(e.getMessage());
            }
            log.info("获得实体uid：" + field.getName());
        }

        boolean result = super.updateById(entity);
        if (result) {
            log.info("更新成功，更新缓存");
            redisService.setCacheObject(redisKeyName, entity, getRandomExpire(), TimeUnit.SECONDS);
        }

        return result;
    }

    /**
     * 获得一个随机expire时间
     * @return
     */
    private Long getRandomExpire() {
         return Math.round(Math.random() * 2 * expire);
    }
}
