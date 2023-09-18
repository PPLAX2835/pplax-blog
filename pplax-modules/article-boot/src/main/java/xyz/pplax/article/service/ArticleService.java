package xyz.pplax.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.article.dao.ext.PPLAXArticleExtDao;
import xyz.pplax.article.po.Article;
import xyz.pplax.article.pojo.ArticlePojo;
import xyz.pplax.article.pojo.CategoryPojo;
import xyz.pplax.article.pojo.TagPojo;
import xyz.pplax.article.service.impl.ParseMarkdownArticleFileImpl;
import xyz.pplax.article.util.TimeUtils;
import xyz.pplax.article.vo.ArticleVO;
import xyz.pplax.article.vo.CategoryVO;
import xyz.pplax.article.vo.TagVO;
import xyz.pplax.core.constant.RedisConstant;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.article.ArticleException;
import xyz.pplax.core.exception.user.UserException;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.core.util.DateUtils;
import xyz.pplax.core.util.LogUtils;
import xyz.pplax.core.util.id.GenerateInfoUtils;
import xyz.pplax.core.util.lambda.AssertUtils;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;
import xyz.pplax.data.util.PageUtils;
import xyz.pplax.starter.properties.PPLAXProperties;
import xyz.pplax.starter.util.UserUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
public class ArticleService {

    @Autowired
    private PPLAXProperties pplaxProperties;
    @Autowired
    private PPLAXArticleService pplaxArticleService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PPLAXArticleExtDao pplaxArticleExtDao;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private final AtomicBoolean atomicUpdateLikeStatus = new AtomicBoolean(false);
    private final AtomicBoolean atomicOldLikeStatus = new AtomicBoolean(false);
    private final AtomicInteger atomicLastLikeStatus = new AtomicInteger(0);

    public int logicDeleteArticle(Long uid) {
        Assert.notNull(uid, "uid不能为null");

        // 构建一个Article对象
        Article article = Article.builder()
                .uid(uid)
                .delete(true)
                .build();
        // 逻辑删除
        return pplaxArticleService.updateById(article);
    }

    public int physicalDeleteArticle(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return pplaxArticleService.deleteById(uid);
    }

    // TODO 这里需要有一个定时任务
    @Transactional
    public Long insertArticle(ArticlePojo record) {
        Assert.notNull(record, "插入的文章数据不能为null");
        record.setUid(GenerateInfoUtils.generateUid(pplaxProperties.getSnowFlakeWorkerId(),
                pplaxProperties.getSnowFlakeDatacenterId()));
        // 创建时间是自动生成的
        record.setDelete(false);
        record.setCommentUids(null);
        setUserUid(record);
        // 查询可用的类别和分类
        setCategory(record);
        setTag(record);
        setTimingPublishTime(record);
        return pplaxArticleService.insert(BeanUtils.copyProperties(record, Article.class)).getUid();
    }

    public PageData<ArticleVO> queryListArticleByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.copyPageDataResult(pplaxArticleService.queryListByCondition(condition), ArticleVO.class);
    }

    public ArticleVO queryListArticleByTagOrCategory(ArticlePojo pojo) {
        return queryArticleByTagInfo(pojo);
    }

    public int queryTotalArticleCount(ArticlePojo pojo) {
        return pplaxArticleService.countByWhere(BeanUtils.copyProperties(pojo, Article.class));
    }

    public ArticleVO queryArticleByUid(Long uid) {
        return BeanUtils.copyProperties(pplaxArticleService.queryById(uid), ArticleVO.class);
    }

    @Transactional
    public int updateArticle(ArticlePojo record) {
        Assert.notNull(record, "文章数据不能为null");
        setCategory(record);
        setTag(record);
        record.setUserUid(null);
        setTimingPublishTime(record);
        return pplaxArticleService.updateById(BeanUtils.copyProperties(record, Article.class));
    }

    public void updateArticleLikeNum(ArticlePojo pojo) {
        String redisKey = RedisConstant.STORAGE_ARTICLE_LIKE_NUMBER + pojo.getUid();
        Article article = new Article();
        do {
            // 从Redis中获取点赞信息
            Integer redisLikeCount = (Integer) redisTemplate.opsForValue().get(redisKey);
            if (redisLikeCount == null) {
                // 使用分布式锁方式从db获取点赞信息
                redisLikeCount = getLikeCountFromDb(pojo, redisKey);
            }
            AssertUtils.stateThrow(redisLikeCount != -1, () -> new ArticleException(pojo.getUid() + " 文章不存在"));
            if (Objects.equals(pojo.getLikeStatus(), 1)) {
                // 执行点赞
                redisLikeCount++;
            }else if (Objects.equals(pojo.getLikeStatus(), 2)) {
                // 取消点赞
                if ((redisLikeCount - 1) < 0) {
                    redisLikeCount = 0;
                }else {
                    redisLikeCount--;
                }
            }else {
                // TODO 执行其他的操作，暂时没有实现
            }
            article.setLikeNumber(redisLikeCount);
            article.setUid(pojo.getUid());
        }while (!compareAndSetLikeNumber(redisKey, article, pojo.getLikeStatus()));
    }

    public void updateArticleReadNum(ArticlePojo pojo) {
        Article article = pplaxArticleService.queryById(pojo.getUid());
        AssertUtils.stateThrow(article != null, () -> new ArticleException("此文章不存在"));
        ArticlePojo articlePojo = BeanUtils.copyProperties(article, ArticlePojo.class);
        if (articlePojo.getReadNumber() == null) {
            articlePojo.setReadNumber(0);
        }
        articlePojo.setReadNumber(articlePojo.getReadNumber() + 1);
        pplaxArticleService.updateById(BeanUtils.copyProperties(articlePojo, Article.class));
    }

    /**
     * 处理导入文章
     * @param pojo
     */
    @Transactional(rollbackFor = Exception.class)
    public void importArticle(ArticlePojo pojo, List<MultipartFile> articleDataFileList) {
        AssertUtils.stateThrow(UserUtils.getCurrentUser() != null, () -> new UserException(ResponseStatusCodeEnum.PERMISSION_TOKEN_EXPIRATION));
        if (articleDataFileList == null || articleDataFileList.isEmpty()) {
            throw new ArticleException("没有需要导入的文件信息");
        }

        for (MultipartFile articleFile : articleDataFileList) {
            String originalFilename = articleFile.getOriginalFilename();
            String extName = "";
            if (StringUtils.hasLength(originalFilename)) {
                extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            }
            ArticlePojo articlePojo = null;
            if ("md".equals(extName)) {
                // 使用markdown进行解析
                ParseArticleFile parseArticleFile = new ParseMarkdownArticleFileImpl(articleFile);
                try {
                    articlePojo = parseArticleFile.parseArticle(pojo.getReservedFrontMatter(),
                            pojo.getFrontmatterCategoryName(),
                            pojo.getFolderAsCategoryName(),
                            pojo.getFrontmatterTagName(),
                            pojo.getUseFileNameAsTitle(),
                            pojo.getUseFirstArticlePictureAsCover());
                } catch (IOException e) {
                    LogUtils.logExceptionInfo(e);
                    continue;
                }
            }else {
                continue;
            }
            if (articlePojo != null) {
                insertArticle(articlePojo);
            }
        }

    }

    private void setCategory(ArticlePojo article) {
        // Long currentUserUid = UserUtils.getCurrentUserUid();
        // if (currentUserUid == null) {
        //     throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN);
        // }
        // 如果文章中的某个标签或者类别不存在，则添加
        if (!StringUtils.hasLength(article.getCategoryNames())) {
            return;
        }

        String categoryNames = Arrays.stream(article.getCategoryNames().split(","))
                .distinct()
                .filter(categoryName -> {
                    // 如果不存在，则添加
                    CategoryPojo categoryPojo = new CategoryPojo();
                    categoryPojo.setTitle(categoryName);
                    categoryPojo.setUserUid(article.getUserUid());
                    if (categoryService.queryOneCategory(categoryPojo) == null) {
                        // 不存在，添加
                        categoryService.insertCategory(categoryPojo);
                    }
                    return true;
                })
                .collect(Collectors.joining(","));
        article.setCategoryNames(categoryNames);

    }

    private void setTag(ArticlePojo article) {
        // Long currentUserUid = UserUtils.getCurrentUserUid();
        // if (currentUserUid == null) {
        //     throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN);
        // }
        if (!StringUtils.hasLength(article.getTagNames())) {
            return;
        }

        String tagNames = Arrays.stream(article.getTagNames().split(","))
                .distinct()
                .filter(tagName -> {
                    // 如果不存在，则添加
                    TagPojo tagPojo = new TagPojo();
                    tagPojo.setTitle(tagName);
                    tagPojo.setUserUid(article.getUserUid());
                    if (tagService.queryOneTag(tagPojo) == null) {
                        // 不存在，添加
                        tagService.insertTag(tagPojo);
                    }
                    return true;
                })
                .collect(Collectors.joining(","));
        article.setTagNames(tagNames);
    }

    private void setUserUid(ArticlePojo article) {
        // TODO bug
        // JwtUserInfo jwtUserInfo = UserUtils.getCurrentUser();
        // AssertUtils.stateThrow(jwtUserInfo != null,
        //         () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN));
        // article.setUserUid(jwtUserInfo.getUserUid());
        article.setUserUid(article.getUserUid());
    }

    /**
     * 判断公告对象中的定时发布时间是否规范，如果不规范，则设置为null
     * @param article
     */
    private void setTimingPublishTime(ArticlePojo article) {
        if (article.getTiming() == null || !article.getTiming()) {
            article.setTimingPublishTime(null);
            return;
        }
        if (!TimeUtils.isTimingPublishTime(article.getTimingPublishTime())) {
            article.setTiming(false);
            article.setTimingPublishTime(null);
            return;
        }
        article.setTimingPublishTime(DateUtils.parse(article.getTimingPublishTime()));
    }

    /**
     * 根据标签信息查询对应的文章
     * @param pojo
     * @return
     */
    private ArticleVO queryArticleByTagInfo(ArticlePojo pojo) {
        if (pojo.getTagTitleList() == null) {
            pojo.setTagTitleList(new ArrayList<>());
        }
        if (pojo.getCategoryTitleList() == null) {
            pojo.setCategoryTitleList(new ArrayList<>());
        }
        if (pojo.getTagUidList() != null && !pojo.getTagUidList().isEmpty()) {
            // 查询标签名
            pojo.getTagUidList().forEach(v -> {
                TagVO tagVO = tagService.queryTagByUid(v);
                if (tagVO != null) {
                    pojo.getTagTitleList().add(tagVO.getTitle());
                }
            });
        }

        if (pojo.getCategoryUidList() != null && !pojo.getCategoryUidList().isEmpty()) {
            // 查询标签名
            pojo.getCategoryUidList().forEach(v -> {
                CategoryVO categoryVO = categoryService.queryCategoryByUid(v);
                if (categoryVO != null) {
                    pojo.getCategoryTitleList().add(categoryVO.getTitle());
                }
            });
        }

        ArticleVO articleVO = new ArticleVO();
        List<Map<String, List<ArticleVO>>> tagArticleMapList = new ArrayList<>();
        List<Map<String, List<ArticleVO>>> categoryArticleMapList = new ArrayList<>();
        pojo.getTagTitleList().forEach(title -> {
            ArticlePojo articlePojo = new ArticlePojo();
            articlePojo.setTagTitleList(Collections.singletonList(title));
            if (pojo.getUserUid() != null) {
                articlePojo.setUserUid(pojo.getUserUid());
            }
            List<Article> articleList = pplaxArticleExtDao.queryListArticleByTagOrCategory(articlePojo);
            Map<String, List<ArticleVO>> map = new HashMap<>();
            map.put(title, BeanUtils.copyList(articleList, ArticleVO.class));
            tagArticleMapList.add(map);
        });
        pojo.getCategoryTitleList().forEach(title -> {
            ArticlePojo articlePojo = new ArticlePojo();
            articlePojo.setCategoryTitleList(Collections.singletonList(title));
            if (pojo.getUserUid() != null) {
                articlePojo.setUserUid(pojo.getUserUid());
            }
            List<Article> articleList = pplaxArticleExtDao.queryListArticleByTagOrCategory(articlePojo);
            Map<String, List<ArticleVO>> map = new HashMap<>();
            map.put(title, BeanUtils.copyList(articleList, ArticleVO.class));
            categoryArticleMapList.add(map);
        });
        articleVO.setTagArticleList(tagArticleMapList);
        articleVO.setCategoryArticleList(categoryArticleMapList);
        return articleVO;
    }

    private void manyFolderToSingleFile(List<File> fileList, File file) {
        if (fileList == null) {
            fileList = new ArrayList<>();
        }
        if (!file.isDirectory()) {
            fileList.add(file);
        }else {
            for (File listFile : file.listFiles()) {
                manyFolderToSingleFile(fileList, listFile);
            }
        }
    }

    private Integer getLikeCountFromDb(ArticlePojo articlePojo, String likeNumRedisKey) {
        String lockRedisKey = RedisConstant.LOCK_ARTICLE_LIKE_NUMBER + articlePojo.getUid();
        while (Boolean.FALSE.equals(redisTemplate.opsForValue().setIfAbsent(lockRedisKey, "lock", 60 * 5, TimeUnit.SECONDS))) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException("点赞操作，睡眠异常");
            }
        }
        // 执行到这里，说明该线程已经拿到分布式锁了 从数据库中获取点赞数量
        Integer likeNumTemp = (Integer) redisTemplate.opsForValue().get(likeNumRedisKey);
        if (likeNumTemp != null) {
            // 在高并发环境下，此线程已经获得了锁，如果不删除锁的话，会导致死锁
            redisTemplate.delete(lockRedisKey);
            return likeNumTemp;
        }

        // Redis中还没有
        Article article = pplaxArticleService.queryById(articlePojo.getUid());
        int likeCount = 0;
        if (article == null) {
            // -1代表不存在
            likeCount = -1;
        }else {
            likeCount = article.getLikeNumber() == null ? 0 : article.getLikeNumber();
        }
        redisTemplate.opsForValue().set(likeNumRedisKey, likeCount, 3, TimeUnit.DAYS);
        redisTemplate.delete(lockRedisKey);
        return likeCount;
    }

    private boolean compareAndSetLikeNumber(String redisKey, Article article, int likeStatus) {
        if (!atomicUpdateLikeStatus.compareAndSet(false, true)) {
            return false;
        }else {
            // 更新db
            Integer redisLikeNum = (Integer) redisTemplate.opsForValue().get(redisKey);
            if (redisLikeNum == null) {
                atomicUpdateLikeStatus.set(false);
                return false;
            }else {
                if (likeStatus == 1) {
                    if (article.getLikeNumber() <= redisLikeNum || atomicLastLikeStatus.get() == 2 && redisLikeNum != article.getLikeNumber() - 1) {
                        atomicUpdateLikeStatus.set(false);
                        return false;
                    }
                }else if (likeStatus == 2) {
                    if (article.getLikeNumber() >= redisLikeNum || atomicLastLikeStatus.get() == 1 && redisLikeNum != article.getLikeNumber() + 1) {
                        atomicUpdateLikeStatus.set(false);
                        return false;
                    }
                }
                // if (redisLikeNum.equals(article.getLikeNumber())) {
                //     atomicUpdateLikeStatus.set(false);
                //     return false;
                // }
            }
            atomicLastLikeStatus.set(likeStatus);
            int i = pplaxArticleService.updateById(article);
            if (i == 0) {
                atomicOldLikeStatus.set(false);
                atomicUpdateLikeStatus.set(false);
                return false;
            }
            redisTemplate.opsForValue().set(redisKey, article.getLikeNumber(), 3, TimeUnit.DAYS);
            atomicOldLikeStatus.set(true);
            atomicUpdateLikeStatus.set(false);

            return true;
        }
    }
}
