package xyz.pplax.pplaxblog.xo.service.blog;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.BlogSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.BlogSortSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.list.BlogGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.BlogSort;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.entity.UserInfo;
import xyz.pplax.pplaxblog.xo.mapper.BlogMapper;
import xyz.pplax.pplaxblog.xo.service.blogsort.BlogSortService;
import xyz.pplax.pplaxblog.xo.service.filestorage.FileStorageService;
import xyz.pplax.pplaxblog.xo.service.tag.TagService;
import xyz.pplax.pplaxblog.xo.service.user.UserService;
import xyz.pplax.pplaxblog.xo.service.userinfo.UserInfoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 博客表 服务实现类
 */
@Service
public class BlogServiceImpl extends SuperServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogSortService blogSortService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public IPage<Blog> list(BlogGetListDto blogGetListDto) {
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(blogGetListDto.getBlogTitle())) {
            // 博客名
            blogQueryWrapper.like(BlogSQLConstants.TITLE, "%" + blogGetListDto.getBlogTitle() + "%");
        }

        if (!StringUtils.isEmpty(blogGetListDto.getBlogSortUid())) {
            // 分类uid
            blogQueryWrapper.eq(BlogSQLConstants.BLOG_SORT_UID, blogGetListDto.getBlogSortUid());
        }

        if (!StringUtils.isEmpty(blogGetListDto.getTagUid())) {
            // 标签uid
            blogQueryWrapper.like(BlogSQLConstants.TAG_UIDS, "%" + blogGetListDto.getTagUid() + "%");
        }

        if (blogGetListDto.getStatus() != null) {
            // 状态
            blogQueryWrapper.eq(BlogSQLConstants.C_STATUS, blogGetListDto.getStatus());
        }

        //分页
        Page<Blog> page = new Page<>();
        page.setCurrent(blogGetListDto.getCurrentPage());
        page.setSize(blogGetListDto.getPageSize());

        // 获得非删除状态的
        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());

        IPage<Blog> pageList = null;

        // 按创建时间排序
        blogQueryWrapper.orderByDesc(BlogSQLConstants.C_CREATE_TIME);

        // 查询
        pageList = page(page, blogQueryWrapper);

        List<Blog> blogList = new ArrayList<>();
        for (Blog blog : page.getRecords()) {
            // 查询封面图
            blog.setCoverImage(fileStorageService.getById(blog.getCoverImageUid()));

            // 获得标签
            String[] tagUids = blog.getTagUids().split(",");
            List<String> tagUidList = Arrays.asList(tagUids);
            blog.setTagList(tagService.listByIds(tagUidList));

            // 获得分类
            blog.setBlogSort(blogSortService.getById(blog.getBlogSortUid()));

            // 添加作者
            User user = userService.getById(blog.getUserUid());
            if (user != null) {
                user.sensitiveDataRemove();
                user.setUserInfo(userInfoService.getByUserUid(user.getUid()));
                blog.setUser(user);
            }

            blogList.add(blog);
        }

        pageList.setRecords(blogList);

        return pageList;
    }
}
