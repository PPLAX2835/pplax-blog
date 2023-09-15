package xyz.pplax.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.pplax.auth.po.LoginInfo;
import xyz.pplax.auth.pojo.LoginInfoPojo;
import xyz.pplax.auth.vo.LoginInfoVO;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;
import xyz.pplax.data.util.PageUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * 登录信息service层
 */
@Service
public class LoginInfoService {

    @Autowired
    private PPLAXLoginInfoService pplaxLoginInfoService;

    /**
     * 通过id进行物理删除
     * @param uid
     * @return
     */
    public int physicalDeleteLoginInfo(Long uid) {
        Assert.notNull(uid, "uid不能为null");
        return pplaxLoginInfoService.deleteById(uid);
    }

    /**
     * 通过uid进行批量删除
     * @param pojo
     * @return
     */
    public int batchDeleteLoginInfoByUid(LoginInfoPojo pojo) {
        Long[] uids = pojo.getUids();
        Assert.notNull(uids, "批量删除记录，uids不能为null");
        final int[] successDeleteNum = {0};
        Arrays.stream(uids).forEach(uid -> successDeleteNum[0] = successDeleteNum[0] + pplaxLoginInfoService.deleteById(uid));
        return successDeleteNum[0];
    }

    /**
     * 添加登录信息
     * @param record
     */
    public void insertLoginInfo(LoginInfoPojo record) {
        Assert.notNull(record, "登录信息不能为null");
        record.setStatus(Optional.ofNullable(record.getStatus()).orElse(false));
        pplaxLoginInfoService.insert(BeanUtils.copyProperties(record, LoginInfo.class));
    }

    /**
     * 条件查询 列表
     * @param condition
     * @return
     */
    public PageData<LoginInfoVO> queryListLoginInfoByCondition(Condition<Long> condition) {
        Assert.notNull(condition, "查询条件不能为null");
        return PageUtils.copyPageDataResult(pplaxLoginInfoService.queryListByCondition(condition), LoginInfoVO.class);
    }

    /**
     * 通过用户名查找
     * @param username
     * @return
     */
    public LoginInfoVO queryLoginInfoByUsername(String username) {
        // 返回该用户最近登录的日志信息
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUsername(username);
        return BeanUtils.copyProperties(pplaxLoginInfoService.queryOne(loginInfo), LoginInfoVO.class);
    }

    /**
     * 更新
     * @param record
     * @return
     */
    public int updateLoginInfo(LoginInfoPojo record) {
        Assert.notNull(record, "登录信息不能为null");
        // 如果ip为null的话，则从请求中获取
        // if (!StringUtils.hasLength(record.getLoginIp())) {
        //     String ip = NetWorkUtils.getIpAddr(PPLAXRequestUtils.getCurrentRequest());
        //     record.setLoginIp(ip);
        // }
        return pplaxLoginInfoService.updateById(BeanUtils.copyProperties(record, LoginInfo.class));
    }
}
