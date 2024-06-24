package xyz.pplax.pplaxblog.file.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ChatRoomFileService extends FileService {

    /**
     * 上传头像
     * @param file
     * @return
     * @throws Exception
     */
    public ResponseResult avatarUpload(MultipartFile file) throws Exception {

        // 判断是否是图片
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseResult.error(HttpStatus.NOT_IMAGE);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return upload("/chatRoom/avatar/" + simpleDateFormat.format(date) + "/", file);
    }

}
