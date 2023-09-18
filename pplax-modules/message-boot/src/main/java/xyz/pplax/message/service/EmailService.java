package xyz.pplax.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import xyz.pplax.admin.pojo.UserPojo;
import xyz.pplax.admin.vo.UserVO;
import xyz.pplax.core.entity.R;
import xyz.pplax.core.enums.ResponseStatusCodeEnum;
import xyz.pplax.core.exception.PPLAXException;
import xyz.pplax.core.exception.email.EmailException;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.core.util.ConvertObjectUtils;
import xyz.pplax.core.util.JSONUtils;
import xyz.pplax.core.util.id.GenerateInfoUtils;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;
import xyz.pplax.data.util.PageUtils;
import xyz.pplax.mail.api.feign.UserFeignService;
import xyz.pplax.message.po.Email;
import xyz.pplax.message.pojo.EmailPojo;
import xyz.pplax.message.vo.EmailVO;
import xyz.pplax.starter.properties.PPLAXProperties;

import java.util.Objects;


@Service
public class EmailService {

    @Autowired
    private PPLAXEmailService pplaxEmailService;

    @Autowired
    private PPLAXProperties pplaxProperties;
    @Autowired
    private UserFeignService userFeignService;

    public void insertEmail(EmailPojo pojo)
            throws BindException, PPLAXException {
        // 判断邮箱是否已经存在
        if (queryByEmailNumber(pojo.getEmail()) != null) {
            throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_EXISTS);
        }
        Email email = BeanUtils.copyProperties(pojo, Email.class);

        /*R userR = userFeignService.queryUserByUid(email.getUserUid());
        UserVO userVO = JSONUtils.parseObjFromResult(ConvertObjectUtils.jsonToString(userR), "data", UserVO.class);

        if (userVO == null || userVO.getUid() == null) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST);
        }

        // 判断是否绑定 如果用户没有验证邮箱，也重新绑定
        if (userVO.getVerifyEmail()) {
            throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_HAD_BINDING);
        }*/

        //生成一个uid
        long uid = GenerateInfoUtils.generateUid(pplaxProperties.getSnowFlakeWorkerId(), pplaxProperties.getSnowFlakeDatacenterId());
        //其中user_uid应该在调用的此方法的时候，就已经赋值在email对象里面
        email.setUid(uid);
        pplaxEmailService.insert(email);
    }

    public int physicalDeleteEmail(long uid) {
        // 查询此邮箱是否绑定在某个用户上
        Email email = pplaxEmailService.queryById(uid);
        if (email == null) {
            throw new EmailException("无效的uid " + uid);
        }
        R r = userFeignService.queryUserByUid(new UserPojo() {{
            setUid(email.getUid());
        }});
        UserVO userVO = JSONUtils.parseObjFromResult(ConvertObjectUtils.jsonToString(r), "data", UserVO.class);
        if (userVO != null && userVO.getVerifyEmail() != null && userVO.getVerifyEmail()) {
            if (userVO.getEmailUid() == uid) {
                throw new EmailException("此邮箱已和" + userVO.getUsername() + "用户绑定，不能删除");
            }
        }
        return pplaxEmailService.deleteById(uid);
    }

    public int updateEmail(EmailPojo email) {
        Objects.requireNonNull(email,"修改操作需要传入数据");
        return pplaxEmailService.updateById(BeanUtils.copyProperties(email, Email.class));
    }

    public PageData<EmailVO> queryListEmailByCondition(Condition<Long> condition) {
        return PageUtils.copyPageDataResult(pplaxEmailService.queryListByCondition(condition), EmailVO.class);
    }

    public EmailVO queryEmailByUid(long uid) {
        Condition<Long> condition = new Condition<>();
        condition.setUid(uid);
        return BeanUtils.copyProperties(pplaxEmailService.queryById(uid),EmailVO.class);
    }

    public EmailVO queryEmailByUserUid(long userUid) {
        Email email = new Email();
        email.setUserUid(userUid);
        return BeanUtils.copyProperties(pplaxEmailService.queryOne(email), EmailVO.class);
    }

    public EmailVO queryByEmailNumber(String emailStr) {
        Email email = new Email();
        email.setEmail(emailStr);
        return BeanUtils.copyProperties(pplaxEmailService.queryOne(email), EmailVO.class);
    }
}
