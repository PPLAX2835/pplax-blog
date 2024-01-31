package xyz.pplax.pplaxblog.xo.service.impl;

import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.mapper.BlogMapper;
import xyz.pplax.pplaxblog.xo.service.BlogService;

/**
 * 博客表 服务实现类
 */
@Service
public class BlogServiceImpl extends SuperServiceImpl<BlogMapper, Blog> implements BlogService {

}
