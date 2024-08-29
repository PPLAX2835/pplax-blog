package xyz.pplax.pplaxblog.xo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.entity.ExceptionLog;

import java.util.Date;


/**
 * @description t_exception_log表 服务类 接口
 * @author PPLAX
 * @date Wed Aug 28 20:48:20 CST 2024
 */
public interface ExceptionLogService extends SuperService<ExceptionLog> {

    Page<ExceptionLog> page(Date startTime, Date endTime, Long currentPage, Long pageSize);

}
