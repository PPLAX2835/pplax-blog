package xyz.pplax.pplaxblog.admin.controller;

import io.swagger.annotations.Api;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.controller.SuperController;
import xyz.pplax.pplaxblog.xo.dto.list.BlogSortGetListDto;
import xyz.pplax.pplaxblog.xo.service.blogsort.BlogSortService;

@RestController
@RequestMapping("/blogSort")
@Api(value="博客分类Controller", tags={"博客分类Controller"})
public class BlogSortController extends SuperController {

    @Autowired
    private BlogSortService blogSortService;

    private static final Logger log = LogManager.getLogger(BlogSortController.class);

    @GetMapping("/list")
    public String getList(
        @RequestParam(value = "sortName", required = false) String sortName,
        @RequestParam(value = "currentPage", required = false) Long currentPage,
        @RequestParam(value = "pageSize", required = false) Long pageSize
    ) {
        // 封装
        BlogSortGetListDto blogSortGetListDto = new BlogSortGetListDto();
        blogSortGetListDto.setSortName(sortName);
        blogSortGetListDto.setCurrentPage(currentPage);
        blogSortGetListDto.setPageSize(pageSize);

        return toJson(ResponseResult.success(
                blogSortService.list(blogSortGetListDto),
                blogSortService.count(blogSortGetListDto)
        ));
    }

}
