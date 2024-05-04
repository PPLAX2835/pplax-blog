package xyz.pplax.pplaxblog.file.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.pplax.pplaxblog.file.model.StorageConfigProperties;
import xyz.pplax.pplaxblog.xo.entity.SiteSetting;
import xyz.pplax.pplaxblog.xo.service.SiteSettingService;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    @Autowired
    private StorageConfigProperties storageConfigProperties;

    /**
     * 注入minio 客户端
     * @return
     */
    @Bean
    public MinioClient minioClient(){

        return MinioClient.builder()
                .endpoint(storageConfigProperties.getEndpoint())
                .credentials(storageConfigProperties.getAccessKey(), storageConfigProperties.getSecretKey())
                .build();
    }
}
