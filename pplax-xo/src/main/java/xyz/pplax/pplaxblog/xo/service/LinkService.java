package xyz.pplax.pplaxblog.xo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.LinkEditDto;
import xyz.pplax.pplaxblog.xo.entity.Link;

/**
 * 友情链接 服务类
 */
public interface LinkService extends SuperService<Link> {

    Page<Link> page(String keyword, Integer status, Long currentPage, Long pageSize);

    Boolean save(LinkEditDto linkEditDto);

    Boolean apply(LinkEditDto linkEditDto);

    Boolean updateById(LinkEditDto linkEditDto);

}
