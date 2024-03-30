package xyz.pplax.pplaxblog.xo.dto.edit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.dto.BaseDto;

/**
 * 站点设置参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "站点设置参数")
public class SiteSettingEditDto extends BaseDto<SiteSettingEditDto> {

    @ApiModelProperty(example = "", notes = "英文名", required = false)
    private String nameEn;

    @ApiModelProperty(example = "", notes = "中文名", required = false)
    private String nameZh;

    @ApiModelProperty(example = "", notes = "值", required = false)
    private Object value;

    @ApiModelProperty(example = "0", notes = "类型", required = false)
    private Integer type;

}
