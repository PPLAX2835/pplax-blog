package xyz.pplax.pplaxblog.xo.service.impl;

import xyz.pplax.pplaxblog.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.entity.PictureSort;
import xyz.pplax.pplaxblog.xo.mapper.PictureSortMapper;
import xyz.pplax.pplaxblog.base.serviceImpl.SuperServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 图片分类表 服务实现类
 * </p>
 */
@Service
public class PictureSortServiceImpl extends SuperServiceImpl<PictureSortMapper, PictureSort> implements SuperService<PictureSort> {

}
