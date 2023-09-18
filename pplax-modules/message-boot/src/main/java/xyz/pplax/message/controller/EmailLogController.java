package xyz.pplax.message.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.pplax.core.annotaion.business.SetCondition;
import xyz.pplax.core.annotaion.controller.ModifyOperation;
import xyz.pplax.core.annotaion.controller.SelectOperation;
import xyz.pplax.core.valid.Insert;
import xyz.pplax.core.valid.Update;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;
import xyz.pplax.message.pojo.EmailLogPojo;
import xyz.pplax.message.service.EmailLogService;
import xyz.pplax.message.vo.EmailLogVO;

import javax.validation.groups.Default;

/**
 * 操作au_email_log表的controller
 */
@Tag(name = "邮件发送日志相关操作")
@RequestMapping("/message/emailLog")
@RestController
@RefreshScope
public class EmailLogController {

    @Autowired
    private EmailLogService emailLogService;

    @Operation(summary = "插入邮件发送日志")
    @ModifyOperation
    @PostMapping("/insertEmailLog")
    public void insertEmailLog(@Validated({Insert.class, Default.class}) @RequestBody EmailLogPojo emailLog) {
        emailLogService.insertEmailLog(emailLog);
    }

    @Operation(summary = "根据uid更新邮件发送日志")
    @ModifyOperation
    @PostMapping("/updateEmailLog")
    public int updateEmailLog(@Validated({Update.class,Default.class}) @RequestBody EmailLogPojo emailLog) throws BindException {
        return emailLogService.updateEmailLog(emailLog);
    }

    @Operation(summary = "删除uid对应邮件发送日志")
    @SelectOperation
    @PostMapping("/physicalDeleteEmailLog")
    public int physicalDeleteEmailLog(@RequestBody EmailLogPojo emailLog) {
        return emailLogService.physicalDeleteEmailLog(emailLog.getUid());
    }

    @Operation(summary = "查询所有邮件发送日志")
    @SelectOperation
    @PostMapping("/queryListEmailLogByCondition")
    @SetCondition(otherUid = "userUid")
    public PageData<EmailLogVO> queryListEmailLogByCondition(@RequestBody Condition<Long> condition) {
        return emailLogService.queryListEmailLogByCondition(condition);
    }

    @Operation(summary = "查询邮件数量")
    @SelectOperation
    @PostMapping("/queryTotalEmailLogCount")
    public Integer queryCommentCount(@RequestBody EmailLogPojo pojo) {
        return emailLogService.queryTotalEmailLogCount(pojo);
    }
}