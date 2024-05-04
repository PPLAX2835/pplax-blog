package xyz.pplax.pplaxblog.file.model;

import lombok.Data;

@Data
public class StorageConfigProperties {

    private String storageMode;

    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String bucketName;

}
