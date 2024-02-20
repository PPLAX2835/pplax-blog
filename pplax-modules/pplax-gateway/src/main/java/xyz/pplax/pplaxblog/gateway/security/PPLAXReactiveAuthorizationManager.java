package xyz.pplax.pplaxblog.gateway.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.pplax.pplaxblog.commons.constants.BaseRegexConstants;
import xyz.pplax.pplaxblog.commons.constants.BaseSysConstants;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.starter.security.model.SecurityUserDetails;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 */
@Component
public class PPLAXReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private static final Logger log = LogManager.getLogger(PPLAXReactiveAuthorizationManager.class);

    @Autowired
    private TokenStore redisTokenStore;

//    @Autowired
//    private RoleFeignClient roleFeignClient;

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
        log.info("当前token所拥有的角色uid:" + authorities.toString());

        // 权限路径，因为没有引入PathAccessPermission的实体类依赖，这里就用map了
        List<Map<String, String>> pathAccessPermissionMapList = new ArrayList<>();

        // 遍历，获得该角色可以访问的路径权限
//        for (GrantedAuthority authority : authorities) {
//            // 获取roleUid
//            String roleUid = authority.getAuthority();          // 这个记录的是roleUid
//
//            // 获得role
//            String respJsonStr = roleFeignClient.getByUid(roleUid);
//            Map respMap = JSONObject.parseObject(respJsonStr, Map.class);
//            // 判断响应码
//            if (respMap.get(BaseSysConstants.DATA) != null) {
//                // 200，获得角色，因为没有引入role的实体类依赖，这里就用map了
//                Map roleMap = (Map) respMap.get(BaseSysConstants.DATA);
//                log.info("当前的角色:" + roleMap.toString());
//
//                // 添加路径访问权限，这里没有去重，以后在配置的时候应该是不会重复的
//                pathAccessPermissionMapList.addAll((Collection<? extends Map<String, String>>) roleMap.get(BaseSysConstants.PATH_ACCESS_PERMISSION));
//            } else {
//                // 获得不到角色，直接拒绝
//                log.warn("获得不到角色");
//                return Mono.just(new AuthorizationDecision(false));
//            }
//
//        }

        // 遍历完成，鉴权
//        boolean permissionFlag = false;
//        for (Map pathAccessPermissionMap : pathAccessPermissionMapList) {
//            /// 获得url
//            String url = serverHttpRequest.getURI().getPath();
//            String pathRegex = pathAccessPermissionMap.get(BaseSysConstants.PATH_REGEX).toString();
//            // 通过pathAccessPermissionMap提供的路径正则进行匹配
//            if (Pattern.matches(pathRegex, url)) {
//                // 当前路径匹配成功，进一步对请求方法进行判断
//
//
//                Boolean result = true;     // 这个result用于记录下面每个规则是否允许，应该是全为 与 的运算
//
//                /*
//                 * 一、判断请求方式是否合法
//                 */
//                result = result && pathAccessPermissionMap.get(BaseSysConstants.METHOD).toString().contains(method.toString());
//
//                /*
//                 * 二、判断是否允许用户访问其他用户uid下的路径
//                 */
//                if (!(Boolean) pathAccessPermissionMap.get(BaseSysConstants.OTHER_USER_ACCESS_PERMISSION)) {
//                    // 不允许用户访问其他用户uid下的路径
//                    JSONObject securityUserDetailJsonObject = JSON.parseObject(JSON.toJSONString(oAuth2Authentication.getUserAuthentication().getPrincipal()));
//                    String uid = (String) securityUserDetailJsonObject.get(BaseSysConstants.UID);       // 当前用户的uid
//
//                    // 获取访问路径的uid
//                    String keyword = "user/";
//                    int startIndex = url.indexOf(keyword);
//                    if (startIndex != -1) {
//                        // 如果找到了关键词
//                        startIndex += keyword.length(); // 跳过关键词本身
//                        int endIndex = url.indexOf("/", startIndex); // 找到下一个斜杠的位置
//
//                        if (endIndex != -1) {
//                            String urlUid = url.substring(startIndex, endIndex);
//
//
//                            result = result && uid.equals(urlUid);                                                      // 判断两个uid是否匹配
//                        }
//                    }
//                }
//
//                /*
//                 * 三、后续可能还会有
//                 */
//
//                // 全部通过了，可以跳出循环了
//                if (result) {
//                    permissionFlag = true;
//                    break;
//                }
//
//            }
//        }

//        if (permissionFlag) {
//            return authenticationMono
//                    .map(auth -> new AuthorizationDecision(true))       // false就是拒绝
//                    .defaultIfEmpty(new AuthorizationDecision(false));
//        }
        return authenticationMono
                .map(auth -> new AuthorizationDecision(true))       // false就是拒绝
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
