package xyz.pplax.pplaxblog.file.restapi;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.file.components.MinioUtils;

import java.util.List;

@RestController
@RequestMapping("${pplax.api.basePath}/fileTest")
public class TestController {
    @Autowired
    private MinioUtils minioUtils;


    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile[] files) throws Exception {

        for (MultipartFile multipartFile : files) {
            minioUtils.putObject("pplax-blog", multipartFile, "/files/" + multipartFile.getOriginalFilename());
        }

        return "";
    }
}
