package xyz.pplax.admin.pojo;

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

/**
 * role数据表的POJO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePojo {

    /**
     * 唯一uid，自增
     */
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * 角色的名称，不用添加ROLE_
     */
    @Length(max = FieldLengthConstant.USER_ROLE)
    @ValidateString(value = "角色的名称", max = FieldLengthConstant.USER_ROLE, groups = {Insert.class})
    private String name;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最后更新时间
     */
    private String updateTime;

    /**
     * 用户的状态 1：已禁用 0：未禁用
     */
    private Boolean status;

}