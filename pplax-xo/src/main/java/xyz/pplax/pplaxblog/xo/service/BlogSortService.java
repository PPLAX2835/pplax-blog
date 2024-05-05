package xyz.pplax.pplaxblog.xo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.BlogSortEditDto;
import xyz.pplax.pplaxblog.xo.entity.BlogSort;

import java.util.List;

/**
 * 博客分类
 */
public interface BlogSortService extends SuperService<BlogSort> {

    Page<BlogSort> page(String keyword, Boolean sortByClickCount, Boolean sortByCites, Long currentPage, Long pageSize);

    List<BlogSort> list();

    Boolean promote(String blogSortUid);

    Boolean promoteCancel(String blogSortUid);

    Boolean save(BlogSortEditDto blogSortEditDto);

    Boolean updateById(BlogSortEditDto blogSortEditDto);

    ResponseResult removeById(String blogSortUid);

    ResponseResult removeByIds(List<String> blogSortUidList);

}
