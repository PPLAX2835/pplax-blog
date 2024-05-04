package xyz.pplax.pplaxblog.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.DateUtil;
import xyz.pplax.pplaxblog.commons.utils.IpUtils;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.constants.redis.SiteRedisConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.*;
import xyz.pplax.pplaxblog.xo.dto.list.TagGetListDto;
import xyz.pplax.pplaxblog.xo.entity.*;
import xyz.pplax.pplaxblog.xo.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SiteSettingService siteSettingService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogSortService blogSortService;

    @Override
    public Map<String, Object> getDashboardData() {

        Map<String, Object> dataMap = new HashMap<>();

        /*
         * 封装统计数据
         */
        // 获取博客数量
        PQueryWrapper<Blog> blogPQueryWrapper = new PQueryWrapper<>();
        int blogTotal = blogService.count(blogPQueryWrapper);

        // 获取当天访问量

        //获取访问量
        Integer siteAccess = (Integer) Optional.ofNullable(redisService.getCacheObject(SiteRedisConstants.BLOG_VIEWS_COUNT)).orElse(0);
        //获取访客量
        Integer visitorAccess = Math.toIntExact(redisService.size(SiteRedisConstants.UNIQUE_VISITOR));

        // 获取用户数
        PQueryWrapper<User> userPQueryWrapper = new PQueryWrapper<>();
        int userTotal = userService.count(userPQueryWrapper);

        // 获取评论数
        PQueryWrapper<Comment> commentPQueryWrapper = new PQueryWrapper<>();
        int commentTotal = commentService.count(commentPQueryWrapper);

        // 封装
        Map<String, Integer> statMap = new HashMap<>();
        statMap.put("blogTotal", blogTotal);
        statMap.put("siteAccess", siteAccess);
        statMap.put("visitorAccess", visitorAccess);
        statMap.put("userTotal", userTotal);
        statMap.put("commentTotal", commentTotal);
        dataMap.put("statistics", statMap);

        /*
         * 封装阅读量前五的文章
         */
        blogPQueryWrapper = new PQueryWrapper<>();
        blogPQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        blogPQueryWrapper.orderByDesc(BlogSQLConstants.CLICK_COUNT);
        Page<Blog> page = new Page<>();
        page.setCurrent(1);
        page.setSize(5);
        IPage<Blog> blogIPage = blogService.page(page, blogPQueryWrapper);
        dataMap.put("blogList", blogIPage.getRecords());

        /*
         * 封装文章分类相关数据
         */
        QueryWrapper<BlogSort> blogSortQueryWrapper = new QueryWrapper<>();
        blogSortQueryWrapper.ne(BlogSortSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        List<BlogSort> blogSortList = blogSortService.list(blogSortQueryWrapper);
        List<String> blogSortNameList = new ArrayList<>();
        List<Map> blogSortResultList = new ArrayList<>();
        for (BlogSort blogSort : blogSortList) {
            blogSortNameList.add(blogSort.getSortName());

            blogPQueryWrapper = new PQueryWrapper<>();
            blogPQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
            blogPQueryWrapper.eq(BlogSQLConstants.BLOG_SORT_UID, blogSort.getUid());
            Integer count = blogService.count(blogPQueryWrapper);

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("name", blogSort.getSortName());
            resultMap.put("value", count);
            blogSortResultList.add(resultMap);
        }
        Map<String, Object> blogSortMap = new HashMap<>();
        blogSortMap.put("blogSortNameList", blogSortNameList);
        blogSortMap.put("blogSortResultList", blogSortResultList);
        dataMap.put("blogSortList", blogSortMap);

        /*
         * 封装标签相关数据
         */
        QueryWrapper<Tag> tagQueryWrapper = new QueryWrapper<>();
        tagQueryWrapper.ne(TagSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        tagQueryWrapper.orderByDesc(TagSQLConstants.CLICK_COUNT);
        Page<Tag> tagPage = new Page<>();
        tagPage.setCurrent(1);
        tagPage.setSize(20);
        IPage<Tag> tagIPage = tagService.page(tagPage, tagQueryWrapper);
        dataMap.put("tagList", tagIPage.getRecords());

        return dataMap;
    }

    @Override
    public ResponseResult getHomeData() {
        TagGetListDto tagGetListDto = new TagGetListDto();
        tagGetListDto.setCurrentPage(1L);
        tagGetListDto.setPageSize(10L);
        tagGetListDto.setSortByCites(true);
        tagGetListDto.setSortByClickCount(true);
        IPage<Tag> tagIPage = tagService.list(tagGetListDto);

        List<Blog> bannerBlogList = blogService.listByBanner();

        IPage<Blog> notByBannerNewIpage = blogService.listNotByBannerNew(CharacterConstants.NUM_TWENTY);

        Map<String,Object> extra = new HashMap<>();
        extra.put("bannerList", bannerBlogList);
        extra.put("tagList", tagIPage.getRecords());
        extra.put("newBlogList", notByBannerNewIpage.getRecords());

        return ResponseResult.success(extra);
    }

    @Override
    public ResponseResult report(HttpServletRequest httpServletRequest) {
        // 获取ip
        String ipAddress = IpUtils.getIpAddress(httpServletRequest);
        // 通过浏览器解析工具类UserAgent获取访问设备信息
        UserAgent userAgent = IpUtils.getUserAgent(httpServletRequest);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        // 生成唯一用户标识
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        // 判断是否访问
        if (!redisService.isMember(SiteRedisConstants.UNIQUE_VISITOR, md5)) {
            // 访客量+1
            redisService.increment(SiteRedisConstants.UNIQUE_VISITOR_COUNT, 1);
            // 保存唯一标识
            redisService.add(SiteRedisConstants.UNIQUE_VISITOR, md5);
        }
        // 访问量+1
        redisService.increment(SiteRedisConstants.BLOG_VIEWS_COUNT, 1);

        return ResponseResult.success(IpUtils.getCityInfo(ipAddress));
    }

    @Override
    public ResponseResult getWebSiteInfo() {
        Map<String, SiteSetting> data = siteSettingService.map();

        Map<String, Object> extra = new HashMap<>();

        //获取访问量
        extra.put("siteAccess", Optional.ofNullable(redisService.getCacheObject(SiteRedisConstants.BLOG_VIEWS_COUNT)).orElse(0));
        //获取访客量
        extra.put("visitorAccess", redisService.size(SiteRedisConstants.UNIQUE_VISITOR));

        return ResponseResult.success(data, extra);
    }
}
