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
import xyz.pplax.core.annotaion.controller.ModifyOperation;
import xyz.pplax.core.annotaion.controller.SelectOperation;
import xyz.pplax.core.exception.PPLAXException;
import xyz.pplax.core.valid.Insert;
import xyz.pplax.core.valid.Update;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;
import xyz.pplax.mail.api.feign.UserFeignService;
import xyz.pplax.message.pojo.EmailPojo;
import xyz.pplax.message.service.EmailService;
import xyz.pplax.message.vo.EmailVO;

import javax.validation.groups.Default;

/**
 * 操作au_email表的controller
 * @author qsyyke
 */

@Tag(name = "邮箱相关的操作")
@RestController
@RefreshScope
@RequestMapping("/message/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    UserFeignService userFeignService;

    @Operation(summary = "向数据库中插入新的邮箱记录，比如主机，授权码等")
    @ModifyOperation
    @PostMapping("/insertEmail")
    public void insertEmail(@Validated({Insert.class,Default.class}) @RequestBody EmailPojo email)
            throws BindException, PPLAXException {
        emailService.insertEmail(email);
    }

    @Operation(summary = "根据唯一uid删除某条邮箱", description = "uid为long型")
    @ModifyOperation
    @PostMapping("/physicalDeleteEmail")
    public int physicalDeleteEmail(@RequestBody EmailPojo email) {
        return emailService.physicalDeleteEmail(email.getUid());
    }

    @Operation(summary = "根据emailDO实体，更新邮箱记录", description = "必须传入uid以及delete字段")
    @ModifyOperation
    @PostMapping("/updateEmail")
    public int updateEmail(@Validated({Update.class, Default.class}) @RequestBody EmailPojo email) {
        return emailService.updateEmail(email);
    }

    @Operation(summary = "根据EmailDO实体中的字段以及分页参数查询所有数据，返回一个集合",description = "部分字段使用了模糊查询")
    @SelectOperation
    @PostMapping("/queryListEmailByCondition")
    public PageData<EmailVO> queryListEmailByCondition(@RequestBody Condition<Long> condition) {
        return emailService.queryListEmailByCondition(condition);
    }

    @Operation(summary = "根据uid查询")
    @SelectOperation
    @PostMapping("/queryEmailByUid")
    public EmailVO queryEmailByUid(@RequestBody EmailPojo email) {
        return emailService.queryEmailByUid(email.getUid());
    }

    @Operation(summary = "根据userUid进行查询")
    @SelectOperation
    @PostMapping("/queryEmailByUserUid")
    public EmailVO queryEmailByUserUid(@RequestBody EmailPojo email) {
        return emailService.queryEmailByUserUid(email.getUserUid());
    }

    @Operation(summary = "根据邮箱号进行查询")
    @SelectOperation
    @PostMapping("/queryByEmailNumber")
    public EmailVO queryByEmailNumber(@RequestBody EmailPojo email) {
        return emailService.queryByEmailNumber(email.getEmail());
    }
}
