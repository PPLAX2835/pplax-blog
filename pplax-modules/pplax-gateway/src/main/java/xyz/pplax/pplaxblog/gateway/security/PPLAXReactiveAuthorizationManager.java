package xyz.pplax.pplaxblog.gateway.security;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;

import java.net.URI;
import java.util.Collection;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 */
@Component
public class PPLAXReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private static final Logger log = LogManager.getLogger(PPLAXReactiveAuthorizationManager.class);

    @Autowired
    private TokenStore redisTokenStore;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authenticationMono, AuthorizationContext authorizationContext) {

        ServerWebExchange serverWebExchange = authorizationContext.getExchange();

        // 获得请求
        ServerHttpRequest serverHttpRequest = serverWebExchange.getRequest();
        // 获得响应
        ServerHttpResponse serverHttpResponse = serverWebExchange.getResponse();
        // 获得请求头
        HttpHeaders serverHttpRequestHeaders = serverHttpRequest.getHeaders();


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
        // 获得token
        String authorizationToken = serverHttpRequestHeaders.getFirst(HttpHeaders.AUTHORIZATION);
        log.info("当前请求头Authorization中的值:" + authorizationToken);
        if (StringUtils.isEmpty(authorizationToken)) {
            log.warn("当前请求头Authorization中的值不存在");
            return Mono.just(new AuthorizationDecision(false));
        }
        String token = authorizationToken.replace(OAuth2AccessToken.BEARER_TYPE + " ", "");

        OAuth2Authentication oAuth2Authentication = redisTokenStore.readAuthentication(token);
        log.info("当前token所拥有的OAuth2Authentication:" + oAuth2Authentication);
        Collection<GrantedAuthority> authorities = oAuth2Authentication.getAuthorities();
        log.info("当前token所拥有的权限:" + authorities.toString());

        for (GrantedAuthority authority : authorities) {
            // 获取roleUid
            String roleUid = authority.getAuthority();          // 这个记录的是roleUid

            // 获得role



//            if (pathMatcher.match(authorityStr, requestPath)) {
//                //设置请求头参数username
//                ServerHttpRequest request = exchange.getRequest().mutate()
//                        .headers(httpHeaders -> httpHeaders.add("username",oAuth2Authentication.getName())).build();
//                exchange.mutate().request(request).build();
//                return Mono.just(new AuthorizationDecision(true));
//            }
        }



        /// 进行鉴权
        String url = serverHttpRequest.getURI().getPath();
        return authenticationMono
                .map(auth -> new AuthorizationDecision(true))       // false就是拒绝
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
