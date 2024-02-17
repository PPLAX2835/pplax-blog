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
import xyz.pplax.pplaxblog.xo.constants.sql.UserInfoSQLConstants;
import xyz.pplax.pplaxblog.xo.entity.FileStorage;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.entity.UserInfo;
import xyz.pplax.pplaxblog.xo.service.filestorage.FileStorageService;
import xyz.pplax.pplaxblog.xo.service.user.UserService;
import xyz.pplax.pplaxblog.xo.service.userinfo.UserInfoService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;

@Service
public class AvatarService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private MinioUtils minioUtils;

    @Value("${pplax.storage.minio.bucketName}")
    private String minioBucketName;

    @Value("${pplax.storage.minio.endpoint}")
    private String minioEndpoint;

    /**
     * 上传头像
     * @param mode
     * @param userUid
     * @param file
     * @return
     * @throws Exception
     */
    public ResponseResult avatarUpload(String mode, String userUid, MultipartFile file) throws Exception {
        // 存储模式参数不能为空
        if (StringUtils.isEmpty(mode)) {
            return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 判断是否是图片
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseResult.error(HttpStatus.BAD_REQUEST);
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

        // 放缩图片
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
        BufferedImage resizeImage = Scalr.resize(bufferedImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, 200);

        // 将放缩后的图片转为输入流
        ByteArrayOutputStream outStream  = new ByteArrayOutputStream();
        ImageIO.write(resizeImage, suffix, outStream);
        InputStream inputStream = new ByteArrayInputStream(outStream .toByteArray());

        FileStorage fileStorage = new FileStorage();
        // 判断使用什么方式存储
        if (mode.equals(StorageModeConstants.MINIO)) {      // minio的存储方式

            String fileStoragePath = "/users/" + userUid + "/avatar/";
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

        // 头像上传成功，更新用户信息
        User user = userService.getById(userUid);
        UpdateWrapper<UserInfo> userInfoUpdateWrapper = new UpdateWrapper<>();
        userInfoUpdateWrapper.eq(UserInfoSQLConstants.UID, user.getUserInfoUid());
        userInfoUpdateWrapper.set(UserInfoSQLConstants.AVATAR_PICTURE_UID, fileStorage.getUid());
        userInfoService.update(userInfoUpdateWrapper);

        return ResponseResult.success();
    }


    /**
     * 删除文件
     * @param mode
     * @param fileUid
     * @return
     * @throws Exception
     */
    public ResponseResult avatarDelete(String mode , String fileUid) throws Exception {
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

        // 逻辑删除
        UpdateWrapper<FileStorage> fileStorageUpdateWrapper = new UpdateWrapper<>();
        fileStorageUpdateWrapper.set(FileStorageSQLConstants.STATUS, EStatus.DISABLED.getStatus());
        fileStorageUpdateWrapper.eq(FileStorageSQLConstants.UID, fileUid);
        fileStorageService.update(fileStorageUpdateWrapper);

        return ResponseResult.success();
    }

}
