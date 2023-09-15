package xyz.pplax;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.pplax.admin.dao.*;
import xyz.pplax.admin.dao.ext.PermissionRelationDaoExt;
import xyz.pplax.admin.po.*;
import xyz.pplax.article.dao.*;
import xyz.pplax.article.dao.ext.PPLAXArticleExtDao;
import xyz.pplax.article.po.*;
import xyz.pplax.auth.dao.PPLAXLoginInfoDao;
import xyz.pplax.auth.dao.PPLAXOauthClientDetailsDao;
import xyz.pplax.auth.dao.PPLAXOauthCodeDao;
import xyz.pplax.auth.po.LoginInfo;
import xyz.pplax.auth.po.OauthClientDetails;
import xyz.pplax.auth.po.OauthCode;
import xyz.pplax.auth.service.PPLAXLoginInfoService;
import xyz.pplax.auth.service.PPLAXOauthClientDetailsService;
import xyz.pplax.auth.service.PPLAXOauthCodeService;
import xyz.pplax.data.entity.Condition;

import java.util.Date;
import java.util.List;

@SpringBootTest
class TestBootApplicationTests {


    /**
     * 这里用的是pplax_auth_server表
     */

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


    /**
     * 这里用的是pplax_admin数据库
     */


    @Autowired
    PermissionRelationDaoExt permissionRelationDaoExt;
    @Test
    public void PermissionRelationDaoExtTest() {
        List<Role> roles = permissionRelationDaoExt.loadAllRoleByUserUid(1);
        System.out.println(roles);
    }


    @Autowired
    AdminRouterDao adminRouterDao;
    @Test
    public void AdminRouterDaoTest() {
        AdminRouter adminRouter = adminRouterDao.queryById(5L);
        System.out.println(adminRouter);
    }


    @Autowired
    PPLAXAdminSidebarDao pplaxAdminSidebarDao;
    @Test
    public void PPLAXAdminSidebarDaoTest() {
        AdminSidebar adminSidebar = new AdminSidebar();
        adminSidebar.setUid(1L);
        adminSidebar.setTitle("pplax");
        adminSidebar.setPath("/");
        pplaxAdminSidebarDao.insert(adminSidebar);
    }


    @Autowired
    PPLAXNavigationDao pplaxNavigationDao;
    @Test
    public void PPLAXNavigationDaoTest() {
        Navigation navigation = new Navigation();
        navigation.setUid(123L);
        navigation.setTitle("pplax");
        navigation.setPath("/");
        navigation.setUserUid(456L);
        pplaxNavigationDao.insert(navigation);
    }


    @Autowired
    PPLAXPermissionDao pplaxPermissionDao;
    @Test
    public void PPLAXPermissionDaoTest() {
        Permission permission = pplaxPermissionDao.queryById(12L);
        System.out.println(permission);
    }

    @Autowired
    PPLAXRoleDao pplaxRoleDao;
    @Test
    public void PPLAXRoleDaoTest() {
        Role role = pplaxRoleDao.queryById(3L);
        System.out.println(role);
    }


    @Autowired
    PPLAXRolePermissionDao pplaxRolePermissionDao;
    @Test
    public void PPLAXRolePermissionDaoTest() {
        Permission permission = pplaxPermissionDao.queryById(5L);
        System.out.println(JSON.toJSONString(permission));
    }


    @Autowired
    PPLAXSettingDao pplaxSettingDao;
    @Test
    public void PPLAXSettingDaoTest() {
        Setting setting = pplaxSettingDao.queryById(1L);
        System.out.println(setting);
    }


    @Autowired
    PPLAXSiteDao pplaxSiteDao;
    @Test
    public void PPLAXSiteDaoTest() {
        Site site = new Site();
        site.setUid(1L);
        site.setTitle("pplax");
        site.setUserUid(2L);
        pplaxSiteDao.insert(site);
    }


    @Autowired
    PPLAXSocialDao pplaxSocialDao;
    @Test
    public void PPLAXSocialDaoTest() {
        Social social = new Social();
        social.setUid(1L);
        social.setSocialName("pplax");
        social.setSocialIcon("pplax");
        social.setSocialUrl("/");
        pplaxSocialDao.insert(social);
    }

    @Autowired
    PPLAXUserDao pplaxUserDao;
    @Test
    public void PPLAXUserDaoTest() {
        User user = pplaxUserDao.queryById(1634877081002713088L);
        System.out.println(user);
    }


    @Autowired
    PPLAXUserRoleDao pplaxUserRoleDao;
    @Test
    public void PPLAXUserRoleDaoTest() {
        UserRoleRelationship userRoleRelationship = pplaxUserRoleDao.queryById(12L);
        System.out.println(userRoleRelationship);
    }


    @Autowired
    PPLAXWhiteUrlDao pplaxWhiteUrlDao;
    @Test
    public void PPLAXWhiteUrlDaoTest() {
        Condition<WhiteUrl> condition = new Condition<>();
        List<WhiteUrl> whiteUrls = pplaxWhiteUrlDao.queryListByCondition(condition);
        System.out.println(whiteUrls);
    }


    @Autowired
    PPLAXRouterPermissionDao pplaxRouterPermissionDao;
    @Test
    public void RouterPermissionDaoTest() {
        RouterPermission routerPermission = new RouterPermission();
        routerPermission.setUid(1L);
        routerPermission.setAdminRouterUid(1L);
        routerPermission.setPermissionUid(1L);
        pplaxRouterPermissionDao.insert(routerPermission);
    }


    @Autowired
    SiteSettingDao siteSettingDao;
    @Test
    public void SiteSettingDaoTest() {
        SiteSetting siteSetting = siteSettingDao.queryById(1L);
        System.out.println(siteSetting);
    }


    /**
     * 这里用的是pplax_article数据库
     */

    @Autowired
    PPLAXArticleExtDao pplaxArticleExtDao;
    @Test
    public void PPLAXArticleExtDaoTest() {
        Article article = pplaxArticleExtDao.queryByIdForUpdate(1L);
        System.out.println(article);
    }


    @Autowired
    PPLAXArticleDao pplaxArticleDao;
    @Test
    public void PPLAXArticleDaoTest() {
        Article article = new Article();
        article.setTitle("PPLAX");
        article.setUserUid(1L);
        article.setUid(1L);
        article.setContent("Thins is an Article");

        pplaxArticleDao.insert(article);
    }


    @Autowired
    PPLAXBulletinDao pplaxBulletinDao;
    @Test
    public void PPLAXBulletinDaoTest() {
        Bulletin bulletin = new Bulletin();
        bulletin.setUid(1L);
        bulletin.setTitle("pplax");
        bulletin.setContent("This is an bulletin");
        pplaxBulletinDao.insert(bulletin);
    }


    @Autowired
    PPLAXCategoryDao pplaxCategoryDao;
    @Test
    public void PPLAXCategoryDaoTest() {
        Category category = new Category();
        category.setUid(1L);
        category.setTitle("pplax");
        category.setUserUid(1L);
        category.setSummary("This is a summary");
        pplaxCategoryDao.insert(category);
    }


    @Autowired
    PPLAXLinkDao pplaxLinkDao;
    @Test
    public void PPLAXLinkDaoTest() {
        Link link = pplaxLinkDao.queryById(1L);
        System.out.println(link);


    }


    @Autowired
    PPLAXTagDao pplaxTagDao;
    @Test
    public void PPLAXTagDaoTest() {
        Tag tag = new Tag();
        tag.setUid(1L);
        tag.setTitle("pplax");
        tag.setSummary("This is a tag");
        tag.setCoverUrl("/");
        tag.setUserUid(1L);
        pplaxTagDao.insert(tag);
    }


    @Autowired
    PPLAXTalkDao pplaxTalkDao;
    @Test
    public void PPLAXTalkDaoTest() {
        Tag tag = pplaxTagDao.queryById(1L);
        System.out.println(tag);
    }
}
