package xyz.pplax.pplaxblog.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="admin-server")
public interface AdminFeignClient {

    @GetMapping(value = "/leaveMessage/list")
    String getLeaveMessageList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    );

    @GetMapping(value = "/user/{userUid}/userInfo")
    String getUserInfo (@PathVariable("userUid") String userUid);

}
