package xyz.pplax.pplaxblog.xo.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import xyz.pplax.pplaxblog.xo.base.mapper.SuperMapper;
import xyz.pplax.pplaxblog.xo.entity.BlogSort;
import xyz.pplax.pplaxblog.xo.entity.Tag;

/**
 * 标签表 Mapper 接口
 */
public interface TagMapper extends SuperMapper<Tag> {

    IPage<Tag> selectListSortByCites(IPage<Tag> page, @Param("ew") Wrapper<Tag> queryWrapper);

}
