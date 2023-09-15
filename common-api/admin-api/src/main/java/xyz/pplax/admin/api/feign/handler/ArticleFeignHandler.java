package xyz.pplax.admin.api.feign.handler;

import org.springframework.stereotype.Component;
import xyz.pplax.admin.api.feign.ArticleFeignService;
import xyz.pplax.article.pojo.ArticlePojo;
import xyz.pplax.core.entity.R;


@Component
public class ArticleFeignHandler implements ArticleFeignService {

    @Override
    public R insertArticle(ArticlePojo pojo) {
        return R.failure();
    }
}
