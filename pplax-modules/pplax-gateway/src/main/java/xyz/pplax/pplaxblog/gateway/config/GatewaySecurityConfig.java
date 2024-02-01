package xyz.pplax.pplaxblog.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class GatewaySecurityConfig {

    /**
     * oauth配置
     * @param httpSecurity
     * @return
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity.csrf().disable();      // 允许oauth跨域
        return httpSecurity.build();
    }

    /**
     * 跨域请求过滤器
     */
    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                ServerHttpResponse response = ctx.getResponse();
                HttpHeaders headers = response.getHeaders();

                /**
                 * 跨域发送复杂请求的时候会先发送一个option请求来判断是否可以进行跨域
                 * 后端需要设置对应的响应头来告诉前端可以跨域
                 * 这里只能在option请求的时候设置请求头
                 * 当前端知道可以跨域的时候就会继续发送请求
                 * 如果这里不用if限制一下就会重复添加请求头
                 * 前端发现请求头重复了就有不允许跨域了
                 */
                if (request.getMethod() == HttpMethod.OPTIONS) {

                    headers.add("Access-Control-Allow-Origin", "*");
                    headers.add("Access-Control-Allow-Methods", "*");
                    headers.add("Access-Control-Max-Age", "18000L");
                    headers.add("Access-Control-Allow-Headers", "*");
                    headers.add("Access-Control-Expose-Headers", "*");
                    headers.add("Access-Control-Allow-Credentials", "true");

                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(ctx);
        };
    }

}
