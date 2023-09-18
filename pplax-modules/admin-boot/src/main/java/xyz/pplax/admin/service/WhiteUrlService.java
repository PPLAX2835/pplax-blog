package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.admin.po.WhiteUrl;
import xyz.pplax.admin.pojo.WhiteUrlPojo;
import xyz.pplax.admin.vo.WhiteUrlVO;
import xyz.pplax.core.constant.RedisConstant;
import xyz.pplax.core.enums.RegexEnum;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.permission.PermissionException;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.core.util.lambda.AssertUtils;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;
import xyz.pplax.data.util.PageUtils;
import xyz.pplax.service.redis.annotation.CleanRedisData;
import xyz.pplax.service.redis.annotation.GetByRedis;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;


@Service
public class WhiteUrlService {
    @Autowired
    private PPLAXWhiteUrlService pplaxWhiteUrlService;

    @CleanRedisData(key = RedisConstant.STORAGE_WHITE_URL_INFO)
    public int physicalDeleteWhiteUrl(Long uid) {
        return pplaxWhiteUrlService.deleteById(uid);
    }

    @CleanRedisData(key = RedisConstant.STORAGE_WHITE_URL_INFO)
    public int batchDeleteWhiteUrl(WhiteUrlPojo pojo) {
        return pplaxWhiteUrlService.batchDelete(pojo.getUidList());
    }

    @CleanRedisData(key = RedisConstant.STORAGE_WHITE_URL_INFO)
    public void insertWhiteUrl(WhiteUrlPojo record) {
        Objects.requireNonNull(record, "白名单记录不能为null");
        Optional.ofNullable(record.getUrl()).orElseThrow(() -> new NullPointerException("白名单地址不能为null"));

        // 判断path是否符合规范，必须是GET:Path这种形式 不支持中文路径
        AssertUtils.stateThrow(matchesResourcePath(record.getUrl()),
                () -> new PermissionException(ResponseStatusCodeEnum.PERMISSION_RESOURCE_NOT_RIGHT));
        // 判断该白名单是否存在于数据库中
        AssertUtils.stateThrow(queryListWhiteUrlByCondition(Condition.instant(record.getUrl())).getResult().isEmpty(),
                () -> new RuntimeException("该白名单已存在"));
       pplaxWhiteUrlService.insert(BeanUtils.copyProperties(record, WhiteUrl.class));
    }

    @GetByRedis(key = RedisConstant.STORAGE_WHITE_URL_INFO, expriedSecond = 60 * 60 * 24)
    public PageData<WhiteUrlVO> queryListWhiteUrlByCondition(Condition<Integer> condition) {
        return PageUtils.copyPageDataResult(pplaxWhiteUrlService.queryListByCondition(condition), WhiteUrlVO.class);
    }

    @CleanRedisData(key = RedisConstant.STORAGE_WHITE_URL_INFO)
    public int updateWhiteUrl(WhiteUrlPojo record) {
        return pplaxWhiteUrlService.updateById(BeanUtils.copyProperties(record, WhiteUrl.class));
    }

    private boolean matchesResourcePath(String resourcePath) {
        return Pattern.matches(RegexEnum.REST_FUL_PATH.getRegex(),resourcePath);
    }
}
