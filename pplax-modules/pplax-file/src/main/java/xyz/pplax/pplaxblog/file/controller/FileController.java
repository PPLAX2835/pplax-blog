package xyz.pplax.pplaxblog.file.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.FileUtils;
import xyz.pplax.pplaxblog.file.service.FileService;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.entity.FileStorage;
import xyz.pplax.pplaxblog.xo.service.FileStorageService;

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
@RequestMapping("")
@Api(value = "文件相关接口", tags = {"文件相关接口"})
public class FileController extends SuperController {
    private static final Logger log = LogManager.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileService fileService;

    @ApiOperation(value="获取文件列表", notes="获取文件列表")
    @GetMapping("/list")
    public ResponseResult getList(
            @RequestParam(value = "currentPage", required = false) Long currentPage,
            @RequestParam(value = "pageSize", required = false) Long pageSize
    ) {

        Page<FileStorage> fileStorageIPage = fileStorageService.page(currentPage, pageSize);

        return ResponseResult.success(fileStorageIPage.getRecords(), fileStorageIPage.getTotal());
    }

    @ApiOperation(value="获取文件", notes="获取文件")
    @GetMapping("/localStorage/{fileUid}")
    public void getFile(@PathVariable("fileUid") String fileUid, HttpServletResponse httpServletResponse) throws IOException {

        FileStorage fileStorage = fileStorageService.getById(fileUid);
        if (fileStorage == null ) {
            return;
        }

        // 从classpath中读取文件
        File file = new File(FileUtils.path("G:/tmp/pplax-blog/" + fileStorage.getFilePath() + fileStorage.getFileName()));
        InputStream inputStream = new FileInputStream(file);
        // 将文件转换为字节数组
        byte[] fileData = new byte[inputStream.available()];
        inputStream.read(fileData);

        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(fileData);
    }

    @ApiOperation(value="删除文件", notes="删除文件")
    @DeleteMapping("/{fileStorageUid}")
    public ResponseResult delete(@PathVariable("fileStorageUid") String fileStorageUid) throws Exception {
        return fileService.delete(fileStorageUid);
    }

    @ApiOperation(value = "批量删除文件", notes = "批量删除文件")
    @DeleteMapping(value = "")
    public ResponseResult delete(@RequestBody List<String> tagUidList) throws Exception {
        return fileService.deleteBatch(tagUidList);
    }

}
