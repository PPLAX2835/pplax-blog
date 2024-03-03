package xyz.pplax.pplaxblog.xo.service.user;

import xyz.pplax.pplaxblog.xo.base.dto.PageDto;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.entity.Role;
import xyz.pplax.pplaxblog.xo.entity.User;

import java.util.List;

/**
 * 用户 服务类
 */
public interface UserService extends SuperService<User> {

    public Role getRoleWithMenu(String userUid);

    public List<User> listByNickName(PageDto pageDto);

}
