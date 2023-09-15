package xyz.pplax.comment.api.service;

import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(value = "pplax-article", name = "pplax-article")
public interface CommentArticleFeignService {

}
