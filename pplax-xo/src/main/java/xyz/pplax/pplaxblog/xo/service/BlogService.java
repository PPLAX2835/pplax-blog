package xyz.pplax.pplaxblog.xo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.BlogEditDto;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.BlogContent;

import java.util.List;

/**
 * 博客表 服务类
 */
public interface BlogService extends SuperService<Blog> {

    Page<Blog> page(String blogTitle, String blogSortUid, String tagUid, Integer status, Long currentPage, Long pageSize);

    Page<Blog> pageHomeBlog(String blogSortUid, String tagUid, String orderByDesc, Long currentPage, Long pageSize);

    Page<Blog> pageByUserUid(String userUid, Boolean isCollect, Long currentPage, Long pageSize);

    Page<Blog> search(String keyword, Long currentPage, Long pageSize);

    ResponseResult archive(String userUid);

    List<Blog> listByBanner();

    Page<Blog> pageNotByBannerNew(Integer size);

    BlogContent getBlogContentByBlogUid(String blogUid);

    Blog getByIdWithAll(String blogUid, String userUid);

    Boolean updateById(BlogEditDto blogEditDto);

    Boolean userUpdateById(String userUid, BlogEditDto blogEditDto);

    Boolean promote(String blogUid);

    Boolean promoteCancel(String blogUid);

    Blog save(BlogEditDto blogEditDto);

    Boolean removeById(String blogUid);

    Boolean removeByIds(List<String> blogUidList);
}
