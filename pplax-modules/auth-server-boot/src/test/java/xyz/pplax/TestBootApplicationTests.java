package xyz.pplax;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.pplax.auth.pojo.OauthClientDetailsPojo;
import xyz.pplax.auth.service.JwtTokenUserDetailsService;
import xyz.pplax.auth.service.OauthClientDetailsService;
import xyz.pplax.auth.vo.OauthClientDetailsVO;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;

import java.util.List;
import java.util.Set;

@SpringBootTest
class TestBootApplicationTests {

    @Test
    public void Test() {
        System.out.println("test");
    }


    @Autowired
    OauthClientDetailsService oauthClientDetailsService;

    /**
     * 插入方法测试
     */
    @Test
    public void OauthClientDetailsServiceInsertTest() {
        OauthClientDetailsPojo oauthClientDetailsPojo = new OauthClientDetailsPojo();
        oauthClientDetailsPojo.setClientId("PPLAX");
        oauthClientDetailsPojo.setScope("All");
        oauthClientDetailsPojo.setAuthorizedGrantTypes("authorization_code,client_credentials,refresh_token,password");
        oauthClientDetailsPojo.setClientSecret("pplax123456");
        oauthClientDetailsService.insertOauthClient(oauthClientDetailsPojo);
    }

    /**
     * 查询测试
     */
    @Test
    public void OauthClientDetailsServiceQueryTest() {
        Condition<Object> condition = new Condition<>();
        condition.setPageNum(0);
        condition.setPageSize(10);
        PageData<OauthClientDetailsVO> oauthClientDetailsVOPageData = oauthClientDetailsService.queryListOauthClientByCondition(condition);
        System.out.println(oauthClientDetailsVOPageData);

        OauthClientDetailsPojo oauthClientDetailsPojo = new OauthClientDetailsPojo();
        oauthClientDetailsPojo.setClientId("PPLAX");
        OauthClientDetailsVO oauthClientDetailsVO = oauthClientDetailsService.queryOauthClientByClientId(oauthClientDetailsPojo);
        System.out.println(oauthClientDetailsVO);
    }


    @Autowired
    JwtTokenUserDetailsService jwtTokenUserDetailsService;
    @Test
    public void JwtTokenUserDetailsServiceTest() {
        UserDetails pplax = jwtTokenUserDetailsService.loadUserByUsername("pplax");
        System.out.println(JSON.toJSONString(pplax));
    }


    @Autowired
    RedisTemplate redisTemplate;
    @Test
    public void doR() {
        Set keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }
}
