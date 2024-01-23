package xyz.pplax.pplaxblog.file.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.file.components.MinioUtil;

import java.util.List;

@RestController
@RequestMapping("/api/123")
public class TestController {
    @Autowired
    private MinioUtil minioUtil;

    @PostMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile[] files) {

        List<String> upload = minioUtil.upload(files);

        return upload.get(0);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
