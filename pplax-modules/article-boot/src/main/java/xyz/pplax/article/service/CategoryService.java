package xyz.pplax.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.pplax.article.po.Category;
import xyz.pplax.article.pojo.CategoryPojo;
import xyz.pplax.article.vo.CategoryVO;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.article.ArticleException;
import xyz.pplax.core.exception.user.UserException;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.core.util.lambda.AssertUtils;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;
import xyz.pplax.data.util.PageUtils;
import xyz.pplax.starter.util.UserUtils;

import java.util.Objects;


@Service
public class CategoryService {

    @Autowired
    private PPLAXCategoryService pplaxCategoryService;

    public int logicDeleteCategory(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        Category category = Category.builder()
                .uid(uid)
                .delete(true)
                .build();
        return pplaxCategoryService.updateById(category);
    }

    public int physicalDeleteCategory(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return pplaxCategoryService.deleteById(uid);
    }

    public void insertCategory(CategoryPojo record) {
        Assert.notNull(record, "类别不能为null");
        record.setDelete(false);
        // JwtUserInfo jwtUserInfo = UserUtils.getCurrentUser();
        // AssertUtils.stateThrow(jwtUserInfo != null,
        //         () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN));
        AssertUtils.stateThrow(record.getUserUid() != null, () -> new ArticleException("userUid不能为null"));
        judgeCategory(record, true);
        record.setUserUid(record.getUserUid());
        pplaxCategoryService.insert(BeanUtils.copyProperties(record, Category.class));
    }

    public PageData<CategoryVO> queryListCategoryByCondition(Condition<Long> condition) {
        Assert.notNull(condition,"查询条件不能为null");
        return PageUtils.copyPageDataResult(pplaxCategoryService.queryListByCondition(condition), CategoryVO.class);
    }

    public CategoryVO queryCategoryByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return BeanUtils.copyProperties(pplaxCategoryService.queryById(uid), CategoryVO.class);
    }

    public CategoryVO queryOneCategory(CategoryPojo pojo) {
        return BeanUtils.copyProperties(pplaxCategoryService.queryOne(BeanUtils.copyProperties(pojo, Category.class)), CategoryVO.class);
    }

    public int updateCategory(CategoryPojo record) {
        Assert.notNull(record, "类别不能为null");
        record.setUserUid(null);
        judgeCategory(record, false);
        return pplaxCategoryService.updateById(BeanUtils.copyProperties(record, Category.class));
    }

    private void judgeCategory(CategoryPojo pojo, boolean isInsert) {
        Long currentUserUid = UserUtils.getCurrentUserUid();
        if (currentUserUid == null) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN);
        }
        CategoryPojo categoryPojo = new CategoryPojo();
        categoryPojo.setUserUid(currentUserUid);
        categoryPojo.setTitle(pojo.getTitle());
        Category category = pplaxCategoryService.queryOne(BeanUtils.copyProperties(categoryPojo, Category.class));
        if (isInsert) {
            AssertUtils.stateThrow(category == null, () -> new ArticleException("存在相同的类别"));
        }
        if (category != null && !Objects.equals(category.getUid(), pojo.getUid())) {
            throw new ArticleException("存在相同的类别");
        }
    }
}
