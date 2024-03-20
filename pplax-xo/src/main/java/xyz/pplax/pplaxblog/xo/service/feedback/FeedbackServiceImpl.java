package xyz.pplax.pplaxblog.xo.service.feedback;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.CommentSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.FeedBackSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.edit.FeedbackEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.FeedbackGetListDto;
import xyz.pplax.pplaxblog.xo.entity.*;
import xyz.pplax.pplaxblog.xo.mapper.FeedbackMapper;
import xyz.pplax.pplaxblog.xo.service.filestorage.FileStorageService;
import xyz.pplax.pplaxblog.xo.service.user.UserService;
import xyz.pplax.pplaxblog.xo.service.userinfo.UserInfoService;

import java.util.ArrayList;
import java.util.List;

/**
 * 反馈表 服务实现类
 */
@Service
public class FeedbackServiceImpl extends SuperServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private FileStorageService fileStorageService;


    @Override
    public IPage<Feedback> list(FeedbackGetListDto feedbackGetListDto) {
        QueryWrapper<Feedback> feedbackQueryWrapper = new QueryWrapper<>();

        if (feedbackGetListDto.getType() != null) {
            feedbackQueryWrapper.eq(FeedBackSQLConstants.TYPE, feedbackGetListDto.getType());
        }

        if (feedbackGetListDto.getStatus() != null) {
            feedbackQueryWrapper.eq(FeedBackSQLConstants.STATUS, feedbackGetListDto.getStatus());
        }

        //分页
        Page<Feedback> page = new Page<>();
        page.setCurrent(feedbackGetListDto.getCurrentPage());
        page.setSize(feedbackGetListDto.getPageSize());

        // 获得非删除状态的
        feedbackQueryWrapper.ne(FeedBackSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());

        IPage<Feedback> pageList = null;

        // 按创建时间排序
        feedbackQueryWrapper.orderByDesc(FeedBackSQLConstants.C_CREATE_TIME);

        pageList = page(page, feedbackQueryWrapper);

        List<Feedback> feedbackList = new ArrayList<>();
        for (Feedback feedback : pageList.getRecords()) {
            // 封装反馈人
            User user = userService.getById(feedback.getUserUid());
            if (user != null) {
                user.setUserInfo(userInfoService.getById(user.getUserInfoUid()));
                user.sensitiveDataRemove();
            }
            feedback.setUser(user);

            // 封装附件图
            FileStorage fileStorage = fileStorageService.getById(feedback.getPictureUid());
            feedback.setPicture(fileStorage);

            feedbackList.add(feedback);
        }
        pageList.setRecords(feedbackList);

        return pageList;
    }

    @Override
    public Boolean updateById(FeedbackEditDto feedbackEditDto) {
        Feedback feedback = new Feedback();
        feedback.setUid(feedbackEditDto.getUid());
        feedback.setStatus(feedback.getStatus());
        feedback.setType(feedback.getType());

        return super.updateById(feedback);
    }
}
