package xyz.pplax.pplaxblog.auth.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.pplax.pplaxblog.auth.service.UserDetailService;

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
        UserDetails userDetails = userDetailService.loadUserByUsername(username);

        // 获得密文密码和盐 密码中包含盐和密码密文，这里先获得一个map
        final Map<String, String> passwordMsg = JSON.parseObject(userDetails.getPassword(), new TypeReference<Map<String, String>>(){});

        // 获得查库拿到密码密文、盐
        final String dbPassword = passwordMsg.get("password");
        final String dbSalt = passwordMsg.get("salt");

        // 进行密码的比对
        boolean flag = passwordEncoder.matches(password + dbSalt, dbPassword);

        // 校验通过
        if (flag) {
            // 将权限信息也封装进去
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        }

        // 校验失败
        throw new AuthenticationException("用户密码错误") {};
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
