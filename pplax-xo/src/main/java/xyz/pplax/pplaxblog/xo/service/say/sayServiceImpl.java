package xyz.pplax.pplaxblog.xo.service.say;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.SaySQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.TagSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.edit.SayEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.SayGetListDto;
import xyz.pplax.pplaxblog.xo.entity.FileStorage;
import xyz.pplax.pplaxblog.xo.entity.Say;
import xyz.pplax.pplaxblog.xo.entity.Tag;
import xyz.pplax.pplaxblog.xo.entity.User;
import xyz.pplax.pplaxblog.xo.mapper.SayMapper;
import xyz.pplax.pplaxblog.xo.service.filestorage.FileStorageService;
import xyz.pplax.pplaxblog.xo.service.user.UserService;
import xyz.pplax.pplaxblog.xo.service.userinfo.UserInfoService;

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
