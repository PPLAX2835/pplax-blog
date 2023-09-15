package xyz.pplax.api.mail.sendmail.entity;

import lombok.*;
import xyz.pplax.api.mail.sendmail.enums.SendHtmlMailTypeNameEnum;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 存储发送的邮件信息dto
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class StorageSendMailInfo {
    /**
     * 存放替换邮件模板中的key和value值，其中key不需要加上{{}},[[]]这些
     */
    private Map<String,String> replacedMap;

    /**
     * 额外的数据，比如回复评论，需要组装一个被回复的评论对象
     */
    @Setter
    private Map<String,Object> additionalData;

    /**
     * 使用哪个用户的模板，如果此模板不存在，则使用默认的
     */
    @Setter
    @NotNull
    private Long userUid;

    @Setter
    @NotNull
    private String htmlContent;

    /**
     * 发送的标题
     */
    @Setter
    private String subject;

    /**
     * 收件人的邮箱号
     */
    @Setter
    private String receiverEmail;

    /**
     * 发送邮件的类型，是收到评论，回复评论，还是验证账户等等
     */
    @Setter
    private SendHtmlMailTypeNameEnum sendType;

    /**
     * mq发送消息的唯一id
     */
    /*@Setter
    private String correlationDataId;*/

    /**
     * 如果发送的是简单文本，则需要对此属性赋值
     */
    @Setter
    private String simpleText;

    public StorageSendMailInfo(Map<String, String> replacedMap) {
        this.replacedMap = replacedMap;
    }

    public static StorageSendMailInfo newInstant(Long userUid, String subject, String receiverEmail,
                                                 SendHtmlMailTypeNameEnum sendType) {
        StorageSendMailInfo mailInfo = new StorageSendMailInfo();
        mailInfo.setUserUid(userUid);
        mailInfo.setSubject(subject);
        mailInfo.setReceiverEmail(receiverEmail);
        mailInfo.setSendType(sendType);
        return mailInfo;
    }
}
