package xyz.pplax.pplaxblog.xo.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.constants.BaseRegexConstants;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
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
    public Map<String, Object> map() {
        List<SiteSetting> siteSettingList = list();

        Map<String, Object> res = new HashMap<>();
        for (SiteSetting siteSetting : siteSettingList) {

            if (!StringUtils.isBlank((String) siteSetting.getValue()) && (((String) siteSetting.getValue()).matches(BaseRegexConstants.JSON_REGEX))) {
                siteSetting.setValue(JSON.parseObject((String) siteSetting.getValue()));
            } else {
                siteSetting.setValue(siteSetting.getValue());
            }

            res.put(siteSetting.getNameEn(), siteSetting);
        }

        return res;
    }

}
