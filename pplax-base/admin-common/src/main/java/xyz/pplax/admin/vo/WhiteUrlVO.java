package xyz.pplax.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.admin.po.WhiteUrl;

/**
 * white_url数据表的VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "white_url数据表的VO")
public class WhiteUrlVO extends WhiteUrl {

}