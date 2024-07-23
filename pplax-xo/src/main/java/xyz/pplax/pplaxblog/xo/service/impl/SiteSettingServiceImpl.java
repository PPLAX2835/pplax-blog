package xyz.pplax.pplaxblog.xo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.constants.BaseRegexConstants;
import xyz.pplax.pplaxblog.commons.constants.SiteSettingConstants;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.exception.BaseException;
import xyz.pplax.pplaxblog.commons.utils.CaptchaUtils;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.starter.redis.service.RedisService;
import xyz.pplax.pplaxblog.starter.redis.utils.RedisUtils;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.redis.SiteRedisConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.SiteSettingSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.edit.SiteSettingEditDto;
import xyz.pplax.pplaxblog.xo.entity.SiteSetting;
import xyz.pplax.pplaxblog.xo.mapper.SiteSettingMapper;
import xyz.pplax.pplaxblog.xo.service.SiteSettingService;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 站点设置 服务实现类
 */
@Service
public class SiteSettingServiceImpl extends SuperServiceImpl<SiteSettingMapper, SiteSetting> implements SiteSettingService {

    @Autowired
    private RedisService redisService;

    @Override
    public SiteSetting getByNameEn(String nameEn) {
        String redisKey = SiteRedisConstants.SITE_SETTING + SiteRedisConstants.SEGMENTATION + nameEn;

        // 先从缓存中找
        SiteSetting siteSetting = JSON.toJavaObject(redisService.getCacheObject(redisKey), SiteSetting.class) ;
        if (siteSetting != null) {
            return siteSetting;
        }

        // 从数据库中找
        PQueryWrapper<SiteSetting> siteSettingPQueryWrapper = new PQueryWrapper<>();
        siteSettingPQueryWrapper.eq(SiteSettingSQLConstants.NAME_EN, nameEn);

        siteSetting = getOne(siteSettingPQueryWrapper);

        if (siteSetting != null) {
            // 保存到缓存中，过期时间为300周围
            redisService.setCacheObject(redisKey, siteSetting, RedisUtils.getRandomExpire(300L), TimeUnit.SECONDS);
        }

        return siteSetting;
    }

    @Override
    public List<SiteSetting> list() {
        PQueryWrapper<SiteSetting> siteSettingPQueryWrapper = new PQueryWrapper<>();

        List<SiteSetting> siteSettingList = list(siteSettingPQueryWrapper);
        for (SiteSetting siteSetting : siteSettingList) {
            // 如果是json形式就转化为json
            try {
                String value = (String) siteSetting.getValue();
                siteSetting.setValue(JSON.parse(value));
            } catch (Exception ignored) {

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

        // 如果是json形式就转化为json
        try {
            String value = siteSettingEditDto.getValue();
            siteSetting.setValue(JSON.parse(value));
        } catch (Exception ignored) {
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

        // 如果是json形式就转化为json
        try {
            String value = siteSettingEditDto.getValue();
            siteSetting.setValue(JSON.parse(value));
        } catch (Exception ignored) {
            siteSetting.setValue(siteSettingEditDto.getValue());
        }

        return updateById(siteSetting);
    }

    @Override
    public Boolean updateByMap(Map<String, SiteSetting> data) {
        // 检查一下captchaUrl是否可用
        BufferedImage bufferedImage = CaptchaUtils.getBufferedImage((String) data.get(SiteSettingConstants.CAPTCHA_URL_EN).getValue());
        if (bufferedImage == null) {
            throw new BaseException(HttpStatus.CAPTCHA_GET_FAILED);
        }

        boolean res = updateBatchById(data.values());

        if (res) {
            // 删除缓存
            res = redisService.deleteObject(SiteRedisConstants.SITE_SETTING);
        }

        return res;
    }

    @Override
    public Map<String, SiteSetting> map() {
        Map<String, SiteSetting> res = new HashMap<>();
        Map<String, JSONObject> setting = redisService.getCacheMap(SiteRedisConstants.SITE_SETTING);

        // 如果缓存中有，直接返回即可
        if (setting != null && setting.size() != 0) {
            for (String key : setting.keySet()) {
                res.put(key, JSON.toJavaObject(setting.get(key), SiteSetting.class));
            }

            return res;
        }

        List<SiteSetting> siteSettingList = list();
        for (SiteSetting siteSetting : siteSettingList) {

            try {
                String value = (String) siteSetting.getValue();
                siteSetting.setValue(JSON.parse(value));
            } catch (Exception ignored) {

            }

            res.put(siteSetting.getNameEn(), siteSetting);
        }
        // 放到缓存中
        redisService.setCacheMap(SiteRedisConstants.SITE_SETTING, res);

        return res;
    }

}
