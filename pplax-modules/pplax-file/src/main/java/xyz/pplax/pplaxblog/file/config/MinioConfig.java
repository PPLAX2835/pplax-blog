package xyz.pplax.pplaxblog.file.config;

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

    @Value("${pplax.storage.minio.endpoint:pplax.xyz:9002}")
    private String endpoint;

    @Value("${pplax.storage.minio.accessKey:root}")
    private String accessKey;

    @Value("${pplax.storage.minio.secretKey:password}")
    private String secretKey;

    @Value("${pplax.storage.minio.bucketName:pplax-blog}")
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
