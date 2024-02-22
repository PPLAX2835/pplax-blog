package xyz.pplax.pplaxblog.xo.service.userinfo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.constants.BaseSQLConstants;
import xyz.pplax.pplaxblog.commons.utils.JwtUtil;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.UserInfoSQLConstants;
import xyz.pplax.pplaxblog.xo.entity.FileStorage;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.entity.UserInfo;
import xyz.pplax.pplaxblog.xo.mapper.UserInfoMapper;
import xyz.pplax.pplaxblog.xo.service.filestorage.FileStorageService;
import xyz.pplax.pplaxblog.xo.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 用户表 服务实现类
 */
@Service
public class UserInfoServiceImpl extends SuperServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

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
}
