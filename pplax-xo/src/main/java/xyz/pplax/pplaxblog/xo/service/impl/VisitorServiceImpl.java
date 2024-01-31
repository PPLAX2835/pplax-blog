package xyz.pplax.pplaxblog.xo.service.impl;

import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.Visitor;
import xyz.pplax.pplaxblog.xo.mapper.VisitorMapper;
import xyz.pplax.pplaxblog.xo.service.VisitorService;

/**
 * 博主表 服务实现类
 */
@Service
public class VisitorServiceImpl extends SuperServiceImpl<VisitorMapper, Visitor> implements VisitorService {

}
