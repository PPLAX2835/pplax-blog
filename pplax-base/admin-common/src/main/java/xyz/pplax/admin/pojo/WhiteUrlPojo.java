package xyz.pplax.admin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import xyz.pplax.core.constant.FieldLengthConstant;
import xyz.pplax.core.valid.Insert;
import xyz.pplax.core.valid.Update;
import xyz.pplax.core.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * white_url数据表的POJO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WhiteUrlPojo {

    /**
     * 唯一uid
     */
    @NotNull(groups = {Update.class})
    private Long uid;

    /**
     * 白名单地址
     */
    @Length(max = FieldLengthConstant.URL)
    @ValidateString(value = "白名单地址", max = FieldLengthConstant.URL, groups = {Insert.class})
    private String url;

    /**
     * 创建时间，自动插入
     */
    private String createTime;

    /**
     * 最后更新时间
     */
    private String updateTime;

    private List<Long> uidList;
}