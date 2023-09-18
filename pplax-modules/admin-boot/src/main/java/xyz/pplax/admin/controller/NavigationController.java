package xyz.pplax.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.pplax.admin.dto.NavigationDTO;
import xyz.pplax.admin.pojo.NavigationPojo;
import xyz.pplax.admin.service.NavigationService;
import xyz.pplax.admin.vo.NavigationVO;
import xyz.pplax.core.annotaion.controller.ModifyOperation;
import xyz.pplax.core.annotaion.controller.SelectOperation;
import xyz.pplax.core.valid.Insert;
import xyz.pplax.core.valid.Update;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;

import javax.validation.groups.Default;

@RestController
@Tag(name = "操作前台导航的api")
@RequestMapping("/admin/navigation")
public class NavigationController {

    @Autowired
    private NavigationService navigationService;

    @ModifyOperation
    @PostMapping("/loginDeleteNavigation")
    @Operation(summary = "根据uid逻辑删除导航")
    public int loginDeleteNavigation(@RequestBody NavigationPojo pojo) {
        return navigationService.loginDeleteNavigation(pojo.getUid());
    }

    @Operation(summary = "物理删除")
    @PostMapping("/physicsDeleteNavigation")
    @ModifyOperation
    public int physicsDeleteNavigation(@RequestBody NavigationPojo pojo) {
        return navigationService.physicsDeleteNavigation(pojo.getUid());
    }

    @PostMapping("/insertNavigation")
    @ModifyOperation
    @Operation(summary = "插入新的导航信息")
    public void insertNavigation(@Validated({Insert.class, Default.class}) @RequestBody NavigationPojo record) {
        navigationService.insertNavigation(record);
    }

    @PostMapping("/queryListNavigationByCondition")
    @SelectOperation
    @Operation(summary = "根据条件查询")
    public PageData<NavigationVO> queryListNavigationByCondition(@RequestBody Condition<Long> condition) {
        return navigationService.queryListNavigationByCondition(condition);
    }

    @Operation(summary = "根据uid查询")
    @PostMapping("/queryNavigationByUid")
    @SelectOperation
    public NavigationVO queryNavigationByUid(@RequestBody NavigationPojo pojo) {
        return navigationService.queryNavigationByUid(pojo.getUid());
    }

    /**
     * 根据用户名，获取该用户的所有导航信息
     * @param pojo
     * @return
     */
    @SelectOperation
    @Operation(summary = "查询该用户所有的前台导航信息")
    @PostMapping("/queryAllNavigationByUserUid")
    public NavigationDTO queryAllNavigationByUserUid(@RequestBody NavigationPojo pojo) {
        return navigationService.queryAllNavigationByUserUid(pojo.getUserUid());
    }

    @PostMapping("/updateNavigation")
    @ModifyOperation
    @Operation(summary = "修改导航信息")
    public int updateNavigation(@Validated({Update.class, Default.class}) @RequestBody NavigationPojo record) {
        return navigationService.updateNavigation(record);
    }
}