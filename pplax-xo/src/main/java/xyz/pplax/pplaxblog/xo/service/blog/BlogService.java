package xyz.pplax.pplaxblog.xo.service.blog;

import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.BlogEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.BlogGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.BlogContent;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 博客表 服务类
 */
public interface BlogService extends SuperService<Blog> {

    IPage<Blog> list(BlogGetListDto blogGetListDto);

    IPage<Blog> listByBlogSort(String blogSortUid, String orderByDesc, Long currentPage, Long pageSize);

    ResponseResult archive(String userUid);

    List<Blog> listByBanner();

    IPage<Blog> listNotByBannerNew(Integer size);

    BlogContent getBlogContentByBlogUid(String blogUid);

    Blog getByIdWithAll(String blogUid, String userUid);

    Boolean updateById(BlogEditDto blogEditDto);

    Boolean promote(String blogUid);

    Boolean promoteCancel(String blogUid);

    Blog save(BlogEditDto blogEditDto);

    Boolean removeById(String blogUid);

    Boolean removeByIds(List<String> blogUidList);
}
