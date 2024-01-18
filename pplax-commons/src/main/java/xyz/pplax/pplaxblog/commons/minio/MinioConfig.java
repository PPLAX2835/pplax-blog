package xyz.pplax.pplaxblog.commons.minio;

import io.minio.MinioClient;
import lombok.Data;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MinioConfig
 * @Description: minio配置工具
 * @author PPLAX
 * @date 2024/1/18 12:39
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {
    private static final Logger logger = LogManager.getLogger(MinioConfig.class);
    /**
     * 服务地址
     */
    private String endpoint;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 用户名
     */
    private String accesskey;
    /**
     * 密码
     */
    private String secretkey;
    /**
     * 请求方式 如果是true，则用的是https而不是http,默认值是true
     */
    private Boolean secure;
    /**
     * 默认存储桶
     */
    private String bucketName;

    @Bean
    public MinioClient getMinioClient() {
        MinioClient minioClient = null;
        try {
            minioClient = new MinioClient(endpoint, port, accesskey, secretkey, secure);
            logger.info("Minio文件存储服务初始化完成");
        } catch (Exception e) {
            logger.error("Minio文件存储服务初始化异常" + e.getMessage());
            e.printStackTrace();
        }
        return minioClient;
    }

}
