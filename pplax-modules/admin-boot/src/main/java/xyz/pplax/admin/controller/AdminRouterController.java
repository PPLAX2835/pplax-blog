package xyz.pplax.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.pplax.admin.pojo.AdminRouterPojo;
import xyz.pplax.admin.service.AdminRouterService;
import xyz.pplax.admin.vo.AdminRouterVO;
import xyz.pplax.core.annotaion.controller.ModifyOperation;
import xyz.pplax.core.annotaion.controller.SelectOperation;
import xyz.pplax.core.valid.Insert;
import xyz.pplax.core.valid.Update;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;

import javax.validation.groups.Default;

@RequestMapping("/admin/adminRouter")
@RestController
@Tag(name = "后台管理路由相关接口")
public class AdminRouterController {

    @Autowired
    private AdminRouterService adminRouterService;

    @ModifyOperation
    @Operation(summary = "插入路由")
    @PostMapping("/insertAdminRouter")
    public void insertAdminRouter(@Validated({Insert.class, Default.class}) @RequestBody AdminRouterPojo pojo) {
        adminRouterService.insertAdminRouter(pojo);
    }

    @ModifyOperation
    @Operation(summary = "修改路由信息")
    @PostMapping("/updateAdminRouter")
    public int updateAdminRouter(@Validated({Update.class, Default.class}) @RequestBody AdminRouterPojo pojo) {
        return adminRouterService.updateAdminRouter(pojo);
    }

    @ModifyOperation
    @Operation(summary = "删除路由")
    @PostMapping("/physicalDeleteAdminRouter")
    public int physicalDeleteAdminRouter(@RequestBody AdminRouterPojo pojo) {
        return adminRouterService.physicalDeleteAdminRouter(pojo);
    }

    @SelectOperation
    @Operation(summary = "根据uid查询路由")
    @PostMapping(value = "/queryAdminRouterByUid", produces = "application/json;charset=UTF-8")
    public AdminRouterVO queryAdminRouterByUid(@RequestBody AdminRouterPojo pojo) {
        return adminRouterService.queryAdminRouterByUid(pojo);
    }

    @SelectOperation
    @Operation(summary = "查询满足要求的所有路由信息")
    @PostMapping("/queryListAdminRouterByCondition")
    public PageData<AdminRouterVO> queryListAdminRouterByCondition(@RequestBody Condition<Long> condition) {
        return adminRouterService.queryListAdminRouterByCondition(condition);
    }
}
