package xyz.pplax.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import xyz.pplax.auth.service.JwtTokenUserDetailsService;
import xyz.pplax.starter.properties.PPLAXProperties;

/**
 * token配置，OAuth配置注入需要
 */
@Configuration
public class AccessTokenConfig {

    @Autowired
    private PPLAXProperties.PPLAXAuthProperties pplaxAuthProperties;
    @Autowired
    private JwtTokenUserDetailsService userDetailsService;

    /**
     * 令牌存储模式,有多种方式存储token，可以是jwt，redis等
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
        //return new RedisTokenStore(factory);
    }


    /**
     * JwtAccessTokenConverter是TokenEnhancer的子类，在JWT编码的令牌值和OAuth身份验证信息之间进行转换。
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenEnhancer jwtAccessTokenEnhancer = new JwtAccessTokenEnhancer();
        // 设置秘钥
        jwtAccessTokenEnhancer.setSigningKey(pplaxAuthProperties.getSecretKey());
        return jwtAccessTokenEnhancer;
    }
}

