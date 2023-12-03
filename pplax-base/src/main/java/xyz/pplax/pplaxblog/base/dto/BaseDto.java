package xyz.pplax.pplaxblog.base.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.base.validator.annotion.IdValid;
import xyz.pplax.pplaxblog.base.validator.group.Delete;
import xyz.pplax.pplaxblog.base.validator.group.Update;

/**
 * dto基类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "基本参数")
public class BaseDto<T> extends PageDto<T> {

    /**
     * 唯一UID
     */
    @IdValid(groups = {Update.class, Delete.class})
    private String uid;

    private Integer status;
}
