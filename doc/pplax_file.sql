-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: pplax_file
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Current Database: `pplax_file`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `pplax_file` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `pplax_file`;

--
-- Table structure for table `t_file`
--

DROP TABLE IF EXISTS `t_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_file` (
  `uid` varchar(36) NOT NULL COMMENT '唯一uid',
  `user_uid` varchar(32) DEFAULT NULL COMMENT '用户uid',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `extend_name` varchar(255) DEFAULT NULL COMMENT '文件扩展名',
  `file_path` varchar(255) DEFAULT NULL COMMENT '文件地址',
  `file_size` int DEFAULT '0' COMMENT '文件大小',
  `is_directory` int NOT NULL COMMENT '是否是目录',
  `type` varchar(255) DEFAULT 'LOCAL_STORAGE' COMMENT '存储方式类型，(七牛云、Minio、本地、阿里云OSS什么的)',
  `file_url` varchar(255) DEFAULT NULL COMMENT '文件地址',
  `file_sort_uid` varchar(36) DEFAULT NULL COMMENT '文件分类uid',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='文件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_file`
--

LOCK TABLES `t_file` WRITE;
/*!40000 ALTER TABLE `t_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_file_sort`
--

DROP TABLE IF EXISTS `t_file_sort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_file_sort` (
  `uid` varchar(36) NOT NULL COMMENT '唯一uid',
  `project_name` varchar(255) DEFAULT NULL COMMENT '项目名',
  `sort_name` varchar(255) DEFAULT NULL COMMENT '分类名',
  `url` varchar(255) DEFAULT NULL COMMENT '分类路径',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='文件分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_file_sort`
--

LOCK TABLES `t_file_sort` WRITE;
/*!40000 ALTER TABLE `t_file_sort` DISABLE KEYS */;
INSERT INTO `t_file_sort` (`uid`, `project_name`, `sort_name`, `url`, `status`, `create_time`, `update_time`) VALUES ('a9a747d944c24845815356f72723ef8e','blog','admin','/blog/admin',1,'2020-06-14 13:06:08','2020-06-14 13:06:10'),('a9a747d944c24845815356f72723ef8f','blog','web','/blog/web',1,'2020-06-14 13:06:12','2020-06-14 13:06:13');
/*!40000 ALTER TABLE `t_file_sort` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_storage`
--

DROP TABLE IF EXISTS `t_storage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_storage` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `user_uid` varchar(32) NOT NULL COMMENT '用户uid',
  `storage_size` bigint NOT NULL,
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `max_storage_size` bigint DEFAULT '0' COMMENT '最大容量大小',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='存储信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_storage`
--

LOCK TABLES `t_storage` WRITE;
/*!40000 ALTER TABLE `t_storage` DISABLE KEYS */;
INSERT INTO `t_storage` (`uid`, `user_uid`, `storage_size`, `status`, `create_time`, `update_time`, `max_storage_size`) VALUES ('1f01cd1d2f474743b241d74008b12334','1f01cd1d2f474743b241d74008b12333',7780,1,'2020-07-11 14:05:48','2020-12-11 08:11:23',10485760),('2bb52a208f2ed8592cfe2239c2d5350e','0954693eb7ebaa0b693d3c787fb8bab7',0,1,'2020-10-09 08:19:43','2020-10-10 03:18:46',524288000),('f0c2c2021a8021d079d1ed70017bb2d8','7621746caa93ce605e2de7143a3787b5',177619,1,'2020-10-09 07:17:01','2020-12-11 08:14:40',524288000),('f0c2c2021a8021d079d1ed70017bb2d9','72346fe9bfc754023e3495f2614bf18f',0,1,'2020-10-09 02:17:41','2020-10-09 02:17:41',50000);
/*!40000 ALTER TABLE `t_storage` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-29 18:04:11
