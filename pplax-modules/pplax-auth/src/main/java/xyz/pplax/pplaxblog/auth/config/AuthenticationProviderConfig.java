package xyz.pplax.pplaxblog.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.pplax.pplaxblog.starter.security.exception.AccountIsNotRegisteredException;
import xyz.pplax.pplaxblog.starter.security.exception.EmailUnactivatedException;
import xyz.pplax.pplaxblog.starter.security.exception.MobileUnactivatedException;
import xyz.pplax.pplaxblog.auth.service.UserDetailService;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.starter.security.exception.PasswordErrorException;
import xyz.pplax.pplaxblog.starter.security.model.SecurityUserDetails;

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
        if (HttpStatus.EMAIL_UNACTIVATED.getMessage().equals(securityUserDetails.getUsername())) {
            throw new EmailUnactivatedException();
        } else if (HttpStatus.MOBILE_UNACTIVATED.getMessage().equals(securityUserDetails.getUsername())) {
            throw new MobileUnactivatedException();
        } else if (HttpStatus.ACCOUNT_IS_NOT_REGISTERED.getMessage().equals(securityUserDetails.getUsername())) {
            throw new AccountIsNotRegisteredException();
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
        throw new PasswordErrorException();
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
