package xyz.pplax.pplaxblog.xo.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;

/**
 * Service父类
 */
public interface SuperService<T extends SuperEntity> extends IService<T>{

}
