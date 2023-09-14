package xyz.pplax.admin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.pplax.core.valid.Delete;
import xyz.pplax.core.valid.Insert;
import xyz.pplax.core.valid.Update;

import javax.validation.constraints.NotNull;

/**
 * user_role数据表的POJO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleRelationshipPojo {

    /**
     *
     */
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * 角色uid
     */
    @NotNull(groups = {Insert.class})
    private Long roleUid;

    /**
     * 用户uid
     */
    @NotNull(groups = {Insert.class})
    private Long userUid;

}