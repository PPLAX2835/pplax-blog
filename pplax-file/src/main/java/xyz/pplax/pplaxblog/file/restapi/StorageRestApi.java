package xyz.pplax.pplaxblog.file.restapi;


import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件操作接口
 *
 * @author PPLAX
 * @date 2024/1/18 14:37
 */
@RestController
@RequestMapping("/storage")
@Api(value = "文件存储服务相关接口", tags = {"文件存储服务相关接口"})
public class StorageRestApi {
    private static final Logger log = LogManager.getLogger(StorageRestApi.class);

    @Value(value = "${file.upload.base-path}")
    String path;



}
