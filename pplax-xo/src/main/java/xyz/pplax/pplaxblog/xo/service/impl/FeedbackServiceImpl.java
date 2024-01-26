package xyz.pplax.pplaxblog.xo.service.impl;

import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.Feedback;
import xyz.pplax.pplaxblog.xo.mapper.FeedbackMapper;
import xyz.pplax.pplaxblog.xo.service.FeedbackService;

/**
 * 反馈表 服务实现类
 */
@Service
public class FeedbackServiceImpl extends SuperServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

}
