package xyz.pplax.pplaxblog.xo.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.dto.PageDto;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.UserSQLConstants;
import xyz.pplax.pplaxblog.xo.entity.Role;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.entity.UserInfo;
import xyz.pplax.pplaxblog.xo.mapper.UserMapper;
import xyz.pplax.pplaxblog.xo.service.role.RoleService;
import xyz.pplax.pplaxblog.xo.service.userinfo.UserInfoService;

import java.util.List;

/**
 * 用户表 服务实现类
 */
@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements UserService {

    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 获取用户的角色，包含菜单
     * @param userUid
     * @return
     */
    @Override
    public Role getRoleWithMenu(String userUid) {
        User user = getById(userUid);
        if (user == null) {
            log.warn("没有获取到用户信息");
            return null;
        }
        Role role = roleService.getById(user.getRoleUid());
        return roleService.setMenu(role);
    }


    @Override
    public List<User> listByNickname(PageDto pageDto) {
        pageDto.paginationCalculate();
        List<User> userList = userMapper.selectListByNickName(pageDto);

        for (User user : userList) {
            // 封装用户信息
            user.setUserInfo(userInfoService.getById(user.getUserInfoUid()));
            // 封装用户角色
            user.setRole(roleService.getById(user.getRoleUid()));

            // 脱敏
            user.sensitiveDataRemove();
        }

        return userList;
    }

    @Override
    public Long getCountByNickname(String nickname) {
        return userMapper.selectCountByNickName(nickname);
    }
}
