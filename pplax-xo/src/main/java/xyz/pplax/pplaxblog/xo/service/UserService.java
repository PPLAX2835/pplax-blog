package xyz.pplax.pplaxblog.xo.service;

import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.UserInfoEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.UserGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Role;
import xyz.pplax.pplaxblog.xo.entity.User;

import java.util.List;

/**
 * 用户 服务类
 */
public interface UserService extends SuperService<User> {

    Role getRoleWithMenu(String userUid);

    List<User> listByNicknameAndUsername(UserGetListDto userGetListDto);

    Long countByNicknameAndUsername(UserGetListDto userGetListDto);

    Boolean isUsernameExist(String username);

    Boolean removeById(String userUid);

    Boolean removeByIds(List<String> userUidList);

    Boolean save(UserInfoEditDto userInfoEditDto);
}
