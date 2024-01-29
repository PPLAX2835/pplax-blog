package xyz.pplax.pplaxblog.auth.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import xyz.pplax.pplaxblog.auth.model.SecurityUserDetails;
import xyz.pplax.pplaxblog.auth.service.UserDetailService;
import xyz.pplax.pplaxblog.auth.exception.AccountIsNotRegisteredException;
import xyz.pplax.pplaxblog.auth.exception.EmailUnactivatedException;
import xyz.pplax.pplaxblog.auth.exception.MobileUnactivatedException;
import xyz.pplax.pplaxblog.commons.base.global.BaseMessageConf;
import xyz.pplax.pplaxblog.commons.base.global.BaseSysConf;
import xyz.pplax.pplaxblog.xo.entity.User;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Configuration
public class AuthenticationProviderConfig implements AuthenticationProvider {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取用户输入的用户名和密码
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();

        // 获取封装用户信息的对象
        SecurityUserDetails securityUserDetails = (SecurityUserDetails) userDetailService.loadUserByUsername(username);

        // 验证一下userDetails有没有问题
        if (BaseMessageConf.EMAIL_UNACTIVATED.equals(securityUserDetails.getUsername())) {
            throw new EmailUnactivatedException(BaseMessageConf.EMAIL_UNACTIVATED);
        } else if (BaseMessageConf.MOBILE_UNACTIVATED.equals(securityUserDetails.getUsername())) {
            throw new MobileUnactivatedException(BaseMessageConf.EMAIL_UNACTIVATED);
        } else if (BaseMessageConf.ACCOUNT_IS_NOT_REGISTERED.equals(securityUserDetails.getUsername())) {
            throw new AccountIsNotRegisteredException(BaseMessageConf.ACCOUNT_IS_NOT_REGISTERED);
        }

        // 获得密文密码和盐
        final String dbPassword = securityUserDetails.getPassword();
        final String dbSalt = securityUserDetails.getSalt();

        // 进行密码的比对
        boolean flag = passwordEncoder.matches(password + dbSalt, dbPassword);

        // 校验通过
        if (flag) {
            // 将权限信息也封装进去
            return new UsernamePasswordAuthenticationToken(securityUserDetails, password, securityUserDetails.getAuthorities());
        }

        // 校验失败
        throw new AuthenticationException(BaseMessageConf.ERROR_PASSWORD) {};
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
