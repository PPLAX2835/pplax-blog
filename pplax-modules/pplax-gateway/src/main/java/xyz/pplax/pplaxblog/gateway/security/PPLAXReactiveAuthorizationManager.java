package xyz.pplax.pplaxblog.gateway.security;

import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 */
@Component
public class PPLAXReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authenticationMono, AuthorizationContext authorizationContext) {

        ServerWebExchange serverWebExchange = authorizationContext.getExchange();

        // 获得请求
        ServerHttpRequest serverHttpRequest = serverWebExchange.getRequest();
        // 获得响应
        ServerHttpResponse serverHttpResponse = serverWebExchange.getResponse();

        // 获得请求方法
        HttpMethod method = serverHttpRequest.getMethod();
        // 获得请求uri
        URI uri = serverHttpRequest.getURI();

        // 将当前的请求方法和uri组装成一个restFul风格的地址
        String restFulPath = method + ":" + uri.getPath();

        /// 对应跨域的预检请求直接放行
        if (method == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 鉴权
        /*
         * 目前想法：
         * 游客只能使用get方法
         * 普通用户只能操作自己的，且访问不了admin路径
         * 管理员可以使用get、post等、但不能使用delete
         * 超级管理员都可以
         */



        /// 进行鉴权
        String url = serverHttpRequest.getURI().getPath();
        return authenticationMono
                .map(auth -> new AuthorizationDecision(true))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
