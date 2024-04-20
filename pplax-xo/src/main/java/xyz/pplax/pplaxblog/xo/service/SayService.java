package xyz.pplax.pplaxblog.xo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.SayEditDto;
import xyz.pplax.pplaxblog.xo.dto.edit.TagEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.SayGetListDto;
import xyz.pplax.pplaxblog.xo.dto.list.TagGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Say;
import xyz.pplax.pplaxblog.xo.entity.Tag;

import java.util.List;

/**
 * 说说表 服务类
 */
public interface SayService extends SuperService<Say> {

    IPage<Say> list(SayGetListDto sayGetListDto);

    Boolean save(SayEditDto sayEditDto);

    Boolean updateById(SayEditDto sayEditDto);

}
