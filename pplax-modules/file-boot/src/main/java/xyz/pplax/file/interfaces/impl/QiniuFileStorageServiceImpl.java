package xyz.pplax.file.interfaces.impl;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.*;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.pplax.starter.properties.PPLAXProperties;
import xyz.pplax.core.exception.file.FileException;
import xyz.pplax.core.util.DateUtils;
import xyz.pplax.core.util.FileUtils;
import xyz.pplax.core.util.LogUtils;
import xyz.pplax.file.dto.FileEntityDTO;
import xyz.pplax.file.interfaces.FileStorageService;
import xyz.pplax.file.pojo.FilePojo;
import xyz.pplax.file.utils.FileStorageUtil;
import xyz.pplax.file.vo.QiniuReturnBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;


@Component
@RefreshScope
public class QiniuFileStorageServiceImpl implements FileStorageService {

    @Autowired
    private PPLAXProperties.PPLAXFileProperties pplaxFileProperties;

    @Override
    public FileEntityDTO upload(InputStream inputStream, FileEntityDTO fileEntity, FilePojo pojo) throws FileException, IOException, ExecutionException, InterruptedException {

        if (!StringUtils.hasLength(pplaxFileProperties.getQiniuOssAccessKey()) ||
                !StringUtils.hasLength(pplaxFileProperties.getQiniuOssSecretKey()) ||
                        !StringUtils.hasLength(pplaxFileProperties.getQiniuOssDomain()) ||
                !StringUtils.hasLength(pplaxFileProperties.getQiniuOssBucketName())) {
            throw new FileException("请配置七牛云相关信息");
        }
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huadongZheJiang2());
        // 指定分片上传版本
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;

        UploadManager uploadManager = new UploadManager(cfg);

        String storagePath = FileStorageUtil.getStoragePathDirByTimeAndFileName(fileEntity.getName(), true, pojo) + "/" + fileEntity.getName();
        Auth auth = Auth.create(pplaxFileProperties.getQiniuOssAccessKey(), pplaxFileProperties.getQiniuOssSecretKey());

        StringMap policy = new StringMap();
        policy.put("returnBody", "{\"key\":\"$(key)\",\"bucket\":\"$(bucket)\",\"fsize\":\"$(fsize)\",\"hash\":\"$(etag)\",\"mimeType\":\"$(mimeType)\"}");
        policy.put("scope", storagePath);
        String upToken = auth.uploadToken(pplaxFileProperties.getQiniuOssBucketName(), storagePath, 3600, policy);

        // 文件名，包括目录路径
        Response response = null;
        try {
            response = uploadManager.put(inputStream, storagePath, upToken, null, null);
        } catch (QiniuException e) {
            LogUtils.logExceptionInfo(e);
            throw new FileException(e.getMessage());
        }
        //解析上传成功的结果
        QiniuReturnBody qiniuReturnBody = JSON.parseObject(response.bodyString(), QiniuReturnBody.class);
        FileEntityDTO fileEntityDTO = new FileEntityDTO();
        fileEntityDTO.setName(qiniuReturnBody.getKey());
        fileEntityDTO.setSize(qiniuReturnBody.getFsize());
        fileEntityDTO.setStoragePath(qiniuReturnBody.getKey());
        fileEntityDTO.setFilePathUri(qiniuReturnBody.getHash());
        fileEntityDTO.setRemoteUrl(pplaxFileProperties.getQiniuOssDomain() + "/" + storagePath);
        fileEntityDTO.setCreateTime(DateUtils.format());
        return fileEntityDTO;
    }

    @Override
    public FileEntityDTO download(String objectName, FilePojo pojo) throws IOException {
        BucketManager bucketManager = getBucketManager();

        Response response = null;
        FileEntityDTO fileEntity = null;
        QiniuReturnBody qiniuReturnBody = null;
        try {
            response = bucketManager.fetchResponse(getDownloadUrl(pplaxFileProperties.getQiniuOssDomain(), false, objectName), pplaxFileProperties.getQiniuOssBucketName(), objectName);
            fileEntity = new FileEntityDTO();
            qiniuReturnBody = JSON.parseObject(response.bodyString(), QiniuReturnBody.class);
        } catch (QiniuException e) {
            LogUtils.logExceptionInfo(e);
            throw new FileException(e.getMessage());
        }
        fileEntity.setName(FileUtils.getFileName(objectName));
        fileEntity.setInputStream(response.bodyStream());
        fileEntity.setRemoteUrl(getDownloadUrl(pplaxFileProperties.getQiniuOssDomain(), true, objectName));
        fileEntity.setStoragePath(qiniuReturnBody.getKey());
        fileEntity.setFilePathUri(qiniuReturnBody.getHash());
        fileEntity.setSize(qiniuReturnBody.getFsize());
        return fileEntity;
    }

    @Override
    public FileEntityDTO query(String objectName, FilePojo pojo) throws IOException {
        // Auth auth = Auth.create(pplaxFileProperties.getQiniuOssAccessKey(), pplaxFileProperties.getQiniuOssSecretKey());
        //
        // Configuration cfg = new Configuration(Region.huadongZheJiang2());
        // // 指定分片上传版本
        // cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        // BucketManager bucketManager = new BucketManager(auth, cfg);
        // FileInfo fileInfo = null;
        // try {
        //     fileInfo = bucketManager.stat(pplaxFileProperties.getQiniuOssBucketName(), objectName);
        // } catch (QiniuException e) {
        //     LogUtils.logExceptionInfo(e);
        //     throw new FileException(e.getMessage());
        // }
        // FileEntityDTO entity = new FileEntityDTO();
        // entity.setName(FileUtils.getFileName(objectName));
        // entity.setCreateTime(DateUtils.format(new Date(fileInfo.putTime)));
        // entity.setSize(fileInfo.fsize);
        // entity.setStoragePath(objectName);
        // entity.setRemoteUrl(pplaxFileProperties.getQiniuOssDomain() + "/" + objectName);
        FileEntityDTO fileEntityDTO = download(objectName, pojo);
        return fileEntityDTO;
    }

    @Override
    public boolean delete(String objectName, FilePojo pojo) {
        BucketManager bucketManager = getBucketManager();
        Response deleteResponse = null;
        try {
            deleteResponse = bucketManager.delete(pplaxFileProperties.getQiniuOssBucketName(), objectName);
        } catch (QiniuException e) {
            LogUtils.logExceptionInfo(e);
            return false;
        }
        return true;
    }

    private BucketManager getBucketManager() {
        if (!StringUtils.hasLength(pplaxFileProperties.getQiniuOssAccessKey()) ||
                !StringUtils.hasLength(pplaxFileProperties.getQiniuOssSecretKey()) ||
                !StringUtils.hasLength(pplaxFileProperties.getQiniuOssDomain()) ||
                !StringUtils.hasLength(pplaxFileProperties.getQiniuOssBucketName())) {
            throw new FileException("请配置七牛云相关信息");
        }
        Configuration configuration = new Configuration(Region.huadongZheJiang2());
        Auth auth = Auth.create(pplaxFileProperties.getQiniuOssAccessKey(), pplaxFileProperties.getQiniuOssSecretKey());
        return new BucketManager(auth, configuration);
    }
    private String getDownloadUrl(String domain, boolean isAttname, String objectName) throws QiniuException {
        boolean useHttps = domain.startsWith("https");
        if (pplaxFileProperties.getQiniuOssDomain().startsWith("https://")) {
            domain = pplaxFileProperties.getQiniuOssDomain().substring("https://".length());
        }

        if (pplaxFileProperties.getQiniuOssDomain().startsWith("http://")) {
            domain = pplaxFileProperties.getQiniuOssDomain().substring("http://".length());
        }
        DownloadUrl downloadUrl = new DownloadUrl(domain, useHttps, objectName);
        if (isAttname) {
            downloadUrl.setAttname(FileUtils.getFileName(objectName));
        }
        return downloadUrl.buildURL();
    }
}
