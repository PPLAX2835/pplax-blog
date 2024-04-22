package xyz.pplax.pplaxblog.gateway.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "pplax.gateway")
public class GatewayProperty {

    private String[] excludePath;                   // 访问路径白名单

    private List<WhitelistEntry> whitelist;         // 包含请求方法的白名单

    @Data
    public static class WhitelistEntry {
        private HttpMethod method;
        private String path;
    }

    public ServerWebExchangeMatcher pathMatchers() {
        return ServerWebExchangeMatchers.matchers(
                whitelist.stream()
                        .map(entry -> ServerWebExchangeMatchers.pathMatchers(entry.getMethod(), entry.getPath()))
                        .toArray(ServerWebExchangeMatcher[]::new)
        );
    }
}
