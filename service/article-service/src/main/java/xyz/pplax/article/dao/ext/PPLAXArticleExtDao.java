package xyz.pplax.article.dao.ext;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.article.po.Article;
import xyz.pplax.article.pojo.ArticlePojo;

import java.util.List;

@Mapper
public interface PPLAXArticleExtDao {
    public List<Article> queryListArticleByTagOrCategory(ArticlePojo pojo);

    public Article queryByIdForUpdate(Long id);
}
