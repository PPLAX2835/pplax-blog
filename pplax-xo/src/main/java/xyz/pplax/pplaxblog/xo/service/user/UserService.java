package xyz.pplax.pplaxblog.xo.service.user;

import com.sun.org.apache.xpath.internal.operations.Bool;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.dto.PageDto;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.UserInfoEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.UserGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Role;
import xyz.pplax.pplaxblog.xo.entity.User;

import java.util.Collection;
import java.util.List;

/**
 * 用户 服务类
 */
public interface UserService extends SuperService<User> {

    Role getRoleWithMenu(String userUid);

    List<User> listByNicknameAndUsername(UserGetListDto userGetListDto);

    Long getCountByNicknameAndUsername(UserGetListDto userGetListDto);

    Boolean isUsernameExist(String username);

    ResponseResult removeById(String userUid);

    ResponseResult removeByIds(List<String> userUidList);

    Boolean save(UserInfoEditDto userInfoEditDto);
}
