package xyz.pplax.pplaxblog.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.validator.group.*;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.UserInfoEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.UserGetListDto;
import xyz.pplax.pplaxblog.xo.service.UserService;

import java.util.List;

/**
 * 用户表 Controller
 */
@RestController
@RequestMapping("/user")
@Api(value="用户Controller", tags={"用户Controller"})
public class UserController extends SuperController {

    @Autowired
    private UserService userService;

    private static final Logger log = LogManager.getLogger(UserController.class);

    @ApiOperation(value="添加用户", notes="添加用户")
    @PostMapping(value = "")
    public ResponseResult add(@RequestBody @Validated(value = {Insert.class}) UserInfoEditDto userInfoEditDto) {
        if (userService.isUsernameExist(userInfoEditDto.getUsername())) {
            return ResponseResult.error(HttpStatus.USERNAME_EXIST);
        }
        return success(userService.save(userInfoEditDto));
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @DeleteMapping(value = "/{userUid}")
    public ResponseResult delete(@PathVariable("userUid") String userUid) {
        Boolean res = userService.removeById(userUid);

        return success();
    }

    @ApiOperation(value = "批量删除用户", notes = "批量删除用户")
    @DeleteMapping(value = "")
    public ResponseResult delete(@RequestBody List<String> userUidList) {
        Boolean res = userService.removeByIds(userUidList);

        return success();
    }

    @ApiOperation(value = "踢用户下线", notes = "踢用户下线")
    @DeleteMapping(value = "/{userUid}/kick")
    public ResponseResult kickUser(@PathVariable("userUid") String userUid) {
        userService.kickUser(userUid);

        return success();
    }

    @ApiOperation(value="获取用户列表", notes="获取用户列表")
    @GetMapping(value = "/list")
    public ResponseResult getList(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    ) {

        // 封装
        UserGetListDto userGetListDto = new UserGetListDto();
        userGetListDto.setCurrentPage(currentPage);
        userGetListDto.setPageSize(pageSize);
        userGetListDto.setUsername(username);
        userGetListDto.setNickname(nickname);

        return ResponseResult.success(
                userService.listByNicknameAndUsername(userGetListDto),
                userService.countByNicknameAndUsername(userGetListDto)
        );
    }

    @ApiOperation(value="获取该用户的角色", notes="获取该用户的角色")
    @GetMapping(value = "/{userUid}/role")
    public ResponseResult getUserRole(@PathVariable("userUid") String userUid) {
        return success(userService.getRoleWithMenu(userUid));
    }


    @ApiOperation(value="判断用户名是否存在", notes="判断用户名是否存在")
    @GetMapping(value = "/exist")
    public ResponseResult isUsernameExist(@RequestParam("username") String username) {
        return success(userService.isUsernameExist(username));
    }

}
