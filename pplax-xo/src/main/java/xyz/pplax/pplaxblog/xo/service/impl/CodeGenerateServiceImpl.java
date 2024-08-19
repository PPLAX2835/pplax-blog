package xyz.pplax.pplaxblog.xo.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.xo.base.entity.SuperEntity;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.mapper.CodeGenerateMapper;
import xyz.pplax.pplaxblog.xo.service.CodeGenerateService;

import java.util.List;
import java.util.Map;

/**
 * 角色权限表 服务实现类
 */
@Service
public class CodeGenerateServiceImpl extends SuperServiceImpl<CodeGenerateMapper, SuperEntity> implements CodeGenerateService {

    private static final Logger log = LogManager.getLogger(CodeGenerateServiceImpl.class);

    @Autowired
    private CodeGenerateMapper codeGenerateMapper;

    @Override
    public List<Map<String, Object>> list(String tableName, Long currentPage, Long pageSize) {
        return codeGenerateMapper.selectPage(tableName, (currentPage - 1) * pageSize, pageSize);
    }

    @Override
    public Map<String, Object> getOne(String tableName) {
        return codeGenerateMapper.selectOne(tableName);
    }

    @Override
    public int count(String tableName) {
        return codeGenerateMapper.selectCount(tableName);
    }

    @Override
    public List<Map<String, Object>> getTableColumns(String tableName) {
        return codeGenerateMapper.selectTableColumns(tableName);
    }
}
