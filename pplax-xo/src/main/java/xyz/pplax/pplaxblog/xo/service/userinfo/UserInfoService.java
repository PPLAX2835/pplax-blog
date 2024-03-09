package xyz.pplax.pplaxblog.xo.service.userinfo;

import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.UserInfoEditDto;
import xyz.pplax.pplaxblog.xo.entity.UserInfo;

/**
 * 用户 服务类
 */
public interface UserInfoService extends SuperService<UserInfo> {

    UserInfo getByUserUid(String userUid);

    Boolean updateByUserUid(String userUid, UserInfoEditDto userInfoEditDto);

}
