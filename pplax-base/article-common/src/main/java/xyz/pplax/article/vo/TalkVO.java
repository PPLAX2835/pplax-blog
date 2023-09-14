package xyz.pplax.article.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.article.po.Talk;

/**
 * talk数据表的VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "talk数据表的VO")
public class TalkVO extends Talk {

}