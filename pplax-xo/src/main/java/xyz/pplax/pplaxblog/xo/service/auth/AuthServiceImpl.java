package xyz.pplax.pplaxblog.xo.service.auth;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.constants.BaseSysConstants;
import xyz.pplax.pplaxblog.commons.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.commons.utils.IpUtils;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.feign.auth.AuthFeignClient;
import xyz.pplax.pplaxblog.xo.constants.UserSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.LoginDto;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.mapper.UserMapper;
import xyz.pplax.pplaxblog.xo.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 认证 服务实现类
 */
@Service
public class AuthServiceImpl extends SuperServiceImpl<UserMapper, User> implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthFeignClient authFeignClient;

    @Value("${pplax.oauth.client-id}")
    private String clientId;

    @Value("${pplax.oauth.client-secret}")
    private String clientSecret;

    public Map<String, String> getToken(HttpServletRequest httpServletRequest, LoginDto loginDto) {
        Map<String, String> map = JSONObject.parseObject(
                authFeignClient.getToken(
                        clientId,
                        clientSecret,
                        BaseSysConstants.PASSWORD,
                        loginDto.getUsername(),
                        loginDto.getPassword()
                ),
                new TypeReference<Map<String, String>>() {
                }
        );

        if (!StringUtils.isEmpty(map.get("access_token"))) {
            // 获取token成功，进行登录信息的储存

            // 记录登录ip、登录次数、登录时间
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq(UserSQLConstants.USERNAME, loginDto.getUsername());

            // 修改登录信息
            User user = userService.getOne(userQueryWrapper);
            user.setLastLoginTime(new Date());
            user.setLastLoginIp(IpUtils.getIpAddress(httpServletRequest));
            user.setLoginCount(user.getLoginCount() + 1);

            // 更新
            userService.updateById(user);

            // 返回结果脱敏
            map.remove(BaseSysConstants.UID);
            map.remove(BaseSysConstants.USER_INFO_UID);
            map.remove(BaseSysConstants.SALT);

            // 返回结果
            return map;
        } else {
            return null;
        }
    }
}
