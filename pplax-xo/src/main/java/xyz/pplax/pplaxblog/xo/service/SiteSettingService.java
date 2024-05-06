package xyz.pplax.pplaxblog.xo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sun.org.apache.xpath.internal.operations.Bool;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.edit.SiteSettingEditDto;
import xyz.pplax.pplaxblog.xo.dto.edit.TagEditDto;
import xyz.pplax.pplaxblog.xo.entity.SiteSetting;

import java.util.List;
import java.util.Map;

/**
 * 站点设置 服务类
 */
public interface SiteSettingService extends SuperService<SiteSetting> {

    List<SiteSetting> list();

    SiteSetting getByNameEn(String nameEn);

    Boolean save(SiteSettingEditDto siteSettingEditDto);

    Boolean updateById(SiteSettingEditDto siteSettingEditDto);

    Boolean updateByMap(Map<String, SiteSetting> data);

    Map<String, SiteSetting> map();

}
