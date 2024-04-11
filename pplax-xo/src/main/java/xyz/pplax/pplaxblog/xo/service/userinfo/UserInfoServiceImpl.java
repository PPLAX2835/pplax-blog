package xyz.pplax.pplaxblog.xo.service.userinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.dto.edit.UserInfoEditDto;
import xyz.pplax.pplaxblog.xo.entity.FileStorage;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.entity.UserInfo;
import xyz.pplax.pplaxblog.xo.mapper.UserInfoMapper;
import xyz.pplax.pplaxblog.xo.service.filestorage.FileStorageService;
import xyz.pplax.pplaxblog.xo.service.user.UserService;

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

        // 封装背景图片
        FileStorage spaceBackgroundPicture = fileStorageService.getById(userInfo.getSpaceBackgroundPictureUid());
        userInfo.setSpaceBackgroundPicture(spaceBackgroundPicture);

        return userInfo;
    }

    @Override
    public UserInfo getByUserUid(String userUid) {
        User user = userService.getById(userUid);
        if (user != null) {
            return getById(user.getUserInfoUid());
        }
        return null;
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
        if (!StringUtils.isEmpty(userInfoEditDto.getSpaceBackgroundPictureUid())) {
            // 空间背景图文件uid
            userInfo.setSpaceBackgroundPictureUid(userInfoEditDto.getSpaceBackgroundPictureUid());
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


}
