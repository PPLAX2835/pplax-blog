package xyz.pplax.pplaxblog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="auth-server")
public interface AuthFeignClient {

    /**
     * 对认证服务的/oauth/token接口调用
     * @param client_id
     * @param client_secret
     * @param grant_type
     * @param password
     * @param username
     * @return
     */
    @PostMapping("/oauth/token")
    public String getToken(
            @RequestParam("client_id") String client_id,
            @RequestParam("client_secret") String client_secret,
            @RequestParam("grant_type") String grant_type,
            @RequestParam("username") String username,
            @RequestParam("password") String password
    );


    @DeleteMapping("/oauth/access_token")
    public String deleteToken(@RequestParam("token") String token);

}
