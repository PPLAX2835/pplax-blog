package xyz.pplax.pplaxblog.auth.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.base.exception.EmailUnactivatedException;
import xyz.pplax.pplaxblog.base.exception.MobileUnactivatedException;
import xyz.pplax.pplaxblog.base.exception.UsernameNullException;
import xyz.pplax.pplaxblog.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.entity.Role;
import xyz.pplax.pplaxblog.xo.entity.User;
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
            throw new UsernameNullException("用户名为空");
        }

        // 邮箱的正则
        String emailRegex = "[a-zA-Z0-9_]+@[a-zA-Z0-9]+(\\.[a-zA-Z]+)+";
        // 手机的正则
        String mobileRegex = "0?(13|14|15|18|17)[0-9]{9}";

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        User user = null;
        if (Pattern.matches(emailRegex, username)) {
            // 根据邮箱查询
            userQueryWrapper.eq("email", username);
            user = userMapper.selectOne(userQueryWrapper);

            if (user.getIsEmailActivated() == null || !user.getIsEmailActivated()) {
                throw new EmailUnactivatedException("邮箱未激活");
            }
        } else if (Pattern.matches(mobileRegex, username)) {
            // 根据手机查询
            userQueryWrapper.eq("mobile", username);
            user = userMapper.selectOne(userQueryWrapper);

            if (user.getIsMobileActivated() == null || !user.getIsMobileActivated()) {
                throw new MobileUnactivatedException("手机未激活");
            }
        } else {
            // 根据用户名查询
            userQueryWrapper.eq("username", username);
            user = userMapper.selectOne(userQueryWrapper);
        }

        // 添加角色
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("uid", user.getRoleUid());
        Role role = roleMapper.selectOne(roleQueryWrapper);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getRoleName()));

        // 密码得携带盐过去
        Map<String, String> map = new HashMap<>();
        map.put("password", user.getPassword());
        map.put("salt", user.getSalt());

        // 返回
        return new org.springframework.security.core.userdetails.User(user.getUsername(), JSON.toJSONString(map), authorities);
    }

}
