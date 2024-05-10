package xyz.pplax.pplaxblog.xo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.constants.sql.FileStorageSQLConstants;
import xyz.pplax.pplaxblog.xo.entity.FileStorage;
import xyz.pplax.pplaxblog.xo.mapper.FileStorageMapper;
import xyz.pplax.pplaxblog.xo.service.FileStorageService;

/**
 * 文件表 服务实现类
 */
@Service
public class FileStorageServiceImpl extends SuperServiceImpl<FileStorageMapper, FileStorage> implements FileStorageService {

    @Override
    public Page<FileStorage> page(Long currentPage, Long pageSize) {
        PQueryWrapper<FileStorage> fileStoragePQueryWrapper = new PQueryWrapper<>();
        //分页
        Page<FileStorage> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        // 按创建时间排序
        fileStoragePQueryWrapper.orderByDesc(FileStorageSQLConstants.C_CREATE_TIME);

        return page(page, fileStoragePQueryWrapper);
    }
}
