package xyz.pplax.pplaxblog.xo.mapper;


import org.apache.ibatis.annotations.Param;
import xyz.pplax.pplaxblog.xo.base.mapper.SuperMapper;
import xyz.pplax.pplaxblog.xo.entity.Blog;

import java.util.Date;
import java.util.List;

/**
 * 博客表 Mapper 接口
 */
public interface BlogMapper extends SuperMapper<Blog> {

    /**
     * 查询博客发布的所有日期
     * @return
     */
    List<Date> selectCreateDateListDesc();

    /**
     * 根据创建日期查询博客列表
     * 其中博客只返回 uid，title，status，createTime，updateTime 字段
     */
    List<Blog> selectSimplifiedListByCreateDate(@Param("date") Date date, @Param("userUid") String userUid);

}
