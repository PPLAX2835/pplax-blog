package xyz.pplax.article.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.article.po.Category;

/**
 * category数据表的VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "category数据表的VO")
public class CategoryVO extends Category {

}