package xyz.pplax.pplaxblog.xo.service.userinfo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.pplax.pplaxblog.commons.constants.BaseSQLConstants;
import xyz.pplax.pplaxblog.commons.utils.JwtUtil;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.UserInfoSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.UserInfoEditDto;
import xyz.pplax.pplaxblog.xo.entity.FileStorage;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.entity.UserInfo;
import xyz.pplax.pplaxblog.xo.mapper.UserInfoMapper;
import xyz.pplax.pplaxblog.xo.service.filestorage.FileStorageService;
import xyz.pplax.pplaxblog.xo.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.UUID;

/**
 * 用户表 服务实现类
 */
@Service
public class UserInfoServiceImpl extends SuperServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserService userService;

    @Override
    public UserInfo getById(Serializable id) {
        UserInfo userInfo = super.getById(id);

        // 封装头像
        FileStorage avatar = fileStorageService.getById(userInfo.getAvatarPictureUid());
        userInfo.setAvatar(avatar);

        return userInfo;
    }

    @Override
    public UserInfo getByUserUid(String userUid) {
        User user = userService.getById(userUid);
        return getById(user.getUserInfoUid());
    }

    @Override
    public Boolean updateByUserUid(String userUid, UserInfoEditDto userInfoEditDto) {
        User user = userService.getById(userUid);
        UserInfo userInfo = getByUserUid(userUid);

        Boolean res = true;

        // 封装
        int flag = 0;
        // 用户
        if (!StringUtils.isEmpty(userInfoEditDto.getUsername())) {
            // 用户名
            user.setUsername(userInfoEditDto.getUsername());
            flag++;
        }
        if (!StringUtils.isEmpty(userInfoEditDto.getRoleUid())) {
            // 角色uid
            user.setRoleUid(userInfoEditDto.getRoleUid());
            flag++;
        }
        if (userInfoEditDto.getStatus() != null) {
            // 状态
            user.setStatus(userInfoEditDto.getStatus());
            flag++;
        }

        if (flag != 0) {
            res = res && userService.updateById(user);
            flag = 0;
        }

        // 用户信息
        if (!StringUtils.isEmpty(userInfoEditDto.getAvatarPictureUid())) {
            // 头像文件uid
            userInfo.setAvatarPictureUid(userInfoEditDto.getAvatarPictureUid());
            flag++;
        }
        if (!StringUtils.isEmpty(userInfoEditDto.getNickname())) {
            // 昵称
            userInfo.setNickname(userInfoEditDto.getNickname());
            flag++;
        }
        if (userInfoEditDto.getBirthday() != null) {
            // 生日
            userInfo.setBirthday(userInfoEditDto.getBirthday());
            flag++;
        }
        if (!StringUtils.isEmpty(userInfoEditDto.getSummary())) {
            // 个性签名
            userInfo.setSummary(userInfoEditDto.getSummary());
            flag++;
        }

        if (flag != 0) {
            res = res && updateById(userInfo);
        }

        return res;
    }


    /**
     * 添加用户，因为需要同时生成两个表，这里用了事务
     * @param userInfoEditDto
     * @return
     */
    @Override
    @Transactional
    public Boolean save(UserInfoEditDto userInfoEditDto) {
        User user = new User();
        UserInfo userInfo = new UserInfo();

        // 生成uuid
        user.setUid(StringUtils.getUUID());
        userInfo.setUid(StringUtils.getUUID());
        user.setUserInfoUid(userInfo.getUid());

        System.out.println(JSON.toJSONString(user));
        System.out.println(JSON.toJSONString(userInfo));

        // 封装
        if (StringUtils.isEmpty(userInfoEditDto.getUsername())) {
            // 用户名非空
            return false;
        }
        if (userInfoEditDto.getUsername().length() < 3 || userInfoEditDto.getUsername().length() > 30) {
            // 用户名在 [3, 30] 内
            return false;
        }
        user.setUsername(userInfoEditDto.getUsername());

        if (StringUtils.isEmpty(userInfoEditDto.getPassword())) {
            // 密码非空
            return false;
        }
        // 生成加密盐
        user.setSalt(StringUtils.getRandomString(36));
        user.setPassword(passwordEncoder.encode(userInfoEditDto.getPassword() + user.getSalt()));

        if (userInfoEditDto.getStatus() == null) {
            // 状态
            return false;
        }
        user.setStatus(userInfoEditDto.getStatus());

        if (StringUtils.isEmpty(userInfoEditDto.getRoleUid())) {
            return false;
        }
        user.setRoleUid(userInfoEditDto.getRoleUid());

        if (StringUtils.isEmpty(userInfoEditDto.getNickname())) {
            // 昵称
            return false;
        }
        userInfo.setNickname(userInfoEditDto.getNickname());
        if (!StringUtils.isEmpty(userInfoEditDto.getSummary())) {
            userInfo.setSummary(userInfoEditDto.getSummary());
        }

        boolean res1 = userService.save(user);
        boolean res2 = save(userInfo);

        if (!(res1 && res2)) {
            throw new RuntimeException();
        }

        return true;
    }
}
