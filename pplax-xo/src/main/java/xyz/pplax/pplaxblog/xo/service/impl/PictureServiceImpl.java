package xyz.pplax.pplaxblog.xo.service.impl;

import xyz.pplax.pplaxblog.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.entity.Picture;
import xyz.pplax.pplaxblog.xo.mapper.PictureMapper;
import xyz.pplax.pplaxblog.base.serviceImpl.SuperServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 图片表 服务实现类
 * </p>
 */
@Service
public class PictureServiceImpl extends SuperServiceImpl<PictureMapper, Picture> implements SuperService<Picture> {

}
