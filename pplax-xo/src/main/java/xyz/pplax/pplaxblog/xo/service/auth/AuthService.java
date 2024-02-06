package xyz.pplax.pplaxblog.xo.service.auth;

import xyz.pplax.pplaxblog.commons.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.LoginDto;
import xyz.pplax.pplaxblog.xo.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 认证 服务类
 */
public interface AuthService extends SuperService<User> {

    public Map<String, String> getToken(HttpServletRequest httpServletRequest, LoginDto loginDto);

}
