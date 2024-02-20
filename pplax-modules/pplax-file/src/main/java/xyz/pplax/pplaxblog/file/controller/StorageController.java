package xyz.pplax.pplaxblog.file.controller;


import io.swagger.annotations.Api;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class StorageController {
    private static final Logger log = LogManager.getLogger(StorageController.class);



}
