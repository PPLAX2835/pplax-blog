package xyz.pplax.pplaxblog.file.restapi;


import io.swagger.annotations.Api;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件操作接口
 *
 * @author PPLAX
 * @date 2024/1/18 14:37
 */
@RestController
@RequestMapping("${pplax.request.base-path}/storage")
@Api(value = "文件存储服务相关接口", tags = {"文件存储服务相关接口"})
public class StorageRestApi {
    private static final Logger log = LogManager.getLogger(StorageRestApi.class);



}
