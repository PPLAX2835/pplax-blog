package xyz.pplax.pplaxblog.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.pplax.pplaxblog.file.components.MinioUtils;

@RestController
@RequestMapping("/fileTest")
public class TestController {
    @Autowired
    private MinioUtils minioUtils;


    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile[] files) throws Exception {

        for (MultipartFile multipartFile : files) {

            System.out.println(multipartFile.getOriginalFilename());

            minioUtils.putObject("pplax-blog", multipartFile, "/files/" + multipartFile.getOriginalFilename());
        }

        return "123";
    }
}
