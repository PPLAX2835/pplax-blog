package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import xyz.pplax.admin.dto.NavigationDTO;
import xyz.pplax.admin.po.Navigation;
import xyz.pplax.admin.pojo.NavigationPojo;
import xyz.pplax.admin.vo.NavigationVO;
import xyz.pplax.admin.vo.UserVO;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.comment.CommentException;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
public class NavigationService {

    @Autowired
    private PPLAXProperties pplaxProperties;
    @Autowired
    private UserService userService;

    @Autowired
    private PPLAXNavigationService pplaxNavigationService;

    public int loginDeleteNavigation(long uid) {
        Navigation navigation = new Navigation();
        navigation.setUid(uid);
        navigation.setUpdateTime(DateUtils.format());
        navigation.setDelete(true);
        return pplaxNavigationService.updateById(navigation);
    }

    public int physicsDeleteNavigation(long uid) {
        return pplaxNavigationService.deleteById(uid);
    }

    @Transactional
    public void insertNavigation(NavigationPojo record) {
        Assert.notNull(record, "导航信息不能为null");
        record.setUserUid(UserUtils.getCurrentUserUid());
        record.setDelete(false);
        record.setExternal(false);
        record.setShow(true);
        record.setUid(GenerateInfoUtils.generateUid(pplaxProperties.getSnowFlakeWorkerId(),
                pplaxProperties.getSnowFlakeDatacenterId()));

        // 插入新的导航，只能将这个新添加的导航添加到已存在的导航上，不能连带着添加该导航的子导航
        record.setSonNavUids(null);
        setEffectiveNavigationUid(record, true);
        int insertNum = pplaxNavigationService.batchInsert(Collections.singletonList(BeanUtils.copyProperties(record, Navigation.class)));
        // 如果增加成功，并且存在父导航，则修改父导航的子导航数据
        if (insertNum == 1 && record.getParentNavUid() != null) {
            addSonNavigationUids(record.getParentNavUid(), record.getUid());
        }
    }

    public PageData<NavigationVO> queryListNavigationByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.copyPageDataResult(pplaxNavigationService.queryListByCondition(condition), NavigationVO.class);
    }

    public NavigationVO queryNavigationByUid(long uid) {
        return BeanUtils.copyProperties(pplaxNavigationService.queryById(uid), NavigationVO.class);
    }

    public NavigationDTO queryAllNavigationByUserUid(long userUid) {

        return null;
    }

    /**
     * 1. 如果是从一个父导航中，移除一个子导航，那么此被移出的子导航将成为一个一级导航
     * 2. 如果是修改该导航的父导航，如果该父导航不属于该子导航，那么不会成功，如果是属于该用户的，那么该导航连带着该导航下属的所有
     * 子导航都将成为该父导航的子导航
     * @param record
     * @return
     */
    @Transactional
    public int updateNavigation(NavigationPojo record) {
        Assert.notNull(record, "导航信息不能为null");
        // 如果userUid存在的话，判断此用户是否存在
        Optional.ofNullable(record.getUserUid()).ifPresent(userUid -> {
            UserVO userVO = userService.queryUserByUid(userUid);
            AssertUtils.stateThrow(userVO != null,
                    () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
        });
        setEffectiveNavigationUid(record, true);
        setEffectiveNavigationUid(record, false);

        // 查询该导航的原始导航数据
        NavigationVO navigationVO = queryNavigationByUid(record.getUid());
        // 判断是否是修改父导航
        boolean updateParentNavigation = isUpdateParentNavigation(navigationVO, record);
        if (updateParentNavigation) {
            // 是修改父导航
            addSonNavigationUids(record.getParentNavUid(), record.getUid());
        }

        // 如果是移出
        return pplaxNavigationService.updateById(BeanUtils.copyProperties(record, Navigation.class));
    }

    /**
     * 设置该导航对象中，有效的子导航或者父导航，如果传入一个不存在的导航uid，那么会将该导航对象的父/子导航uid设置为null
     * @param navigation
     * @param setParentNav
     */
    private void setEffectiveNavigationUid(NavigationPojo navigation, boolean setParentNav) {
        if (setParentNav) {
            if (navigation.getParentNavUid() == null) {
                return;
            }

            NavigationVO navigationVO = queryNavigationByUid(navigation.getParentNavUid());
            if (navigationVO == null) {
                navigation.setParentNavUid(null);
                return;
            }
            return;
        }

        if (!StringUtils.hasLength(navigation.getSonNavUids())) {
            navigation.setSonNavUids(null);
            return;
        }
        try {
            navigation.setSonNavUids(getEffectiveSonNavigationUidStr(navigation));
        } catch (Exception e) {
            throw new RuntimeException("类型错误");
        }
    }

    /**
     * 获取一个导航中，有效的子导航字符串
     * @param navigation
     * @return
     */
    private String getEffectiveSonNavigationUidStr(NavigationPojo navigation) {
        Assert.notNull(navigation, "导航信息不能为null");
        if (!StringUtils.hasLength(navigation.getSonNavUids())) {
            return null;
        }

        // 获取有效的导航信息
        return Arrays.stream(navigation.getSonNavUids().split(","))
                .map(Long::parseLong)
                .filter(uid -> queryNavigationByUid(uid) != null)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    private String getEffectiveSonNavigationUids(Navigation parentNavigation) {
        if (parentNavigation == null) {
            return null;
        }

        NavigationVO navigationVO = queryNavigationByUid(parentNavigation.getUid());
        if (navigationVO == null) {
            return null;
        }

        // 查看该父导航的可用子导航
        if (!StringUtils.hasLength(navigationVO.getSonNavUids())) {
            return null;
        }

        return Arrays.stream(navigationVO.getSonNavUids().split(","))
                .map(Long::parseLong)
                .filter(uid -> queryNavigationByUid(uid) != null)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    /**
     * 获取该导航对象中的子导航字符串，不会验证该导航中的子导航uid是否有效
     * @param navigation
     * @return
     */
    private String getSonNavigationUids(Navigation navigation) {
        if (navigation == null) {
            return null;
        }

        if (!StringUtils.hasLength(navigation.getSonNavUids())) {
            return null;
        }
        return String.join(",", navigation.getSonNavUids().split(","));
    }

    /**
     * 为parentNavUid对对象的父导航，增加一个新的子导航，newSonNavUid是一个有效的uid，不会验证该newSonNavUid是否有效，但是
     * 会验证parentNavUid是否有效
     * @param parentNavUid
     * @param newSonNavUid
     */
    private void addSonNavigationUids(long parentNavUid, long newSonNavUid) {
        NavigationVO navigationVO = queryNavigationByUid(parentNavUid);
        AssertUtils.stateThrow(navigationVO != null, () -> new CommentException("该" + parentNavUid + "不存在"));

        // 获取有效的子导航字符串
        String effectiveSonNavigationUids =
                getEffectiveSonNavigationUids(BeanUtils.copyProperties(navigationVO, Navigation.class));

        // 修改
        AtomicReference<String> sonNavigationStrAtomicReference = new AtomicReference<>("");
        Optional.ofNullable(effectiveSonNavigationUids)
                .ifPresentOrElse(uidStr -> sonNavigationStrAtomicReference.set(uidStr + "," + newSonNavUid),
                        () -> sonNavigationStrAtomicReference.set(newSonNavUid + ""));

        NavigationPojo pojo = new NavigationPojo();
        pojo.setUid(parentNavUid);
        pojo.setSonNavUids(sonNavigationStrAtomicReference.get());
        updateNavigation(pojo);
    }

    private boolean isUpdateParentNavigation(NavigationVO queriedNav, NavigationPojo updateNav) {
        if (queriedNav == null && updateNav.getParentNavUid() != null) {
            return true;
        }

        if (queriedNav == null) {
            return false;
        }

        if (updateNav.getParentNavUid() == null) {
            return false;
        }

        return !Objects.equals(queriedNav.getParentNavUid(), updateNav.getParentNavUid());
    }
}
