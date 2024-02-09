package xyz.pplax.pplaxblog.auth.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.pplax.pplaxblog.commons.controller.SuperController;

@RestController
@RequestMapping(value = "/oauth")
public class PPLAXOauthRestApi extends SuperController {

    @Autowired
    private TokenStore redisTokenStore;

    @DeleteMapping("/access_token")
    public String removeToken(@RequestParam String token) {
        OAuth2AccessToken oAuth2AccessToken = redisTokenStore.readAccessToken(token);
        redisTokenStore.removeAccessToken(oAuth2AccessToken);

        return success();
    }

}
