package xyz.pplax.pplaxblog.xo.base.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.validator.annotion.IdValid;
import xyz.pplax.pplaxblog.commons.validator.annotion.IdsValid;
import xyz.pplax.pplaxblog.commons.validator.annotion.IntegerNotNull;
import xyz.pplax.pplaxblog.commons.validator.group.*;

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
    @IdValid(required = false, groups = {Update.class, Delete.class, GetList.class, GetOne.class, Insert.class})
    private String uid;

    /**
     * 批量操作时用到的uid
     */
    @IdsValid(required = false, groups = {Update.class, Delete.class, GetList.class, GetOne.class, Insert.class})
    private List<String> uids;

    @IntegerNotNull(required = false, groups = {Update.class, Delete.class, GetList.class, GetOne.class, Insert.class})
    private Integer status;
}
