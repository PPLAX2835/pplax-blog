package xyz.pplax.auth.exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import xyz.pplax.auth.util.OauthServerUtils;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义AuthenticationEntryPoint，
 * 用于clientId，账号，密码等出错处理类
 */
@Component
public class OAuthServerAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // AbstractUserDetailsAuthenticationProvider
        OauthServerUtils.failure(request, response, ResponseStatusCodeEnum.OAUTH_CLIENT_SECRET_MISTAKE.getMessage(),
                ResponseStatusCodeEnum.OAUTH_CLIENT_SECRET_MISTAKE.getCode());
    }
}
