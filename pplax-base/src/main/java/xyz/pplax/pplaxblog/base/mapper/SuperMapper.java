package xyz.pplax.pplaxblog.base.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;

/**
 * mapper 父类，注意这个类不要让 mybatis-plus 扫描到！！
 */
public interface SuperMapper<T> extends BaseMapper<T> {

    // 这里可以放一些公共的方法
//    public void updateBatchStatus(ArrayList<Integer> uids, Integer status);

}
