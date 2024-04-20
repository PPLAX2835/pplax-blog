package xyz.pplax.pplaxblog.xo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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

    IPage<BlogSort> list(BlogSortGetListDto blogSortGetListDto);

    List<BlogSort> list();

    Boolean promote(String blogSortUid);

    Boolean promoteCancel(String blogSortUid);

    Boolean save(BlogSortEditDto blogSortEditDto);

    Boolean updateById(BlogSortEditDto blogSortEditDto);

    ResponseResult removeById(String blogSortUid);

    ResponseResult removeByIds(List<String> blogSortUidList);

}
