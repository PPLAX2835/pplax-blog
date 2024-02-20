package xyz.pplax.pplaxblog.xo.base.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;

/**
 * mapper 父类
 */
public interface SuperMapper<T extends SuperEntity> extends BaseMapper<T> {

}
