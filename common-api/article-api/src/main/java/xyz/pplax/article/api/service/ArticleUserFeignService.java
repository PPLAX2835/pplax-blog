package xyz.pplax.article.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.pplax.admin.pojo.UserPojo;
import xyz.pplax.article.api.service.handler.ArticleUserFeignHandler;
import xyz.pplax.core.entity.R;


@FeignClient(value = "pplax-admin", name = "pplax-admin", contextId = "article-user-feign", fallback = ArticleUserFeignHandler.class)
public interface ArticleUserFeignService {

    @PostMapping("/admin/user/queryUserByUid")
    R queryUserByUid(@RequestBody UserPojo pojo);
}