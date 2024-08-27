package xyz.pplax.pplaxblog.file.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.constants.SiteSettingConstants;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.FileUtils;
import xyz.pplax.pplaxblog.file.service.FileService;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.entity.FileStorage;
import xyz.pplax.pplaxblog.xo.entity.SiteSetting;
import xyz.pplax.pplaxblog.xo.service.FileStorageService;
import xyz.pplax.pplaxblog.xo.service.SiteSettingService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * 文件操作接口
 *
 * @author PPLAX
 * @date 2024/1/18 14:37
 */
@RestController
@RequestMapping("/admin")
@Api(value = "文件操作相关接口", tags = {"文件操作相关接口"})
public class FileAdminController extends SuperController {
    private static final Logger log = LogManager.getLogger(FileAdminController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SiteSettingService siteSettingService;

    @ApiOperation(value="获取文件列表", notes="获取文件列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页码",defaultValue = "0",paramType = "query",dataType="Long",required = false),
            @ApiImplicitParam(name = "pageSize",value = "单页长度",defaultValue = "20",paramType = "query",dataType="Long",required = false)
    })
    @GetMapping("/list")
    public ResponseResult getList(
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Long currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Long pageSize
    ) {

        Page<FileStorage> fileStorageIPage = fileStorageService.page(currentPage, pageSize);

        return ResponseResult.success(fileStorageIPage.getRecords(), fileStorageIPage.getTotal());
    }

    @ApiOperation(value="获取文件", notes="获取文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileStorageUid",value = "文件uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @GetMapping("/localStorage/{fileStorageUid}")
    public void getFile(@PathVariable("fileStorageUid") String fileStorageUid, HttpServletResponse httpServletResponse) throws IOException {

        FileStorage fileStorage = fileStorageService.getById(fileStorageUid);
        if (fileStorage == null ) {
            return;
        }

        // 从classpath中读取文件
        SiteSetting localStorageBasePath = siteSettingService.getByNameEn(SiteSettingConstants.LOCAL_STORAGE_BASE_PATH_NAME_EN);
        File file = new File(FileUtils.path(localStorageBasePath.getValue() + fileStorage.getFilePath() + fileStorage.getFileName()));
        InputStream inputStream = new FileInputStream(file);
        // 将文件转换为字节数组
        byte[] fileData = new byte[inputStream.available()];
        inputStream.read(fileData);

        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(fileData);
    }

    @ApiOperation(value="删除文件", notes="删除文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileStorageUid",value = "文件uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @DeleteMapping("/{fileStorageUid}")
    public ResponseResult delete(@PathVariable("fileStorageUid") String fileStorageUid) throws Exception {
        return fileService.delete(fileStorageUid);
    }

    @ApiOperation(value = "批量删除文件", notes = "批量删除文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileStorageUidList",value = "文件uidList", defaultValue = "",dataType="List<String>",required = true)
    })
    @DeleteMapping(value = "")
    public ResponseResult delete(@RequestBody List<String> fileStorageUidList) throws Exception {
        return fileService.deleteBatch(fileStorageUidList);
    }

}
