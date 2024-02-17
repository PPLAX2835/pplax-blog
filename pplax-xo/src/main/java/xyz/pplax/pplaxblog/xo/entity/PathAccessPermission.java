package xyz.pplax.pplaxblog.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;

/**
 * @description 路径访问权限表
 * @author PPLAX
 * @date 2024-2-4
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_path_access_permission")
public class PathAccessPermission extends SuperEntity<PathAccessPermission> {

    private static final long serialVersionUID = 1L;

    /**
     * 访问路径的正则
     */
    private String pathRegex;

    /**
     * 请求方法，get、post这种
     */
    private String method;

    /**
     * 访问其他用户的权限
     */
    private Boolean otherUserAccessPermission;

    public PathAccessPermission() {}
}
