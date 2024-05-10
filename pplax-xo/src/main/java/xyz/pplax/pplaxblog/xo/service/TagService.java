package xyz.pplax.pplaxblog.xo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.TagEditDto;
import xyz.pplax.pplaxblog.xo.entity.Tag;

import java.util.List;

/**
 * 标签表 服务类
 */
public interface TagService extends SuperService<Tag> {

    Page<Tag> page(String keyword, Boolean sortByClickCount, Boolean sortByCites, Long currentPage, Long pageSize);

    Boolean save(TagEditDto tagEditDto);

    Boolean updateById(TagEditDto tagEditDto);

    Integer count(String name);

    ResponseResult removeById(String tagUid);

    ResponseResult removeByIds(List<String> tagUidList);

    Boolean isTagNameExist(String name);
}
