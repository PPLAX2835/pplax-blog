package xyz.pplax.pplaxblog.xo.base.dto;

import xyz.pplax.pplaxblog.commons.validator.Messages;
import xyz.pplax.pplaxblog.commons.validator.annotion.LongNotNull;
import xyz.pplax.pplaxblog.commons.validator.group.GetList;
import lombok.Data;

/**
 * PageVO  用于分页
 */
@Data
public class PageDto<T> {

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 当前页
     */
    @LongNotNull(groups = {GetList.class}, message = Messages.PAGE_NOT_NULL)
    private Long currentPage;

    /**
     * 页大小
     */
    @LongNotNull(groups = {GetList.class}, message = Messages.SIZE_NOT_NULL)
    private Long pageSize;

    /**
     * 数据库查询参数limit
     */
    @LongNotNull(groups = {GetList.class}, message = Messages.PAGE_NOT_NULL)
    private Long limit;

    /**
     * 数据库查询参数offset
     */
    @LongNotNull(groups = {GetList.class}, message = Messages.SIZE_NOT_NULL)
    private Long offset;

    /**
     * 计算分页参数，便于数据库查询时使用
     */
    public void paginationCalculate() {
        limit = (currentPage - 1) * pageSize;
        offset = pageSize;
    }
}
