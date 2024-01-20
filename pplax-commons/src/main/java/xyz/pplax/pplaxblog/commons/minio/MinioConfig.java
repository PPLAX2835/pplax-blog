package xyz.pplax.pplaxblog.commons.minio;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    @Value("${pplax.minio.endpoint}")
    private String endpoint;

    @Value("${pplax.minio.accessKey}")
    private String accessKey;

    @Value("${pplax.minio.secretKey}")
    private String secretKey;

    @Value("${pplax.minio.bucketName}")
    private String bucketName;

    /**
     * 注入minio 客户端
     * @return
     */
    @Bean
    public MinioClient minioClient(){

        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
