package xyz.pplax.article.api.service.handler;

import org.springframework.stereotype.Component;
import xyz.pplax.admin.pojo.UserPojo;
import xyz.pplax.article.api.service.ArticleUserFeignService;
import xyz.pplax.core.entity.R;


@Component
public class ArticleUserFeignHandler implements ArticleUserFeignService {

    @Override
    public R queryUserByUid(UserPojo pojo) {
        return R.failure();
    }
}