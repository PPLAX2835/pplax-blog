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
import xyz.pplax.pplaxblog.commons.utils.DateUtils;
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

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 博客表 服务实现类
 */
@Service
public class BlogServiceImpl extends SuperServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

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
    public ResponseResult archive(String userUid) {

        List<Date> dateList = blogMapper.selectCreateDateListDesc();

        List<Map> res = new ArrayList<>();

        Long total = 0L;
        for (Date date : dateList) {

            Map<String, Object> map = new HashMap<>();
            map.put("time", date);

            List<Blog> blogList = blogMapper.selectSimplifiedListByCreateDate(date, userUid);
            total += blogList.size();
            map.put("list", blogList);

            res.add(map);
        }

        return ResponseResult.success(res, total);
    }

    @Override
    public IPage<Blog> search(String keyword, Long currentPage, Long pageSize) {
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());

        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.OFF_SHELF.getStatus());          // 排除非正常状态
        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.PENDING_APPROVAL.getStatus());
        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DRAFT.getStatus());

        blogQueryWrapper.and(
                QueryWrapper -> QueryWrapper
                        .like(BlogSQLConstants.TITLE, "%" + keyword + "%")
                        .or()
                        .like(BlogSQLConstants.SUMMARY, "%" + keyword + "%")
        );

        Page<Blog> blogPage = new Page<>();
        blogPage.setCurrent(currentPage);
        blogPage.setPages(pageSize);

        Page<Blog> page = page(blogPage, blogQueryWrapper);

        // 为查询结果打上高亮
        for (Blog blog : page.getRecords()) {
            String newKeyword = "<span style=\"color:red\">" + keyword + "</span>";

            if (blog.getTitle() != null) {
                blog.setTitle(blog.getTitle().replaceAll(keyword, newKeyword));
            }

            if (blog.getSummary() != null) {
                blog.setSummary(blog.getSummary().replaceAll(keyword, newKeyword));
            }
        }

        return page;
    }

    @Override
    public IPage<Blog> listByUserUid(String userUid, Boolean isCollect, Long currentPage, Long pageSize) {
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        blogQueryWrapper.eq(BlogSQLConstants.USER_UID, userUid);

        Page<Blog> blogPage = new Page<>();
        blogPage.setCurrent(currentPage);
        blogPage.setPages(pageSize);

        if (isCollect) {
            QueryWrapper<Collect> collectQueryWrapper = new QueryWrapper<>();
            collectQueryWrapper.ne(CollectSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
            collectQueryWrapper.eq(CollectSQLConstants.USER_UID, userUid);

            //分页
            Page<Collect> collectPage = new Page<>();
            collectPage.setCurrent(currentPage);
            collectPage.setSize(pageSize);

            Page<Collect> page = collectService.page(collectPage, collectQueryWrapper);

            List<String> blogUidList = new ArrayList<>();
            blogUidList.add("");
            for (Collect collect : page.getRecords()) {
                blogUidList.add(collect.getBlogUid());
            }

            blogQueryWrapper.in(BlogSQLConstants.UID, blogUidList);
        }

        Page<Blog> pageList = page(blogPage, blogQueryWrapper);
        pageList.getRecords().forEach(item ->{
            // 获得标签
            String[] tagUids = item.getTagUids().split(",");
            List<String> tagUidList = Arrays.asList(tagUids);
            item.setTagList(tagService.listByIds(tagUidList));

            // 获得分类
            item.setBlogSort(blogSortService.getById(item.getBlogSortUid()));

            // 获得封面图
            item.setCoverImage(fileStorageService.getById(item.getCoverImageUid()));

            // 添加作者
            User user = userService.getById(item.getUserUid());
            if (user != null) {
                user.sensitiveDataRemove();
                user.setUserInfo(userInfoService.getByUserUid(user.getUid()));
                item.setUser(user);
            }

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
    public IPage<Blog> listHomeBlog(String blogSortUid, String tagUid, String orderByDesc, Long currentPage, Long pageSize) {
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());

        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.OFF_SHELF.getStatus());          // 排除非正常状态
        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.PENDING_APPROVAL.getStatus());
        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DRAFT.getStatus());

        if (!StringUtils.isEmpty(blogSortUid)) {
            blogQueryWrapper.eq(BlogSQLConstants.BLOG_SORT_UID, blogSortUid);
        }
        if (!StringUtils.isEmpty(tagUid)) {
            blogQueryWrapper.like(BlogSQLConstants.TAG_UIDS, "%" + tagUid + "%");
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

            // 添加作者
            User user = userService.getById(item.getUserUid());
            if (user != null) {
                user.sensitiveDataRemove();
                user.setUserInfo(userInfoService.getByUserUid(user.getUid()));
                item.setUser(user);
            }

            // 获得点赞量和评论量
            QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.ne(CommentSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
            commentQueryWrapper.eq(CommentSQLConstants.TYPE, CharacterConstants.NUM_ZERO);
            commentQueryWrapper.eq(CommentSQLConstants.ORIGINAL_UID, item.getUid());

            long likeCount = commentService.count(commentQueryWrapper);

            commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.ne(CommentSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
            commentQueryWrapper.eq(CommentSQLConstants.TYPE, CharacterConstants.NUM_ONE);
            commentQueryWrapper.eq(CommentSQLConstants.ORIGINAL_UID, item.getUid());

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

        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.OFF_SHELF.getStatus());          // 排除非正常状态
        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.PENDING_APPROVAL.getStatus());
        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DRAFT.getStatus());

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
        blogQueryWrapper.eq(BlogSQLConstants.C_STATUS, EStatus.ENABLE.getStatus());
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

        // 封装封面
        blog.setCoverImage(fileStorageService.getById(blog.getCoverImageUid()));

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
            commentQueryWrapper.eq(CommentSQLConstants.TYPE, CharacterConstants.NUM_ONE);
            commentQueryWrapper.eq(CommentSQLConstants.ORIGINAL_UID, blog.getUid());
            int count = commentService.count(commentQueryWrapper);
            if (count > 0) {
                isLike = true;
            }
        }
        blog.setIsLike(isLike);

        // 判断自己是否已经收藏
        boolean isCollect = false;
        if (!StringUtils.isBlank(userUid)) {
            QueryWrapper<Collect> collectQueryWrapper = new QueryWrapper<>();
            collectQueryWrapper.ne(CollectSQLConstants.STATUS, EStatus.DISABLED.getStatus());
            collectQueryWrapper.eq(CollectSQLConstants.USER_UID, userUid);
            collectQueryWrapper.eq(CollectSQLConstants.BLOG_UID, blog.getUid());
            int count = collectService.count(collectQueryWrapper);
            if (count > 0) {
                isCollect = true;
            }
        }
        blog.setIsCollect(isCollect);

        // 获得收藏数
        QueryWrapper<Collect> collectQueryWrapper = new QueryWrapper<>();
        collectQueryWrapper.ne(CollectSQLConstants.STATUS, EStatus.DISABLED.getStatus());
        collectQueryWrapper.eq(CollectSQLConstants.BLOG_UID, blog.getUid());
        int collectCount = collectService.count(collectQueryWrapper);
        blog.setCollectCount(collectCount);



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
