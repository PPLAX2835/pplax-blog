package xyz.pplax.pplaxblog.file.service;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.file.model.ConvertToMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserFileService extends FileService {

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
        // 放缩图片
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
        BufferedImage resizeImage = Scalr.resize(bufferedImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, 200);

        // BufferedImage 转化为 byte[]
        byte[] imageByte = bufferedImageToByteArray(resizeImage);

        ConvertToMultipartFile convertToMultipartFile = new ConvertToMultipartFile(imageByte, file.getName(), file.getOriginalFilename(), file.getContentType(), file.getSize());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return upload("/user/avatar/" + simpleDateFormat.format(date) + "/", convertToMultipartFile);
    }

    /**
     * 上传空间背景图
     * @param file
     * @return
     * @throws Exception
     */
    public ResponseResult spaceBackgroundPictureUpload(MultipartFile file) throws Exception {

        // 判断是否是图片
        if (!isImage(file)) {
            return ResponseResult.error(HttpStatus.NOT_IMAGE);
        }
        // 放缩图片
        BufferedImage resizeImage = imageResize(file, 1024, 590);

        // BufferedImage 转化为 byte[]
        byte[] imageByte = bufferedImageToByteArray(resizeImage);

        ConvertToMultipartFile convertToMultipartFile = new ConvertToMultipartFile(imageByte, file.getName(), file.getOriginalFilename(), file.getContentType(), file.getSize());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return upload("/user/spaceBackgroundPicture/" + simpleDateFormat.format(date) + "/", convertToMultipartFile);
    }

}
