package xyz.pplax.pplaxblog.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.exception.DeleteFailException;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.constants.sql.BlogSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.TagSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.edit.TagEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.TagGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.Tag;
import xyz.pplax.pplaxblog.xo.mapper.TagMapper;
import xyz.pplax.pplaxblog.xo.service.BlogService;
import xyz.pplax.pplaxblog.xo.service.TagService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 标签表 服务实现类
 */
@Service
public class TagServiceImpl extends SuperServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogService blogService;

    /**
     * 获得标签列表
     * @param tagGetListDto
     * @return
     */
    @Override
    public IPage<Tag> list(TagGetListDto tagGetListDto) {
        PQueryWrapper<Tag> tagPQueryWrapper = new PQueryWrapper<>();
        if(!StringUtils.isEmpty(tagGetListDto.getKeyword())) {
            // 如果关键词参数非空，就按该条件查询
            tagPQueryWrapper.like(TagSQLConstants.NAME, "%" + tagGetListDto.getKeyword() + "%");
        }

        //分页
        Page<Tag> page = new Page<>();
        page.setCurrent(tagGetListDto.getCurrentPage());
        page.setSize(tagGetListDto.getPageSize());

        IPage<Tag> pageList = null;
        // 排序
        if (tagGetListDto.getSortByClickCount() != null && tagGetListDto.getSortByClickCount()) {
            // 按点击量排序
            tagPQueryWrapper.orderByAsc(TagSQLConstants.CLICK_COUNT);
            // 查询
            pageList = page(page, tagPQueryWrapper);
        } else if (tagGetListDto.getSortByCites() != null && tagGetListDto.getSortByCites()) {
            // 按引用量排序
            tagPQueryWrapper.and(
                    i -> i.ne(BlogSQLConstants.C_STATUS, EStatus.DISABLED.getStatus())
                            .or().isNull(BlogSQLConstants.C_STATUS)
            );
            // 查询
            pageList = tagMapper.selectListSortByCites(page, tagPQueryWrapper);
        } else {
            // 按创建时间排序
            tagPQueryWrapper.orderByDesc(TagSQLConstants.C_CREATE_TIME);
            // 查询
            pageList = page(page, tagPQueryWrapper);
        }

        List<Tag> tagList = new ArrayList<>();
        // 获得引用量
        for (Tag tag : pageList.getRecords()) {
            PQueryWrapper<Blog> blogPQueryWrapper = new PQueryWrapper<>();
            blogPQueryWrapper.like(BlogSQLConstants.TAG_UIDS, "%" + tag.getUid() + "%");

            blogPQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.OFF_SHELF.getStatus());          // 排除非正常状态
            blogPQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.PENDING_APPROVAL.getStatus());
            blogPQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DRAFT.getStatus());

            tag.setCites(blogService.count(blogPQueryWrapper));

            tagList.add(tag);
        }
        pageList.setRecords(tagList);

        // 返回
        return pageList;
    }

    /**
     * 添加标签
     * @param tagEditDto
     * @return
     */
    @Override
    public Boolean save(TagEditDto tagEditDto) {
        Tag tag = new Tag();

        // 封装
        tag.setUid(StringUtils.getUUID());             // 生成uuid
        tag.setName(tagEditDto.getName());
        tag.setLevel(tag.getLevel());

        return save(tag);
    }

    /**
     * 通过id更新标签
     * @param tagEditDto
     * @return
     */
    @Override
    public Boolean updateById(TagEditDto tagEditDto) {
        Tag tag = new Tag();

        // 封装
        tag.setUid(tagEditDto.getUid());             // 生成uuid
        tag.setName(tagEditDto.getName());
        tag.setLevel(tag.getLevel());

        return updateById(tag);
    }

    @Override
    public Integer count(String name) {
        PQueryWrapper<Tag> blogPQueryWrapper = new PQueryWrapper<>();
        blogPQueryWrapper.eq(TagSQLConstants.NAME, name);

        return count(blogPQueryWrapper);
    }

    /**
     * 通过id删除
     * @param tagUid
     * @return
     */
    @Override
    public ResponseResult removeById(String tagUid) {
        PQueryWrapper<Blog> blogPQueryWrapper = new PQueryWrapper<>();
        blogPQueryWrapper.like(BlogSQLConstants.TAG_UIDS, "%" + tagUid + "%");
        int count = blogService.count(blogPQueryWrapper);

        if (count > 0) {
            return new ResponseResult(HttpStatus.BLOG_UNDER_THIS_TAG);
        }

        boolean res = super.removeById(tagUid);
        if (!res) {
            throw new RuntimeException();
        }

        return ResponseResult.success(HttpStatus.DELETE_SUCCESS);
    }

    /**
     * 通过id列表批量删除
     * @param tagUidList
     * @return
     */
    @Override
    @Transactional
    public ResponseResult removeByIds(List<String> tagUidList) {
        List<Tag> tagList = listByIds(tagUidList);

        for (Tag tag : tagList) {
            // 批量删除出问题就回滚
            ResponseResult responseResult = removeById(tag.getUid());
            if (!Objects.equals(responseResult.getCode(), HttpStatus.OK.getCode())) {
                throw new DeleteFailException(responseResult.getMessage());
            }
        }

        return ResponseResult.success(HttpStatus.DELETE_SUCCESS);
    }

    @Override
    public Boolean isTagNameExist(String name) {
        return count(name) > 0;
    }
}
