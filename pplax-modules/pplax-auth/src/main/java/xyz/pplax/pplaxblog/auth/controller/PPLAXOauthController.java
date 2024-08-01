package xyz.pplax.pplaxblog.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;

@RestController
@RequestMapping(value = "/oauth")
public class PPLAXOauthController extends SuperController {

    @Autowired
    private TokenStore redisTokenStore;

    @DeleteMapping("/access_token")
    public ResponseResult removeToken(@RequestParam String token) {
        OAuth2AccessToken oAuth2AccessToken = redisTokenStore.readAccessToken(token);
        redisTokenStore.removeAccessToken(oAuth2AccessToken);

        return success();
    }

}
