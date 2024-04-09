package xyz.pplax.pplaxblog.feign.web;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="web-server")
public interface WebSiteFeignClient {

    @GetMapping(value = "/api/site/report", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String report();

}
