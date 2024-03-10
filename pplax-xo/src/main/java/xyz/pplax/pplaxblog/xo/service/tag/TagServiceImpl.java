package xyz.pplax.pplaxblog.xo.service.tag;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.xo.constants.sql.BlogSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.BlogSortSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.TagSQLConstants;
import xyz.pplax.pplaxblog.xo.dto.list.BlogSortGetListDto;
import xyz.pplax.pplaxblog.xo.dto.list.TagGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.BlogSort;
import xyz.pplax.pplaxblog.xo.entity.Tag;
import xyz.pplax.pplaxblog.xo.mapper.TagMapper;
import xyz.pplax.pplaxblog.xo.service.blog.BlogService;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签表 服务实现类
 */
@Service
public class TagServiceImpl extends SuperServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogService blogService;

    @Override
    public List<Tag> list(TagGetListDto tagGetListDto) {
        QueryWrapper<Tag> tagQueryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(tagGetListDto.getKeyword())) {
            // 如果关键词参数非空，就按该条件查询
            tagQueryWrapper.like(TagSQLConstants.NAME, "%" + tagGetListDto.getKeyword() + "%");
        }

        //分页
        Page<Tag> page = new Page<>();
        page.setCurrent(tagGetListDto.getCurrentPage());
        page.setSize(tagGetListDto.getPageSize());

        // 获得非删除状态的
        tagQueryWrapper.ne(TagSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());

        IPage<Tag> pageList = null;
        // 排序
        if (tagGetListDto.getSortByClickCount() != null && tagGetListDto.getSortByClickCount()) {
            // 按点击量排序
            tagQueryWrapper.orderByAsc(TagSQLConstants.CLICK_COUNT);
            // 查询
            pageList = tagMapper.selectPage(page, tagQueryWrapper);
        } else if (tagGetListDto.getSortByCites() != null && tagGetListDto.getSortByCites()) {
            // 按引用量排序
            tagQueryWrapper.and(
                    i -> i.ne(BlogSQLConstants.C_STATUS, EStatus.DISABLED.getStatus())
                            .or().isNull(BlogSQLConstants.C_STATUS)
            );
            // 查询
            pageList = tagMapper.selectListSortByCites(page, tagQueryWrapper);
        } else {
            // 按创建时间排序
            tagQueryWrapper.orderByDesc(TagSQLConstants.C_CREATE_TIME);
            // 查询
            pageList = tagMapper.selectPage(page, tagQueryWrapper);
        }

        List<Tag> tagList = new ArrayList<>();
        // 获得引用量
        for (Tag tag : pageList.getRecords()) {
            QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
            blogQueryWrapper.like(BlogSQLConstants.TAG_UIDS, "%" + tag.getUid() + "%");
            blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
            tag.setCites(blogService.count(blogQueryWrapper));

            tagList.add(tag);
        }

        return tagList;
    }

    @Override
    public Long count(TagGetListDto tagGetListDto) {
        QueryWrapper<Tag> tagQueryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(tagGetListDto.getKeyword())) {
            // 如果关键词参数非空，就按该条件查询
            tagQueryWrapper.like(TagSQLConstants.NAME, "%" + tagGetListDto.getKeyword() + "%");
        }
        // 获得非删除状态的
        tagQueryWrapper.ne(TagSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
        return (long) count(tagQueryWrapper);
    }
}
