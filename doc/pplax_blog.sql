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
INSERT INTO `t_feedback` (`uid`, `user_uid`, `content`, `status`, `create_time`, `update_time`) VALUES ('3b3f83c0af664338ab4be666fb97875a','7c5bb9f3ccd54fc6a9804955cf6488ad','你家厕所漏水了',1,'2023-11-30 07:52:16','2023-11-30 07:52:16'),('4a571b6b5ab346ad88b3b4319563f450','94437dc0211745449649b283840ab2a5','2+656',1,'2023-11-30 09:36:27','2023-11-30 09:36:27'),('ac537eb9d7c7476b871fd61a65d0a29e','7a0233eb493345d0831b34d3a9c1b4e8','这是一条反馈',1,'2023-11-30 07:52:16','2023-11-30 07:52:16'),('c5589ab67ae546c2b4dfe20065f45e86','a0c2bb72c1934ab08455f564794d09c1','do you like what you say?',1,'2023-11-30 07:52:16','2023-11-30 07:52:16'),('d0a7dc6f21164d18995ffe1353fde9ed','7c5bb9f3ccd54fc6a9804955cf6488ad','asdasd',1,'2023-11-30 09:37:16','2023-11-30 09:37:16'),('de4a57cc421a46039289cbd89a783f87','784c38743e93482ba79cd2e4075a59d8','来自河北的皮先生，你好',1,'2023-11-30 07:52:16','2024-03-08 10:59:17');
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
INSERT INTO `t_file_storage` (`uid`, `user_uid`, `original_name`, `file_name`, `suffix`, `file_path`, `file_size`, `is_directory`, `is_image`, `storage_mode`, `file_url`, `status`, `create_time`, `update_time`) VALUES ('005ddd14d503f3d47c980d49b1060408','a2fc23c7668a4b6386b75584d72498a5','屏幕截图 2024-02-05 193535.png','1707298973711.png','png','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',6152707,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1707298973711.png',1,'2024-02-07 09:42:54','2024-02-07 09:42:54'),('00bf834ccaf2590c0ca4cee79b4d6f5f','a2fc23c7668a4b6386b75584d72498a5','OIP-C.jpg','1709713122715.jpg','jpg','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1709713122715.jpg',1,'2024-03-06 08:18:43','2024-03-06 08:18:43'),('19484db6ab082d4fed9dc85b07f39260','a2fc23c7668a4b6386b75584d72498a5','20200521182613_58567.jpg','1707289182688.jpg','jpg','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',56657,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1707289182688.jpg',1,'2024-02-07 06:59:43','2024-02-07 09:13:56'),('1add1565e07dea4cbbb941ea658d9d16','a2fc23c7668a4b6386b75584d72498a5','1707379097298.jpg','1709715918639.jpg','jpg','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1709715918639.jpg',1,'2024-03-06 09:05:19','2024-03-06 09:05:19'),('1eb986d55893ffb815eb2e04adda100e','103e0baa6d964e3bbb3e3deda94fc0e3','1707379097298.jpg','1709715384666.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709715384666.jpg',1,'2024-03-06 08:56:25','2024-03-06 08:56:25'),('1faf2d2999967434a90538823d040c3c','103e0baa6d964e3bbb3e3deda94fc0e3','g.jpg','1709727649797.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',3179,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709727649797.jpg',1,'2024-03-06 12:20:50','2024-03-06 12:20:50'),('284fa35e3d6a3c17d8fefed959d16c8b','a2fc23c7668a4b6386b75584d72498a5','OIP-C.jpg','1709725172108.jpg','jpg','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1709725172108.jpg',1,'2024-03-06 11:39:32','2024-03-06 11:39:32'),('2b5d33b147cf32aca6cd87b2001c9b9e','103e0baa6d964e3bbb3e3deda94fc0e3','1707379097298.jpg','1709715241437.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709715241437.jpg',1,'2024-03-06 08:54:01','2024-03-06 08:54:01'),('2b9c0581b16d013e218d0faae0096b81','103e0baa6d964e3bbb3e3deda94fc0e3','1707379097298.jpg','1709715799273.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709715799273.jpg',1,'2024-03-06 09:03:19','2024-03-06 09:03:19'),('2dbb75de43b8541821fb58c021c962eb','103e0baa6d964e3bbb3e3deda94fc0e3','1707379097298.jpg','1709715699490.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709715699490.jpg',1,'2024-03-06 09:01:39','2024-03-06 09:01:39'),('2de5a625f7437c5c005cbce953fc0d1e','ee515eca338c4d358baea8d2c9930a45','1645512111007.jpg','1709727636806.jpg','jpg','/users/ee515eca338c4d358baea8d2c9930a45/avatar/',11909,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/ee515eca338c4d358baea8d2c9930a45/avatar/1709727636806.jpg',1,'2024-03-06 12:20:37','2024-03-06 12:20:37'),('3220fc7fcb647a2360e900cbed6c12d2','a2fc23c7668a4b6386b75584d72498a5','屏幕截图 2024-02-05 193535.png','1707298904952.png','png','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',6152707,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1707298904952.png',0,'2024-02-07 09:41:45','2024-02-08 05:34:59'),('3c5e37f19b14596eb8a17c064fc8e2d6','a2fc23c7668a4b6386b75584d72498a5','OIP-C.jpg','1709711535910.jpg','jpg','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1709711535910.jpg',1,'2024-03-06 07:52:16','2024-03-06 07:52:16'),('453c7bb2ae88f5c9624a78cbbf6f6da7','f8616ce5aa0c8eeacfa0d997dbdbe0d3','1707379097298.jpg','1709727552697.jpg','jpg','/users/f8616ce5aa0c8eeacfa0d997dbdbe0d3/avatar/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/f8616ce5aa0c8eeacfa0d997dbdbe0d3/avatar/1709727552697.jpg',1,'2024-03-06 12:19:13','2024-03-06 12:19:13'),('45d5adcac8ec944a1d8cd031c409a132','103e0baa6d964e3bbb3e3deda94fc0e3','1707379097298.jpg','1709724402461.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709724402461.jpg',1,'2024-03-06 11:26:42','2024-03-06 11:26:42'),('473daa8c5c076e27f1556918932c242f','a2fc23c7668a4b6386b75584d72498a5','20220801091938_9f3d5.thumb.400_0.jpg','1709810336326.jpg','jpg','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',12844,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1709810336326.jpg',1,'2024-03-07 11:18:56','2024-03-07 11:18:56'),('48d1b4c3b2b0fc1e9630f936932352ea','a2fc23c7668a4b6386b75584d72498a5','屏幕截图 2024-02-05 193535.png','1707298979532.png','png','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',6152707,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1707298979532.png',1,'2024-02-07 09:43:00','2024-02-07 09:43:00'),('492f163922eb4bef53d8c00dedbbdadb','103e0baa6d964e3bbb3e3deda94fc0e3','1707379097298.jpg','1709716275592.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709716275592.jpg',1,'2024-03-06 09:11:16','2024-03-06 09:11:16'),('493baada0b1496a02cd01355630febcc','103e0baa6d964e3bbb3e3deda94fc0e3','1707379097298.jpg','1709715846152.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709715846152.jpg',1,'2024-03-06 09:04:06','2024-03-06 09:04:06'),('4bab3c20286fe09824117b61103e297f','103e0baa6d964e3bbb3e3deda94fc0e3','OIP-C.jpg','1709714828723.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709714828723.jpg',1,'2024-03-06 08:47:09','2024-03-06 08:47:09'),('562f0ad498058921e3edf804abc5a4e1','a2fc23c7668a4b6386b75584d72498a5','屏幕截图 2024-02-05 193535.png','1707298427529.png','png','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',6152707,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1707298427529.png',1,'2024-02-07 09:33:48','2024-02-07 09:33:48'),('5e453ac8d0226f4e70f05b162b0ec034','103e0baa6d964e3bbb3e3deda94fc0e3','1707379097298.jpg','1709716262132.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709716262132.jpg',1,'2024-03-06 09:11:02','2024-03-06 09:11:02'),('64a319a35e2f1c0ba9465344bd9f699a','d288eec502414298a62298aba33a0441','20220626195023_f1e25.jpg','1709810345568.jpg','jpg','/users/d288eec502414298a62298aba33a0441/avatar/',33807,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/d288eec502414298a62298aba33a0441/avatar/1709810345568.jpg',1,'2024-03-07 11:19:06','2024-03-07 11:19:06'),('65ceeb00efc158fdd8cf0b4e8a07f6da','103e0baa6d964e3bbb3e3deda94fc0e3','OIP-C.jpg','1709715494864.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709715494864.jpg',1,'2024-03-06 08:58:15','2024-03-06 08:58:15'),('676677d86d137db5fe869fb85180031f','103e0baa6d964e3bbb3e3deda94fc0e3','OIP-C.jpg','1709715098789.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709715098789.jpg',1,'2024-03-06 08:51:39','2024-03-06 08:51:39'),('72b52050e52b6638662cb631eca31de7','f8616ce5aa0c8eeacfa0d997dbdbe0d3','1707289182688.jpg','1707379094148.jpg','jpg','/users/f8616ce5aa0c8eeacfa0d997dbdbe0d3/avatar/',8106,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/f8616ce5aa0c8eeacfa0d997dbdbe0d3/avatar/1707379094148.jpg',1,'2024-02-08 07:58:14','2024-02-08 07:58:14'),('7eb3944994ab25c02acf217066e25c07','103e0baa6d964e3bbb3e3deda94fc0e3','1707289182688.jpg','1707379097298.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',8106,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1707379097298.jpg',1,'2024-02-08 07:58:17','2024-02-08 07:58:17'),('7f9e4b9ca8542646764ec53a8e6a4422','103e0baa6d964e3bbb3e3deda94fc0e3','1707379097298.jpg','1709715215801.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709715215801.jpg',1,'2024-03-06 08:53:36','2024-03-06 08:53:36'),('8382c4f7fbb905c1f8d05896f45fa045','103e0baa6d964e3bbb3e3deda94fc0e3','OIP-C.jpg','1709715254022.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709715254022.jpg',1,'2024-03-06 08:54:14','2024-03-06 08:54:14'),('867cfb5272ad41386fb88c56e755a9c8','103e0baa6d964e3bbb3e3deda94fc0e3','g.jpg','1709727765115.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',3179,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709727765115.jpg',1,'2024-03-06 12:22:45','2024-03-06 12:22:45'),('8bd26c0593a7ad5401128501b35846d5','103e0baa6d964e3bbb3e3deda94fc0e3','OIP-C.jpg','1709715809369.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709715809369.jpg',1,'2024-03-06 09:03:29','2024-03-06 09:03:29'),('8d37dec9b0302b43206b57a43d76ae41','103e0baa6d964e3bbb3e3deda94fc0e3','屏幕截图 2024-02-05 193535.png','1707360380564.png','png','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',6152707,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1707360380564.png',1,'2024-02-08 02:46:21','2024-02-08 02:46:21'),('8da9f7c3755e85258b078de7a23a84f7','a2fc23c7668a4b6386b75584d72498a5','1707289182688.jpg','1707379086947.jpg','jpg','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',8106,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1707379086947.jpg',1,'2024-02-08 07:58:07','2024-02-08 07:58:07'),('90a20b3a675294d1ccbf331433bde782','1217367d649c4e99abedf53cf7aadc2a','OIP-C.jpg','1709803942400.jpg','jpg','/users/1217367d649c4e99abedf53cf7aadc2a/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/1217367d649c4e99abedf53cf7aadc2a/avatar/1709803942400.jpg',1,'2024-03-07 09:32:22','2024-03-07 09:32:22'),('9154269db96a63a318fa5d20fba45889','103e0baa6d964e3bbb3e3deda94fc0e3','1707379097298.jpg','1709727529050.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709727529050.jpg',1,'2024-03-06 12:18:49','2024-03-06 12:18:49'),('998b7d5a3eef41fd9e661aebec3531f7','d288eec502414298a62298aba33a0441','1645512111007.jpg','1709809883738.jpg','jpg','/users/d288eec502414298a62298aba33a0441/avatar/',11909,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/d288eec502414298a62298aba33a0441/avatar/1709809883738.jpg',1,'2024-03-07 11:11:24','2024-03-07 11:11:24'),('99bdbe6e198c78218771cf845f5ebd95','a2fc23c7668a4b6386b75584d72498a5','OIP-C.jpg','1709711632328.jpg','jpg','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1709711632328.jpg',1,'2024-03-06 07:53:52','2024-03-06 07:53:52'),('ae128ac0f761106e1368b5a724a739aa','103e0baa6d964e3bbb3e3deda94fc0e3','OIP-C.jpg','1709716117337.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709716117337.jpg',1,'2024-03-06 09:08:37','2024-03-06 09:08:37'),('b771c45d35f1f7e3441890c4690b8224','9f5ba3afd10e41c7a5ada60d0f3e4cd7','1707379097298.jpg','1709810198887.jpg','jpg','/users/9f5ba3afd10e41c7a5ada60d0f3e4cd7/avatar/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/9f5ba3afd10e41c7a5ada60d0f3e4cd7/avatar/1709810198887.jpg',1,'2024-03-07 11:16:39','2024-03-07 11:16:39'),('b9e27628e3c6b9e588a37297e10a2c16','a2fc23c7668a4b6386b75584d72498a5','OIP-C.jpg','1709713909771.jpg','jpg','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1709713909771.jpg',1,'2024-03-06 08:31:50','2024-03-06 08:31:50'),('c1383db53b9e418cbf2eccf1ee48aa28','ee515eca338c4d358baea8d2c9930a45','1707289182688.jpg','1707377164750.jpg','jpg','/users/ee515eca338c4d358baea8d2c9930a45/avatar/',8106,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/ee515eca338c4d358baea8d2c9930a45/avatar/1707377164750.jpg',1,'2024-02-08 07:26:05','2024-02-08 07:26:05'),('c522a4ade8629bad6361fd8793502ce5','a2fc23c7668a4b6386b75584d72498a5','屏幕截图 2024-02-05 193535.png','1707360431035.png','png','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',6152707,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1707360431035.png',1,'2024-02-08 02:47:11','2024-02-08 02:47:11'),('c5f109f4fd8433117d305c7301086244','ee515eca338c4d358baea8d2c9930a45','OIP-C.jpg','1709725398535.jpg','jpg','/users/ee515eca338c4d358baea8d2c9930a45/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/ee515eca338c4d358baea8d2c9930a45/avatar/1709725398535.jpg',1,'2024-03-06 11:43:19','2024-03-06 11:43:19'),('cbf11e0745aaf096b85344bd81d7bfe4','a2fc23c7668a4b6386b75584d72498a5','OIP-C.jpg','1709713907377.jpg','jpg','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1709713907377.jpg',1,'2024-03-06 08:31:47','2024-03-06 08:31:47'),('da780523034a0b6e861b936922db85c2','a2fc23c7668a4b6386b75584d72498a5','OIP-C.jpg','1709727538907.jpg','jpg','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1709727538907.jpg',1,'2024-03-06 12:18:59','2024-03-06 12:18:59'),('dab82db927c34cb003661966f3f76788','a2fc23c7668a4b6386b75584d72498a5','OIP-C.jpg','1709712111356.jpg','jpg','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1709712111356.jpg',1,'2024-03-06 08:01:51','2024-03-06 08:01:51'),('dc667dd4a65b6aa7494010762d5ec4e4','103e0baa6d964e3bbb3e3deda94fc0e3','OIP-C.jpg','1709715709155.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709715709155.jpg',1,'2024-03-06 09:01:49','2024-03-06 09:01:49'),('dcf04aa257cdc863cfa1261c29e2f4c9','a2fc23c7668a4b6386b75584d72498a5','OIP-C.jpg','1709711514562.jpg','jpg','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1709711514562.jpg',1,'2024-03-06 07:51:55','2024-03-06 07:51:55'),('e2a4031ddb8e55f6b81c69b75ef531d1','103e0baa6d964e3bbb3e3deda94fc0e3','OIP-C.jpg','1709722604293.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709722604293.jpg',1,'2024-03-06 10:56:44','2024-03-06 10:56:44'),('e5d8e6cc711ea394bafabb79475315f5','a2fc23c7668a4b6386b75584d72498a5','屏幕截图 2024-02-05 193535.png','1707297628327.png','png','/users/a2fc23c7668a4b6386b75584d72498a5/avatar/',6152707,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/a2fc23c7668a4b6386b75584d72498a5/avatar/1707297628327.png',1,'2024-02-07 09:20:28','2024-02-07 09:20:28'),('e795b0cebcbbc29fcc6cbbddd976702e','103e0baa6d964e3bbb3e3deda94fc0e3','1707379097298.jpg','1709716228294.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709716228294.jpg',1,'2024-03-06 09:10:28','2024-03-06 09:10:28'),('ea60e351588968fa504e0265abee8df3','ee515eca338c4d358baea8d2c9930a45','1707289182688.jpg','1707379090796.jpg','jpg','/users/ee515eca338c4d358baea8d2c9930a45/avatar/',8106,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/ee515eca338c4d358baea8d2c9930a45/avatar/1707379090796.jpg',1,'2024-02-08 07:58:11','2024-02-08 07:58:11'),('f5b9ea6bc873ce834b3debd6c3257c18','784c38743e93482ba79cd2e4075a59d8','R-C.jpg','1709896089452.jpg','jpg','/users/784c38743e93482ba79cd2e4075a59d8/avatar/',128449,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/784c38743e93482ba79cd2e4075a59d8/avatar/1709896089452.jpg',1,'2024-03-08 11:08:09','2024-03-08 11:08:09'),('f7e5517693cdf028c7a01b0a78488fcd','ee515eca338c4d358baea8d2c9930a45','1707379097298.jpg','1709727543693.jpg','jpg','/users/ee515eca338c4d358baea8d2c9930a45/avatar/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/ee515eca338c4d358baea8d2c9930a45/avatar/1709727543693.jpg',1,'2024-03-06 12:19:04','2024-03-06 12:19:04'),('fb9c943f49d5db798d78019bf244b14a','103e0baa6d964e3bbb3e3deda94fc0e3','1645512111007.jpg','1709727758457.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',11909,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709727758457.jpg',1,'2024-03-06 12:22:38','2024-03-06 12:22:38'),('fceb98a1f48f79b4078fcf8e71485866','103e0baa6d964e3bbb3e3deda94fc0e3','OIP-C.jpg','1709716272573.jpg','jpg','/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/',10330,0,1,'minio','http://127.0.0.1:9002/pplax-blog/users/103e0baa6d964e3bbb3e3deda94fc0e3/avatar/1709716272573.jpg',1,'2024-03-06 09:11:13','2024-03-06 09:11:13');
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
INSERT INTO `t_menu` (`uid`, `parent_uid`, `type`, `route`, `endpoint`, `title`, `level`, `sort_no`, `icon`, `remarks`, `hidden`, `create_time`, `update_time`, `status`) VALUES ('0de0a4cc2db44cd385d3adc55c81d244','7c68ef5629244eb0901738596d2418a6','button',NULL,'GET:/api/admin/user/exist','是否存在',3,NULL,NULL,NULL,0,'2024-03-06 12:53:14','2024-03-06 12:53:14',1),('17351863092649fd94c42820447b57d7','79cede9382df47a8914ef5697b38d901','button',NULL,'PUT:/api/admin/article/{uid}/promote','置顶文章',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-02-22 02:52:41',1),('2780d3f5249b44e0b286b54023b13fcf',NULL,'menu','/admin/system',NULL,'系统管理',1,3,'el-icon-user',NULL,0,'2024-02-22 02:54:51','2024-02-27 11:19:40',1),('30b0ca544cd3471180cffcb3a9840c4b','9b34843fa84841c399698ad272f2850b','button',NULL,'GET:/api/admin/role/list','列表',3,NULL,NULL,NULL,0,'2024-03-06 09:35:12','2024-03-06 12:02:54',1),('35b3ca666b97431ea522e6048aa819a0','7c68ef5629244eb0901738596d2418a6','button',NULL,'GET:/api/admin/user/list','列表',3,NULL,NULL,NULL,0,'2024-03-03 08:26:10','2024-03-06 02:17:47',1),('35ebe57a735f4b0aab8cea6cdfdb149a','7c68ef5629244eb0901738596d2418a6','button',NULL,'POST:/api/file/user/{uid}/avatar','上传头像',3,NULL,NULL,NULL,0,'2024-03-06 05:35:58','2024-03-06 05:38:26',1),('45a5eb42e3404270ad8328ae834e3600','7c68ef5629244eb0901738596d2418a6','button',NULL,'GET:/api/admin/user/{uid}/userInfo','用户信息',3,NULL,NULL,NULL,0,'2024-02-22 02:57:17','2024-02-22 03:04:19',1),('5408004e1fd2405db45cb98c42e29d0c',NULL,'menu','/admin/blog',NULL,'博客管理',1,2,'el-icon-notebook-2',NULL,0,'2024-02-19 11:00:31','2024-03-01 08:35:21',1),('5b284b55ab7747f793498def8c776241','79cede9382df47a8914ef5697b38d901','button',NULL,'DELETE:/api/admin/article/{uid}','删除',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-02-22 02:52:42',1),('79cede9382df47a8914ef5697b38d901','5408004e1fd2405db45cb98c42e29d0c','menu','/admin/blog/index',NULL,'博客管理',2,NULL,'el-icon-notebook-2',NULL,0,'2024-02-19 11:03:02','2024-03-01 08:35:21',1),('7c68ef5629244eb0901738596d2418a6','2780d3f5249b44e0b286b54023b13fcf','menu','/admin/system/user',NULL,'用户管理',2,NULL,'el-icon-user',NULL,0,'2024-02-22 02:57:17','2024-02-27 11:19:40',1),('82c86c8c26974f6cb8e1faa360c240d3','79cede9382df47a8914ef5697b38d901','button',NULL,'GET:/api/admin/article/list','列表',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-02-22 02:52:41',1),('88b35441dd7344b9adcf56556deda882','79cede9382df47a8914ef5697b38d901','button',NULL,'POST:/api/admin/article','添加',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-02-22 02:52:41',1),('8bf225ec6d6a4d15a3b0be9adcddc72b','7c68ef5629244eb0901738596d2418a6','button',NULL,'DELETE:/api/admin/user/{uid}','删除',3,NULL,NULL,NULL,0,'2024-03-07 11:51:05','2024-03-07 11:52:00',1),('952c4af5dce04e56887810d4ac0aff4d','7c68ef5629244eb0901738596d2418a6','button',NULL,'GET:/api/admin/user/{uid}/role','角色信息',3,NULL,'el-icon-user-solid',NULL,0,'2024-02-23 01:32:49','2024-02-23 01:32:49',1),('9b34843fa84841c399698ad272f2850b','2780d3f5249b44e0b286b54023b13fcf','menu','/admin/system/role',NULL,'角色管理',2,NULL,'el-icon-user-solid',NULL,0,'2024-03-06 11:47:14','2024-03-06 12:02:54',1),('9b62cc5570ae4ad59b9d8f96d63aad85','7c68ef5629244eb0901738596d2418a6','button',NULL,'DELETE:/api/admin/user','批量删除',3,NULL,NULL,NULL,0,'2024-03-08 11:34:22','2024-03-08 11:47:16',1),('a2f26ceaa846401f912bcc378b96cadd','79cede9382df47a8914ef5697b38d901','button',NULL,'GET:/api/admin/article/{uid}','详情',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-02-22 02:52:41',1),('ab92729e562d490fbc05a35fc83db90d',NULL,'menu','/admin/dashboard',NULL,'首页',1,1,'el-icon-s-home',NULL,0,'2024-02-19 06:04:36','2024-02-27 11:00:22',1),('b08d46d599e442a8829c7185e8ba8a83','79cede9382df47a8914ef5697b38d901','button',NULL,'PUT:/api/admin/article/{uid}','修改',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-02-22 02:52:41',1),('e47308d6526241f18eb260ca9fd9e540','7c68ef5629244eb0901738596d2418a6','button',NULL,'PUT:/api/admin/user/{uid}/userInfo','修改',3,NULL,NULL,NULL,0,'2024-03-06 02:17:47','2024-03-06 02:21:58',1),('f90351dd57664dfbbdcd2389aab7a528','7c68ef5629244eb0901738596d2418a6','button',NULL,'POST:/api/admin/user','添加',3,NULL,NULL,NULL,0,'2024-03-07 05:51:03','2024-03-07 05:52:43',1);
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
INSERT INTO `t_role` (`uid`, `menu_uids`, `role_name`, `summary`, `create_time`, `update_time`, `status`) VALUES ('352af55485b0198631adc4f3f589bb3f',NULL,'管理员',NULL,'0000-00-00 00:00:00','2024-02-08 05:50:19',1),('cd853c7b475723fb4457aebe30fd39ac','2780d3f5249b44e0b286b54023b13fcf,ab92729e562d490fbc05a35fc83db90d,5408004e1fd2405db45cb98c42e29d0c,7c68ef5629244eb0901738596d2418a6,9b34843fa84841c399698ad272f2850b,79cede9382df47a8914ef5697b38d901,5b284b55ab7747f793498def8c776241,17351863092649fd94c42820447b57d7,82c86c8c26974f6cb8e1faa360c240d3,88b35441dd7344b9adcf56556deda882,a2f26ceaa846401f912bcc378b96cadd,b08d46d599e442a8829c7185e8ba8a83,45a5eb42e3404270ad8328ae834e3600,35ebe57a735f4b0aab8cea6cdfdb149a,952c4af5dce04e56887810d4ac0aff4d,35b3ca666b97431ea522e6048aa819a0,e47308d6526241f18eb260ca9fd9e540,30b0ca544cd3471180cffcb3a9840c4b,0de0a4cc2db44cd385d3adc55c81d244,f90351dd57664dfbbdcd2389aab7a528,8bf225ec6d6a4d15a3b0be9adcddc72b,9b62cc5570ae4ad59b9d8f96d63aad85','超级管理员',NULL,'2024-02-03 07:14:39','2024-03-08 11:34:46',1),('ffca2113713df757e0293c6dfd3b4e32',NULL,'普通用户',NULL,'0000-00-00 00:00:00','2024-02-08 05:50:19',1);
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
  `last_login_address` varchar(256) DEFAULT '未知' COMMENT '上次登录的地址',
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
INSERT INTO `t_user` (`uid`, `role_uid`, `user_info_uid`, `username`, `password`, `salt`, `email`, `is_email_activated`, `mobile`, `is_mobile_activated`, `login_count`, `last_login_time`, `last_login_ip`, `last_login_address`, `status`, `create_time`, `update_time`) VALUES ('103e0baa6d964e3bbb3e3deda94fc0e3','ffca2113713df757e0293c6dfd3b4e32','A7C9AACDC396292E3825639379041442','lucky','$2a$10$HuDCsbGXZTbBgVjXryfHeeObIJHz926y9qQTb/z9GWUFHp.9d5o4i','testSalt','123456@qq.com',1,'18819439264',1,218,'2024-03-08 19:13:37','0:0:0:0:0:0:0:1','未知',1,'2024-01-26 03:36:37','2024-03-06 09:16:32'),('1217367d649c4e99abedf53cf7aadc2a','ffca2113713df757e0293c6dfd3b4e32','d98273375f9c494abd1133ecd485897c','hentai','$2a$10$wvduytt6HJkGOBjrEzc9junp8ZQ0.JqKwZ.9dXhc83M.h3E4z7g7W','IXsdqvmVdyQIt3VAqXsC1LRjaug2HmZMtm0I',NULL,0,NULL,0,1,'2024-03-07 16:17:18','0:0:0:0:0:0:0:1','未知',1,'2024-03-07 08:16:05','2024-03-07 08:16:05'),('784c38743e93482ba79cd2e4075a59d8','ffca2113713df757e0293c6dfd3b4e32','c4a488a16fde4cfd932570db80a8a0d7','asdfasdf','$2a$10$z91QMCMCBHquaRcH9ENG4O5wR1knBF7I/fN6MLSsm1g85PAcnOyJ2','o7NkDVcs0ghpyU7drcLQ8O9pDHnCiduCgJDB',NULL,0,NULL,0,0,NULL,'127.0.0.1','未知',1,'2024-03-08 10:57:26','2024-03-08 12:21:05'),('9f5ba3afd10e41c7a5ada60d0f3e4cd7','352af55485b0198631adc4f3f589bb3f','f4d7cfd1f18f481c9e8e15effc779b1c','hn123456','$2a$10$gyloF8JPxje0pSbDhO2a2OGHLIhsuMY3sfKdlzh2F2cG9j.VuKuP.','Pu7cKZ4CEf1KgKgqfDKILcCfiu96DiXxNtxH',NULL,0,NULL,0,1,'2024-03-07 19:17:25','0:0:0:0:0:0:0:1','未知',1,'2024-03-07 11:16:31','2024-03-07 11:16:31'),('a2fc23c7668a4b6386b75584d72498a5','ffca2113713df757e0293c6dfd3b4e32','A4A7B8FBB8E9623FA72556F13A97ED5F','testUser','$2a$10$HuDCsbGXZTbBgVjXryfHeeObIJHz926y9qQTb/z9GWUFHp.9d5o4i','testSalt','123466@qq.com',1,'18819439265',1,1,'2024-02-02 12:55:12','202.89.233.101','未知',1,'2024-01-26 03:36:37','2024-03-06 08:53:30'),('d288eec502414298a62298aba33a0441','352af55485b0198631adc4f3f589bb3f','420b5ebd162143c585dc4582c6c0319c','saddness_player','$2a$10$nVPGC75zT/DVURO8KxCBaeEslPYbdbcOK9QCmyYA4hhsLzpKaG5U6','xfsotHHg7E4XMk6ctIHu2GTo8wDZ1UviMOMQ',NULL,0,NULL,0,1,'2024-03-07 16:36:21','0:0:0:0:0:0:0:1','未知',1,'2024-03-07 08:33:40','2024-03-07 08:33:40'),('ee515eca338c4d358baea8d2c9930a45','ffca2113713df757e0293c6dfd3b4e32','D218CD2BB34F0A91565390458A20E871','123321','$2a$10$HuDCsbGXZTbBgVjXryfHeeObIJHz926y9qQTb/z9GWUFHp.9d5o4i','testSalt','123476@qq.com',1,'18819439266',1,1,'2024-02-08 15:24:29','127.0.0.1','未知',1,'2024-01-26 03:36:37','2024-03-06 09:16:32'),('ef3b67e9b89f40e18df57d6cdf6c001f','ffca2113713df757e0293c6dfd3b4e32','d4b0677e07e6487993e3f6c757e6af67','123123','$2a$10$.s0XVIgiJD5mq0tvwajaPOhSmnZ4L9uNJY3PZh0ilZkrnTn1QBNje','DpUkHdX7GNTBsAHe5mJ890jMEh7NZm26nvvk',NULL,0,NULL,0,0,NULL,'127.0.0.1','未知',1,'2024-03-08 10:54:31','2024-03-08 12:21:26'),('f8616ce5aa0c8eeacfa0d997dbdbe0d3','cd853c7b475723fb4457aebe30fd39ac','f6a7dc9f2df3544912c464e7adb9ce32','PPLAX','$2a$10$HuDCsbGXZTbBgVjXryfHeeObIJHz926y9qQTb/z9GWUFHp.9d5o4i','testSalt','123486@qq.com',1,NULL,1,222,'2024-03-08 20:16:24','0:0:0:0:0:0:0:1','未知',1,'2024-02-03 07:17:56','2024-03-05 11:24:23');
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
INSERT INTO `t_user_info` (`uid`, `nickname`, `gender`, `avatar_picture_uid`, `space_background_picture_uid`, `birthday`, `summary`, `status`, `create_time`, `update_time`) VALUES ('420b5ebd162143c585dc4582c6c0319c','伤心的太刀领域大神',NULL,'64a319a35e2f1c0ba9465344bd9f699a',NULL,NULL,'玩太刀玩的',1,'2024-03-07 11:19:06','2024-03-07 11:19:06'),('A4A7B8FBB8E9623FA72556F13A97ED5F','测试用户',NULL,'473daa8c5c076e27f1556918932c242f',NULL,'2024-03-14',NULL,1,'2024-03-07 11:18:56','2024-03-07 11:18:56'),('A7C9AACDC396292E3825639379041442','李康勇',1,'867cfb5272ad41386fb88c56e755a9c8',NULL,'2018-09-20','测试表情',1,'2024-03-06 12:22:45','2024-03-06 12:22:45'),('c4a488a16fde4cfd932570db80a8a0d7','11111',NULL,'f5b9ea6bc873ce834b3debd6c3257c18',NULL,NULL,NULL,1,'2024-03-08 11:08:11','2024-03-08 11:08:11'),('D218CD2BB34F0A91565390458A20E871','怨虎龙',1,'2de5a625f7437c5c005cbce953fc0d1e',NULL,'2018-09-20','测试表情',1,'2024-03-06 12:20:37','2024-03-06 12:20:37'),('d4b0677e07e6487993e3f6c757e6af67','123',NULL,NULL,NULL,NULL,NULL,1,'2024-03-08 10:54:31','2024-03-08 12:21:23'),('d98273375f9c494abd1133ecd485897c','略略略',NULL,'90a20b3a675294d1ccbf331433bde782',NULL,NULL,NULL,1,'2024-03-07 09:32:24','2024-03-07 09:32:24'),('f4d7cfd1f18f481c9e8e15effc779b1c','hn123456',NULL,'b771c45d35f1f7e3441890c4690b8224',NULL,'2002-11-26',NULL,1,'2024-03-07 11:16:39','2024-03-07 11:16:39'),('f6a7dc9f2df3544912c464e7adb9ce32','普拉克斯',1,'453c7bb2ae88f5c9624a78cbbf6f6da7',NULL,'2024-02-03',NULL,1,'2024-03-06 12:19:13','2024-03-06 12:19:13');
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

-- Dump completed on 2024-03-08 20:22:16
