package xyz.pplax.pplaxblog.xo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.SayEditDto;
import xyz.pplax.pplaxblog.xo.entity.Say;

/**
 * 说说表 服务类
 */
public interface SayService extends SuperService<Say> {

    Page<Say> pagePublic(String userUid, Long currentPage, Long pageSize);

    Page<Say> page(String keyword, Long currentPage, Long pageSize);

    Boolean save(SayEditDto sayEditDto);

    Boolean updateById(SayEditDto sayEditDto);

}
