package xyz.pplax.pplaxblog.message.consumer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.starter.amqp.constants.MqConstants;
import xyz.pplax.pplaxblog.starter.email.model.Mail;
import xyz.pplax.pplaxblog.starter.email.service.EmailService;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.xo.constants.redis.AuthRedisConstants;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: MailConsumer
 * @Author: PPLAX
 * @Date: 2024/4/20
 **/
@Component
public class MailConsumer {

    private static Logger log = LogManager.getLogger(MailConsumer.class);

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private RedisService redisService;

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = MqConstants.PPLAX_EMAIL)
    public void sendEmailCode(String email) {
        log.info("需要发送验证码的邮箱：" + email);

        Context context = new Context();
        String code = StringUtils.getRandomString(6).toUpperCase();       // 生成验证码
        context.setVariable("code", code);

        // 发送邮件
        Mail mail = new Mail();
        mail.setRecipient(email);
        mail.setSubject("邮箱验证码");
        mail.setContent(templateEngine.process("mail", context));
        emailService.sendHTMLMail(mail);

        // 缓存
        redisService.setCacheObject(
                AuthRedisConstants.EMAIL_CODE + AuthRedisConstants.SEGMENTATION + email,
                code,
                15L,
                TimeUnit.MINUTES
        );

    }


}
