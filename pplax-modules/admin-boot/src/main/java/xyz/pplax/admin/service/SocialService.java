package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.pplax.admin.po.Social;
import xyz.pplax.admin.pojo.SocialPojo;
import xyz.pplax.admin.vo.SocialVO;
import xyz.pplax.admin.vo.UserVO;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.user.UserException;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.core.util.lambda.AssertUtils;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;
import xyz.pplax.data.util.PageUtils;
import xyz.pplax.starter.util.UserUtils;

import java.util.Optional;


@Service
public class SocialService {

    @Autowired
    private PPLAXSocialService pplaxSocialService;
    @Autowired
    private UserService userService;

    public int loginDeleteSocial(long uid) {
        Social social = Social.builder()
                .uid(uid)
                .delete(true)
                .build();
        return pplaxSocialService.updateById(social);
    }

    public int physicalDeleteSocial(long uid) {
        return pplaxSocialService.deleteById(uid);
    }

    public void insertSocial(SocialPojo record) {
        Assert.notNull(record, "社交信息不能为null");
        // uid是自增的
        record.setUserUid(UserUtils.getCurrentUserUid());
        record.setShow(true);
       pplaxSocialService.insert(BeanUtils.copyProperties(record, Social.class));
    }

    public PageData<SocialVO> queryListSocialByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.copyPageDataResult(pplaxSocialService.queryListByCondition(condition), SocialVO.class);
    }

    public SocialVO querySocialByUid(long uid) {
        return BeanUtils.copyProperties(pplaxSocialService.queryById(uid), SocialVO.class);
    }

    public int updateSocial(SocialPojo record) {
        Assert.notNull(record, "社交信息不能为null");
        // 如果userUid存在的话，判断此用户是否存在
        Optional.ofNullable(record.getUserUid()).ifPresent(userUid -> {
            UserVO userVO = userService.queryUserByUid(userUid);
            AssertUtils.stateThrow(userVO != null,
                    () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
        });
        return pplaxSocialService.updateById(BeanUtils.copyProperties(record, Social.class));
    }
}
