package xyz.pplax.pplaxblog.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.IpUtils;
import xyz.pplax.pplaxblog.commons.utils.JwtUtil;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.starter.amqp.constants.MqConstants;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.CommentEditDto;
import xyz.pplax.pplaxblog.xo.entity.Comment;
import xyz.pplax.pplaxblog.xo.service.CommentService;

import javax.servlet.http.HttpServletRequest;


/**
 * 评论 Controller
 */
@RestController
@RequestMapping("/comment")
@Api(value="评论Controller", tags={"评论Controller"})
public class CommentController extends SuperController {

    private static Logger log = LogManager.getLogger(CommentController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "回复", httpMethod = "POST", response = ResponseResult.class, notes = "回复")
    @PostMapping("/{commentUid}/reply")
    public String comment(
            HttpServletRequest httpServletRequest,
            @PathVariable("commentUid") String commentUid,
            @RequestBody CommentEditDto commentEditDto
    ){

        String userUid = getUserUid(httpServletRequest);

        Comment comment = new Comment();
        comment.setToUid(commentEditDto.getToUid());
        comment.setContent(commentEditDto.getContent());
        comment.setToUserUid(commentEditDto.getToUserUid());
        comment.setOriginalUid(commentUid);
        comment.setType(4);
        comment.setUid(StringUtils.getUUID());
        comment.setUserUid(userUid);
        comment.setIp(IpUtils.getIpAddress(httpServletRequest));
        comment.setAddress(IpUtils.getCityInfo(comment.getIp()));

        rabbitTemplate.convertAndSend(MqConstants.EXCHANGE_DIRECT, MqConstants.PPLAX_COMMENT, comment);

        return success();
    }


    @ApiOperation(value = "获得回复列表", httpMethod = "GET", response = ResponseResult.class, notes = "获得回复列表")
    @GetMapping("/{commentUid}/reply/list")
    public String comment(
            @PathVariable("commentUid") String commentUid,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ){

        Page<Comment> commentIPage = commentService.pageByOriginalUid(commentUid, CharacterConstants.NUM_FOUR, currentPage, pageSize);

        return toJson(ResponseResult.success(commentIPage.getRecords(), commentIPage.getTotal()));
    }
}

