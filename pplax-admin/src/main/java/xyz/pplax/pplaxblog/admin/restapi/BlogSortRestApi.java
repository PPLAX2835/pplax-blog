package xyz.pplax.pplaxblog.admin.restapi;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.admin.dto.add.BlogSortAddDto;
import xyz.pplax.pplaxblog.admin.dto.edit.BlogSortEditDto;
import xyz.pplax.pplaxblog.admin.global.BlogSQLConf;
import xyz.pplax.pplaxblog.admin.global.BlogSortSQLConf;
import xyz.pplax.pplaxblog.base.response.ResponseCode;
import xyz.pplax.pplaxblog.base.response.ResponseResult;
import xyz.pplax.pplaxblog.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.entity.*;
import xyz.pplax.pplaxblog.xo.service.BlogSortService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/blogSort")
@Api(value="博客分类RestApi", tags={"BlogSortRestApi"})
public class BlogSortRestApi {

    @Autowired
    private BlogSortService blogSortService;

    private static final Logger log = LogManager.getLogger(BlogSortRestApi.class);

    /**
     * 检查是否重名，get方法
     * @param request
     * @param sortName
     * @return
     */
    @ApiOperation(value="检查是否重名", notes="检查是否重名", response = String.class)
    @GetMapping(value = "/{sortName}/exists")
    public String checkSortNameExists(
            HttpServletRequest request,
            @ApiParam(name = "sortName", value = "分类名",required = true) @PathVariable String sortName
    ) {

        QueryWrapper<BlogSort> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BlogSortSQLConf.SORT_NAME, sortName);

        //分页
        Page<BlogSort> page = new Page<>();
        page.setCurrent(1);
        page.setSize(1);

        // 查询
        IPage<BlogSort> pageList = blogSortService.page(page, queryWrapper);
        List<BlogSort> blogSortListList = pageList.getRecords();

        Boolean result = (blogSortListList.size() == 0);
        log.info("返回结果");

        return JSON.toJSONString(ResponseResult.success(result));
    }

    /**
     * 获取博客分类列表，get方法
     * @param request
     * @param keyword
     * @param currentPage
     * @param pageSize
     * @return
     */
    @ApiOperation(value="获取博客分类列表", notes="获取博客分类列表", response = String.class)
    @GetMapping(value = "")
    public String getList(HttpServletRequest request,
                          @ApiParam(name = "keyword", value = "关键字",required = false) @RequestParam(name = "keyword", required = false) String keyword,
                          @ApiParam(name = "currentPage", value = "当前页数",required = false) @RequestParam(name = "currentPage", required = false, defaultValue = "1") Long currentPage,
                          @ApiParam(name = "pageSize", value = "每页显示数目",required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize
    ) {

        QueryWrapper<BlogSort> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(keyword)) {
            queryWrapper.like(BlogSortSQLConf.SORT_NAME, keyword);
        }

        //分页
        Page<BlogSort> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        // 按创建时间排序
        queryWrapper.orderByDesc(BlogSQLConf.CREATE_TIME);

        // 查询
        IPage<BlogSort> pageList = blogSortService.page(page, queryWrapper);
        List<BlogSort> blogSortListList = pageList.getRecords();

        for(BlogSort blogSort : blogSortListList) {
            // 添加父级分类
            if(blogSort != null && !StringUtils.isEmpty(blogSort.getParentUid())) {
                String parentUid = blogSort.getParentUid();
                BlogSort parentBlogSort = blogSortService.getById(parentUid);
                if(parentBlogSort != null) {
                    blogSort.setParentBlogSort(parentBlogSort);
                }
            }
        }

        log.info("返回结果");
        pageList.setRecords(blogSortListList);

        return JSON.toJSONString(ResponseResult.success(pageList));
    }


    /**
     *  新增博客分类，post方法
     * @param request
     * @param blogSortAddDto
     * @return
     */
    @ApiOperation(value="增加博客分类", notes="增加博客分类", response = String.class)
    @PostMapping("")
    public String add(
            HttpServletRequest request,
            @RequestBody BlogSortAddDto blogSortAddDto
    ) {

        if(StringUtils.isEmpty(blogSortAddDto.getSortName()) || blogSortAddDto.getStatus() == null) {
            return JSON.toJSONString(ResponseResult.error(ResponseCode.ERROR, "必填项不能为空"));
        }

        BlogSort blogSort = new BlogSort();
        blogSort.setSortName(blogSortAddDto.getSortName());
        blogSort.setSummary(blogSortAddDto.getSummary());
        blogSort.setContent(blogSortAddDto.getContent());
        blogSort.setStatus(blogSortAddDto.getStatus());
        blogSort.setParentUid(blogSortAddDto.getParentBlogSortUid());

        blogSortService.save(blogSort);
        return JSON.toJSONString(ResponseResult.success("添加成功"));
    }


    /**
     * 编辑博客分类，put方法
     * @param request
     * @param blogSortEditDto
     * @return
     */
    @ApiOperation(value="编辑博客分类", notes="编辑博客分类", response = String.class)
    @PutMapping("/{uid}")
    public String edit(
            HttpServletRequest request,
            @ApiParam(name = "uid", value = "唯一标识符",required = true) @PathVariable String uid,
            @RequestBody BlogSortEditDto blogSortEditDto
    ) {

        if(StringUtils.isEmpty(uid)) {
            return JSON.toJSONString(ResponseResult.error(ResponseCode.ERROR, "数据错误"));
        }

        if(StringUtils.isEmpty(blogSortEditDto.getSortName()) || blogSortEditDto.getStatus() == null) {
            return JSON.toJSONString(ResponseResult.error(ResponseCode.ERROR, "必填项不能为空"));
        }

        BlogSort blogSort = new BlogSort();
        blogSort.setUid(uid);
        blogSort.setSortName(blogSortEditDto.getSortName());
        blogSort.setSummary(blogSortEditDto.getSummary());
        blogSort.setContent(blogSortEditDto.getContent());
        blogSort.setStatus(blogSortEditDto.getStatus());
        blogSort.setParentUid(blogSortEditDto.getParentBlogSortUid());

        blogSortService.updateById(blogSort);
        return JSON.toJSONString(ResponseResult.success("修改成功"));
    }

    /**
     * 物理删除博客分类 delete方法
     * @param request
     * @param uid
     * @return
     */
    @ApiOperation(value="物理删除博客分类", notes="物理删除博客分类", response = String.class)
    @DeleteMapping("/{uid}")
    public String delete(
            HttpServletRequest request,
            @ApiParam(name = "uid", value = "唯一标识符",required = true) @PathVariable String uid
    ) {

        if(StringUtils.isEmpty(uid)) {
            return JSON.toJSONString(ResponseResult.error(ResponseCode.ERROR, "数据错误"));
        }

        blogSortService.removeById(uid);

        return JSON.toJSONString(ResponseResult.success("删除成功"));
    }
}
