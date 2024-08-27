package xyz.pplax.pplaxblog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.entity.Comment;
import xyz.pplax.pplaxblog.xo.service.CommentService;

import java.util.List;

/**
 * 评论表 Controller
 */
@RestController
@RequestMapping("/comment")
@Api(value="评论Controller", tags={"评论Controller"})
public class CommentController extends SuperController {

    private static final Logger log = LogManager.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @ApiOperation(value="获取评论列表", notes="获取评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword",value = "关键词", defaultValue = "",paramType = "query",dataType="String",required = false),
            @ApiImplicitParam(name = "nickname",value = "用户昵称",defaultValue = "",paramType = "query",dataType="String",required = false),
            @ApiImplicitParam(name = "type",value = "类型",defaultValue = "",paramType = "query",dataType="Integer",required = false),
            @ApiImplicitParam(name = "currentPage",value = "当前页码",defaultValue = "0",paramType = "query",dataType="Long",required = false),
            @ApiImplicitParam(name = "pageSize",value = "单页长度",defaultValue = "20",paramType = "query",dataType="Long",required = false)
    })
    @GetMapping("/list")
    public ResponseResult getList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Long currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Long pageSize
    ) {
        Page<Comment> commentIPage = commentService.page(keyword, nickname, type, null, currentPage, pageSize);

        return ResponseResult.success(commentIPage.getRecords(), commentIPage.getTotal());
    }

    @ApiOperation(value="删除评论", notes="删除评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentUid",value = "评论uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @DeleteMapping("/{commentUid}")
    public ResponseResult delete(@PathVariable("commentUid") String commentUid) {
        boolean res = commentService.removeById(commentUid);

        return success();
    }

    @ApiOperation(value = "批量删除评论", notes = "批量删除评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentUidList",value = "评论uidList", defaultValue = "",dataType="List<String>",required = true)
    })
    @DeleteMapping(value = "")
    public ResponseResult delete(@RequestBody List<String> commentUidList) {
        boolean res = commentService.removeByIds(commentUidList);

        return success();
    }
}

