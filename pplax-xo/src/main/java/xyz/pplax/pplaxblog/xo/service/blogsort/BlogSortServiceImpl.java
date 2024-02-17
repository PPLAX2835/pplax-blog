package xyz.pplax.pplaxblog.xo.service.blogsort;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.dto.BlogSortDto;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.BlogSort;
import xyz.pplax.pplaxblog.xo.constants.sql.BlogSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.BlogSortSQLConstants;
import xyz.pplax.pplaxblog.xo.mapper.BlogMapper;
import xyz.pplax.pplaxblog.xo.mapper.BlogSortMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 博客分类表 服务实现类
 */
@Service
public class BlogSortServiceImpl extends SuperServiceImpl<BlogSortMapper, BlogSort> implements BlogSortService {

    @Autowired
    private BlogSortMapper blogSortMapper;

    @Autowired
    private BlogMapper blogMapper;

    /**
     * 获取博客分类列表
     *
     * @param blogSortDto
     * @return
     */
    @Override
    public IPage<BlogSort> getPageList(BlogSortDto blogSortDto) {
        QueryWrapper<BlogSort> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(blogSortDto.getKeyword())) {
            queryWrapper.like(BlogSortSQLConstants.SORT_NAME, blogSortDto.getKeyword());
        }

        //分页
        Page<BlogSort> page = new Page<>();
        page.setCurrent(blogSortDto.getCurrentPage());
        page.setSize(blogSortDto.getPageSize());

        // 获得非删除状态的
        queryWrapper.ne(BlogSortSQLConstants.STATUS, EStatus.DISABLED);

        IPage<BlogSort> pageList = null;

        // 排序
        if (blogSortDto.getSortByClickCount()) {
            // 按点击量排序
            queryWrapper.orderByDesc(BlogSortSQLConstants.CLICK_COUNT);
            // 查询
            pageList = blogSortMapper.selectPage(page, queryWrapper);
        } else if (blogSortDto.getSortByCites()) {
            // 按引用量排序
            queryWrapper.and(
                    i -> i.ne(BlogSQLConstants.STATUS, EStatus.DISABLED)
                            .or().isNull(BlogSQLConstants.STATUS)
            );
            // 查询
            pageList = blogSortMapper.selectListOrderByCites(page, queryWrapper);
        } else {
            // 按创建时间排序
            queryWrapper.orderByDesc(BlogSortSQLConstants.CREATE_TIME);
            // 查询
            pageList = blogSortMapper.selectPage(page, queryWrapper);
        }

        // 获得列表
        List<BlogSort> blogSortListList = pageList.getRecords();

        for(BlogSort blogSort : blogSortListList) {
            // 添加父级分类
            if(blogSort != null && !StringUtils.isEmpty(blogSort.getParentUid())) {
                String parentUid = blogSort.getParentUid();
                BlogSort parentBlogSort = blogSortMapper.selectById(parentUid);
                if(parentBlogSort != null) {
                    blogSort.setParentBlogSort(parentBlogSort);
                }
            }
        }

        pageList.setRecords(blogSortListList);

        return pageList;
    }

    /**
     * 检查当前分类是否存在
     * @param blogSortDto
     * @return
     */
    @Override
    public Boolean checkSortNameExists(BlogSortDto blogSortDto) {
        QueryWrapper<BlogSort> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BlogSortSQLConstants.SORT_NAME, blogSortDto.getSortName());
        BlogSort tempSort = blogSortMapper.selectOne(queryWrapper);

        return tempSort != null;
    }

    /**
     * 新增博客分类
     *
     * @param blogSortDto
     */
    @Override
    public ResponseResult addBlogSort(BlogSortDto blogSortDto) {
        // 检查参数
        if(StringUtils.isEmpty(blogSortDto.getSortName()) || blogSortDto.getStatus() == null) {
            return ResponseResult.error(HttpStatus.REQUIRED_IS_NULL);
        }

        // 检查是否存在
        Boolean isExist = checkSortNameExists(blogSortDto);
        if (isExist) {
            return ResponseResult.error(HttpStatus.ENTITY_EXIST);
        }

        // 封装
        BlogSort blogSort = new BlogSort();
        blogSort.setSortName(blogSortDto.getSortName());
        blogSort.setSummary(blogSortDto.getSummary());
        blogSort.setContent(blogSortDto.getContent());
        blogSort.setStatus(blogSortDto.getStatus());
        blogSort.setParentUid(blogSortDto.getParentBlogSortUid());

        blogSortMapper.insert(blogSort);
        return ResponseResult.success(HttpStatus.INSERT_SUCCESS.getMessage());
    }


    /**
     * 编辑博客分类
     *
     * @param blogSortDto
     */
    public ResponseResult editBlogSort(BlogSortDto blogSortDto){
        // 检查参数
        if(StringUtils.isEmpty(blogSortDto.getSortName()) || blogSortDto.getStatus() == null) {
            return ResponseResult.error(HttpStatus.REQUIRED_IS_NULL);
        }

        // 检查是否存在
        BlogSort blogSort = blogSortMapper.selectById(blogSortDto.getUid());

        if (!blogSort.getSortName().equals(blogSortDto.getSortName())) {
            // 名字被改了，检查有没有重的
            QueryWrapper<BlogSort> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(BlogSortSQLConstants.SORT_NAME, blogSortDto.getSortName());
            BlogSort tempSort = blogSortMapper.selectOne(queryWrapper);
            if (tempSort != null) {
                // 确实重了
                return ResponseResult.error(HttpStatus.ENTITY_EXIST);
            }
        }

        // 封装
        blogSort.setUid(blogSortDto.getUid());
        blogSort.setSortName(blogSortDto.getSortName());
        blogSort.setSummary(blogSortDto.getSummary());
        blogSort.setContent(blogSortDto.getContent());
        blogSort.setStatus(blogSortDto.getStatus());
        blogSort.setParentUid(blogSortDto.getParentBlogSortUid());

        blogSortMapper.updateById(blogSort);
        return ResponseResult.success(HttpStatus.UPDATE_SUCCESS);
    }

    /**
     * 逻辑删除
     * @param uid
     * @return
     */
    public ResponseResult logicDelete(String uid){
        List<String> uids = new ArrayList<>();
        uids.add(uid);
        BlogSortDto blogSortDto = new BlogSortDto();

        blogSortDto.setUids(uids);

        return logicBatchDelete(blogSortDto);
    }

    /**
     * 批量逻辑删除
     * @param blogSortDto
     * @return
     */
    @Override
    public ResponseResult logicBatchDelete(BlogSortDto blogSortDto) {
        if (blogSortDto.getUids() == null || blogSortDto.getUids().isEmpty()) {
            return ResponseResult.error(HttpStatus.PLEASE_SELECT_A_RECORD_TO_DELETE);
        }

        List<String> uids = new ArrayList<>(blogSortDto.getUids());

        // 判断要删除的分类，是否有博客
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.ne(BlogSortSQLConstants.STATUS, EStatus.DISABLED);
        blogQueryWrapper.in(BlogSQLConstants.BLOG_SORT_UID, uids);
        if (blogMapper.selectCount(blogQueryWrapper) > 0) {
            return ResponseResult.error(HttpStatus.BLOG_UNDER_THIS_SORT);
        }

        // 将该分类的子分类的parentUid置空
        UpdateWrapper<BlogSort> blogSortUpdateWrapper = new UpdateWrapper<>();
        blogSortUpdateWrapper.in(BlogSortSQLConstants.PARENT_UID, uids);
        blogSortUpdateWrapper.set(BlogSortSQLConstants.PARENT_UID, null);
        blogSortMapper.update(new BlogSort(), blogSortUpdateWrapper);

        // 逻辑删除代码
        Collection<BlogSort> blogSorts = blogSortMapper.selectBatchIds(blogSortDto.getUids());
        for (BlogSort blogSort : blogSorts) {
            blogSort.setStatus(EStatus.DISABLED.getStatus());
            blogSortMapper.updateById(blogSort);
        }

        return ResponseResult.success(HttpStatus.DELETE_SUCCESS);
    }
}
