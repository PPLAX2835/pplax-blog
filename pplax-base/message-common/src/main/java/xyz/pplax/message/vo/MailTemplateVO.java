package xyz.pplax.message.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.message.po.MailTemplate;

/**
 * mail_template数据表的VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "mail_template数据表的VO")
public class MailTemplateVO extends MailTemplate {

}