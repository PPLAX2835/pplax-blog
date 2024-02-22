package xyz.pplax.pplaxblog.gateway.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.gateway.exception.ExpiredTokenException;
import xyz.pplax.pplaxblog.gateway.exception.InvalidTokenException;
import xyz.pplax.pplaxblog.gateway.exception.NoAuthenticationException;
import xyz.pplax.pplaxblog.gateway.exception.UnLoggedInException;

import java.nio.charset.StandardCharsets;

@Component
public class PPLAXGateWayExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {

        ServerHttpResponse response = serverWebExchange.getResponse();
        String resultJsonStr = null;

        if (throwable instanceof InvalidTokenException) {
            // token无效
            resultJsonStr = JSON.toJSONString(ResponseResult.error(HttpStatus.INVALID_TOKEN));
        } else if (throwable instanceof ExpiredTokenException) {
            // token过期
            resultJsonStr = JSON.toJSONString(ResponseResult.error(HttpStatus.EXPIRED_TOKEN));
        } else if (throwable instanceof NoAuthenticationException) {
            // 没有角色信息
            resultJsonStr = JSON.toJSONString(ResponseResult.error(HttpStatus.NO_ROLE));
        } else if (throwable instanceof UnLoggedInException) {
            // 需要登陆
            resultJsonStr = JSON.toJSONString(ResponseResult.error(HttpStatus.LOGIN_REQUIRED));
        } else {
            resultJsonStr = JSON.toJSONString(ResponseResult.error(HttpStatus.ACCESS_DENIED));
        }

        DataBuffer buffer = response.bufferFactory().wrap(resultJsonStr.getBytes(StandardCharsets.UTF_8));


        return response.writeWith(Mono.just(buffer));
    }
}
