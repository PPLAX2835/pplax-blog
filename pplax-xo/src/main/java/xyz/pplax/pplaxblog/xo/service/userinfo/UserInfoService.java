package xyz.pplax.pplaxblog.xo.service.userinfo;

import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.entity.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 用户 服务类
 */
public interface UserInfoService extends SuperService<UserInfo> {

    UserInfo getByUserUid(String userUid);

}
