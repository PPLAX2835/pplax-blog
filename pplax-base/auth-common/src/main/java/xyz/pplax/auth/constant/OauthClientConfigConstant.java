package xyz.pplax.auth.constant;

import java.util.Arrays;
import java.util.List;

/**
 * Oauth配置的constant
 */
public class OauthClientConfigConstant {
    /** 认证中心支持的授权类型 **/
    public static final List<String> AUTHORIZED_GRANT_TYPES =
            Arrays.asList("authorization_code","client_credentials","implicit","refresh_token","password");

}
