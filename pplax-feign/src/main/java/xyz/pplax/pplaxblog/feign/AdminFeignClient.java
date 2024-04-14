package xyz.pplax.pplaxblog.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="admin-server")
public interface AdminFeignClient {

    @GetMapping(value = "/message/list")
    String getList(
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "currentPage") Long currentPage,
            @RequestParam(value = "pageSize") Long pageSize
    );

    @GetMapping(value = "/user/{userUid}/userInfo")
    String getUserInfo (@PathVariable("userUid") String userUid);

    @DeleteMapping(value = "/chatRoom/{chatRoomUid}")
    String deleteChatRoom(@PathVariable("chatRoomUid") String chatRoomUid);

    @DeleteMapping(value = "/blog/{blogUid}")
    String deleteBlog(@PathVariable("blogUid") String blogUid);

}
