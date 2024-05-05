package xyz.pplax.pplaxblog.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.RelativeDateFormat;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.constants.sql.*;
import xyz.pplax.pplaxblog.xo.dto.edit.BlogEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.BlogGetListDto;
import xyz.pplax.pplaxblog.xo.entity.*;
import xyz.pplax.pplaxblog.xo.mapper.BlogMapper;
import xyz.pplax.pplaxblog.xo.service.BlogService;
import xyz.pplax.pplaxblog.xo.service.BlogContentService;
import xyz.pplax.pplaxblog.xo.service.BlogSortService;
import xyz.pplax.pplaxblog.xo.service.CollectService;
import xyz.pplax.pplaxblog.xo.service.CommentService;
import xyz.pplax.pplaxblog.xo.service.FileStorageService;
import xyz.pplax.pplaxblog.xo.service.TagService;
import xyz.pplax.pplaxblog.xo.service.UserService;
import xyz.pplax.pplaxblog.xo.service.UserInfoService;

import java.util.*;

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
    public Page<Blog> search(String keyword, Long currentPage, Long pageSize) {
        PQueryWrapper<Blog> blogPQueryWrapper = new PQueryWrapper<>();

        blogPQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.OFF_SHELF.getStatus());          // 排除非正常状态
        blogPQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.PENDING_APPROVAL.getStatus());
        blogPQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DRAFT.getStatus());

        blogPQueryWrapper.and(
                QueryWrapper -> QueryWrapper
                        .like(BlogSQLConstants.TITLE, "%" + keyword + "%")
                        .or()
                        .like(BlogSQLConstants.SUMMARY, "%" + keyword + "%")
        );

        Page<Blog> blogPage = new Page<>();
        blogPage.setCurrent(currentPage);
        blogPage.setPages(pageSize);

        Page<Blog> page = page(blogPage, blogPQueryWrapper);

        // 为查询结果打上高亮
        for (Blog blog : page.getRecords()) {

            if (blog.getTitle() != null) {
                String title = blog.getTitle();
                // 获取所有关键词
                ArrayList<String> allMatchingStrings = getAllMatchingStrings(title, keyword);
                // 遍历，全打上高亮
                for (String matchingString : allMatchingStrings) {
                    String newKeyword = "<span style=\"color:red\">" + matchingString + "</span>";
                    blog.setTitle(title.replace(matchingString, newKeyword));
                }
            }

            if (blog.getSummary() != null) {
                String summary = blog.getSummary();
                // 获取所有关键词
                ArrayList<String> allMatchingStrings = getAllMatchingStrings(summary, keyword);
                // 遍历，全打上高亮
                for (String matchingString : allMatchingStrings) {
                    String newKeyword = "<span style=\"color:red\">" + matchingString + "</span>";
                    blog.setSummary(summary.replace(matchingString, newKeyword));
                }
            }
        }

        return page;
    }

    /**
     * 通过用户uid查询
     * @param userUid
     * @param isCollect
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<Blog> pageByUserUid(String userUid, Boolean isCollect, Long currentPage, Long pageSize) {
        PQueryWrapper<Blog> blogPQueryWrapper = new PQueryWrapper<>();
        blogPQueryWrapper.eq(BlogSQLConstants.USER_UID, userUid);

        Page<Blog> blogPage = new Page<>();
        blogPage.setCurrent(currentPage);
        blogPage.setPages(pageSize);

        // 插叙你收藏列表
        if (isCollect) {
            PQueryWrapper<Collect> collectPQueryWrapper = new PQueryWrapper<>();
            collectPQueryWrapper.eq(CollectSQLConstants.USER_UID, userUid);

            //分页
            Page<Collect> collectPage = new Page<>();
            collectPage.setCurrent(currentPage);
            collectPage.setSize(pageSize);

            Page<Collect> page = collectService.page(collectPage, collectPQueryWrapper);

            List<String> blogUidList = new ArrayList<>();
            blogUidList.add("");
            for (Collect collect : page.getRecords()) {
                blogUidList.add(collect.getBlogUid());
            }

            blogPQueryWrapper.in(BlogSQLConstants.UID, blogUidList);
        }

        Page<Blog> pageList = page(blogPage, blogPQueryWrapper);
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
            PQueryWrapper<Comment> commentPQueryWrapper = new PQueryWrapper<>();
            commentPQueryWrapper.eq(CommentSQLConstants.TYPE, CharacterConstants.NUM_ZERO);

            long likeCount = commentService.count(commentPQueryWrapper);

            commentPQueryWrapper = new PQueryWrapper<>();
            commentPQueryWrapper.eq(CommentSQLConstants.TYPE, CharacterConstants.NUM_ONE);

            long commentCount = commentService.count(commentPQueryWrapper);

            item.setLikeCount(likeCount);
            item.setCommentCount(commentCount);

            //格式化时间为几秒前 几分钟前等
            item.setFormatCreateTime(RelativeDateFormat.format(item.getCreateTime()));
        });

        return pageList;
    }

    /**
     * 获取首页的博客列表
     * @param blogSortUid
     * @param tagUid
     * @param orderByDesc
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<Blog> pageHomeBlog(String blogSortUid, String tagUid, String orderByDesc, Long currentPage, Long pageSize) {
        PQueryWrapper<Blog> blogPQueryWrapper = new PQueryWrapper<>();

        blogPQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.OFF_SHELF.getStatus());          // 排除非正常状态
        blogPQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.PENDING_APPROVAL.getStatus());
        blogPQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DRAFT.getStatus());

        if (!StringUtils.isEmpty(blogSortUid)) {
            // 通过分类查询
            blogPQueryWrapper.eq(BlogSQLConstants.BLOG_SORT_UID, blogSortUid);
        }
        if (!StringUtils.isEmpty(tagUid)) {
            // 通过标签插叙你
            blogPQueryWrapper.like(BlogSQLConstants.TAG_UIDS, "%" + tagUid + "%");
        }
        blogPQueryWrapper.orderByAsc("case when status = " + EStatus.STICK.getStatus() + " then 1 else 2 end");
        if (!StringUtils.isEmpty(orderByDesc)) {
            switch (orderByDesc) {
                case "createTime":
                    blogPQueryWrapper.orderByDesc(BlogSQLConstants.CREATE_TIME);
                    break;
                case "quantity":
                    blogPQueryWrapper.orderByDesc(BlogSQLConstants.QUANTITY);
                    break;
            }
        }

        //分页
        Page<Blog> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        Page<Blog> pageList = page(page, blogPQueryWrapper);

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
            PQueryWrapper<Comment> commentPQueryWrapper = new PQueryWrapper<>();
            commentPQueryWrapper.eq(CommentSQLConstants.TYPE, CharacterConstants.NUM_ZERO);
            commentPQueryWrapper.eq(CommentSQLConstants.ORIGINAL_UID, item.getUid());

            long likeCount = commentService.count(commentPQueryWrapper);

            commentPQueryWrapper = new PQueryWrapper<>();
            commentPQueryWrapper.eq(CommentSQLConstants.TYPE, CharacterConstants.NUM_ONE);
            commentPQueryWrapper.eq(CommentSQLConstants.ORIGINAL_UID, item.getUid());

            long commentCount = commentService.count(commentPQueryWrapper);

            item.setLikeCount(likeCount);
            item.setCommentCount(commentCount);

            //格式化时间为几秒前 几分钟前等
            item.setFormatCreateTime(RelativeDateFormat.format(item.getCreateTime()));
        });

        return pageList;
    }

    @Override
    public Page<Blog> page(String blogTitle, String blogSortUid, String tagUid, Integer status, Long currentPage, Long pageSize) {
        PQueryWrapper<Blog> blogPQueryWrapper = new PQueryWrapper<>();

        if (!StringUtils.isEmpty(blogTitle)) {
            // 博客名
            blogPQueryWrapper.like(BlogSQLConstants.TITLE, "%" + blogTitle + "%");
        }

        if (!StringUtils.isEmpty(blogSortUid)) {
            // 分类uid
            blogPQueryWrapper.eq(BlogSQLConstants.BLOG_SORT_UID, blogSortUid);
        }

        if (!StringUtils.isEmpty(tagUid)) {
            // 标签uid
            blogPQueryWrapper.like(BlogSQLConstants.TAG_UIDS, "%" + tagUid + "%");
        }

        if (status != null) {
            // 状态
            blogPQueryWrapper.eq(BlogSQLConstants.C_STATUS, status);
        }

        //分页
        Page<Blog> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        // 按创建时间排序
        blogPQueryWrapper.orderByDesc(BlogSQLConstants.C_CREATE_TIME);

        // 查询
        Page<Blog> pageList = page(page, blogPQueryWrapper);

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
        }

        return pageList;
    }

    /**
     * 通过推荐排序
     * @return
     */
    @Override
    public List<Blog> listByBanner() {
        PQueryWrapper<Blog> blogPQueryWrapper = new PQueryWrapper<>();
        blogPQueryWrapper.ne(BlogSQLConstants.LEVEL, CharacterConstants.NUM_ZERO);             // 排除非推荐文章

        blogPQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.OFF_SHELF.getStatus());          // 排除非正常状态
        blogPQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.PENDING_APPROVAL.getStatus());
        blogPQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DRAFT.getStatus());

        blogPQueryWrapper.orderByDesc(BlogSQLConstants.LEVEL);
        blogPQueryWrapper.orderByDesc(BlogSQLConstants.CREATE_TIME);

        List<Blog> blogList = list(blogPQueryWrapper);

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
    public Page<Blog> pageNotByBannerNew(Integer size) {
        PQueryWrapper<Blog> blogPQueryWrapper = new PQueryWrapper<>();
        blogPQueryWrapper.eq(BlogSQLConstants.LEVEL, CharacterConstants.NUM_ZERO);
        blogPQueryWrapper.eq(BlogSQLConstants.C_STATUS, EStatus.ENABLE.getStatus());
        blogPQueryWrapper.orderByDesc(BlogSQLConstants.CREATE_TIME);

        //分页
        Page<Blog> page = new Page<>();
        page.setCurrent(1);
        page.setSize(size);

        Page<Blog> pageList = page(page, blogPQueryWrapper);

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
        }

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
        PQueryWrapper<Comment> commentPQueryWrapper = new PQueryWrapper<>();
        commentPQueryWrapper.eq(CommentSQLConstants.TYPE, CharacterConstants.NUM_ZERO);
        commentPQueryWrapper.eq(CommentSQLConstants.ORIGINAL_UID, blogUid);

        long commentCount = commentService.count(commentPQueryWrapper);

        commentPQueryWrapper = new PQueryWrapper<>();
        commentPQueryWrapper.eq(CommentSQLConstants.TYPE, CharacterConstants.NUM_ONE);
        commentPQueryWrapper.eq(CommentSQLConstants.ORIGINAL_UID, blogUid);

        long likeCount = commentService.count(commentPQueryWrapper);

        blog.setLikeCount(likeCount);
        blog.setCommentCount(commentCount);

        // 判断自己是否已经点赞
        boolean isLike = false;
        if (!StringUtils.isBlank(userUid)) {
            commentPQueryWrapper = new PQueryWrapper<>();
            commentPQueryWrapper.eq(CommentSQLConstants.USER_UID, userUid);
            commentPQueryWrapper.eq(CommentSQLConstants.TYPE, CharacterConstants.NUM_ONE);
            commentPQueryWrapper.eq(CommentSQLConstants.ORIGINAL_UID, blog.getUid());
            int count = commentService.count(commentPQueryWrapper);
            if (count > 0) {
                isLike = true;
            }
        }
        blog.setIsLike(isLike);

        // 判断自己是否已经收藏
        boolean isCollect = false;
        if (!StringUtils.isBlank(userUid)) {
            PQueryWrapper<Collect> collectPQueryWrapper = new PQueryWrapper<>();
            collectPQueryWrapper.eq(CollectSQLConstants.USER_UID, userUid);
            collectPQueryWrapper.eq(CollectSQLConstants.BLOG_UID, blog.getUid());
            int count = collectService.count(collectPQueryWrapper);
            if (count > 0) {
                isCollect = true;
            }
        }
        blog.setIsCollect(isCollect);

        // 获得收藏数
        PQueryWrapper<Collect> collectPQueryWrapper = new PQueryWrapper<>();
        collectPQueryWrapper.eq(CollectSQLConstants.BLOG_UID, blog.getUid());
        int collectCount = collectService.count(collectPQueryWrapper);
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


    /**
     * 获取所有mainString中与targetString相同的字符串，匹配时忽略大小写
     * @param mainString
     * @param targetString
     * @return
     */
    public static ArrayList<String> getAllMatchingStrings(String mainString, String targetString) {
        ArrayList<String> matchingStrings = new ArrayList<>();
        String lowerMainString = mainString.toLowerCase();
        String lowerTargetString = targetString.toLowerCase();

        int index = lowerMainString.indexOf(lowerTargetString);
        while (index != -1) {
            matchingStrings.add(mainString.substring(index, index + targetString.length()));
            index = lowerMainString.indexOf(lowerTargetString, index + 1);
        }

        return matchingStrings;
    }
}
