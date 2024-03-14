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
INSERT INTO `t_blog` (`uid`, `title`, `summary`, `blog_content_uid`, `tag_uids`, `click_count`, `collect_count`, `cover_image_uid`, `status`, `user_uid`, `is_original`, `articles_part`, `blog_sort_uid`, `level`, `create_time`, `update_time`) VALUES ('11a4784c4d8b4cb38f8523284364690c','再次测试添加','','2a55082075064b47998053f69c7d1dc3','8f3d992d404347dc998f09b99d363714',0,0,'',1,'f8616ce5aa0c8eeacfa0d997dbdbe0d3',1,'','3ae899e993b744c99fb78dccafac8e66',0,'2024-03-14 09:42:58','2024-03-14 09:42:58'),('211fb300fe7a4a20bfda396a9450b117','后端开发简历咋整呢','这是摘要','771690201d694446b653b9f6a9eabbaf','25228e8e5d4847d39a4b1466e7403e69,53c5a0f3142e4f54820315936f78383b,7e0e93ea6cdb44ae92e58f48e6496ed7',0,0,'fffddcb3a41e6192073a75252a2dc65e',1,'f8616ce5aa0c8eeacfa0d997dbdbe0d3',1,'','2c93dfab0e754006866f8ed486923a41',0,'2024-01-15 07:54:25','2024-03-11 11:49:50'),('22b5a762375648edabd72c31b74dd4c2','再再次测试','','3d1a2672e8134e99a34828df99efe052','7e0e93ea6cdb44ae92e58f48e6496ed7',0,0,'',1,'f8616ce5aa0c8eeacfa0d997dbdbe0d3',1,'','3ae899e993b744c99fb78dccafac8e66',0,'2024-03-14 09:44:45','2024-03-14 09:44:45'),('5de884b1e9a74f51a25df84321c165cd','测试添加','','7dd7ab3a48984550a596f483178442f0','04612e33964d45d19fa7f4fd61acd807',0,0,'',1,'ee515eca338c4d358baea8d2c9930a45',1,'','cce33bd372a5403a81346a3001ff544a',0,'2024-03-14 09:18:29','2024-03-14 09:36:01'),('93481c3880774ae59bbfad5ac094ac95','测试添加','','f6121ceeb0bf47939e2097760b0d8f40','1b2c264406fe4f6bb243cb6200b9e9e4',0,0,'',6,'ee515eca338c4d358baea8d2c9930a45',1,'','3ae899e993b744c99fb78dccafac8e66',0,'2024-03-14 09:16:55','2024-03-14 09:36:01'),('b7dbc212bf304422be9fa22c3f2cf1a2','年轻人的第一款怪猎','这是摘要','00ef773b1cb542bb8e4d262e5f23e37c','7efee6f74d594d25928ba86bc7adee28,9da13eb143f54c6bb70ecbd5212bde69',0,0,NULL,6,'ee515eca338c4d358baea8d2c9930a45',1,'','3ae899e993b744c99fb78dccafac8e66',1,'2024-01-15 07:48:52','2024-03-14 09:36:01'),('dcf8f268a51143fb988384e7559e5650','黑龙登龙点讲解','这是摘要','afa22a4ffa7e41de99863f16ab3e5c97','7efee6f74d594d25928ba86bc7adee28,9da13eb143f54c6bb70ecbd5212bde69',0,0,NULL,5,'ee515eca338c4d358baea8d2c9930a45',1,'','3ae899e993b744c99fb78dccafac8e66',2,'2024-01-15 07:52:42','2024-03-14 09:36:00'),('f4bd17e13924496889a2eb16ae01ba30','这是一个标题','这是摘要','9ddd597bb3ff4426b84f35439f553e94','ca928e8718654aa5a802e2f69277b137',0,0,NULL,4,'f8616ce5aa0c8eeacfa0d997dbdbe0d3',0,'https://cn.bing.com/','093d8bdd01c84890a928e923d5c235fe',3,'2024-01-15 07:48:52','2024-03-14 02:36:04'),('fd1622ca585b4ccea34c4bbc88ac4122','saf','','699071efe3ce4df5add4089e1adc5994',',97e3b2d7d7934a8dbae95222e04ef25e',0,0,'',1,'f8616ce5aa0c8eeacfa0d997dbdbe0d3',1,'','3ae899e993b744c99fb78dccafac8e66',0,'2024-03-14 09:27:16','2024-03-14 09:40:25');
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
INSERT INTO `t_blog_content` (`uid`, `content`, `status`, `create_time`, `update_time`) VALUES ('00ef773b1cb542bb8e4d262e5f23e37c','罚你登龙给怪回血',1,'2024-01-15 07:48:34','2024-01-15 07:48:34'),('2a55082075064b47998053f69c7d1dc3','### 三级标题![g.jpg](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-14/1710409778701.jpg)',1,'2024-03-14 09:42:58','2024-03-14 09:42:58'),('3d1a2672e8134e99a34828df99efe052','s![RC.jpg](http://127.0.0.1:9002/pplax-blog/blog/attach/file/2024-03-14/1710409534738.jpg)',1,'2024-03-14 09:44:45','2024-03-14 09:44:45'),('699071efe3ce4df5add4089e1adc5994','### 三级标题<video height=100% width=100% controls autoplay src=\"http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-14/1710409803557.mp4\"></video>',1,'2024-03-14 09:27:16','2024-03-14 09:27:16'),('771690201d694446b653b9f6a9eabbaf','## 到底怎么整呢',1,'2024-01-15 07:53:59','2024-01-15 07:53:59'),('7dd7ab3a48984550a596f483178442f0','123456',1,'2024-03-14 09:18:29','2024-03-14 09:18:29'),('9ddd597bb3ff4426b84f35439f553e94','这是文章内容',1,'2024-01-15 07:48:34','2024-01-15 07:48:34'),('afa22a4ffa7e41de99863f16ab3e5c97','扇形火狠狠登',1,'2024-01-15 07:52:28','2024-01-15 07:52:28'),('f6121ceeb0bf47939e2097760b0d8f40','测试添加',1,'2024-03-14 09:16:55','2024-03-14 09:16:55');
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
  `sort_name` varchar(64) DEFAULT NULL COMMENT '分类名',
  `content` varchar(255) DEFAULT NULL COMMENT '分类内容',
  `click_count` int DEFAULT '0' COMMENT '点击数',
  `icon` varchar(128) DEFAULT NULL COMMENT '分类图标',
  `sort_no` int DEFAULT NULL COMMENT '排列位次',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='博客分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_blog_sort`
--

LOCK TABLES `t_blog_sort` WRITE;
/*!40000 ALTER TABLE `t_blog_sort` DISABLE KEYS */;
INSERT INTO `t_blog_sort` (`uid`, `sort_name`, `content`, `click_count`, `icon`, `sort_no`, `create_time`, `update_time`, `status`) VALUES ('093d8bdd01c84890a928e923d5c235fe','时光轴','时光轴的简介',4,'el-icon-alarm-clock',3,'2024-03-09 11:52:48','2024-03-10 04:21:52',1),('2c93dfab0e754006866f8ed486923a41','生活随笔','日常',2,'el-icon-camera',2,'2024-03-10 02:57:22','2024-03-10 04:21:52',1),('2fc443ca683bc93248873dcac0ccda9d','考研','现在考研好难哦',1,'el-icon-document',4,'2024-01-16 14:48:17','2024-03-10 04:21:52',1),('3ae899e993b744c99fb78dccafac8e66','游戏','现在我要唤醒那个小时候的自己了！',5,'el-icon-ship',5,'2024-03-10 10:12:04','2024-03-10 10:12:04',1),('3cf89eab5cf2484f8023f088dd3f3cd5','编程','编程 简介',3,'el-icon-monitor',1,'2024-03-09 11:46:15','2024-03-10 04:21:52',1),('cce33bd372a5403a81346a3001ff544a','学习笔记','',7,'el-icon-edit-outline',7,'2024-03-10 02:46:57','2024-03-10 04:21:52',1),('fbef6ff4be704781a0e0c4aeb7a2b64b','美食','我是饭桶',9,'el-icon-knife-fork',6,'2024-03-10 02:24:53','2024-03-10 04:21:52',1);
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
INSERT INTO `t_file_storage` (`uid`, `original_name`, `file_name`, `suffix`, `file_path`, `file_size`, `is_directory`, `is_image`, `storage_mode`, `file_url`, `status`, `create_time`, `update_time`) VALUES ('0618f8e92ff895f650ec3111baa2e304','1707379097298.jpg','1710401335079.jpg','jpg','/spaceBackgroundPicture/2024-03-14/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/spaceBackgroundPicture/2024-03-14/1710401335079.jpg',1,'2024-03-14 07:28:55','2024-03-14 07:28:55'),('248c4f14514472fc4731f6adc44bfc33','1645512111007.jpg','1710410104686.jpg','jpg','/blog/attach/2024-03-14/',11909,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-14/1710410104686.jpg',1,'2024-03-14 09:55:05','2024-03-14 09:55:05'),('2ff1bcc5643eafd610eac430c7f0e23e','R-C.jpg','1710409534738.jpg','jpg','/blog/attach/file/2024-03-14/',128449,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/file/2024-03-14/1710409534738.jpg',1,'2024-03-14 09:45:35','2024-03-14 09:45:35'),('34b62d510ba35b7d434ca4b718b49951','20231214_1702545284825.mp4','1710403892727.mp4','mp4','/blog/attach/video/2024-03-14/',2613215,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/video/2024-03-14/1710403892727.mp4',1,'2024-03-14 08:11:33','2024-03-14 08:11:33'),('3976f9b5692d6e5958cd84a9500e07d4','1707379097298.jpg','1710401340587.jpg','jpg','/avatar/2024-03-14/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/avatar/2024-03-14/1710401340587.jpg',1,'2024-03-14 07:29:01','2024-03-14 07:29:01'),('418eef0b14c069e1f6e562f0f8db440b','20231214_1702545284825.mp4','1710403833323.mp4','mp4','/blog/attach/video/2024-03-14/',2613215,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/video/2024-03-14/1710403833323.mp4',1,'2024-03-14 08:10:33','2024-03-14 08:10:33'),('459bcde4223ac8f04f5c2f01c4fe7838','g.jpg','1710409778701.jpg','jpg','/blog/attach/2024-03-14/',3179,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-14/1710409778701.jpg',1,'2024-03-14 09:49:39','2024-03-14 09:49:39'),('4c97865b9fd0d0f12cd19cb6caa94ce8','VCG211269969903.jpg','1710401344099.jpg','jpg','/spaceBackgroundPicture/2024-03-14/',267470,0,1,'minio','http://127.0.0.1:9002/pplax-blog/spaceBackgroundPicture/2024-03-14/1710401344099.jpg',1,'2024-03-14 07:29:04','2024-03-14 07:29:04'),('51f7bd20169b9ef9e8999dc25a860a1f','VCG211258127993.jpg','1710402211437.jpg','jpg','/spaceBackgroundPicture/2024-03-14/',173714,0,1,'minio','http://127.0.0.1:9002/pplax-blog/spaceBackgroundPicture/2024-03-14/1710402211437.jpg',1,'2024-03-14 07:43:31','2024-03-14 07:43:31'),('7256421e7ba40630afa29895dc017f2b','20231214_1702545284825.mp4','1710409803557.mp4','mp4','/blog/attach/2024-03-14/',2613215,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-14/1710409803557.mp4',1,'2024-03-14 09:50:04','2024-03-14 09:50:04'),('75eb8d169bd9c0d888552454046ce281','1645512111007.jpg','1710402218993.jpg','jpg','/avatar/2024-03-14/',11909,0,1,'minio','http://127.0.0.1:9002/pplax-blog/avatar/2024-03-14/1710402218993.jpg',1,'2024-03-14 07:43:39','2024-03-14 07:43:39'),('7bd960cf90e97de86c39a259a19e0c52','012d365aa88c9fa80121246d981d9a.jpg@1280w_1l_2o_100sh.jpg','1710401400857.jpg','jpg','/blog/coverImage/2024-03-14/',808904,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/coverImage/2024-03-14/1710401400857.jpg',1,'2024-03-14 07:30:01','2024-03-14 07:30:01'),('c91665b3c584bf8fdc59dd115e626602','VCG211258127993.jpg','1710407351626.jpg','jpg','/blog/coverImage/2024-03-14/',173714,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/coverImage/2024-03-14/1710407351626.jpg',1,'2024-03-14 09:09:12','2024-03-14 09:09:12'),('fd7ccf122e12f04c417f573d13db52c2','20220801091938_9f3d5.thumb.400_0.jpg','1710403068269.jpg','jpg','/blog/attach/2024-03-14/',12844,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-14/1710403068269.jpg',1,'2024-03-14 07:57:48','2024-03-14 07:57:48'),('fffddcb3a41e6192073a75252a2dc65e','012d365aa88c9fa80121246d981d9a.jpg@1280w_1l_2o_100sh.jpg','1710409743114.jpg','jpg','/blog/coverImage/2024-03-14/',808904,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/coverImage/2024-03-14/1710409743114.jpg',1,'2024-03-14 09:49:03','2024-03-14 09:49:03');
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
INSERT INTO `t_menu` (`uid`, `parent_uid`, `type`, `route`, `endpoint`, `title`, `level`, `sort_no`, `icon`, `remarks`, `hidden`, `create_time`, `update_time`, `status`) VALUES ('01c0f7e362744b988e9cbd458ec1bcf3','79cede9382df47a8914ef5697b38d901','button',NULL,'POST:/api/file/blog/coverImage','上传封面图',3,NULL,NULL,NULL,0,'2024-03-13 08:26:09','2024-03-14 07:33:04',1),('079a677ea6c74e8abd752d84ec49f449','4431c6e47f9f4b15a48554669a4067ad','button',NULL,'DELETE:/api/admin/tag','批量删除',3,NULL,NULL,NULL,0,'2024-03-10 06:16:44','2024-03-10 06:16:44',1),('0de0a4cc2db44cd385d3adc55c81d244','7c68ef5629244eb0901738596d2418a6','button',NULL,'GET:/api/admin/user/exist','是否存在',3,NULL,NULL,NULL,0,'2024-03-06 12:53:14','2024-03-06 12:53:14',1),('17351863092649fd94c42820447b57d7','79cede9382df47a8914ef5697b38d901','button',NULL,'PUT:/api/admin/blog/{uid}/promote','置顶',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-03-09 05:56:42',1),('19b411b8f1ad4f45b87768fc882b25cf','7c68ef5629244eb0901738596d2418a6','button',NULL,'POST:/api/file/user/spaceBackgroundPicture','上传空间背景图',3,NULL,NULL,NULL,0,'2024-03-06 05:35:58','2024-03-14 07:41:28',1),('1a76044085234e0c9e4c1a8c50e8473c','5408004e1fd2405db45cb98c42e29d0c','menu','/admin/blog/blogSort',NULL,'分类管理',2,NULL,'el-icon-files',NULL,0,'2024-03-09 04:03:08','2024-03-09 04:03:08',1),('1b592b748a2a414e9c9fec1ce6b2091f','1a76044085234e0c9e4c1a8c50e8473c','button',NULL,'PUT:/api/admin/blogSort/{uid}/promote','置顶',3,NULL,NULL,NULL,0,'2024-03-09 05:56:42','2024-03-09 07:07:34',1),('2780d3f5249b44e0b286b54023b13fcf',NULL,'menu','/admin/system',NULL,'系统管理',1,3,'el-icon-setting',NULL,0,'2024-02-22 02:54:51','2024-03-10 06:49:16',1),('30b0ca544cd3471180cffcb3a9840c4b','9b34843fa84841c399698ad272f2850b','button',NULL,'GET:/api/admin/role/list','列表',3,NULL,NULL,NULL,0,'2024-03-06 09:35:12','2024-03-06 12:02:54',1),('35b3ca666b97431ea522e6048aa819a0','7c68ef5629244eb0901738596d2418a6','button',NULL,'GET:/api/admin/user/list','列表',3,NULL,NULL,NULL,0,'2024-03-03 08:26:10','2024-03-06 02:17:47',1),('35ebe57a735f4b0aab8cea6cdfdb149a','7c68ef5629244eb0901738596d2418a6','button',NULL,'POST:/api/file/user/avatar','上传头像',3,NULL,NULL,NULL,0,'2024-03-06 05:35:58','2024-03-14 07:41:28',1),('3e5afdd05057400091ab6f9947070b56','1a76044085234e0c9e4c1a8c50e8473c','button',NULL,'PUT:/api/admin/blogSort/{uid}','修改',3,NULL,NULL,NULL,0,'2024-03-09 07:23:18','2024-03-09 07:23:18',1),('4431c6e47f9f4b15a48554669a4067ad','5408004e1fd2405db45cb98c42e29d0c','menu','/admin/blog/tag',NULL,'标签管理',2,NULL,'el-icon-collection-tag',NULL,0,'2024-03-10 03:52:26','2024-03-10 03:52:26',1),('448b8e594ecf4c749c487954690fc0fd','4431c6e47f9f4b15a48554669a4067ad','button',NULL,'DELETE:/api/admin/tag/{uid}','删除',3,NULL,NULL,NULL,0,'2024-03-10 06:16:44','2024-03-10 06:16:44',1),('45a5eb42e3404270ad8328ae834e3600','7c68ef5629244eb0901738596d2418a6','button',NULL,'GET:/api/admin/user/{uid}/userInfo','用户信息',3,NULL,NULL,NULL,0,'2024-02-22 02:57:17','2024-02-22 03:04:19',1),('5408004e1fd2405db45cb98c42e29d0c',NULL,'menu','/admin/blog',NULL,'博客管理',1,2,'el-icon-notebook-2',NULL,0,'2024-02-19 11:00:31','2024-03-01 08:35:21',1),('5b284b55ab7747f793498def8c776241','79cede9382df47a8914ef5697b38d901','button',NULL,'DELETE:/api/admin/blog/{uid}','删除',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-03-09 02:52:34',1),('620862a3c64e4bf788fdae591d15c650','79cede9382df47a8914ef5697b38d901','button',NULL,'POST:/api/file/blog/attach/image','上传博客附件文件（图片）',3,NULL,NULL,NULL,0,'2024-03-13 08:26:09','2024-03-14 07:33:04',1),('69538529b10b4c2dbcd75db0aa7f8391','79cede9382df47a8914ef5697b38d901','button',NULL,'POST:/api/file/blog/attach/video','上传博客附件文件（视频）',3,NULL,NULL,NULL,0,'2024-03-13 08:26:09','2024-03-14 07:33:04',1),('76c352a02ddf4399b0d094b64399f9d4','79cede9382df47a8914ef5697b38d901','button',NULL,'GET:/api/admin/blog/{uid}/content','获得内容',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-03-09 02:52:34',1),('79cede9382df47a8914ef5697b38d901','5408004e1fd2405db45cb98c42e29d0c','menu','/admin/blog/index',NULL,'博客管理',2,NULL,'el-icon-notebook-2',NULL,0,'2024-02-19 11:03:02','2024-03-01 08:35:21',1),('7b475e6fe7ad4382a5750b5666f5290f','4431c6e47f9f4b15a48554669a4067ad','button',NULL,'PUT:/api/admin/tag/{uid}','修改',3,NULL,NULL,NULL,0,'2024-03-10 06:16:44','2024-03-10 06:16:44',1),('7c68ef5629244eb0901738596d2418a6','2780d3f5249b44e0b286b54023b13fcf','menu','/admin/system/user',NULL,'用户管理',2,NULL,'el-icon-user',NULL,0,'2024-02-22 02:57:17','2024-02-27 11:19:40',1),('82c86c8c26974f6cb8e1faa360c240d3','79cede9382df47a8914ef5697b38d901','button',NULL,'GET:/api/admin/blog/list','列表',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-03-09 02:52:34',1),('8357f88168874841ab48d1ec764fea23','1a76044085234e0c9e4c1a8c50e8473c','button',NULL,'DELETE:/api/admin/blogSort/{uid}','删除',3,NULL,NULL,NULL,0,'2024-03-10 02:52:50','2024-03-10 02:52:50',1),('88b35441dd7344b9adcf56556deda882','79cede9382df47a8914ef5697b38d901','button',NULL,'POST:/api/admin/blog','添加',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-03-09 02:52:34',1),('8bf225ec6d6a4d15a3b0be9adcddc72b','7c68ef5629244eb0901738596d2418a6','button',NULL,'DELETE:/api/admin/user/{uid}','删除',3,NULL,NULL,NULL,0,'2024-03-07 11:51:05','2024-03-07 11:52:00',1),('952c4af5dce04e56887810d4ac0aff4d','7c68ef5629244eb0901738596d2418a6','button',NULL,'GET:/api/admin/user/{uid}/role','角色信息',3,NULL,'el-icon-user-solid',NULL,0,'2024-02-23 01:32:49','2024-02-23 01:32:49',1),('9b34843fa84841c399698ad272f2850b','2780d3f5249b44e0b286b54023b13fcf','menu','/admin/system/role',NULL,'角色管理',2,NULL,'el-icon-user-solid',NULL,0,'2024-03-06 11:47:14','2024-03-06 12:02:54',1),('9b62cc5570ae4ad59b9d8f96d63aad85','7c68ef5629244eb0901738596d2418a6','button',NULL,'DELETE:/api/admin/user','批量删除',3,NULL,NULL,NULL,0,'2024-03-08 11:34:22','2024-03-08 11:47:16',1),('9c7c1bae35a149efb51766380bfc246c','4431c6e47f9f4b15a48554669a4067ad','button',NULL,'GET:/api/admin/tag/list','列表',3,NULL,NULL,NULL,0,'2024-03-10 04:03:59','2024-03-10 04:03:59',1),('a2f26ceaa846401f912bcc378b96cadd','79cede9382df47a8914ef5697b38d901','button',NULL,'GET:/api/admin/blog/{uid}','详情',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-03-09 02:52:34',1),('ab92729e562d490fbc05a35fc83db90d',NULL,'menu','/admin/dashboard',NULL,'首页',1,1,'el-icon-s-home',NULL,0,'2024-02-19 06:04:36','2024-02-27 11:00:22',1),('b08d46d599e442a8829c7185e8ba8a83','79cede9382df47a8914ef5697b38d901','button',NULL,'PUT:/api/admin/blog/{uid}','修改',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-03-09 02:52:34',1),('bf27986c815f48c08b99ff890359446a','1a76044085234e0c9e4c1a8c50e8473c','button',NULL,'POST:/api/admin/blogSort','添加',3,NULL,NULL,NULL,0,'2024-03-10 02:35:49','2024-03-10 02:35:49',1),('c8d939d535e24b8fb2981ff520e1d102','79cede9382df47a8914ef5697b38d901','button',NULL,'DELETE:/api/admin/blog','批量删除',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-03-09 02:52:34',1),('ce89deb669f74549a40b35244e51e2e0','79cede9382df47a8914ef5697b38d901','button',NULL,'POST:/api/file/blog/attach/file','上传博客附件文件',3,NULL,NULL,NULL,0,'2024-03-13 08:26:09','2024-03-14 08:06:22',1),('cfd038df1fee40af9afe2a6cfedf2a92','1a76044085234e0c9e4c1a8c50e8473c','button',NULL,'GET:/api/admin/blogSort/list','列表',3,NULL,NULL,NULL,0,'2024-03-09 04:12:56','2024-03-09 07:07:34',1),('d8e0d7b294374e90a2d52259cd527eb1','4431c6e47f9f4b15a48554669a4067ad','button',NULL,'POST:/api/admin/tag','添加',3,NULL,NULL,NULL,0,'2024-03-10 06:16:44','2024-03-10 06:16:44',1),('e47308d6526241f18eb260ca9fd9e540','7c68ef5629244eb0901738596d2418a6','button',NULL,'PUT:/api/admin/user/{uid}/userInfo','修改',3,NULL,NULL,NULL,0,'2024-03-06 02:17:47','2024-03-06 02:21:58',1),('e639bd6b3be7461ab3c461466bc1cbdb','1a76044085234e0c9e4c1a8c50e8473c','button',NULL,'DELETE:/api/admin/blogSort','批量删除',3,NULL,NULL,NULL,0,'2024-03-10 03:15:06','2024-03-10 03:15:06',1),('e80e3aeab2d940828455162e1d4d9cff','4431c6e47f9f4b15a48554669a4067ad','button',NULL,'GET:/api/admin/tag/exist','是否存在',3,NULL,NULL,NULL,0,'2024-03-10 04:03:59','2024-03-10 04:03:59',1),('f0a5d2e7cec84f68bbb508b74fa18eda','1a76044085234e0c9e4c1a8c50e8473c','button',NULL,'DELETE:/api/admin/blogSort/{uid}/promote','取消置顶',3,NULL,NULL,NULL,0,'2024-03-09 06:56:37','2024-03-09 07:07:34',1),('f90351dd57664dfbbdcd2389aab7a528','7c68ef5629244eb0901738596d2418a6','button',NULL,'POST:/api/admin/user','添加',3,NULL,NULL,NULL,0,'2024-03-07 05:51:03','2024-03-07 05:52:43',1);
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
INSERT INTO `t_role` (`uid`, `menu_uids`, `role_name`, `summary`, `create_time`, `update_time`, `status`) VALUES ('352af55485b0198631adc4f3f589bb3f',NULL,'管理员',NULL,'0000-00-00 00:00:00','2024-02-08 05:50:19',1),('cd853c7b475723fb4457aebe30fd39ac','2780d3f5249b44e0b286b54023b13fcf,ab92729e562d490fbc05a35fc83db90d,5408004e1fd2405db45cb98c42e29d0c,f0a5d2e7cec84f68bbb508b74fa18eda,e639bd6b3be7461ab3c461466bc1cbdb,cfd038df1fee40af9afe2a6cfedf2a92,bf27986c815f48c08b99ff890359446a,1b592b748a2a414e9c9fec1ce6b2091f,3e5afdd05057400091ab6f9947070b56,8357f88168874841ab48d1ec764fea23,9b34843fa84841c399698ad272f2850b,7c68ef5629244eb0901738596d2418a6,079a677ea6c74e8abd752d84ec49f449,d8e0d7b294374e90a2d52259cd527eb1,9c7c1bae35a149efb51766380bfc246c,7b475e6fe7ad4382a5750b5666f5290f,448b8e594ecf4c749c487954690fc0fd,1a76044085234e0c9e4c1a8c50e8473c,79cede9382df47a8914ef5697b38d901,4431c6e47f9f4b15a48554669a4067ad,01c0f7e362744b988e9cbd458ec1bcf3,c8d939d535e24b8fb2981ff520e1d102,b08d46d599e442a8829c7185e8ba8a83,ce89deb669f74549a40b35244e51e2e0,a2f26ceaa846401f912bcc378b96cadd,17351863092649fd94c42820447b57d7,88b35441dd7344b9adcf56556deda882,82c86c8c26974f6cb8e1faa360c240d3,5b284b55ab7747f793498def8c776241,76c352a02ddf4399b0d094b64399f9d4,69538529b10b4c2dbcd75db0aa7f8391,620862a3c64e4bf788fdae591d15c650,f90351dd57664dfbbdcd2389aab7a528,0de0a4cc2db44cd385d3adc55c81d244,e47308d6526241f18eb260ca9fd9e540,19b411b8f1ad4f45b87768fc882b25cf,35b3ca666b97431ea522e6048aa819a0,9b62cc5570ae4ad59b9d8f96d63aad85,35ebe57a735f4b0aab8cea6cdfdb149a,952c4af5dce04e56887810d4ac0aff4d,8bf225ec6d6a4d15a3b0be9adcddc72b,45a5eb42e3404270ad8328ae834e3600,30b0ca544cd3471180cffcb3a9840c4b,e80e3aeab2d940828455162e1d4d9cff','超级管理员',NULL,'2024-02-03 07:14:39','2024-03-14 10:41:23',1),('ffca2113713df757e0293c6dfd3b4e32',NULL,'普通用户',NULL,'0000-00-00 00:00:00','2024-02-08 05:50:19',1);
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
INSERT INTO `t_tag` (`uid`, `name`, `click_count`, `level`, `status`, `create_time`, `update_time`) VALUES ('04612e33964d45d19fa7f4fd61acd807','github',2,0,1,'2024-03-14 10:48:17','2024-03-14 10:48:17'),('1b2c264406fe4f6bb243cb6200b9e9e4','鸟山明',0,0,1,'2024-03-10 10:50:42','2024-03-10 06:40:10'),('25228e8e5d4847d39a4b1466e7403e69','Spring Cloud',1,0,1,'2018-09-21 12:09:50','2018-09-21 12:09:50'),('53c5a0f3142e4f54820315936f78383b','Spring Boot',1,0,1,'2018-09-21 12:11:06','2018-09-21 12:11:06'),('6c499d64a6c84705bb374ac48472e088','宫崎骏',0,0,1,'2024-03-10 10:50:53','2024-03-10 06:40:10'),('7e0e93ea6cdb44ae92e58f48e6496ed7','Java',1,0,1,'2018-09-21 12:12:52','2018-09-21 12:12:52'),('7efee6f74d594d25928ba86bc7adee28','游戏',1,0,1,'2018-09-20 06:51:39','2018-09-20 06:51:39'),('8f3d992d404347dc998f09b99d363714','钢琴',0,0,1,'2024-03-14 10:50:10','2024-03-14 10:50:10'),('97e3b2d7d7934a8dbae95222e04ef25e','vk克',0,0,1,'2024-03-14 10:49:57','2024-03-14 10:49:57'),('9da13eb143f54c6bb70ecbd5212bde69','怪物',1,0,1,'2018-09-21 12:13:51','2024-03-10 06:40:10'),('a9a747d944c24845815356f72723ef8e','前端开发',2,0,1,'2018-09-20 06:51:39','2018-09-20 06:51:39'),('ca928e8718654aa5a802e2f69277b137','生活琐事',2,0,1,'2018-09-21 12:13:41','2018-09-21 12:13:41'),('d813c892224a4f97a40ca2d745d922c3','mirror night',0,0,1,'2024-03-14 10:50:19','2024-03-14 10:50:19'),('e2c7913050cf4ab9aa92902316aaf075','校园生活',1,0,1,'2018-09-21 12:13:51','2018-09-21 12:13:51'),('f18448a27f6e4320b40cfe51cb9b0a66','李康勇',1,0,1,'2018-09-21 12:13:41','2024-03-10 06:40:10'),('f90d3c2fd9434302951130e897a89164','Vue',1,0,1,'2018-09-21 12:12:52','2018-09-21 12:12:52');
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
INSERT INTO `t_user` (`uid`, `role_uid`, `user_info_uid`, `username`, `password`, `salt`, `email`, `is_email_activated`, `mobile`, `is_mobile_activated`, `login_count`, `last_login_time`, `last_login_ip`, `last_login_address`, `status`, `create_time`, `update_time`) VALUES ('103e0baa6d964e3bbb3e3deda94fc0e3','ffca2113713df757e0293c6dfd3b4e32','A7C9AACDC396292E3825639379041442','lucky','$2a$10$HuDCsbGXZTbBgVjXryfHeeObIJHz926y9qQTb/z9GWUFHp.9d5o4i','testSalt','123456@qq.com',1,'18819439264',1,218,'2024-03-08 19:13:37','0:0:0:0:0:0:0:1','未知',1,'2024-01-26 03:36:37','2024-03-06 09:16:32'),('1217367d649c4e99abedf53cf7aadc2a','ffca2113713df757e0293c6dfd3b4e32','d98273375f9c494abd1133ecd485897c','hentai','$2a$10$wvduytt6HJkGOBjrEzc9junp8ZQ0.JqKwZ.9dXhc83M.h3E4z7g7W','IXsdqvmVdyQIt3VAqXsC1LRjaug2HmZMtm0I',NULL,0,NULL,0,1,'2024-03-07 16:17:18','0:0:0:0:0:0:0:1','未知',1,'2024-03-07 08:16:05','2024-03-07 08:16:05'),('1e8221b961fe4019baefd6e006988a75','ffca2113713df757e0293c6dfd3b4e32','92846d9fabb0458b8ec768a9681920cf','lalala','$2a$10$eKEIvGxeJW79Yv0oiUadyedRh4StQfJEDTmA4BR5V2/GIAtakyj32','yBMVtLDraR1vNnwHcNWxglevZLJ257ntnQW6',NULL,0,NULL,0,0,NULL,'127.0.0.1','未知',1,'2024-03-09 02:19:45','2024-03-09 02:29:15'),('594a9829162340babf5d3e3340a7eb93','ffca2113713df757e0293c6dfd3b4e32','e5bae0aca6ae48e98eae9a86318b7be8','node_modules','$2a$10$yBA/O/YQ/91hahF2mw246uj3RziiYPKv.KOUX8il9QyAg/nu0kVvG','pB0NFS94Vbwe1QRO7n5IG6Qjc0svHPhl4if5',NULL,0,NULL,0,0,NULL,'127.0.0.1','未知',1,'2024-03-09 02:10:53','2024-03-09 02:10:53'),('784c38743e93482ba79cd2e4075a59d8','ffca2113713df757e0293c6dfd3b4e32','c4a488a16fde4cfd932570db80a8a0d7','asdfasdf','$2a$10$z91QMCMCBHquaRcH9ENG4O5wR1knBF7I/fN6MLSsm1g85PAcnOyJ2','o7NkDVcs0ghpyU7drcLQ8O9pDHnCiduCgJDB',NULL,0,NULL,0,0,NULL,'127.0.0.1','未知',1,'2024-03-08 10:57:26','2024-03-08 12:21:05'),('9f5ba3afd10e41c7a5ada60d0f3e4cd7','352af55485b0198631adc4f3f589bb3f','f4d7cfd1f18f481c9e8e15effc779b1c','hn123456','$2a$10$gyloF8JPxje0pSbDhO2a2OGHLIhsuMY3sfKdlzh2F2cG9j.VuKuP.','Pu7cKZ4CEf1KgKgqfDKILcCfiu96DiXxNtxH',NULL,0,NULL,0,1,'2024-03-07 19:17:25','0:0:0:0:0:0:0:1','未知',1,'2024-03-07 11:16:31','2024-03-07 11:16:31'),('a2fc23c7668a4b6386b75584d72498a5','ffca2113713df757e0293c6dfd3b4e32','A4A7B8FBB8E9623FA72556F13A97ED5F','testUser','$2a$10$HuDCsbGXZTbBgVjXryfHeeObIJHz926y9qQTb/z9GWUFHp.9d5o4i','testSalt','123466@qq.com',1,'18819439265',1,1,'2024-02-02 12:55:12','202.89.233.101','未知',1,'2024-01-26 03:36:37','2024-03-06 08:53:30'),('d288eec502414298a62298aba33a0441','352af55485b0198631adc4f3f589bb3f','420b5ebd162143c585dc4582c6c0319c','saddness_player','$2a$10$nVPGC75zT/DVURO8KxCBaeEslPYbdbcOK9QCmyYA4hhsLzpKaG5U6','xfsotHHg7E4XMk6ctIHu2GTo8wDZ1UviMOMQ',NULL,0,NULL,0,1,'2024-03-07 16:36:21','0:0:0:0:0:0:0:1','未知',1,'2024-03-07 08:33:40','2024-03-07 08:33:40'),('ee515eca338c4d358baea8d2c9930a45','ffca2113713df757e0293c6dfd3b4e32','D218CD2BB34F0A91565390458A20E871','123321','$2a$10$HuDCsbGXZTbBgVjXryfHeeObIJHz926y9qQTb/z9GWUFHp.9d5o4i','testSalt','123476@qq.com',1,'18819439266',1,1,'2024-02-08 15:24:29','127.0.0.1','未知',1,'2024-01-26 03:36:37','2024-03-06 09:16:32'),('ef3b67e9b89f40e18df57d6cdf6c001f','ffca2113713df757e0293c6dfd3b4e32','d4b0677e07e6487993e3f6c757e6af67','1123','$2a$10$.s0XVIgiJD5mq0tvwajaPOhSmnZ4L9uNJY3PZh0ilZkrnTn1QBNje','DpUkHdX7GNTBsAHe5mJ890jMEh7NZm26nvvk',NULL,0,NULL,0,0,NULL,'127.0.0.1','未知',1,'2024-03-08 10:54:31','2024-03-08 12:21:26'),('f8616ce5aa0c8eeacfa0d997dbdbe0d3','cd853c7b475723fb4457aebe30fd39ac','f6a7dc9f2df3544912c464e7adb9ce32','PPLAX','$2a$10$HuDCsbGXZTbBgVjXryfHeeObIJHz926y9qQTb/z9GWUFHp.9d5o4i','testSalt','123486@qq.com',1,NULL,1,261,'2024-03-14 18:48:55','0:0:0:0:0:0:0:1','未知',1,'2024-02-03 07:17:56','2024-03-05 11:24:23');
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
INSERT INTO `t_user_info` (`uid`, `nickname`, `gender`, `avatar_picture_uid`, `space_background_picture_uid`, `birthday`, `summary`, `status`, `create_time`, `update_time`) VALUES ('420b5ebd162143c585dc4582c6c0319c','伤心的太刀领域大神',NULL,'64a319a35e2f1c0ba9465344bd9f699a',NULL,NULL,'玩太刀玩的',1,'2024-03-07 11:19:06','2024-03-07 11:19:06'),('92846d9fabb0458b8ec768a9681920cf','巴啦啦小魔仙',NULL,'aa7d6c427db232d3291e40488f271738',NULL,'2022-03-23',NULL,1,'2024-03-10 11:27:10','2024-03-10 11:27:10'),('A4A7B8FBB8E9623FA72556F13A97ED5F','测试用户',NULL,'c8d53a5ab9bb59c0bebb90ea914a19c9',NULL,'2024-03-14',NULL,1,'2024-03-07 11:18:56','2024-03-07 11:18:56'),('A7C9AACDC396292E3825639379041442','李康勇',1,'be3587f8c60134cef416d9de3159ba79','b79a15d8e752d0b4e6cfda3c54eede4d','2018-09-20','测试表情',1,'2024-03-10 11:26:48','2024-03-10 11:26:48'),('c4a488a16fde4cfd932570db80a8a0d7','11111',NULL,'a877cbf8b255e0170fd2eb5d6a8df193','7dd3119f19fba23c15251d38f2701920',NULL,NULL,1,'2024-03-09 02:48:30','2024-03-09 02:48:30'),('D218CD2BB34F0A91565390458A20E871','怨虎龙',1,'75eb8d169bd9c0d888552454046ce281','51f7bd20169b9ef9e8999dc25a860a1f','2018-09-20','测试表情',1,'2024-03-10 11:28:03','2024-03-10 11:28:03'),('d4b0677e07e6487993e3f6c757e6af67','123',NULL,'c8c2848cb0802c9300997055baf46b8a',NULL,NULL,NULL,1,'2024-03-08 10:54:31','2024-03-08 12:21:23'),('d98273375f9c494abd1133ecd485897c','略略略',NULL,'324308093c250ae72730337f3d8026c2',NULL,NULL,NULL,1,'2024-03-10 11:26:55','2024-03-10 11:26:55'),('e5bae0aca6ae48e98eae9a86318b7be8','不知道起什么名儿',NULL,'1f483b73b991ebbb6e6342ce4cd58218',NULL,NULL,NULL,1,'2024-03-10 11:27:46','2024-03-10 11:27:46'),('f4d7cfd1f18f481c9e8e15effc779b1c','hn123456',NULL,'e65f3587c2c276f0c07777b204c417f8',NULL,'2002-11-26',NULL,1,'2024-03-10 11:27:55','2024-03-10 11:27:55'),('f6a7dc9f2df3544912c464e7adb9ce32','普拉克斯',1,'3976f9b5692d6e5958cd84a9500e07d4','4c97865b9fd0d0f12cd19cb6caa94ce8','2024-02-03',NULL,1,'2024-03-10 11:27:38','2024-03-10 11:27:38');
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

-- Dump completed on 2024-03-14 19:18:29
