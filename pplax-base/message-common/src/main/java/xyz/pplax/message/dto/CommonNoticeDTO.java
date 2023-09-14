package xyz.pplax.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发送普通通知的邮件dto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonNoticeDTO {

    private String sendMessage;
}
