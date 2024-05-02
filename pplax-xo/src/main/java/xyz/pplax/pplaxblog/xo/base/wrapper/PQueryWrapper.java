package xyz.pplax.pplaxblog.xo.base.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import xyz.pplax.pplaxblog.commons.constants.BaseSQLConstants;
import xyz.pplax.pplaxblog.commons.enums.EStatus;

public class PQueryWrapper<T> extends QueryWrapper<T> {

    public PQueryWrapper() {
        super();
        this.ne(BaseSQLConstants.STATUS, EStatus.DISABLED.getStatus());
    }

    public PQueryWrapper(T entity) {
        super(entity);
        this.ne(BaseSQLConstants.STATUS, EStatus.DISABLED.getStatus());
    }

    public PQueryWrapper(T entity, String... columns) {
        super(entity, columns);
        this.ne(BaseSQLConstants.STATUS, EStatus.DISABLED.getStatus());
    }

}
