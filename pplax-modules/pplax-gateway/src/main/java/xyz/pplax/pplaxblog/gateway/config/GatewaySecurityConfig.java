package xyz.pplax.pplaxblog.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import xyz.pplax.pplaxblog.gateway.properties.GatewayProperty;

@Configuration
@EnableWebFluxSecurity
public class GatewaySecurityConfig {

    @Autowired
    private ReactiveAuthorizationManager<AuthorizationContext> pplaxReactiveAuthorizationManager;

    @Autowired
    private ReactiveAuthenticationManager jwtAuthenticationManager;

    @Autowired
    private GatewayProperty gatewayProperty;

    /**
     * oauth配置
     * @param httpSecurity
     * @return
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity httpSecurity) {
        // 认证过滤器，放入认证管理器tokenAuthenticationManager
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(jwtAuthenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(new ServerBearerTokenAuthenticationConverter());

        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .authorizeExchange()
                // 白名单直接放行
                .pathMatchers(gatewayProperty.getExclude()).permitAll()
                // 其他的请求必须鉴权，使用鉴权管理器
                .anyExchange().access(pplaxReactiveAuthorizationManager)
                // 鉴权的异常处理，权限不足，token失效
                .and().exceptionHandling()
//                // 未登录访问时处理
//                .authenticationEntryPoint()
//                // 访问被拒绝时处理，没有权限访问
//                .accessDeniedHandler()
                .and()
                // 跨域过滤器
                .addFilterAt(corsFilter(), SecurityWebFiltersOrder.CORS)
                // token的认证过滤器，用于校验token和认证
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);
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
