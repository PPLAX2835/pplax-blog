package xyz.pplax.pplaxblog.xo.service.impl;

import xyz.pplax.pplaxblog.xo.entity.Admin;
import xyz.pplax.pplaxblog.xo.mapper.AdminMapper;
import xyz.pplax.pplaxblog.xo.service.AdminService;
import xyz.pplax.pplaxblog.base.serviceImpl.SuperServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 管理员表 服务实现类
 */
@Service
public class AdminServiceImpl extends SuperServiceImpl<AdminMapper, Admin> implements AdminService {

}
