package xyz.pplax.service.base;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;

import java.util.List;

@Service
public class BaseService<T> {
    private BaseDao<T> baseDao;

    public void setBaseDao(BaseDao<T> baseDao) {
        this.baseDao = baseDao;
    }

    @Transactional(rollbackFor = Exception.class)
    public T insert(T t) {
        baseDao.insert(t);
        return t;
    }

    @Transactional(rollbackFor = Exception.class)
    public int batchInsert(List<T> list) {
        return baseDao.batchInsert(list);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateById(T t) {
        return baseDao.updateById(t);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteById(Long uid) {
        return baseDao.deleteById(uid);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteByWhere(T t) {
        return baseDao.deleteByWhere(t);
    }

    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(List<Long> list) {
        return baseDao.batchDelete(list);
    }

    public T queryById(Long uid) {
        return baseDao.queryById(uid);
    }

    public T queryOne(T t) {
        return baseDao.queryOne(t);
    }

    public List<T> queryListByWhere(T t) {
        return baseDao.queryListByWhere(t);
    }

    public List<T> queryListByIds(List<Long> uids) {
        return baseDao.queryListByIds(uids);
    }

    public int countByWhere(T t) {
        return baseDao.countByWhere(t);
    }

    public PageData<T> queryListByCondition(Condition condition) {
        String orderBy = "";
        if (StringUtils.hasLength(condition.getOrderBy())) {
            orderBy = condition.getOrderBy();
        }else {
            // 默认采用创建时间降序
            orderBy = "create_time desc";
        }
        condition.setOrderBy(orderBy);
        Page<T> page = PageHelper.startPage(condition.getPageNum(), condition.getPageSize(), condition.getOrderBy());
        List<T> list = baseDao.queryListByCondition(condition);
        PageData<T> pageData = new PageData<>();
        pageData.setResult(list);
        pageData.setTotal(page.getTotal());
        pageData.setPages(page.getPages());
        pageData.setPageSize(page.getPageSize());
        pageData.setPageNum(page.getPageNum());
        return pageData;
    }

    public PageData<T> pageQuery(T t, int currentPage, int pageSize) {
        Page<T> page = PageHelper.startPage(currentPage, pageSize);
        List<T> list = baseDao.queryListByWhere(t);
        PageData<T> pageData = new PageData<T>();
        pageData.setResult(list);
        pageData.setTotal(page.getTotal());
        pageData.setPages(page.getPages());
        return pageData;
    }
}