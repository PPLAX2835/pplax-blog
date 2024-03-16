package xyz.pplax.pplaxblog.xo.service.blog;

import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.BlogEditDto;
import xyz.pplax.pplaxblog.xo.dto.edit.BlogSortEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.BlogGetListDto;
import xyz.pplax.pplaxblog.xo.dto.list.BlogSortGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.BlogContent;
import xyz.pplax.pplaxblog.xo.entity.BlogSort;

import java.util.List;

/**
 * 博客表 服务类
 */
public interface BlogService extends SuperService<Blog> {

    IPage<Blog> list(BlogGetListDto blogGetListDto);

    BlogContent getBlogContentByBlogUid(String blogUid);

    Boolean updateById(BlogEditDto blogEditDto);

    Blog save(BlogEditDto blogEditDto);

    Boolean removeById(String blogUid);

    Boolean removeByIds(List<String> blogUidList);
}
