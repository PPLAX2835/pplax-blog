package xyz.pplax.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.pplax.admin.pojo.WhiteUrlPojo;
import xyz.pplax.admin.service.WhiteUrlService;
import xyz.pplax.admin.vo.WhiteUrlVO;
import xyz.pplax.core.annotaion.controller.ModifyOperation;
import xyz.pplax.core.annotaion.controller.SelectOperation;
import xyz.pplax.core.valid.Insert;
import xyz.pplax.core.valid.Update;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;

import javax.validation.groups.Default;


@Tag(name = "和白名单相关的控制器")
@RestController
@RequestMapping("/admin/whiteUrl")
public class WhiteUrlController {

    @Autowired
    private WhiteUrlService whiteUrlService;

    @PostMapping("/physicalDeleteWhiteUrl")
    @ModifyOperation
    @Operation(summary = "根据uid删除白名单")
    public int physicalDeleteWhiteUrl(@RequestBody WhiteUrlPojo record) {
        return whiteUrlService.physicalDeleteWhiteUrl(record.getUid());
    }

    @PostMapping("/batchDeleteWhiteUrl")
    @ModifyOperation
    @Operation(summary = "批量删除白名单")
    public int batchDeleteWhiteUrl(@RequestBody WhiteUrlPojo record) {
        return whiteUrlService.batchDeleteWhiteUrl(record);
    }

    @Operation(summary = "插入白名单记录")
    @ModifyOperation
    @PostMapping("/insertWhiteUrl")
    public void insertWhiteUrl(@Validated({Insert.class, Default.class}) @RequestBody WhiteUrlPojo record) {
        whiteUrlService.insertWhiteUrl(record);
    }

    /**
     * 条件查询，其中keyword为url
     * @param condition
     * @return
     */
    @Operation(summary = "根据查询条件获取所有的白名单数据")
    @PostMapping("/queryListWhiteUrlByCondition")
    @SelectOperation
    public PageData<WhiteUrlVO> queryListWhiteUrlByCondition(@RequestBody Condition<Integer> condition) {
        return whiteUrlService.queryListWhiteUrlByCondition(condition);
    }

    @PostMapping("/updateWhiteUrl")
    @Operation(summary = "修改白名单数据")
    @ModifyOperation
    public int updateWhiteUrl(@Validated({Update.class}) @RequestBody WhiteUrlPojo record) {
        return whiteUrlService.updateWhiteUrl(record);
    }
}
