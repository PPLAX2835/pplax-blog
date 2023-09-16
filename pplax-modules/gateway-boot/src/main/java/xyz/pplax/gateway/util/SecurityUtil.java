package xyz.pplax.gateway.util;

import org.springframework.web.server.ServerWebExchange;

/**
 * security工具类
 */
public class SecurityUtil {

    public static String getRequestUri(ServerWebExchange exchange) {

        return exchange.getRequest().getURI().getPath();
    }
}
