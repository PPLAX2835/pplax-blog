package xyz.pplax.pplaxblog.gateway.security;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.pplax.pplaxblog.commons.constants.BaseSysConstants;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.gateway.exception.UnLoggedInException;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.xo.constants.redis.RoleRedisConstants;
import xyz.pplax.pplaxblog.xo.entity.Menu;
import xyz.pplax.pplaxblog.xo.entity.Role;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 */
@Component
public class PPLAXReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private static final Logger log = LogManager.getLogger(PPLAXReactiveAuthorizationManager.class);

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private TokenStore redisTokenStore;

    @Autowired
    private RedisService redisService;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authenticationMono, AuthorizationContext authorizationContext) {

        ServerWebExchange serverWebExchange = authorizationContext.getExchange();

        // 获得请求
        ServerHttpRequest serverHttpRequest = serverWebExchange.getRequest();
        // 获得请求头
        HttpHeaders serverHttpRequestHeaders = serverHttpRequest.getHeaders();

        // 获得请求方法
        HttpMethod method = serverHttpRequest.getMethod();

        /// 对应跨域的预检请求直接放行
        if (method == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 获得token
        String authorizationToken = serverHttpRequestHeaders.getFirst(HttpHeaders.AUTHORIZATION);
        log.info("当前请求头Authorization中的值:" + authorizationToken);
        if (StringUtils.isEmpty(authorizationToken)) {
            log.warn("当前请求头Authorization中的值不存在");
            return Mono.error(new UnLoggedInException(HttpStatus.LOGIN_REQUIRED.getMessage()));
        }
        String token = authorizationToken.replace(OAuth2AccessToken.BEARER_TYPE + " ", "");

        OAuth2Authentication oAuth2Authentication = redisTokenStore.readAuthentication(token);
        log.info("当前token所拥有的OAuth2Authentication:" + oAuth2Authentication);
        Collection<GrantedAuthority> authorities = oAuth2Authentication.getAuthorities();
        log.info("当前token所拥有的角色uid:" + authorities.toString());

        // 角色列表
        List<Role> roleList = new ArrayList<>();

        // 遍历，获得该角色可以访问的路径权限
        for (GrantedAuthority authority : authorities) {
            // 获取roleUid
            String roleUid = authority.getAuthority();          // 这个记录的是roleUid

            // 获得role
            Role role = JSON.toJavaObject(redisService.getCacheObject(RoleRedisConstants.ROLE + RoleRedisConstants.SEGMENTATION + roleUid), Role.class);

            roleList.add(role);
        }

        /// 获得url
        String url = serverHttpRequest.getURI().getPath();
        // 鉴权
        if (requestAuthenticationByRoleList(method.toString(), url, roleList)) {
            return authenticationMono
                    .map(auth -> new AuthorizationDecision(true))
                    .defaultIfEmpty(new AuthorizationDecision(false));
        }

        return authenticationMono
                .map(auth -> new AuthorizationDecision(false))       // false就是拒绝
                .defaultIfEmpty(new AuthorizationDecision(false));
    }


    /**
     * 根据请求方式，请求地址和用户所拥有的权限进行鉴权
     * @param method
     * @param url
     * @param roleList
     * @return
     */
    private boolean requestAuthenticationByRoleList(String method, String url, List<Role> roleList) {
        if (roleList == null) {
            return false;
        }
        for (Role role : roleList) {
            if (requestAuthenticationByByMenuList(method, url, role.getMenuList())) {
                return true;
            }
        }
        return false;
    }
    private boolean requestAuthenticationByByMenuList(String method, String url, List<Menu> menuList) {
        if (menuList == null) {
            return false;
        }
        for (Menu menu : menuList) {
            if (BaseSysConstants.BUTTON.equals(menu.getType())) {
                // 如果是按钮类型，则需要鉴权，路由类型后端管不着

                // 获得这个菜单的访问规则
                String[] urlArr = menu.getEndpoint().split(CharacterConstants.DELIMITER_COLON);
                String menuMethod = urlArr[0];                              // 请求方式
                String menuUrl = urlArr[1];                                 // 请求url

                if (method.equals(menuMethod) && antPathMatcher.match(menuUrl, url)) {
                    // 判断url
                    return true;
                }
            }
            // 向子菜单递归
            if (menu.getChildren() != null && requestAuthenticationByByMenuList(method, url, menu.getChildren())) {
                return true;
            }
        }
        return false;
    }
}
