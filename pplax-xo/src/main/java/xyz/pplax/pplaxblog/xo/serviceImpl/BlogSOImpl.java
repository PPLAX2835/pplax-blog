package xyz.pplax.pplaxblog.xo.serviceImpl;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.base.serviceImpl.BaseSOImpl;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.service.BlogSO;

/**
 * 博客Service实现
 */
@Service
@ComponentScan({"xyz.pplax.pplaxblog.xo.dao"})
public class BlogSOImpl extends BaseSOImpl<Blog> implements BlogSO {
	
}
