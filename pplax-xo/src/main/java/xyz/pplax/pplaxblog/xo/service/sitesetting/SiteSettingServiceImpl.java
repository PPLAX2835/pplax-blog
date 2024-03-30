package xyz.pplax.pplaxblog.xo.service.sitesetting;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.SiteSettingSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.edit.SiteSettingEditDto;
import xyz.pplax.pplaxblog.xo.entity.SiteSetting;
import xyz.pplax.pplaxblog.xo.mapper.SiteSettingMapper;

import java.util.List;

/**
 * 站点设置 服务实现类
 */
@Service
public class SiteSettingServiceImpl extends SuperServiceImpl<SiteSettingMapper, SiteSetting> implements SiteSettingService {
    @Override
    public List<SiteSetting> list() {
        QueryWrapper<SiteSetting> siteSettingQueryWrapper = new QueryWrapper<>();
        siteSettingQueryWrapper.ne(SiteSettingSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        siteSettingQueryWrapper.orderByAsc(SiteSettingSQLConstants.C_CREATE_TIME);

        List<SiteSetting> siteSettingList = list(siteSettingQueryWrapper);
        for (SiteSetting siteSetting : siteSettingList) {           // 如果是页脚徽标
            if (siteSetting.getType() == 3) {
                siteSetting.setValue(JSON.parseObject((String) siteSetting.getValue()));
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
        siteSetting.setType(siteSettingEditDto.getType());
        siteSetting.setValue(siteSettingEditDto.getValue());

        return save(siteSetting);
    }

    @Override
    public Boolean updateById(SiteSettingEditDto siteSettingEditDto) {
        // 封装
        SiteSetting siteSetting = new SiteSetting();
        siteSetting.setUid(siteSettingEditDto.getUid());
        siteSetting.setNameEn(siteSettingEditDto.getNameEn());
        siteSetting.setNameZh(siteSettingEditDto.getNameZh());
        siteSetting.setType(siteSettingEditDto.getType());
        siteSetting.setValue(siteSettingEditDto.getValue());

        return updateById(siteSetting);
    }

}
