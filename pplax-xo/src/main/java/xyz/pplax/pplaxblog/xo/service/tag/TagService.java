package xyz.pplax.pplaxblog.xo.service.tag;

import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.list.BlogSortGetListDto;
import xyz.pplax.pplaxblog.xo.dto.list.TagGetListDto;
import xyz.pplax.pplaxblog.xo.entity.BlogSort;
import xyz.pplax.pplaxblog.xo.entity.Tag;

import java.util.List;

/**
 * 标签表 服务类
 */
public interface TagService extends SuperService<Tag> {


    List<Tag> list(TagGetListDto tagGetListDto);

    Long count(TagGetListDto tagGetListDto);
}
