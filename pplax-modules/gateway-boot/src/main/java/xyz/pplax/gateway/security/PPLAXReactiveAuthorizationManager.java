package xyz.pplax.gateway.security;


import cn.hutool.core.collection.CollectionUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.pplax.admin.dto.RolePermissionDTO;
import xyz.pplax.admin.po.WhiteUrl;
import xyz.pplax.admin.vo.WhiteUrlVO;
import xyz.pplax.amqp.api.AmqpSenderService;
import xyz.pplax.amqp.comstant.AmqpExchangeNameConstant;
import xyz.pplax.amqp.comstant.AmqpQueueNameConstant;
import xyz.pplax.auth.constant.OauthJwtConstant;
import xyz.pplax.auth.constant.RequestConstant;
import xyz.pplax.core.constant.RedisConstant;
import xyz.pplax.core.util.lambda.AssertUtils;
import xyz.pplax.data.entity.PageData;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 鉴权管理器
 * 自定义鉴权逻辑处理类 在这里判断用户的账户是否过期等等操作,这个类的执行，在登录的时候，不会执行，只有登录成功或者没有登录的时候，进行鉴权
 * 如果返回new AuthorizationDecision(true)，鉴权成功
 * 返回new AuthorizationDecision(false)，鉴权失败
 */
@Component
public class PPLAXReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private AmqpSenderService amqpSenderService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> contextAuthentication, AuthorizationContext authorizationContext) {

        ServerWebExchange exchange = authorizationContext.getExchange();
        ServerHttpRequest request = exchange.getRequest();

        // 获取请求方法
        String method = request.getMethodValue();
        // 获取请求的uri
        URI uri = request.getURI();

        // 将当前的请求方法和uri组装成一个restFul风格的地址
        String restFulPath = method + ":" + uri.getPath();

        // 添加请求头
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = response.getHeaders();
        headers.add("Access-Control-Allow-Origin","*");
        headers.add("Access-Control-Allow-Methods","GET, POST, OPTIONS");
        headers.add("Access-Control-Allow-Credentials","true");
        headers.add("Access-Control-Allow-Headers","*");

        // 白名单监测
        if (isWhiteUrl(restFulPath)) {
            // 如果是白名单的话，将白名单的状态放入到当前RequestContextHolder中，不放入到请求头中，为了方便获取
            exchange.getRequest().mutate().header(RequestConstant.REQUEST_WHITE_URL_STATUS, "true");
            return Mono.just(new AuthorizationDecision(true));
        }

        // 获取redis中的所有角色权限关系
        List<RolePermissionDTO> allRolePermissionList = null;
        try {
            allRolePermissionList = (List<RolePermissionDTO>) redisTemplate.opsForValue().get(RedisConstant.STORAGE_ROLE_PERMISSION_INFO);
        } catch (Exception e) {
            // 如果redis中没有角色权限关系信息，直接返回鉴权失败 为了保护系统
            return Mono.just(new AuthorizationDecision(false));
        }

        if (allRolePermissionList == null) {
            // 如果redis中没有角色权限关系信息，可能是缓存失效，则调用feign重新获取缓存信息，对于此次请求，直接返回鉴权失败 为了保护系统
            rabbitTemplate.send(AmqpExchangeNameConstant.PPLAX_SEND_OPERATE_USER_EXCHANGE,
                    AmqpQueueNameConstant.UPDATE_ROLE_PERMISSION_CACHE_ROUTING_KEY, new Message("更新角色权限缓存".getBytes(StandardCharsets.UTF_8)));

            // 这是干啥呢？    现在我猜是把刚才放到消息队列中的数据一个个拿出来放进去
            while (redisTemplate.opsForValue().get(RedisConstant.STORAGE_ROLE_PERMISSION_INFO) == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
            }


            try {
                allRolePermissionList = (List<RolePermissionDTO>) redisTemplate.opsForValue().get(RedisConstant.STORAGE_ROLE_PERMISSION_INFO);
            } catch (Exception e) {
                // 如果redis中没有角色权限关系信息，直接返回鉴权失败 为了保护系统
                return Mono.just(new AuthorizationDecision(false));
            }
            // return Mono.just(new AuthorizationDecision(false));
        }

        // 获取访问该路径，所需要的权限
        Set<String> roleList = new HashSet<>();
        AssertUtils.ifNullThrow(allRolePermissionList, () -> new NullPointerException("权限列表为空"));
        for (RolePermissionDTO rolePermissionDTO : allRolePermissionList) {
            if (antPathMatcher.match(rolePermissionDTO.getPath(), restFulPath)) {
                roleList.add(rolePermissionDTO.getRoleName());
            }
        }

        return contextAuthentication
                // 是否认证成功
                .filter(Authentication::isAuthenticated)
                // 获取认证后的全部权限
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                //如果权限包含则判断为true
                .any(authority->{
                    // 超级管理员直接放行
                    if (OauthJwtConstant.SUPER_ADMINISTRATOR_ROLE_NAME.equals(authority)) {
                        return true;
                    }
                    //其他必须要判断角色是否存在交集
                    return CollectionUtil.isNotEmpty(roleList) && roleList.contains(authority);
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    /**
     * 检查是否位于白名单
     * @param restFulPath
     * @return
     */
    private boolean isWhiteUrl(String restFulPath) {
        // 获取redis中的所有白名单
        PageData<WhiteUrlVO> pageData = (PageData<WhiteUrlVO>) redisTemplate.opsForValue().get(RedisConstant.STORAGE_WHITE_URL_INFO);
        List<WhiteUrlVO> whiteUrlList = new ArrayList<>();
        if (pageData != null) {
            whiteUrlList = pageData.getResult();
        }
        if (whiteUrlList == null || whiteUrlList.isEmpty()) {
            // 发送消息更新缓存
            rabbitTemplate.send(AmqpExchangeNameConstant.PPLAX_SEND_OPERATE_USER_EXCHANGE,
                    AmqpQueueNameConstant.UPDATE_WHITE_URL_CACHE_ROUTING_KEY, new Message("更新白名单".getBytes(StandardCharsets.UTF_8)));
        }
        String[] whiteUrlArr = getStaticResourceWhiteUrlArr(whiteUrlList);
        // 判断当前访问的restful风格的请求地址是否是一个白名单
        for (String whiteUrl : whiteUrlArr) {
            if (antPathMatcher.match(whiteUrl, restFulPath)) {
                return true;
            }
        }
        return false;
    }

    private String[] getStaticResourceWhiteUrlArr(List<WhiteUrlVO> whiteUrlList) {
        whiteUrlList = Optional.ofNullable(whiteUrlList).orElse(List.of(new WhiteUrlVO()));

        Stream<String> whiteUrlStream = whiteUrlList.stream().map(WhiteUrl::getUrl);
        Stream<String> staticResourceStream = Arrays.stream(OauthJwtConstant.PUBLIC_STATIC_RESOURCE)
                .map(statusResource -> "*." + statusResource);
        // 获取所有静态资源
        return Stream.concat(whiteUrlStream, staticResourceStream)
                .collect(Collectors.joining(","))
                .split(",");
    }
}
