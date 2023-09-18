package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.pplax.admin.po.SiteSetting;
import xyz.pplax.admin.pojo.SiteSettingPojo;
import xyz.pplax.admin.vo.SiteSettingVO;
import xyz.pplax.admin.vo.UserVO;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.user.UserException;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.core.util.lambda.AssertUtils;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;
import xyz.pplax.data.util.PageUtils;

import java.util.Optional;


@Service
public class SiteSettingService {

    @Autowired
    private PPLAXSiteSettingService pplaxSiteSettingService;
    @Autowired
    private UserService userService;

    public int physicalDeleteSiteSetting(long uid) {
        return pplaxSiteSettingService.deleteById(uid);
    }

    public void insertSiteSetting(SiteSettingPojo pojo) {
        // SiteSetting siteSetting = new SiteSetting();
        // siteSetting.setUserUid(pojo.getUserUid());
        // SiteSetting queriedSiteSetting = pplaxSiteSettingService.queryOne(siteSetting);
        // AssertUtils.stateThrow(queriedSiteSetting == null, () -> new SettingException("站点设置每个用户只能存在一条记录"));
        pplaxSiteSettingService.insert(BeanUtils.copyProperties(pojo, SiteSetting.class));
    }

    public PageData<SiteSettingVO> queryListSiteSettingByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.copyPageDataResult(pplaxSiteSettingService.queryListByCondition(condition), SiteSettingVO.class);
    }

    public SiteSettingVO querySiteSettingByUid(long uid) {
        return BeanUtils.copyProperties(pplaxSiteSettingService.queryById(uid), SiteSettingVO.class);
    }

    public SiteSettingVO querySiteSettingByUserUid(long userUid) {
        SiteSetting siteSetting = new SiteSetting();
        siteSetting.setUserUid(userUid);
        return BeanUtils.copyProperties(pplaxSiteSettingService.queryOne(siteSetting), SiteSettingVO.class);
    }

    public int updateSiteSetting(SiteSettingPojo pojo) {
        Optional.ofNullable(pojo.getUserUid()).ifPresent(userUid -> {
            UserVO userVO = userService.queryUserByUid(userUid);
            AssertUtils.stateThrow(userVO != null,
                    () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
        });
        return pplaxSiteSettingService.updateById(BeanUtils.copyProperties(pojo, SiteSetting.class));
    }
}
