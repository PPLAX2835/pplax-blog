-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
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
  `tag_uids` varchar(255) DEFAULT NULL COMMENT '标签uid',
  `click_count` int DEFAULT '0' COMMENT '博客点击数',
  `collect_count` int DEFAULT '0' COMMENT '博客收藏数',
  `cover_image_uid` varchar(255) DEFAULT NULL COMMENT '标题图片uid',
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
INSERT INTO `t_blog` (`uid`, `title`, `summary`, `blog_content_uid`, `tag_uids`, `click_count`, `collect_count`, `cover_image_uid`, `status`, `is_publish`, `user_uid`, `is_original`, `articles_part`, `blog_sort_uid`, `level`, `create_time`, `update_time`) VALUES ('211fb300fe7a4a20bfda396a9450b117','后端开发简历咋整呢',NULL,'771690201d694446b653b9f6a9eabbaf',NULL,0,0,NULL,1,'1',NULL,1,NULL,'3cf89eab5cf2484f8023f088dd3f3cd5',0,'2024-01-15 07:54:25','2024-01-15 07:54:25'),('b7dbc212bf304422be9fa22c3f2cf1a2','年轻人的第一款怪猎',NULL,'00ef773b1cb542bb8e4d262e5f23e37c',NULL,0,0,NULL,1,'1',NULL,1,NULL,'218b8f96728541d3847099737c3ef947',0,'2024-01-15 07:48:52','2024-01-15 07:51:10'),('dcf8f268a51143fb988384e7559e5650','黑龙登龙点讲解',NULL,'afa22a4ffa7e41de99863f16ab3e5c97',NULL,0,0,NULL,1,'1',NULL,1,NULL,'218b8f96728541d3847099737c3ef947',0,'2024-01-15 07:52:42','2024-01-15 07:52:42'),('f4bd17e13924496889a2eb16ae01ba30','这是一个标题',NULL,'9ddd597bb3ff4426b84f35439f553e94',NULL,0,0,NULL,1,'1',NULL,1,NULL,NULL,0,'2024-01-15 07:48:52','2024-01-15 07:48:52');
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
  `icon` varchar(128) DEFAULT NULL COMMENT '分类图标',
  `order` int DEFAULT NULL COMMENT '排列位次',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `click_count` int DEFAULT '0' COMMENT '点击数',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='博客分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_blog_sort`
--

LOCK TABLES `t_blog_sort` WRITE;
/*!40000 ALTER TABLE `t_blog_sort` DISABLE KEYS */;
INSERT INTO `t_blog_sort` (`uid`, `sort_name`, `summary`, `content`, `icon`, `order`, `create_time`, `update_time`, `status`, `click_count`) VALUES ('093d8bdd01c84890a928e923d5c235fe','时光轴','时光轴的简介','时光轴的简介',NULL,NULL,'2018-09-24 09:14:59','2024-02-19 09:48:46',1,0),('2c93dfab0e754006866f8ed486923a41','慢生活','慢生活的简介','慢生活的简介',NULL,NULL,'2018-09-24 08:29:33','2024-02-19 09:48:46',1,0),('2fc443ca683bc93248873dcac0ccda9d','考研','现在考研好难哦','现在考研好难哦',NULL,NULL,'2024-01-16 14:48:17','2024-02-19 09:48:46',1,0),('3ae899e993b744c99fb78dccafac8e66','游戏','游戏的简介','游戏的简介',NULL,NULL,'2023-11-29 11:18:55','2024-02-19 09:48:46',1,0),('3cf89eab5cf2484f8023f088dd3f3cd5','编程','编程 简介','编程 简介',NULL,NULL,'2023-11-30 03:34:12','2024-02-19 09:48:46',1,0),('fbef6ff4be704781a0e0c4aeb7a2b64b','美食',NULL,NULL,NULL,NULL,'2023-11-30 07:21:42','2023-11-30 07:21:42',1,0);
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
-- Table structure for table `t_file_storage`
--

DROP TABLE IF EXISTS `t_file_storage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_file_storage` (
  `uid` varchar(36) NOT NULL COMMENT '唯一uid',
  `user_uid` varchar(32) DEFAULT NULL COMMENT '用户uid',
  `original_name` varchar(255) DEFAULT NULL COMMENT '资源原始名称',
  `file_name` varchar(255) DEFAULT NULL COMMENT '资源名称',
  `suffix` varchar(20) DEFAULT NULL COMMENT '后缀名',
  `file_path` varchar(255) DEFAULT NULL COMMENT '文件地址',
  `file_size` bigint DEFAULT '0' COMMENT '文件大小，单位bit',
  `is_directory` tinyint(1) DEFAULT NULL COMMENT '是否是目录',
  `is_image` tinyint(1) DEFAULT NULL COMMENT '是否图片',
  `storage_mode` varchar(30) DEFAULT 'LOCAL_STORAGE' COMMENT '文件存储的模式，(七牛云、Minio、本地、阿里云OSS什么的)',
  `file_url` varchar(255) DEFAULT NULL COMMENT '资源路径',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='文件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_file_storage`
--

LOCK TABLES `t_file_storage` WRITE;
/*!40000 ALTER TABLE `t_file_storage` DISABLE KEYS */;
INSERT INTO `t_file_storage` (`uid`, `user_uid`, `original_name`, `file_name`, `suffix`, `file_path`, `file_size`, `is_directory`, `is_image`, `storage_mode`, `file_url`, `status`, `create_time`, `update_time`) VALUES ('005ddd14d503f3d47c980d49b1060408','a2fc23c7668a4b6386b75584d72498a5','屏幕截图 2024-02-05 193535.png','1707298973711.png','png','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',6152707,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1707298973711.png',1,'2024-02-07 09:42:54','2024-02-07 09:42:54'),('19484db6ab082d4fed9dc85b07f39260','a2fc23c7668a4b6386b75584d72498a5','20200521182613_58567.jpg','1707289182688.jpg','jpg','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',56657,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1707289182688.jpg',1,'2024-02-07 06:59:43','2024-02-07 09:13:56'),('3220fc7fcb647a2360e900cbed6c12d2','a2fc23c7668a4b6386b75584d72498a5','屏幕截图 2024-02-05 193535.png','1707298904952.png','png','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',6152707,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1707298904952.png',0,'2024-02-07 09:41:45','2024-02-08 05:34:59'),('48d1b4c3b2b0fc1e9630f936932352ea','a2fc23c7668a4b6386b75584d72498a5','屏幕截图 2024-02-05 193535.png','1707298979532.png','png','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',6152707,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1707298979532.png',1,'2024-02-07 09:43:00','2024-02-07 09:43:00'),('562f0ad498058921e3edf804abc5a4e1','a2fc23c7668a4b6386b75584d72498a5','屏幕截图 2024-02-05 193535.png','1707298427529.png','png','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',6152707,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1707298427529.png',1,'2024-02-07 09:33:48','2024-02-07 09:33:48'),('72b52050e52b6638662cb631eca31de7','f8616ce5aa0c8eeacfa0d997dbdbe0d3','1707289182688.jpg','1707379094148.jpg','jpg','/users/f8616ce5aa0c8eeacfa0d997dbdbe0d3/avatar/',8106,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/f8616ce5aa0c8eeacfa0d997dbdbe0d3/avatar/1707379094148.jpg',1,'2024-02-08 07:58:14','2024-02-08 07:58:14'),('7eb3944994ab25c02acf217066e25c07','103e0baa6d964e3bbb3e3deda94fc0e3','1707289182688.jpg','1707379097298.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',8106,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1707379097298.jpg',1,'2024-02-08 07:58:17','2024-02-08 07:58:17'),('8d37dec9b0302b43206b57a43d76ae41','103e0baa6d964e3bbb3e3deda94fc0e3','屏幕截图 2024-02-05 193535.png','1707360380564.png','png','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',6152707,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1707360380564.png',1,'2024-02-08 02:46:21','2024-02-08 02:46:21'),('8da9f7c3755e85258b078de7a23a84f7','a2fc23c7668a4b6386b75584d72498a5','1707289182688.jpg','1707379086947.jpg','jpg','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',8106,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1707379086947.jpg',1,'2024-02-08 07:58:07','2024-02-08 07:58:07'),('c1383db53b9e418cbf2eccf1ee48aa28','ee515eca338c4d358baea8d2c9930a45','1707289182688.jpg','1707377164750.jpg','jpg','/users/ee515eca338c4d358baea8d2c9930a45/avatar/',8106,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/ee515eca338c4d358baea8d2c9930a45/avatar/1707377164750.jpg',1,'2024-02-08 07:26:05','2024-02-08 07:26:05'),('c522a4ade8629bad6361fd8793502ce5','a2fc23c7668a4b6386b75584d72498a5','屏幕截图 2024-02-05 193535.png','1707360431035.png','png','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',6152707,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1707360431035.png',1,'2024-02-08 02:47:11','2024-02-08 02:47:11'),('e5d8e6cc711ea394bafabb79475315f5','a2fc23c7668a4b6386b75584d72498a5','屏幕截图 2024-02-05 193535.png','1707297628327.png','png','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',6152707,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1707297628327.png',1,'2024-02-07 09:20:28','2024-02-07 09:20:28'),('ea60e351588968fa504e0265abee8df3','ee515eca338c4d358baea8d2c9930a45','1707289182688.jpg','1707379090796.jpg','jpg','/users/ee515eca338c4d358baea8d2c9930a45/avatar/',8106,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/ee515eca338c4d358baea8d2c9930a45/avatar/1707379090796.jpg',1,'2024-02-08 07:58:11','2024-02-08 07:58:11');
/*!40000 ALTER TABLE `t_file_storage` ENABLE KEYS */;
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
-- Table structure for table `t_menu`
--

DROP TABLE IF EXISTS `t_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_menu` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `parent_uid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上级资源ID',
  `type` varchar(32) DEFAULT NULL COMMENT '类型 menu、button',
  `route` varchar(255) DEFAULT NULL COMMENT '路由',
  `endpoint` varchar(255) DEFAULT NULL COMMENT 'restful端口端点',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '资源名称',
  `level` int DEFAULT NULL COMMENT '资源级别',
  `sort_no` int DEFAULT NULL COMMENT '排序',
  `icon` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '资源图标',
  `remarks` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `hidden` tinyint(1) DEFAULT NULL COMMENT '是否隐藏',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='菜单表 ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_menu`
--

LOCK TABLES `t_menu` WRITE;
/*!40000 ALTER TABLE `t_menu` DISABLE KEYS */;
INSERT INTO `t_menu` (`uid`, `parent_uid`, `type`, `route`, `endpoint`, `title`, `level`, `sort_no`, `icon`, `remarks`, `hidden`, `create_time`, `update_time`, `status`) VALUES ('17351863092649fd94c42820447b57d7','79cede9382df47a8914ef5697b38d901','button',NULL,'PUT:/api/admin/article/{uid}/promote','置顶文章',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-02-22 02:52:41',1),('2780d3f5249b44e0b286b54023b13fcf',NULL,'menu','/router/user',NULL,'用户管理',1,NULL,'el-icon-user',NULL,0,'2024-02-22 02:54:51','2024-02-22 03:03:18',1),('45a5eb42e3404270ad8328ae834e3600','7c68ef5629244eb0901738596d2418a6','button',NULL,'GET:/api/admin/user/{uid}/userInfo','用户信息',3,NULL,NULL,NULL,0,'2024-02-22 02:57:17','2024-02-22 03:04:19',1),('5408004e1fd2405db45cb98c42e29d0c',NULL,'menu','/router/article',NULL,'文章管理',1,NULL,'el-icon-notebook-2',NULL,0,'2024-02-19 11:00:31','2024-02-19 11:00:31',1),('5b284b55ab7747f793498def8c776241','79cede9382df47a8914ef5697b38d901','button',NULL,'DELETE:/api/admin/article/{uid}','删除',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-02-22 02:52:42',1),('79cede9382df47a8914ef5697b38d901','5408004e1fd2405db45cb98c42e29d0c','menu','/router/article/index',NULL,'文章管理',2,NULL,'el-icon-notebook-2',NULL,0,'2024-02-19 11:03:02','2024-02-22 03:27:10',1),('7c68ef5629244eb0901738596d2418a6','2780d3f5249b44e0b286b54023b13fcf','menu','/router/user/index',NULL,'用户管理',2,NULL,'el-icon-user',NULL,0,'2024-02-22 02:57:17','2024-02-22 03:03:18',1),('82c86c8c26974f6cb8e1faa360c240d3','79cede9382df47a8914ef5697b38d901','button',NULL,'GET:/api/admin/article/list','列表',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-02-22 02:52:41',1),('88b35441dd7344b9adcf56556deda882','79cede9382df47a8914ef5697b38d901','button',NULL,'POST:/api/admin/article','添加',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-02-22 02:52:41',1),('a2f26ceaa846401f912bcc378b96cadd','79cede9382df47a8914ef5697b38d901','button',NULL,'GET:/api/admin/article/{uid}','详情',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-02-22 02:52:41',1),('ab92729e562d490fbc05a35fc83db90d',NULL,'menu','/router/home',NULL,'首页',1,NULL,'el-icon-s-home',NULL,0,'2024-02-19 06:04:36','2024-02-19 10:57:54',1),('b08d46d599e442a8829c7185e8ba8a83','79cede9382df47a8914ef5697b38d901','button',NULL,'PUT:/api/admin/article/{uid}','修改',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-02-22 02:52:41',1);
/*!40000 ALTER TABLE `t_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role`
--

DROP TABLE IF EXISTS `t_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_role` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `menu_uids` text COMMENT '菜单uid',
  `role_name` varchar(255) NOT NULL COMMENT '角色名',
  `summary` varchar(255) DEFAULT NULL COMMENT '角色介绍',
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
INSERT INTO `t_role` (`uid`, `menu_uids`, `role_name`, `summary`, `create_time`, `update_time`, `status`) VALUES ('352af55485b0198631adc4f3f589bb3f',NULL,'管理员',NULL,'0000-00-00 00:00:00','2024-02-08 05:50:19',1),('cd853c7b475723fb4457aebe30fd39ac','7c68ef5629244eb0901738596d2418a6,2780d3f5249b44e0b286b54023b13fcf,ab92729e562d490fbc05a35fc83db90d,79cede9382df47a8914ef5697b38d901,5408004e1fd2405db45cb98c42e29d0c,17351863092649fd94c42820447b57d7,45a5eb42e3404270ad8328ae834e3600,5b284b55ab7747f793498def8c776241,82c86c8c26974f6cb8e1faa360c240d3,88b35441dd7344b9adcf56556deda882,a2f26ceaa846401f912bcc378b96cadd,b08d46d599e442a8829c7185e8ba8a83','超级管理员',NULL,'2024-02-03 07:14:39','2024-02-22 03:07:13',1),('ffca2113713df757e0293c6dfd3b4e32',NULL,'普通用户',NULL,'0000-00-00 00:00:00','2024-02-08 05:50:19',1);
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
  `name` varchar(255) DEFAULT NULL COMMENT '标签名',
  `click_count` int DEFAULT '0' COMMENT '点击量',
  `level` int DEFAULT '0' COMMENT '推荐等级',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
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
INSERT INTO `t_tag` (`uid`, `name`, `click_count`, `level`, `status`, `create_time`, `update_time`) VALUES ('04612e33964d45d19fa7f4fd61acd807','github',2,0,1,'2018-09-21 12:12:52','2018-09-21 12:12:52'),('25228e8e5d4847d39a4b1466e7403e69','Spring Cloud',1,0,1,'2018-09-21 12:09:50','2018-09-21 12:09:50'),('53c5a0f3142e4f54820315936f78383b','Spring Boot',1,0,1,'2018-09-21 12:11:06','2018-09-21 12:11:06'),('7e0e93ea6cdb44ae92e58f48e6496ed7','Java',1,0,1,'2018-09-21 12:12:52','2018-09-21 12:12:52'),('7efee6f74d594d25928ba86bc7adee28','游戏',1,0,1,'2018-09-20 06:51:39','2018-09-20 06:51:39'),('9da13eb143f54c6bb70ecbd5212bde69','原神',1,0,0,'2018-09-21 12:13:51','2018-09-21 12:13:51'),('a9a747d944c24845815356f72723ef8e','前端开发',2,0,1,'2018-09-20 06:51:39','2018-09-20 06:51:39'),('ca928e8718654aa5a802e2f69277b137','生活琐事',2,0,1,'2018-09-21 12:13:41','2018-09-21 12:13:41'),('e2c7913050cf4ab9aa92902316aaf075','校园生活',1,0,1,'2018-09-21 12:13:51','2018-09-21 12:13:51'),('f18448a27f6e4320b40cfe51cb9b0a66','李康勇',1,0,0,'2018-09-21 12:13:41','2018-09-21 12:13:41'),('f90d3c2fd9434302951130e897a89164','Vue',1,0,1,'2018-09-21 12:12:52','2018-09-21 12:12:52');
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
  `role_uid` varchar(32) DEFAULT NULL COMMENT '角色uid，用,隔开',
  `user_info_uid` varchar(32) NOT NULL COMMENT '用户信息uid',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(36) NOT NULL COMMENT '加密盐',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `is_email_activated` tinyint(1) DEFAULT '0' COMMENT '邮箱是否激活（0:不是 1：是）',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机',
  `is_mobile_activated` tinyint(1) DEFAULT '0' COMMENT '手机是否激活（0:不是 1：是）',
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
INSERT INTO `t_user` (`uid`, `role_uid`, `user_info_uid`, `username`, `password`, `salt`, `email`, `is_email_activated`, `mobile`, `is_mobile_activated`, `login_count`, `last_login_time`, `last_login_ip`, `status`, `create_time`, `update_time`) VALUES ('103e0baa6d964e3bbb3e3deda94fc0e3','ffca2113713df757e0293c6dfd3b4e32','A4A7B8FBB8E9623FA72556F13A97ED5F','lucky','$2a$10$HuDCsbGXZTbBgVjXryfHeeObIJHz926y9qQTb/z9GWUFHp.9d5o4i','testSalt','123456@qq.com',1,'18819439264',1,216,'2024-02-09 12:26:03','127.0.0.1',1,'2024-01-26 03:36:37','2024-01-26 03:58:08'),('a2fc23c7668a4b6386b75584d72498a5','ffca2113713df757e0293c6dfd3b4e32','A7C9AACDC396292E3825639379041442','testuser','$2a$10$HuDCsbGXZTbBgVjXryfHeeObIJHz926y9qQTb/z9GWUFHp.9d5o4i','testSalt','123466@qq.com',1,'18819439265',1,1,'2024-02-02 12:55:12','0:0:0:0:0:0:0:1',1,'2024-01-26 03:36:37','2024-01-26 03:58:08'),('ee515eca338c4d358baea8d2c9930a45','ffca2113713df757e0293c6dfd3b4e32','D218CD2BB34F0A91565390458A20E871','123321','$2a$10$HuDCsbGXZTbBgVjXryfHeeObIJHz926y9qQTb/z9GWUFHp.9d5o4i','testSalt','123476@qq.com',1,'18819439266',1,1,'2024-02-08 15:24:29','127.0.0.1',1,'2024-01-26 03:36:37','2024-01-26 03:58:08'),('f8616ce5aa0c8eeacfa0d997dbdbe0d3','cd853c7b475723fb4457aebe30fd39ac','f6a7dc9f2df3544912c464e7adb9ce32','PPLAX','$2a$10$HuDCsbGXZTbBgVjXryfHeeObIJHz926y9qQTb/z9GWUFHp.9d5o4i','testSalt','123486@qq.com',1,NULL,1,83,'2024-02-22 12:49:48','127.0.0.1',1,'2024-02-03 07:17:56','2024-02-21 10:03:59');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_info`
--

DROP TABLE IF EXISTS `t_user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_info` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `nickname` varchar(32) NOT NULL COMMENT '昵称',
  `gender` tinyint unsigned DEFAULT NULL COMMENT '性别(1:男2:女)',
  `avatar_picture_uid` varchar(255) DEFAULT NULL COMMENT '个人头像 图片uid',
  `space_background_picture_uid` varchar(255) DEFAULT NULL COMMENT '空间背景 图片uid',
  `birthday` date DEFAULT NULL COMMENT '出生年月日',
  `summary` varchar(200) DEFAULT NULL COMMENT '自我简介最多150字',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_info`
--

LOCK TABLES `t_user_info` WRITE;
/*!40000 ALTER TABLE `t_user_info` DISABLE KEYS */;
INSERT INTO `t_user_info` (`uid`, `nickname`, `gender`, `avatar_picture_uid`, `space_background_picture_uid`, `birthday`, `summary`, `status`, `create_time`, `update_time`) VALUES ('A4A7B8FBB8E9623FA72556F13A97ED5F','测试用户',NULL,'7eb3944994ab25c02acf217066e25c07',NULL,NULL,NULL,1,'2024-01-26 03:32:47','2024-02-08 07:58:19'),('A7C9AACDC396292E3825639379041442','李康勇',1,'8da9f7c3755e85258b078de7a23a84f7',NULL,'2018-09-20','测试表情',1,'2024-01-26 03:32:47','2024-02-08 07:58:08'),('D218CD2BB34F0A91565390458A20E871','怨虎龙',1,'ea60e351588968fa504e0265abee8df3',NULL,'2018-09-20','测试表情',1,'2024-01-26 03:32:47','2024-02-08 07:58:12'),('f6a7dc9f2df3544912c464e7adb9ce32','普拉克斯',1,'72b52050e52b6638662cb631eca31de7',NULL,'2024-02-03',NULL,1,'2024-02-03 07:17:03','2024-02-08 07:58:15');
/*!40000 ALTER TABLE `t_user_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-22 13:08:09
