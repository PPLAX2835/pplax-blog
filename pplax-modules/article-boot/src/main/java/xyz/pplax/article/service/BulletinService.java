package xyz.pplax.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.pplax.article.po.Bulletin;
import xyz.pplax.article.pojo.BulletinPojo;
import xyz.pplax.article.util.TimeUtils;
import xyz.pplax.article.vo.BulletinVO;
import xyz.pplax.core.dto.JwtUserInfo;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.user.UserException;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.core.util.DateUtils;
import xyz.pplax.core.util.id.GenerateInfoUtils;
import xyz.pplax.core.util.lambda.AssertUtils;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;
import xyz.pplax.data.util.PageUtils;
import xyz.pplax.starter.properties.PPLAXProperties;
import xyz.pplax.starter.util.UserUtils;


@Service
public class BulletinService {

    @Autowired
    private PPLAXProperties pplaxProperties;

    @Autowired
    private PPLAXBulletinService pplaxBulletinService;

    public int logicDeleteBulletin(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        Bulletin bulletin = Bulletin.builder()
                .delete(true)
                .uid(uid)
                .build();
        return pplaxBulletinService.updateById(bulletin);
    }

    public int physicalDeleteBulletin(Long uid) {
        return pplaxBulletinService.deleteById(uid);
    }

    public void insertBulletin(BulletinPojo record) {
        Assert.notNull(record, "公告不能为null");
        record.setCreateTime(null);
        record.setDelete(false);
        record.setUid(GenerateInfoUtils.generateUid(pplaxProperties.getSnowFlakeWorkerId(),
                pplaxProperties.getSnowFlakeDatacenterId()));
        setTimingPublishTime(record);
        JwtUserInfo jwtUserInfo = UserUtils.getCurrentUser();
        AssertUtils.stateThrow(jwtUserInfo != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN));
        record.setUserUid(jwtUserInfo.getUserUid());
        pplaxBulletinService.insert(BeanUtils.copyProperties(record, Bulletin.class));
    }

    public PageData<BulletinVO> queryListBulletinByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.copyPageDataResult(pplaxBulletinService.queryListByCondition(condition), BulletinVO.class);
    }

    public BulletinVO queryBulletinByUid(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return BeanUtils.copyProperties(pplaxBulletinService.queryById(uid), BulletinVO.class);
    }

    public int updateBulletin(BulletinPojo record) {
        Assert.notNull(record, "公告信息不能为null");
        record.setUserUid(null);
        setTimingPublishTime(record);
        return pplaxBulletinService.updateById(BeanUtils.copyProperties(record, Bulletin.class));
    }

    /**
     * 判断公告对象中的定时发布时间是否规范，如果不规范，则设置为null
     * @param bulletin
     */
    private void setTimingPublishTime(BulletinPojo bulletin) {
        if (bulletin.getTiming() == null || !bulletin.getTiming()) {
            bulletin.setTimingPublishTime(null);
            return;
        }
        if (!TimeUtils.isTimingPublishTime(bulletin.getTimingPublishTime())) {
            bulletin.setTiming(false);
            bulletin.setTimingPublishTime(null);
            return;
        }
        bulletin.setTimingPublishTime(DateUtils.parse(bulletin.getTimingPublishTime()));
    }

}
