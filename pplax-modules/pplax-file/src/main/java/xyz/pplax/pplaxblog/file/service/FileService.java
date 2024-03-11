package xyz.pplax.pplaxblog.file.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.minio.ObjectWriteResponse;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.commons.constants.StorageModeConstants;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.file.components.MinioUtils;
import xyz.pplax.pplaxblog.xo.constants.sql.FileStorageSQLConstants;
import xyz.pplax.pplaxblog.xo.entity.FileStorage;
import xyz.pplax.pplaxblog.xo.service.filestorage.FileStorageService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;

@Service
public class FileService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private MinioUtils minioUtils;

    @Value("${pplax.storage.minio.bucketName:pplax-blog}")
    private String minioBucketName;

    @Value("${pplax.storage.minio.endpoint:pplax.xyz:9002}")
    private String minioEndpoint;

    /**
     * 上传文件
     * @param mode
     * @param userUid
     * @param path
     * @param file
     * @return
     */
    public ResponseResult upload(String mode, String userUid, String path, MultipartFile file) throws Exception {
        // 存储模式参数不能为空
        if (StringUtils.isEmpty(mode)) {
            return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 获得文件后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = null;
        if (StringUtils.isEmpty(originalFilename)) {
            return ResponseResult.error(HttpStatus.BAD_REQUEST);
        }
        int lastDotIndex = originalFilename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return ResponseResult.error(HttpStatus.BAD_REQUEST);
        }
        suffix = originalFilename.substring(lastDotIndex + 1);

        // 获得输入流
        InputStream inputStream = file.getInputStream();

        FileStorage fileStorage = new FileStorage();
        // 判断使用什么方式存储
        if (mode.equals(StorageModeConstants.MINIO)) {      // minio的存储方式

            String fileStoragePath = path + userUid + "/";
            String fileStorageName = new Date().getTime() + (suffix == null ? "" : "." + suffix);

            // 上传到minio
            ObjectWriteResponse objectWriteResponse = minioUtils.putObject(minioBucketName, fileStoragePath + fileStorageName, inputStream);
            // 从响应中获得访问地址
            String fileUrl = minioEndpoint + "/" + minioBucketName + objectWriteResponse.object();

            // 封装
            fileStorage.setUserUid(userUid);
            fileStorage.setOriginalName(file.getOriginalFilename());
            fileStorage.setFileName(fileStorageName);        // 用时间戳命名
            fileStorage.setSuffix(suffix);
            fileStorage.setFilePath(fileStoragePath);
            fileStorage.setFileSize(file.getSize());
            fileStorage.setIsDirectory(false);
            fileStorage.setIsImage(true);
            fileStorage.setStorageMode(StorageModeConstants.MINIO);
            fileStorage.setFileUrl(fileUrl);

            fileStorageService.save(fileStorage);
        } else if (mode.equals(StorageModeConstants.LOCAL_STORAGE)) {
            // 本地存储

        }

        return ResponseResult.success(fileStorage);
    }



    /**
     * 删除文件
     * @param mode
     * @param fileUid
     * @return
     * @throws Exception
     */
    public ResponseResult delete(String mode, String fileUid) throws Exception {
        // 校验参数
        if (StringUtils.isEmpty(mode) || StringUtils.isEmpty(fileUid)) {
            return ResponseResult.error(HttpStatus.BAD_REQUEST);
        }

        // 获取这个文件的信息
        FileStorage fileStorage = fileStorageService.getById(fileUid);

        if (mode.equals(StorageModeConstants.MINIO)) {
            // 在minio中删除
            minioUtils.removeObject(minioBucketName, fileStorage.getFilePath() + fileStorage.getFileName());
        }

        // 逻辑删除表数据
        UpdateWrapper<FileStorage> fileStorageUpdateWrapper = new UpdateWrapper<>();
        fileStorageUpdateWrapper.set(FileStorageSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        fileStorageUpdateWrapper.eq(FileStorageSQLConstants.C_UID, fileUid);
        fileStorageService.update(fileStorageUpdateWrapper);

        return ResponseResult.success();
    }

}
