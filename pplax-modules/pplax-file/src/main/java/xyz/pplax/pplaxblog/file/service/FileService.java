package xyz.pplax.pplaxblog.file.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.commons.constants.StorageModeConstants;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.file.utils.MinioUtils;
import xyz.pplax.pplaxblog.xo.constants.sql.FileStorageSQLConstants;
import xyz.pplax.pplaxblog.xo.entity.FileStorage;
import xyz.pplax.pplaxblog.xo.entity.SiteSetting;
import xyz.pplax.pplaxblog.xo.service.FileStorageService;
import xyz.pplax.pplaxblog.xo.service.SiteSettingService;

import javax.validation.constraints.Min;
import java.io.InputStream;
import java.util.*;

@Service
public class FileService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private SiteSettingService siteSettingService;

    /**
     * 上传文件
     * @param path
     * @param file
     * @return
     */
    public ResponseResult upload(String path, MultipartFile file) throws Exception {
        // 获取必要实例，不使用注入方式了
        String storageMode = getStorageMode();
        MinioUtils minioUtils = getMinioUtils();

        // 存储模式参数不能为空
        if (StringUtils.isEmpty(storageMode)) {
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
        if (StorageModeConstants.MINIO.equals(storageMode)) {      // minio的存储方式

            String fileStoragePath = path;
            String fileStorageName = new Date().getTime() + (suffix == null ? "" : "." + suffix);

            // 上传到minio
            ObjectWriteResponse objectWriteResponse = minioUtils.putObject(minioUtils.getMinioBucketName(), fileStoragePath + fileStorageName, inputStream);
            // 从响应中获得访问地址
            String fileUrl = minioUtils.getMinioEndpoint() + "/" + minioUtils.getMinioBucketName() + objectWriteResponse.object();

            // 封装
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
        } else if (StorageModeConstants.LOCAL_STORAGE.equals(storageMode)) {
            // 本地存储

        }

        return ResponseResult.success(fileStorage);
    }



    /**
     * 删除文件
     * @param fileUid
     * @return
     * @throws Exception
     */
    public ResponseResult delete(String fileUid) throws Exception {
        // 获取必要实例，不使用注入方式了
        MinioUtils minioUtils = getMinioUtils();

        // 获取这个文件的信息
        FileStorage fileStorage = fileStorageService.getById(fileUid);

        if (StorageModeConstants.MINIO.equals(fileStorage.getStorageMode())) {
            // 在minio中删除
            minioUtils.removeObject(minioUtils.getMinioBucketName(), fileStorage.getFilePath() + fileStorage.getFileName());
        }

        // 逻辑删除表数据
        UpdateWrapper<FileStorage> fileStorageUpdateWrapper = new UpdateWrapper<>();
        fileStorageUpdateWrapper.set(FileStorageSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        fileStorageUpdateWrapper.eq(FileStorageSQLConstants.C_UID, fileUid);
        fileStorageService.update(fileStorageUpdateWrapper);

        return ResponseResult.success();
    }

    @Transactional
    public ResponseResult deleteBatch(List<String> fileStorageUidList) throws Exception {
        for (String fileStorageUid : fileStorageUidList) {
            ResponseResult responseResult = delete(fileStorageUid);

            if (!Objects.equals(responseResult.getCode(), ResponseResult.success().getCode())) {
                throw new RuntimeException();
            }
        }


        return ResponseResult.success();
    }


    /**
     * 获取minioUtils
     * @return
     */
    private MinioUtils getMinioUtils() {
        Object o =  siteSettingService.map();
        Map<String, JSON> jsonObjectMap = (Map<String, JSON>) o;

        SiteSetting minioEndpointSetting = JSON.toJavaObject(jsonObjectMap.get("minioEndpoint"), SiteSetting.class);
        SiteSetting minioBucketNameSetting = JSON.toJavaObject(jsonObjectMap.get("minioBucketName"), SiteSetting.class);
        SiteSetting minioAccessKeySetting = JSON.toJavaObject(jsonObjectMap.get("minioAccessKey"), SiteSetting.class);
        SiteSetting minioSecretKeySetting = JSON.toJavaObject(jsonObjectMap.get("minioSecretKey"), SiteSetting.class);

        return new MinioUtils(
                MinioClient.builder()
                        .endpoint((String) minioEndpointSetting.getValue())
                        .credentials((String) minioAccessKeySetting.getValue(), (String) minioSecretKeySetting.getValue())
                        .build(),
                (String) minioEndpointSetting.getValue(),
                (String) minioAccessKeySetting.getValue(),
                (String) minioSecretKeySetting.getValue(),
                (String) minioBucketNameSetting.getValue()
        );
    }

    /**
     * 获取存储模式
     * @return
     */
    private String getStorageMode() {
        Object o = siteSettingService.map();
        Map<String, JSON> jsonObjectMap = (Map<String, JSON>) o;

        SiteSetting minioEndpointSetting = JSON.toJavaObject(jsonObjectMap.get("storageMode"), SiteSetting.class);
        return (String) minioEndpointSetting.getValue();
    }
}
