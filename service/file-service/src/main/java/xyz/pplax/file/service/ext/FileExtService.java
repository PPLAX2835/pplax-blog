package xyz.pplax.file.service.ext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.file.dao.ext.FileExtDao;
import xyz.pplax.file.po.File;

import java.util.List;


@Service
public class FileExtService {

    @Autowired
    private FileExtDao fileExtDao;
    public List<File> selectSpecifyFormatFiles(Condition<Long> condition) {
        return fileExtDao.selectSpecifyFormatFiles(condition);
    }

    /**
     * 查询此userUid对应的所有文件格式
     * @param userUid
     * @return
     */
    public List<String> selectAllFileFormat(Long userUid) {
        return fileExtDao.selectAllFileFormat(userUid);
    }
}
