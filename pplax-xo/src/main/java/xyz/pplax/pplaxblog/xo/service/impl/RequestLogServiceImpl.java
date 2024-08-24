package xyz.pplax.pplaxblog.xo.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.RequestLog;
import xyz.pplax.pplaxblog.xo.mapper.RequestLogMapper;
import xyz.pplax.pplaxblog.xo.service.RequestLogService;

/**
* @description request_log表 服务实现类
* @author PPLAX
* @date Sat Aug 24 10:02:22 CST 2024
*/
@Service
public class RequestLogServiceImpl extends SuperServiceImpl<RequestLogMapper, RequestLog> implements RequestLogService {

    private static final Logger log = LogManager.getLogger(RequestLogServiceImpl.class);

}
