package xyz.pplax.auth.util;

import cn.hutool.core.codec.Base64;
import xyz.pplax.auth.constant.OauthJwtConstant;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求相关工具类
 */
public class RequestUtils {

    /**
     * 对失效时间和用户名进行编码
     * @param cookieExpiryTime
     * @param username
     * @return
     */
    public static String encodeCookie(long cookieExpiryTime, String username) {
        String data = username + ":" + cookieExpiryTime + ":" + OauthJwtConstant.STORAGE_COOKIE_SECRET_KEY;
        return Base64.encode(data);
    }

    /**
     * 计算cookie失效时间
     * @param maxAge
     * @return
     */
    public static long calculateExpiryTime(int maxAge) {
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis + (maxAge * 1000L);
    }

    /**
     * 获取path
     * @param request
     * @return
     */
    public static String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return (contextPath.length() > 0) ? contextPath : "/";
    }

}
