package xyz.pplax.article.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.article.po.Tag;

/**
 * tag数据表的VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "tag数据表的VO")
public class TagVO extends Tag {

}