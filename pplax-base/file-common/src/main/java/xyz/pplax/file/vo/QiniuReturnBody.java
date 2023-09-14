package xyz.pplax.file.vo;

import lombok.Data;

/**
 * 七牛云的响应
 */
@Data
public class QiniuReturnBody {

    private String bucket;

    private String key;

    private Long fsize;

    private String mimeType;

    private String hash;
}
