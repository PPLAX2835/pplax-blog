package xyz.pplax.pplaxblog.xo.service.sitesetting;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.constants.BaseRegexConstants;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.SiteSettingSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.edit.SiteSettingEditDto;
import xyz.pplax.pplaxblog.xo.entity.BlogSort;
import xyz.pplax.pplaxblog.xo.entity.SiteSetting;
import xyz.pplax.pplaxblog.xo.mapper.SiteSettingMapper;
import xyz.pplax.pplaxblog.xo.service.blogsort.BlogSortService;

import java.util.*;

/**
 * 站点设置 服务实现类
 */
@Service
public class SiteSettingServiceImpl extends SuperServiceImpl<SiteSettingMapper, SiteSetting> implements SiteSettingService {

    @Autowired
    private BlogSortService blogSortService;

    @Override
    public List<SiteSetting> list() {
        QueryWrapper<SiteSetting> siteSettingQueryWrapper = new QueryWrapper<>();
        siteSettingQueryWrapper.ne(SiteSettingSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());

        List<SiteSetting> siteSettingList = list(siteSettingQueryWrapper);
        for (SiteSetting siteSetting : siteSettingList) {
            // 如果是json形式就转化为json
            if ((siteSetting.getValue().matches(BaseRegexConstants.JSON_REGEX))) {
                siteSetting.setValue(JSON.toJSONString(JSON.parseObject(siteSetting.getValue())));
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
        }

        return updateById(siteSetting);
    }

    @Override
    public Map<String, Object> map() {
        List<SiteSetting> siteSettingList = list();

        Map<String, Object> res = new HashMap<>();
        for (SiteSetting siteSetting : siteSettingList) {
            res.put(siteSetting.getNameEn(), siteSetting);
        }

        return res;
    }

}
