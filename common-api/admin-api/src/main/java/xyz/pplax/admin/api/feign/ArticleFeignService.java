package xyz.pplax.admin.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.pplax.admin.api.feign.handler.ArticleFeignHandler;
import xyz.pplax.article.pojo.ArticlePojo;
import xyz.pplax.core.entity.R;


@FeignClient(value = "pplax-article", contextId = "admin-pplax-article", fallback = ArticleFeignHandler.class)
public interface ArticleFeignService {

    @PostMapping("/blog/article/insertArticle")
    R insertArticle (@RequestBody ArticlePojo pojo);
}
