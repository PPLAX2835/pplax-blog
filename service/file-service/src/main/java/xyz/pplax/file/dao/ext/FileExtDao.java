package xyz.pplax.file.dao.ext;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.file.po.File;

import java.util.List;

@Mapper
public interface FileExtDao {
    List<File> selectSpecifyFormatFiles(@Param("condition") Condition<Long> condition);

    /**
     * 查询此userUid对应的所有文件格式
     * @param userUid
     * @return
     */
    List<String> selectAllFileFormat(@Param("userUid") Long userUid);

}