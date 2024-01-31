package xyz.pplax.pplaxblog.xo.service.impl;

import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.Link;
import xyz.pplax.pplaxblog.xo.mapper.LinkMapper;
import xyz.pplax.pplaxblog.xo.service.LinkService;

/**
 * 友情链接表 服务实现类
 */
@Service
public class LinkServiceImpl extends SuperServiceImpl<LinkMapper, Link> implements LinkService {

}
