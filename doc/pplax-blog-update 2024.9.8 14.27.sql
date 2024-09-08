-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: pplax_blog
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_site_setting`
--

DROP TABLE IF EXISTS `t_site_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_site_setting` (
                                  `uid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '唯一uid',
                                  `name_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                                  `name_zh` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                                  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
                                  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
                                  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='站点配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_site_setting`
--

LOCK TABLES `t_site_setting` WRITE;
/*!40000 ALTER TABLE `t_site_setting` DISABLE KEYS */;
INSERT INTO `t_site_setting` (`uid`, `name_en`, `name_zh`, `value`, `status`, `create_time`, `update_time`) VALUES ('06f3bc1502de428b8a5a5aa31d5874fe','keyword','关键词','PPLAX,pplax,blog,博客,pplax blog',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('09cb25947cf647ea9761122540ec1cb3','qiniuAccessKey','qiniu访问key','null',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('1426840901bd45dc809d5417e42c43ac','gitee','码云','https://gitee.com/PPLAX',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('142a643b38754f2990bad307e9164716','storageMode','存储模式','qiniu',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('193075f8ab2147e6a210804a5df8817d','qq','QQ','1458667357',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('1d22ca96f15843feb0698f31dd99efae','touristAvatar','游客头像','http://file.pplax.xyz/default/avatar/visitor.jpg',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('26fd618acf194fe9adc02760498a1966','siteDomain','站点域名','http://pplax.xyz/',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('27576d573c194ab5a4f769ad1f5fe49b','authorAvatar','作者头像','http://file.pplax.xyz/default/avatar/author.jpg',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('2d1f0a19089646b5909f65ee0b0b596a','apiDomain','api地址','http://pplax.xyz:4390',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('3348fcd01fdb4e7ab1d45643c06a5ec6','email','email','lax1458667357@gmail.com',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('356dcca07b9f4eab9a28d554b70d918f','bilibili','bilibili','PPLAX',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('381460b8a6c84f7fb0d6bba620242277','recordNum','ICP备案号','冀ICP备2023028082号',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('3e921106b3854c25af92538a94befa98','localStorageBasePath','本地存储基本路径','G:\\tmp\\pplax-blog\\',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('3fe2872bf7774ea2ad1eebac979ed4f0','qiniuBucketName','qiniu桶名','null',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('50f0b0903b214c7f912df89b4899dd64','minioAccessKey','minio访问key','root',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('56279c86ce964d76ab8ad18f245adcf4','minioBucketName','minio桶名','pplax-blog',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('6466c39bf1d549248c28ee72e33f01ca','minioEndpoint','minio端点','http://127.0.0.1:9002',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('64d81e894b4647b298baa6e9a671c2b5','leaveMessagePageBackground','留言页面背景','http://file.pplax.xyz/default/bg/leaveMessage.jpg',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('660ed1f832064acaabf7d88b789dbed5','authorInfo','作者信息','穿红袍的是曹操',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('7d2424028d3e4132a0a9cc59ed3579cb','aboutMe','关于我','## 这是一个Spring Cloud的博客项目，借鉴学习了[蘑菇博客](https://gitee.com/moxi159753/mogu_blog_v2)、[aurora-blog](https://github.com/blog-aurora/aurora-blog)、[拾壹博客](https://gitee.com/quequnlong/shiyi-blog)等开源项目\n\n非常感谢大佬们的开源精神！\n\n这是我的仓库\n[pplax](https://gitee.com/PPLAX)\n\n![image.png](http://file.pplax.xyz/siteSetting/aboutMe/2024-05-05/1714891739784.png)\n\n这个是项目\n[pplax-blog](https://gitee.com/PPLAX/pplax-blog)\n![image.png](http://file.pplax.xyz/siteSetting/aboutMe/2024-05-05/1714891778447.png)\n\n么么~',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('7dec2b953d7d498e9080e8ea4e85ae68','author','作者','PPLAX',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('8984e045ce634171857e0342a227c164','qiniuZone','qiniu地区','null',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('8c5656f3460546748bf7742a2ae9b65a','loginBackground','登录背景','http://file.pplax.xyz/default/bg/login.jpg',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('8d54930b1e7644599e6464dd838d24f8','defaultRoleUid','注册用户默认角色uid','ffca2113713df757e0293c6dfd3b4e32',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('8e1e937ebeec4c0eac47e90924a7aa3a','captchaUrl','验证码图片url','https://imgapi.xl0408.top/index.php',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('929ddb3581ec48ba98f160caf7a56e6b','authorAvatarPendant','作者头像挂件','http://file.pplax.xyz/default/avatar/pendant.gif',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('92f1ff83e166454fb6b6d6ed1197a698','qiniuSecretKey','qiniu密钥','null',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('964937dade0749ad83a679bbba3a365e','minioSecretKey','minio密钥','password',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('a563564fd5104af48885ce76828334e8','blogName','博客名称','PPLAX\'s Blog',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('b52556d698ab4d25b59973fa741d7de2','github','github','https://github.com/PPLAX2835',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('b9ddde0934bb4940b5cec2a7c7ccb15a','authorBackground','作者背景图','http://file.pplax.xyz/default/bg/author.png',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('c0e5ad49d4d44700a2bac20ac984ffe8','touristBackground','游客背景图','http://file.pplax.xyz/default/bg/visitor.png',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('c1f55552c268428e8e741c2e2b934d5b','qiniuEndpoint','qiniu端点','null',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('ccb38c12cb894c618628e139eb0fff10','chatBackground','聊天背景','http://file.pplax.xyz/default/bg/chat.png',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('d3972e7266b6492192075b34844bef28','copyright','Copyright','PPLAX-Blog Copyright © 2022 - 2024',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('eb80c74344294b71bf31aaab09e764a9','sayPageBackground','说说页面背景图','http://file.pplax.xyz/default/bg/say.png',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('f0394085fb1d40cd99e38f7b7982b2be','theme','主题','http://file.pplax.xyz/default/logo/9877752_pplax_1687007948.png',1,'2024-09-08 07:35:37','2024-09-08 08:03:38'),('f5981ee5fd7641cfbbfa2327f40c8ca9','summary','站点简介','这是一个皮某人的网站',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('f6033f0f57074c6cb33226854148d6a6','defaultBlogCoverImage','博客默认封面','http://file.pplax.xyz/default/blogCoverImage/v2-c0094a18c6660c7efc50a7338728c4f3_720w.jpg',1,'2024-09-08 07:35:37','2024-09-08 07:35:37'),('f8a4f6faa0ff470cbb0b3187631bfb63','siteLogo','网站图标','http://file.pplax.xyz/default/logo/9877752_pplax_1687007948.png',1,'2024-09-08 07:35:37','2024-09-08 07:35:37');
/*!40000 ALTER TABLE `t_site_setting` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-08 16:13:03
