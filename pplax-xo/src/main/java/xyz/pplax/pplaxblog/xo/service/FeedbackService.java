package xyz.pplax.pplaxblog.xo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.FeedbackEditDto;
import xyz.pplax.pplaxblog.xo.entity.Feedback;

/**
 * 反馈表 服务类
 */
public interface FeedbackService extends SuperService<Feedback> {

    Page<Feedback> page(Integer type, Integer status, Long currentPage, Long pageSize);

    Boolean updateById(FeedbackEditDto feedbackEditDto);
}
