package xyz.pplax.admin.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * site_setting数据表的POJO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteSettingPojo {

    /**
     * 
     */
    @Schema(title = "")
    private Long uid;

    /**
     * 用户uid
     */
    @Schema(title = "用户uid")
    private Long userUid;

    /**
     * 参数名称
     */
    @Schema(title = "参数名称")
    private String paramName;

    /**
     * 参数值
     */
    @Schema(title = "参数值")
    private String paramValue;

    /**
     * 创建时间
     */
    @Schema(title = "创建时间")
    private String createTime;

    /**
     * 最后更新时间
     */
    @Schema(title = "最后更新时间")
    private String updateTime;

}