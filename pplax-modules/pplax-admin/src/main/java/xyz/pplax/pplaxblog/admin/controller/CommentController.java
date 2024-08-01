package xyz.pplax.pplaxblog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
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
    @GetMapping("/list")
    public ResponseResult getList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ) {
        Page<Comment> commentIPage = commentService.page(keyword, nickname, type, null, currentPage, pageSize);

        return ResponseResult.success(commentIPage.getRecords(), commentIPage.getTotal());
    }

    @ApiOperation(value="删除评论", notes="删除评论")
    @DeleteMapping("/{commentUid}")
    public ResponseResult delete(@PathVariable("commentUid") String commentUid) {
        boolean res = commentService.removeById(commentUid);

        if (res) {
            return success();
        }

        return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "批量删除评论", notes = "批量删除评论")
    @DeleteMapping(value = "")
    public ResponseResult delete(@RequestBody List<String> commentUidList) {
        boolean res = commentService.removeByIds(commentUidList);

        if (res) {
            return success();
        }
        return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

