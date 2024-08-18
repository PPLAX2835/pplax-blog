package xyz.pplax.pplaxblog.xo.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;
import xyz.pplax.pplaxblog.xo.base.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * 代码生成 Mapper 接口
 */
public interface CodeGenerateMapper extends SuperMapper<SuperEntity> {

    List<Map<String, Object>> selectPage(@Param("tableName") String tableName, @Param("offset") Long offset, @Param("limit") Long limit);

    int selectCount(@Param("tableName") String tableName);
}

