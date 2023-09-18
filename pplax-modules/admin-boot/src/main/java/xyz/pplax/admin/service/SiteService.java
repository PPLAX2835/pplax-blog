package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.pplax.admin.po.Site;
import xyz.pplax.admin.pojo.SitePojo;
import xyz.pplax.admin.vo.SiteVO;
import xyz.pplax.admin.vo.UserVO;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.user.UserException;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.core.util.id.GenerateInfoUtils;
import xyz.pplax.core.util.lambda.AssertUtils;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;
import xyz.pplax.data.util.PageUtils;
import xyz.pplax.starter.properties.PPLAXProperties;
import xyz.pplax.starter.util.UserUtils;

import java.util.Optional;


@Service
public class SiteService {

    @Autowired
    private PPLAXSiteService pplaxSiteService;
    @Autowired
    private PPLAXProperties pplaxProperties;
    @Autowired
    private UserService userService;

    public int logicDeleteSite(long uid) {
        Site site = Site.builder()
                .uid(uid)
                .delete(true)
                .build();
        return pplaxSiteService.updateById(site);
    }

    public int physicalDeleteSite(long uid) {
        return pplaxSiteService.deleteById(uid);
    }

    public void insertSite(SitePojo record) {
        Assert.notNull(record, "站点信息不能为null");
        // 因为插入导航，一定要登录才能操作，所以userUid可以直接从认证对象中获取
        Long userUid = UserUtils.getCurrentUserUid();
        long uid = GenerateInfoUtils.generateUid(pplaxProperties.getSnowFlakeWorkerId(),
                pplaxProperties.getSnowFlakeDatacenterId());
        record.setUid(uid);
        record.setUserUid(userUid);
        record.setDelete(false);
        pplaxSiteService.insert(BeanUtils.copyProperties(record, Site.class));
    }

    public PageData<SiteVO> queryListSiteByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.copyPageDataResult(pplaxSiteService.queryListByCondition(condition), SiteVO.class);
    }

    public SiteVO querySiteByUid(long uid) {
        return BeanUtils.copyProperties(pplaxSiteService.queryById(uid), SiteVO.class);
    }

    public int updateSite(SitePojo record) {
        Assert.notNull(record, "站点信息不能为null");
        // 如果userUid存在的话，判断此用户是否存在
        Optional.ofNullable(record.getUserUid()).ifPresent(userUid -> {
            UserVO userVO = userService.queryUserByUid(userUid);
            AssertUtils.stateThrow(userVO != null,
                    () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
        });
        return pplaxSiteService.updateById(BeanUtils.copyProperties(record, Site.class));
    }
}
