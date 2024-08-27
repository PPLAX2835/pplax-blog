package xyz.pplax.pplaxblog.file.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.file.model.ConvertToMultipartFile;

import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BlogFileService extends FileService {

    /**
     * 上传博客封面图
     * @param file
     * @return
     * @throws Exception
     */
    public ResponseResult blogCoverImageUpload(MultipartFile file) throws Exception {

        // 判断是否是图片
        if (!isImage(file)) {
            return ResponseResult.error(HttpStatus.NOT_IMAGE);
        }
        // 放缩图片
        BufferedImage resizeImage = imageResize(file, 1280, 800);

        // BufferedImage 转化为 byte[]
        byte[] imageByte = bufferedImageToByteArray(resizeImage);

        ConvertToMultipartFile convertToMultipartFile = new ConvertToMultipartFile(imageByte, file.getName(), file.getOriginalFilename(), file.getContentType(), file.getSize());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return upload("/blog/coverImage/" + simpleDateFormat.format(date) + "/", convertToMultipartFile);
    }

    /**
     * 为博客内容上传文件
     * @param file
     * @return
     * @throws Exception
     */
    public ResponseResult attachUpload(MultipartFile file) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return upload("/blog/attach/" + simpleDateFormat.format(date) + "/", file);
    }

    /**
     * 为博客内容上传图片
     * @param file
     * @return
     * @throws Exception
     */
    public ResponseResult imageAttachUpload( MultipartFile file) throws Exception {

        // 判断是否是图片
        if (!isImage(file)) {
            return ResponseResult.error(HttpStatus.NOT_IMAGE);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return upload("/blog/attach/" + simpleDateFormat.format(date) + "/", file);
    }

    /**
     * 为博客内容上传视频
     * @param file
     * @return
     * @throws Exception
     */
    public ResponseResult videoAttachUpload(MultipartFile file) throws Exception {
        // 判断是否是视频
        if (!isVideo(file)) {
            return ResponseResult.error(HttpStatus.NOT_VIDEO);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return upload("/blog/attach/" + simpleDateFormat.format(date) + "/", file);
    }

}
