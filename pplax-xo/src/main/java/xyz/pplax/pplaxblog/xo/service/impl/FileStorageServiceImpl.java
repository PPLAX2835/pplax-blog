package xyz.pplax.pplaxblog.xo.service.impl;

import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.FileStorage;
import xyz.pplax.pplaxblog.xo.mapper.FileStorageMapper;
import xyz.pplax.pplaxblog.xo.service.FileStorageService;

/**
 * 文件表 服务实现类
 */
@Service
public class FileStorageServiceImpl extends SuperServiceImpl<FileStorageMapper, FileStorage> implements FileStorageService {

}
