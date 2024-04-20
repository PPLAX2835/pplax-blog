package xyz.pplax.pplaxblog.file.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.file.service.FileService;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.list.FileStorageGetListDto;
import xyz.pplax.pplaxblog.xo.entity.FileStorage;
import xyz.pplax.pplaxblog.xo.service.FileStorageService;

import java.util.List;

/**
 * 文件操作接口
 *
 * @author PPLAX
 * @date 2024/1/18 14:37
 */
@RestController
@RequestMapping("")
@Api(value = "文件相关接口", tags = {"文件相关接口"})
public class FileController extends SuperController {
    private static final Logger log = LogManager.getLogger(FileController.class);

    @Value("${pplax.storage.mode:localStorage}")
    private String storageMode;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileService fileService;

    @ApiOperation(value="获取文件列表", notes="获取文件列表")
    @GetMapping("/list")
    public String getList(
            @RequestParam(value = "currentPage", required = false) Long currentPage,
            @RequestParam(value = "pageSize", required = false) Long pageSize
    ) {
        // 封装
        FileStorageGetListDto fileStorageGetListDto = new FileStorageGetListDto();
        fileStorageGetListDto.setCurrentPage(currentPage);
        fileStorageGetListDto.setPageSize(pageSize);

        IPage<FileStorage> fileStorageIPage = fileStorageService.list(fileStorageGetListDto);

        return toJson(ResponseResult.success(fileStorageIPage.getRecords(), fileStorageIPage.getTotal()));
    }

    @ApiOperation(value="删除标签", notes="删除标签")
    @DeleteMapping("/{fileStorageUid}")
    public String delete(@PathVariable("fileStorageUid") String fileStorageUid) throws Exception {
        return toJson(fileService.delete(storageMode, fileStorageUid));
    }

    @ApiOperation(value = "批量删除标签", notes = "批量删除标签")
    @DeleteMapping(value = "")
    public String delete(@RequestBody List<String> tagUidList) throws Exception {
        return toJson(fileService.deleteBatch(storageMode, tagUidList));
    }

}
