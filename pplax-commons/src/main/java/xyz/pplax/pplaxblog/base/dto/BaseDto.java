package xyz.pplax.pplaxblog.base.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.base.validator.annotion.IdValid;
import xyz.pplax.pplaxblog.base.validator.annotion.IdsValid;
import xyz.pplax.pplaxblog.base.validator.annotion.IntegerNotNull;
import xyz.pplax.pplaxblog.base.validator.group.Delete;
import xyz.pplax.pplaxblog.base.validator.group.Update;

import java.util.List;

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

    /**
     * 批量操作时用到的uid
     */
    @IdsValid(groups = {Update.class, Delete.class})
    private List<String> uids;

    @IntegerNotNull
    private Integer status;
}
