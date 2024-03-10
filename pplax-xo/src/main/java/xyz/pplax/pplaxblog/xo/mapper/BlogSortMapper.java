package xyz.pplax.pplaxblog.xo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import xyz.pplax.pplaxblog.xo.base.mapper.SuperMapper;
import xyz.pplax.pplaxblog.xo.entity.BlogSort;

/**
 * 博客分类 Mapper 接口
 */
public interface BlogSortMapper extends SuperMapper<BlogSort> {

    IPage<BlogSort> selectListSortByCites(IPage<BlogSort> page, @Param("ew") Wrapper<BlogSort> queryWrapper);

}
