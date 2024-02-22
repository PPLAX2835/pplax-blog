package xyz.pplax.pplaxblog.gateway.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.gateway.exception.ExpiredTokenException;
import xyz.pplax.pplaxblog.gateway.exception.InvalidTokenException;
import xyz.pplax.pplaxblog.gateway.exception.NoAuthenticationException;

/**
 * JWT认证管理器，主要的作用就是对携带过来的token进行校验，比如过期时间，加密方式等
 * 一旦token校验通过，则交给鉴权管理器进行鉴权
 */
@Component
@Slf4j
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private TokenStore redisTokenStore;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(a -> a instanceof BearerTokenAuthenticationToken)
                .cast(BearerTokenAuthenticationToken.class)
                .map(BearerTokenAuthenticationToken::getToken)
                .flatMap((accessToken -> {
                    OAuth2AccessToken oAuth2AccessToken = redisTokenStore.readAccessToken(accessToken);
                    // 根据access_token从数据库获取不到OAuth2AccessToken
                    if (oAuth2AccessToken == null) {
                        return Mono.error(new InvalidTokenException(HttpStatus.INVALID_TOKEN.getMessage()));
                    } else if (oAuth2AccessToken.isExpired()) {
                        return Mono.error(new ExpiredTokenException(HttpStatus.EXPIRED_TOKEN.getMessage()));
                    }
                    OAuth2Authentication oAuth2Authentication = redisTokenStore.readAuthentication(accessToken);
                    if (oAuth2Authentication == null) {
                        return Mono.error(new NoAuthenticationException(HttpStatus.INVALID_TOKEN.getMessage()));
                    } else {
                        return Mono.just(oAuth2Authentication);
                    }
                })).cast(Authentication.class);
    }
}