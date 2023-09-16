package xyz.pplax.auth.service;

import cn.hutool.core.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.pplax.auth.constant.OauthJwtConstant;
import xyz.pplax.auth.util.RequestUtils;
import xyz.pplax.starter.properties.PPLAXProperties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class AuthServerRememberMeServices implements RememberMeServices {

    @Autowired
    private PPLAXProperties.PPLAXAuthProperties pplaxAuthProperties;


    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        // 登陆成功，存到cookie中
        // 默认30分钟
        int maxAge = Optional.ofNullable(pplaxAuthProperties.getCookieMaxAge()).orElse(60 * 30);

    }

    /**
     * 设置cookie
     * @param maxAge
     * @param request
     * @param response
     * @param successfulAuthentication
     */
    protected void setCookie(int maxAge, HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        // 获得cookie的值
        String cookieValue = RequestUtils.encodeCookie(RequestUtils.calculateExpiryTime(maxAge), retrieveUserName(successfulAuthentication));
        // 添加cookie
        Cookie cookie = new Cookie(OauthJwtConstant.COOKIE_STORAGE_LOGIN_SUCCESS_STATUS, cookieValue);
        cookie.setMaxAge(maxAge);
        cookie.setPath(RequestUtils.getCookiePath(request));

        if (StringUtils.hasLength(pplaxAuthProperties.getCookieDomain())) {
            cookie.setDomain(pplaxAuthProperties.getCookieDomain());
        }
        if (maxAge < 1) {
            cookie.setVersion(1);
        }
        cookie.setSecure((pplaxAuthProperties.getUseSecureCookie() != null) ?
                pplaxAuthProperties.getUseSecureCookie() : request.isSecure());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }


    /**
     * 获取用户名
     * @param authentication
     * @return
     */
    protected String retrieveUserName(Authentication authentication) {
        if (isInstanceOfUserDetails(authentication)) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return authentication.getPrincipal().toString();
    }

    /**
     * 获取密码
     * @param authentication
     * @return
     */
    protected String retrievePassword(Authentication authentication) {
        if (isInstanceOfUserDetails(authentication)) {
            return ((UserDetails) authentication.getPrincipal()).getPassword();
        }
        if (authentication.getCredentials() != null) {
            return authentication.getCredentials().toString();
        }
        return null;
    }

    /**
     * 判断实例
     * @param authentication
     * @return
     */
    private boolean isInstanceOfUserDetails(Authentication authentication) {
        return authentication.getPrincipal() instanceof UserDetails;
    }
}
