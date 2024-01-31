package xyz.pplax.pplaxblog.commons.dto;

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
}
