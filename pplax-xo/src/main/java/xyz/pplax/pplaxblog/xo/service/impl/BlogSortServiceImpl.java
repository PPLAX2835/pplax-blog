package xyz.pplax.pplaxblog.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.exception.curd.DeleteException;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.base.wrapper.PQueryWrapper;
import xyz.pplax.pplaxblog.xo.dto.edit.BlogSortEditDto;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.BlogSort;
import xyz.pplax.pplaxblog.xo.constants.sql.BlogSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.BlogSortSQLConstants;
import xyz.pplax.pplaxblog.xo.mapper.BlogSortMapper;
import xyz.pplax.pplaxblog.xo.service.BlogService;
import xyz.pplax.pplaxblog.xo.service.BlogSortService;

import java.util.List;
import java.util.Objects;

/**
 * 博客分类表 服务实现类
 */
@Service
public class BlogSortServiceImpl extends SuperServiceImpl<BlogSortMapper, BlogSort> implements BlogSortService {

    @Autowired
    private BlogSortMapper blogSortMapper;

    @Autowired
    private BlogService blogService;

    /**
     * 获取博客分类列表
     * @param keyword
     * @param sortByClickCount
     * @param sortByCites
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<BlogSort> page(String keyword, Boolean sortByClickCount, Boolean sortByCites, Long currentPage, Long pageSize) {
        QueryWrapper<BlogSort> blogSortQueryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(keyword)) {
            // 如果关键词参数非空，就按该条件查询
            blogSortQueryWrapper.like(BlogSortSQLConstants.SORT_NAME, "%" + keyword + "%");
        }

        //分页
        Page<BlogSort> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        // 获得非删除状态的
        blogSortQueryWrapper.ne(BlogSortSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());

        Page<BlogSort> pageList = null;

        // 排序
        if (sortByClickCount != null && sortByClickCount) {
            // 按点击量排序
            blogSortQueryWrapper.orderByAsc(BlogSortSQLConstants.CLICK_COUNT);
            // 查询
            pageList = blogSortMapper.selectPage(page, blogSortQueryWrapper);
        } else if (sortByCites != null && sortByCites) {
            // 按引用量排序
            blogSortQueryWrapper.and(
                    i -> i.ne(BlogSQLConstants.C_STATUS, EStatus.DISABLED.getStatus())
                            .or().isNull(BlogSQLConstants.C_STATUS)
            );
            // 查询
            pageList = blogSortMapper.selectPageSortByCites(page, blogSortQueryWrapper);
        } else {
            // 按创建时间排序
            blogSortQueryWrapper.orderByDesc(BlogSortSQLConstants.C_CREATE_TIME);
            // 查询
            pageList = blogSortMapper.selectPage(page, blogSortQueryWrapper);
        }

        // 获得引用量
        for (BlogSort blogSort : pageList.getRecords()) {
            QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
            blogQueryWrapper.eq(BlogSQLConstants.BLOG_SORT_UID, blogSort.getUid());
            blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DISABLED.getStatus());
            blogSort.setCites(blogService.count(blogQueryWrapper));
        }

        // 返回
        return pageList;
    }

    @Override
    public List<BlogSort> list() {
        PQueryWrapper<BlogSort> blogSortPQueryWrapper = new PQueryWrapper<>();

        List<BlogSort> blogSortList = list(blogSortPQueryWrapper);
        for (BlogSort blogSort : blogSortList) {
            // 封装文章数
            PQueryWrapper<Blog> blogPQueryWrapper = new PQueryWrapper<>();

            blogPQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.OFF_SHELF.getStatus());          // 排除非正常状态
            blogPQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.PENDING_APPROVAL.getStatus());
            blogPQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DRAFT.getStatus());

            blogPQueryWrapper.eq(BlogSQLConstants.BLOG_SORT_UID, blogSort.getUid());

            blogSort.setCites(blogService.count(blogPQueryWrapper));
        }

        return blogSortList;
    }

    /**
     * 对分类进行置顶
     * @param blogSortUid
     * @return
     */
    @Override
    public Boolean promote(String blogSortUid) {
        BlogSort blogSort = new BlogSort();
        blogSort.setUid(blogSortUid);
        blogSort.setSortNo(CharacterConstants.NUM_ZERO);
        return updateById(blogSort);
    }

    /**
     * 取消置顶分类
     * @param blogSortUid
     * @return
     */
    @Override
    public Boolean promoteCancel(String blogSortUid) {
        // 先获得排序了的分类列表
        PQueryWrapper<BlogSort> blogSortPQueryWrapper = new PQueryWrapper<>();
        blogSortPQueryWrapper.orderByAsc(BlogSortSQLConstants.SORT_NO);
        List<BlogSort> blogSortList = list(blogSortPQueryWrapper);

        int index = 0;
        int gapSortNo = 1;
        // 过滤掉已经置顶的
        while (index < blogSortList.size() && blogSortList.get(index).getSortNo() == 0) {
            index++;
        }
        // 找出未置顶的分类中的空隙
        while (index < blogSortList.size() && gapSortNo == blogSortList.get(index).getSortNo()) {
            index++;
            gapSortNo++;
        }

        BlogSort blogSort = new BlogSort();
        blogSort.setUid(blogSortUid);
        blogSort.setSortNo(gapSortNo);

        return updateById(blogSort);
    }

    /**
     * 新增博客分类
     *
     * @param blogSortEditDto
     */
    @Override
    public Boolean save(BlogSortEditDto blogSortEditDto) {

        BlogSort blogSort = new BlogSort();

        // 封装
        blogSort.setUid(StringUtils.getUUID());             // 生成uuid
        blogSort.setSortName(blogSortEditDto.getSortName());
        blogSort.setContent(blogSortEditDto.getContent());
        blogSort.setIcon(blogSortEditDto.getIcon());
        blogSort.setSortNo(blogSortEditDto.getSortNo());
        blogSort.setStatus(blogSortEditDto.getStatus());

        return save(blogSort);
    }


    /**
     * 编辑博客分类
     *
     * @param blogSortEditDto
     */
    public Boolean updateById(BlogSortEditDto blogSortEditDto){

        BlogSort blogSort = new BlogSort();

        // 封装
        blogSort.setUid(blogSortEditDto.getUid());
        blogSort.setSortName(blogSortEditDto.getSortName());
        blogSort.setContent(blogSortEditDto.getContent());
        blogSort.setIcon(blogSortEditDto.getIcon());
        blogSort.setSortNo(blogSortEditDto.getSortNo());
        blogSort.setStatus(blogSortEditDto.getStatus());

        return updateById(blogSort);
    }

    /**
     * 通过id删除该分类
     * @param blogSortUid
     * @return
     */
    @Override
    public Boolean removeById(String blogSortUid) {
        // 先查询该分类下有没有文章
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.eq(BlogSQLConstants.BLOG_SORT_UID, blogSortUid);
        blogQueryWrapper.ne(BlogSQLConstants.C_STATUS, EStatus.DISABLED);
        int count = blogService.count(blogQueryWrapper);

        if (count > 0) {
            // 存在非删除状态的文章，返回错误信息
            throw new DeleteException(HttpStatus.BLOG_UNDER_THIS_SORT);
        }

        boolean res = super.removeById(blogSortUid);
        if (!res) {
            throw new DeleteException();
        }

        return true;
    }

    /**
     * 通过id列表删除
     * @param blogSortUidList
     * @return
     */
    @Override
    @Transactional
    public Boolean removeByIds(List<String> blogSortUidList) {
        List<BlogSort> blogSortList = listByIds(blogSortUidList);

        for (BlogSort blogSort : blogSortList) {
            // 批量删除出问题就回滚
            Boolean res = removeById(blogSort.getUid());
            if (!res) {
                throw new DeleteException();
            }
        }

        return true;
    }

}
