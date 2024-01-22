package xyz.pplax.pplaxblog.auth.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.auth.exception.UsernameNullException;
import xyz.pplax.pplaxblog.commons.base.global.BaseMessageConf;
import xyz.pplax.pplaxblog.commons.base.global.BaseRegexConf;
import xyz.pplax.pplaxblog.commons.base.global.BaseSQLConf;
import xyz.pplax.pplaxblog.commons.base.global.BaseSysConf;
import xyz.pplax.pplaxblog.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.entity.Role;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.global.UserSQLConf;
import xyz.pplax.pplaxblog.xo.mapper.RoleMapper;
import xyz.pplax.pplaxblog.xo.mapper.UserMapper;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class UserDetailService  implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private xyz.pplax.pplaxblog.xo.service.UserService userService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNullException(BaseMessageConf.USERNAME_IS_NULL);
        }

        // 邮箱的正则
        String emailRegex = BaseRegexConf.EMAIL_REGEX;
        // 手机的正则
        String mobileRegex = BaseRegexConf.MOBILE_REGEX;

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        User user = null;
        if (Pattern.matches(emailRegex, username)) {
            // 根据邮箱查询
            userQueryWrapper.eq(UserSQLConf.EMAIL, username);
            user = userMapper.selectOne(userQueryWrapper);

            if (user.getIsEmailActivated() == null || !user.getIsEmailActivated()) {
                return new org.springframework.security.core.userdetails.User(BaseMessageConf.EMAIL_UNACTIVATED, BaseMessageConf.EMAIL_UNACTIVATED, new ArrayList<>());
            }
        } else if (Pattern.matches(mobileRegex, username)) {
            // 根据手机查询
            userQueryWrapper.eq(UserSQLConf.MOBILE, username);
            user = userMapper.selectOne(userQueryWrapper);

            if (user.getIsMobileActivated() == null || !user.getIsMobileActivated()) {
                return new org.springframework.security.core.userdetails.User(BaseMessageConf.MOBILE_UNACTIVATED, BaseMessageConf.MOBILE_UNACTIVATED, new ArrayList<>());
            }
        } else {
            // 根据用户名查询
            userQueryWrapper.eq(UserSQLConf.USERNAME, username);
            user = userMapper.selectOne(userQueryWrapper);
        }

        if (user == null) {
            return new org.springframework.security.core.userdetails.User(BaseMessageConf.ACCOUNT_IS_NOT_REGISTERED, BaseMessageConf.ACCOUNT_IS_NOT_REGISTERED, new ArrayList<>());
        }

        // 添加角色
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq(BaseSQLConf.UID, user.getRoleUid());
        Role role = roleMapper.selectOne(roleQueryWrapper);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getRoleName()));

        // 密码得携带盐过去
        Map<String, String> map = new HashMap<>();
        map.put(BaseSysConf.PASSWORD, user.getPassword());
        map.put(BaseSysConf.SALT, user.getSalt());

        // 返回
        return new org.springframework.security.core.userdetails.User(user.getUsername(), JSON.toJSONString(map), authorities);
    }

}
