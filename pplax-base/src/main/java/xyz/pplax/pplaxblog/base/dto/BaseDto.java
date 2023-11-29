package xyz.pplax.pplaxblog.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * dto基类
 */
@Data
@ApiModel(value = "基本参数")
public class BaseDto {

    /**
     * 查询关键字
     */
    @ApiModelProperty(name = "keyWord", value = "查询关键字", required = false)
    private String keyWord;

    /**
     * 当前页
     */
    @ApiModelProperty(name = "currentPage", value = "当前页", required = false)
    private Long currentPage;

    /**
     * 页码大小
     */
    @ApiModelProperty(name = "pageSize", value = "页码大小", required = false)
    private Long pageSize;


    BaseDto () {
        this.currentPage = 1L;
        this.pageSize = 10L;
    }
}
