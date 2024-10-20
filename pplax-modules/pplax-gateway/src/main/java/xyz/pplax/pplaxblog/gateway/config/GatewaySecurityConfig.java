package xyz.pplax.pplaxblog.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
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
                .addFilterAt(corsWebFilter(), SecurityWebFiltersOrder.CORS)
                // token的认证过滤器，用于校验token和认证
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        return httpSecurity.build();
    }

    /**
     * 跨域请求过滤器
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");  // 允许所有跨域访问，生产环境建议限定域名
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);  // 应用到所有路径

        return new CorsWebFilter(source);
    }

}
