package xyz.pplax.gateway.exception.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;

/**
 * 未登录访问需要认证的资源时处理类
 */
@Component
public class PPLAXAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return SecurityResultHandler.failure(exchange, ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN);
    }
}
