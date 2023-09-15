package xyz.pplax.auth.manager.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import xyz.pplax.auth.constant.AuthRedisConstant;
import xyz.pplax.auth.model.SecurityUserDetails;
import xyz.pplax.core.util.ConvertObjectUtils;
import xyz.pplax.core.util.DateUtils;
import xyz.pplax.core.util.JSONUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 加载UserDetails的一个缓存
 */
@Slf4j
@Component
public class PPLAXUserDetailsCache implements UserCache {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 从缓存中获得用户详情的对象
     * @param username
     * @return
     */
    @Override
    public UserDetails getUserFromCache(String username) {
        long time = new Date().getTime();
        // TokenEndpoint
        // 先获得json串
        String json = (String) redisTemplate.opsForValue().get(AuthRedisConstant.USER_DETAILS_CACHE_PREFIX + username);
        // 反序列化
        SecurityUserDetails securityUserDetails = JSON.parseObject(json, SecurityUserDetails.class);
        ArrayList<JSONObject> grantedAuthorities = JSONUtils.parseObjFromResult(json, "grantedAuthorities", ArrayList.class);

        String[] authorities = null;
        if (grantedAuthorities != null) {
            authorities = grantedAuthorities.stream()
                .map(obj -> (String) obj.get("authority"))
                .collect(Collectors.joining(","))
                .split(",");
        }
        String[] finalAuthorities = authorities;
        Optional.ofNullable(securityUserDetails)
                .ifPresent(t -> t.setGrantedAuthorities(AuthorityUtils.createAuthorityList(finalAuthorities)));
        long end = new Date().getTime();
        log.info("登录从缓存中获取用户，耗时{}", (end - time));
        return securityUserDetails;
    }

    /**
     * 将用户详情放到缓存中
     * @param user
     */
    @Override
    public void putUserInCache(UserDetails user) {
        // 设置一个随机失效时间 在60分钟到5天内的随机一个数
        Duration duration = Duration.ofSeconds(DateUtils.getRandomMinute(60, 60 * 24 * 5) * 60);
        String json = ConvertObjectUtils.jsonToString(user);
        redisTemplate.opsForValue().set(AuthRedisConstant.USER_DETAILS_CACHE_PREFIX + user.getUsername(), json, duration);
    }

    /**
     * 将用户详情从缓存中移除
     * @param username
     */
    @Override
    public void removeUserFromCache(String username) {
        redisTemplate.delete(AuthRedisConstant.USER_DETAILS_CACHE_PREFIX + username);
    }
}


