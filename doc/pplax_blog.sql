-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: pplax_blog
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `t_blog`
--

DROP TABLE IF EXISTS `t_blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_blog` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `title` varchar(200) DEFAULT NULL COMMENT '博客标题',
  `summary` varchar(200) DEFAULT NULL COMMENT '博客简介',
  `blog_content_uid` varchar(32) NOT NULL COMMENT '博客内容uid',
  `tag_uid` varchar(255) DEFAULT NULL COMMENT '标签uid',
  `click_count` int DEFAULT '0' COMMENT '博客点击数',
  `collect_count` int DEFAULT '0' COMMENT '博客收藏数',
  `picture_uid` varchar(255) DEFAULT NULL COMMENT '标题图片uid',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `is_publish` varchar(1) DEFAULT '1' COMMENT '是否发布：0：否，1：是',
  `user_uid` varchar(32) DEFAULT NULL COMMENT '用户uid',
  `is_original` tinyint(1) DEFAULT '1' COMMENT '是否原创（0:不是 1：是）',
  `articles_part` varchar(255) DEFAULT NULL COMMENT '文章出处',
  `blog_sort_uid` varchar(32) DEFAULT NULL COMMENT '博客分类UID',
  `level` tinyint(1) DEFAULT '0' COMMENT '推荐等级(0:正常)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='博客表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_blog`
--

LOCK TABLES `t_blog` WRITE;
/*!40000 ALTER TABLE `t_blog` DISABLE KEYS */;
INSERT INTO `t_blog` (`uid`, `title`, `summary`, `blog_content_uid`, `tag_uid`, `click_count`, `collect_count`, `picture_uid`, `status`, `is_publish`, `user_uid`, `is_original`, `articles_part`, `blog_sort_uid`, `level`, `create_time`, `update_time`) VALUES ('211fb300fe7a4a20bfda396a9450b117','后端开发简历咋整呢',NULL,'771690201d694446b653b9f6a9eabbaf',NULL,0,0,NULL,1,'1',NULL,1,NULL,'3cf89eab5cf2484f8023f088dd3f3cd5',0,'2024-01-15 07:54:25','2024-01-15 07:54:25'),('b7dbc212bf304422be9fa22c3f2cf1a2','年轻人的第一款怪猎',NULL,'00ef773b1cb542bb8e4d262e5f23e37c',NULL,0,0,NULL,1,'1',NULL,1,NULL,'218b8f96728541d3847099737c3ef947',0,'2024-01-15 07:48:52','2024-01-15 07:51:10'),('dcf8f268a51143fb988384e7559e5650','黑龙登龙点讲解',NULL,'afa22a4ffa7e41de99863f16ab3e5c97',NULL,0,0,NULL,1,'1',NULL,1,NULL,'218b8f96728541d3847099737c3ef947',0,'2024-01-15 07:52:42','2024-01-15 07:52:42'),('f4bd17e13924496889a2eb16ae01ba30','这是一个标题',NULL,'9ddd597bb3ff4426b84f35439f553e94',NULL,0,0,NULL,1,'1',NULL,1,NULL,NULL,0,'2024-01-15 07:48:52','2024-01-15 07:48:52');
/*!40000 ALTER TABLE `t_blog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_blog_content`
--

DROP TABLE IF EXISTS `t_blog_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_blog_content` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `content` longtext COMMENT '博客内容',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='博客内容表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_blog_content`
--

LOCK TABLES `t_blog_content` WRITE;
/*!40000 ALTER TABLE `t_blog_content` DISABLE KEYS */;
INSERT INTO `t_blog_content` (`uid`, `content`, `status`, `create_time`, `update_time`) VALUES ('00ef773b1cb542bb8e4d262e5f23e37c','罚你登龙给怪回血',1,'2024-01-15 07:48:34','2024-01-15 07:48:34'),('771690201d694446b653b9f6a9eabbaf','STAR法狠狠整',1,'2024-01-15 07:53:59','2024-01-15 07:53:59'),('9ddd597bb3ff4426b84f35439f553e94','这是文章内容',1,'2024-01-15 07:48:34','2024-01-15 07:48:34'),('afa22a4ffa7e41de99863f16ab3e5c97','扇形火狠狠登',1,'2024-01-15 07:52:28','2024-01-15 07:52:28');
/*!40000 ALTER TABLE `t_blog_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_blog_sort`
--

DROP TABLE IF EXISTS `t_blog_sort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_blog_sort` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `sort_name` varchar(255) DEFAULT NULL COMMENT '分类名',
  `summary` varchar(200) DEFAULT NULL COMMENT '分类简介',
  `content` varchar(255) DEFAULT NULL COMMENT '分类内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `parent_uid` varchar(32) DEFAULT NULL COMMENT '父级分类uid',
  `click_count` int DEFAULT '0' COMMENT '点击数',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='博客分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_blog_sort`
--

LOCK TABLES `t_blog_sort` WRITE;
/*!40000 ALTER TABLE `t_blog_sort` DISABLE KEYS */;
INSERT INTO `t_blog_sort` (`uid`, `sort_name`, `summary`, `content`, `create_time`, `update_time`, `status`, `parent_uid`, `click_count`) VALUES ('093d8bdd01c84890a928e923d5c235fe','时光轴','时光轴的简介','时光飞逝，机会就在我们眼前，何时找到了灵感，就要把握机遇，我们需要智慧和勇气去把握机会','2018-09-24 09:14:59','2023-11-29 12:03:29',1,NULL,0),('218b8f96728541d3847099737c3ef947','怪物猎人：世界','第一代高清化怪猎',NULL,'2024-01-15 14:37:06','2024-01-15 06:37:24',1,'ba52e210f5174a4f943680033dc03d3e',0),('265c29f1807440aeaf745075e949c971','原神','你说得对，后边忘了',NULL,'2023-11-30 03:36:37','2024-01-15 06:00:26',1,'3ae899e993b744c99fb78dccafac8e66',0),('2c93dfab0e754006866f8ed486923a41','慢生活','慢生活的简介','慢生活，不是懒惰，放慢速度不是拖延时间，而是让我们在生活中寻找到平衡','2018-09-24 08:29:33','2023-11-29 12:03:29',1,NULL,0),('2fc443ca683bc93248873dcac0ccda9d','考研','现在考研好难哦',NULL,'2024-01-16 14:48:17','2024-01-16 14:48:17',1,NULL,0),('3ae899e993b744c99fb78dccafac8e66','游戏','游戏的简介','玩游戏玩的','2023-11-29 11:18:55','2023-11-29 12:03:29',1,NULL,0),('3cf89eab5cf2484f8023f088dd3f3cd5','编程','编程 简介','内容','2023-11-30 03:34:12','2023-11-30 03:34:12',1,NULL,0),('9bf8bc845c124195839a34473d88aff4','灭尽龙','本体封面怪，脑婆~',NULL,'2024-01-15 14:35:39','2024-01-15 08:18:06',1,'218b8f96728541d3847099737c3ef947',0),('ba52e210f5174a4f943680033dc03d3e','怪物猎人','怪物猎人的简介','玩太刀玩的','2023-11-29 11:19:30','2024-01-15 06:37:24',1,'3ae899e993b744c99fb78dccafac8e66',0),('fbef6ff4be704781a0e0c4aeb7a2b64b','美食',NULL,NULL,'2023-11-30 07:21:42','2023-11-30 07:21:42',1,'2c93dfab0e754006866f8ed486923a41',0);
/*!40000 ALTER TABLE `t_blog_sort` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_collect`
--

DROP TABLE IF EXISTS `t_collect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_collect` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `user_uid` varchar(32) NOT NULL COMMENT '用户的uid',
  `blog_uid` varchar(32) NOT NULL COMMENT '博客的uid',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='收藏表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_collect`
--

LOCK TABLES `t_collect` WRITE;
/*!40000 ALTER TABLE `t_collect` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_collect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_comment`
--

DROP TABLE IF EXISTS `t_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_comment` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `user_uid` varchar(32) DEFAULT NULL COMMENT '用户uid',
  `to_uid` varchar(32) DEFAULT NULL COMMENT '回复某条评论的uid',
  `to_user_uid` varchar(32) DEFAULT NULL COMMENT '回复某个人的uid',
  `content` varchar(1000) DEFAULT NULL COMMENT '评论内容',
  `blog_uid` varchar(32) DEFAULT NULL COMMENT '博客uid',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_comment`
--

LOCK TABLES `t_comment` WRITE;
/*!40000 ALTER TABLE `t_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_feedback`
--

DROP TABLE IF EXISTS `t_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_feedback` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `user_uid` varchar(32) NOT NULL COMMENT '用户uid',
  `content` varchar(1000) DEFAULT NULL COMMENT '反馈的内容',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='反馈表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_feedback`
--

LOCK TABLES `t_feedback` WRITE;
/*!40000 ALTER TABLE `t_feedback` DISABLE KEYS */;
INSERT INTO `t_feedback` (`uid`, `user_uid`, `content`, `status`, `create_time`, `update_time`) VALUES ('3b3f83c0af664338ab4be666fb97875a','7c5bb9f3ccd54fc6a9804955cf6488ad','你家厕所漏水了',1,'2023-11-30 07:52:16','2023-11-30 07:52:16'),('4a571b6b5ab346ad88b3b4319563f450','94437dc0211745449649b283840ab2a5','2+656',1,'2023-11-30 09:36:27','2023-11-30 09:36:27'),('ac537eb9d7c7476b871fd61a65d0a29e','7a0233eb493345d0831b34d3a9c1b4e8','这是一条反馈',1,'2023-11-30 07:52:16','2023-11-30 07:52:16'),('c5589ab67ae546c2b4dfe20065f45e86','a0c2bb72c1934ab08455f564794d09c1','do you like what you say?',1,'2023-11-30 07:52:16','2023-11-30 07:52:16'),('d0a7dc6f21164d18995ffe1353fde9ed','7c5bb9f3ccd54fc6a9804955cf6488ad','asdasd',1,'2023-11-30 09:37:16','2023-11-30 09:37:16'),('dcf886705a9147d783144118636dddb6','94437dc0211745449649b283840ab2a5','来自河北的皮先生，你好',1,'2023-11-30 07:52:16','2023-11-30 08:43:50');
/*!40000 ALTER TABLE `t_feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_link`
--

DROP TABLE IF EXISTS `t_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `title` varchar(255) DEFAULT NULL COMMENT '友情链接标题',
  `summary` varchar(255) DEFAULT NULL COMMENT '友情链接介绍',
  `url` varchar(255) DEFAULT NULL COMMENT '友情链接URL',
  `click_count` int DEFAULT '0' COMMENT '点击数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='友情链接表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_link`
--

LOCK TABLES `t_link` WRITE;
/*!40000 ALTER TABLE `t_link` DISABLE KEYS */;
INSERT INTO `t_link` (`uid`, `title`, `summary`, `url`, `click_count`, `create_time`, `update_time`, `status`) VALUES ('bb418e0a9d27490bb71972bd8da5afa6','IT大本营','IT大本营 - 专注于技术分享的开发者社区','http://www.itarea.cn/',1,'2018-09-26 05:55:33','2018-09-26 05:55:33',1),('dcc01149be71492dabd55821c22f6061','Mybatis-plus','MyBatis-Plus 为简化开发而生','http://mp.baomidou.com/',1,'2018-09-26 10:52:58','2018-09-26 10:52:58',1);
/*!40000 ALTER TABLE `t_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_picture`
--

DROP TABLE IF EXISTS `t_picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_picture` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `file_uid` varchar(32) DEFAULT NULL COMMENT '文件uid',
  `picture_name` varchar(255) DEFAULT NULL COMMENT '图片名',
  `picture_sort_uid` varchar(32) DEFAULT NULL COMMENT '分类uid',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='图片表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_picture`
--

LOCK TABLES `t_picture` WRITE;
/*!40000 ALTER TABLE `t_picture` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_picture_sort`
--

DROP TABLE IF EXISTS `t_picture_sort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_picture_sort` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `file_uid` varchar(32) DEFAULT NULL COMMENT '文件uid',
  `sort_name` varchar(255) DEFAULT NULL COMMENT '分类名',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `parent_uid` varchar(32) DEFAULT NULL COMMENT '父级分类uid',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='图片分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_picture_sort`
--

LOCK TABLES `t_picture_sort` WRITE;
/*!40000 ALTER TABLE `t_picture_sort` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_picture_sort` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role`
--

DROP TABLE IF EXISTS `t_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_role` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `role_name` varchar(255) NOT NULL COMMENT '角色名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role`
--

LOCK TABLES `t_role` WRITE;
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` (`uid`, `role_name`, `create_time`, `update_time`, `status`) VALUES ('352af55485b0198631adc4f3f589bb3f','管理员','0000-00-00 00:00:00','0000-00-00 00:00:00',1),('fc5fea59ae7309773f8f70074a27eec4','游客','0000-00-00 00:00:00','0000-00-00 00:00:00',1),('ffca2113713df757e0293c6dfd3b4e32','普通用户','0000-00-00 00:00:00','0000-00-00 00:00:00',1);
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_tag`
--

DROP TABLE IF EXISTS `t_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_tag` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `content` varchar(1000) DEFAULT NULL COMMENT '标签内容',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `click_count` int DEFAULT '0' COMMENT '标签简介',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='标签表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_tag`
--

LOCK TABLES `t_tag` WRITE;
/*!40000 ALTER TABLE `t_tag` DISABLE KEYS */;
INSERT INTO `t_tag` (`uid`, `content`, `status`, `click_count`, `create_time`, `update_time`) VALUES ('04612e33964d45d19fa7f4fd61acd807','github',1,2,'2018-09-21 12:12:52','2018-09-21 12:12:52'),('25228e8e5d4847d39a4b1466e7403e69','Spring Cloud',1,1,'2018-09-21 12:09:50','2018-09-21 12:09:50'),('53c5a0f3142e4f54820315936f78383b','Spring Boot',1,1,'2018-09-21 12:11:06','2018-09-21 12:11:06'),('7e0e93ea6cdb44ae92e58f48e6496ed7','Java',1,1,'2018-09-21 12:12:52','2018-09-21 12:12:52'),('7efee6f74d594d25928ba86bc7adee28','游戏',1,1,'2018-09-20 06:51:39','2018-09-20 06:51:39'),('9da13eb143f54c6bb70ecbd5212bde69','原神',0,1,'2018-09-21 12:13:51','2018-09-21 12:13:51'),('a9a747d944c24845815356f72723ef8e','前端开发',1,2,'2018-09-20 06:51:39','2018-09-20 06:51:39'),('ca928e8718654aa5a802e2f69277b137','生活琐事',1,2,'2018-09-21 12:13:41','2018-09-21 12:13:41'),('e2c7913050cf4ab9aa92902316aaf075','校园生活',1,1,'2018-09-21 12:13:51','2018-09-21 12:13:51'),('f18448a27f6e4320b40cfe51cb9b0a66','李康勇',0,1,'2018-09-21 12:13:41','2018-09-21 12:13:41'),('f90d3c2fd9434302951130e897a89164','Vue',1,1,'2018-09-21 12:12:52','2018-09-21 12:12:52');
/*!40000 ALTER TABLE `t_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `user_name` varchar(255) NOT NULL COMMENT '用户名',
  `pass_word` varchar(32) NOT NULL COMMENT '密码',
  `salt` varchar(36) NOT NULL COMMENT '加密盐',
  `nick_name` varchar(32) NOT NULL COMMENT '昵称',
  `gender` tinyint unsigned DEFAULT NULL COMMENT '性别(1:男2:女)',
  `picture_uid` varchar(255) DEFAULT NULL COMMENT '个人头像 图片uid',
  `email` varchar(255) NOT NULL COMMENT '邮箱',
  `is_email_enabled` tinyint(1) DEFAULT '1' COMMENT '邮箱是否激活（0:不是 1：是）',
  `birthday` date DEFAULT NULL COMMENT '出生年月日',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机',
  `is_mobile_enabled` tinyint(1) DEFAULT '1' COMMENT '手机是否激活（0:不是 1：是）',
  `summary` varchar(200) DEFAULT NULL COMMENT '自我简介最多150字',
  `login_count` int unsigned DEFAULT '0' COMMENT '登录次数',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) DEFAULT '127.0.0.1' COMMENT '最后登录IP',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` (`uid`, `user_name`, `pass_word`, `salt`, `nick_name`, `gender`, `picture_uid`, `email`, `is_email_enabled`, `birthday`, `mobile`, `is_mobile_enabled`, `summary`, `login_count`, `last_login_time`, `last_login_ip`, `status`, `create_time`, `update_time`) VALUES ('0b537f5183524756bd8e52d80227d355','ddasdx','21232f297a57a5a743894a0e4a801fc3','','cxz',1,'测试头像','xzx19950624@qq.com',1,'2018-09-20','1597531973',1,'测试表情',0,'2018-09-20 14:49:00','127.0.0.1',1,'2023-11-30 07:26:36','2023-11-30 07:26:44'),('21f4954be377449481b6864383693747','zcx','21232f297a57a5a743894a0e4a801fc3','','cxvvb',1,'y','xzx19950624@qq.com',1,'2018-09-20','1597531973',1,'测试表情',0,'2018-09-20 14:49:00','127.0.0.1',1,'2023-11-30 07:26:36','2023-11-30 07:26:44'),('5821462bc29a4570ad80e87f3aa3f02d','admin','21232f297a57a5a743894a0e4a801fc3','','PPLAX',1,'测试头像','xzx19950624@qq.com',1,'2018-09-20','1597531973',1,'测试表情',1,'2018-09-20 14:49:00','127.0.0.1',1,'0000-00-00 00:00:00','0000-00-00 00:00:00'),('7a0233eb493345d0831b34d3a9c1b4e8','zxc','21232f297a57a5a743894a0e4a801fc3','','zcx',1,'5','xzx19950624@qq.com',1,'2018-09-20','1597531973',1,'测试表情',0,'2018-09-20 14:49:00','127.0.0.1',1,'2023-11-30 07:26:36','2023-11-30 07:26:44'),('7c5bb9f3ccd54fc6a9804955cf6488ad','asd','21232f297a57a5a743894a0e4a801fc3','','asd',1,'测试头像','xzx19950624@qq.com',1,'2018-09-20','1597531973',1,'测试表情',0,'2018-09-20 14:49:00','127.0.0.1',1,'2023-11-30 07:26:36','2023-11-30 07:26:44'),('94437dc0211745449649b283840ab2a5','zhangsan','21232f297a57a5a743894a0e4a801fc3','','三哥',1,'2','xzx19950624@qq.com',1,'2018-09-20','1597531973',1,'测试表情',0,'2018-09-20 14:49:00','127.0.0.1',1,'2023-11-30 07:26:36','2023-11-30 07:26:44'),('a0c2bb72c1934ab08455f564794d09c1','lzicu','21232f297a57a5a743894a0e4a801fc3','','dsazxc',1,'测试头像4','xzx19950624@qq.com',1,'2018-09-20','1597531973',1,'测试表情',0,'2018-09-20 14:49:00','127.0.0.1',1,'2023-11-30 07:26:36','2023-11-30 07:26:44'),('a2fc23c7668a4b6386b75584d72498a5','lucky','21232f297a57a5a743894a0e4a801fc3','','李康勇',1,'测试头像1','xzx19950624@qq.com',1,'2018-09-20','1597531973',1,'测试表情',0,'2018-09-20 14:49:00','127.0.0.1',1,'2023-11-30 07:26:36','2023-11-30 07:26:44'),('e8081bd801c0433295e29f052f5f6bd5','asd','21232f297a57a5a743894a0e4a801fc3','','gdsf',1,'y','xzx19950624@qq.com',1,'2018-09-20','1597531973',1,'测试表情',0,'2018-09-20 14:49:00','127.0.0.1',1,'2023-11-30 07:26:36','2023-11-30 07:26:44'),('ee515eca338c4d358baea8d2c9930a45','yuanhulong','21232f297a57a5a743894a0e4a801fc3','','怨虎龙',1,'测试头像3','xzx19950624@qq.com',1,'2018-09-20','1597531973',1,'测试表情',0,'2018-09-20 14:49:00','127.0.0.1',1,'2023-11-30 07:26:36','2023-11-30 07:26:44');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_visitor`
--

DROP TABLE IF EXISTS `t_visitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_visitor` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `email` varchar(255) NOT NULL COMMENT '邮箱',
  `login_count` int unsigned DEFAULT '0' COMMENT '登录次数',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) DEFAULT '127.0.0.1' COMMENT '最后登录IP',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='游客表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_visitor`
--

LOCK TABLES `t_visitor` WRITE;
/*!40000 ALTER TABLE `t_visitor` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_visitor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-16 14:48:31
