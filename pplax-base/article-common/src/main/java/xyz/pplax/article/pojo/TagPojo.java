package xyz.pplax.article.pojo;

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
import javax.validation.constraints.Null;

/**
 * tag数据表的POJO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagPojo {

    /**
     * 唯一uid
     */
    @Schema(title = "唯一uid")
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * 此标签的标题
     */
    @Schema(title = "此标签的标题")
    @Length(max = FieldLengthConstant.TITLE)
    @ValidateString(value = "标签的标题", max = FieldLengthConstant.TITLE, groups = Insert.class)
    private String title;

    /**
     * 此标签的简介
     */
    @Schema(title = "此标签的简介")
    @Length(max = FieldLengthConstant.SUMMARY)
    private String summary;

    /**
     * 创建时间
     */
    @Schema(title = "创建时间")
    private String createTime;

    /**
     * 最后修改时间
     */
    @Schema(title = "最后修改时间")
    private String updateTime;

    /**
     * 此类别的封面图uid
     */
    @Schema(title = "此类别的封面图uid")
    @Length(max = FieldLengthConstant.URL)
    private String coverUrl;

    /**
     * 1: 删除，0：未删除
     */
    @Schema(title = "1: 删除，0：未删除")
    @Null(groups = Insert.class)
    private Boolean delete;

    /**
     * 用户的userUid
     */
    @Schema(title = "用户的userUid")
    private Long userUid;

}