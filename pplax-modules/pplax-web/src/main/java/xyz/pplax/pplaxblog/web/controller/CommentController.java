package xyz.pplax.pplaxblog.web.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.list.CommentGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Comment;
import xyz.pplax.pplaxblog.xo.service.comment.CommentService;


/**
 * 评论 Controller
 */
@RestController
@RequestMapping("/comment")
@Api(value="评论Controller", tags={"评论Controller"})
public class CommentController extends SuperController {

    private static Logger log = LogManager.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;


    @ApiOperation(value = "获取评论列表", httpMethod = "GET", response = ResponseResult.class, notes = "获取评论列表")
    @GetMapping("/list")
    public String getBlogList(
            @RequestParam(value = "blogUid") String blogUid,
            @RequestParam(value = "type") Integer type,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ){

        IPage<Comment> commentIPage = commentService.pageByBlogUid(blogUid, type, currentPage, pageSize);

        return toJson(ResponseResult.success(commentIPage.getRecords(), commentIPage.getTotal()));
    }

}

