package xyz.pplax.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.pplax.admin.dto.AdminRouterDTO;
import xyz.pplax.admin.po.RouterPermission;
import xyz.pplax.admin.pojo.AdminRouterPojo;
import xyz.pplax.admin.pojo.RouterPermissionPojo;
import xyz.pplax.admin.vo.AdminRouterVO;
import xyz.pplax.core.util.BeanUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PermissionAdminRouterRelationshipService {
    @Autowired
    private PPLAXRouterPermissionService pplaxRouterPermissionService;

    @Autowired
    private AdminRouterService adminRouterService;

    public void insertRouterPermission(RouterPermissionPojo pojo) {
        pplaxRouterPermissionService.insert(BeanUtils.copyProperties(pojo, RouterPermission.class));
    }

    public int updateRouterPermission(RouterPermissionPojo pojo) {
        return pplaxRouterPermissionService.updateById(BeanUtils.copyProperties(pojo, RouterPermission.class));
    }

    public int physicalDeleteRouterPermission(RouterPermissionPojo pojo) {
        return pplaxRouterPermissionService.deleteById(pojo.getUid());
    }


    /**
     * 查询权限uid对应的所有路由信息
     * @param pojo
     * @return
     */
    public List<AdminRouterDTO> queryAllAdminRouterInfoByPermissionUid(RouterPermissionPojo pojo) {
        List<AdminRouterDTO> routerList = new ArrayList<>();
        pojo.getPermissionUidList().forEach(permissionUid -> {
            // 查询路由
            List<RouterPermission> routerPermissionList = pplaxRouterPermissionService.queryListByWhere(new RouterPermission() {{
                setPermissionUid(permissionUid);
            }});

            routerPermissionList.forEach(router -> routerList.add(packageRouterInfo(router.getUid(), null)));
        });
        return routerList;
    }

    private AdminRouterDTO packageRouterInfo(long routerUid, AdminRouterDTO adminRouterDTO) {
        AdminRouterVO adminRouterVO = adminRouterService.queryAdminRouterByUid(new AdminRouterPojo() {{
            setUid(routerUid);
        }});
        if (adminRouterVO == null) {
            return null;
        }

        if (adminRouterDTO == null) {
            adminRouterDTO = BeanUtils.copyProperties(adminRouterVO, AdminRouterDTO.class);
        }

        List<Long> effectiveRouterUid = getEffectiveRouterUid(adminRouterVO.getSonRouterUids());
        if (adminRouterDTO.getChildren() == null && effectiveRouterUid.size() != 0) {
            adminRouterDTO.setChildren(new ArrayList<>());
        }
        for (int i = 0; i < effectiveRouterUid.size(); i++) {
            adminRouterDTO.getChildren().add(packageRouterInfo(effectiveRouterUid.get(i), adminRouterDTO));
        }
        return adminRouterDTO;
    }

    private List<Long> getEffectiveRouterUid(String sonRouterUidArr) {
        if (!StringUtils.hasLength(sonRouterUidArr)) {
            return new ArrayList<>();
        }
        return Arrays.stream(sonRouterUidArr.split(",")).map(Long::parseLong).filter(uid -> {
            AdminRouterPojo pojo = new AdminRouterPojo();
            pojo.setUid(uid);
            return adminRouterService.queryAdminRouterByUid(pojo) != null;
        }).collect(Collectors.toList());
    }
}
