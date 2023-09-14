package xyz.pplax;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.pplax.auth.dao.PPLAXLoginInfoDao;
import xyz.pplax.auth.po.LoginInfo;

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

}
