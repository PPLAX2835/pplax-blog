package xyz.pplax.pplaxblog.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.admin.component.CodeGenerator;
import xyz.pplax.pplaxblog.admin.model.CodeGenerateParam;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.NamingUtils;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword",value = "关键词", defaultValue = "",paramType = "query",dataType="String",required = false),
            @ApiImplicitParam(name = "currentPage",value = "当前页码",defaultValue = "0",paramType = "query",dataType="Long",required = false),
            @ApiImplicitParam(name = "pageSize",value = "单页长度",defaultValue = "20",paramType = "query",dataType="Long",required = false)
    })
    @GetMapping("/table/list")
    public ResponseResult list(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Long currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Long pageSize
    ) {
        //查询列表数据
        List<Map<String, Object>> list = codeGenerateService.list(keyword, currentPage, pageSize);
        long count = codeGenerateService.count(keyword);

        return ResponseResult.success(list, count);
    }

    @ApiOperation(value="获得数据库表的列表", notes="获得数据库表的列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName",value = "表名",defaultValue = "t_table",paramType = "path",dataType="String",required = true)
    })
    @GetMapping("/table/{tableName}/columns")
    public ResponseResult list(@PathVariable("tableName") String tableName) {
        //查询列表数据
        return success(codeGenerateService.getTableColumns(tableName));
    }

    @ApiOperation(value="生成", notes="生成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName",value = "表名",defaultValue = "t_table",paramType = "path",dataType="String",required = true),
            @ApiImplicitParam(name = "prefix",value = "替换前缀",defaultValue = "t_",paramType = "query",dataType="String",required = false),
            @ApiImplicitParam(name = "type",value = "类型",defaultValue = "java",paramType = "query",dataType="String",required = true)
    })
    @GetMapping("/table/{tableName}/generate")
    public void generate(
            @PathVariable("tableName") String tableName,
            @RequestParam(value = "prefix", required = false) String prefix,
            @RequestParam("type") String type,
            HttpServletResponse response
    ) throws IOException {
        Map<String, Object> table = codeGenerateService.getOne(tableName);
        List<Map<String, Object>> tableColumns = null;
        if (table != null) {
            tableColumns = codeGenerateService.getTableColumns(tableName);
        }

        // 定义变量
        ByteArrayOutputStream byteArrayOutputStream = null;
        String fileName = null;

        // 判断是生成xo还是controller
        if ("java".equals(type)) {
            fileName = "xo.zip";
            byteArrayOutputStream = codeGenerator.generateXo(
                    table,
                    tableColumns,
                    prefix
            );
        } else if ("web".equals(type)) {
            fileName = "AdminWeb.zip";
            byteArrayOutputStream = codeGenerator.generateAdminWeb(
                    table,
                    prefix
            );
        }

        // 设置响应的内容类型和头信息，准备下载
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        // 将生成的压缩包写入响应的输出流中
        if (byteArrayOutputStream != null) {
            response.getOutputStream().write(byteArrayOutputStream.toByteArray());
            response.getOutputStream().flush();
        }
    }
}
