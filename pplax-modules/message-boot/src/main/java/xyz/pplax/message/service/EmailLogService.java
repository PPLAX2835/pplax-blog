package xyz.pplax.message.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.core.util.ValidationUtils;
import xyz.pplax.core.valid.Update;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;
import xyz.pplax.data.util.PageUtils;
import xyz.pplax.message.po.EmailLog;
import xyz.pplax.message.pojo.EmailLogPojo;
import xyz.pplax.message.vo.EmailLogVO;

import javax.validation.groups.Default;


@Service
public class EmailLogService {

    @Autowired
    private PPLAXEmailLogService pplaxEmailLogService;

    public void insertEmailLog(EmailLogPojo emailLog) {
        //因为au_email_log表中的uid是自增的，可以不用设置uid
        pplaxEmailLogService.insert(BeanUtils.copyProperties(emailLog, EmailLog.class));
    }

    public int updateEmailLog(EmailLogPojo emailLog) throws BindException {
        //参数验证
        ValidationUtils.valid(emailLog, Update.class, Default.class);
        return pplaxEmailLogService.updateById(BeanUtils.copyProperties(emailLog, EmailLog.class));
    }

    public int physicalDeleteEmailLog(long uid) {
        return pplaxEmailLogService.deleteById(uid);
    }

    public PageData<EmailLogVO> queryListEmailLogByCondition(Condition<Long> condition) {
        PageHelper.startPage(condition.getPageNum(),condition.getPageSize(),condition.getOrderBy());
        return PageUtils.copyPageDataResult(pplaxEmailLogService.queryListByCondition(condition),EmailLogVO.class);
    }

    public int queryTotalEmailLogCount(EmailLogPojo pojo) {
        return pplaxEmailLogService.countByWhere(BeanUtils.copyProperties(pojo, EmailLog.class));
    }

    public EmailLogVO queryByUid(long uid) {
        return BeanUtils.copyProperties(pplaxEmailLogService.queryById(uid), EmailLogVO.class);
    }
}
