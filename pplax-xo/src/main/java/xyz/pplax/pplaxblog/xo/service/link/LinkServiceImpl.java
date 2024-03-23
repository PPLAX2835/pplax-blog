package xyz.pplax.pplaxblog.xo.service.link;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.FileStorageSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.LinkSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.TagSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.edit.LinkEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.LinkGetListDto;
import xyz.pplax.pplaxblog.xo.entity.FileStorage;
import xyz.pplax.pplaxblog.xo.entity.Link;
import xyz.pplax.pplaxblog.xo.entity.Tag;
import xyz.pplax.pplaxblog.xo.mapper.LinkMapper;
import xyz.pplax.pplaxblog.xo.service.filestorage.FileStorageService;

import java.util.ArrayList;
import java.util.List;

/**
 * 友情链接表 服务实现类
 */
@Service
public class LinkServiceImpl extends SuperServiceImpl<LinkMapper, Link> implements LinkService {

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public IPage<Link> list(LinkGetListDto linkGetListDto) {
        QueryWrapper<Link> linkQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(linkGetListDto.getKeyword())) {
            // 如果关键词参数非空，就按该条件查询
            linkQueryWrapper.like(LinkSQLConstants.TITLE, "%" + linkGetListDto.getKeyword() + "%");
        }
        if (linkGetListDto.getStatus() != null) {
            linkQueryWrapper.eq(LinkSQLConstants.C_STATUS, linkGetListDto.getStatus());
        }

        //分页
        Page<Link> page = new Page<>();
        page.setCurrent(linkGetListDto.getCurrentPage());
        page.setSize(linkGetListDto.getPageSize());

        // 获得非删除状态的
        linkQueryWrapper.ne(LinkSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());

        IPage<Link> pageList = page(page, linkQueryWrapper);

        List<Link> linkList = new ArrayList<>();

        // 封装图标
        for (Link link : pageList.getRecords()) {
            QueryWrapper<FileStorage> fileStorageQueryWrapper = new QueryWrapper<>();
            fileStorageQueryWrapper.eq(FileStorageSQLConstants.C_UID, link.getIconImageUid());
            fileStorageQueryWrapper.ne(FileStorageSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
            FileStorage fileStorage = fileStorageService.getOne(fileStorageQueryWrapper);

            link.setIconImage(fileStorage);
            linkList.add(link);
        }

        pageList.setRecords(linkList);

        return pageList;
    }

    @Override
    public Boolean save(LinkEditDto linkEditDto) {
        Link link = new Link();
        link.setTitle(linkEditDto.getTitle());
        link.setSummary(linkEditDto.getSummary());
        link.setUrl(linkEditDto.getUrl());
        link.setIconImageUid(linkEditDto.getIconImageUid());
        link.setStatus(linkEditDto.getStatus());

        return save(link);
    }

    @Override
    public Boolean updateById(LinkEditDto linkEditDto) {

        Link link = new Link();
        link.setUid(linkEditDto.getUid());
        link.setTitle(linkEditDto.getTitle());
        link.setSummary(linkEditDto.getSummary());
        link.setUrl(linkEditDto.getUrl());
        link.setIconImageUid(linkEditDto.getIconImageUid());
        link.setStatus(linkEditDto.getStatus());

        return updateById(link);
    }

}
