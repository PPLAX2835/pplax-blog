package xyz.pplax.admin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin/statistics")
@Tag(name = "统计数据api")
public class StatisticsController {

    // @ModifyOperation
    // @Operation(summary = "逻辑删除此社交信息")
    // @PostMapping("/loginDeleteSocial")
    // public int loginDeleteSocial(@RequestBody SocialPojo record) {
    //     return socialService.loginDeleteSocial(record.getUid());
    // }
}
