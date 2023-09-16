package xyz.pplax.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.pplax.auth.constant.AuthRedisConstant;
import xyz.pplax.auth.constant.OauthJwtConstant;
import xyz.pplax.core.dto.JwtEntityDTO;
import xyz.pplax.core.dto.JwtUserInfo;
import xyz.pplax.core.entity.R;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.token.TokenException;
import xyz.pplax.core.exception.user.UserException;
import xyz.pplax.core.util.jwt.JwtUtils;
import xyz.pplax.starter.properties.PPLAXProperties;
import xyz.pplax.starter.util.PPLAXRequestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

/**
 * 退出登录Controller
 */
@RequestMapping("/auth/logout")
@RestController
public class LoginController {

    @Autowired
    private PPLAXProperties.PPLAXAuthProperties pplaxAuthProperties;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/out")
    public R logout(HttpServletRequest request, HttpServletResponse response) {
        // 将当前用户的token放入redis中，也就是黑名单，持有这个jwt的用户，都不能登录
        storageJwtBlacklist(request);
        cancelCookie(request, response);
        return R.success(ResponseStatusCodeEnum.SUCCESS.getCode(), "注销成功", true);
    }

    private void storageJwtBlacklist(HttpServletRequest request) {
        JwtUserInfo jwtUserInfo = null;
        try {
            jwtUserInfo = PPLAXRequestUtils.getJwtUserInfo(request);
        } catch (Exception e) {
            // 可能用户没有登录，直接退出
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN);
        }

        // 获取jwt
        String jwtToken = jwtUserInfo.getJwtToken();

        // 解析jwt
        JwtEntityDTO jwtEntityDTO = JwtUtils.parseJwtToken(jwtToken, pplaxAuthProperties.getSecretKey());

        // 判断是否已经登录过
        if (redisExistJti(jwtEntityDTO)) {
            throw new TokenException(ResponseStatusCodeEnum.PERMISSION_USER_HAD_LOGOUT);
        }

        // 获取jwt的到期时间
        long jwtExpiryTime = (jwtEntityDTO.getExpirationAt().getTime() - System.currentTimeMillis()) / 1000;

        // jwt如redis黑名单中 失效时间就是此token的失效时间
        Duration duration = Duration.ofSeconds(jwtExpiryTime);
        redisTemplate.opsForValue().set(AuthRedisConstant.STORAGE_JWT_BLACKLIST_PREFIX + jwtEntityDTO.getJti(), jwtToken, duration);
    }

    /**
     * 取消cookie的设置
     * @param request
     * @param response
     */
    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(OauthJwtConstant.COOKIE_STORAGE_LOGIN_SUCCESS_STATUS, null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));
        if (pplaxAuthProperties.getCookieDomain() != null) {
            cookie.setDomain(pplaxAuthProperties.getCookieDomain());
        }
        cookie.setSecure((pplaxAuthProperties.getUseSecureCookie() != null) ?
                pplaxAuthProperties.getUseSecureCookie() : request.isSecure());
        response.addCookie(cookie);
    }

    /**
     * 获取path
     * @param request
     * @return
     */
    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return (contextPath.length() > 0) ? contextPath : "/";
    }

    /**
     * 判断该jwt的jti是否存在于redis中，如果存在，返回true，否则返回false
     * @param jwtEntity 对象
     * @return true表示存在
     */
    private boolean redisExistJti(JwtEntityDTO jwtEntity) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(AuthRedisConstant.STORAGE_JWT_BLACKLIST_PREFIX + jwtEntity.getJti()));
    }
}
