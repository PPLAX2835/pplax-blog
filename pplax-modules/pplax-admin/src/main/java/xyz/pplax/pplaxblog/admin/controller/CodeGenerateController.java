package xyz.pplax.pplaxblog.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.admin.component.CodeGenerator;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.service.CodeGenerateService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/codeGenerate")
@Api(value="代码生成Controller", tags={"代码生成Controller"})
public class CodeGenerateController extends SuperController {

    private static final Logger log = LogManager.getLogger(CodeGenerateController.class);

    @Autowired
    private CodeGenerateService codeGenerateService;

    @Autowired
    private CodeGenerator codeGenerator;

    @ApiOperation(value="获得数据库表的列表", notes="获得数据库表的列表")
    @GetMapping("/table/list")
    public ResponseResult list(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "currentPage", required = false) Long currentPage,
            @RequestParam(value = "pageSize", required = false) Long pageSize
    ) {
        //查询列表数据
        List<Map<String, Object>> list = codeGenerateService.list(keyword, currentPage, pageSize);
        long count = codeGenerateService.count(keyword);

        return ResponseResult.success(list, count);
    }

    @ApiOperation(value="获得数据库表的列表", notes="获得数据库表的列表")
    @GetMapping("/table/{tableName}/columns")
    public ResponseResult list(@PathVariable("tableName") String tableName) {
        //查询列表数据
        return success(codeGenerateService.getTableColumns(tableName));
    }

    @ApiOperation(value="生成", notes="生成")
    @RequestMapping("/table/{tableName}/generate")
    public void generate(@PathVariable("tableName") String tableName, HttpServletResponse response) throws IOException {
        Map<String, Object> table = codeGenerateService.getOne(tableName);
        List<Map<String, Object>> tableColumns = null;
        if (table != null) {
            tableColumns = codeGenerateService.getTableColumns(tableName);
        }

        ByteArrayOutputStream byteArrayOutputStream = codeGenerator.generate(table, tableColumns, "t_");

        // 设置响应的内容类型和头信息，准备下载
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=example-with-dir.zip");

        // 将生成的压缩包写入响应的输出流中
        response.getOutputStream().write(byteArrayOutputStream.toByteArray());
        response.getOutputStream().flush();
    }
}
