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
import xyz.pplax.pplaxblog.file.utils.QiniuUtils;
import xyz.pplax.pplaxblog.xo.constants.sql.FileStorageSQLConstants;
import xyz.pplax.pplaxblog.xo.entity.FileStorage;
import xyz.pplax.pplaxblog.xo.entity.SiteSetting;
import xyz.pplax.pplaxblog.xo.service.FileStorageService;
import xyz.pplax.pplaxblog.xo.service.SiteSettingService;

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

        // 封装
        FileStorage fileStorage = new FileStorage();
        String fileStoragePath = path;
        String fileStorageName = new Date().getTime() + (suffix == null ? "" : "." + suffix);
        fileStorage.setOriginalName(file.getOriginalFilename());
        fileStorage.setFileName(fileStorageName);        // 用时间戳命名
        fileStorage.setSuffix(suffix);
        fileStorage.setFilePath(fileStoragePath);
        fileStorage.setFileSize(file.getSize());
        fileStorage.setIsDirectory(false);
        fileStorage.setStorageMode(storageMode);

        // 判断使用什么方式存储
        if (StorageModeConstants.MINIO.equals(storageMode)) {
            // minio的存储方式
            MinioUtils minioUtils = getMinioUtils();

            // 上传到minio
            ObjectWriteResponse objectWriteResponse = minioUtils.putObject(minioUtils.getBucketName(), fileStoragePath + fileStorageName, inputStream);
            // 从响应中获得访问地址
            String fileUrl = minioUtils.getEndpoint() + "/" + minioUtils.getBucketName() + objectWriteResponse.object();

            // 封装fileUrl
            fileStorage.setFileUrl(fileUrl);

            fileStorageService.save(fileStorage);
        } else if (StorageModeConstants.LOCAL_STORAGE.equals(storageMode)) {
            // 本地存储

        } else if (StorageModeConstants.QINIU.equals(storageMode)) {
            // 七牛云
            QiniuUtils qiniuUtils = getQiniuUtils();

            // 上传到七牛云
            String upload = qiniuUtils.upload(inputStream, fileStoragePath.substring(1) + fileStorageName);
            if (upload != null) {
                // 上传成功，封装fileUrl
                fileStorage.setFileUrl(qiniuUtils.getEndpoint() + fileStoragePath + fileStorageName);
                // 持久化
                fileStorageService.save(fileStorage);
            }
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
        // 获取这个文件的信息
        FileStorage fileStorage = fileStorageService.getById(fileUid);

        if (StorageModeConstants.MINIO.equals(fileStorage.getStorageMode())) {
            // 获取MinioUtils实例
            MinioUtils minioUtils = getMinioUtils();
            // 在minio中删除
            minioUtils.removeObject(minioUtils.getBucketName(), fileStorage.getFilePath() + fileStorage.getFileName());
        } else if (StorageModeConstants.QINIU.equals(fileStorage.getStorageMode())) {
            // 获取qiniuUtil实例
            QiniuUtils qiniuUtils = getQiniuUtils();
            // 在七牛云中删除
            qiniuUtils.delete(fileStorage.getFilePath().substring(1) + fileStorage.getFileName());
        }

        // 逻辑删除表数据
        fileStorageService.removeById(fileUid);

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
        Map<String, JSONObject> jsonObjectMap = (Map<String, JSONObject>) o;

        SiteSetting minioEndpointSetting = JSON.toJavaObject(jsonObjectMap.get("minioEndpoint"), SiteSetting.class);
        SiteSetting minioBucketNameSetting = JSON.toJavaObject(jsonObjectMap.get("minioBucketName"), SiteSetting.class);
        SiteSetting minioAccessKeySetting = JSON.toJavaObject(jsonObjectMap.get("minioAccessKey"), SiteSetting.class);
        SiteSetting minioSecretKeySetting = JSON.toJavaObject(jsonObjectMap.get("minioSecretKey"), SiteSetting.class);

        return new MinioUtils(
                (String) minioEndpointSetting.getValue(),
                (String) minioAccessKeySetting.getValue(),
                (String) minioSecretKeySetting.getValue(),
                (String) minioBucketNameSetting.getValue()
        );
    }

    /**
     * 获得七牛云utils
     * @return
     */
    private QiniuUtils getQiniuUtils() {
        Object o =  siteSettingService.map();
        Map<String, JSONObject> jsonObjectMap = (Map<String, JSONObject>) o;

        SiteSetting qiniuEndpointSetting = JSON.toJavaObject(jsonObjectMap.get("qiniuEndpoint"), SiteSetting.class);
        SiteSetting qiniuBucketNameSetting = JSON.toJavaObject(jsonObjectMap.get("qiniuBucketName"), SiteSetting.class);
        SiteSetting qiniuAccessKeySetting = JSON.toJavaObject(jsonObjectMap.get("qiniuAccessKey"), SiteSetting.class);
        SiteSetting qiniuSecretKeySetting = JSON.toJavaObject(jsonObjectMap.get("qiniuSecretKey"), SiteSetting.class);
        SiteSetting qiniuZoneSetting = JSON.toJavaObject(jsonObjectMap.get("qiniuZone"), SiteSetting.class);

        return new QiniuUtils(
                (String) qiniuEndpointSetting.getValue(),
                (String) qiniuAccessKeySetting.getValue(),
                (String) qiniuSecretKeySetting.getValue(),
                (String) qiniuZoneSetting.getValue(),
                (String) qiniuBucketNameSetting.getValue()
        );
    }

    /**
     * 获取存储模式
     * @return
     */
    private String getStorageMode() {
        Object o = siteSettingService.map();
        Map<String, JSONObject> jsonObjectMap = (Map<String, JSONObject>) o;

        SiteSetting minioEndpointSetting = JSON.toJavaObject(jsonObjectMap.get("storageMode"), SiteSetting.class);
        return (String) minioEndpointSetting.getValue();
    }
}
