package xyz.pplax.pplaxblog.file.service;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.file.model.ConvertToMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BlogFileService extends FileService {

    /**
     * 上传博客封面图
     * @param mode
     * @param file
     * @return
     * @throws Exception
     */
    public ResponseResult blogCoverImageUpload(String mode, MultipartFile file) throws Exception {

        // 判断是否是图片
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseResult.error(HttpStatus.BAD_REQUEST);
        }
        // 放缩图片
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
        BufferedImage resizeImage = Scalr.resize(bufferedImage, 1280, 800);

        //BufferedImage 转化为 ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(resizeImage, CharacterConstants.FILE_SUFFIX_JPG, byteArrayOutputStream);
        //ByteArrayOutputStream 转化为 byte[]
        byte[] imageByte = byteArrayOutputStream.toByteArray();

        ConvertToMultipartFile convertToMultipartFile = new ConvertToMultipartFile(imageByte, file.getName(), file.getOriginalFilename(), file.getContentType(), file.getSize());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return upload(mode, "/blog/coverImage/" + simpleDateFormat.format(date) + "/", convertToMultipartFile);
    }

    /**
     * 为博客内容上传文件
     * @param mode
     * @param file
     * @return
     * @throws Exception
     */
    public ResponseResult attachUpload(String mode, MultipartFile file) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return upload(mode, "/blog/attach/" + simpleDateFormat.format(date) + "/", file);
    }

    /**
     * 为博客内容上传图片
     * @param mode
     * @param file
     * @return
     * @throws Exception
     */
    public ResponseResult imageAttachUpload(String mode, MultipartFile file) throws Exception {

        // 判断是否是图片
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseResult.error(HttpStatus.NOT_IMAGE);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return upload(mode, "/blog/attach/" + simpleDateFormat.format(date) + "/", file);
    }

    /**
     * 为博客内容上传视频
     * @param mode
     * @param file
     * @return
     * @throws Exception
     */
    public ResponseResult videoAttachUpload(String mode, MultipartFile file) throws Exception {
        // 判断是否是视频
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("video/")) {
            return ResponseResult.error(HttpStatus.NOT_VIDEO);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return upload(mode, "/blog/attach/" + simpleDateFormat.format(date) + "/", file);
    }

}
