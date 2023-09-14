package xyz.pplax.file.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import xyz.pplax.core.constant.FieldLengthConstant;
import xyz.pplax.core.valid.Delete;
import xyz.pplax.core.valid.Insert;
import xyz.pplax.core.valid.Update;
import xyz.pplax.core.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * file数据表的POJO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilePojo {

    /**
     * 唯一uid 不能为null 主键
     */
    @Schema(title = "唯一uid")
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    @NotNull(groups = {Insert.class})
    @Schema(title = "用户uid")
    private Long userUid;

    /**
     * 文件创建时间 不能为null
     * <p>mysql -> datetime</p>
     */
    private String createTime;

    /**
     * 此文件的删除状态 true：已删除 false：未删除
     */
    @Schema(title = "是否删除", description = "1: 删除")
    private Boolean delete;

    /**
     * 此文件的名字，含后缀
     * <p>length < 120</p>
     */
    private String fileName;

    /**
     * 文件的大小 字节单位
     */
    private Long size;

    /**
     * 此文件的删除时间 可以为null
     * <p>mysql -> datetime</p>
     */
    private String deleteTime;

    /**
     * 此文件的简介 可以为null
     * <p>length < 500</p>
     */
    @Length(max = FieldLengthConstant.SUMMARY)
    @ValidateString(value = "文件，文件简介", max = FieldLengthConstant.SUMMARY, groups = Insert.class)
    private String summary;

    /**
     * 此文件的访问路径
     */
    @Length(max = FieldLengthConstant.FILE_PATH,message = "文件的访问路径长度不能超过{max}")
    private String path;

    /**
     * 文件的存储模式
     */
    private Integer storageMode;

    /**
     * 此文件的存储位置
     */
    @Length(message = "文件存储路径长度不能超过{max}",max = FieldLengthConstant.FILE_PATH)
    private String storagePath;

}