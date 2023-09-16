package xyz.pplax.gateway.exception.handler;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.pplax.core.entity.R;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.util.ConvertObjectUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一对security的异常或者登录成功，权限错误等进行统一处理
 */
@Component
public class SecurityResultHandler {

    /**
     * 成功
     * @param exchange
     * @param authentication
     * @return
     */
    public static Mono<Void> success(ServerWebExchange exchange, Authentication authentication) {
        //1. 获取用户名等信息
        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) authentication.getAuthorities();
        setContentType(exchange, null);
        Map<String,Object> grantedAuthoritiesMap = new HashMap<>();
        grantedAuthoritiesMap.put("permission",grantedAuthorities);
        R success = R.success(ResponseStatusCodeEnum.SUCCESS.getCode(), ResponseStatusCodeEnum.SUCCESS.getMessage(),grantedAuthoritiesMap, true);

        String s = ConvertObjectUtils.jsonToString(success);
        return getMonoTypeResult(s,exchange);
    }

    /**
     * 鉴权异常，登录异常，无权访问等异常处理方法
     * @param exchange ServerWebExchange
     * @param statusCodeEnum 自定义响应码
     * @return Mono
     */
    public static Mono<Void> failure(ServerWebExchange exchange, ResponseStatusCodeEnum statusCodeEnum) {
        ServerHttpRequest request = exchange.getRequest();

        //uri就是访问出错的路径
        String uri = request.getPath().value();

        //设置响应头信息
        setContentType(exchange, statusCodeEnum);

        //封装数据
        R failureResult = R.failure(statusCodeEnum.getCode(), statusCodeEnum.getMessage());

        Map<String,Object> errorMap = new HashMap<>();
        errorMap.put("errorUrl",uri);

        failureResult.setData(errorMap);

        String s = "";
        try {
            s = ConvertObjectUtils.jsonToString(failureResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getMonoTypeResult(s,exchange);
    }

    /**
     * 设置响应头
     * @param exchange
     */
    private static void setContentType(ServerWebExchange exchange, ResponseStatusCodeEnum statusCodeEnum) {
        ServerHttpResponse response = exchange.getResponse();
        if (statusCodeEnum != null && ResponseStatusCodeEnum.PERMISSION_TOKEN_EXPIRATION == statusCodeEnum) {
            // token过期
             response.setStatusCode(HttpStatus.FORBIDDEN);
        }
        //设置响应头
        MediaType applicationJson = new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8);
        response.getHeaders().setContentType(applicationJson);
    }

    /**
     * 根据json字符串返回一个Mono<Void>对象
     * @param resultJsonStr json字符串
     * @param exchange
     * @return
     */
    private static Mono<Void> getMonoTypeResult(String resultJsonStr,ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        DataBuffer buffer = response.bufferFactory().wrap(resultJsonStr.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
