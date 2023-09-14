package xyz.pplax.article.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.article.po.Bulletin;

/**
 * bulletin数据表的VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "bulletin数据表的VO")
public class BulletinVO extends Bulletin {

}