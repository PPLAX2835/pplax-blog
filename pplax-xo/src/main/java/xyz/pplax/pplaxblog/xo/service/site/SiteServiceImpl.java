package xyz.pplax.pplaxblog.xo.service.site;

import com.baomidou.mybatisplus.core.metadata.IPage;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.IpUtils;
import xyz.pplax.pplaxblog.starter.redis.constants.BaseRedisConstants;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.xo.constants.redis.SiteRedisConstants;
import xyz.pplax.pplaxblog.xo.dto.list.TagGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.Tag;
import xyz.pplax.pplaxblog.xo.service.blog.BlogService;
import xyz.pplax.pplaxblog.xo.service.sitesetting.SiteSettingService;
import xyz.pplax.pplaxblog.xo.service.tag.TagService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        Map<String, Object> data = siteSettingService.map();

        Map<String, Object> extra = new HashMap<>();

        //获取访问量
        extra.put("siteAccess", Optional.ofNullable(redisService.getCacheObject(SiteRedisConstants.BLOG_VIEWS_COUNT)).orElse(0));
        //获取访客量
        extra.put("visitorAccess", redisService.size(SiteRedisConstants.UNIQUE_VISITOR));

        return ResponseResult.success(data, extra);
    }
}
