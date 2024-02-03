package xyz.pplax.pplaxblog.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.starter.security.exception.UsernameNullException;
import xyz.pplax.pplaxblog.starter.security.model.SecurityUserDetails;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.constants.BaseRegexConstants;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.constants.UserSQLConstants;
import xyz.pplax.pplaxblog.xo.mapper.RoleMapper;
import xyz.pplax.pplaxblog.xo.mapper.UserInfoMapper;
import xyz.pplax.pplaxblog.xo.mapper.UserMapper;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class UserDetailService  implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private xyz.pplax.pplaxblog.xo.service.UserService userService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNullException(HttpStatus.USERNAME_IS_NULL.getMessage());
        }

        // 邮箱的正则
        String emailRegex = BaseRegexConstants.EMAIL_REGEX;
        // 手机的正则
        String mobileRegex = BaseRegexConstants.MOBILE_REGEX;

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        User user = null;
        if (Pattern.matches(emailRegex, username)) {
            // 根据邮箱查询
            userQueryWrapper.eq(UserSQLConstants.EMAIL, username);
            user = userMapper.selectOne(userQueryWrapper);

            if (user.getIsEmailActivated() == null || !user.getIsEmailActivated()) {
                return new SecurityUserDetails(HttpStatus.EMAIL_UNACTIVATED.getMessage(), HttpStatus.EMAIL_UNACTIVATED.getMessage(), new ArrayList<>());
            }
        } else if (Pattern.matches(mobileRegex, username)) {
            // 根据手机查询
            userQueryWrapper.eq(UserSQLConstants.MOBILE, username);
            user = userMapper.selectOne(userQueryWrapper);

            if (user.getIsMobileActivated() == null || !user.getIsMobileActivated()) {
                return new SecurityUserDetails(HttpStatus.MOBILE_UNACTIVATED.getMessage(), HttpStatus.MOBILE_UNACTIVATED.getMessage(), new ArrayList<>());
            }
        } else {
            // 根据用户名查询
            userQueryWrapper.eq(UserSQLConstants.USERNAME, username);
            user = userMapper.selectOne(userQueryWrapper);
        }

        if (user == null) {
            return new SecurityUserDetails(HttpStatus.ACCOUNT_IS_NOT_REGISTERED.getMessage(), HttpStatus.ACCOUNT_IS_NOT_REGISTERED.getMessage(), new ArrayList<>());
        }

        // 将该用户所拥有的角色uid放入集合中
        String[] roleUidArray = user.getRoleUid().split(",");
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String roleUid : roleUidArray) {
            authorities.add(new SimpleGrantedAuthority(roleUid));
        }

        // 添加点查询用户信息会用到的认证信息
        SecurityUserDetails securityUserDetails = new SecurityUserDetails(user.getUsername(), user.getPassword(), authorities);
        securityUserDetails.setUid(user.getUid());      // 用户uid
        securityUserDetails.setSalt(user.getSalt());    // 加密盐

        // 添加用户信息uid
        securityUserDetails.setUserInfoUid(user.getUserInfoUid());

        // 返回
        return securityUserDetails;
    }

}
