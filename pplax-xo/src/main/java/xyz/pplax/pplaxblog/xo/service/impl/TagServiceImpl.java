package xyz.pplax.pplaxblog.xo.service.impl;

import xyz.pplax.pplaxblog.xo.entity.Tag;
import xyz.pplax.pplaxblog.xo.mapper.TagMapper;
import xyz.pplax.pplaxblog.xo.service.TagService;
import xyz.pplax.pplaxblog.commons.base.serviceImpl.SuperServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 标签表 服务实现类
 */
@Service
public class TagServiceImpl extends SuperServiceImpl<TagMapper, Tag> implements TagService {

}
