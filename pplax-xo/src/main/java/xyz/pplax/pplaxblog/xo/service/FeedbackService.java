package xyz.pplax.pplaxblog.xo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.BlogEditDto;
import xyz.pplax.pplaxblog.xo.dto.edit.FeedbackEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.CommentGetListDto;
import xyz.pplax.pplaxblog.xo.dto.list.FeedbackGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Comment;
import xyz.pplax.pplaxblog.xo.entity.Feedback;

/**
 * 反馈表 服务类
 */
public interface FeedbackService extends SuperService<Feedback> {

    IPage<Feedback> list(FeedbackGetListDto feedbackGetListDto);

    Boolean updateById(FeedbackEditDto feedbackEditDto);
}
