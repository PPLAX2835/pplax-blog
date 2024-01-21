package xyz.pplax.pplaxblog.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 这是redis token方式
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore redisTokenStore;

    /**
     * redis token 方式
     */
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)  // 调用此方法才能支持 password 模式
                .userDetailsService(userDetailsService)     // 设置用户验证服务
                .tokenStore(redisTokenStore);                   //指定 token 的存储方式
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("user-client")
                .secret(passwordEncoder.encode("user-secret"))
                .authorizedGrantTypes("refresh_token", "authorization_code", "password")
                .accessTokenValiditySeconds(3600)
                .scopes("all")
                .resourceIds("test_resource")
                .redirectUris("https://www.bilibili.com/");
    }
//    http://localhost:8082/oauth/authorize?client_id=user-client&client_secret=user-secret&response_type=code&redirect_uri=https://www.bilibili.com/

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        security.checkTokenAccess("isAuthenticated()");
        security.tokenKeyAccess("isAuthenticated()");
    }

}