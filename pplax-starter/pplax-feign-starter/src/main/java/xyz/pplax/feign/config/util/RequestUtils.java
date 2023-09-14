package xyz.pplax.feign.config.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xyz.pplax.auth.constant.RequestConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@Component
public class RequestUtils {
    public Map<String,String> getRequestHeadsFromHolder() {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }

        HttpServletRequest request = requestAttributes.getRequest();
        Map<String,String> headMaps = new HashMap<>();

        Enumeration<String> requestHeaderNames = request.getHeaderNames();
        while (requestHeaderNames.hasMoreElements()) {
            String headName = requestHeaderNames.nextElement();
            String headerValue = request.getHeader(headName);

            // 将请求头名称和值放入map中
            headMaps.put(headName, headerValue);
        }

        return headMaps;
    }

    /**
     * 获取需要的请求头，也就是必须要的，比如白名单标识等
     * @return
     */
    public Map<String, String> getAllNeedHeaders() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }

        // 获取需要的请求头信息
        HttpServletRequest request = requestAttributes.getRequest();
        Map<String,String> headMaps = new HashMap<>();
        Enumeration<String> requestHeaderNames = request.getHeaderNames();
        while (requestHeaderNames.hasMoreElements()) {
            String headName = requestHeaderNames.nextElement();
            String headerValue = request.getHeader(headName);

            // 获取存储用户认证信息
            if (RequestConstant.REQUEST_TOKEN_NAME.equals(headName)) {
                headMaps.put(headName, headerValue);
            }
            // 获取请求头中的白名单状态
            if (RequestConstant.REQUEST_WHITE_URL_STATUS.equals(headName)) {
                headMaps.put(headName, headerValue);
            }
        }
        return headMaps;
    }
}
