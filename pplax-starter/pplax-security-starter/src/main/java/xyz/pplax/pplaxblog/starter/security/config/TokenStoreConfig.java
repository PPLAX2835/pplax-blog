package xyz.pplax.pplaxblog.starter.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.LinkedHashMap;

@Configuration
public class TokenStoreConfig {

    @Value("${pplax.oauth.secret-key}")
    private String secretKey;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 获得redis的token存储bean
     * @return
     */
    @Bean
    public TokenStore redisTokenStore (){
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * 获得jwt的token存储bean
     * @return
     */
    @Bean
    public TokenStore jwtTokenStore () {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenEnhancer = new JwtAccessTokenEnhancer();
        // 设置秘钥
        jwtAccessTokenEnhancer.setSigningKey(secretKey);
        return jwtAccessTokenEnhancer;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
