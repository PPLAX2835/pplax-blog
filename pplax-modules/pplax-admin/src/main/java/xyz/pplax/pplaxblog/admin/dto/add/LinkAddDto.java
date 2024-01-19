package xyz.pplax.pplaxblog.admin.dto.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "编辑友情链接的dto")
public class LinkAddDto {

    @ApiModelProperty(example = "", notes = "唯一标识符", required = false)
    private String uid;

    @ApiModelProperty(example = "PPLAX", notes = "友情链接标题", required = true)
    private String title;

    @ApiModelProperty(example = "这是皮某人的网站", notes = "友情链接介绍", required = true)
    private String summary;

    @ApiModelProperty(example = "pplax.xyz", notes = "友情链接URL", required = true)
    private String url;

    @ApiModelProperty(example = "10", notes = "点击数", required = true)
    private Integer clickCount;

    @ApiModelProperty(example = "1", notes = "状态", required = true)
    private Integer status;

}
