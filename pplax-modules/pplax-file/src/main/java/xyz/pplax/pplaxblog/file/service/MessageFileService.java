package xyz.pplax.pplaxblog.file.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MessageFileService extends FileService {

    /**
     * 上传图片
     * @param file
     * @return
     * @throws Exception
     */
    public ResponseResult imageUpload(MultipartFile file) throws Exception {

        // 判断是否是图片
        if (!isImage(file)) {
            return ResponseResult.error(HttpStatus.NOT_IMAGE);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return upload("/message/" + simpleDateFormat.format(date) + "/", file);
    }

}
