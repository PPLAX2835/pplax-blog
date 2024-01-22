package xyz.pplax.pplaxblog.xo.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.base.entity.SuperEntity;

/**
 * @description 文件表
 * @author PPLAX
 * @date 2024-1-17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_file")
public class FileStorage extends SuperEntity<FileStorage> {

    private static final long serialVersionUID = 1L;

    /**
     * <pre>
     * 用户uid
     * </pre>
     */
    private String	userUid;

    /**
     * <pre>
     * 文件名
     * </pre>
     */
    private String	fileName;

    /**
     * <pre>
     * 文件扩展名
     * </pre>
     */
    private String	extendName;

    /**
     * <pre>
     * 文件地址
     * </pre>
     */
    private String	filePath;

    /**
     * <pre>
     * 文件大小
     * </pre>
     */
    private Integer	fileSize;

    /**
     * <pre>
     * 是否是目录
     * </pre>
     */
    private Integer	isDirectory;

    /**
     * <pre>
     * 文件存储的模式，(七牛云、Minio、本地、阿里云OSS什么的)
     * </pre>
     */
    private String	storageMode;

    /**
     * <pre>
     * 文件地址
     * </pre>
     */
    private String	fileUrl;

    @TableField(exist = false)
    private User user; // 父级分类

    public FileStorage() {}

}
