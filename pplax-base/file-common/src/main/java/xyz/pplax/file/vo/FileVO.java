package xyz.pplax.file.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.file.po.File;

/**
 * file数据表的VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "file数据表的VO")
public class FileVO extends File {
    /**
     * 文件路径部分的uri
     */
    private String filePathUri;
}