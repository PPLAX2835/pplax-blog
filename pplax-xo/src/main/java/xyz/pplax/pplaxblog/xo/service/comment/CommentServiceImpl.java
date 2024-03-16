package xyz.pplax.pplaxblog.xo.service.comment;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.BlogSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.CommentSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.list.BlogGetListDto;
import xyz.pplax.pplaxblog.xo.dto.list.CommentGetListDto;
import xyz.pplax.pplaxblog.xo.dto.list.UserGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.Comment;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.entity.UserInfo;
import xyz.pplax.pplaxblog.xo.mapper.CommentMapper;
import xyz.pplax.pplaxblog.xo.service.blog.BlogService;
import xyz.pplax.pplaxblog.xo.service.user.UserService;
import xyz.pplax.pplaxblog.xo.service.userinfo.UserInfoService;

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

    @Override
    public IPage<Comment> list(CommentGetListDto commentGetListDto) {
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(commentGetListDto.getNickname())) {
            UserGetListDto userGetListDto = new UserGetListDto();
            userGetListDto.setNickname(commentGetListDto.getNickname());
            userGetListDto.setCurrentPage(commentGetListDto.getCurrentPage());
            userGetListDto.setPageSize(commentGetListDto.getPageSize());
            List<User> userList = userService.listByNicknameAndUsername(userGetListDto);

            List<String> userUidList = new ArrayList<>();
            for (User user : userList) {
                userUidList.add(user.getUid());
            }
            if (userUidList.size() > 0) {
                commentQueryWrapper.in(CommentSQLConstants.USER_UID, userUidList);
            }
        }
        if (!StringUtils.isEmpty(commentGetListDto.getKeyword())) {
            commentQueryWrapper.like(CommentSQLConstants.CONTENT, "%" + commentGetListDto.getKeyword() + "%");
        }
        if (commentGetListDto.getType() != null) {
            commentQueryWrapper.eq(CommentSQLConstants.TYPE, commentGetListDto.getType());
        }

        //分页
        Page<Comment> page = new Page<>();
        page.setCurrent(commentGetListDto.getCurrentPage());
        page.setSize(commentGetListDto.getPageSize());

        // 获得非删除状态的
        commentQueryWrapper.ne(CommentSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());

        IPage<Comment> pageList = null;

        // 按创建时间排序
        commentQueryWrapper.orderByDesc(CommentSQLConstants.C_CREATE_TIME);

        pageList = page(page, commentQueryWrapper);

        List<Comment> commentList = new ArrayList<>();
        for (Comment comment : pageList.getRecords()) {
            // 封装所属博客
            Blog blog = blogService.getById(comment.getBlogUid());
            comment.setBlog(blog);

            // 封装评论人
            User commentator = userService.getById(comment.getUserUid());
            if (commentator != null) {
                UserInfo commentatorUserInfo = userInfoService.getById(commentator.getUserInfoUid());
                commentator.setUserInfo(commentatorUserInfo);
                // 脱敏
                commentator.sensitiveDataRemove();
            }
            comment.setCommentator(commentator);

            // 封装被评论人
            User targetUser = userService.getById(comment.getToUserUid());
            if (targetUser != null) {
                UserInfo targetUserUserInfo = userInfoService.getById(targetUser.getUserInfoUid());
                targetUser.setUserInfo(targetUserUserInfo);
                // 脱敏
                targetUser.sensitiveDataRemove();
            }
            comment.setTargetUser(targetUser);

            commentList.add(comment);
        }

        pageList.setRecords(commentList);

        return pageList;
    }
}
