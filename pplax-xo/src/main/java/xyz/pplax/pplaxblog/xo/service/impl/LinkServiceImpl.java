package xyz.pplax.pplaxblog.xo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.constants.sql.LinkSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.edit.LinkEditDto;
import xyz.pplax.pplaxblog.xo.entity.FileStorage;
import xyz.pplax.pplaxblog.xo.entity.Link;
import xyz.pplax.pplaxblog.xo.mapper.LinkMapper;
import xyz.pplax.pplaxblog.xo.service.FileStorageService;
import xyz.pplax.pplaxblog.xo.service.LinkService;

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
    public Page<Link> page(String keyword, Integer status, Long currentPage, Long pageSize) {
        PQueryWrapper<Link> linkPQueryWrapper = new PQueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            // 如果关键词参数非空，就按该条件查询
            linkPQueryWrapper.like(LinkSQLConstants.TITLE, "%" + keyword + "%");
        }
        if (status != null) {
            linkPQueryWrapper.eq(LinkSQLConstants.C_STATUS, status);
        }

        //分页
        Page<Link> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        Page<Link> pageList = page(page, linkPQueryWrapper);

        List<Link> linkList = new ArrayList<>();

        // 封装图标
        for (Link link : pageList.getRecords()) {
            FileStorage fileStorage = fileStorageService.getById(link.getIconImageUid());

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

    /**
     * 申请友链
     * @param linkEditDto
     * @return
     */
    @Override
    public Boolean apply(LinkEditDto linkEditDto) {
        Link link = new Link();
        link.setTitle(linkEditDto.getTitle());
        link.setSummary(linkEditDto.getSummary());
        link.setUrl(linkEditDto.getUrl());
        link.setIconImageUid(linkEditDto.getIconImageUid());
        link.setStatus(EStatus.APPLYING.getStatus());

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
