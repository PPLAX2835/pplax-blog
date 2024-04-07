package xyz.pplax.pplaxblog.feign.sitesetting;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="admin-server")
public interface SiteSettingFeignClient {

    @GetMapping(value = "/api/siteSetting/list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String getSiteSettingList();

}
