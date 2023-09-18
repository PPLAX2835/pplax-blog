package xyz.pplax.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import xyz.pplax.article.po.Talk;
import xyz.pplax.article.pojo.TalkPojo;
import xyz.pplax.article.vo.TalkVO;
import xyz.pplax.core.dto.JwtUserInfo;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.article.ArticleException;
import xyz.pplax.core.exception.user.UserException;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.core.util.id.GenerateInfoUtils;
import xyz.pplax.core.util.lambda.AssertUtils;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;
import xyz.pplax.data.util.PageUtils;
import xyz.pplax.starter.properties.PPLAXProperties;
import xyz.pplax.starter.util.UserUtils;


@Service
public class TalkService {

    @Autowired
    private PPLAXTalkService pplaxTalkService;

    @Autowired
    private PPLAXProperties pplaxProperties;

    public int logicDeleteTalk(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        Talk talk = Talk.builder()
                .uid(uid)
                .delete(true)
                .build();
        return pplaxTalkService.updateById(talk);
    }

    public int physicalDeleteTalk(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return pplaxTalkService.deleteById(uid);
    }

    public void updateTalkLikeNum(TalkPojo pojo) {
        Talk talk = pplaxTalkService.queryById(pojo.getUid());
        AssertUtils.stateThrow(talk != null, () -> new ArticleException("此说说不存在"));
        TalkPojo talkPojo = BeanUtils.copyProperties(talk, TalkPojo.class);
        if (talkPojo.getLikeNumber() == null) {
            talkPojo.setLikeNumber(0);
        }
        talkPojo.setLikeNumber(talkPojo.getLikeNumber() + 1);
        pplaxTalkService.updateById(BeanUtils.copyProperties(talkPojo, Talk.class));
    }

    @Transactional
    public void insertTalk(TalkPojo pojo) {
        Assert.notNull(pojo, "说说信息不能为null");
        Talk record = BeanUtils.copyProperties(pojo, Talk.class);
        record.setUid(GenerateInfoUtils.generateUid(pplaxProperties.getSnowFlakeWorkerId(),
                pplaxProperties.getSnowFlakeDatacenterId()));
        record.setCommentUids(null);
        JwtUserInfo jwtUserInfo = UserUtils.getCurrentUser();
        AssertUtils.stateThrow(jwtUserInfo != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN));
        record.setUserUid(jwtUserInfo.getUserUid());
        pplaxTalkService.insert(record);
    }

    public PageData<TalkVO> queryListTalkByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.copyPageDataResult(pplaxTalkService.queryListByCondition(condition), TalkVO.class);
    }

    public TalkVO queryTalkByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return BeanUtils.copyProperties(pplaxTalkService.queryById(uid), TalkVO.class);
    }

    @Transactional
    public int updateTalk(TalkPojo record) {
        Assert.notNull(record, "说说信息不能为null");
        record.setUserUid(null);
        return pplaxTalkService.updateById(BeanUtils.copyProperties(record, Talk.class));
    }

    public int queryTotalTalkCount(TalkPojo pojo) {
        return pplaxTalkService.countByWhere(BeanUtils.copyProperties(pojo, Talk.class));
    }
}
