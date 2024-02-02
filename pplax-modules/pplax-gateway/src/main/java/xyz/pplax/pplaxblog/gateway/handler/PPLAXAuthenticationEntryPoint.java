package xyz.pplax.pplaxblog.gateway.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.pplax.pplaxblog.commons.constants.BaseMessageConstants;
import xyz.pplax.pplaxblog.commons.response.ResponseCode;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;

import java.nio.charset.StandardCharsets;


/**
 * 未登录访问需要认证的资源时处理类
 */
@Component
public class PPLAXAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange serverWebExchange, AuthenticationException e) {
        ServerHttpResponse response = serverWebExchange.getResponse();
        String resultJsonStr = JSON.toJSONString(ResponseResult.error(ResponseCode.NO_AUTH, BaseMessageConstants.LOGIN_REQUIRED));
        DataBuffer buffer = response.bufferFactory().wrap(resultJsonStr.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
