package xyz.pplax.api.mail.sendmail.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import xyz.pplax.amqp.api.AmqpSenderService;
import xyz.pplax.api.mail.sendmail.entity.StorageSendMailInfo;
import xyz.pplax.api.mail.sendmail.enums.SendHtmlMailTypeNameEnum;
import xyz.pplax.api.mail.sendmail.service.SendMQMessageService;
import xyz.pplax.api.mail.sendmail.util.StorageMailUtils;
import xyz.pplax.comment.po.Comment;
import xyz.pplax.core.enums.RegexEnum;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.PPLAXException;
import xyz.pplax.core.exception.email.EmailException;
import xyz.pplax.core.util.ConvertObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


@Slf4j
@Service
public class SendMQMessageServiceImpl implements SendMQMessageService {

    @Autowired
    private AmqpSenderService amqpSenderService;

    @Override
    public void sendReplyMail(Comment replyingCommentInfo, Comment repliedCommentInfo, String exchangeName,
                              String exchangeType, String routingKey) throws BindException {
        // 组装新评论对象，发送给被评论页面所对应的用户
        StorageSendMailInfo mailInfo = new StorageSendMailInfo();
        mailInfo.setSubject(replyingCommentInfo.getContent());
        mailInfo.setUserUid(repliedCommentInfo.getUserUid());

        // 如果不是回复评论的话，则直接传入userUid便可以，会通过此userUid查询对应的email，但是如果是回复评论，则需要在此处进行设置收件人邮箱
        // 优先级：receiverEmail > 通过userUid查询到的email
        mailInfo.setReceiverEmail(repliedCommentInfo.getEmail());
        mailInfo.setSendType(SendHtmlMailTypeNameEnum.REPLY_COMMENT);

        List<Map<SendHtmlMailTypeNameEnum,Object>> list = new ArrayList<>();
        Map<SendHtmlMailTypeNameEnum, Object> map = new HashMap<>();
        map.put(SendHtmlMailTypeNameEnum.RECEIVE_COMMENT,replyingCommentInfo);
        map.put(SendHtmlMailTypeNameEnum.REPLY_COMMENT, repliedCommentInfo);
        list.add(map);

        // 将组装的map集合转换成json字符串，发送到交换机
        mailInfo = StorageMailUtils.generateMailInfo(mailInfo, list);

        // 组装一个存放被回复评论对象的数据
        Map<String,Object> repliedMap = new HashMap<>();
        repliedMap.put(SendHtmlMailTypeNameEnum.ADDITIONAL_DATA.name(), replyingCommentInfo);
        mailInfo.setAdditionalData(repliedMap);
        String msgJson = ConvertObjectUtils.jsonToString(mailInfo);
        amqpSenderService.sendMQMsg(msgJson, exchangeName, routingKey, exchangeType);
    }

    @Override
    public void sendCommonMail(StorageSendMailInfo sendMailInfo, String exchangeName, String exchangeType,
                               String routingKey, List<Map<SendHtmlMailTypeNameEnum,Object>> replacedObjList) throws PPLAXException, BindException {
        sendMail(sendMailInfo, exchangeName, exchangeType, routingKey, replacedObjList);
    }

    @Override
    public void sendSimpleTextMail(StorageSendMailInfo sendMailInfo, String exchangeName, String exchangeType,
                                   String routingKey) throws PPLAXException, BindException {
        // 发送简单文本
        if (!StringUtils.hasLength(sendMailInfo.getSimpleText())) {
            // 没有简单文本，不做处理
            throw new EmailException("待发送的邮件没有内容");
        }
        sendMail(sendMailInfo, exchangeName, exchangeType, routingKey, null);
    }

    /**
     * 判断待发送的邮件对象中的数据，是否合法
     * @param mailInfo
     * @return
     */
    private void isLegitimateReceiverEmail(StorageSendMailInfo mailInfo) {
        if (!StringUtils.hasLength(mailInfo.getReceiverEmail()) && mailInfo.getUserUid() == null) {
            throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_MISSING_RECEIVER);
        }

        if (StringUtils.hasLength(mailInfo.getReceiverEmail())) {
            boolean matches = Pattern.matches(RegexEnum.MAIL_REGEX.getRegex(), mailInfo.getReceiverEmail());
            if (!matches) {
                mailInfo.setReceiverEmail(null);
            }
        }
    }

    private void sendMail(StorageSendMailInfo sendMailInfo, String exchangeName, String exchangeType,
                          String routingKey, List<Map<SendHtmlMailTypeNameEnum,Object>> replacedObjList) throws BindException {
        isLegitimateReceiverEmail(sendMailInfo);

        // 将发送的回复评论数据组装成一个map集合
        String msgJson = StorageMailUtils.generateMailJson(sendMailInfo, replacedObjList);
        amqpSenderService.sendMQMsg(msgJson, exchangeName, routingKey, exchangeType);
    }
}
