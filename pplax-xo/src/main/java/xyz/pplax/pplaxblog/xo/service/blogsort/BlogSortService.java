package xyz.pplax.pplaxblog.xo.service.blogsort;

import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.BlogSortEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.BlogSortGetListDto;
import xyz.pplax.pplaxblog.xo.entity.BlogSort;

import java.util.List;

/**
 * 博客分类
 */
public interface BlogSortService extends SuperService<BlogSort> {

    List<BlogSort> list(BlogSortGetListDto blogSortGetListDto);

    Long count(BlogSortGetListDto blogSortGetListDto);

    Boolean promote(String blogSortUid);

    Boolean promoteCancel(String blogSortUid);

    Boolean save(BlogSortEditDto blogSortEditDto);

    Boolean updateById(BlogSortEditDto blogSortEditDto);

    ResponseResult removeById(String blogSortUid);

    ResponseResult logicDelete(String uid);

    ResponseResult logicBatchDelete(BlogSortEditDto blogSortEditDto);

}
