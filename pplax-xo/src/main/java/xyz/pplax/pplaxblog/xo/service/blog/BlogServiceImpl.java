package xyz.pplax.pplaxblog.xo.service.blog;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.exception.DeleteFailException;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.RelativeDateFormat;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.*;
import xyz.pplax.pplaxblog.xo.dto.edit.BlogEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.BlogGetListDto;
import xyz.pplax.pplaxblog.xo.entity.*;
import xyz.pplax.pplaxblog.xo.mapper.BlogMapper;
import xyz.pplax.pplaxblog.xo.service.blogcontent.BlogContentService;
import xyz.pplax.pplaxblog.xo.service.blogsort.BlogSortService;
import xyz.pplax.pplaxblog.xo.service.collect.CollectService;
import xyz.pplax.pplaxblog.xo.service.comment.CommentService;
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
    private BlogContentService blogContentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CollectService collectService;

    @Override
    public IPage<Blog> listByBlogSort(String blogSortUid, String orderByDesc, Long currentPage, Long pageSize) {
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        if (!StringUtils.isEmpty(blogSortUid)) {
            blogQueryWrapper.eq(BlogSQLConstants.BLOG_SORT_UID, blogSortUid);
        }
        blogQueryWrapper.orderByAsc("case when status = " + EStatus.STICK.getStatus() + " then 1 else 2 end");
        if (!StringUtils.isEmpty(orderByDesc)) {
            switch (orderByDesc) {
                case "createTime":
                    blogQueryWrapper.orderByDesc(BlogSQLConstants.CREATE_TIME);
                    break;
                case "quantity":
                    blogQueryWrapper.orderByDesc(BlogSQLConstants.QUANTITY);
                    break;
            }
        }

        //分页
        Page<Blog> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        IPage<Blog> pageList = page(page, blogQueryWrapper);

        pageList.getRecords().forEach(item ->{
            // 获得标签
            String[] tagUids = item.getTagUids().split(",");
            List<String> tagUidList = Arrays.asList(tagUids);
            item.setTagList(tagService.listByIds(tagUidList));

            // 获得分类
            item.setBlogSort(blogSortService.getById(item.getBlogSortUid()));

            // 获得封面图
            item.setCoverImage(fileStorageService.getById(item.getCoverImageUid()));

            // 获得点赞量和评论量
            QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.ne(CommentSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
            commentQueryWrapper.eq(CommentSQLConstants.TYPE, CharacterConstants.NUM_ZERO);

            long likeCount = commentService.count(commentQueryWrapper);

            commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.ne(CommentSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
            commentQueryWrapper.eq(CommentSQLConstants.TYPE, CharacterConstants.NUM_ONE);

            long commentCount = commentService.count(commentQueryWrapper);

            item.setLikeCount(likeCount);
            item.setCommentCount(commentCount);

            //格式化时间为几秒前 几分钟前等
            item.setFormatCreateTime(RelativeDateFormat.format(item.getCreateTime()));
        });

        return pageList;
    }

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

    /**
     * 通过推荐排序
     * @return
     */
    @Override
    public List<Blog> listByBanner() {
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        blogQueryWrapper.ne(BlogSQLConstants.LEVEL, CharacterConstants.NUM_ZERO);             // 排除非推荐文章
        blogQueryWrapper.orderByDesc(BlogSQLConstants.LEVEL);
        blogQueryWrapper.orderByDesc(BlogSQLConstants.CREATE_TIME);

        List<Blog> blogList = list(blogQueryWrapper);

        for (Blog blog : blogList) {
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
        }

        return blogList;

    }

    /**
     * 获取指定数量的最新非推荐文章
     * @param size
     * @return
     */
    @Override
    public IPage<Blog> listNotByBannerNew(Integer size) {
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.eq(BlogSQLConstants.LEVEL, CharacterConstants.NUM_ZERO);
        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        blogQueryWrapper.orderByDesc(BlogSQLConstants.CREATE_TIME);

        //分页
        Page<Blog> page = new Page<>();
        page.setCurrent(1);
        page.setSize(size);

        IPage<Blog> pageList = page(page, blogQueryWrapper);

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

    /**
     * 通过博客uid获得博客内容
     * @param blogUid
     * @return
     */
    @Override
    public BlogContent getBlogContentByBlogUid(String blogUid) {
        Blog blog = getById(blogUid);
        return blogContentService.getById(blog.getBlogContentUid());
    }

    @Override
    public Blog getByIdWithAll(String blogUid, String userUid) {
        Blog blog = getById(blogUid);
        if (blog == null) {
            return null;
        }
        blog.setBlogContent(getBlogContentByBlogUid(blogUid));

        // 封装作者
        User user = userService.getById(blog.getUserUid());
        if (user != null) {
            user.sensitiveDataRemove();
            user.setUserInfo(userInfoService.getByUserUid(user.getUid()));
            blog.setUser(user);
        }

        // 获得分类
        blog.setBlogSort(blogSortService.getById(blog.getBlogSortUid()));

        // 添加标签列表
        String[] tagUids = blog.getTagUids().split(",");
        blog.setTagList(tagService.listByIds(Arrays.asList(tagUids)));

        // 获得点赞量和评论量
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.ne(CommentSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        commentQueryWrapper.eq(CommentSQLConstants.TYPE, CharacterConstants.NUM_ZERO);
        commentQueryWrapper.eq(CommentSQLConstants.ORIGINAL_UID, blogUid);

        long commentCount = commentService.count(commentQueryWrapper);

        commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.ne(CommentSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        commentQueryWrapper.eq(CommentSQLConstants.TYPE, CharacterConstants.NUM_ONE);
        commentQueryWrapper.eq(CommentSQLConstants.ORIGINAL_UID, blogUid);

        long likeCount = commentService.count(commentQueryWrapper);

        blog.setLikeCount(likeCount);
        blog.setCommentCount(commentCount);

        // 判断自己是否已经点赞
        boolean isLike = false;
        if (!StringUtils.isBlank(userUid)) {
            commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.ne(CommentSQLConstants.STATUS, EStatus.DISABLED.getStatus());
            commentQueryWrapper.eq(CommentSQLConstants.USER_UID, userUid);
            int count = commentService.count(commentQueryWrapper);
            if (count > 0) {
                isLike = true;
            }
        }
        blog.setIsLike(isLike);

        // 获得收藏数
        QueryWrapper<Collect> collectQueryWrapper = new QueryWrapper<>();
        collectQueryWrapper.ne(CollectSQLConstants.STATUS, EStatus.DISABLED.getStatus());
        collectQueryWrapper.eq(CollectSQLConstants.BLOG_UID, blog.getUid());
        int collectCount = collectService.count(collectQueryWrapper);
        blog.setCollectCount(collectCount);

        // 判断自己是否收藏
        boolean isCollect = false;
        if (!StringUtils.isBlank(userUid)) {
            collectQueryWrapper.eq(CollectSQLConstants.USER_UID, userUid);
            int count = collectService.count(collectQueryWrapper);
            if (count > 0) {
                isCollect = true;
            }
        }
        blog.setIsCollect(isCollect);



        return blog;
    }

    @Override
    @Transactional
    public Boolean updateById(BlogEditDto blogEditDto) {
        Blog blog = getById(blogEditDto.getUid());
        BlogContent blogContent = blogContentService.getById(blog.getBlogContentUid());

        // 封装
        blogContent.setContent(blogEditDto.getContent());

        blog.setTitle(blogEditDto.getTitle());
        blog.setBlogSortUid(blogEditDto.getBlogSortUid());
        blog.setIsOriginal(blogEditDto.getIsOriginal());
        blog.setCoverImageUid(blogEditDto.getCoverImageUid());
        blog.setArticlesPart(blogEditDto.getArticlesPart());
        blog.setSummary(blogEditDto.getSummary());
        blog.setLevel(blogEditDto.getLevel());
        blog.setTagUids(blogEditDto.getTagUids());
        blog.setStatus(blogEditDto.getStatus());

        boolean res1 = updateById(blog);
        boolean res2 = blogContentService.updateById(blogContent);

        if (!(res1 && res2)) {
            throw new RuntimeException();
        }

        return true;
    }

    @Override
    public Boolean promote(String blogUid) {
        Blog blog = getById(blogUid);
        if (blog.getStatus() != EStatus.ENABLE.getStatus()) {
            return false;
        }
        blog.setStatus(EStatus.STICK.getStatus());
        return updateById(blog);
    }

    @Override
    public Boolean promoteCancel(String blogUid) {
        Blog blog = new Blog();
        blog.setUid(blogUid);
        blog.setStatus(EStatus.ENABLE.getStatus());
        return updateById(blog);
    }

    @Override
    @Transactional
    public Blog save(BlogEditDto blogEditDto) {
        Blog blog = new Blog();
        BlogContent blogContent = new BlogContent();

        // 生成uuid
        blog.setUid(StringUtils.getUUID());
        blogContent.setUid(StringUtils.getUUID());
        blog.setBlogContentUid(blogContent.getUid());

        // 封装
        blogContent.setContent(blogEditDto.getContent());

        blog.setUserUid(blogEditDto.getUserUid());
        blog.setTitle(blogEditDto.getTitle());
        blog.setBlogSortUid(blogEditDto.getBlogSortUid());
        blog.setIsOriginal(blogEditDto.getIsOriginal());
        blog.setCoverImageUid(blogEditDto.getCoverImageUid());
        blog.setArticlesPart(blogEditDto.getArticlesPart());
        blog.setSummary(blogEditDto.getSummary());
        blog.setLevel(blogEditDto.getLevel());
        blog.setTagUids(blogEditDto.getTagUids());
        blog.setStatus(blogEditDto.getStatus());

        boolean res1 = save(blog);
        boolean res2 = blogContentService.save(blogContent);

        if (!(res1 && res2)) {
            throw new RuntimeException();
        }

        return blog;
    }

    /**
     * 通过id删除博客
     * @param blogUid
     * @return
     */
    @Override
    @Transactional
    public Boolean removeById(String blogUid) {
        Blog blog = getById(blogUid);

        boolean res1 = super.removeById(blogUid);
        boolean res2 = blogContentService.removeById(blog.getBlogContentUid());

        // 没有同时删除就回滚
        if (!(res1 && res2)) {
            throw new RuntimeException();
        }

        return true;
    }

    /**
     * 通过ids批量删除
     * @param blogUidList
     * @return
     */
    @Override
    @Transactional
    public Boolean removeByIds(List<String> blogUidList) {
        for (String uid : blogUidList) {
            Boolean res = removeById(uid);
            if (!res) {
                throw new RuntimeException();
            }
        }
        return true;
    }
}
