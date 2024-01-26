package xyz.pplax.pplaxblog.file.restapi;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.file.components.MinioUtil;

import java.util.List;

@RestController
@RequestMapping("${pplax.api.basePath}/fileTest")
public class TestController {
    @Autowired
    private MinioUtil minioUtil;

    @PostMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile[] files) {

        List<String> upload = minioUtil.upload("/111/222/abc", files);

        return upload.get(0);
    }

    @GetMapping("/test")
    public String test() {

        return JSON.toJSONString(minioUtil.listObjects("pplax-blog"));
    }
}
