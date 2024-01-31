package xyz.pplax.pplaxblog.commons.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xyz.pplax.pplaxblog.commons.mapper.SuperMapper;
import xyz.pplax.pplaxblog.commons.service.SuperService;


/**
 * SuperService 实现类（ 泛型：M 是  mapper(dao) 对象，T 是实体 ）
 * @param <T>
 */

public class SuperServiceImpl<M extends SuperMapper<T>, T> extends ServiceImpl<M, T>  implements SuperService<T> {

}
