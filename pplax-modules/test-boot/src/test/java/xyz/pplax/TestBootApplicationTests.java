package xyz.pplax;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.pplax.auth.dao.PPLAXLoginInfoDao;
import xyz.pplax.auth.dao.PPLAXOauthClientDetailsDao;
import xyz.pplax.auth.dao.PPLAXOauthCodeDao;
import xyz.pplax.auth.po.LoginInfo;
import xyz.pplax.auth.po.OauthClientDetails;
import xyz.pplax.auth.po.OauthCode;
import xyz.pplax.auth.service.PPLAXLoginInfoService;
import xyz.pplax.auth.service.PPLAXOauthClientDetailsService;
import xyz.pplax.auth.service.PPLAXOauthCodeService;

import java.util.Date;

@SpringBootTest
class TestBootApplicationTests {

    @Autowired
    PPLAXLoginInfoDao pplaxLoginInfoDao;
    @Test
    public void PPLAXLoginInfoDaoTest() {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setLoginIp("127.0.0.1");
        loginInfo.setLoginLocation("hebei");
        loginInfo.setCreateTime("2023-09-14 19:31");
        loginInfo.setMessage("aaa");
        loginInfo.setStatus(true);
        loginInfo.setUid(123456L);
        loginInfo.setUsername("PPLAX");
        loginInfo.setOperationSystemInfo("Windows 11");

        pplaxLoginInfoDao.insert(loginInfo);
    }


    @Autowired
    PPLAXOauthClientDetailsDao pplaxOauthClientDetailsDao;
    @Test
    public void PPLAXOauthClientDetailsDaoTest() {
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        oauthClientDetails.setClientId("pplax123");
        oauthClientDetails.setClientSecret("123");
        pplaxOauthClientDetailsDao.insert(oauthClientDetails);
    }

    @Autowired
    PPLAXOauthCodeDao pplaxOauthCodeDao;
    @Test
    public void PPLAXOauthCodeDaoTest() {
        OauthCode oauthCode = new OauthCode();
        oauthCode.setCode("123");
        oauthCode.setAuthentication(123L);

        pplaxOauthCodeDao.insert(oauthCode);
    }


    @Autowired
    PPLAXLoginInfoService pplaxLoginInfoService;
    @Test
    public void PPLAXLoginInfoServiceTest() {
        LoginInfo loginInfo = pplaxLoginInfoService.queryById(123456L);
        System.out.println(JSON.toJSONString(loginInfo));
    }


    @Autowired
    PPLAXOauthClientDetailsService pplaxOauthClientDetailsService;
    @Test
    public void PPLAXOauthClientDetailsServiceTest() {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setLoginIp("127.0.0.1");
        loginInfo.setLoginLocation("hebei");
        loginInfo.setCreateTime("2023-09-14 19:31");
        loginInfo.setMessage("aaa");
        loginInfo.setStatus(true);
        loginInfo.setUid(1234567L);
        loginInfo.setUsername("PPLAX");
        loginInfo.setOperationSystemInfo("Windows 11");
        pplaxLoginInfoService.insert(loginInfo);
    }

    @Autowired
    PPLAXOauthCodeService pplaxOauthCodeService;
    @Test
    public void PPLAXOauthCodeServiceTest() {
        OauthCode oauthCode = new OauthCode();
        oauthCode.setCode("456");
        oauthCode.setAuthentication(456L);
        pplaxOauthCodeService.insert(oauthCode);
    }

}
