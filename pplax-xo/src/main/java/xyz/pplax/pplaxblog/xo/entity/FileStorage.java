package xyz.pplax.pplaxblog.xo.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;

/**
 * @description 文件表
 * @author PPLAX
 * @date 2024-1-17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_file_storage")
public class FileStorage extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * <pre>
     * 资源原始名称
     * </pre>
     */
    private String originalName;

    /**3
     * <pre>
     * 资源名称
     * </pre>
     */
    private String	fileName;

    /**
     * <pre>
     * 后缀名
     * </pre>
     */
    private String	suffix;

    /**
     * <pre>
     * 文件地址
     * </pre>
     */
    private String	filePath;

    /**
     * <pre>
     * 文件大小，单位bit
     * </pre>
     */
    private Long fileSize;

    /**
     * <pre>
     * 是否是目录
     * </pre>
     */
    private Boolean	isDirectory;

    /**
     * <pre>
     * 是否图片
     * </pre>
     */
    private Boolean	isImage;

    /**
     * <pre>
     * 文件存储的模式，(七牛云、Minio、本地、阿里云OSS什么的)
     * </pre>
     */
    private String	storageMode;

    /**
     * <pre>
     * 资源路径
     * </pre>
     */
    private String	fileUrl;

    public FileStorage() {}

}
