package xyz.pplax.pplaxblog.xo.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.entity.${className};
import xyz.pplax.pplaxblog.xo.mapper.${className}Mapper;
import xyz.pplax.pplaxblog.xo.service.${className}Service;

/**
* @description ${tableName}表 服务实现类
* @author ${author}
* @date ${date}
*/
@Service
public class ${className}ServiceImpl extends SuperServiceImpl<${className}Mapper, ${className}> implements ${className}Service {

    private static final Logger log = LogManager.getLogger(${className}ServiceImpl.class);

}
