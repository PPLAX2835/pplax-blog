package xyz.pplax.pplaxblog.xo.mapper;

import xyz.pplax.pplaxblog.xo.base.dto.PageDto;
import xyz.pplax.pplaxblog.xo.base.mapper.SuperMapper;
import xyz.pplax.pplaxblog.xo.entity.User;

import java.util.List;

/**
 * 用户表 Mapper 接口
 */
public interface UserMapper extends SuperMapper<User> {

    List<User> selectListByNickName(PageDto pageDto);

    Long selectCountByNickName(String nickname);
}
