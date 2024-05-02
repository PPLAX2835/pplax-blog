package xyz.pplax.pplaxblog.file.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SayFileService extends FileService {

    /**
     * 说说图片上传
     * @param file
     * @return
     * @throws Exception
     */
    public ResponseResult imageUpload(MultipartFile file) throws Exception {

        // 判断是否是图片
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseResult.error(HttpStatus.BAD_REQUEST);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return upload("/say/image/" + simpleDateFormat.format(date) + "/", file);
    }

}
