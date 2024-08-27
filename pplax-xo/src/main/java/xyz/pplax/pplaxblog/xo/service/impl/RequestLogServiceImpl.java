package xyz.pplax.pplaxblog.xo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.constants.sql.RequestLogSQLConstants;
import xyz.pplax.pplaxblog.xo.entity.RequestLog;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.entity.UserInfo;
import xyz.pplax.pplaxblog.xo.mapper.RequestLogMapper;
import xyz.pplax.pplaxblog.xo.service.MenuService;
import xyz.pplax.pplaxblog.xo.service.RequestLogService;
import xyz.pplax.pplaxblog.xo.service.UserInfoService;
import xyz.pplax.pplaxblog.xo.service.UserService;

import java.util.Date;

/**
* @description request_log表 服务实现类
* @author PPLAX
* @date Sat Aug 24 10:02:22 CST 2024
*/
@Service
public class RequestLogServiceImpl extends SuperServiceImpl<RequestLogMapper, RequestLog> implements RequestLogService {

    private static final Logger log = LogManager.getLogger(RequestLogServiceImpl.class);

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public Page<RequestLog> page(Integer type, Date startTime, Date endTime, Long currentPage, Long pageSize) {
        // 查询参数
        PQueryWrapper<RequestLog> requestLogPQueryWrapper = new PQueryWrapper<>();
        if (type != null) {
            requestLogPQueryWrapper.eq(RequestLogSQLConstants.TYPE, type);
        }
        if (startTime != null && endTime != null) {
            requestLogPQueryWrapper.between(RequestLogSQLConstants.CREATE_TIME, startTime, endTime);
        } else if (startTime != null) {
            requestLogPQueryWrapper.ge(RequestLogSQLConstants.CREATE_TIME, startTime); // 查询 createTime >= startTime
        } else if (endTime != null) {
            requestLogPQueryWrapper.le(RequestLogSQLConstants.CREATE_TIME, endTime);   // 查询 createTime <= endTime
        }
        requestLogPQueryWrapper.orderByDesc(RequestLogSQLConstants.CREATE_TIME);

        Page<RequestLog> requestLogPage = new Page<>();
        requestLogPage.setCurrent(currentPage);
        requestLogPage.setPages(pageSize);

        // 查询
        Page<RequestLog> page = page(requestLogPage, requestLogPQueryWrapper);

        // 封装菜单、请求人
        for (RequestLog requestLog : page.getRecords()) {
            if (!StringUtils.isEmpty(requestLog.getMenuUid())) {
                requestLog.setMenu(menuService.getById(requestLog.getMenuUid()));
            }
            if (!StringUtils.isEmpty(requestLog.getUserUid())) {
                User user = userService.getById(requestLog.getUserUid());
                if (user != null) {
                    user.setUserInfo(userInfoService.getById(user.getUserInfoUid()));
                    user.sensitiveDataRemove();
                }
                requestLog.setUser(user);
            }
        }

        return page;
    }
}
