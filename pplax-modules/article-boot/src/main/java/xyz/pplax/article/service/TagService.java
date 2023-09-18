package xyz.pplax.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.pplax.article.po.Tag;
import xyz.pplax.article.pojo.TagPojo;
import xyz.pplax.article.vo.TagVO;
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
public class TagService {

    @Autowired
    private PPLAXTagService pplaxTagService;

    public int logicDeleteTag(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        Tag tag = Tag.builder()
                .uid(uid)
                .delete(true)
                .build();
        return pplaxTagService.updateById(tag);
    }

    public int physicalDeleteTag(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return pplaxTagService.deleteById(uid);
    }

    public void insertTag(TagPojo pojo) {
        Assert.notNull(pojo, "标签信息不能为null");
        AssertUtils.stateThrow(pojo.getUserUid() != null, () -> new ArticleException("userUid不能为null"));
        judgeTag(pojo, true);
        Tag record = BeanUtils.copyProperties(pojo, Tag.class);
        // JwtUserInfo jwtUserInfo = UserUtils.getCurrentUser();
        // AssertUtils.stateThrow(jwtUserInfo != null,
        //         () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN));
        record.setUserUid(pojo.getUserUid());
        pplaxTagService.insert(record);
    }

    public PageData<TagVO> queryListTagByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.copyPageDataResult(pplaxTagService.queryListByCondition(condition), TagVO.class);
    }

    public TagVO queryTagByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return BeanUtils.copyProperties(pplaxTagService.queryById(uid), TagVO.class);
    }

    public TagVO queryOneTag(TagPojo pojo) {
        return BeanUtils.copyProperties(pplaxTagService.queryOne(BeanUtils.copyProperties(pojo, Tag.class)), TagVO.class);
    }

    public int updateTag(TagPojo record) {
        Assert.notNull(record, "标签信息不能为null");
        judgeTag(record, false);
        record.setUserUid(null);
        return pplaxTagService.updateById(BeanUtils.copyProperties(record, Tag.class));
    }

    private void judgeTag(TagPojo pojo, boolean isInsert) {
        Long currentUserUid = UserUtils.getCurrentUserUid();
        if (currentUserUid == null) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN);
        }
        Tag tag = pplaxTagService.queryOne(new Tag() {{
            setTitle(pojo.getTitle());
            setUserUid(currentUserUid);
        }});
        if (isInsert) {
            AssertUtils.stateThrow(tag == null, () -> new ArticleException("存在相同的标签"));
        }
        if (tag != null && !Objects.equals(tag.getUid(), pojo.getUid())) {
            throw new ArticleException("存在相同的标签");
        }
    }
}
