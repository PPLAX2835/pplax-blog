package xyz.pplax.auth.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.pplax.starter.properties.PPLAXProperties;
import xyz.pplax.auth.constant.OauthJwtConstant;
import xyz.pplax.auth.handler.OauthServerAuthenticationFailureHandler;
import xyz.pplax.auth.handler.OauthServerAuthenticationSuccessHandler;
import xyz.pplax.auth.manager.cache.PPLAXUserDetailsCache;
import xyz.pplax.auth.manager.security.CustomAuthServerAccess;
import xyz.pplax.auth.service.AuthServerRememberMeServices;
import xyz.pplax.auth.service.JwtTokenUserDetailsService;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 使用自定义的验证逻辑，从数据库中查询用户信息，有就是根据用户名，查看此用户是否存在，存在的话，存储用户的信息
     */
    @Autowired
    private JwtTokenUserDetailsService jwtTokenUserDetailsService;
    @Autowired
    private OauthServerAuthenticationSuccessHandler oauthServerAuthenticationSuccessHandler;
    @Autowired
    private OauthServerAuthenticationFailureHandler oauthServerAuthenticationFailureHandler;
    @Autowired
    private PPLAXUserDetailsCache pplaxUserDetailsCache;
    @Autowired
    private AuthServerRememberMeServices authServerRememberMeServices;
    @Autowired
    private PPLAXProperties.PPLAXAuthProperties pplaxAuthProperties;

    private final CustomAuthServerAccess serverAccess = new CustomAuthServerAccess();

    /**
     * 数据库中，存储密码使用的加密算法，需要使用这个进行密码的验证
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    /**
//     * 配置验证用户信息的自定义逻辑实现
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(jwtTokenUserDetailsService).passwordEncoder(passwordEncoder());
//    }

    /**
     * 此AuthenticationManager对象在oauth中会使用到
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(jwtTokenUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserCache(pplaxUserDetailsCache);
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .access("@customAuthServerAccess.hasPermission(request)")
                .and()
                .rememberMe()
                .rememberMeServices(authServerRememberMeServices)
                //.tokenValiditySeconds(pplaxAuthProperties.getTokenValiditySeconds())
                .and()
                .formLogin()
                .loginProcessingUrl(OauthJwtConstant.LOGIN_PROCESS_URL)
                .permitAll()
                .successHandler(oauthServerAuthenticationSuccessHandler)
                .failureHandler(oauthServerAuthenticationFailureHandler)
                .and()
                //.authenticationProvider(authenticationProvider())
                .csrf()
                .disable();
    }
}

