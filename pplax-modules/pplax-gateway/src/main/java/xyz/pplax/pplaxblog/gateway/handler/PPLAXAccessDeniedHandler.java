package xyz.pplax.pplaxblog.gateway.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.pplax.pplaxblog.commons.constants.BaseMessageConstants;
import xyz.pplax.pplaxblog.commons.response.ResponseCode;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;

import java.nio.charset.StandardCharsets;


/**
 * 访问被拒绝时处理类
 */
@Component
public class PPLAXAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException e) {
        ServerHttpResponse response = serverWebExchange.getResponse();
        String resultJsonStr = JSON.toJSONString(ResponseResult.error(ResponseCode.NO_AUTH, BaseMessageConstants.ACCESS_DENIED));
        DataBuffer buffer = response.bufferFactory().wrap(resultJsonStr.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
