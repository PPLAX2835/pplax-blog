package xyz.pplax.pplaxblog.starter.security.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;
import xyz.pplax.pplaxblog.commons.constants.BaseSysConstants;
import xyz.pplax.pplaxblog.starter.security.model.SecurityUserDetails;

import java.util.LinkedHashMap;

/**
 * JWT令牌增强，继承JwtAccessTokenConverter
 * 将业务所需的额外信息放入令牌中，这样下游微服务就能解析令牌获取
 */
public class JwtAccessTokenEnhancer extends JwtAccessTokenConverter {
    /**
     * 重写enhance方法，在其中扩展
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        // 获取查询到的用户信息
        SecurityUserDetails user = (SecurityUserDetails) authentication.getUserAuthentication().getPrincipal();
        // 将额外的信息放入hashmap中
        LinkedHashMap<String, Object> extendInformation = new LinkedHashMap<>();

        // 添加信息
        extendInformation.put(BaseSysConstants.UID, user.getUid());                          // 用户uid
        extendInformation.put(BaseSysConstants.SALT, user.getSalt());                        // 加密盐
        extendInformation.put(BaseSysConstants.USER_INFO_UID, user.getUserInfoUid());        // 用户信息uid
        extendInformation.put(BaseSysConstants.ROLE_UID, user.getRoleUid());                 // 用户的角色uid

        // 将extendInformation添加到额外的信息中
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(extendInformation);
        return super.enhance(accessToken, authentication);
    }
}