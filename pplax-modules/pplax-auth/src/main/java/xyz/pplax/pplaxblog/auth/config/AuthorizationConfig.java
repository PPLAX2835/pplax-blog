package xyz.pplax.pplaxblog.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import xyz.pplax.pplaxblog.commons.base.global.BaseSysConf;

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

    @Value("${pplax.sso.admin.client-id}")
    private String adminClientId;

    @Value("${pplax.sso.admin.client-secret}")
    private String adminClientSecret;

    @Value("${pplax.sso.admin.resource-id}")
    private String adminResourceId;

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
                // admin服务的配置
                .withClient(adminClientId)
                .secret(passwordEncoder.encode(adminClientSecret))
                .authorizedGrantTypes(BaseSysConf.PASSWORD)
                .accessTokenValiditySeconds(3600)
                .scopes("all")
                .resourceIds(adminResourceId);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        security.checkTokenAccess("isAuthenticated()");
        security.tokenKeyAccess("isAuthenticated()");
    }

}