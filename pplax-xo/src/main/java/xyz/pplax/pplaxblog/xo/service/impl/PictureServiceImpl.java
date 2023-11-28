package xyz.pplax.pplaxblog.xo.service.impl;

import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.Picture;
import xyz.pplax.pplaxblog.xo.entity.Tag;
import xyz.pplax.pplaxblog.xo.mapper.PictureMapper;
import xyz.pplax.pplaxblog.xo.mapper.TagMapper;
import xyz.pplax.pplaxblog.xo.service.PictureService;
import xyz.pplax.pplaxblog.xo.service.TagService;

/**
 * 图片表 服务实现类
 */
@Service
public class PictureServiceImpl extends SuperServiceImpl<PictureMapper, Picture> implements PictureService {

}
