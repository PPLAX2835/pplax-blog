package xyz.pplax.pplaxblog.xo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.RequestLog;

import java.util.Date;


/**
 * @description request_log表 服务类 接口
 * @author PPLAX
 * @date Sat Aug 24 10:02:22 CST 2024
 */
public interface RequestLogService extends SuperService<RequestLog> {

    Page<RequestLog> page(Integer type, Date startTime, Date endTime, Long currentPage, Long pageSize);

}
