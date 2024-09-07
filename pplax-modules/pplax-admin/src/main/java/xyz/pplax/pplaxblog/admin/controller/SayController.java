package xyz.pplax.pplaxblog.admin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword",value = "关键词",defaultValue = "",paramType = "query",dataType="String",required = false),
            @ApiImplicitParam(name = "currentPage",value = "当前页码",defaultValue = "0",paramType = "query",dataType="Long",required = false),
            @ApiImplicitParam(name = "pageSize",value = "单页长度",defaultValue = "20",paramType = "query",dataType="Long",required = false)
    })
    @GetMapping("/list")
    public ResponseResult getList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Long currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Long pageSize
    ) {

        IPage<Say> sayIPage = sayService.page(keyword, currentPage, pageSize);

        return ResponseResult.success(sayIPage.getRecords(), sayIPage.getTotal());
    }

    @ApiOperation(value="编辑说说", notes="编辑说说")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sayUid",value = "编辑uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @PutMapping("/{sayUid}")
    public ResponseResult update(@PathVariable("sayUid") String sayUid, @RequestBody @Validated(value = {Update.class}) SayEditDto sayEditDto) {

        sayEditDto.setUid(sayUid);
        Boolean res = sayService.updateById(sayEditDto);

        return success();
    }

    @ApiOperation(value="新增说说", notes="新增说说")
    @PostMapping("")
    public ResponseResult add(HttpServletRequest httpServletRequest, @RequestBody @Validated(value = {Insert.class}) SayEditDto sayEditDto) {
        // 获取用户uid
        String accessToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        String payloadByBase64 = JwtUtil.getPayloadByBase64(accessToken);
        JSONObject jsonObject = JSON.parseObject(payloadByBase64);
        String userUid = (String) jsonObject.get("uid");

        sayEditDto.setUserUid(userUid);
        Boolean res = sayService.save(sayEditDto);

        return success();
    }

    @ApiOperation(value="删除说说", notes="删除说说")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sayUid",value = "编辑uid", defaultValue = "04f53a39f0ba4ef2a9e3b1571eb16f70",paramType = "path",dataType="String",required = true)
    })
    @DeleteMapping("/{sayUid}")
    public ResponseResult delete(@PathVariable("sayUid") String sayUid) {
        boolean res = sayService.removeById(sayUid);

        return success();
    }

    @ApiOperation(value = "批量删除说说", notes = "批量删除说说")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sayUidList",value = "说说uidList", defaultValue = "",dataType="List<String>",required = true)
    })
    @DeleteMapping(value = "")
    public ResponseResult delete(@RequestBody List<String> sayUidList) {
        boolean res = sayService.removeByIds(sayUidList);

        return success();
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

}

