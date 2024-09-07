package xyz.pplax.pplaxblog.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    private SayService sayService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value="新增说说", notes="新增说说")
    @PostMapping("")
    public ResponseResult add(HttpServletRequest httpServletRequest, @RequestBody @Validated(value = {Insert.class}) SayEditDto sayEditDto) {
        // 获取用户uid
        String userUid = getUserUid(httpServletRequest);

        sayEditDto.setUserUid(userUid);
        Boolean res = sayService.save(sayEditDto);

        return success();
    }

    @ApiOperation(value="删除说说", notes="删除说说")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sayUid",value = "说说uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @DeleteMapping("/{sayUid}")
    public ResponseResult delete(@PathVariable("sayUid") String sayUid) {
        boolean res = sayService.removeById(sayUid);

        return success();
    }

    @ApiOperation(value = "获取说说列表", httpMethod = "GET", response = ResponseResult.class, notes = "获取说说列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页码",defaultValue = "0",paramType = "query",dataType="Long",required = false),
            @ApiImplicitParam(name = "pageSize",value = "单页长度",defaultValue = "20",paramType = "query",dataType="Long",required = false)
    })
    @GetMapping("/list")
    public ResponseResult getSayList(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Long currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Long pageSize
    ){
        String userUid = getUserUid(httpServletRequest);

        Page<Say> sayIPage = sayService.pagePublic(userUid, currentPage, pageSize);

        return ResponseResult.success(sayIPage.getRecords(), sayIPage.getTotal());
    }

    @ApiOperation(value="获取地理位置", notes="获取地理位置")
    @GetMapping("/address")
    public ResponseResult getAddress(HttpServletRequest httpServletRequest) {
        // 获取ip
        String ipAddress = IpUtils.getIpAddress(httpServletRequest);

        // 获取所在的省
        String cityInfo = IpUtils.getCityInfo(ipAddress);
        String[] split = cityInfo.split("\\|");
        if (split.length >= 2) {
            return success(split[2]);
        }

        return success(cityInfo);
    }

    @ApiOperation(value = "说说点赞", httpMethod = "POST", response = ResponseResult.class, notes = "说说点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sayUid",value = "说说uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @PostMapping("/{sayUid}/like")
    public ResponseResult like(
            HttpServletRequest httpServletRequest,
            @PathVariable(value = "sayUid") String sayUid
    ){
        String userUid = getUserUid(httpServletRequest);

        boolean res = commentService.like(sayUid, userUid, CharacterConstants.NUM_THREE);

        return success();
    }


    @ApiOperation(value = "评论", httpMethod = "POST", response = ResponseResult.class, notes = "评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sayUid",value = "说说uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @PostMapping("/{sayUid}/comment")
    public ResponseResult comment(
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

        return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ApiOperation(value = "获取评论列表", httpMethod = "GET", response = ResponseResult.class, notes = "获取评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sayUid",value = "说说uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true),
            @ApiImplicitParam(name = "currentPage",value = "当前页码",defaultValue = "0",paramType = "query",dataType="Long",required = false),
            @ApiImplicitParam(name = "pageSize",value = "单页长度",defaultValue = "20",paramType = "query",dataType="Long",required = false)
    })
    @GetMapping("/{sayUid}/comment/list")
    public ResponseResult getBlogList(
            @PathVariable(value = "sayUid") String sayUid,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Long currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Long pageSize
    ){

        Page<Comment> commentIPage = commentService.pageByOriginalUid(sayUid, CharacterConstants.NUM_TWO, currentPage, pageSize);

        return ResponseResult.success(commentIPage.getRecords(), commentIPage.getTotal());
    }
}

