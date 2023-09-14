package xyz.pplax.article.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.article.po.Article;

import java.util.List;
import java.util.Map;

/**
 * article数据表的VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "article数据表的VO")
public class ArticleVO extends Article {

    private List<Map<String, List<ArticleVO>>> tagArticleList;
    private List<Map<String, List<ArticleVO>>> categoryArticleList;
}