package xyz.pplax.pplaxblog.xo.service.blogcontent;

import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.BlogContent;
import xyz.pplax.pplaxblog.xo.mapper.BlogContentMapper;

/**
 * 博客表 服务实现类
 */
@Service
public class BlogContentServiceImpl extends SuperServiceImpl<BlogContentMapper, BlogContent> implements BlogContentService {

}
