package xyz.pplax.pplaxblog.xo.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.ExceptionLog;
import xyz.pplax.pplaxblog.xo.mapper.ExceptionLogMapper;
import xyz.pplax.pplaxblog.xo.service.ExceptionLogService;

/**
* @description t_exception_log表 服务实现类
* @author PPLAX
* @date Wed Aug 28 20:48:20 CST 2024
*/
@Service
public class ExceptionLogServiceImpl extends SuperServiceImpl<ExceptionLogMapper, ExceptionLog> implements ExceptionLogService {

    private static final Logger log = LogManager.getLogger(ExceptionLogServiceImpl.class);

}
