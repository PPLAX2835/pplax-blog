package xyz.pplax.pplaxblog.xo.service.tag;

import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.BlogSortEditDto;
import xyz.pplax.pplaxblog.xo.dto.edit.TagEditDto;
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

    Boolean save(TagEditDto tagEditDto);

    Boolean updateById(TagEditDto tagEditDto);

    ResponseResult removeById(String tagUid);

    ResponseResult removeByIds(List<String> tagUidList);
}
