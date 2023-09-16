package xyz.pplax.gateway.exception.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;


/**
 * 访问被拒绝时处理类
 */
@Component
public class PPLAXAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        return SecurityResultHandler.failure(exchange, ResponseStatusCodeEnum.PERMISSION_DENIED);
    }
}
