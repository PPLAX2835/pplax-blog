package xyz.pplax.pplaxblog.xo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.pplax.pplaxblog.commons.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.BlogSortDto;
import xyz.pplax.pplaxblog.xo.entity.BlogSort;

/**
 * 博客分类
 */
public interface BlogSortService extends SuperService<BlogSort> {

    /**
     * 获取博客分类列表
     *
     * @param blogSortDto
     * @return
     */
    public IPage<BlogSort> getPageList(BlogSortDto blogSortDto);

    /**
     * 检查当前分类是否存在
     * @param blogSortDto
     * @return
     */
    public Boolean checkSortNameExists(BlogSortDto blogSortDto);

    /**
     * 新增博客分类
     *
     * @param blogSortDto
     */
    public String addBlogSort(BlogSortDto blogSortDto);

    /**
     * 编辑博客分类
     *
     * @param blogSortDto
     */
    public String editBlogSort(BlogSortDto blogSortDto);

    /**
     * 逻辑删除
     * @param uid
     * @return
     */
    public String logicDelete(String uid);

    /**
     * 批量逻辑删除
     * @param blogSortDto
     * @return
     */
    public String logicBatchDelete(BlogSortDto blogSortDto);

}
