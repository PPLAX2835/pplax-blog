package xyz.pplax.pplaxblog.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import xyz.pplax.pplaxblog.commons.config.cors.CustomerCorsFilter;

@Configuration
@EnableResourceServer
public class ResourceConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Value("${pplax.sso.admin.resource-id}")
    private String resourceId;

    @Autowired
    private CustomerCorsFilter customerCorsFilter;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(resourceId).stateless(true);
        resources.tokenStore(tokenStore());

    }

    @Bean
    public RedisTokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    public void configure(HttpSecurity http) throws Exception {

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/api/admin/oauth/**",
                        "/api/admin/test/**",
                        "/api/admin/testes/**",
                        "/assets/**",
                        "/advice/**",
                        "/css/**",
                        "/js/**",
                        "/image/**"
                ).permitAll()
                .antMatchers("/api/admin/**").authenticated()
                .and()
                .anonymous()
                .and()
                .csrf()
                .disable()
                .addFilterBefore(customerCorsFilter, WebAsyncManagerIntegrationFilter.class);

    }


}