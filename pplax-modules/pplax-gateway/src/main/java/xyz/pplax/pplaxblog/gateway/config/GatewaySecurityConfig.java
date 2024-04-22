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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import xyz.pplax.pplaxblog.gateway.handler.PPLAXAccessDeniedHandler;
import xyz.pplax.pplaxblog.gateway.handler.PPLAXAuthenticationEntryPoint;
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

    @Autowired
    private PPLAXAccessDeniedHandler pplaxAccessDeniedHandler;

    @Autowired
    private PPLAXAuthenticationEntryPoint pplaxAuthenticationEntryPoint;

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
                .matchers(gatewayProperty.pathMatchers()).permitAll()
                .pathMatchers(gatewayProperty.getExcludePath()).permitAll()
                // 其他的请求必须鉴权，使用鉴权管理器
                .anyExchange().access(pplaxReactiveAuthorizationManager)
                // 鉴权的异常处理，权限不足，token失效
                .and()
                .exceptionHandling()
                // 未登录访问时处理
                .authenticationEntryPoint(pplaxAuthenticationEntryPoint)
                // 访问被拒绝时处理，没有权限访问
                .accessDeniedHandler(pplaxAccessDeniedHandler)
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
    public CorsWebFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

}
