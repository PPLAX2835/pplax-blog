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
import org.springframework.web.servlet.view.RedirectView;
import xyz.pplax.pplaxblog.admin.dto.add.LinkAddDto;
import xyz.pplax.pplaxblog.admin.dto.edit.LinkEditDto;
import xyz.pplax.pplaxblog.xo.global.LinkSQLConf;
import xyz.pplax.pplaxblog.base.global.response.ResponseCode;
import xyz.pplax.pplaxblog.base.global.response.ResponseResult;
import xyz.pplax.pplaxblog.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.entity.Link;
import xyz.pplax.pplaxblog.xo.service.LinkService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("${pplax.api.base-path}/link")
@Api(value="友情链接RestApi", tags={"LinkRestApi"})
public class LinkRestApi {

    @Autowired
    private LinkService linkService;

    private static final Logger log = LogManager.getLogger(LinkRestApi.class);
    /**
     * 获取友情链接列表，get方法
     * @param request
     * @param keyword
     * @param currentPage
     * @param pageSize
     * @return
     */
    @ApiOperation(value="获取友情链接列表", notes="获取友情链接列表", response = String.class)
    @GetMapping(value = "")
    public String getList(HttpServletRequest request,
                          @ApiParam(name = "keyword", value = "关键字",required = false) @RequestParam(name = "keyword", required = false) String keyword,
                          @ApiParam(name = "currentPage", value = "当前页数",required = false) @RequestParam(name = "currentPage", required = false, defaultValue = "1") Long currentPage,
                          @ApiParam(name = "pageSize", value = "每页显示数目",required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize
    ) {

        QueryWrapper<Link> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(keyword)) {
            queryWrapper.like(LinkSQLConf.TITLE, keyword);
        }

        //分页
        Page<Link> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        // 按创建时间排序
//        queryWrapper.orderByDesc(LinkSQLConf.CREATE_TIME);

        // 查询
        IPage<Link> pageList = linkService.page(page, queryWrapper);
        List<Link> linkList = pageList.getRecords();

        log.info("返回结果");
        pageList.setRecords(linkList);

        return JSON.toJSONString(ResponseResult.success(pageList));
    }

    /**
     * 新增友情链接，post方法
     * @param request
     * @param linkAddDto
     * @return
     */
    @ApiOperation(value="增加友情链接", notes="增加友情链接", response = String.class)
    @PostMapping("")
    public String add(
            HttpServletRequest request,
            @RequestBody LinkAddDto linkAddDto
    ) {

        if(StringUtils.isEmpty(linkAddDto.getTitle()) || StringUtils.isEmpty(linkAddDto.getUrl()) || linkAddDto.getStatus() == null) {
            return JSON.toJSONString(ResponseResult.error(ResponseCode.ERROR, "必填项不能为空"));
        }

        Link link = new Link();
        link.setTitle(linkAddDto.getTitle());
        link.setSummary(linkAddDto.getSummary());
        link.setUrl(linkAddDto.getUrl());
        link.setClickCount(linkAddDto.getClickCount());
        link.setStatus(linkAddDto.getStatus());

        linkService.save(link);
        return JSON.toJSONString(ResponseResult.success("添加成功"));
    }

    /**
     * 编辑友情链接，put方法
     * @param request
     * @param uid
     * @param linkEditDto
     * @return
     */
    @ApiOperation(value="编辑友情链接", notes="编辑友情链接", response = String.class)
    @PutMapping("/{uid}")
    public String edit(
            HttpServletRequest request,
            @ApiParam(name = "uid", value = "唯一标识符",required = true) @PathVariable String uid,
            @RequestBody LinkEditDto linkEditDto
    ) {

        if(StringUtils.isEmpty(uid)) {
            return JSON.toJSONString(ResponseResult.error(ResponseCode.ERROR, "数据错误"));
        }

        if(StringUtils.isEmpty(linkEditDto.getTitle()) || StringUtils.isEmpty(linkEditDto.getUrl()) || linkEditDto.getStatus() == null) {
            return JSON.toJSONString(ResponseResult.error(ResponseCode.ERROR, "必填项不能为空"));
        }

        Link link = new Link();
        link.setUid(uid);
        link.setTitle(linkEditDto.getTitle());
        link.setSummary(linkEditDto.getSummary());
        link.setUrl(linkEditDto.getUrl());
        link.setClickCount(linkEditDto.getClickCount());
        link.setStatus(linkEditDto.getStatus());

        linkService.updateById(link);
        return JSON.toJSONString(ResponseResult.success("修改成功"));
    }

    /**
     * 物理删除友情链接 delete方法
     * @param request
     * @param uid
     * @return
     */
    @ApiOperation(value="物理删除友情链接", notes="物理删除友情链接", response = String.class)
    @DeleteMapping("/{uid}")
    public String delete(
            HttpServletRequest request,
            @ApiParam(name = "uid", value = "唯一标识符",required = true) @PathVariable String uid
    ) {

        if(StringUtils.isEmpty(uid)) {
            return JSON.toJSONString(ResponseResult.error(ResponseCode.ERROR, "数据错误"));
        }

        linkService.removeById(uid);

        return JSON.toJSONString(ResponseResult.success("删除成功"));
    }


    @ApiOperation(value="访问友情链接", notes="访问友情链接", response = String.class)
    @GetMapping("/redirect")
    public RedirectView redirectToUrl(@RequestParam(value = "url") String url, @RequestParam(value = "uid") String uid) {
        // 点击数加一
        Link link = linkService.getById(uid);
        link.setClickCount(link.getClickCount() + 1);
        linkService.updateById(link);

        // 构造重定向视图，直接将url参数作为重定向目标
        return new RedirectView(url);
    }

}
