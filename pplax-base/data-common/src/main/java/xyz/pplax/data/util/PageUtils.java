package xyz.pplax.data.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;

import java.util.List;
import java.util.function.Consumer;

/**
 * 分页相关工具类
 */
public class PageUtils {
    public static <T> PageData<T> pageList(List<T> queriedResult) {
        PageInfo<T> pageInfo = new PageInfo<>(queriedResult);
        PageData<T> pageData = new PageData<>();
        pageData.setPages(pageInfo.getPages());
        pageData.setResult(queriedResult);
        pageData.setTotal(pageInfo.getTotal());
        return pageData;
    }

    public static <T,I> PageData<I> copyPageDataResult(PageData<T> pageData, Class<I> cClass) {
        List<T> result = pageData.getResult();
        List<I> list = BeanUtils.copyList(result, cClass);
        PageData<I> copyPageData = new PageData<>();
        copyPageData.setTotal(pageData.getTotal());
        copyPageData.setPages(pageData.getPages());
        copyPageData.setResult(list);
        copyPageData.setPageSize(pageData.getPageSize());
        copyPageData.setPageNum(pageData.getPageNum());
        return copyPageData;
    }

    @SafeVarargs
    public static <T,I> PageData<T> pageList(Condition<I> condition, Consumer<Condition<I>> consumer, Class<T>... zClass) {
        Page<T> page = getPageInfo(condition, consumer);
        PageData<T> pageData = new PageData<>();

        pageData.setResult(page.getResult());
        pageData.setTotal(page.getTotal());
        pageData.setPages(page.getPages());

        if (zClass.length > 0) {
            // 只需要第一个
            List<T> list = BeanUtils.copyList(page.getResult(), zClass[0]);
            pageData.setResult(list);
        }
        return pageData;
    }

    private static <T,I> Page<T> getPageInfo(Condition<I> condition, Consumer<Condition<I>> consumer) {
        return PageHelper.startPage(condition.getPageNum(), condition.getPageSize(), condition.getOrderBy()).doSelectPage(() -> {
            consumer.accept(condition);
        });
    }
}
