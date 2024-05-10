package xyz.pplax.pplaxblog.xo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.entity.FileStorage;

/**
 * 文件 服务类
 */
public interface FileStorageService extends SuperService<FileStorage> {

    Page<FileStorage> page(Long currentPage, Long pageSize);

}
