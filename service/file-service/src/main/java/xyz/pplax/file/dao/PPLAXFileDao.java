package xyz.pplax.file.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.pplax.file.po.File;
import xyz.pplax.service.base.BaseDao;

/**
 * file 表DAO层
 */
@Mapper
public interface PPLAXFileDao extends BaseDao<File> {

}