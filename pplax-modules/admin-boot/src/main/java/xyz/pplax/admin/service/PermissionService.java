package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.pplax.admin.po.Permission;
import xyz.pplax.admin.pojo.PermissionPojo;
import xyz.pplax.core.enums.RegexEnum;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.permission.PermissionException;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.core.util.lambda.AssertUtils;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class PermissionService {

    @Autowired
    private PPLAXPermissionService pplaxPermissionService;

    public int physicalDeletePermission(long uid) {
        return pplaxPermissionService.deleteById(uid);
    }

    public int batchPhysicalDeletePermission(PermissionPojo permission) {
        List<Long> uidList = permission.getPermissionList().stream().map(PermissionPojo::getUid).collect(Collectors.toList());
        return pplaxPermissionService.batchDelete(uidList);
    }

    public void insertPermission(PermissionPojo permission) {
        Objects.requireNonNull(permission,"方法路径信息不能为null");
        // 判断path是否符合规范，必须是GET:Path这种形式 不支持中文路径
        AssertUtils.stateThrow(matchesResourcePath(permission.getPath()), () -> new PermissionException(ResponseStatusCodeEnum.PERMISSION_RESOURCE_NOT_RIGHT));
        if (judgeSimilarPermissionPath(permission.getPath())) {
            throw new PermissionException("此资源路径已经存在");
        }
        pplaxPermissionService.insert(BeanUtils.copyProperties(permission, Permission.class));
    }

    public void batchInsertPermission(PermissionPojo permission) {
        Objects.requireNonNull(permission,"方法路径信息不能为null");
        Objects.requireNonNull(permission.getPermissionList(),"方法路径信息不能为null");
        List<PermissionPojo> permissionList = permission.getPermissionList();
        for (PermissionPojo pojo : permissionList) {
            if (!StringUtils.hasLength(pojo.getPath())) {
                continue;
            }
            if (!matchesResourcePath(pojo.getPath())) {
                continue;
            }
            if (judgeSimilarPermissionPath(pojo.getPath())) {
                continue;
            }
            if (!StringUtils.hasLength(pojo.getName())) {
                pojo.setName(pojo.getPath());
            }
            pplaxPermissionService.insert(BeanUtils.copyProperties(pojo, Permission.class));
        }
        // 判断path是否符合规范，必须是GET:Path这种形式 不支持中文路径
    }

    public int updatePermission(PermissionPojo permission) {
        Objects.requireNonNull(permission, "资源路径权限信息不能为null");
        if (StringUtils.hasLength(permission.getPath())) {
            AssertUtils.stateThrow(matchesResourcePath(permission.getPath()),
                    () -> new PermissionException(ResponseStatusCodeEnum.PERMISSION_RESOURCE_NOT_RIGHT));
        }else {
            // 没有path
            permission.setPath(null);
        }
        return pplaxPermissionService.updateById(BeanUtils.copyProperties(permission, Permission.class));
    }

    public PageData<Permission> queryListPermissionByCondition(Condition<Long> condition) {
        return pplaxPermissionService.queryListByCondition(condition);
    }

    private boolean matchesResourcePath(String resourcePath) {
        return Pattern.matches(RegexEnum.REST_FUL_PATH.getRegex(),resourcePath);
    }

    public Permission queryPermissionByUid(long uid) {
        return pplaxPermissionService.queryById(uid);
    }

    private boolean judgeSimilarPermissionPath(String permissionPath) {
        Permission permission = new Permission();
        permission.setPath(permissionPath);
        List<Permission> permissions = pplaxPermissionService.queryListByWhere(permission);
        if (permissions != null) {
            return !permissions.isEmpty();
        }
        return true;
    }
}
