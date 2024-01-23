package xyz.pplax.pplaxblog.admin.restapi;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.admin.dto.add.FeedBackAddDto;
import xyz.pplax.pplaxblog.admin.dto.edit.FeedBackEditDto;
import xyz.pplax.pplaxblog.xo.global.FeedBackSQLConf;
import xyz.pplax.pplaxblog.commons.base.global.response.ResponseCode;
import xyz.pplax.pplaxblog.commons.base.global.response.ResponseResult;
import xyz.pplax.pplaxblog.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.entity.Feedback;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.service.FeedbackService;
import xyz.pplax.pplaxblog.xo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 反馈表 RestApi
 */
@RestController
@RequestMapping("${pplax.api.basePath}/feedback")
public class FeedbackRestApi {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserService userService;

    private static final Logger log = LogManager.getLogger(FeedbackRestApi.class);

    /**
     * 获取反馈列表，get方法
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

        QueryWrapper<Feedback> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(keyword)) {
            queryWrapper.like(FeedBackSQLConf.CONTENT, keyword);
        }

        //分页
        Page<Feedback> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        // 按创建时间排序
//        queryWrapper.orderByDesc(FeedBackSQLConf.CREATE_TIME);

        // 查询
        IPage<Feedback> pageList = feedbackService.page(page, queryWrapper);
        List<Feedback> feedbackList = pageList.getRecords();

        for(Feedback feedback : feedbackList) {
            // 添加父级分类
            if(feedback != null && !StringUtils.isEmpty(feedback.getUserUid())) {
                User user = userService.getById(feedback.getUserUid());
                if(user != null) {
                    user.sensitiveDataRemove();
                    feedback.setUser(user);
                }
            }
        }

        log.info("返回结果");
        pageList.setRecords(feedbackList);

        return JSON.toJSONString(ResponseResult.success(pageList));
    }

    /**
     * 新增反馈，post方法
     * @param request
     * @param feedBackAddDto
     * @return
     */
    @ApiOperation(value="增加反馈", notes="增加反馈", response = String.class)
    @PostMapping("")
    public String add(
            HttpServletRequest request,
            @RequestBody FeedBackAddDto feedBackAddDto
    ) {

        if(StringUtils.isEmpty(feedBackAddDto.getUserUid()) || StringUtils.isEmpty(feedBackAddDto.getContent()) || feedBackAddDto.getStatus() == null) {
            return JSON.toJSONString(ResponseResult.error(ResponseCode.ERROR, "必填项不能为空"));
        }

        Feedback feedback = new Feedback();
        feedback.setUserUid(feedBackAddDto.getUserUid());
        feedback.setContent(feedBackAddDto.getContent());
        feedback.setStatus(feedBackAddDto.getStatus());

        feedbackService.save(feedback);
        return JSON.toJSONString(ResponseResult.success("添加成功"));
    }

    /**
     * 编辑反馈，put方法
     * @param request
     * @param feedBackEditDto
     * @return
     */
    @ApiOperation(value="编辑反馈", notes="编辑反馈", response = String.class)
    @PutMapping("/{uid}")
    public String edit(
            HttpServletRequest request,
            @ApiParam(name = "uid", value = "唯一标识符",required = true) @PathVariable String uid,
            @RequestBody FeedBackEditDto feedBackEditDto
            ) {

        if(StringUtils.isEmpty(uid)) {
            return JSON.toJSONString(ResponseResult.error(ResponseCode.ERROR, "数据错误"));
        }

        if(StringUtils.isEmpty(feedBackEditDto.getUserUid()) || StringUtils.isEmpty(feedBackEditDto.getContent()) || feedBackEditDto.getStatus() == null) {
            return JSON.toJSONString(ResponseResult.error(ResponseCode.ERROR, "必填项不能为空"));
        }

        Feedback feedback = new Feedback();
        feedback.setUid(uid);
        feedback.setUserUid(feedBackEditDto.getUserUid());
        feedback.setContent(feedBackEditDto.getContent());
        feedback.setStatus(feedBackEditDto.getStatus());

        feedbackService.updateById(feedback);
        return JSON.toJSONString(ResponseResult.success("修改成功"));
    }

    /**
     * 物理删除反馈 delete方法
     * @param request
     * @param uid
     * @return
     */
    @ApiOperation(value="物理删除反馈", notes="物理删除反馈", response = String.class)
    @DeleteMapping("/{uid}")
    public String delete(
            HttpServletRequest request,
            @ApiParam(name = "uid", value = "唯一标识符",required = true) @PathVariable String uid
    ) {

        if(StringUtils.isEmpty(uid)) {
            return JSON.toJSONString(ResponseResult.error(ResponseCode.ERROR, "数据错误"));
        }

        feedbackService.removeById(uid);

        return JSON.toJSONString(ResponseResult.success("删除成功"));
    }

}

