package xyz.pplax.pplaxblog.xo.service.blogsort;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pplax.pplaxblog.commons.constants.BaseSysConstants;
import xyz.pplax.pplaxblog.commons.constants.CharacterConstants;
import xyz.pplax.pplaxblog.commons.enums.EStatus;
import xyz.pplax.pplaxblog.commons.enums.HttpStatus;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.serviceImpl.SuperServiceImpl;
import xyz.pplax.pplaxblog.commons.utils.StringUtils;
import xyz.pplax.pplaxblog.xo.dto.edit.BlogSortEditDto;
import xyz.pplax.pplaxblog.xo.dto.list.BlogSortGetListDto;
import xyz.pplax.pplaxblog.xo.entity.Blog;
import xyz.pplax.pplaxblog.xo.entity.BlogSort;
import xyz.pplax.pplaxblog.xo.constants.sql.BlogSQLConstants;
import xyz.pplax.pplaxblog.xo.constants.sql.BlogSortSQLConstants;
import xyz.pplax.pplaxblog.xo.mapper.BlogSortMapper;
import xyz.pplax.pplaxblog.xo.service.blog.BlogService;

import java.util.ArrayList;
import java.util.List;

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
     *
     * @param blogSortGetListDto
     * @return
     */
    @Override
    public List<BlogSort> list(BlogSortGetListDto blogSortGetListDto) {
        QueryWrapper<BlogSort> blogSortQueryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(blogSortGetListDto.getKeyword())) {
            // 如果关键词参数非空，就按该条件查询
            blogSortQueryWrapper.like(BlogSortSQLConstants.SORT_NAME, "%" + blogSortGetListDto.getKeyword() + "%");
        }

        //分页
        Page<BlogSort> page = new Page<>();
        page.setCurrent(blogSortGetListDto.getCurrentPage());
        page.setSize(blogSortGetListDto.getPageSize());

        // 获得非删除状态的
        blogSortQueryWrapper.ne(BlogSortSQLConstants.STATUS, EStatus.DISABLED.getStatus());
        // 按照sortNo排序
        blogSortQueryWrapper.orderByAsc(BlogSortSQLConstants.SORT_NO);

        IPage<BlogSort> pageList = null;

        // 排序
        if (blogSortGetListDto.getSortByClickCount() != null && blogSortGetListDto.getSortByClickCount()) {
            // 按点击量排序
            blogSortQueryWrapper.orderByDesc(BlogSortSQLConstants.CLICK_COUNT);
            // 查询
            pageList = blogSortMapper.selectPage(page, blogSortQueryWrapper);
        } else if (blogSortGetListDto.getSortByCites() != null && blogSortGetListDto.getSortByCites()) {
            // 按引用量排序
            blogSortQueryWrapper.and(
                    i -> i.ne(BlogSQLConstants.STATUS, EStatus.DISABLED.getStatus())
                            .or().isNull(BlogSQLConstants.STATUS)
            );
            // 查询
            pageList = blogSortMapper.selectListOrderByCites(page, blogSortQueryWrapper);
        } else {
            // 按创建时间排序
            blogSortQueryWrapper.orderByDesc(BlogSortSQLConstants.CREATE_TIME);
            // 查询
            pageList = blogSortMapper.selectPage(page, blogSortQueryWrapper);
        }

        List<BlogSort> blogSortList = new ArrayList<>();
        // 获得引用量
        for (BlogSort blogSort : pageList.getRecords()) {
            QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
            blogQueryWrapper.eq(BlogSQLConstants.BLOG_SORT_UID, blogSort.getUid());
            blogQueryWrapper.ne(BlogSQLConstants.STATUS, EStatus.DISABLED.getStatus());
            blogSort.setCites(blogService.count(blogQueryWrapper));

            blogSortList.add(blogSort);
        }

        // 获得列表
        return blogSortList;
    }

    /**
     * 获取博客分类的数量
     * @param blogSortGetListDto
     * @return
     */
    @Override
    public Long count(BlogSortGetListDto blogSortGetListDto) {
        QueryWrapper<BlogSort> blogSortQueryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(blogSortGetListDto.getKeyword())) {
            // 如果关键词参数非空，就按该条件查询
            blogSortQueryWrapper.like(BlogSortSQLConstants.SORT_NAME, "%" + blogSortGetListDto.getKeyword() + "%");
        }
        // 获得非删除状态的
        blogSortQueryWrapper.ne(BlogSortSQLConstants.STATUS, EStatus.DISABLED.getStatus());
        return (long) count(blogSortQueryWrapper);
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
        QueryWrapper<BlogSort> blogSortQueryWrapper = new QueryWrapper<>();
        blogSortQueryWrapper.orderByAsc(BlogSortSQLConstants.SORT_NO);
        List<BlogSort> blogSortList = list(blogSortQueryWrapper);

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

    @Override
    public ResponseResult removeById(String blogSortUid) {
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.eq(BlogSQLConstants.BLOG_SORT_UID, blogSortUid);
        blogQueryWrapper.ne(BlogSQLConstants.STATUS, EStatus.DISABLED);
        int count = blogService.count(blogQueryWrapper);

        if (count > 0) {
            return new ResponseResult(HttpStatus.BLOG_UNDER_THIS_SORT);
        }

        boolean res = super.removeById(blogSortUid);
        if (!res) {
            throw new RuntimeException();
        }

        return ResponseResult.success(HttpStatus.DELETE_SUCCESS);
    }

    /**
     * 逻辑删除
     * @param uid
     * @return
     */
    public ResponseResult logicDelete(String uid){
        List<String> uids = new ArrayList<>();
        uids.add(uid);
        BlogSortEditDto blogSortDto = new BlogSortEditDto();

        blogSortDto.setUids(uids);

        return logicBatchDelete(blogSortDto);
    }

    /**
     * 批量逻辑删除
     * @param blogSortEditDto
     * @return
     */
    @Override
    public ResponseResult logicBatchDelete(BlogSortEditDto blogSortEditDto) {
//        if (blogSortDto.getUids() == null || blogSortDto.getUids().isEmpty()) {
//            return ResponseResult.error(HttpStatus.PLEASE_SELECT_A_RECORD_TO_DELETE);
//        }
//
//        List<String> uids = new ArrayList<>(blogSortDto.getUids());
//
//        // 判断要删除的分类，是否有博客
//        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
//        blogQueryWrapper.ne(BlogSortSQLConstants.STATUS, EStatus.DISABLED);
//        blogQueryWrapper.in(BlogSQLConstants.BLOG_SORT_UID, uids);
//        if (blogMapper.selectCount(blogQueryWrapper) > 0) {
//            return ResponseResult.error(HttpStatus.BLOG_UNDER_THIS_SORT);
//        }
//
//        // 将该分类的子分类的parentUid置空
//        UpdateWrapper<BlogSort> blogSortUpdateWrapper = new UpdateWrapper<>();
//        blogSortUpdateWrapper.in(BlogSortSQLConstants.PARENT_UID, uids);
//        blogSortUpdateWrapper.set(BlogSortSQLConstants.PARENT_UID, null);
//        blogSortMapper.update(new BlogSort(), blogSortUpdateWrapper);
//
//        // 逻辑删除代码
//        Collection<BlogSort> blogSorts = blogSortMapper.selectBatchIds(blogSortDto.getUids());
//        for (BlogSort blogSort : blogSorts) {
//            blogSort.setStatus(EStatus.DISABLED.getStatus());
//            blogSortMapper.updateById(blogSort);
//        }

        return ResponseResult.success(HttpStatus.DELETE_SUCCESS);
    }
}
