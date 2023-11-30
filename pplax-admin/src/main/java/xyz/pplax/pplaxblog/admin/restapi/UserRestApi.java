package xyz.pplax.pplaxblog.admin.restapi;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.pplax.pplaxblog.admin.global.FeedBackSQLConf;
import xyz.pplax.pplaxblog.base.response.ResponseResult;
import xyz.pplax.pplaxblog.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.entity.Feedback;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 博客表 RestApi
 */
@RestController
@RequestMapping("/user")
@Api(value="用户RestApi", tags={"UserRestApi"})
public class UserRestApi {

    @Autowired
    private UserService userService;

    private static final Logger log = LogManager.getLogger(UserRestApi.class);

    /**
     * 获取用户列表，get方法   临时弄一个假的
     * @param request
     * @param keyword
     * @param currentPage
     * @param pageSize
     * @return
     */
    @ApiOperation(value="获取反馈列表", notes="获取反馈列表", response = String.class)
    @GetMapping(value = "")
    public String getList(HttpServletRequest request,
                          @ApiParam(name = "keyword", value = "关键字",required = false) @RequestParam(name = "keyword", required = false) String keyword,
                          @ApiParam(name = "currentPage", value = "当前页数",required = false) @RequestParam(name = "currentPage", required = false, defaultValue = "1") Long currentPage,
                          @ApiParam(name = "pageSize", value = "每页显示数目",required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize
    ) {


        return "{\"code\":200,\"data\":{\"current\":1,\"pages\":1,\"records\":[{\"birthday\":1537372800000,\"createTime\":1701329196000,\"gender\":1,\"lastLoginTime\":1537426140000,\"nickName\":\"asd\",\"pictureUid\":\"测试头像\",\"status\":1,\"summary\":\"测试表情\",\"uid\":\"7c5bb9f3ccd54fc6a9804955cf6488ad\",\"updateTime\":1701329204000,\"userName\":\"asd\"},{\"birthday\":1537372800000,\"createTime\":1701329196000,\"gender\":1,\"lastLoginTime\":1537426140000,\"nickName\":\"zcx\",\"pictureUid\":\"5\",\"status\":1,\"summary\":\"测试表情\",\"uid\":\"7a0233eb493345d0831b34d3a9c1b4e8\",\"updateTime\":1701329204000,\"userName\":\"zxc\"},{\"birthday\":1537372800000,\"createTime\":1701329196000,\"gender\":1,\"lastLoginTime\":1537426140000,\"nickName\":\"dsazxc\",\"pictureUid\":\"测试头像4\",\"status\":1,\"summary\":\"测试表情\",\"uid\":\"a0c2bb72c1934ab08455f564794d09c1\",\"updateTime\":1701329204000,\"userName\":\"lzicu\"},{\"birthday\":1537372800000,\"createTime\":1701329196000,\"gender\":1,\"lastLoginTime\":1537426140000,\"nickName\":\"三哥\",\"pictureUid\":\"2\",\"status\":1,\"summary\":\"测试表情\",\"uid\":\"94437dc0211745449649b283840ab2a5\",\"updateTime\":1701329204000,\"userName\":\"zhangsan\"}],\"size\":10,\"total\":4}}";
    }



}
