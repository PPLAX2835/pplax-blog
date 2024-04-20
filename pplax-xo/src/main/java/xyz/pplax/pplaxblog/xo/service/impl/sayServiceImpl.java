package xyz.pplax.pplaxblog.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.CommentSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.SaySQLConstants;
import xyz.pplax.pplaxblog.xo.dto.edit.SayEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.CommentGetListDto;
import xyz.pplax.pplaxblog.xo.dto.list.SayGetListDto;
import xyz.pplax.pplaxblog.xo.entity.*;
import xyz.pplax.pplaxblog.xo.mapper.SayMapper;
import xyz.pplax.pplaxblog.xo.service.CommentService;
import xyz.pplax.pplaxblog.xo.service.FileStorageService;
import xyz.pplax.pplaxblog.xo.service.SayService;
import xyz.pplax.pplaxblog.xo.service.UserService;
import xyz.pplax.pplaxblog.xo.service.UserInfoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 说说表 服务实现类
 */
@Service
public class sayServiceImpl extends SuperServiceImpl<SayMapper, Say> implements SayService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CommentService commentService;

    @Override
    public IPage<Say> list(SayGetListDto sayGetListDto) {
        QueryWrapper<Say> sayQueryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(sayGetListDto.getKeyword())) {
            // 如果关键词参数非空，就按该条件查询
            sayQueryWrapper.like(SaySQLConstants.CONTENT, "%" + sayGetListDto.getKeyword() + "%");
        }

        //分页
        Page<Say> page = new Page<>();
        page.setCurrent(sayGetListDto.getCurrentPage());
        page.setSize(sayGetListDto.getPageSize());

        // 获得非删除状态的
        sayQueryWrapper.ne(SaySQLConstants.C_STATUS, EStatus.DISABLED.getStatus());

        IPage<Say> sayPage = page(page, sayQueryWrapper);
        List<Say> sayList = new ArrayList<>();
        for (Say say : sayPage.getRecords()) {
            // 封装图片
            if (!StringUtils.isBlank(say.getImageUids())) {
                String[] imageUids = say.getImageUids().split(",");
                say.setImageList(fileStorageService.listByIds(Arrays.asList(imageUids)));
            }

            // 封装用户
            User user = userService.getById(say.getUserUid());
            if (user != null) {
                user.sensitiveDataRemove();
                user.setUserInfo(userInfoService.getByUserUid(user.getUid()));
                say.setUser(user);
            }

            // 封装评论列表
            CommentGetListDto commentGetListDto = new CommentGetListDto();
            commentGetListDto.setOriginalUid(say.getUid());
            commentGetListDto.setCurrentPage(1L);
            commentGetListDto.setPageSize(Long.MAX_VALUE);
            commentGetListDto.setType(CharacterConstants.NUM_TWO);
            IPage<Comment> commentIPage = commentService.list(commentGetListDto);
            say.setCommentList(commentIPage.getRecords());

            // 封装点赞列表
            commentGetListDto.setType(CharacterConstants.NUM_THREE);
            IPage<Comment> likeIPage = commentService.list(commentGetListDto);
            say.setLikeList(likeIPage.getRecords());

            // 判断自己是否已经点赞
            boolean isLike = false;
            if (!StringUtils.isBlank(sayGetListDto.getUserUid())) {
                QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
                commentQueryWrapper.ne(CommentSQLConstants.STATUS, EStatus.DISABLED.getStatus());
                commentQueryWrapper.eq(CommentSQLConstants.USER_UID, sayGetListDto.getUserUid());
                commentQueryWrapper.eq(CommentSQLConstants.TYPE, CharacterConstants.NUM_THREE);
                commentQueryWrapper.eq(CommentSQLConstants.ORIGINAL_UID, say.getUid());
                int count = commentService.count(commentQueryWrapper);
                if (count > 0) {
                    isLike = true;
                }
            }
            say.setIsLike(isLike);

            sayList.add(say);
        }

        sayPage.setRecords(sayList);

        return sayPage;
    }

    @Override
    public Boolean updateById(SayEditDto sayEditDto) {
        // 封装
        Say say = new Say();
        say.setUid(sayEditDto.getUid());
        say.setImageUids(sayEditDto.getImageUids());
        say.setIsPublic(sayEditDto.getIsPublic());
        say.setContent(sayEditDto.getContent());
        say.setAddress(sayEditDto.getAddress());

        return updateById(say);
    }

    @Override
    public Boolean save(SayEditDto sayEditDto) {
        // 封装
        Say say = new Say();
        say.setUid(StringUtils.getUUID());
        say.setUserUid(sayEditDto.getUserUid());
        say.setImageUids(sayEditDto.getImageUids());
        say.setIsPublic(sayEditDto.getIsPublic());
        say.setContent(sayEditDto.getContent());
        say.setAddress(sayEditDto.getAddress());

        return save(say);
    }


}
