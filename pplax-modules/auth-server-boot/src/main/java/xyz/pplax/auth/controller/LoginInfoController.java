package xyz.pplax.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.pplax.auth.constant.OauthJwtConstant;
import xyz.pplax.auth.pojo.LoginInfoPojo;
import xyz.pplax.auth.service.LoginInfoService;
import xyz.pplax.auth.vo.LoginInfoVO;
import xyz.pplax.core.annotaion.FieldFilter;
import xyz.pplax.core.annotaion.business.SetCondition;
import xyz.pplax.core.annotaion.controller.ModifyOperation;
import xyz.pplax.core.annotaion.controller.SelectOperation;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;


@Tag(name = "登录日志")
@RequestMapping("/login/loginInfo")
@RestController
public class LoginInfoController {

    @Autowired
    private LoginInfoService loginInfoService;

    @ModifyOperation
    @Operation(summary = "根据uid删除登录日志")
    @PostMapping("/physicalDeleteLoginInfo")
    public int physicalDeleteLoginInfo(@RequestBody LoginInfoPojo pojo) {
        return loginInfoService.physicalDeleteLoginInfo(pojo.getUid());
    }

    @ModifyOperation
    @Operation(summary = "根据uid，批量删除")
    @PostMapping("/batchDeleteLoginInfoByUid")
    public int batchDeleteLoginInfoByUid(@RequestBody LoginInfoPojo pojo) {
        return loginInfoService.batchDeleteLoginInfoByUid(pojo);
    }

    @FieldFilter(value = LoginInfoVO.class, excludeFields = {"","",""},
            ignoreRole = {OauthJwtConstant.SUPER_ADMINISTRATOR_ROLE_NAME})
    @SelectOperation
    @Operation(summary = "根据条件查询")
    @PostMapping("/queryListLoginInfoByCondition")
    @SetCondition(keyword = "username")
    public PageData<LoginInfoVO> queryListLoginInfoByCondition(@RequestBody Condition<Long> condition) {
        return loginInfoService.queryListLoginInfoByCondition(condition);
    }

    @FieldFilter(value = LoginInfoVO.class, excludeFields = {"loginIp","loginLocation","username"})
    @SelectOperation
    @Operation(summary = "根据用户名查询")
    @PostMapping("/queryLoginInfoByUsername")
    public LoginInfoVO queryLoginInfoByUsername(@RequestBody LoginInfoPojo pojo) {
        return loginInfoService.queryLoginInfoByUsername(pojo.getUsername());
    }
}
