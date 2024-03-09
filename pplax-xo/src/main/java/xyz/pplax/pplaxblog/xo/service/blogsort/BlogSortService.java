package xyz.pplax.pplaxblog.xo.service.blogsort;

import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.pplax.pplaxblog.commons.response.ResponseResult;
import xyz.pplax.pplaxblog.xo.base.service.SuperService;
import xyz.pplax.pplaxblog.xo.dto.BlogSortDto;
import xyz.pplax.pplaxblog.xo.dto.list.BlogSortGetListDto;
import xyz.pplax.pplaxblog.xo.entity.BlogSort;

import java.util.List;

/**
 * 博客分类
 */
public interface BlogSortService extends SuperService<BlogSort> {

    /**
     * 获取博客分类列表
     *
     * @param blogSortGetListDto
     * @return
     */
    public List<BlogSort> list(BlogSortGetListDto blogSortGetListDto);

    /**
     * 获取博客分类的数量
     * @param blogSortGetListDto
     * @return
     */
    public Long count(BlogSortGetListDto blogSortGetListDto);

    /**
     * 检查当前分类是否存在
     * @param sortName
     * @return
     */
    public Boolean isSortNameExist(String sortName);

    /**
     * 新增博客分类
     *
     * @param blogSortDto
     */
    public ResponseResult addBlogSort(BlogSortDto blogSortDto);

    /**
     * 编辑博客分类
     *
     * @param blogSortDto
     */
    public ResponseResult editBlogSort(BlogSortDto blogSortDto);

    /**
     * 逻辑删除
     * @param uid
     * @return
     */
    public ResponseResult logicDelete(String uid);

    /**
     * 批量逻辑删除
     * @param blogSortDto
     * @return
     */
    public ResponseResult logicBatchDelete(BlogSortDto blogSortDto);

}
