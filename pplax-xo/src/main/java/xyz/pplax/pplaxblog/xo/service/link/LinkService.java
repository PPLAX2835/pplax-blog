package xyz.pplax.pplaxblog.xo.service.link;

import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.LinkEditDto;
import xyz.pplax.pplaxblog.xo.dto.edit.TagEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.LinkGetListDto;
import xyz.pplax.pplaxblog.xo.dto.list.TagGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Link;
import xyz.pplax.pplaxblog.xo.entity.Tag;

import java.util.List;

/**
 * 友情链接 服务类
 */
public interface LinkService extends SuperService<Link> {

    IPage<Link> list(LinkGetListDto linkGetListDto);

    Boolean save(LinkEditDto linkEditDto);

    Boolean updateById(LinkEditDto linkEditDto);

}
