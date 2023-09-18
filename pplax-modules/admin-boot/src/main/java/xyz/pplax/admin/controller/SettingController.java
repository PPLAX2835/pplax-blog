package xyz.pplax.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.pplax.admin.pojo.SettingPojo;
import xyz.pplax.admin.service.SettingService;
import xyz.pplax.admin.vo.SettingVO;
import xyz.pplax.core.annotaion.controller.ModifyOperation;
import xyz.pplax.core.annotaion.controller.SelectOperation;
import xyz.pplax.core.valid.Insert;
import xyz.pplax.core.valid.Update;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;

import javax.validation.groups.Default;


@RequestMapping("/admin/sysSetting")
@RestController
@Tag(name = "系统设置controller")
public class SettingController {

    @Autowired
    private SettingService settingService;

    @ModifyOperation
    @Operation(summary = "插入系统设置")
    @PostMapping("/insertSysSetting")
    public void insertSysSetting(@Validated({Insert.class, Default.class}) @RequestBody SettingPojo pojo) {
        settingService.insertSysSetting(pojo);
    }

    @ModifyOperation
    @Operation(summary = "修改系统设置信息")
    @PostMapping("/updateSysSetting")
    public int updateSysSetting(@Validated({Update.class, Default.class}) @RequestBody SettingPojo pojo) {
        return settingService.updateSysSetting(pojo);
    }

    @ModifyOperation
    @Operation(summary = "删除系统设置")
    @PostMapping("/physicalDeleteSysSetting")
    public int physicalDeleteSysSetting(@RequestBody SettingPojo pojo) {
        return settingService.physicalDeleteSysSetting(pojo.getUid());
    }

    @SelectOperation
    @Operation(summary = "根据uid查询系统设置")
    @PostMapping(value = "/querySysSettingByUid", produces = "application/json;charset=UTF-8")
    public SettingVO querySysSettingByUid(@RequestBody SettingPojo pojo) {
        return settingService.querySysSettingByUid(pojo.getUid());
    }

    @SelectOperation
    @Operation(summary = "查询满足要求的所有系统设置")
    @PostMapping("/queryListSysSettingByCondition")
    public PageData<SettingVO> queryListSysSettingByCondition(@RequestBody Condition<Long> condition) {
        return settingService.queryListSysSettingByCondition(condition);
    }
}
