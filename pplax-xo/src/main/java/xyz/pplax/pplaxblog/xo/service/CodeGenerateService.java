package xyz.pplax.pplaxblog.xo.service;

import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;

import java.util.List;
import java.util.Map;

/**
 * 代码生成 服务类
 */
public interface CodeGenerateService extends SuperService<SuperEntity> {

    List<Map<String, Object>> list(String keyword, Long currentPage, Long pageSize);

    int count(String tableName);
}
