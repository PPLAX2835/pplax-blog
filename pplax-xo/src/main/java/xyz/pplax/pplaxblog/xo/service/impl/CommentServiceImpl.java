package xyz.pplax.pplaxblog.xo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.constants.sql.CommentSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.type.CommentTypeConstants;
import xyz.pplax.pplaxblog.xo.dto.list.UserGetListDto;
import xyz.pplax.pplaxblog.xo.entity.*;
import xyz.pplax.pplaxblog.xo.mapper.CommentMapper;
import xyz.pplax.pplaxblog.xo.service.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论表 服务实现类
 */
@Service
public class CommentServiceImpl extends SuperServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private SayService sayService;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public Page<Comment> page(String keyword, String nickname, Integer type, String originalUid, Long currentPage, Long pageSize) {
        PQueryWrapper<Comment> commentPQueryWrapper = new PQueryWrapper<>();

        if (!StringUtils.isEmpty(nickname)) {
            UserGetListDto userGetListDto = new UserGetListDto();
            userGetListDto.setNickname(nickname);
            userGetListDto.setCurrentPage(currentPage);
            userGetListDto.setPageSize(currentPage);
            List<User> userList = userService.listByNicknameAndUsername(userGetListDto);

            List<String> userUidList = new ArrayList<>();
            for (User user : userList) {
                userUidList.add(user.getUid());
            }
            if (userUidList.size() > 0) {
                commentPQueryWrapper.in(CommentSQLConstants.USER_UID, userUidList);
            }
        }
        if (!StringUtils.isEmpty(keyword)) {
            commentPQueryWrapper.like(CommentSQLConstants.CONTENT, "%" + keyword + "%");
        }
        if (type != null) {
            commentPQueryWrapper.eq(CommentSQLConstants.TYPE, type);
        }
        if (!StringUtils.isBlank(originalUid)) {
            commentPQueryWrapper.eq(CommentSQLConstants.ORIGINAL_UID, originalUid);
        }

        //分页
        Page<Comment> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        Page<Comment> pageList = null;

        // 按创建时间排序
        commentPQueryWrapper.orderByDesc(CommentSQLConstants.C_CREATE_TIME);

        pageList = page(page, commentPQueryWrapper);

        for (Comment comment : pageList.getRecords()) {
            // 封装所属原文
            if (comment.getType() == CommentTypeConstants.BLOG_COMMENT || comment.getType() == CommentTypeConstants.BLOG_LIKE) {
                // 属于博客
                Blog blog = blogService.getById(comment.getOriginalUid());
                comment.setBlog(blog);
            } else if (comment.getType() == CommentTypeConstants.SAY_LIKE || comment.getType() == CommentTypeConstants.COMMENT_REPLY) {
                // 属于说说
                Say say = sayService.getById(comment.getOriginalUid());
                comment.setSay(say);
            }

            // 封装评论人
            User commentator = userService.getById(comment.getUserUid());
            if (commentator != null) {
                UserInfo commentatorUserInfo = userInfoService.getById(commentator.getUserInfoUid());
                FileStorage commentatorAvatar = fileStorageService.getById(commentatorUserInfo.getAvatarPictureUid());
                commentatorUserInfo.setAvatar(commentatorAvatar);
                commentator.setUserInfo(commentatorUserInfo);
                // 脱敏
                commentator.sensitiveDataRemove();
            }
            comment.setCommentator(commentator);

            // 封装被评论人
            User targetUser = userService.getById(comment.getToUserUid());
            if (targetUser != null) {
                UserInfo targetUserUserInfo = userInfoService.getById(targetUser.getUserInfoUid());
                FileStorage commentatorAvatar = fileStorageService.getById(targetUserUserInfo.getAvatarPictureUid());
                targetUserUserInfo.setAvatar(commentatorAvatar);
                targetUser.setUserInfo(targetUserUserInfo);
                // 脱敏
                targetUser.sensitiveDataRemove();
            }
            comment.setTargetUser(targetUser);

        }

        return pageList;
    }

    @Override
    public List<Comment> listByOriginalUid(String originalUid, Integer type) {
        PQueryWrapper<Comment> commentPQueryWrapper = new PQueryWrapper<>();
        commentPQueryWrapper.eq(CommentSQLConstants.TYPE, type);
        commentPQueryWrapper.eq(CommentSQLConstants.ORIGINAL_UID, originalUid);

        List<Comment> commentList = list(commentPQueryWrapper);
        for (Comment comment : commentList) {
            // 封装评论人
            User commentator = userService.getById(comment.getUserUid());
            if (commentator != null) {
                UserInfo commentatorUserInfo = userInfoService.getById(commentator.getUserInfoUid());
                FileStorage commentatorAvatar = fileStorageService.getById(commentatorUserInfo.getAvatarPictureUid());
                commentatorUserInfo.setAvatar(commentatorAvatar);
                commentator.setUserInfo(commentatorUserInfo);
                // 脱敏
                commentator.sensitiveDataRemove();
            }
            comment.setCommentator(commentator);

            // 封装被评论人
            User targetUser = userService.getById(comment.getToUserUid());
            if (targetUser != null) {
                UserInfo targetUserUserInfo = userInfoService.getById(targetUser.getUserInfoUid());
                FileStorage commentatorAvatar = fileStorageService.getById(targetUserUserInfo.getAvatarPictureUid());
                targetUserUserInfo.setAvatar(commentatorAvatar);
                targetUser.setUserInfo(targetUserUserInfo);
                // 脱敏
                targetUser.sensitiveDataRemove();
            }
            comment.setTargetUser(targetUser);
        }

        return commentList;
    }

    @Override
    public Page<Comment> pageByOriginalUid(String originalUid, Integer type, Long currentPage, Long pageSize) {
        PQueryWrapper<Comment> commentPQueryWrapper = new PQueryWrapper<>();
        commentPQueryWrapper.eq(CommentSQLConstants.TYPE, type);
        commentPQueryWrapper.eq(CommentSQLConstants.ORIGINAL_UID, originalUid);
        commentPQueryWrapper.orderByAsc(CommentSQLConstants.C_CREATE_TIME);

        //分页
        Page<Comment> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        Page<Comment> commentIPage = page(page, commentPQueryWrapper);
        for (Comment comment : commentIPage.getRecords()) {
            // 封装评论人
            User commentator = userService.getById(comment.getUserUid());
            if (commentator != null) {
                UserInfo commentatorUserInfo = userInfoService.getById(commentator.getUserInfoUid());
                FileStorage commentatorAvatar = fileStorageService.getById(commentatorUserInfo.getAvatarPictureUid());
                commentatorUserInfo.setAvatar(commentatorAvatar);
                commentator.setUserInfo(commentatorUserInfo);
                // 脱敏
                commentator.sensitiveDataRemove();
            }
            comment.setCommentator(commentator);

            // 封装被评论人
            User targetUser = userService.getById(comment.getToUserUid());
            if (targetUser != null) {
                UserInfo targetUserUserInfo = userInfoService.getById(targetUser.getUserInfoUid());
                FileStorage commentatorAvatar = fileStorageService.getById(targetUserUserInfo.getAvatarPictureUid());
                targetUserUserInfo.setAvatar(commentatorAvatar);
                targetUser.setUserInfo(targetUserUserInfo);
                // 脱敏
                targetUser.sensitiveDataRemove();
            }
            comment.setTargetUser(targetUser);
        }

        return commentIPage;
    }

    @Override
    public Page<Comment> pageByBlogUid(String blogUid, Integer type, Long currentPage, Long pageSize) {
        PQueryWrapper<Comment> commentPQueryWrapper = new PQueryWrapper<>();
        commentPQueryWrapper.eq(CommentSQLConstants.TYPE, type);
        commentPQueryWrapper.eq(CommentSQLConstants.ORIGINAL_UID, blogUid);

        //分页
        Page<Comment> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        Page<Comment> commentIPage = page(page, commentPQueryWrapper);
        for (Comment comment : commentIPage.getRecords()) {
            // 封装子评论
            Page<Comment> childrenIPage = pageByOriginalUid(comment.getUid(), CommentTypeConstants.COMMENT_REPLY, 1L, 4L);
            comment.setChildrenTotal(childrenIPage.getTotal());
            comment.setChildren(childrenIPage.getRecords());

            // 封装评论人
            User commentator = userService.getById(comment.getUserUid());
            if (commentator != null) {
                UserInfo commentatorUserInfo = userInfoService.getById(commentator.getUserInfoUid());
                FileStorage commentatorAvatar = fileStorageService.getById(commentatorUserInfo.getAvatarPictureUid());
                commentatorUserInfo.setAvatar(commentatorAvatar);
                commentator.setUserInfo(commentatorUserInfo);
                // 脱敏
                commentator.sensitiveDataRemove();
            }
            comment.setCommentator(commentator);
        }

        return commentIPage;
    }

    @Override
    public Boolean like(String originalUid, String userUid, Integer type) {
        PQueryWrapper<Comment> commentPQueryWrapper = new PQueryWrapper<>();
        commentPQueryWrapper.eq(CommentSQLConstants.USER_UID, userUid);
        commentPQueryWrapper.eq(CommentSQLConstants.ORIGINAL_UID, originalUid);
        commentPQueryWrapper.eq(CommentSQLConstants.TYPE, type);

        Comment record = getOne(commentPQueryWrapper);           // 这里可能会在高并发点赞时出问题吧，以后要优化
        if (record == null) {
            Comment comment = new Comment();
            comment.setUid(StringUtils.getUUID());
            comment.setOriginalUid(originalUid);
            comment.setUserUid(userUid);
            comment.setType(type);

            return save(comment);
        }

        return removeById(record.getUid());
    }
}
