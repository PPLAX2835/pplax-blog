package xyz.pplax.pplaxblog.file.config;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.pplax.pplaxblog.commons.constants.StorageModeConstants;
import xyz.pplax.pplaxblog.file.model.StorageConfigProperties;
import xyz.pplax.pplaxblog.xo.entity.SiteSetting;
import xyz.pplax.pplaxblog.xo.service.SiteSettingService;

import java.util.Map;

@Configuration
public class StorageConfig {

    @Autowired
    private SiteSettingService siteSettingService;

    @Bean
    public StorageConfigProperties storageConfigProperties() {
        Object o =  siteSettingService.map();
        Map<String, JSON> jsonObjectMap = (Map<String, JSON>) o;

        SiteSetting storageModeSetting = JSON.toJavaObject(jsonObjectMap.get("storageMode"), SiteSetting.class);
        SiteSetting minioEndpointSetting = JSON.toJavaObject(jsonObjectMap.get("minioEndpoint"), SiteSetting.class);
        SiteSetting minioBucketNameSetting = JSON.toJavaObject(jsonObjectMap.get("minioBucketName"), SiteSetting.class);
        SiteSetting minioAccessKeySetting = JSON.toJavaObject(jsonObjectMap.get("minioAccessKey"), SiteSetting.class);
        SiteSetting minioSecretKeySetting = JSON.toJavaObject(jsonObjectMap.get("minioSecretKey"), SiteSetting.class);

        StorageConfigProperties storageConfigProperties = new StorageConfigProperties();
        storageConfigProperties.setStorageMode((String) storageModeSetting.getValue());
        if (StorageModeConstants.MINIO.equals(storageModeSetting.getValue())) {
            storageConfigProperties.setEndpoint((String) minioEndpointSetting.getValue());
            storageConfigProperties.setBucketName((String) minioBucketNameSetting.getValue());
            storageConfigProperties.setAccessKey((String) minioAccessKeySetting.getValue());
            storageConfigProperties.setSecretKey((String) minioSecretKeySetting.getValue());

        }

        return storageConfigProperties;
    }

}
