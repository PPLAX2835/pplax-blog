package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.entity.SuperEntity;

/**
 * @description 角色表
 * @author PPLAX
 * @date 2023-11-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_role")
public class Role extends SuperEntity<Role> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名
     */
    private String roleName;

    public Role() {}
}
