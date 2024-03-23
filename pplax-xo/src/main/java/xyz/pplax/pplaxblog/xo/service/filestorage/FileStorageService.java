package xyz.pplax.pplaxblog.xo.service.filestorage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.list.FileStorageGetListDto;
import xyz.pplax.pplaxblog.xo.dto.list.TagGetListDto;
import xyz.pplax.pplaxblog.xo.entity.FileStorage;
import xyz.pplax.pplaxblog.xo.entity.Tag;

/**
 * 文件 服务类
 */
public interface FileStorageService extends SuperService<FileStorage> {

    IPage<FileStorage> list(FileStorageGetListDto fileStorageGetListDto);

}
