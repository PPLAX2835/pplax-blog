package xyz.pplax.pplaxblog.commons.enums;
/**
 * 状态枚举类
 */
public enum EStatus {
	DISABLED (0),                   //删除的
    ENABLE (1),                     //激活的
    FREEZE (2),                     //冻结的
    STICK (3);                      //置顶的

    private final Integer status;

    EStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
