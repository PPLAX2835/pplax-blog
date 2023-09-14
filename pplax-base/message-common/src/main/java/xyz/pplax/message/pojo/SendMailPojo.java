package xyz.pplax.message.pojo;

import lombok.Data;


@Data
public class SendMailPojo {

    private String receiverEmail;
    private String subject;
    private String content;

    private Long emailLogUid;

    private Long userUid;
}
