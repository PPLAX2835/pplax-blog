package xyz.pplax.admin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.pplax.core.valid.Delete;
import xyz.pplax.core.valid.Update;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * permission数据表的POJO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionPojo {

    /**
     * 唯一uid，自增
     */
    @NotNull(groups = {Update.class, Delete.class})
    private Long uid;

    /**
     * 此路径的名称
     */
    // @Length(max = FieldLengthConstant.URL)
    // @ValidateString(value = "资源的名称", max = FieldLengthConstant.URL, groups = {Insert.class})
    private String name;

    /**
     * 权限的地址，可以是组件的名称，必须遵守Method:path的约定
     */
    // @Length(max = FieldLengthConstant.METHOD_AND_PATH)
    // @ValidateString(value = "路径的名称", max = FieldLengthConstant.METHOD_AND_PATH, groups = {Insert.class})
    private String path;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最后更新时间
     */
    private String updateTime;

    List<PermissionPojo> permissionList;
}