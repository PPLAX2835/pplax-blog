package xyz.pplax.pplaxblog.file.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.ImageUtils;
import xyz.pplax.pplaxblog.file.model.ConvertToMultipartFile;

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
        if (!isImage(file)) {
            return ResponseResult.error(HttpStatus.NOT_IMAGE);
        }
        // 压缩图片
        byte[] imageByte = ImageUtils.compressPicForScale(file.getBytes(), 0.5f);
        ConvertToMultipartFile convertToMultipartFile = new ConvertToMultipartFile(imageByte, file.getName(), file.getOriginalFilename(), file.getContentType(), file.getSize());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return upload("/chatRoom/avatar/" + simpleDateFormat.format(date) + "/", convertToMultipartFile);
    }

}
