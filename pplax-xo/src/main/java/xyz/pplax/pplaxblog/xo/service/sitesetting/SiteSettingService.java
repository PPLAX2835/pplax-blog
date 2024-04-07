package xyz.pplax.pplaxblog.xo.service.sitesetting;

import com.baomidou.mybatisplus.core.metadata.IPage;
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

    Boolean save(SiteSettingEditDto siteSettingEditDto);

    Boolean updateById(SiteSettingEditDto siteSettingEditDto);

    Map<String, Object> map();

}
