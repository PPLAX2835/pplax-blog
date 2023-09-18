package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import xyz.pplax.admin.po.Setting;
import xyz.pplax.admin.pojo.SettingPojo;
import xyz.pplax.admin.vo.SettingVO;
import xyz.pplax.core.exception.setting.SettingException;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.core.util.lambda.AssertUtils;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;
import xyz.pplax.data.util.PageUtils;

@Service
public class SettingService {

    @Autowired
    private PPLAXSettingService pplaxSettingService;

    @Transactional
    public void insertSysSetting(SettingPojo pojo) {
        Assert.notNull(pojo, "系统信息不能为null");
        Setting settingInfo = new Setting();
        settingInfo.setParamCode(pojo.getParamCode());
        Setting setting = pplaxSettingService.queryOne(settingInfo);
        AssertUtils.stateThrow(setting == null, () -> new SettingException("数据库中存在相同名称"));
        pplaxSettingService.insert(BeanUtils.copyProperties(pojo, Setting.class));
    }

    public int updateSysSetting(SettingPojo pojo) {
        // 判断此参数名是否存在
        Setting setting = pplaxSettingService.queryOne(new Setting() {{
            setParamCode(pojo.getParamCode());
        }});
        if (setting != null && !setting.getUid().equals(pojo.getUid())) {
            throw new SettingException("存在相似参数编码");
        }
        return pplaxSettingService.updateById(BeanUtils.copyProperties(pojo, Setting.class));
    }

    public int physicalDeleteSysSetting(long uid) {
        return pplaxSettingService.deleteById(uid);
    }

    public PageData<SettingVO> queryListSysSettingByCondition(Condition<Long> condition) {
        return PageUtils.copyPageDataResult(pplaxSettingService.queryListByCondition(condition), SettingVO.class);
    }

    public SettingVO querySysSettingByUid(long uid) {
        return BeanUtils.copyProperties(pplaxSettingService.queryById(uid), SettingVO.class);
    }
}
