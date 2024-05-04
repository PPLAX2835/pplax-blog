package xyz.pplax.pplaxblog.xo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.pplax.pplaxblog.commons.constants.BaseRegexConstants;
import xyz.pplax.pplaxblog.commons.constants.BaseSysConstants;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.exception.DeleteFailException;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.redis.SiteRedisConstants;
import xyz.pplax.pplaxblog.xo.dto.edit.SiteSettingEditDto;
import xyz.pplax.pplaxblog.xo.entity.SiteSetting;
import xyz.pplax.pplaxblog.xo.mapper.SiteSettingMapper;
import xyz.pplax.pplaxblog.xo.service.SiteSettingService;

import java.util.*;

/**
 * 站点设置 服务实现类
 */
@Service
public class SiteSettingServiceImpl extends SuperServiceImpl<SiteSettingMapper, SiteSetting> implements SiteSettingService {

    @Autowired
    private RedisService redisService;

    @Override
    public List<SiteSetting> list() {
        PQueryWrapper<SiteSetting> siteSettingPQueryWrapper = new PQueryWrapper<>();

        List<SiteSetting> siteSettingList = list(siteSettingPQueryWrapper);
        for (SiteSetting siteSetting : siteSettingList) {
            // 如果是json形式就转化为json
            if (!StringUtils.isBlank((String) siteSetting.getValue()) && (((String) siteSetting.getValue()).matches(BaseRegexConstants.JSON_REGEX))) {
                siteSetting.setValue(JSON.toJSONString(JSON.parseObject((String) siteSetting.getValue())));
            }
        }

        return siteSettingList;
    }

    @Override
    public Boolean save(SiteSettingEditDto siteSettingEditDto) {
        // 封装
        SiteSetting siteSetting = new SiteSetting();
        siteSetting.setUid(StringUtils.getUUID());
        siteSetting.setNameEn(siteSettingEditDto.getNameEn());
        siteSetting.setNameZh(siteSettingEditDto.getNameZh());
        if ((siteSettingEditDto.getValue().matches(BaseRegexConstants.JSON_REGEX))) {
            siteSetting.setValue(JSON.toJSONString(JSON.parseObject(siteSettingEditDto.getValue())));
        } else {
            siteSetting.setValue(siteSettingEditDto.getValue());
        }

        return save(siteSetting);
    }

    @Override
    public Boolean updateById(SiteSettingEditDto siteSettingEditDto) {
        // 封装
        SiteSetting siteSetting = new SiteSetting();
        siteSetting.setUid(siteSettingEditDto.getUid());
        siteSetting.setNameEn(siteSettingEditDto.getNameEn());
        siteSetting.setNameZh(siteSettingEditDto.getNameZh());
        if ((siteSettingEditDto.getValue().matches(BaseRegexConstants.JSON_REGEX))) {
            siteSetting.setValue(JSON.toJSONString(JSON.parseObject(siteSettingEditDto.getValue())));
        } else {
            siteSetting.setValue(siteSettingEditDto.getValue());
        }

        return updateById(siteSetting);
    }

    @Override
    public Boolean updateByMap(Map<String, SiteSetting> data) {
        boolean res = updateBatchById(data.values());

        if (res) {
            // 删除缓存
            boolean b = redisService.deleteObject(SiteRedisConstants.SITE_SETTING);
        }

        return res;
    }

    @Override
    public Map<String, SiteSetting> map() {
        Map<String, SiteSetting> setting = redisService.getCacheObject(SiteRedisConstants.SITE_SETTING);

        // 如果缓存中有，直接返回即可
        if (setting != null) {
            return setting;
        }

        List<SiteSetting> siteSettingList = list();
        Map<String, SiteSetting> res = new HashMap<>();
        for (SiteSetting siteSetting : siteSettingList) {
            res.put(siteSetting.getNameEn(), siteSetting);
        }
        // 放到缓存中
        redisService.setCacheObject(SiteRedisConstants.SITE_SETTING, res);

        return res;
    }

}
