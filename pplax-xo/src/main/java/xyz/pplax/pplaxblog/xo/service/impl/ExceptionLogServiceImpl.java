package xyz.pplax.pplaxblog.xo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.constants.sql.ExceptionLogSQLConstants;
import xyz.pplax.pplaxblog.xo.entity.ExceptionLog;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.mapper.ExceptionLogMapper;
import xyz.pplax.pplaxblog.xo.service.ExceptionLogService;
import xyz.pplax.pplaxblog.xo.service.MenuService;
import xyz.pplax.pplaxblog.xo.service.UserInfoService;
import xyz.pplax.pplaxblog.xo.service.UserService;

import java.util.Date;

/**
* @description t_exception_log表 服务实现类
* @author PPLAX
* @date Wed Aug 28 20:48:20 CST 2024
*/
@Service
public class ExceptionLogServiceImpl extends SuperServiceImpl<ExceptionLogMapper, ExceptionLog> implements ExceptionLogService {

    private static final Logger log = LogManager.getLogger(ExceptionLogServiceImpl.class);

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public Page<ExceptionLog> page(Date startTime, Date endTime, Long currentPage, Long pageSize) {
        // 查询参数
        PQueryWrapper<ExceptionLog> exceptionLogPQueryWrapper = new PQueryWrapper<>();
        if (startTime != null && endTime != null) {
            exceptionLogPQueryWrapper.between(ExceptionLogSQLConstants.CREATE_TIME, startTime, endTime);
        } else if (startTime != null) {
            exceptionLogPQueryWrapper.ge(ExceptionLogSQLConstants.CREATE_TIME, startTime); // 查询 createTime >= startTime
        } else if (endTime != null) {
            exceptionLogPQueryWrapper.le(ExceptionLogSQLConstants.CREATE_TIME, endTime);   // 查询 createTime <= endTime
        }
        exceptionLogPQueryWrapper.orderByDesc(ExceptionLogSQLConstants.CREATE_TIME);

        Page<ExceptionLog> exceptionLogPage = new Page<>();
        exceptionLogPage.setCurrent(currentPage);
        exceptionLogPage.setPages(pageSize);

        // 查询
        Page<ExceptionLog> page = page(exceptionLogPage, exceptionLogPQueryWrapper);

        // 封装菜单、请求人
        for (ExceptionLog exceptionLog : page.getRecords()) {
            if (!StringUtils.isEmpty(exceptionLog.getMenuUid())) {
                exceptionLog.setMenu(menuService.getById(exceptionLog.getMenuUid()));
            }
            if (!StringUtils.isEmpty(exceptionLog.getUserUid())) {
                User user = userService.getById(exceptionLog.getUserUid());
                if (user != null) {
                    user.setUserInfo(userInfoService.getById(user.getUserInfoUid()));
                    user.sensitiveDataRemove();
                }
                exceptionLog.setUser(user);
            }
        }

        return page;
    }
}
