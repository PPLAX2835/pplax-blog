package xyz.pplax.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.pplax.article.pojo.BulletinPojo;
import xyz.pplax.article.service.BulletinService;
import xyz.pplax.article.vo.BulletinVO;
import xyz.pplax.core.annotaion.controller.ModifyOperation;
import xyz.pplax.core.annotaion.controller.SelectOperation;
import xyz.pplax.core.valid.Insert;
import xyz.pplax.core.valid.Update;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;

import javax.validation.groups.Default;


@RequestMapping("/blog/bulletin")
@Tag(name = "和公告相关的控制类")
@RestController
@RefreshScope
public class BulletinController {

    @Autowired
    private BulletinService bulletinService;

    @Operation(summary = "插入公告")
    @ModifyOperation
    @PostMapping("/insertBulletin")
    public void insertBulletin(@Validated({Insert.class, Default.class}) @RequestBody BulletinPojo bulletin) {
        bulletinService.insertBulletin(bulletin);
    }

    @ModifyOperation
    @Operation(summary = "逻辑删除公告")
    @PostMapping("/logicDeleteBulletin")
    public int logicDeleteBulletin(@RequestBody BulletinPojo bulletin) {
        return bulletinService.logicDeleteBulletin(bulletin.getUid());
    }

    @Operation(summary = "物理删除公告")
    @ModifyOperation
    @PostMapping("/physicalDeleteBulletin")
    public int physicalDeleteBulletin(@RequestBody BulletinPojo bulletin) {
        return bulletinService.physicalDeleteBulletin(bulletin.getUid());
    }

    @Operation(summary = "修改公告内容")
    @ModifyOperation
    @PostMapping("/updateBulletin")
    public int updateBulletin(@Validated({Update.class, Default.class}) @RequestBody BulletinPojo bulletin) {
        return bulletinService.updateBulletin(bulletin);
    }

    @Operation(summary = "根据条件，查询满足要求的公告")
    @SelectOperation
    @PostMapping("/queryListBulletinByCondition")
    public PageData<BulletinVO> queryListBulletinByCondition(@RequestBody Condition<Long> condition) {
        return bulletinService.queryListBulletinByCondition(condition);
    }

    @Operation(summary = "根据uid查询公告")
    @SelectOperation
    @PostMapping("/queryBulletinByUid")
    public BulletinVO queryBulletinByUid(@RequestBody BulletinPojo bulletin) {
        return bulletinService.queryBulletinByUid(bulletin.getUid());
    }
}
