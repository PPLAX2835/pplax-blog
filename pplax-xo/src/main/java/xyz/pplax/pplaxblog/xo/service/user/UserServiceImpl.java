package xyz.pplax.pplaxblog.xo.service.user;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.exception.DeleteFailException;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.*;
import xyz.pplax.pplaxblog.xo.dto.edit.UserInfoEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.UserGetListDto;
import xyz.pplax.pplaxblog.xo.entity.*;
import xyz.pplax.pplaxblog.xo.mapper.UserMapper;
import xyz.pplax.pplaxblog.xo.service.blog.BlogService;
import xyz.pplax.pplaxblog.xo.service.collect.CollectService;
import xyz.pplax.pplaxblog.xo.service.comment.CommentService;
import xyz.pplax.pplaxblog.xo.service.feedback.FeedbackService;
import xyz.pplax.pplaxblog.xo.service.filestorage.FileStorageService;
import xyz.pplax.pplaxblog.xo.service.role.RoleService;
import xyz.pplax.pplaxblog.xo.service.userinfo.UserInfoService;

import java.util.List;
import java.util.Objects;

/**
 * 用户表 服务实现类
 */
@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements UserService {

    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CollectService collectService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FileStorageService fileStorageService;

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

    /**
     * 通过昵称获得列表 分页
     * @param pageDto
     * @return
     */
    @Override
    public List<User> listByNicknameAndUsername(UserGetListDto userGetListDto) {
        userGetListDto.paginationCalculate();
        List<User> userList = userMapper.selectListByNicknameAndUsername(userGetListDto);

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

    /**
     * 通过昵称查询数量 （模糊查询）
     * @param userGetListDto
     * @return
     */
    @Override
    public Long countByNicknameAndUsername(UserGetListDto userGetListDto) {
        return userMapper.selectCountByNicknameAndUsername(userGetListDto);
    }

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    @Override
    public Boolean isUsernameExist(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq(UserSQLConstants.USERNAME, username);
        return count(userQueryWrapper) > 0;
    }

    /**
     * 删除用户 (逻辑)
     * @param userUid
     * @return
     */
    @Override
    @Transactional
    public ResponseResult removeById(String userUid) {
        User user = getById(userUid);

        // 检查该用户下是否还有别的数据
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.eq(BlogSQLConstants.USER_UID, user.getUid());
        blogQueryWrapper.ne(BlogSQLConstants.STATUS, EStatus.DISABLED.getStatus());
        int blogCount = blogService.count(blogQueryWrapper);
        if (blogCount > 0) {
            // 还有博客，无法删除
            return new ResponseResult(HttpStatus.BLOG_UNDER_THIS_USER);
        }

        QueryWrapper<Collect> collectQueryWrapper = new QueryWrapper<>();
        collectQueryWrapper.eq(CollectSQLConstants.USER_UID, user.getUid());
        collectQueryWrapper.ne(CollectSQLConstants.STATUS, EStatus.DISABLED.getStatus());
        int collectCount = collectService.count(collectQueryWrapper);
        if (collectCount > 0) {
            // 收藏还有东西，无法删除
            return new ResponseResult(HttpStatus.COLLECTION_UNDER_THIS_USER);
        }

        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq(CommentSQLConstants.USER_UID, user.getUid());
        commentQueryWrapper.ne(CommentSQLConstants.STATUS, EStatus.DISABLED.getStatus());
        int commentCount = commentService.count(commentQueryWrapper);
        if (commentCount > 0) {
            // 还有评论
            return new ResponseResult(HttpStatus.COMMENT_UNDER_THIS_USER);
        }

        QueryWrapper<Feedback> feedbackQueryWrapper = new QueryWrapper<>();
        feedbackQueryWrapper.eq(FeedBackSQLConstants.USER_UID, user.getUid());
        feedbackQueryWrapper.ne(FeedBackSQLConstants.STATUS, EStatus.DISABLED.getStatus());
        int feedbackCount = feedbackService.count(feedbackQueryWrapper);
        if (feedbackCount > 0) {
            // 还有反馈
            return new ResponseResult(HttpStatus.FEEDBACK_UNDER_THIS_USER);
        }

        QueryWrapper<FileStorage> fileStorageQueryWrapper = new QueryWrapper<>();
        fileStorageQueryWrapper.eq(FileStorageSQLConstants.USER_UID, user.getUid());
        fileStorageQueryWrapper.ne(FileStorageSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        int fileStorageCount = fileStorageService.count(fileStorageQueryWrapper);
        if (fileStorageCount > 0) {
            // 还有文件
            return new ResponseResult(HttpStatus.FILE_STORAGE_UNDER_THIS_USER);
        }
        
        boolean res1 = super.removeById(userUid);
        boolean res2 = userInfoService.removeById(user.getUserInfoUid());

        // 如果有一个删除失败就回滚业务
        if (!(res1 && res2)) {
            throw new RuntimeException();
        }

        return ResponseResult.success(HttpStatus.DELETE_SUCCESS);
    }

    /**
     * 批量删除用户
     * @param userUidList
     * @return
     */
    @Override
    @Transactional
    public ResponseResult removeByIds(List<String> userUidList) {

        List<User> userList = listByIds(userUidList);

        for (User user : userList) {
            // 批量删除出问题就回滚
            ResponseResult responseResult = removeById(user.getUid());
            if (!Objects.equals(responseResult.getCode(), HttpStatus.OK.getCode())) {
                throw new DeleteFailException(responseResult.getMessage());
            }
        }

        return ResponseResult.success(HttpStatus.DELETE_SUCCESS);
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

        boolean res1 = save(user);
        boolean res2 = userInfoService.save(userInfo);

        // 如果有一个更新失败就回滚业务
        if (!(res1 && res2)) {
            throw new RuntimeException();
        }

        return true;
    }
}
