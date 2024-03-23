package xyz.pplax.pplaxblog.commons.enums;
/**
 * 状态枚举类
 */
public enum EStatus {
    DISABLED (0),                   // 删除的
    ENABLE (1),                     // 正常的
    FREEZE (2),                     // 冻结的
    STICK (3),                      // 置顶的
    OFF_SHELF(4),                   // 下架的
    PENDING_APPROVAL(5),            // 待审批
    DRAFT(6),                       // 草稿
    LOCKED(7),                      // 锁定的
    UNRESOLVED(8),                  // 未解决
    RESOLVED(9),                    // 已解决
    IN_PROGRESS(10),                // 进行中
    REFUSED(11),                    // 已拒绝
    APPLYING(12);                   // 申请中

    private final Integer status;

    EStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
