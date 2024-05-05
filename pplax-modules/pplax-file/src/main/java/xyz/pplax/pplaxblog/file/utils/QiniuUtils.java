package xyz.pplax.pplaxblog.file.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import lombok.Data;

import java.io.InputStream;

/**
 * @description: 七牛工具类
 * @author: PPLAX
 * @create: 2024-05-05 12:43
 */
@Data
public class QiniuUtils {

    // 端点
    private String endpoint = "sso.qiniu.com";
    // key 控制台查看
    private String accessKey = "你的key"; // key
    // secret 秘钥
    private String secretKey = "你的secret"; // 秘钥
    // 空间
    private String bucket = "你的空间";
    // 地区
    private String zone = "地区";

    /**
     * 地区：
     * 华东    Region.region0(), Region.huadong()
     * 华北    Region.region1(), Region.huabei()
     * 华南    Region.region2(), Region.huanan()
     * 北美    Region.regionNa0(), Region.beimei()
     * 东南亚    Region.regionAs0(), Region.xinjiapo()
     */
    //构造一个带指定 Region 对象的配置类
    private Configuration cfg = null;
    private String upToken = "";
    private Auth auth = null;

    public QiniuUtils (String endpoint, String accessKey, String secretKey, String zone, String bucket) {
        this.endpoint = endpoint;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.zone = zone;
        this.bucket = bucket;

        switch (zone) {
            case "huadong":
                this.cfg =  new Configuration(Region.huadong());
                break;
            case "huabei":
                this.cfg =  new Configuration(Region.huabei());
                break;
            case "huanan":
                this.cfg =  new Configuration(Region.huanan());
                break;
            case "beimei":
                this.cfg =  new Configuration(Region.beimei());
                break;
            case "xinjiapo":
                this.cfg =  new Configuration(Region.xinjiapo());
                break;
        }
        this.auth = Auth.create(accessKey, secretKey);
        this.upToken = auth.uploadToken(bucket);
    }

    /**
     * create by: Mr.Fang
     * description: 本地上传文件
     * create time: 2022/5/1 9:22
     *
     * @return java.lang.String 返回 JSON 字符串响应结果：[{"hash":"FsTuB-z8xNrghfYYF8HD8z4RsG5b","key":"FsTuB-z8xNrghfYYF8HD8z4RsG5b"}]
     * @Param: localFilePath 文件路径
     * @Param: key 默认不指定key的情况下，以文件内容的hash值作为文件名
     */
    public String upload(String localFilePath, String key) {
        UploadManager uploadManager = new UploadManager(cfg);
        Response response = null;
        try {
            response = uploadManager.put(localFilePath, key, upToken);
            String bodyString = response.bodyString();  // 响应结果
            printResult(response);
            return bodyString;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                ex2.printStackTrace();
            }
        }
        return null;
    }

    /**
     * create by: Mr.Fang
     * description: 字节数组上传
     * create time: 2022/5/1 9:22
     *
     * @return java.lang.String 返回 JSON 字符串响应结果：[{"hash":"FsTuB-z8xNrghfYYF8HD8z4RsG5b","key":"FsTuB-z8xNrghfYYF8HD8z4RsG5b"}]
     * @Param: bytes 字节数组
     * @Param: key 默认不指定 key 的情况下，以文件内容的 hash 值作为文件名
     */
    public String upload(byte[] bytes, String key) {
        UploadManager uploadManager = new UploadManager(cfg);
        Response response = null;
        try {
            response = uploadManager.put(bytes, key, upToken);
            String bodyString = response.bodyString();  // 响应结果
            printResult(response);
            return bodyString;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                ex2.printStackTrace();
            }
        }
        return null;
    }

    /**
     * create by: Mr.Fang
     * description: 字节数组上传
     * create time: 2022/5/1 9:22
     *
     * @return java.lang.String 返回 JSON 字符串响应结果：[{"hash":"FsTuB-z8xNrghfYYF8HD8z4RsG5b","key":"FsTuB-z8xNrghfYYF8HD8z4RsG5b"}]
     * @Param: is 输入流
     * @Param: key 默认不指定 key 的情况下，以文件内容的 hash 值作为文件名
     */
    public String upload(InputStream is, String key) {
        UploadManager uploadManager = new UploadManager(cfg);
        Response response = null;
        try {
            response = uploadManager.put(is, key, upToken, null, null);
            String bodyString = response.bodyString();  // 响应结果
            printResult(response);
            return bodyString;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                ex2.printStackTrace();
            }
        }
        return null;
    }


    /**
     * create by: Mr.Fang
     * description: 删除服务器文件
     * create time: 2022/5/1 9:39
     *
     * @return int 返回状态码 200 就 ok
     * @Param: key 七牛的key
     */
    public int delete(String key) {
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
            Response response = bucketManager.bucketsResponse();
            int statusCode = response.statusCode; // 状态码
            printResult(response);
            return statusCode;
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        }
        return 0;
    }

    /**
     * create by: Mr.Fang
     * description: 删除服务器文件，指定过期时间，以天为单位
     * create time: 2022/5/1 9:45
     *
     * @return int 返回状态码 200 就 ok
     * @Param: key 七牛的key
     * @Param: days 啥时候删除
     */
    public int delete(String key, int days) {
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.deleteAfterDays(bucket, key, days);
            Response response = bucketManager.bucketsResponse();
            int statusCode = response.statusCode; // 状态码
            printResult(response);
            return statusCode;
        } catch (QiniuException ex) {
            ex.printStackTrace();
            System.err.println(ex.response.toString());
        }
        return 0;
    }

    /**
     * create by: Mr.Fang
     * description: 批量删除
     * create time: 2022/5/1 15:42
     *
     * @return int
     * @Param: keys 多个 key
     */
    public BatchStatus[] batchDelete(String... keys) {
        BucketManager bucketManager = new BucketManager(auth, cfg);
        BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
        batchOperations.addDeleteOp(bucket, keys);
        try {
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            return batchStatusList;
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * create by: Mr.Fang
     * description: 获取资源列表，
     *
     * @return java.lang.String  字符串响应结果：
     * @Param: prefix 文件名前缀
     * @Param: marker 位置标记这个参数是七牛返回的，下次携带这个参数访问就可以实现分页了，如果没有返回这个参数表示，总记录不满足分页条件，或最后一页了
     * @Param: limit 每次迭代的长度限制，最大1000，推荐值 1000
     * @Param: delimiter 指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
     * @Time time: 2022/5/1 9:52
     * @see "字段含义这里 https://developer.qiniu.com/kodo/1284/list"
     */
    public FileListing listPage(String prefix, String marker, int limit, String delimiter) {
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            // FIXME: 2022/5/1 返回JSON格式 自己处理
//            Response response = bucketManager.listV2(bucket, prefix, marker, limit, delimiter);
//            String bodyString = response.bodyString();  // 响应结果
//            printResult(response);
//            return bodyString;
            // FIXME: 2022/5/1 返回 牛牛封装的文件对象
            FileListing fileListing = bucketManager.listFiles(bucket, prefix, marker, limit, delimiter);
            return fileListing;

        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * create by: Mr.Fang
     * description: 输出响应结果
     * create time: 2022/5/1 9:41
     *
     * @return void
     * @Param: response
     */
    public void printResult(Response response) {
        System.out.println(String.format("状态码：[%d]", response.statusCode)); // 状态码：[200]
        System.out.println(String.format("耗时：[%f]", response.duration)); // 耗时：[0.366000] 单位毫秒
        try {
            System.out.println(String.format("响应结果：[%s]", response.bodyString())); // 响应结果：[["32123","222"]]
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }

}