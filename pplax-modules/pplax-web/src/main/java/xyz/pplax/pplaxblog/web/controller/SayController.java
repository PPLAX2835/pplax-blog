package xyz.pplax.pplaxblog.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.IpUtils;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.CommentEditDto;
import xyz.pplax.pplaxblog.xo.dto.edit.SayEditDto;
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

    @ApiOperation(value="新增说说", notes="新增说说")
    @PostMapping("")
    public String add(HttpServletRequest httpServletRequest, @RequestBody @Validated(value = {Insert.class}) SayEditDto sayEditDto) {
        // 获取用户uid
        String userUid = getUserUid(httpServletRequest);

        sayEditDto.setUserUid(userUid);
        Boolean res = sayService.save(sayEditDto);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value="删除说说", notes="删除说说")
    @DeleteMapping("/{sayUid}")
    public String delete(@PathVariable("sayUid") String sayUid) {
        boolean res = sayService.removeById(sayUid);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value = "获取说说列表", httpMethod = "GET", response = ResponseResult.class, notes = "获取说说列表")
    @GetMapping("/list")
    public String getSayList(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ){
        String userUid = getUserUid(httpServletRequest);

        Page<Say> sayIPage = sayService.pagePublic(userUid, currentPage, pageSize);

        return toJson(ResponseResult.success(sayIPage.getRecords(), sayIPage.getTotal()));
    }

    @ApiOperation(value="获取地理位置", notes="获取地理位置")
    @GetMapping("/address")
    public String getAddress(HttpServletRequest httpServletRequest) {
        // 获取ip
        String ipAddress = IpUtils.getIpAddress(httpServletRequest);
        // 通过浏览器解析工具类UserAgent获取访问设备信息
        UserAgent userAgent = IpUtils.getUserAgent(httpServletRequest);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        // 生成唯一用户标识
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());

        return success(IpUtils.getCityInfo(ipAddress));
    }

    @ApiOperation(value = "说说点赞", httpMethod = "POST", response = ResponseResult.class, notes = "说说点赞")
    @PostMapping("/{sayUid}/like")
    public String like(
            HttpServletRequest httpServletRequest,
            @PathVariable(value = "sayUid") String sayUid
    ){
        String userUid = getUserUid(httpServletRequest);

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
        String userUid = getUserUid(httpServletRequest);

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


    @ApiOperation(value = "获取评论列表", httpMethod = "GET", response = ResponseResult.class, notes = "获取评论列表")
    @GetMapping("/{sayUid}/comment/list")
    public String getBlogList(
            @PathVariable(value = "sayUid") String sayUid,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ){

        Page<Comment> commentIPage = commentService.pageByOriginalUid(sayUid, CharacterConstants.NUM_TWO, currentPage, pageSize);

        return toJson(ResponseResult.success(commentIPage.getRecords(), commentIPage.getTotal()));
    }
}

