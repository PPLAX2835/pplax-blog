package xyz.pplax.pplaxblog.admin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.IpUtils;
import xyz.pplax.pplaxblog.commons.utils.JwtUtil;
import xyz.pplax.pplaxblog.commons.validator.group.Insert;
import xyz.pplax.pplaxblog.commons.validator.group.Update;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.edit.SayEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.SayGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Say;
import xyz.pplax.pplaxblog.xo.service.SayService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 说说表 Controller
 */
@RestController
@RequestMapping("/say")
@Api(value="说说Controller", tags={"说说Controller"})
public class SayController extends SuperController {

    @Autowired
    private SayService sayService;

    private static Logger log = LogManager.getLogger(SayController.class);

    @ApiOperation(value="获取说说列表", notes="获取说说列表")
    @GetMapping("/list")
    public String getList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "currentPage", required = false) Long currentPage,
            @RequestParam(value = "pageSize", required = false) Long pageSize
    ) {

        IPage<Say> sayIPage = sayService.page(keyword, currentPage, pageSize);

        return toJson(ResponseResult.success(sayIPage.getRecords(), sayIPage.getTotal()));
    }

    @ApiOperation(value="编辑标说说", notes="编辑说说")
    @PutMapping("/{sayUid}")
    public String update(@PathVariable("sayUid") String sayUid, @RequestBody @Validated(value = {Update.class}) SayEditDto sayEditDto) {

        sayEditDto.setUid(sayUid);
        Boolean res = sayService.updateById(sayEditDto);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ApiOperation(value="新增说说", notes="新增说说")
    @PostMapping("")
    public String add(HttpServletRequest httpServletRequest, @RequestBody @Validated(value = {Insert.class}) SayEditDto sayEditDto) {
        // 获取用户uid
        String accessToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
        JSONObject jsonObject = JSON.parseObject(payloadByBase64);
        String userUid = (String) jsonObject.get("uid");

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

    @ApiOperation(value = "批量删除说说", notes = "批量删除说说")
    @DeleteMapping(value = "")
    public String delete(@RequestBody List<String> sayUidList) {
        boolean res = sayService.removeByIds(sayUidList);

        if (res) {
            return success();
        }
        return toJson(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR));
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

}

