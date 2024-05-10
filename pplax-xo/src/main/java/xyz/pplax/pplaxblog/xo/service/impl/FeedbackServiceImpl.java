package xyz.pplax.pplaxblog.xo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.constants.sql.FeedBackSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.edit.FeedbackEditDto;
import xyz.pplax.pplaxblog.xo.entity.*;
import xyz.pplax.pplaxblog.xo.mapper.FeedbackMapper;
import xyz.pplax.pplaxblog.xo.service.FeedbackService;
import xyz.pplax.pplaxblog.xo.service.FileStorageService;
import xyz.pplax.pplaxblog.xo.service.UserInfoService;
import xyz.pplax.pplaxblog.xo.service.UserService;

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
    public Page<Feedback> page(Integer type, Integer status, Long currentPage, Long pageSize) {
        PQueryWrapper<Feedback> feedbackPQueryWrapper = new PQueryWrapper<>();

        if (type != null) {
            feedbackPQueryWrapper.eq(FeedBackSQLConstants.TYPE, type);
        }

        if (status != null) {
            feedbackPQueryWrapper.eq(FeedBackSQLConstants.STATUS, status);
        }

        //分页
        Page<Feedback> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        Page<Feedback> pageList = null;

        // 按创建时间排序
        feedbackPQueryWrapper.orderByDesc(FeedBackSQLConstants.C_CREATE_TIME);

        pageList = page(page, feedbackPQueryWrapper);

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

        }

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
