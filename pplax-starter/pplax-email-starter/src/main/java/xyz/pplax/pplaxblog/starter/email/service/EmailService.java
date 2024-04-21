package xyz.pplax.pplaxblog.starter.email.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import xyz.pplax.pplaxblog.starter.email.model.Mail;

import javax.mail.internet.MimeMessage;

@Component
public class EmailService {

    @Value("${spring.mail.username}")
    private String MAIL_SENDER; //邮件发送者

    @Autowired
    private JavaMailSender javaMailSender;//注入QQ发送邮件的bean

    private Logger logger = LoggerFactory.getLogger(EmailService.class);

    /**
     * 发送文本邮件
     *
     * @param mail
     */
    public void sendSimpleMail(Mail mail) {
        try {
            SimpleMailMessage mailMessage= new SimpleMailMessage();
            mailMessage.setFrom(MAIL_SENDER);//发送者
            mailMessage.setTo(mail.getRecipient());//接收者
            mailMessage.setSubject(mail.getSubject());//邮件标题
            mailMessage.setText(mail.getContent());//邮件内容
            javaMailSender.send(mailMessage);//发送邮箱
        } catch (Exception e) {
            logger.error("邮件发送失败", e.getMessage());
        }
    }

    /**
     * 发送HTML模板
     * @param mail
     */
    public void sendHTMLMail(Mail mail) {
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            //true 表示需要创建一个multipart message
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(MAIL_SENDER);//发送者
            mimeMessageHelper.setTo(mail.getRecipient());//接受者
            mimeMessageHelper.setSubject(mail.getSubject());//邮件标题
            //这里的 true，你加了的话，它发送你HTML页面里面的内容
            //不加的话，默认是 false，它发送整个HTML页面代码
            mimeMessageHelper.setText(mail.getContent(), true);
            //邮件抄送
            javaMailSender.send(mimeMailMessage);//发送邮件
        } catch (Exception e) {
            logger.error("邮件发送失败", e.getMessage());
        }
    }

}
