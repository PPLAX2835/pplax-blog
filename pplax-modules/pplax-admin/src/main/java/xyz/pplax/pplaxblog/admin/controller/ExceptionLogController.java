package xyz.pplax.pplaxblog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.entity.ExceptionLog;
import xyz.pplax.pplaxblog.xo.service.ExceptionLogService;

import java.util.Date;
import java.util.List;

/**
* @description t_exception_log表 controller
* @author PPLAX
* @date Wed Aug 28 20:48:20 CST 2024
*/
@RestController
@RequestMapping("/exceptionLog")
@Api(value="t_exception_log表Controller", tags={"异常日志表"})
public class ExceptionLogController extends SuperController {

    private static final Logger log = LogManager.getLogger(ExceptionLogController.class);

    @Autowired
    private ExceptionLogService exceptionLogService;

    @ApiOperation(value="获取异常日志列表", notes="获取异常日志列表")
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime",value = "开始时间",defaultValue = "2020-03-12 00:00:00",paramType = "query",dataType="Date",required = false),
            @ApiImplicitParam(name = "endTime",value = "结束时间",defaultValue = "2024-03-12 00:00:00",paramType = "query",dataType="Date",required = false),
            @ApiImplicitParam(name = "currentPage",value = "当前页码",defaultValue = "0",paramType = "query",dataType="Long",required = false),
            @ApiImplicitParam(name = "pageSize",value = "单页长度",defaultValue = "20",paramType = "query",dataType="Long",required = false)
    })
    public ResponseResult getList(
            @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Long currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Long pageSize
    ) {

        Page<ExceptionLog> exceptionLogPage = exceptionLogService.page(startTime, endTime, currentPage, pageSize);
        return ResponseResult.success(exceptionLogPage.getRecords(), exceptionLogPage.getTotal());
    }


    @ApiOperation(value="删除异常日志", notes="删除异常日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exceptionLogUid",value = "异常日志uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @DeleteMapping("/{exceptionLogUid}")
    public ResponseResult delete(@PathVariable("exceptionLogUid") String exceptionLogUid) {
        exceptionLogService.removeById(exceptionLogUid);
        return success();
    }

    @ApiOperation(value = "批量删除异常日志", notes = "批量删除异常日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blogUidList",value = "异常日志uids", defaultValue = "",dataType="List<String>",required = true)
    })
    @DeleteMapping(value = "")
    public ResponseResult delete(@RequestBody List<String> exceptionLogUidList) {
        exceptionLogService.removeByIds(exceptionLogUidList);
        return success();
    }

}
