package xyz.pplax.admin.manager.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import xyz.pplax.admin.dto.RolePermissionDTO;
import xyz.pplax.admin.service.PermissionRelationService;
import xyz.pplax.data.entity.Condition;

import java.util.List;

/**
 * 加载角色和权限对应信息，程序应该在启动之初，就将角色和权限信息加载到redis中进行存储，并且该加载方法可以通过mq进行调用
 * 如果在认证服务中，查询到某个用户的角色没有在redis中找到该角色对应的关系，那么会通过mq发送消息，通知此方法更新缓存，并且
 * 应该使用定时器和更新角色权限操作所触发的动作调用该方法进行更新
 */
@Slf4j
@Component
public class LoadRolePermissionInfo {
    @Autowired
    private PermissionRelationService permissionRelationService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void storagePermissionInfoToRedis(RedisTemplate<String, Object> template) {
        storageData(template);
    }

    public void storagePermissionInfoToRedis() {
        storageData(this.redisTemplate);
    }

    /**
     * 将角色权限信息放入redis中，随机生成一个ttl
     * @param template
     */
    private void storageData(RedisTemplate<String, Object> template) {
        // 获取所有的角色权限关系
        Condition<Long> condition = new Condition<>();
        // 默认分页大小为10，会导致数据不完整
        condition.setPageSize(1000000);

        List<RolePermissionDTO> rolePermissionDTOList = permissionRelationService.loadAllRolePermission(condition);
        // TODO 只查出来了，但是还没有放到redis里
        // 存入redis中Duration.ofSeconds(DateUtils.getRandomMinute(60, 60 * 24 * 3) * 60)
        // template.opsForValue().set(RedisStorageConstant.STORAGE_ROLE_PERMISSION_INFO, rolePermissionDTOList,
        //         Duration.ofSeconds(DateUtils.getRandomMinute(60, 60 * 24 * 3) * 60));
    }
}
