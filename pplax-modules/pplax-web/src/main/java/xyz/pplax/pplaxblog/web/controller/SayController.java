package xyz.pplax.pplaxblog.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.CommentEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.SayGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Comment;
import xyz.pplax.pplaxblog.xo.entity.Say;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.service.CommentService;
import xyz.pplax.pplaxblog.xo.service.SayService;
import xyz.pplax.pplaxblog.xo.service.UserService;
import xyz.pplax.pplaxblog.xo.service.UserInfoService;

import javax.servlet.http.HttpServletRequest;


/**
 * 说说 Controller
 */
@RestController
@RequestMapping("/say")
@Api(value="说说Controller", tags={"说说Controller"})
public class SayController extends SuperController {

    private static Logger log = LogManager.getLogger(SayController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SayService sayService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "获取博客列表", httpMethod = "GET", response = ResponseResult.class, notes = "网站相关信息")
    @GetMapping("/list")
    public String getSayList(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ){
        String userUid = null;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization)) {
            String accessToken = authorization.replace("Bearer ", "");
            String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
            JSONObject jsonObject = JSON.parseObject(payloadByBase64);
            userUid = (String) jsonObject.get("uid");
        }

        SayGetListDto sayGetListDto = new SayGetListDto();
        sayGetListDto.setUserUid(userUid);
        sayGetListDto.setCurrentPage(currentPage);
        sayGetListDto.setPageSize(pageSize);

        IPage<Say> sayIPage = sayService.list(sayGetListDto);

        return toJson(ResponseResult.success(sayIPage.getRecords(), sayIPage.getTotal()));
    }


    @ApiOperation(value = "说说点赞", httpMethod = "POST", response = ResponseResult.class, notes = "说说点赞")
    @PostMapping("/{sayUid}/like")
    public String like(
            HttpServletRequest httpServletRequest,
            @PathVariable(value = "sayUid") String sayUid
    ){
        String userUid = null;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization)) {
            String accessToken = authorization.replace("Bearer ", "");
            String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
            JSONObject jsonObject = JSON.parseObject(payloadByBase64);
            userUid = (String) jsonObject.get("uid");
        }

        boolean res = commentService.like(sayUid, userUid, CharacterConstants.NUM_THREE);

        if (res) {
            return success();
        }

        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }


    @ApiOperation(value = "评论", httpMethod = "POST", response = ResponseResult.class, notes = "评论")
    @PostMapping("/{sayUid}/comment")
    public String comment(
            HttpServletRequest httpServletRequest,
            @PathVariable("sayUid") String sayUid,
            @RequestBody CommentEditDto commentEditDto
    ){
        String userUid = null;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization)) {
            String accessToken = authorization.replace("Bearer ", "");
            String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
            JSONObject jsonObject = JSON.parseObject(payloadByBase64);
            userUid = (String) jsonObject.get("uid");
        }

        Comment comment = new Comment();
        comment.setContent(commentEditDto.getContent());
        comment.setToUid(commentEditDto.getToUid());
        comment.setToUserUid(commentEditDto.getToUserUid());
        comment.setOriginalUid(sayUid);
        comment.setUid(StringUtils.getUUID());
        comment.setType(2);
        comment.setUserUid(userUid);
        comment.setIp(IpUtils.getIpAddress(httpServletRequest));
        comment.setAddress(IpUtils.getCityInfo(comment.getIp()));

        boolean res = commentService.save(comment);

        if (res) {
            User commentator = userService.getById(userUid);
            if (commentator != null) {
                commentator.setUserInfo(userInfoService.getById(commentator.getUserInfoUid()));
                commentator.sensitiveDataRemove();
            }
            comment.setCommentator(commentator);

            User targetUser = userService.getById(commentEditDto.getToUserUid());
            if (targetUser != null) {
                targetUser.setUserInfo(userInfoService.getById(targetUser.getUserInfoUid()));
                targetUser.sensitiveDataRemove();
            }
            comment.setTargetUser(targetUser);

            return success(comment);
        }

        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}

