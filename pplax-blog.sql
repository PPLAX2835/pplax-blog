-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: nacos_config
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Current Database: `nacos_config`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `nacos_config` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `nacos_config`;

--
-- Table structure for table `config_info`
--

DROP TABLE IF EXISTS `config_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1046 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='config_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info`
--

LOCK TABLES `config_info` WRITE;
/*!40000 ALTER TABLE `config_info` DISABLE KEYS */;
INSERT INTO `config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (84,'transport.type','SEATA_GROUP','TCP','b136ef5f6a01d816991fe3cf7a6ac763','2022-04-13 06:40:03','2022-04-13 06:50:15','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(85,'transport.server','SEATA_GROUP','NIO','b6d9dfc0fb54277321cebc0fff55df2f','2022-04-13 06:40:03','2022-04-13 06:50:15','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(86,'transport.heartbeat','SEATA_GROUP','true','b326b5062b2f0e69046810717534cb09','2022-04-13 06:40:03','2022-04-13 06:50:15','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(87,'transport.enableTmClientBatchSendRequest','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 06:40:04','2022-04-13 06:50:16','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(88,'transport.enableRmClientBatchSendRequest','SEATA_GROUP','true','b326b5062b2f0e69046810717534cb09','2022-04-13 06:40:04','2022-04-13 06:50:16','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(89,'transport.enableTcServerBatchSendResponse','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 06:40:04','2022-04-13 06:50:16','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(90,'transport.rpcRmRequestTimeout','SEATA_GROUP','30000','5ecc613150de01b7e6824594426f24f4','2022-04-13 06:40:04','2022-04-13 06:50:16','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(91,'transport.rpcTmRequestTimeout','SEATA_GROUP','30000','5ecc613150de01b7e6824594426f24f4','2022-04-13 06:40:05','2022-04-13 06:50:16','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(92,'transport.rpcTcRequestTimeout','SEATA_GROUP','30000','5ecc613150de01b7e6824594426f24f4','2022-04-13 06:40:05','2022-04-13 06:50:16','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(93,'transport.threadFactory.bossThreadPrefix','SEATA_GROUP','NettyBoss','0f8db59a3b7f2823f38a70c308361836','2022-04-13 06:40:05','2022-04-13 06:50:17','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(94,'transport.threadFactory.workerThreadPrefix','SEATA_GROUP','NettyServerNIOWorker','a78ec7ef5d1631754c4e72ae8a3e9205','2022-04-13 06:40:06','2022-04-13 06:50:17','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(95,'transport.threadFactory.serverExecutorThreadPrefix','SEATA_GROUP','NettyServerBizHandler','11a36309f3d9df84fa8b59cf071fa2da','2022-04-13 06:40:06','2022-04-13 06:50:17','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(96,'transport.threadFactory.shareBossWorker','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 06:40:06','2022-04-13 06:50:17','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(97,'transport.threadFactory.clientSelectorThreadPrefix','SEATA_GROUP','NettyClientSelector','cd7ec5a06541e75f5a7913752322c3af','2022-04-13 06:40:07','2022-04-13 06:50:18','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(98,'transport.threadFactory.clientSelectorThreadSize','SEATA_GROUP','1','c4ca4238a0b923820dcc509a6f75849b','2022-04-13 06:40:07','2022-04-13 06:50:18','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(99,'transport.threadFactory.clientWorkerThreadPrefix','SEATA_GROUP','NettyClientWorkerThread','61cf4e69a56354cf72f46dc86414a57e','2022-04-13 06:40:08','2022-04-13 06:50:18','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(100,'transport.threadFactory.bossThreadSize','SEATA_GROUP','1','c4ca4238a0b923820dcc509a6f75849b','2022-04-13 06:40:08','2022-04-13 06:50:18','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(101,'transport.threadFactory.workerThreadSize','SEATA_GROUP','default','c21f969b5f03d33d43e04f8f136e7682','2022-04-13 06:40:08','2022-04-13 06:50:19','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(102,'transport.shutdown.wait','SEATA_GROUP','3','eccbc87e4b5ce2fe28308fd9f2a7baf3','2022-04-13 06:40:09','2022-04-13 06:50:19','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(103,'transport.serialization','SEATA_GROUP','seata','b943081c423b9a5416a706524ee05d40','2022-04-13 06:40:09','2022-04-13 06:50:19','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(104,'transport.compressor','SEATA_GROUP','none','334c4a4c42fdb79d7ebc3e73b517e6f8','2022-04-13 06:40:09','2022-04-13 06:50:19','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(105,'service.vgroupMapping.default_tx_group','SEATA_GROUP','default','c21f969b5f03d33d43e04f8f136e7682','2022-04-13 06:40:09','2022-04-13 06:50:19','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(106,'service.default.grouplist','SEATA_GROUP','127.0.0.1:8091','c32ce0d3e264525dcdada751f98143a3','2022-04-13 06:40:10','2022-04-13 06:50:20','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(107,'service.enableDegrade','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 06:40:10','2022-04-13 06:50:20','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(108,'service.disableGlobalTransaction','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 06:40:10','2022-04-13 06:50:20','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(109,'client.rm.asyncCommitBufferLimit','SEATA_GROUP','10000','b7a782741f667201b54880c925faec4b','2022-04-13 06:40:10','2022-04-13 06:50:20','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(110,'client.rm.lock.retryInterval','SEATA_GROUP','10','d3d9446802a44259755d38e6d163e820','2022-04-13 06:40:10','2022-04-13 06:50:20','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(111,'client.rm.lock.retryTimes','SEATA_GROUP','30','34173cb38f07f89ddbebc2ac9128303f','2022-04-13 06:40:11','2022-04-13 06:50:20','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(112,'client.rm.lock.retryPolicyBranchRollbackOnConflict','SEATA_GROUP','true','b326b5062b2f0e69046810717534cb09','2022-04-13 06:40:11','2022-04-13 06:50:21','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(113,'client.rm.reportRetryCount','SEATA_GROUP','5','e4da3b7fbbce2345d7772b0674a318d5','2022-04-13 06:40:11','2022-04-13 06:50:21','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(114,'client.rm.tableMetaCheckEnable','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 06:40:11','2022-04-13 06:50:21','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(115,'client.rm.tableMetaCheckerInterval','SEATA_GROUP','60000','2b4226dd7ed6eb2d419b881f3ae9c97c','2022-04-13 06:40:12','2022-04-13 06:50:21','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(116,'client.rm.sqlParserType','SEATA_GROUP','druid','3d650fb8a5df01600281d48c47c9fa60','2022-04-13 06:40:12','2022-04-13 06:50:22','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(117,'client.rm.reportSuccessEnable','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 06:40:12','2022-04-13 06:50:22','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(118,'client.rm.sagaBranchRegisterEnable','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 06:40:12','2022-04-13 06:50:22','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(119,'client.rm.sagaJsonParser','SEATA_GROUP','fastjson','d00d3dbc0834f08411c7b6c4c39e9f00','2022-04-13 06:40:13','2022-04-13 06:50:22','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(120,'client.rm.tccActionInterceptorOrder','SEATA_GROUP','-2147482648','f056d9efa5dae3872f9da035c83bcde8','2022-04-13 06:40:13','2022-04-13 06:50:22','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(121,'client.tm.commitRetryCount','SEATA_GROUP','5','e4da3b7fbbce2345d7772b0674a318d5','2022-04-13 06:40:13','2022-04-13 06:50:23','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(122,'client.tm.rollbackRetryCount','SEATA_GROUP','5','e4da3b7fbbce2345d7772b0674a318d5','2022-04-13 06:40:13','2022-04-13 06:50:23','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(123,'client.tm.defaultGlobalTransactionTimeout','SEATA_GROUP','60000','2b4226dd7ed6eb2d419b881f3ae9c97c','2022-04-13 06:40:14','2022-04-13 06:50:23','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(124,'client.tm.degradeCheck','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 06:40:14','2022-04-13 06:50:23','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(125,'client.tm.degradeCheckAllowTimes','SEATA_GROUP','10','d3d9446802a44259755d38e6d163e820','2022-04-13 06:40:14','2022-04-13 06:50:23','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(126,'client.tm.degradeCheckPeriod','SEATA_GROUP','2000','08f90c1a417155361a5c4b8d297e0d78','2022-04-13 06:40:15','2022-04-13 06:50:23','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(127,'client.tm.interceptorOrder','SEATA_GROUP','-2147482648','f056d9efa5dae3872f9da035c83bcde8','2022-04-13 06:40:15','2022-04-13 06:50:24','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(128,'client.undo.dataValidation','SEATA_GROUP','true','b326b5062b2f0e69046810717534cb09','2022-04-13 06:40:15','2022-04-13 06:50:24','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(129,'client.undo.logSerialization','SEATA_GROUP','jackson','b41779690b83f182acc67d6388c7bac9','2022-04-13 06:40:15','2022-04-13 06:50:24','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(130,'client.undo.onlyCareUpdateColumns','SEATA_GROUP','true','b326b5062b2f0e69046810717534cb09','2022-04-13 06:40:16','2022-04-13 06:50:24','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(131,'server.undo.logSaveDays','SEATA_GROUP','7','8f14e45fceea167a5a36dedd4bea2543','2022-04-13 06:40:16','2022-04-13 06:50:24','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(132,'server.undo.logDeletePeriod','SEATA_GROUP','86400000','f4c122804fe9076cb2710f55c3c6e346','2022-04-13 06:40:16','2022-04-13 06:50:25','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(133,'client.undo.logTable','SEATA_GROUP','undo_log','2842d229c24afe9e61437135e8306614','2022-04-13 06:40:16','2022-04-13 06:50:25','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(134,'client.undo.compress.enable','SEATA_GROUP','true','b326b5062b2f0e69046810717534cb09','2022-04-13 06:40:16','2022-04-13 06:50:25','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(135,'client.undo.compress.type','SEATA_GROUP','zip','adcdbd79a8d84175c229b192aadc02f2','2022-04-13 06:40:17','2022-04-13 06:50:25','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(136,'client.undo.compress.threshold','SEATA_GROUP','64k','bd44a6458bdbff0b5cac721ba361f035','2022-04-13 06:40:17','2022-04-13 06:50:25','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(137,'tcc.fence.logTableName','SEATA_GROUP','tcc_fence_log','db229b665c7cfd5abc03971d4ed284c6','2022-04-13 06:40:17','2022-04-13 06:50:26','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(138,'tcc.fence.cleanPeriod','SEATA_GROUP','1h','7c68645d71b803bf0ba2f22519f73e08','2022-04-13 06:40:17','2022-04-13 06:50:26','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(139,'log.exceptionRate','SEATA_GROUP','100','f899139df5e1059396431415e770c6dd','2022-04-13 06:40:17','2022-04-13 06:50:26','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(140,'store.mode','SEATA_GROUP','db','d77d5e503ad1439f585ac494268b351b','2022-04-13 06:40:17','2022-04-13 06:50:26','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(141,'store.lock.mode','SEATA_GROUP','file','8c7dd922ad47494fc02c388e12c00eac','2022-04-13 06:40:18','2022-04-13 06:50:26','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(142,'store.session.mode','SEATA_GROUP','file','8c7dd922ad47494fc02c388e12c00eac','2022-04-13 06:40:18','2022-04-13 06:50:26','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(143,'store.file.dir','SEATA_GROUP','file_store/data','6a8dec07c44c33a8a9247cba5710bbb2','2022-04-13 06:40:18','2022-04-13 06:50:27','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(144,'store.file.maxBranchSessionSize','SEATA_GROUP','16384','c76fe1d8e08462434d800487585be217','2022-04-13 06:40:18','2022-04-13 06:50:27','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(145,'store.file.maxGlobalSessionSize','SEATA_GROUP','512','10a7cdd970fe135cf4f7bb55c0e3b59f','2022-04-13 06:40:18','2022-04-13 06:50:27','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(146,'store.file.fileWriteBufferCacheSize','SEATA_GROUP','16384','c76fe1d8e08462434d800487585be217','2022-04-13 06:40:19','2022-04-13 06:50:28','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(147,'store.file.flushDiskMode','SEATA_GROUP','async','0df93e34273b367bb63bad28c94c78d5','2022-04-13 06:40:19','2022-04-13 06:50:28','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(148,'store.file.sessionReloadReadSize','SEATA_GROUP','100','f899139df5e1059396431415e770c6dd','2022-04-13 06:40:19','2022-04-13 06:50:28','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(149,'store.db.datasource','SEATA_GROUP','druid','3d650fb8a5df01600281d48c47c9fa60','2022-04-13 06:40:19','2022-04-13 06:50:28','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(150,'store.db.dbType','SEATA_GROUP','mysql','81c3b080dad537de7e10e0987a4bf52e','2022-04-13 06:40:20','2022-04-13 06:50:28','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(151,'store.db.driverClassName','SEATA_GROUP','com.mysql.cj.jdbc.Driver','33763409bb7f4838bde4fae9540433e4','2022-04-13 06:40:20','2022-04-13 06:50:29','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(152,'store.db.url','SEATA_GROUP','jdbc:mysql://127.0.0.1:3306/seata?useUnicode=true&rewriteBatchedStatements=true','030ea5ff5c2ef043adf9826c70570b0b','2022-04-13 06:40:20','2022-04-13 06:50:29','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(153,'store.db.user','SEATA_GROUP','root','63a9f0ea7bb98050796b649e85481845','2022-04-13 06:40:21','2022-04-13 06:50:29','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(154,'store.db.password','SEATA_GROUP','Cqy19981202','8fce5c1254e0615fab810a3d55c3a9f1','2022-04-13 06:40:21','2022-04-13 06:50:30','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(155,'store.db.minConn','SEATA_GROUP','5','e4da3b7fbbce2345d7772b0674a318d5','2022-04-13 06:40:21','2022-04-13 06:50:30','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(156,'store.db.maxConn','SEATA_GROUP','30','34173cb38f07f89ddbebc2ac9128303f','2022-04-13 06:40:21','2022-04-13 06:50:30','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(157,'store.db.globalTable','SEATA_GROUP','global_table','8b28fb6bb4c4f984df2709381f8eba2b','2022-04-13 06:40:21','2022-04-13 06:50:30','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(158,'store.db.branchTable','SEATA_GROUP','branch_table','54bcdac38cf62e103fe115bcf46a660c','2022-04-13 06:40:22','2022-04-13 06:50:30','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(159,'store.db.distributedLockTable','SEATA_GROUP','distributed_lock','26146b7a3a4907101617cb0f19bf613f','2022-04-13 06:40:22','2022-04-13 06:50:31','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(160,'store.db.queryLimit','SEATA_GROUP','100','f899139df5e1059396431415e770c6dd','2022-04-13 06:40:22','2022-04-13 06:50:31','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(161,'store.db.lockTable','SEATA_GROUP','lock_table','55e0cae3b6dc6696b768db90098b8f2f','2022-04-13 06:40:22','2022-04-13 06:50:31','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(162,'store.db.maxWait','SEATA_GROUP','5000','a35fe7f7fe8217b4369a0af4244d1fca','2022-04-13 06:40:22','2022-04-13 06:50:31','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(163,'store.redis.mode','SEATA_GROUP','single','dd5c07036f2975ff4bce568b6511d3bc','2022-04-13 06:40:23','2022-04-13 06:50:31','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(164,'store.redis.single.host','SEATA_GROUP','127.0.0.1','f528764d624db129b32c21fbca0cb8d6','2022-04-13 06:40:23','2022-04-13 06:50:32','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(165,'store.redis.single.port','SEATA_GROUP','6379','92c3b916311a5517d9290576e3ea37ad','2022-04-13 06:40:23','2022-04-13 06:50:32','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(166,'store.redis.maxConn','SEATA_GROUP','10','d3d9446802a44259755d38e6d163e820','2022-04-13 06:40:24','2022-04-13 06:50:32','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(167,'store.redis.minConn','SEATA_GROUP','1','c4ca4238a0b923820dcc509a6f75849b','2022-04-13 06:40:24','2022-04-13 06:50:33','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(168,'store.redis.maxTotal','SEATA_GROUP','100','f899139df5e1059396431415e770c6dd','2022-04-13 06:40:24','2022-04-13 06:50:33','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(169,'store.redis.database','SEATA_GROUP','0','cfcd208495d565ef66e7dff9f98764da','2022-04-13 06:40:24','2022-04-13 06:50:33','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(170,'store.redis.queryLimit','SEATA_GROUP','100','f899139df5e1059396431415e770c6dd','2022-04-13 06:40:24','2022-04-13 06:50:33','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(171,'server.recovery.committingRetryPeriod','SEATA_GROUP','1000','a9b7ba70783b617e9998dc4dd82eb3c5','2022-04-13 06:40:25','2022-04-13 06:50:34','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(172,'server.recovery.asynCommittingRetryPeriod','SEATA_GROUP','1000','a9b7ba70783b617e9998dc4dd82eb3c5','2022-04-13 06:40:25','2022-04-13 06:50:34','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(173,'server.recovery.rollbackingRetryPeriod','SEATA_GROUP','1000','a9b7ba70783b617e9998dc4dd82eb3c5','2022-04-13 06:40:25','2022-04-13 06:50:34','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(174,'server.recovery.timeoutRetryPeriod','SEATA_GROUP','1000','a9b7ba70783b617e9998dc4dd82eb3c5','2022-04-13 06:40:26','2022-04-13 06:50:34','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(175,'server.maxCommitRetryTimeout','SEATA_GROUP','-1','6bb61e3b7bce0931da574d19d1d82c88','2022-04-13 06:40:26','2022-04-13 06:50:35','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(176,'server.maxRollbackRetryTimeout','SEATA_GROUP','-1','6bb61e3b7bce0931da574d19d1d82c88','2022-04-13 06:40:26','2022-04-13 06:50:35','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(177,'server.rollbackRetryTimeoutUnlockEnable','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 06:40:26','2022-04-13 06:50:35','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(178,'server.distributedLockExpireTime','SEATA_GROUP','10000','b7a782741f667201b54880c925faec4b','2022-04-13 06:40:27','2022-04-13 06:50:35','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(179,'server.xaerNotaRetryTimeout','SEATA_GROUP','60000','2b4226dd7ed6eb2d419b881f3ae9c97c','2022-04-13 06:40:27','2022-04-13 06:50:36','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(180,'server.session.branchAsyncQueueSize','SEATA_GROUP','5000','a35fe7f7fe8217b4369a0af4244d1fca','2022-04-13 06:40:27','2022-04-13 06:50:36','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(181,'server.session.enableBranchAsyncRemove','SEATA_GROUP','true','b326b5062b2f0e69046810717534cb09','2022-04-13 06:40:27','2022-04-13 06:50:36','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(182,'metrics.enabled','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 06:40:28','2022-04-13 06:50:36','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(183,'metrics.registryType','SEATA_GROUP','compact','7cf74ca49c304df8150205fc915cd465','2022-04-13 06:40:28','2022-04-13 06:50:37','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(184,'metrics.exporterList','SEATA_GROUP','prometheus','e4f00638b8a10e6994e67af2f832d51c','2022-04-13 06:40:28','2022-04-13 06:50:37','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(185,'metrics.exporterPrometheusPort','SEATA_GROUP','9898','7b9dc501afe4ee11c56a4831e20cee71','2022-04-13 06:40:28','2022-04-13 06:50:37','nacos','0:0:0:0:0:0:0:1','','5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca',NULL,NULL,NULL,'text',NULL,''),(716,'transport.type','SEATA_GROUP','TCP','b136ef5f6a01d816991fe3cf7a6ac763','2022-04-13 13:29:12','2022-04-13 13:29:12','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(717,'transport.server','SEATA_GROUP','NIO','b6d9dfc0fb54277321cebc0fff55df2f','2022-04-13 13:29:12','2022-04-13 13:29:12','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(718,'transport.heartbeat','SEATA_GROUP','true','b326b5062b2f0e69046810717534cb09','2022-04-13 13:29:13','2022-04-13 13:29:13','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(719,'transport.enableTmClientBatchSendRequest','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 13:29:13','2022-04-13 13:29:13','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(720,'transport.enableRmClientBatchSendRequest','SEATA_GROUP','true','b326b5062b2f0e69046810717534cb09','2022-04-13 13:29:13','2022-04-13 13:29:13','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(721,'transport.enableTcServerBatchSendResponse','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 13:29:13','2022-04-13 13:29:13','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(722,'transport.rpcRmRequestTimeout','SEATA_GROUP','30000','5ecc613150de01b7e6824594426f24f4','2022-04-13 13:29:13','2022-04-13 13:29:13','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(723,'transport.rpcTmRequestTimeout','SEATA_GROUP','30000','5ecc613150de01b7e6824594426f24f4','2022-04-13 13:29:13','2022-04-13 13:29:13','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(724,'transport.rpcTcRequestTimeout','SEATA_GROUP','30000','5ecc613150de01b7e6824594426f24f4','2022-04-13 13:29:14','2022-04-13 13:29:14','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(725,'transport.threadFactory.bossThreadPrefix','SEATA_GROUP','NettyBoss','0f8db59a3b7f2823f38a70c308361836','2022-04-13 13:29:14','2022-04-13 13:29:14','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(726,'transport.threadFactory.workerThreadPrefix','SEATA_GROUP','NettyServerNIOWorker','a78ec7ef5d1631754c4e72ae8a3e9205','2022-04-13 13:29:14','2022-04-13 13:29:14','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(727,'transport.threadFactory.serverExecutorThreadPrefix','SEATA_GROUP','NettyServerBizHandler','11a36309f3d9df84fa8b59cf071fa2da','2022-04-13 13:29:14','2022-04-13 13:29:14','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(728,'transport.threadFactory.shareBossWorker','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 13:29:14','2022-04-13 13:29:14','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(729,'transport.threadFactory.clientSelectorThreadPrefix','SEATA_GROUP','NettyClientSelector','cd7ec5a06541e75f5a7913752322c3af','2022-04-13 13:29:15','2022-04-13 13:29:15','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(730,'transport.threadFactory.clientSelectorThreadSize','SEATA_GROUP','1','c4ca4238a0b923820dcc509a6f75849b','2022-04-13 13:29:15','2022-04-13 13:29:15','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(731,'transport.threadFactory.clientWorkerThreadPrefix','SEATA_GROUP','NettyClientWorkerThread','61cf4e69a56354cf72f46dc86414a57e','2022-04-13 13:29:15','2022-04-13 13:29:15','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(732,'transport.threadFactory.bossThreadSize','SEATA_GROUP','1','c4ca4238a0b923820dcc509a6f75849b','2022-04-13 13:29:15','2022-04-13 13:29:15','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(733,'transport.threadFactory.workerThreadSize','SEATA_GROUP','default','c21f969b5f03d33d43e04f8f136e7682','2022-04-13 13:29:16','2022-04-13 13:29:16','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(734,'transport.shutdown.wait','SEATA_GROUP','3','eccbc87e4b5ce2fe28308fd9f2a7baf3','2022-04-13 13:29:16','2022-04-13 13:29:16','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(735,'transport.serialization','SEATA_GROUP','seata','b943081c423b9a5416a706524ee05d40','2022-04-13 13:29:16','2022-04-13 13:29:16','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(736,'transport.compressor','SEATA_GROUP','none','334c4a4c42fdb79d7ebc3e73b517e6f8','2022-04-13 13:29:16','2022-04-13 13:29:16','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(738,'service.default.grouplist','SEATA_GROUP','127.0.0.1:8091','c32ce0d3e264525dcdada751f98143a3','2022-04-13 13:29:17','2022-04-13 13:29:17','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(739,'service.enableDegrade','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 13:29:17','2022-04-13 13:29:17','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(740,'service.disableGlobalTransaction','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 13:29:17','2022-04-13 13:29:17','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(741,'client.rm.asyncCommitBufferLimit','SEATA_GROUP','10000','b7a782741f667201b54880c925faec4b','2022-04-13 13:29:17','2022-04-13 13:29:17','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(742,'client.rm.lock.retryInterval','SEATA_GROUP','10','d3d9446802a44259755d38e6d163e820','2022-04-13 13:29:17','2022-04-13 13:29:17','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(743,'client.rm.lock.retryTimes','SEATA_GROUP','30','34173cb38f07f89ddbebc2ac9128303f','2022-04-13 13:29:17','2022-04-13 13:29:17','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(744,'client.rm.lock.retryPolicyBranchRollbackOnConflict','SEATA_GROUP','true','b326b5062b2f0e69046810717534cb09','2022-04-13 13:29:18','2022-04-13 13:29:18','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(745,'client.rm.reportRetryCount','SEATA_GROUP','5','e4da3b7fbbce2345d7772b0674a318d5','2022-04-13 13:29:18','2022-04-13 13:29:18','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(746,'client.rm.tableMetaCheckEnable','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 13:29:18','2022-04-13 13:29:18','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(747,'client.rm.tableMetaCheckerInterval','SEATA_GROUP','60000','2b4226dd7ed6eb2d419b881f3ae9c97c','2022-04-13 13:29:18','2022-04-13 13:29:18','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(748,'client.rm.sqlParserType','SEATA_GROUP','druid','3d650fb8a5df01600281d48c47c9fa60','2022-04-13 13:29:18','2022-04-13 13:29:18','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(749,'client.rm.reportSuccessEnable','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 13:29:18','2022-04-13 13:29:18','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(750,'client.rm.sagaBranchRegisterEnable','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 13:29:18','2022-04-13 13:29:18','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(751,'client.rm.sagaJsonParser','SEATA_GROUP','fastjson','d00d3dbc0834f08411c7b6c4c39e9f00','2022-04-13 13:29:19','2022-04-13 13:29:19','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(752,'client.rm.tccActionInterceptorOrder','SEATA_GROUP','-2147482648','f056d9efa5dae3872f9da035c83bcde8','2022-04-13 13:29:19','2022-04-13 13:29:19','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(753,'client.tm.commitRetryCount','SEATA_GROUP','5','e4da3b7fbbce2345d7772b0674a318d5','2022-04-13 13:29:19','2022-04-13 13:29:19','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(754,'client.tm.rollbackRetryCount','SEATA_GROUP','5','e4da3b7fbbce2345d7772b0674a318d5','2022-04-13 13:29:19','2022-04-13 13:29:19','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(755,'client.tm.defaultGlobalTransactionTimeout','SEATA_GROUP','60000','2b4226dd7ed6eb2d419b881f3ae9c97c','2022-04-13 13:29:19','2022-04-13 13:29:19','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(756,'client.tm.degradeCheck','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 13:29:20','2022-04-13 13:29:20','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(757,'client.tm.degradeCheckAllowTimes','SEATA_GROUP','10','d3d9446802a44259755d38e6d163e820','2022-04-13 13:29:20','2022-04-13 13:29:20','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(758,'client.tm.degradeCheckPeriod','SEATA_GROUP','2000','08f90c1a417155361a5c4b8d297e0d78','2022-04-13 13:29:20','2022-04-13 13:29:20','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(759,'client.tm.interceptorOrder','SEATA_GROUP','-2147482648','f056d9efa5dae3872f9da035c83bcde8','2022-04-13 13:29:20','2022-04-13 13:29:20','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(760,'client.undo.dataValidation','SEATA_GROUP','true','b326b5062b2f0e69046810717534cb09','2022-04-13 13:29:20','2022-04-13 13:29:20','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(761,'client.undo.logSerialization','SEATA_GROUP','kryo','d78f017576c8b3ad5beec73e6c39a59e','2022-04-13 13:29:20','2022-04-23 03:22:32','nacos','0:0:0:0:0:0:0:1','','','','','','text','',''),(762,'client.undo.onlyCareUpdateColumns','SEATA_GROUP','true','b326b5062b2f0e69046810717534cb09','2022-04-13 13:29:20','2022-04-13 13:29:20','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(763,'server.undo.logSaveDays','SEATA_GROUP','7','8f14e45fceea167a5a36dedd4bea2543','2022-04-13 13:29:21','2022-04-13 13:29:21','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(764,'server.undo.logDeletePeriod','SEATA_GROUP','86400000','f4c122804fe9076cb2710f55c3c6e346','2022-04-13 13:29:21','2022-04-13 13:29:21','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(765,'client.undo.logTable','SEATA_GROUP','undo_log','2842d229c24afe9e61437135e8306614','2022-04-13 13:29:21','2022-04-13 13:29:21','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(766,'client.undo.compress.enable','SEATA_GROUP','true','b326b5062b2f0e69046810717534cb09','2022-04-13 13:29:21','2022-04-13 13:29:21','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(767,'client.undo.compress.type','SEATA_GROUP','zip','adcdbd79a8d84175c229b192aadc02f2','2022-04-13 13:29:21','2022-04-13 13:29:21','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(768,'client.undo.compress.threshold','SEATA_GROUP','64k','bd44a6458bdbff0b5cac721ba361f035','2022-04-13 13:29:21','2022-04-13 13:29:21','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(769,'tcc.fence.logTableName','SEATA_GROUP','tcc_fence_log','db229b665c7cfd5abc03971d4ed284c6','2022-04-13 13:29:22','2022-04-13 13:29:22','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(770,'tcc.fence.cleanPeriod','SEATA_GROUP','1h','7c68645d71b803bf0ba2f22519f73e08','2022-04-13 13:29:22','2022-04-13 13:29:22','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(771,'log.exceptionRate','SEATA_GROUP','100','f899139df5e1059396431415e770c6dd','2022-04-13 13:29:22','2022-04-13 13:29:22','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(772,'store.mode','SEATA_GROUP','file','8c7dd922ad47494fc02c388e12c00eac','2022-04-13 13:29:22','2022-04-13 13:29:22','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(773,'store.lock.mode','SEATA_GROUP','file','8c7dd922ad47494fc02c388e12c00eac','2022-04-13 13:29:22','2022-04-13 13:29:22','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(774,'store.session.mode','SEATA_GROUP','file','8c7dd922ad47494fc02c388e12c00eac','2022-04-13 13:29:22','2022-04-13 13:29:22','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(775,'store.db.datasource','SEATA_GROUP','druid','3d650fb8a5df01600281d48c47c9fa60','2022-04-13 13:29:22','2022-04-13 13:29:22','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(776,'store.db.dbType','SEATA_GROUP','mysql','81c3b080dad537de7e10e0987a4bf52e','2022-04-13 13:29:22','2022-04-13 13:29:22','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(777,'store.db.driverClassName','SEATA_GROUP','com.mysql.cj.jdbc.Driver','33763409bb7f4838bde4fae9540433e4','2022-04-13 13:29:23','2022-04-13 13:29:23','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(778,'store.db.url','SEATA_GROUP','jdbc:mysql://127.0.0.1:3306/seata?useUnicode=true&rewriteBatchedStatements=true','030ea5ff5c2ef043adf9826c70570b0b','2022-04-13 13:29:23','2022-04-13 13:29:23','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(779,'store.db.user','SEATA_GROUP','root','63a9f0ea7bb98050796b649e85481845','2022-04-13 13:29:23','2022-04-13 13:29:23','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(780,'store.db.password','SEATA_GROUP','Cqy19981202','8fce5c1254e0615fab810a3d55c3a9f1','2022-04-13 13:29:24','2022-04-13 13:29:24','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(781,'store.db.minConn','SEATA_GROUP','5','e4da3b7fbbce2345d7772b0674a318d5','2022-04-13 13:29:24','2022-04-13 13:29:24','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(782,'store.db.maxConn','SEATA_GROUP','30','34173cb38f07f89ddbebc2ac9128303f','2022-04-13 13:29:24','2022-04-13 13:29:24','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(783,'store.db.globalTable','SEATA_GROUP','global_table','8b28fb6bb4c4f984df2709381f8eba2b','2022-04-13 13:29:24','2022-04-13 13:29:24','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(784,'store.db.branchTable','SEATA_GROUP','branch_table','54bcdac38cf62e103fe115bcf46a660c','2022-04-13 13:29:24','2022-04-13 13:29:24','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(785,'store.db.distributedLockTable','SEATA_GROUP','distributed_lock','26146b7a3a4907101617cb0f19bf613f','2022-04-13 13:29:24','2022-04-13 13:29:24','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(786,'store.db.queryLimit','SEATA_GROUP','100','f899139df5e1059396431415e770c6dd','2022-04-13 13:29:25','2022-04-13 13:29:25','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(787,'store.db.lockTable','SEATA_GROUP','lock_table','55e0cae3b6dc6696b768db90098b8f2f','2022-04-13 13:29:25','2022-04-13 13:29:25','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(788,'store.db.maxWait','SEATA_GROUP','5000','a35fe7f7fe8217b4369a0af4244d1fca','2022-04-13 13:29:25','2022-04-13 13:29:25','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(789,'store.redis.mode','SEATA_GROUP','single','dd5c07036f2975ff4bce568b6511d3bc','2022-04-13 13:29:25','2022-04-13 13:29:25','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(790,'store.redis.single.host','SEATA_GROUP','127.0.0.1','f528764d624db129b32c21fbca0cb8d6','2022-04-13 13:29:25','2022-04-13 13:29:25','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(791,'store.redis.single.port','SEATA_GROUP','6379','92c3b916311a5517d9290576e3ea37ad','2022-04-13 13:29:25','2022-04-13 13:29:25','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(792,'store.redis.sentinel.masterName','SEATA_GROUP','null','37a6259cc0c1dae299a7866489dff0bd','2022-04-13 13:29:26','2022-04-13 13:29:26','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(793,'store.redis.sentinel.sentinelHosts','SEATA_GROUP','null','37a6259cc0c1dae299a7866489dff0bd','2022-04-13 13:29:26','2022-04-13 13:29:26','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(794,'store.redis.maxConn','SEATA_GROUP','10','d3d9446802a44259755d38e6d163e820','2022-04-13 13:29:26','2022-04-13 13:29:26','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(795,'store.redis.minConn','SEATA_GROUP','1','c4ca4238a0b923820dcc509a6f75849b','2022-04-13 13:29:26','2022-04-13 13:29:26','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(796,'store.redis.maxTotal','SEATA_GROUP','100','f899139df5e1059396431415e770c6dd','2022-04-13 13:29:26','2022-04-13 13:29:26','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(797,'store.redis.database','SEATA_GROUP','0','cfcd208495d565ef66e7dff9f98764da','2022-04-13 13:29:27','2022-04-13 13:29:27','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(798,'store.redis.password','SEATA_GROUP','null','37a6259cc0c1dae299a7866489dff0bd','2022-04-13 13:29:27','2022-04-13 13:29:27','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(799,'store.redis.queryLimit','SEATA_GROUP','100','f899139df5e1059396431415e770c6dd','2022-04-13 13:29:27','2022-04-13 13:29:27','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(800,'server.recovery.committingRetryPeriod','SEATA_GROUP','1000','a9b7ba70783b617e9998dc4dd82eb3c5','2022-04-13 13:29:27','2022-04-13 13:29:27','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(801,'server.recovery.asynCommittingRetryPeriod','SEATA_GROUP','1000','a9b7ba70783b617e9998dc4dd82eb3c5','2022-04-13 13:29:27','2022-04-13 13:29:27','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(802,'server.recovery.rollbackingRetryPeriod','SEATA_GROUP','1000','a9b7ba70783b617e9998dc4dd82eb3c5','2022-04-13 13:29:28','2022-04-13 13:29:28','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(803,'server.recovery.timeoutRetryPeriod','SEATA_GROUP','1000','a9b7ba70783b617e9998dc4dd82eb3c5','2022-04-13 13:29:28','2022-04-13 13:29:28','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(804,'server.maxCommitRetryTimeout','SEATA_GROUP','-1','6bb61e3b7bce0931da574d19d1d82c88','2022-04-13 13:29:28','2022-04-13 13:29:28','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(805,'server.maxRollbackRetryTimeout','SEATA_GROUP','-1','6bb61e3b7bce0931da574d19d1d82c88','2022-04-13 13:29:28','2022-04-13 13:29:28','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(806,'server.rollbackRetryTimeoutUnlockEnable','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 13:29:29','2022-04-13 13:29:29','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(807,'server.distributedLockExpireTime','SEATA_GROUP','10000','b7a782741f667201b54880c925faec4b','2022-04-13 13:29:29','2022-04-13 13:29:29','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(808,'server.xaerNotaRetryTimeout','SEATA_GROUP','60000','2b4226dd7ed6eb2d419b881f3ae9c97c','2022-04-13 13:29:29','2022-04-13 13:29:29','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(809,'server.session.branchAsyncQueueSize','SEATA_GROUP','5000','a35fe7f7fe8217b4369a0af4244d1fca','2022-04-13 13:29:29','2022-04-13 13:29:29','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(810,'server.session.enableBranchAsyncRemove','SEATA_GROUP','true','b326b5062b2f0e69046810717534cb09','2022-04-13 13:29:29','2022-04-13 13:29:29','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(811,'metrics.enabled','SEATA_GROUP','false','68934a3e9455fa72420237eb05902327','2022-04-13 13:29:30','2022-04-13 13:29:30','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(812,'metrics.registryType','SEATA_GROUP','compact','7cf74ca49c304df8150205fc915cd465','2022-04-13 13:29:30','2022-04-13 13:29:30','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(813,'metrics.exporterList','SEATA_GROUP','prometheus','e4f00638b8a10e6994e67af2f832d51c','2022-04-13 13:29:30','2022-04-13 13:29:30','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(814,'metrics.exporterPrometheusPort','SEATA_GROUP','9898','7b9dc501afe4ee11c56a4831e20cee71','2022-04-13 13:29:30','2022-04-13 13:29:30','','0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(816,'service.vgroupMapping.pplax-blog-tx-group','SEATA_GROUP','default','c21f969b5f03d33d43e04f8f136e7682','2022-05-03 08:25:43','2022-05-03 08:25:43',NULL,'0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'text',NULL,''),(820,'nacos-config-dev.properties','DEFAULT_GROUP','user.name=nacos-config-properties','7ff1687d091968f281131f6a9244d6b4','2022-05-19 10:39:08','2022-05-19 10:39:08',NULL,'0:0:0:0:0:0:0:1','','',NULL,NULL,NULL,'properties',NULL,''),(847,'share-redis-dev.yaml','THEME-GROUP','spring:\n  redis:\n    database: 1\n    host: 127.0.0.1\n    password: \'\'\n    port: 6379\n    jedis:\n      pool:\n        max-active: 15\n        max-idle: 10\n        min-idle: 0\n        max-wait: -1ms','b4028a48ed2667dade920177fbe8e613','2022-05-19 23:49:25','2023-09-01 09:12:28',NULL,'172.18.0.1','','a6b1905f-d8c6-4a43-8f21-fe662f62bdae','','','','yaml','',''),(848,'share-sentinel-dev.yaml','THEME-GROUP','spring:\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 127.0.0.1:18080','1a301f4c3ac9cd5bcf18ab0754ba7285','2022-05-19 23:50:08','2023-09-01 17:42:51',NULL,'172.18.0.1','','a6b1905f-d8c6-4a43-8f21-fe662f62bdae','','','','yaml','',''),(849,'share-rabbitmq-dev.yaml','THEME-GROUP','spring:\n  rabbitmq:\n    host: 127.0.0.1\n    port: 5672\n    username: guest\n    password: guest\n    publisher-returns: true\n    publisher-confirm-type: correlated\n    listener:\n      direct:\n        acknowledge-mode: manual\n      simple:\n        acknowledge-mode: manual','ef15505782a49ca54cbd3096eaf2f3da','2022-05-19 23:50:50','2023-09-01 16:50:53',NULL,'172.18.0.1','','a6b1905f-d8c6-4a43-8f21-fe662f62bdae','','','','yaml','',''),(850,'share-pplax-oauth-dev.yaml','THEME-GROUP','# token验证和生成用到的秘钥\npplax:\n  oauth:\n    secret-key: pplaxaadkfhasdfuywasdfkjahdsfuewkrnbsdjtywq8745ghsdfsgdfhsdfgerbjhsdtfquewrw9erygtwertfysatf','e41685372342053eec5d5d26e53f732f','2022-05-19 23:52:03','2022-05-19 23:52:03',NULL,'0:0:0:0:0:0:0:1','','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',NULL,NULL,NULL,'yaml',NULL,''),(851,'share-actuator-dev.yaml','THEME-GROUP','\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"','76f4e71d8ec547c2d940ab52c7a14aaf','2022-05-19 23:53:01','2022-05-19 23:53:01',NULL,'0:0:0:0:0:0:0:1','','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',NULL,NULL,NULL,'yaml',NULL,''),(852,'share-pagehelper-dev.yaml','THEME-GROUP','# 分页插件\npagehelper:\n  helperDialect: mysql\n  reasonable: true\n  supportMethodsArguments: true\n  params: count=countSql','51cdaf050077cc14350cc9cd2d8d207a','2022-05-19 23:53:42','2022-05-19 23:53:42',NULL,'0:0:0:0:0:0:0:1','','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',NULL,NULL,NULL,'yaml',NULL,''),(853,'share-mybatis-dev.yaml','THEME-GROUP','mybatis:\n  configuration:\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl','ac0e247b309081c92f966a8de49234e0','2022-05-19 23:54:16','2022-05-19 23:54:16',NULL,'0:0:0:0:0:0:0:1','','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',NULL,NULL,NULL,'yaml',NULL,''),(854,'share-feign-dev.yaml','THEME-GROUP','feign:\n  sentinel:\n    enabled: true\n  client:\n    config:\n      default:\n        connectTimeout: 5000\n        readTimeout: 5000','03e10c99cce614ba75c6d3306ab8e5f2','2022-05-19 23:56:55','2022-05-19 23:56:55',NULL,'0:0:0:0:0:0:0:1','','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',NULL,NULL,NULL,'yaml',NULL,''),(855,'pplax-auth-server-dev.yaml','THEME-GROUP','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/pplax_auth_server?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: root\n\nlogging:\n  level:\n    xyz.xcye.web.common.service.feign.MessageLogFeignService: DEBUG\npplax:\n  oauth:\n    access-token-validity-seconds: 259200\n    refresh-token-validity-seconds: 259200\n    redis-delete-retry: 4\n    max-login-failure: 4\n    re-login-minute: 5\n    tx-map-api: https://apis.map.qq.com/ws/location/v1/ip?key=PU6BZ-VVO6V-66QP2-UNRJQ-K2JCO-24BCX\n  snow-flake-datacenter-id: 1\n  snow-flake-worker-id: 2\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: lax1458667357@gmail.com\n    api-info:\n      title: 认证中心API\n      description: 这是pplax主题的博客的认证中心api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','179913462b89f99d1411203d860c84e5','2022-05-19 23:57:06','2023-09-01 09:16:25',NULL,'172.18.0.1','','a6b1905f-d8c6-4a43-8f21-fe662f62bdae','','','','yaml','',''),(856,'share-seata-dev.yaml','THEME-GROUP','seata:\n  enabled: true\n  application-id: applicationName\n  tx-service-group: pplax-blog-tx-group\n  enable-auto-data-source-proxy: true\n  config:\n    type: nacos\n    nacos:\n      serverAddr: 127.0.0.1:8848\n      group: SEATA_GROUP\n      username: \"nacos\"\n      password: \"nacos\"\n  registry:\n    type: nacos\n    nacos:\n      application: seata-server\n      server-addr: 127.0.0.1:8848\n      group: SEATA_GROUP\n#      namespace:\n      username: \"nacos\"\n      password: \"nacos\"\n  service:\n    vgroup-mapping:\n      pplax-blog-tx-group: default','0807680b86adace52670ee1bc9caad97','2022-05-20 00:08:00','2023-09-01 09:56:31',NULL,'172.18.0.1','','a6b1905f-d8c6-4a43-8f21-fe662f62bdae','','','','yaml','',''),(857,'pplax-message-dev.yaml','THEME-GROUP','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/pplax_email?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: root\n  mail:\n    host: smtp.qq.com\n    username: lax1458667357@gmail.com\n    password: uutqhjyodxtrdiea\n    protocol: smtps\n    properties.mail.smtp.port: 465\n    properties.mail.smtp.starttls.enable: true\n    properties.mail.smtp.starttlls.required: true\n    properties.mail.smtp.ssl.enable: true\n    default-encoding: utf-8\n\n# 自定义的配置\npplax:\n  snow-flake-worker-id: 14\n  snow-flake-datacenter-id: 24\n  amqp:\n    amqp-max-retry-consume: 3\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客消息API\n      description: 这是pplax主题的博客消息api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE\nlogging:\n  level:\n    root: info\n','9ede857e7355cce1dc2ff1831a127db2','2022-05-20 00:11:22','2023-09-01 17:44:08',NULL,'172.18.0.1','','a6b1905f-d8c6-4a43-8f21-fe662f62bdae','','','','yaml','',''),(858,'pplax-file-dev.yaml','THEME-GROUP','spring:\n  servlet:\n    multipart:\n      max-file-size: 150MB\n      max-request-size: 500MB\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/pplax_file?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: root\n\n\n# 自定义的配置\npplax:\n  file:\n    nginx-root-path: F:\\opt\\pplax-theme\\nginx-upload\n    nginx-server-name: http://127.0.0.1\n    save-file-folder: pplax-upload\n  snow-flake-worker-id: 12\n  snow-flake-datacenter-id: 23\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: lax1458667357@gmail.com\n    api-info:\n      title: 博客文件API\n      description: 这是pplax主题的博客文件api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','ccb17e8a0ac35404e6402f65cb3f299c','2022-05-20 00:17:39','2023-09-01 17:44:29',NULL,'172.18.0.1','','a6b1905f-d8c6-4a43-8f21-fe662f62bdae','','','','yaml','',''),(859,'pplax-comment-dev.yaml','THEME-GROUP','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/pplax_comment?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: root\n\npplax:\n  snow-flake-datacenter-id: 5\n  snow-flake-worker-id: 23\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: lax1458667357@gmail.com\n    api-info:\n      title: 博客评论API\n      description: 这是pplax主题的博客评论api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','ca86e23772921c83b0ab39ca08be812a','2022-05-20 00:19:53','2023-09-01 17:44:38',NULL,'172.18.0.1','','a6b1905f-d8c6-4a43-8f21-fe662f62bdae','','','','yaml','',''),(860,'pplax-gateway-dev.yaml','THEME-GROUP','spring:\n  cloud:\n    sentinel:\n      filter:\n        enabled: false\n      scg:\n        fallback:\n          mode: response\n          response-status: 200\n          response-body: \'{\"code\":404,\"data\":{},\"message\":\"请求太快,请稍后在试┭┮﹏┭┮\",\"success\":false}\'\n      transport:\n        port: 8719\n        dashboard: http://127.0.0.1:18081\n      datasource:\n        ds1:\n          nacos:\n            server-addr: http://127.0.0.1:8848\n            dataId: sentinel-pplax-gateway\n            groupId: DEFAULT_GROUP\n            data-type: json\n            rule-type: flow\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        - id: openapi\n          uri: http://127.0.0.1:${server.port}\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*),/$\\{path}/v3/api-docs\n        - id: pplax-admin\n          uri: lb://pplax-admin\n          predicates:\n            - Path=/admin/**\n        - id: pplax-comment\n          uri: lb://pplax-comment\n          predicates:\n            - Path=/comment/**\n        - id: pplax-article\n          uri: lb://pplax-article\n          predicates:\n            - Path=/blog/**\n        - id: pplax-file\n          uri: lb://pplax-file\n          predicates:\n            - Path=/file/**\n        - id: pplax-message\n          uri: lb://pplax-message\n          predicates:\n            - Path=/message/**\n        - id: pplax-auth-server\n          uri: lb://pplax-auth-server\n          predicates:\n            - Path=/auth/**,/login/**,/oauth/**\n      default-filters:\n        - DedupeResponseHeader=Vary Access-Control-Allow-Origin Access-Control-Allow-Credentials, RETAIN_FIRST\n\n\npplax:\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: gateway聚合平台\n      description: 这是pplax主题的博客后台管理部分api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','ccb2efe9ac2ba6e13cc3656d9918a185','2022-05-20 00:26:18','2023-09-01 17:45:29',NULL,'172.18.0.1','','a6b1905f-d8c6-4a43-8f21-fe662f62bdae','','','','yaml','',''),(861,'pplax-article-dev.yaml','THEME-GROUP','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/pplax_article?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: root\n\n# 自定义的配置\npplax:\n  snow-flake-worker-id: 2\n  snow-flake-datacenter-id: 12\n  account:\n    mail-verify-account-expiration-time: 120000\n    mail-verify-account-prefix-path: http://127.0.0.1:8088/admin/verifyAccount/\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: lax1458667357@gmail.com\n    api-info:\n      title: 博客文章API\n      description: 这是pplax主题的博客文章部分的api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE\nlogging:\n  level:\n    xyz.xcye.web.common.service.feign.MessageLogFeignService: DEBUG\n','c333486ef6fe338955ae6a95292af254','2022-05-20 00:32:39','2023-09-01 17:45:48',NULL,'172.18.0.1','','a6b1905f-d8c6-4a43-8f21-fe662f62bdae','','','','yaml','',''),(863,'pplax-admin-dev.yaml','THEME-GROUP','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/pplax_admin?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: root\n# 自定义的配置\npplax:\n  defaultRoleList: \n    - user\n  snow-flake-worker-id: 2\n  snow-flake-datacenter-id: 12\n  account:\n    mail-verify-account-expiration-time: 120000\n    mail-verify-account-prefix-path: http://127.0.0.1:8080/admin/verifyAccount/bindEmail\n    enable-account-prefix-path: http://127.0.0.1:8080/admin/verifyAccount/enable\n  default:\n    user:\n      site-info: \"{\\\"readme\\\":\\\"# Hi auUsernameua\\\\n\\\",\\\"showWave\\\":true,\\\"showTopImgBubble\\\":true,\\\"mobilePageSidebar\\\":true,\\\"latestPageSize\\\":6,\\\"githubUrl\\\":\\\"https://github.com/xcyeye\\\",\\\"homePageLazyLoadingImg\\\":\\\"https://picture.xcye.xyz/image-20220328221012634.png\\\",\\\"randomPictureInterface\\\":\\\"https://cdn.seovx.com/d/?mom=302\\\",\\\"defaultCoverRequestInterface\\\":\\\"https://cdn.seovx.com/d/?mom=302\\\",\\\"footerInfo\\\":{\\\"enable\\\":true,\\\"isShowThemeCopyright\\\":true,\\\"isShowRunTime\\\":true,\\\"prefixRuntime\\\":\\\"pplax博客系统\\\",\\\"backgroundImage\\\":\\\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\\\",\\\"footInfo\\\":[\\\"Copyright©byxcyeAllRightsReserved.\\\",\\\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\\\"]},\\\"friendLinkSiteInformation\\\":{\\\"title\\\":\\\"pplax博客系统\\\",\\\"url\\\":\\\"http://xcye.xyz/user/1522074993315815424\\\",\\\"logo\\\":\\\"http://127.0.0.1/pplax-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\\\",\\\"cover\\\":\\\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\\\",\\\"describe\\\":\\\"pplax博客系统和pplax主题作者\\\",\\\"contact\\\":\\\"2291308094\\\"},\\\"socialsArr\\\":[{\\\"aHref\\\":\\\"tencent://message/?uin=2291308094\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:qq\\\",\\\"color\\\":\\\"#90f1ef\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"https://github.com/xcyeye/\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:github\\\",\\\"color\\\":\\\"#bbe6e4\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"https://space.bilibili.com/483962286\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa-brands:envira\\\",\\\"color\\\":\\\"efd1cd\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"https://music.163.com/#/user/home?id=1411050784\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:music\\\",\\\"color\\\":\\\"#6fffe9\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"mailto:2291308094@qq.com\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:envelope\\\",\\\"color\\\":\\\"#f2b5d4\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"/friendLink/1522074993315815424\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":false,\\\"icon\\\":\\\"fa:paper-plane\\\",\\\"color\\\":\\\"#b8f2e6\\\",\\\"showImgSrc\\\":\\\"\\\"}]}\"\n      welcome-article: \'# HI 你好世界\'\n      navbar-info: \"[{\\\"name\\\":\\\"首页\\\",\\\"url\\\":\\\"/user/auUserUidua\\\",\\\"icon\\\":\\\"fa:home\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"友情链接\\\",\\\"url\\\":\\\"/friendLink/auUserUidua\\\",\\\"icon\\\":\\\"fa:paper-plane\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"关于\\\",\\\"url\\\":\\\"/about/auUserUidua\\\",\\\"icon\\\":\\\"fa:pagelines\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"评论\\\",\\\"url\\\":\\\"/comment/auUserUidua\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"相册\\\",\\\"url\\\":\\\"/photo/auUserUidua\\\",\\\"icon\\\":\\\"fa:image\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"时间轴\\\",\\\"url\\\":\\\"/archive/auUserUidua\\\",\\\"icon\\\":\\\"fa:hourglass-3\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"分类\\\",\\\"url\\\":\\\"/category/auUserUidua\\\",\\\"icon\\\":\\\"mdi:alarm-bell\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"标签\\\",\\\"url\\\":\\\"/tag/auUserUidua/\\\",\\\"icon\\\":\\\"mdi:abugida-thai\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说\\\",\\\"url\\\":\\\"\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[{\\\"name\\\":\\\"说说1\\\",\\\"url\\\":\\\"/shareSpace/auUserUidua\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说2\\\",\\\"url\\\":\\\"/shareSpace-page/auUserUidua\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]}]}]\"\n      page-info: \'[{\"name\":\"首页\",\"url\":\"/user/auUserUidua\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/auUserUidua\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/auUserUidua\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/auUserUidua\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/auUserUidua\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/auUserUidua\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/auUserUidua\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/auUserUidua/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/auUserUidua\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/auUserUidua\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]\'\n    avatar: https://picture.xcye.xyz/avatar.jpg\n    role: ROLE_user\n    permission: user\n    nickname: pplax-new\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: lax1458667357@gmail.com\n    api-info:\n      title: 博客后台API\n      description: 这是pplax主题的博客后台管理部分api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE\n  amqp:\n    exchanges:\n      # 发送邮件的交换机\n      - exchange: pplax.send.email.common.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.email.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          # 发送html邮件\n          - queue: send.html.mail.queue\n            dead-letter-queue: send.html.mail.dead.letter.queue\n            routing-key: send.html.amil.routing\n            dead-letter-routing-key: send.html.mail.dead.letter.routing\n\n          - queue: send.simple.text.mail.queue\n            dead-letter-queue: send.simple.text.mail.dead.letter.queue\n            routing-key: send.simple.text.mail.routing\n            dead-letter-routing-key: send.simple.text.mail.dead.letter.routing\n            # 发送错误消息的交换机\n      - exchange: pplax.send.mistake.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.mistake.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          - queue: pplax.mistake.queue\n            dead-letter-queue: pplax.mistake.dead.letter.queue\n            routing-key: pplax.mistake.routing.key\n            dead-letter-routing-key: pplax.mistake.dead.letter.routing.key\n              # 发送操作用户数据的交换机\n      - exchange: pplax.send.operate.user.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.operate.user.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          - queue: pplax.operate.user.binding.email.queue\n            dead-letter-queue: pplax.operate.user.binding.email.dead.letter.queue\n            routing-key: pplax.operate.user.binding.email.routing\n            dead-letter-routing-key: pplax.operate.user.binding.email.dead.letter.routing\n\n          - queue: pplax.operate.user.lock.account.queue\n            dead-letter-queue: pplax.operate.user.lock.account.dead.letter.queue\n            routing-key: pplax.operate.user.lock.account.routing.key\n            dead-letter-routing-key: pplax.operate.user.lock.account.dead.letter.routing.key\n\n          - queue: pplax.update.role.permission.queue\n            dead-letter-queue: pplax.update.role.permission.dead.letter.queue\n            routing-key: pplax.update.role.permission.routing.key\n            dead-letter-routing-key: pplax.update.role.permission.dead.letter.routing\n\n          - queue: pplax.update.white.url.queue\n            dead-letter-queue: pplax.update.white.url.dead.letter.queue\n            routing-key: pplax.update.white.url.routing.key\n            dead-letter-routing-key: pplax.update.white.url.dead.letter.routing\n              # 发送评论的交换机\n      - exchange: pplax.send.comment.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.comment.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          - queue: pplax.PAGE.comment.queue\n            dead-letter-queue: pplax.PAGE.comment.dead.letter.queue\n            routing-key: pplax.PAGE.routingKey\n            dead-letter-routing-key: pplax.PAGE.comment.dead.letter.routingKey\n    default-max-length: 25\n    default-ttl: 6000\nlogging:\n  level:\n    xyz.xcye.web.common.service.feign.MessageLogFeignService: DEBUG','a062f2b8163d2f02c01f4d3dd2b6a564','2022-05-20 00:40:39','2023-09-01 10:53:25',NULL,'172.18.0.1','','a6b1905f-d8c6-4a43-8f21-fe662f62bdae','','','','yaml','',''),(870,'share-swagger-dev.yaml','THEME-GROUP','pplax:\n    gateway:\n        server-base-url: http://127.0.0.1:8080','c0ffffa961a3a90d797d4e880bf075c4','2022-05-21 04:16:37','2023-09-01 09:59:50',NULL,'172.18.0.1','','a6b1905f-d8c6-4a43-8f21-fe662f62bdae','','','','yaml','',''),(954,'pplax-admin-prod.yaml','THEME-GROUP','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/pplax_admin?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202@*\n# 自定义的配置\npplax:\n  defaultRoleList: \n    - user\n  snow-flake-worker-id: 2\n  snow-flake-datacenter-id: 12\n  account:\n    mail-verify-account-expiration-time: 120000\n    mail-verify-account-prefix-path: https://api.xcye.xyz/admin/verifyAccount/bindEmail\n    enable-account-prefix-path: https://api.xcye.xyz/admin/verifyAccount/enable/enableAccount\n  default:\n    user:\n      site-info: \"{\\\"readme\\\":\\\"# Hi auUsernameua\\\\n\\\",\\\"showWave\\\":true,\\\"showTopImgBubble\\\":true,\\\"mobilePageSidebar\\\":true,\\\"latestPageSize\\\":6,\\\"githubUrl\\\":\\\"https://github.com/xcyeye\\\",\\\"homePageLazyLoadingImg\\\":\\\"https://picture.xcye.xyz/image-20220328221012634.png\\\",\\\"randomPictureInterface\\\":\\\"https://cdn.seovx.com/d/?mom=302\\\",\\\"defaultCoverRequestInterface\\\":\\\"https://cdn.seovx.com/d/?mom=302\\\",\\\"footerInfo\\\":{\\\"enable\\\":true,\\\"isShowThemeCopyright\\\":true,\\\"isShowRunTime\\\":true,\\\"prefixRuntime\\\":\\\"pplax博客系统\\\",\\\"backgroundImage\\\":\\\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\\\",\\\"footInfo\\\":[\\\"Copyright©byxcyeAllRightsReserved.\\\",\\\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\\\"]},\\\"friendLinkSiteInformation\\\":{\\\"title\\\":\\\"pplax博客系统\\\",\\\"url\\\":\\\"http://xcye.xyz/user/1522074993315815424\\\",\\\"logo\\\":\\\"http://127.0.0.1/pplax-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\\\",\\\"cover\\\":\\\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\\\",\\\"describe\\\":\\\"pplax博客系统和pplax主题作者\\\",\\\"contact\\\":\\\"2291308094\\\"},\\\"socialsArr\\\":[{\\\"aHref\\\":\\\"tencent://message/?uin=2291308094\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:qq\\\",\\\"color\\\":\\\"#90f1ef\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"https://github.com/xcyeye/\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:github\\\",\\\"color\\\":\\\"#bbe6e4\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"https://space.bilibili.com/483962286\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa-brands:envira\\\",\\\"color\\\":\\\"efd1cd\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"https://music.163.com/#/user/home?id=1411050784\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:music\\\",\\\"color\\\":\\\"#6fffe9\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"mailto:2291308094@qq.com\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:envelope\\\",\\\"color\\\":\\\"#f2b5d4\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"/friendLink/1522074993315815424\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":false,\\\"icon\\\":\\\"fa:paper-plane\\\",\\\"color\\\":\\\"#b8f2e6\\\",\\\"showImgSrc\\\":\\\"\\\"}]}\"\n      welcome-article: \'# HI 你好世界\'\n      navbar-info: \"[{\\\"name\\\":\\\"首页\\\",\\\"url\\\":\\\"/user/auUserUidua\\\",\\\"icon\\\":\\\"fa:home\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"友情链接\\\",\\\"url\\\":\\\"/friendLink/auUserUidua\\\",\\\"icon\\\":\\\"fa:paper-plane\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"关于\\\",\\\"url\\\":\\\"/about/auUserUidua\\\",\\\"icon\\\":\\\"fa:pagelines\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"评论\\\",\\\"url\\\":\\\"/comment/auUserUidua\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"相册\\\",\\\"url\\\":\\\"/photo/auUserUidua\\\",\\\"icon\\\":\\\"fa:image\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"时间轴\\\",\\\"url\\\":\\\"/archive/auUserUidua\\\",\\\"icon\\\":\\\"fa:hourglass-3\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"分类\\\",\\\"url\\\":\\\"/category/auUserUidua\\\",\\\"icon\\\":\\\"mdi:alarm-bell\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"标签\\\",\\\"url\\\":\\\"/tag/auUserUidua/\\\",\\\"icon\\\":\\\"mdi:abugida-thai\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说\\\",\\\"url\\\":\\\"\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[{\\\"name\\\":\\\"说说1\\\",\\\"url\\\":\\\"/shareSpace/auUserUidua\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说2\\\",\\\"url\\\":\\\"/shareSpace-page/auUserUidua\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]}]}]\"\n      page-info: \'[{\"name\":\"首页\",\"url\":\"/user/auUserUidua\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/auUserUidua\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/auUserUidua\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/auUserUidua\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/auUserUidua\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/auUserUidua\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/auUserUidua\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/auUserUidua/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/auUserUidua\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/auUserUidua\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]\'\n    avatar: https://cdn.xcye.xyz/note-picture/avatar.jpg\n    role: ROLE_user\n    permission: user\n    nickname: pplax-new\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客后台API\n      description: 这是pplax主题的博客后台管理部分api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE\n  amqp:\n    exchanges:\n      # 发送邮件的交换机\n      - exchange: pplax.send.email.common.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.email.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          # 发送html邮件\n          - queue: send.html.mail.queue\n            dead-letter-queue: send.html.mail.dead.letter.queue\n            routing-key: send.html.amil.routing\n            dead-letter-routing-key: send.html.mail.dead.letter.routing\n\n          - queue: send.simple.text.mail.queue\n            dead-letter-queue: send.simple.text.mail.dead.letter.queue\n            routing-key: send.simple.text.mail.routing\n            dead-letter-routing-key: send.simple.text.mail.dead.letter.routing\n            # 发送错误消息的交换机\n      - exchange: pplax.send.mistake.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.mistake.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          - queue: pplax.mistake.queue\n            dead-letter-queue: pplax.mistake.dead.letter.queue\n            routing-key: pplax.mistake.routing.key\n            dead-letter-routing-key: pplax.mistake.dead.letter.routing.key\n              # 发送操作用户数据的交换机\n      - exchange: pplax.send.operate.user.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.operate.user.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          - queue: pplax.operate.user.binding.email.queue\n            dead-letter-queue: pplax.operate.user.binding.email.dead.letter.queue\n            routing-key: pplax.operate.user.binding.email.routing\n            dead-letter-routing-key: pplax.operate.user.binding.email.dead.letter.routing\n\n          - queue: pplax.operate.user.lock.account.queue\n            dead-letter-queue: pplax.operate.user.lock.account.dead.letter.queue\n            routing-key: pplax.operate.user.lock.account.routing.key\n            dead-letter-routing-key: pplax.operate.user.lock.account.dead.letter.routing.key\n\n          - queue: pplax.update.role.permission.queue\n            dead-letter-queue: pplax.update.role.permission.dead.letter.queue\n            routing-key: pplax.update.role.permission.routing.key\n            dead-letter-routing-key: pplax.update.role.permission.dead.letter.routing\n\n          - queue: pplax.update.white.url.queue\n            dead-letter-queue: pplax.update.white.url.dead.letter.queue\n            routing-key: pplax.update.white.url.routing.key\n            dead-letter-routing-key: pplax.update.white.url.dead.letter.routing\n              # 发送评论的交换机\n      - exchange: pplax.send.comment.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.comment.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          - queue: pplax.PAGE.comment.queue\n            dead-letter-queue: pplax.PAGE.comment.dead.letter.queue\n            routing-key: pplax.PAGE.routingKey\n            dead-letter-routing-key: pplax.PAGE.comment.dead.letter.routingKey\n    default-max-length: 25\n    default-ttl: 6000\nlogging:\n  level:\n    xyz.xcye.web.common.service.feign.MessageLogFeignService: DEBUG','f8b594d724eb102dd5a8840d6a91aff0','2023-02-07 16:37:42','2023-02-14 01:30:07','nacos','183.225.67.58','','84313f7a-0de5-4214-9f62-455859382e1a','','','','yaml','',''),(955,'pplax-article-prod.yaml','THEME-GROUP','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/pplax_article?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202@*\n\n# 自定义的配置\npplax:\n  snow-flake-worker-id: 2\n  snow-flake-datacenter-id: 12\n  account:\n    mail-verify-account-expiration-time: 120000\n    mail-verify-account-prefix-path: http://localhost:8088/admin/verifyAccount/\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客文章API\n      description: 这是pplax主题的博客文章部分的api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE\nlogging:\n  level:\n    xyz.xcye.web.common.service.feign.MessageLogFeignService: DEBUG\n','746dea5afbd42b38182067ca952a0722','2023-02-07 16:37:42','2023-02-07 16:37:42',NULL,'183.225.67.58','','84313f7a-0de5-4214-9f62-455859382e1a',NULL,NULL,NULL,'yaml',NULL,''),(956,'pplax-auth-server-prod.yaml','THEME-GROUP','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/pplax_auth_server?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202@*\n\nlogging:\n  level:\n    xyz.xcye.web.common.service.feign.MessageLogFeignService: DEBUG\npplax:\n  oauth:\n    access-token-validity-seconds: 259200\n    refresh-token-validity-seconds: 259200\n    redis-delete-retry: 4\n    max-login-failure: 4\n    re-login-minute: 5\n    tx-map-api: https://apis.map.qq.com/ws/location/v1/ip?key=PU6BZ-VVO6V-66QP2-UNRJQ-K2JCO-24BCX\n  snow-flake-datacenter-id: 1\n  snow-flake-worker-id: 2\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 认证中心API\n      description: 这是pplax主题的博客的认证中心api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','c63a0b3ed5cf96be6cb36761c5ad6bc8','2023-02-07 16:37:42','2023-02-07 16:37:42',NULL,'183.225.67.58','','84313f7a-0de5-4214-9f62-455859382e1a',NULL,NULL,NULL,'yaml',NULL,''),(957,'pplax-comment-prod.yaml','THEME-GROUP','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/pplax_comment?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202@*\n\npplax:\n  pageWebUrl: https://blog.xcye.xyz\n  adminWebUrl: https://admin.xcye.xyz\n  snow-flake-datacenter-id: 5\n  snow-flake-worker-id: 23\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客评论API\n      description: 这是pplax主题的博客评论api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','68fd16be143045677db1271499a2e84d','2023-02-07 16:37:42','2023-02-11 15:38:17','nacos','183.225.67.58','','84313f7a-0de5-4214-9f62-455859382e1a','','','','yaml','',''),(958,'pplax-file-prod.yaml','THEME-GROUP','spring:\n  servlet:\n    multipart:\n      max-file-size: 150MB\n      max-request-size: 500MB\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/pplax_file?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202@*\n\n\n# 自定义的配置\npplax:\n  file:\n    nginx-root-path: /cloud-disk/pplax-theme\n    nginx-server-name: https://cdn.xcye.xyz/blog-upload/\n    save-file-folder: blog-upload\n  snow-flake-worker-id: 12\n  snow-flake-datacenter-id: 23\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客文件API\n      description: 这是pplax主题的博客文件api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','aca877b130607fa549877dbc2ebcba9e','2023-02-07 16:37:42','2023-02-08 16:39:48','nacos','183.225.67.58','','84313f7a-0de5-4214-9f62-455859382e1a','','','','yaml','',''),(959,'pplax-gateway-prod.yaml','THEME-GROUP','spring:\n  cloud:\n    sentinel:\n      filter:\n        enabled: false\n      scg:\n        fallback:\n          mode: response\n          response-status: 200\n          response-body: \'{\"code\":404,\"data\":{},\"message\":\"请求太快,请稍后在试┭┮﹏┭┮\",\"success\":false}\'\n      transport:\n        port: 8719\n        dashboard: localhost:8080\n      datasource:\n        ds1:\n          nacos:\n            server-addr: localhost:8848\n            dataId: sentinel-pplax-gateway\n            groupId: DEFAULT_GROUP\n            data-type: json\n            rule-type: flow\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        - id: openapi\n          uri: http://localhost:${server.port}\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*),/$\\{path}/v3/api-docs\n        - id: pplax-admin\n          uri: lb://pplax-admin\n          predicates:\n            - Path=/admin/**\n        - id: pplax-comment\n          uri: lb://pplax-comment\n          predicates:\n            - Path=/comment/**\n        - id: pplax-article\n          uri: lb://pplax-article\n          predicates:\n            - Path=/blog/**\n        - id: pplax-file\n          uri: lb://pplax-file\n          predicates:\n            - Path=/file/**\n        - id: pplax-message\n          uri: lb://pplax-message\n          predicates:\n            - Path=/message/**\n        - id: pplax-auth-server\n          uri: lb://pplax-auth-server\n          predicates:\n            - Path=/auth/**,/login/**,/oauth/**\n      default-filters:\n        - DedupeResponseHeader=Vary Access-Control-Allow-Origin Access-Control-Allow-Credentials, RETAIN_FIRST\n\n\npplax:\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: gateway聚合平台\n      description: 这是pplax主题的博客后台管理部分api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','3ad454ab87f610b977e319e448e0959b','2023-02-07 16:37:42','2023-02-07 16:37:42',NULL,'183.225.67.58','','84313f7a-0de5-4214-9f62-455859382e1a',NULL,NULL,NULL,'yaml',NULL,''),(960,'pplax-message-prod.yaml','THEME-GROUP','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/pplax_email?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202@*\n  mail:\n    host: smtp.qq.com\n    username: 2291308094@qq.com\n    password: gukmguyxccsleafj\n    protocol: smtps\n    properties.mail.smtp.port: 465\n    properties.mail.smtp.starttls.enable: true\n    properties.mail.smtp.starttlls.required: true\n    properties.mail.smtp.ssl.enable: true\n    default-encoding: utf-8\n\n# 自定义的配置\npplax:\n  snow-flake-worker-id: 14\n  snow-flake-datacenter-id: 24\n  amqp:\n    amqp-max-retry-consume: 3\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客消息API\n      description: 这是pplax主题的博客消息api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE\nlogging:\n  level:\n    root: info\n','f2ad9a6634ed89edf739bfc06fc74770','2023-02-07 16:37:42','2023-02-10 17:32:52','nacos','183.225.67.58','','84313f7a-0de5-4214-9f62-455859382e1a','','','','yaml','',''),(961,'share-actuator-prod.yaml','THEME-GROUP','\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"','76f4e71d8ec547c2d940ab52c7a14aaf','2023-02-07 16:37:42','2023-02-07 16:37:42',NULL,'183.225.67.58','','84313f7a-0de5-4214-9f62-455859382e1a',NULL,NULL,NULL,'yaml',NULL,''),(962,'share-pplax-oauth-prod.yaml','THEME-GROUP','# token验证和生成用到的秘钥\npplax:\n  oauth:\n    secret-key: pplaxaadkfhasdfuywasdfkjahdsfuewkrnbsdjtywq8745ghsdfsgdfhsdfgerbjhsdtfquewrw9erygtwertfysatf','e41685372342053eec5d5d26e53f732f','2023-02-07 16:37:42','2023-02-07 16:37:42',NULL,'183.225.67.58','','84313f7a-0de5-4214-9f62-455859382e1a',NULL,NULL,NULL,'yaml',NULL,''),(963,'share-feign-prod.yaml','THEME-GROUP','feign:\n  sentinel:\n    enabled: true\n  client:\n    config:\n      default:\n        connectTimeout: 5000\n        readTimeout: 5000','03e10c99cce614ba75c6d3306ab8e5f2','2023-02-07 16:37:42','2023-02-07 16:37:42',NULL,'183.225.67.58','','84313f7a-0de5-4214-9f62-455859382e1a',NULL,NULL,NULL,'yaml',NULL,''),(964,'share-mybatis-prod.yaml','THEME-GROUP','mybatis:\n  configuration:\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl','ac0e247b309081c92f966a8de49234e0','2023-02-07 16:37:42','2023-02-07 16:37:42',NULL,'183.225.67.58','','84313f7a-0de5-4214-9f62-455859382e1a',NULL,NULL,NULL,'yaml',NULL,''),(965,'share-pagehelper-prod.yaml','THEME-GROUP','# 分页插件\npagehelper:\n  helperDialect: mysql\n  reasonable: true\n  supportMethodsArguments: true\n  params: count=countSql','51cdaf050077cc14350cc9cd2d8d207a','2023-02-07 16:37:42','2023-02-07 16:37:42',NULL,'183.225.67.58','','84313f7a-0de5-4214-9f62-455859382e1a',NULL,NULL,NULL,'yaml',NULL,''),(966,'share-rabbitmq-prod.yaml','THEME-GROUP','spring:\n  rabbitmq:\n    host: 127.0.0.1\n    port: 5672\n    username: admin\n    password: 123456\n    publisher-returns: true\n    publisher-confirm-type: correlated\n    listener:\n      direct:\n        acknowledge-mode: manual\n      simple:\n        acknowledge-mode: manual','3d7fb43ff6017d151b4a6a1fc084db99','2023-02-07 16:37:42','2023-02-07 16:37:42',NULL,'183.225.67.58','','84313f7a-0de5-4214-9f62-455859382e1a',NULL,NULL,NULL,'yaml',NULL,''),(967,'share-redis-prod.yaml','THEME-GROUP','spring:\n  redis:\n    database: 1\n    host: 127.0.0.1\n    password: \'\'\n    port: 6380\n    jedis:\n      pool:\n        max-active: 15\n        max-idle: 10\n        min-idle: 0\n        max-wait: -1ms','d5b52ca5639cf86c7ed899a619993689','2023-02-07 16:37:42','2023-02-08 12:40:20','nacos','183.225.67.58','','84313f7a-0de5-4214-9f62-455859382e1a','','','','yaml','',''),(968,'share-seata-prod.yaml','THEME-GROUP','seata:\n  enabled: true\n  application-id: applicationName\n  tx-service-group: pplax-blog-tx-group\n  enable-auto-data-source-proxy: true\n  config:\n    type: nacos\n    nacos:\n      serverAddr: 127.0.0.1:8848\n      group: SEATA_GROUP\n      username: \"nacos\"\n      password: \"nacos\"\n  registry:\n    type: nacos\n    nacos:\n      application: seata-server\n      server-addr: 127.0.0.1:8848\n      group: SEATA_GROUP\n#      namespace:\n      username: \"nacos\"\n      password: \"nacos\"\n  service:\n    vgroup-mapping:\n      pplax-blog-tx-group: default','7b9f3a7f5f4754c926b6d0cc125be8e1','2023-02-07 16:37:42','2023-02-07 16:37:42',NULL,'183.225.67.58','','84313f7a-0de5-4214-9f62-455859382e1a',NULL,NULL,NULL,'yaml',NULL,''),(969,'share-sentinel-prod.yaml','THEME-GROUP','spring:\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 127.0.0.1:8080','8b6a50b82e760fe21f4792ce559dd07c','2023-02-07 16:37:42','2023-02-07 16:37:42',NULL,'183.225.67.58','','84313f7a-0de5-4214-9f62-455859382e1a',NULL,NULL,NULL,'yaml',NULL,''),(970,'share-swagger-prod.yaml','THEME-GROUP','pplax:\n    gateway:\n        server-base-url: http://localhost:8080','928543c3d321fc76b7d373319f1fa116','2023-02-07 16:37:42','2023-02-07 16:37:42',NULL,'183.225.67.58','','84313f7a-0de5-4214-9f62-455859382e1a',NULL,NULL,NULL,'yaml',NULL,''),(990,'pplax-admin-remote.yaml','THEME-GROUP','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://81.68.201.154:3306/pplax_admin?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202@*\n# 自定义的配置\npplax:\n  defaultRoleList: \n    - user\n  snow-flake-worker-id: 2\n  snow-flake-datacenter-id: 12\n  account:\n    mail-verify-account-expiration-time: 120000\n    mail-verify-account-prefix-path: http://localhost:8080/admin/verifyAccount/bindEmail\n    enable-account-prefix-path: http://localhost:8080/admin/verifyAccount/enable\n  default:\n    user:\n      site-info: \"{\\\"readme\\\":\\\"# Hi auUsernameua\\\\n\\\",\\\"showWave\\\":true,\\\"showTopImgBubble\\\":true,\\\"mobilePageSidebar\\\":true,\\\"latestPageSize\\\":6,\\\"githubUrl\\\":\\\"https://github.com/xcyeye\\\",\\\"homePageLazyLoadingImg\\\":\\\"https://picture.xcye.xyz/image-20220328221012634.png\\\",\\\"randomPictureInterface\\\":\\\"https://cdn.seovx.com/d/?mom=302\\\",\\\"defaultCoverRequestInterface\\\":\\\"https://cdn.seovx.com/d/?mom=302\\\",\\\"footerInfo\\\":{\\\"enable\\\":true,\\\"isShowThemeCopyright\\\":true,\\\"isShowRunTime\\\":true,\\\"prefixRuntime\\\":\\\"pplax博客系统\\\",\\\"backgroundImage\\\":\\\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\\\",\\\"footInfo\\\":[\\\"Copyright©byxcyeAllRightsReserved.\\\",\\\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\\\"]},\\\"friendLinkSiteInformation\\\":{\\\"title\\\":\\\"pplax博客系统\\\",\\\"url\\\":\\\"http://xcye.xyz/user/1522074993315815424\\\",\\\"logo\\\":\\\"http://127.0.0.1/pplax-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\\\",\\\"cover\\\":\\\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\\\",\\\"describe\\\":\\\"pplax博客系统和pplax主题作者\\\",\\\"contact\\\":\\\"2291308094\\\"},\\\"socialsArr\\\":[{\\\"aHref\\\":\\\"tencent://message/?uin=2291308094\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:qq\\\",\\\"color\\\":\\\"#90f1ef\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"https://github.com/xcyeye/\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:github\\\",\\\"color\\\":\\\"#bbe6e4\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"https://space.bilibili.com/483962286\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa-brands:envira\\\",\\\"color\\\":\\\"efd1cd\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"https://music.163.com/#/user/home?id=1411050784\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:music\\\",\\\"color\\\":\\\"#6fffe9\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"mailto:2291308094@qq.com\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:envelope\\\",\\\"color\\\":\\\"#f2b5d4\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"/friendLink/1522074993315815424\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":false,\\\"icon\\\":\\\"fa:paper-plane\\\",\\\"color\\\":\\\"#b8f2e6\\\",\\\"showImgSrc\\\":\\\"\\\"}]}\"\n      welcome-article: \'# HI 你好世界\'\n      navbar-info: \"[{\\\"name\\\":\\\"首页\\\",\\\"url\\\":\\\"/user/auUserUidua\\\",\\\"icon\\\":\\\"fa:home\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"友情链接\\\",\\\"url\\\":\\\"/friendLink/auUserUidua\\\",\\\"icon\\\":\\\"fa:paper-plane\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"关于\\\",\\\"url\\\":\\\"/about/auUserUidua\\\",\\\"icon\\\":\\\"fa:pagelines\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"评论\\\",\\\"url\\\":\\\"/comment/auUserUidua\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"相册\\\",\\\"url\\\":\\\"/photo/auUserUidua\\\",\\\"icon\\\":\\\"fa:image\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"时间轴\\\",\\\"url\\\":\\\"/archive/auUserUidua\\\",\\\"icon\\\":\\\"fa:hourglass-3\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"分类\\\",\\\"url\\\":\\\"/category/auUserUidua\\\",\\\"icon\\\":\\\"mdi:alarm-bell\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"标签\\\",\\\"url\\\":\\\"/tag/auUserUidua/\\\",\\\"icon\\\":\\\"mdi:abugida-thai\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说\\\",\\\"url\\\":\\\"\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[{\\\"name\\\":\\\"说说1\\\",\\\"url\\\":\\\"/shareSpace/auUserUidua\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说2\\\",\\\"url\\\":\\\"/shareSpace-page/auUserUidua\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]}]}]\"\n      page-info: \'[{\"name\":\"首页\",\"url\":\"/user/auUserUidua\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/auUserUidua\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/auUserUidua\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/auUserUidua\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/auUserUidua\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/auUserUidua\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/auUserUidua\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/auUserUidua/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/auUserUidua\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/auUserUidua\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]\'\n    avatar: https://picture.xcye.xyz/avatar.jpg\n    role: ROLE_user\n    permission: user\n    nickname: pplax-new\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客后台API\n      description: 这是pplax主题的博客后台管理部分api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE\n  amqp:\n    exchanges:\n      # 发送邮件的交换机\n      - exchange: pplax.send.email.common.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.email.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          # 发送html邮件\n          - queue: send.html.mail.queue\n            dead-letter-queue: send.html.mail.dead.letter.queue\n            routing-key: send.html.amil.routing\n            dead-letter-routing-key: send.html.mail.dead.letter.routing\n\n          - queue: send.simple.text.mail.queue\n            dead-letter-queue: send.simple.text.mail.dead.letter.queue\n            routing-key: send.simple.text.mail.routing\n            dead-letter-routing-key: send.simple.text.mail.dead.letter.routing\n            # 发送错误消息的交换机\n      - exchange: pplax.send.mistake.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.mistake.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          - queue: pplax.mistake.queue\n            dead-letter-queue: pplax.mistake.dead.letter.queue\n            routing-key: pplax.mistake.routing.key\n            dead-letter-routing-key: pplax.mistake.dead.letter.routing.key\n              # 发送操作用户数据的交换机\n      - exchange: pplax.send.operate.user.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.operate.user.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          - queue: pplax.operate.user.binding.email.queue\n            dead-letter-queue: pplax.operate.user.binding.email.dead.letter.queue\n            routing-key: pplax.operate.user.binding.email.routing\n            dead-letter-routing-key: pplax.operate.user.binding.email.dead.letter.routing\n\n          - queue: pplax.operate.user.lock.account.queue\n            dead-letter-queue: pplax.operate.user.lock.account.dead.letter.queue\n            routing-key: pplax.operate.user.lock.account.routing.key\n            dead-letter-routing-key: pplax.operate.user.lock.account.dead.letter.routing.key\n\n          - queue: pplax.update.role.permission.queue\n            dead-letter-queue: pplax.update.role.permission.dead.letter.queue\n            routing-key: pplax.update.role.permission.routing.key\n            dead-letter-routing-key: pplax.update.role.permission.dead.letter.routing\n\n          - queue: pplax.update.white.url.queue\n            dead-letter-queue: pplax.update.white.url.dead.letter.queue\n            routing-key: pplax.update.white.url.routing.key\n            dead-letter-routing-key: pplax.update.white.url.dead.letter.routing\n              # 发送评论的交换机\n      - exchange: pplax.send.comment.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.comment.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          - queue: pplax.PAGE.comment.queue\n            dead-letter-queue: pplax.PAGE.comment.dead.letter.queue\n            routing-key: pplax.PAGE.routingKey\n            dead-letter-routing-key: pplax.PAGE.comment.dead.letter.routingKey\n    default-max-length: 25\n    default-ttl: 6000\nlogging:\n  level:\n    xyz.xcye.web.common.service.feign.MessageLogFeignService: DEBUG','bac847a97299a42aba04d7deec5a3a6b','2023-02-07 16:49:45','2023-02-07 16:49:45',NULL,'183.225.67.58','','227521b4-71f0-4088-a0e5-ad80b454e836',NULL,NULL,NULL,'yaml',NULL,''),(991,'pplax-article-remote.yaml','THEME-GROUP','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://81.68.201.154:3306/pplax_article?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202@*\n\n# 自定义的配置\npplax:\n  snow-flake-worker-id: 2\n  snow-flake-datacenter-id: 12\n  account:\n    mail-verify-account-expiration-time: 120000\n    mail-verify-account-prefix-path: http://localhost:8088/admin/verifyAccount/\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客文章API\n      description: 这是pplax主题的博客文章部分的api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE\nlogging:\n  level:\n    xyz.xcye.web.common.service.feign.MessageLogFeignService: DEBUG\n','a9998e078fb549cf93be856a19fcdbaa','2023-02-07 16:49:45','2023-02-07 16:49:45',NULL,'183.225.67.58','','227521b4-71f0-4088-a0e5-ad80b454e836',NULL,NULL,NULL,'yaml',NULL,''),(992,'pplax-auth-server-remote.yaml','THEME-GROUP','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://81.68.201.154:3306/pplax_auth_server?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202@*\n\nlogging:\n  level:\n    xyz.xcye.web.common.service.feign.MessageLogFeignService: DEBUG\npplax:\n  oauth:\n    access-token-validity-seconds: 259200\n    refresh-token-validity-seconds: 259200\n    redis-delete-retry: 4\n    max-login-failure: 4\n    re-login-minute: 5\n    tx-map-api: https://apis.map.qq.com/ws/location/v1/ip?key=PU6BZ-VVO6V-66QP2-UNRJQ-K2JCO-24BCX\n  snow-flake-datacenter-id: 1\n  snow-flake-worker-id: 2\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 认证中心API\n      description: 这是pplax主题的博客的认证中心api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','e7850719c0da9d0a228b1c86be083ceb','2023-02-07 16:49:45','2023-02-07 16:49:45',NULL,'183.225.67.58','','227521b4-71f0-4088-a0e5-ad80b454e836',NULL,NULL,NULL,'yaml',NULL,''),(993,'pplax-comment-remote.yaml','THEME-GROUP','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://81.68.201.154:3306/pplax_comment?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202@*\n\npplax:\n  snow-flake-datacenter-id: 5\n  snow-flake-worker-id: 23\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客评论API\n      description: 这是pplax主题的博客评论api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','4c0bd7b4e584a4b95e0e289fb1388370','2023-02-07 16:49:45','2023-02-07 16:49:45',NULL,'183.225.67.58','','227521b4-71f0-4088-a0e5-ad80b454e836',NULL,NULL,NULL,'yaml',NULL,''),(994,'pplax-file-remote.yaml','THEME-GROUP','spring:\n  servlet:\n    multipart:\n      max-file-size: 150MB\n      max-request-size: 500MB\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://81.68.201.154:3306/pplax_file?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202@*\n\n\n# 自定义的配置\npplax:\n  file:\n    nginx-root-path: F:\\opt\\pplax-theme\\nginx-upload\n    nginx-server-name: http://127.0.0.1\n    save-file-folder: pplax-upload\n  snow-flake-worker-id: 12\n  snow-flake-datacenter-id: 23\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客文件API\n      description: 这是pplax主题的博客文件api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','4a2962893c25940b5eefac8aa72caa3c','2023-02-07 16:49:45','2023-02-07 16:49:45',NULL,'183.225.67.58','','227521b4-71f0-4088-a0e5-ad80b454e836',NULL,NULL,NULL,'yaml',NULL,''),(995,'pplax-gateway-remote.yaml','THEME-GROUP','spring:\n  cloud:\n    sentinel:\n      filter:\n        enabled: false\n      scg:\n        fallback:\n          mode: response\n          response-status: 200\n          response-body: \'{\"code\":404,\"data\":{},\"message\":\"请求太快,请稍后在试┭┮﹏┭┮\",\"success\":false}\'\n      transport:\n        port: 8719\n        dashboard: 81.68.201.154:8080\n      datasource:\n        ds1:\n          nacos:\n            server-addr: 81.68.201.154:8848\n            dataId: sentinel-pplax-gateway\n            groupId: DEFAULT_GROUP\n            data-type: json\n            rule-type: flow\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        - id: openapi\n          uri: http://localhost:${server.port}\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*),/$\\{path}/v3/api-docs\n        - id: pplax-admin\n          uri: lb://pplax-admin\n          predicates:\n            - Path=/admin/**\n        - id: pplax-comment\n          uri: lb://pplax-comment\n          predicates:\n            - Path=/comment/**\n        - id: pplax-article\n          uri: lb://pplax-article\n          predicates:\n            - Path=/blog/**\n        - id: pplax-file\n          uri: lb://pplax-file\n          predicates:\n            - Path=/file/**\n        - id: pplax-message\n          uri: lb://pplax-message\n          predicates:\n            - Path=/message/**\n        - id: pplax-auth-server\n          uri: lb://pplax-auth-server\n          predicates:\n            - Path=/auth/**,/login/**,/oauth/**\n      default-filters:\n        - DedupeResponseHeader=Vary Access-Control-Allow-Origin Access-Control-Allow-Credentials, RETAIN_FIRST\n\n\npplax:\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: gateway聚合平台\n      description: 这是pplax主题的博客后台管理部分api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','85cff761d82fba139d9c3ac70a21ab76','2023-02-07 16:49:45','2023-02-07 16:49:45',NULL,'183.225.67.58','','227521b4-71f0-4088-a0e5-ad80b454e836',NULL,NULL,NULL,'yaml',NULL,''),(996,'pplax-message-remote.yaml','THEME-GROUP','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://81.68.201.154:3306/pplax_email?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202@*\n  mail:\n    host: smtp.qq.com\n    username: 2291308094@qq.com\n    password: uutqhjyodxtrdiea\n    protocol: smtps\n    properties.mail.smtp.port: 465\n    properties.mail.smtp.starttls.enable: true\n    properties.mail.smtp.starttlls.required: true\n    properties.mail.smtp.ssl.enable: true\n    default-encoding: utf-8\n\n# 自定义的配置\npplax:\n  snow-flake-worker-id: 14\n  snow-flake-datacenter-id: 24\n  amqp:\n    amqp-max-retry-consume: 3\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客消息API\n      description: 这是pplax主题的博客消息api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE\nlogging:\n  level:\n    root: info\n','7a80e91f22cdfb2b7002e52cf522dda2','2023-02-07 16:49:45','2023-02-07 16:49:45',NULL,'183.225.67.58','','227521b4-71f0-4088-a0e5-ad80b454e836',NULL,NULL,NULL,'yaml',NULL,''),(997,'share-actuator-remote.yaml','THEME-GROUP','\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"','76f4e71d8ec547c2d940ab52c7a14aaf','2023-02-07 16:49:45','2023-02-07 16:49:45',NULL,'183.225.67.58','','227521b4-71f0-4088-a0e5-ad80b454e836',NULL,NULL,NULL,'yaml',NULL,''),(998,'share-pplax-oauth-remote.yaml','THEME-GROUP','# token验证和生成用到的秘钥\npplax:\n  oauth:\n    secret-key: pplaxaadkfhasdfuywasdfkjahdsfuewkrnbsdjtywq8745ghsdfsgdfhsdfgerbjhsdtfquewrw9erygtwertfysatf','e41685372342053eec5d5d26e53f732f','2023-02-07 16:49:45','2023-02-07 16:49:45',NULL,'183.225.67.58','','227521b4-71f0-4088-a0e5-ad80b454e836',NULL,NULL,NULL,'yaml',NULL,''),(999,'share-feign-remote.yaml','THEME-GROUP','feign:\n  sentinel:\n    enabled: true\n  client:\n    config:\n      default:\n        connectTimeout: 5000\n        readTimeout: 5000','03e10c99cce614ba75c6d3306ab8e5f2','2023-02-07 16:49:45','2023-02-07 16:49:45',NULL,'183.225.67.58','','227521b4-71f0-4088-a0e5-ad80b454e836',NULL,NULL,NULL,'yaml',NULL,''),(1000,'share-mybatis-remote.yaml','THEME-GROUP','mybatis:\n  configuration:\n    map-underscore-to-camel-case: true\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl','ac0e247b309081c92f966a8de49234e0','2023-02-07 16:49:45','2023-02-07 16:49:45',NULL,'183.225.67.58','','227521b4-71f0-4088-a0e5-ad80b454e836',NULL,NULL,NULL,'yaml',NULL,''),(1001,'share-pagehelper-remote.yaml','THEME-GROUP','# 分页插件\npagehelper:\n  helperDialect: mysql\n  reasonable: true\n  supportMethodsArguments: true\n  params: count=countSql','51cdaf050077cc14350cc9cd2d8d207a','2023-02-07 16:49:45','2023-02-07 16:49:45',NULL,'183.225.67.58','','227521b4-71f0-4088-a0e5-ad80b454e836',NULL,NULL,NULL,'yaml',NULL,''),(1002,'share-rabbitmq-remote.yaml','THEME-GROUP','spring:\n  rabbitmq:\n    host: 81.68.201.154\n    port: 5672\n    username: admin\n    password: 123456\n    publisher-returns: true\n    publisher-confirm-type: correlated\n    listener:\n      direct:\n        acknowledge-mode: manual\n      simple:\n        acknowledge-mode: manual','bb0e0b39f010dcbfb24712beaf942b37','2023-02-07 16:49:45','2023-02-07 16:49:45',NULL,'183.225.67.58','','227521b4-71f0-4088-a0e5-ad80b454e836',NULL,NULL,NULL,'yaml',NULL,''),(1003,'share-redis-remote.yaml','THEME-GROUP','spring:\n  redis:\n    database: 1\n    host: 81.68.201.154\n    password: \'\'\n    port: 6380\n    jedis:\n      pool:\n        max-active: 15\n        max-idle: 10\n        min-idle: 0\n        max-wait: -1ms','07e94fab99e964f3c69bc951e1337b45','2023-02-07 16:49:45','2023-02-08 12:40:00','nacos','183.225.67.58','','227521b4-71f0-4088-a0e5-ad80b454e836','','','','yaml','',''),(1004,'share-seata-remote.yaml','THEME-GROUP','seata:\n  enabled: true\n  application-id: applicationName\n  tx-service-group: pplax-blog-tx-group\n  enable-auto-data-source-proxy: true\n  config:\n    type: nacos\n    nacos:\n      serverAddr: 81.68.201.154:8848\n      group: SEATA_GROUP\n      username: \"nacos\"\n      password: \"nacos\"\n  registry:\n    type: nacos\n    nacos:\n      application: seata-server\n      server-addr: 81.68.201.154:8848\n      group: SEATA_GROUP\n#      namespace:\n      username: \"nacos\"\n      password: \"nacos\"\n  service:\n    vgroup-mapping:\n      pplax-blog-tx-group: default','794e5293d1bc98601e0a147448edde6a','2023-02-07 16:49:45','2023-02-07 16:49:45',NULL,'183.225.67.58','','227521b4-71f0-4088-a0e5-ad80b454e836',NULL,NULL,NULL,'yaml',NULL,''),(1005,'share-sentinel-remote.yaml','THEME-GROUP','spring:\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 81.68.201.154:8080','4ef945a73176cd329865b2d75f9b6360','2023-02-07 16:49:45','2023-02-07 16:49:45',NULL,'183.225.67.58','','227521b4-71f0-4088-a0e5-ad80b454e836',NULL,NULL,NULL,'yaml',NULL,''),(1006,'share-swagger-remote.yaml','THEME-GROUP','pplax:\n    gateway:\n        server-base-url: http://localhost:8080','928543c3d321fc76b7d373319f1fa116','2023-02-07 16:49:45','2023-02-07 16:49:45',NULL,'183.225.67.58','','227521b4-71f0-4088-a0e5-ad80b454e836',NULL,NULL,NULL,'yaml',NULL,'');
/*!40000 ALTER TABLE `config_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_aggr`
--

DROP TABLE IF EXISTS `config_info_aggr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info_aggr` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='增加租户字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_aggr`
--

LOCK TABLES `config_info_aggr` WRITE;
/*!40000 ALTER TABLE `config_info_aggr` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_aggr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_beta`
--

DROP TABLE IF EXISTS `config_info_beta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info_beta` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='config_info_beta';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_beta`
--

LOCK TABLES `config_info_beta` WRITE;
/*!40000 ALTER TABLE `config_info_beta` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_beta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_tag`
--

DROP TABLE IF EXISTS `config_info_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='config_info_tag';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_tag`
--

LOCK TABLES `config_info_tag` WRITE;
/*!40000 ALTER TABLE `config_info_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_tags_relation`
--

DROP TABLE IF EXISTS `config_tags_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_tags_relation` (
  `id` bigint NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='config_tag_relation';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_tags_relation`
--

LOCK TABLES `config_tags_relation` WRITE;
/*!40000 ALTER TABLE `config_tags_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_tags_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_capacity`
--

DROP TABLE IF EXISTS `group_capacity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_group_id` (`group_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='集群、各Group容量信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_capacity`
--

LOCK TABLES `group_capacity` WRITE;
/*!40000 ALTER TABLE `group_capacity` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_capacity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `his_config_info`
--

DROP TABLE IF EXISTS `his_config_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `his_config_info` (
  `id` bigint unsigned NOT NULL,
  `nid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `src_ip` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`nid`) USING BTREE,
  KEY `idx_gmt_create` (`gmt_create`) USING BTREE,
  KEY `idx_gmt_modified` (`gmt_modified`) USING BTREE,
  KEY `idx_did` (`data_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1233 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='多租户改造';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `his_config_info`
--

LOCK TABLES `his_config_info` WRITE;
/*!40000 ALTER TABLE `his_config_info` DISABLE KEYS */;
INSERT INTO `his_config_info` (`id`, `nid`, `data_id`, `group_id`, `app_name`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `op_type`, `tenant_id`, `encrypted_data_key`) VALUES (847,1211,'share-redis-dev.yaml','THEME-GROUP','','spring:\n  redis:\n    database: 1\n    host: 192.168.158.120\n    password: \'\'\n    port: 6379\n    jedis:\n      pool:\n        max-active: 15\n        max-idle: 10\n        min-idle: 0\n        max-wait: -1ms','0fb72755d43b487cac8239f72eb05f60','2023-09-01 01:12:27','2023-09-01 09:12:28',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(848,1212,'share-sentinel-dev.yaml','THEME-GROUP','','spring:\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.158.120:8080','9e6ebe4557e443dadeaecb31147282fa','2023-09-01 01:12:45','2023-09-01 09:12:46',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(849,1213,'share-rabbitmq-dev.yaml','THEME-GROUP','','spring:\n  rabbitmq:\n    host: 192.168.158.120\n    port: 5672\n    username: admin\n    password: 123456\n    publisher-returns: true\n    publisher-confirm-type: correlated\n    listener:\n      direct:\n        acknowledge-mode: manual\n      simple:\n        acknowledge-mode: manual','d7bfe1aad8a095e0888046bde433d70a','2023-09-01 01:13:29','2023-09-01 09:13:29',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(855,1214,'pplax-auth-server-dev.yaml','THEME-GROUP','','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.158.120:3306/pplax_auth_server?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202\n\nlogging:\n  level:\n    xyz.xcye.web.common.service.feign.MessageLogFeignService: DEBUG\npplax:\n  oauth:\n    access-token-validity-seconds: 259200\n    refresh-token-validity-seconds: 259200\n    redis-delete-retry: 4\n    max-login-failure: 4\n    re-login-minute: 5\n    tx-map-api: https://apis.map.qq.com/ws/location/v1/ip?key=PU6BZ-VVO6V-66QP2-UNRJQ-K2JCO-24BCX\n  snow-flake-datacenter-id: 1\n  snow-flake-worker-id: 2\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 认证中心API\n      description: 这是pplax主题的博客的认证中心api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','1465b7c2d58df1c2164b7e9fe28820e8','2023-09-01 01:16:25','2023-09-01 09:16:25',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(856,1215,'share-seata-dev.yaml','THEME-GROUP','','seata:\n  enabled: true\n  application-id: applicationName\n  tx-service-group: pplax-blog-tx-group\n  enable-auto-data-source-proxy: true\n  config:\n    type: nacos\n    nacos:\n      serverAddr: 192.168.158.120:8848\n      group: SEATA_GROUP\n      username: \"nacos\"\n      password: \"nacos\"\n  registry:\n    type: nacos\n    nacos:\n      application: seata-server\n      server-addr: 192.168.158.120:8848\n      group: SEATA_GROUP\n#      namespace:\n      username: \"nacos\"\n      password: \"nacos\"\n  service:\n    vgroup-mapping:\n      pplax-blog-tx-group: default','6b05cd1776723edce0f43bbf69d55cb2','2023-09-01 01:56:31','2023-09-01 09:56:31',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(857,1216,'pplax-message-dev.yaml','THEME-GROUP','','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.158.120:3306/pplax_email?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202\n  mail:\n    host: smtp.qq.com\n    username: 2291308094@qq.com\n    password: uutqhjyodxtrdiea\n    protocol: smtps\n    properties.mail.smtp.port: 465\n    properties.mail.smtp.starttls.enable: true\n    properties.mail.smtp.starttlls.required: true\n    properties.mail.smtp.ssl.enable: true\n    default-encoding: utf-8\n\n# 自定义的配置\npplax:\n  snow-flake-worker-id: 14\n  snow-flake-datacenter-id: 24\n  amqp:\n    amqp-max-retry-consume: 3\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客消息API\n      description: 这是pplax主题的博客消息api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE\nlogging:\n  level:\n    root: info\n','806f826757c5a82d9c71ddf14b16a311','2023-09-01 01:57:04','2023-09-01 09:57:05',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(858,1217,'pplax-file-dev.yaml','THEME-GROUP','','spring:\n  servlet:\n    multipart:\n      max-file-size: 150MB\n      max-request-size: 500MB\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.158.120:3306/pplax_file?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202\n\n\n# 自定义的配置\npplax:\n  file:\n    nginx-root-path: F:\\opt\\pplax-theme\\nginx-upload\n    nginx-server-name: http://127.0.0.1\n    save-file-folder: pplax-upload\n  snow-flake-worker-id: 12\n  snow-flake-datacenter-id: 23\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客文件API\n      description: 这是pplax主题的博客文件api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','7d231dcfc17486bf01d9f34d88b158e1','2023-09-01 01:57:33','2023-09-01 09:57:33',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(859,1218,'pplax-comment-dev.yaml','THEME-GROUP','','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.158.120:3306/pplax_comment?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202\n\npplax:\n  snow-flake-datacenter-id: 5\n  snow-flake-worker-id: 23\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客评论API\n      description: 这是pplax主题的博客评论api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','e320d040a590bfa45c8bfeaf04d79bfd','2023-09-01 01:58:10','2023-09-01 09:58:10',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(860,1219,'pplax-gateway-dev.yaml','THEME-GROUP','','spring:\n  cloud:\n    sentinel:\n      filter:\n        enabled: false\n      scg:\n        fallback:\n          mode: response\n          response-status: 200\n          response-body: \'{\"code\":404,\"data\":{},\"message\":\"请求太快,请稍后在试┭┮﹏┭┮\",\"success\":false}\'\n      transport:\n        port: 8719\n        dashboard: localhost:8080\n      datasource:\n        ds1:\n          nacos:\n            server-addr: localhost:8848\n            dataId: sentinel-pplax-gateway\n            groupId: DEFAULT_GROUP\n            data-type: json\n            rule-type: flow\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        - id: openapi\n          uri: http://localhost:${server.port}\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*),/$\\{path}/v3/api-docs\n        - id: pplax-admin\n          uri: lb://pplax-admin\n          predicates:\n            - Path=/admin/**\n        - id: pplax-comment\n          uri: lb://pplax-comment\n          predicates:\n            - Path=/comment/**\n        - id: pplax-article\n          uri: lb://pplax-article\n          predicates:\n            - Path=/blog/**\n        - id: pplax-file\n          uri: lb://pplax-file\n          predicates:\n            - Path=/file/**\n        - id: pplax-message\n          uri: lb://pplax-message\n          predicates:\n            - Path=/message/**\n        - id: pplax-auth-server\n          uri: lb://pplax-auth-server\n          predicates:\n            - Path=/auth/**,/login/**,/oauth/**\n      default-filters:\n        - DedupeResponseHeader=Vary Access-Control-Allow-Origin Access-Control-Allow-Credentials, RETAIN_FIRST\n\n\npplax:\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: gateway聚合平台\n      description: 这是pplax主题的博客后台管理部分api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','bc9386b26819edc185ed5e6265c1a176','2023-09-01 01:58:33','2023-09-01 09:58:33',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(861,1220,'pplax-article-dev.yaml','THEME-GROUP','','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.158.120:3306/pplax_article?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202\n\n# 自定义的配置\npplax:\n  snow-flake-worker-id: 2\n  snow-flake-datacenter-id: 12\n  account:\n    mail-verify-account-expiration-time: 120000\n    mail-verify-account-prefix-path: http://localhost:8088/admin/verifyAccount/\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客文章API\n      description: 这是pplax主题的博客文章部分的api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE\nlogging:\n  level:\n    xyz.xcye.web.common.service.feign.MessageLogFeignService: DEBUG\n','30827dd69e48d469ad201fee90fca0d8','2023-09-01 01:59:00','2023-09-01 09:59:00',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(863,1221,'pplax-admin-dev.yaml','THEME-GROUP','','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.158.120:3306/pplax_admin?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202\n# 自定义的配置\npplax:\n  defaultRoleList: \n    - user\n  snow-flake-worker-id: 2\n  snow-flake-datacenter-id: 12\n  account:\n    mail-verify-account-expiration-time: 120000\n    mail-verify-account-prefix-path: http://localhost:8080/admin/verifyAccount/bindEmail\n    enable-account-prefix-path: http://localhost:8080/admin/verifyAccount/enable\n  default:\n    user:\n      site-info: \"{\\\"readme\\\":\\\"# Hi auUsernameua\\\\n\\\",\\\"showWave\\\":true,\\\"showTopImgBubble\\\":true,\\\"mobilePageSidebar\\\":true,\\\"latestPageSize\\\":6,\\\"githubUrl\\\":\\\"https://github.com/xcyeye\\\",\\\"homePageLazyLoadingImg\\\":\\\"https://picture.xcye.xyz/image-20220328221012634.png\\\",\\\"randomPictureInterface\\\":\\\"https://cdn.seovx.com/d/?mom=302\\\",\\\"defaultCoverRequestInterface\\\":\\\"https://cdn.seovx.com/d/?mom=302\\\",\\\"footerInfo\\\":{\\\"enable\\\":true,\\\"isShowThemeCopyright\\\":true,\\\"isShowRunTime\\\":true,\\\"prefixRuntime\\\":\\\"pplax博客系统\\\",\\\"backgroundImage\\\":\\\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\\\",\\\"footInfo\\\":[\\\"Copyright©byxcyeAllRightsReserved.\\\",\\\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\\\"]},\\\"friendLinkSiteInformation\\\":{\\\"title\\\":\\\"pplax博客系统\\\",\\\"url\\\":\\\"http://xcye.xyz/user/1522074993315815424\\\",\\\"logo\\\":\\\"http://127.0.0.1/pplax-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\\\",\\\"cover\\\":\\\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\\\",\\\"describe\\\":\\\"pplax博客系统和pplax主题作者\\\",\\\"contact\\\":\\\"2291308094\\\"},\\\"socialsArr\\\":[{\\\"aHref\\\":\\\"tencent://message/?uin=2291308094\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:qq\\\",\\\"color\\\":\\\"#90f1ef\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"https://github.com/xcyeye/\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:github\\\",\\\"color\\\":\\\"#bbe6e4\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"https://space.bilibili.com/483962286\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa-brands:envira\\\",\\\"color\\\":\\\"efd1cd\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"https://music.163.com/#/user/home?id=1411050784\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:music\\\",\\\"color\\\":\\\"#6fffe9\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"mailto:2291308094@qq.com\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:envelope\\\",\\\"color\\\":\\\"#f2b5d4\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"/friendLink/1522074993315815424\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":false,\\\"icon\\\":\\\"fa:paper-plane\\\",\\\"color\\\":\\\"#b8f2e6\\\",\\\"showImgSrc\\\":\\\"\\\"}]}\"\n      welcome-article: \'# HI 你好世界\'\n      navbar-info: \"[{\\\"name\\\":\\\"首页\\\",\\\"url\\\":\\\"/user/auUserUidua\\\",\\\"icon\\\":\\\"fa:home\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"友情链接\\\",\\\"url\\\":\\\"/friendLink/auUserUidua\\\",\\\"icon\\\":\\\"fa:paper-plane\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"关于\\\",\\\"url\\\":\\\"/about/auUserUidua\\\",\\\"icon\\\":\\\"fa:pagelines\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"评论\\\",\\\"url\\\":\\\"/comment/auUserUidua\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"相册\\\",\\\"url\\\":\\\"/photo/auUserUidua\\\",\\\"icon\\\":\\\"fa:image\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"时间轴\\\",\\\"url\\\":\\\"/archive/auUserUidua\\\",\\\"icon\\\":\\\"fa:hourglass-3\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"分类\\\",\\\"url\\\":\\\"/category/auUserUidua\\\",\\\"icon\\\":\\\"mdi:alarm-bell\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"标签\\\",\\\"url\\\":\\\"/tag/auUserUidua/\\\",\\\"icon\\\":\\\"mdi:abugida-thai\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说\\\",\\\"url\\\":\\\"\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[{\\\"name\\\":\\\"说说1\\\",\\\"url\\\":\\\"/shareSpace/auUserUidua\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说2\\\",\\\"url\\\":\\\"/shareSpace-page/auUserUidua\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]}]}]\"\n      page-info: \'[{\"name\":\"首页\",\"url\":\"/user/auUserUidua\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/auUserUidua\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/auUserUidua\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/auUserUidua\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/auUserUidua\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/auUserUidua\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/auUserUidua\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/auUserUidua/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/auUserUidua\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/auUserUidua\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]\'\n    avatar: https://picture.xcye.xyz/avatar.jpg\n    role: ROLE_user\n    permission: user\n    nickname: pplax-new\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客后台API\n      description: 这是pplax主题的博客后台管理部分api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE\n  amqp:\n    exchanges:\n      # 发送邮件的交换机\n      - exchange: pplax.send.email.common.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.email.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          # 发送html邮件\n          - queue: send.html.mail.queue\n            dead-letter-queue: send.html.mail.dead.letter.queue\n            routing-key: send.html.amil.routing\n            dead-letter-routing-key: send.html.mail.dead.letter.routing\n\n          - queue: send.simple.text.mail.queue\n            dead-letter-queue: send.simple.text.mail.dead.letter.queue\n            routing-key: send.simple.text.mail.routing\n            dead-letter-routing-key: send.simple.text.mail.dead.letter.routing\n            # 发送错误消息的交换机\n      - exchange: pplax.send.mistake.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.mistake.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          - queue: pplax.mistake.queue\n            dead-letter-queue: pplax.mistake.dead.letter.queue\n            routing-key: pplax.mistake.routing.key\n            dead-letter-routing-key: pplax.mistake.dead.letter.routing.key\n              # 发送操作用户数据的交换机\n      - exchange: pplax.send.operate.user.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.operate.user.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          - queue: pplax.operate.user.binding.email.queue\n            dead-letter-queue: pplax.operate.user.binding.email.dead.letter.queue\n            routing-key: pplax.operate.user.binding.email.routing\n            dead-letter-routing-key: pplax.operate.user.binding.email.dead.letter.routing\n\n          - queue: pplax.operate.user.lock.account.queue\n            dead-letter-queue: pplax.operate.user.lock.account.dead.letter.queue\n            routing-key: pplax.operate.user.lock.account.routing.key\n            dead-letter-routing-key: pplax.operate.user.lock.account.dead.letter.routing.key\n\n          - queue: pplax.update.role.permission.queue\n            dead-letter-queue: pplax.update.role.permission.dead.letter.queue\n            routing-key: pplax.update.role.permission.routing.key\n            dead-letter-routing-key: pplax.update.role.permission.dead.letter.routing\n\n          - queue: pplax.update.white.url.queue\n            dead-letter-queue: pplax.update.white.url.dead.letter.queue\n            routing-key: pplax.update.white.url.routing.key\n            dead-letter-routing-key: pplax.update.white.url.dead.letter.routing\n              # 发送评论的交换机\n      - exchange: pplax.send.comment.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.comment.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          - queue: pplax.PAGE.comment.queue\n            dead-letter-queue: pplax.PAGE.comment.dead.letter.queue\n            routing-key: pplax.PAGE.routingKey\n            dead-letter-routing-key: pplax.PAGE.comment.dead.letter.routingKey\n    default-max-length: 25\n    default-ttl: 6000\nlogging:\n  level:\n    xyz.xcye.web.common.service.feign.MessageLogFeignService: DEBUG','f0a9d330dc25eb9a92b1abbd6a8d7796','2023-09-01 01:59:38','2023-09-01 09:59:38',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(870,1222,'share-swagger-dev.yaml','THEME-GROUP','','pplax:\n    gateway:\n        server-base-url: http://localhost:8080','fdaf42ab86cedc16aa853f1fe543a86f','2023-09-01 01:59:49','2023-09-01 09:59:50',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(863,1223,'pplax-admin-dev.yaml','THEME-GROUP','','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/pplax_admin?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202\n# 自定义的配置\npplax:\n  defaultRoleList: \n    - user\n  snow-flake-worker-id: 2\n  snow-flake-datacenter-id: 12\n  account:\n    mail-verify-account-expiration-time: 120000\n    mail-verify-account-prefix-path: http://127.0.0.1:8080/admin/verifyAccount/bindEmail\n    enable-account-prefix-path: http://127.0.0.1:8080/admin/verifyAccount/enable\n  default:\n    user:\n      site-info: \"{\\\"readme\\\":\\\"# Hi auUsernameua\\\\n\\\",\\\"showWave\\\":true,\\\"showTopImgBubble\\\":true,\\\"mobilePageSidebar\\\":true,\\\"latestPageSize\\\":6,\\\"githubUrl\\\":\\\"https://github.com/xcyeye\\\",\\\"homePageLazyLoadingImg\\\":\\\"https://picture.xcye.xyz/image-20220328221012634.png\\\",\\\"randomPictureInterface\\\":\\\"https://cdn.seovx.com/d/?mom=302\\\",\\\"defaultCoverRequestInterface\\\":\\\"https://cdn.seovx.com/d/?mom=302\\\",\\\"footerInfo\\\":{\\\"enable\\\":true,\\\"isShowThemeCopyright\\\":true,\\\"isShowRunTime\\\":true,\\\"prefixRuntime\\\":\\\"pplax博客系统\\\",\\\"backgroundImage\\\":\\\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\\\",\\\"footInfo\\\":[\\\"Copyright©byxcyeAllRightsReserved.\\\",\\\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\\\"]},\\\"friendLinkSiteInformation\\\":{\\\"title\\\":\\\"pplax博客系统\\\",\\\"url\\\":\\\"http://xcye.xyz/user/1522074993315815424\\\",\\\"logo\\\":\\\"http://127.0.0.1/pplax-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\\\",\\\"cover\\\":\\\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\\\",\\\"describe\\\":\\\"pplax博客系统和pplax主题作者\\\",\\\"contact\\\":\\\"2291308094\\\"},\\\"socialsArr\\\":[{\\\"aHref\\\":\\\"tencent://message/?uin=2291308094\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:qq\\\",\\\"color\\\":\\\"#90f1ef\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"https://github.com/xcyeye/\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:github\\\",\\\"color\\\":\\\"#bbe6e4\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"https://space.bilibili.com/483962286\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa-brands:envira\\\",\\\"color\\\":\\\"efd1cd\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"https://music.163.com/#/user/home?id=1411050784\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:music\\\",\\\"color\\\":\\\"#6fffe9\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"mailto:2291308094@qq.com\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":true,\\\"icon\\\":\\\"fa:envelope\\\",\\\"color\\\":\\\"#f2b5d4\\\",\\\"showImgSrc\\\":\\\"\\\"},{\\\"aHref\\\":\\\"/friendLink/1522074993315815424\\\",\\\"isHome\\\":true,\\\"show\\\":true,\\\"sidebar\\\":false,\\\"icon\\\":\\\"fa:paper-plane\\\",\\\"color\\\":\\\"#b8f2e6\\\",\\\"showImgSrc\\\":\\\"\\\"}]}\"\n      welcome-article: \'# HI 你好世界\'\n      navbar-info: \"[{\\\"name\\\":\\\"首页\\\",\\\"url\\\":\\\"/user/auUserUidua\\\",\\\"icon\\\":\\\"fa:home\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"友情链接\\\",\\\"url\\\":\\\"/friendLink/auUserUidua\\\",\\\"icon\\\":\\\"fa:paper-plane\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"关于\\\",\\\"url\\\":\\\"/about/auUserUidua\\\",\\\"icon\\\":\\\"fa:pagelines\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"评论\\\",\\\"url\\\":\\\"/comment/auUserUidua\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"相册\\\",\\\"url\\\":\\\"/photo/auUserUidua\\\",\\\"icon\\\":\\\"fa:image\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"时间轴\\\",\\\"url\\\":\\\"/archive/auUserUidua\\\",\\\"icon\\\":\\\"fa:hourglass-3\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"分类\\\",\\\"url\\\":\\\"/category/auUserUidua\\\",\\\"icon\\\":\\\"mdi:alarm-bell\\\",\\\"outLink\\\":false,\\\"children\\\":[]},{\\\"name\\\":\\\"标签\\\",\\\"url\\\":\\\"/tag/auUserUidua/\\\",\\\"icon\\\":\\\"mdi:abugida-thai\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说\\\",\\\"url\\\":\\\"\\\",\\\"icon\\\":\\\"fa:comments\\\",\\\"outLink\\\":false,\\\"children\\\":[{\\\"name\\\":\\\"说说1\\\",\\\"url\\\":\\\"/shareSpace/auUserUidua\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]},{\\\"name\\\":\\\"说说2\\\",\\\"url\\\":\\\"/shareSpace-page/auUserUidua\\\",\\\"icon\\\":\\\"fa:comments-o\\\",\\\"children\\\":[]}]}]\"\n      page-info: \'[{\"name\":\"首页\",\"url\":\"/user/auUserUidua\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/auUserUidua\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/auUserUidua\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/auUserUidua\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/auUserUidua\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/auUserUidua\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/auUserUidua\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/auUserUidua/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/auUserUidua\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/auUserUidua\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]\'\n    avatar: https://picture.xcye.xyz/avatar.jpg\n    role: ROLE_user\n    permission: user\n    nickname: pplax-new\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: lax1458667357@gmail.com\n    api-info:\n      title: 博客后台API\n      description: 这是pplax主题的博客后台管理部分api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE\n  amqp:\n    exchanges:\n      # 发送邮件的交换机\n      - exchange: pplax.send.email.common.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.email.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          # 发送html邮件\n          - queue: send.html.mail.queue\n            dead-letter-queue: send.html.mail.dead.letter.queue\n            routing-key: send.html.amil.routing\n            dead-letter-routing-key: send.html.mail.dead.letter.routing\n\n          - queue: send.simple.text.mail.queue\n            dead-letter-queue: send.simple.text.mail.dead.letter.queue\n            routing-key: send.simple.text.mail.routing\n            dead-letter-routing-key: send.simple.text.mail.dead.letter.routing\n            # 发送错误消息的交换机\n      - exchange: pplax.send.mistake.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.mistake.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          - queue: pplax.mistake.queue\n            dead-letter-queue: pplax.mistake.dead.letter.queue\n            routing-key: pplax.mistake.routing.key\n            dead-letter-routing-key: pplax.mistake.dead.letter.routing.key\n              # 发送操作用户数据的交换机\n      - exchange: pplax.send.operate.user.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.operate.user.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          - queue: pplax.operate.user.binding.email.queue\n            dead-letter-queue: pplax.operate.user.binding.email.dead.letter.queue\n            routing-key: pplax.operate.user.binding.email.routing\n            dead-letter-routing-key: pplax.operate.user.binding.email.dead.letter.routing\n\n          - queue: pplax.operate.user.lock.account.queue\n            dead-letter-queue: pplax.operate.user.lock.account.dead.letter.queue\n            routing-key: pplax.operate.user.lock.account.routing.key\n            dead-letter-routing-key: pplax.operate.user.lock.account.dead.letter.routing.key\n\n          - queue: pplax.update.role.permission.queue\n            dead-letter-queue: pplax.update.role.permission.dead.letter.queue\n            routing-key: pplax.update.role.permission.routing.key\n            dead-letter-routing-key: pplax.update.role.permission.dead.letter.routing\n\n          - queue: pplax.update.white.url.queue\n            dead-letter-queue: pplax.update.white.url.dead.letter.queue\n            routing-key: pplax.update.white.url.routing.key\n            dead-letter-routing-key: pplax.update.white.url.dead.letter.routing\n              # 发送评论的交换机\n      - exchange: pplax.send.comment.exchange\n        exchange-type: topic\n        dead-letter-exchange: pplax.send.comment.dead.letter.exchange\n        dead-letter-exchange-type: direct\n        queues:\n          - queue: pplax.PAGE.comment.queue\n            dead-letter-queue: pplax.PAGE.comment.dead.letter.queue\n            routing-key: pplax.PAGE.routingKey\n            dead-letter-routing-key: pplax.PAGE.comment.dead.letter.routingKey\n    default-max-length: 25\n    default-ttl: 6000\nlogging:\n  level:\n    xyz.xcye.web.common.service.feign.MessageLogFeignService: DEBUG','810d68f1f7f3337bf5937e74a88f3ce4','2023-09-01 02:53:25','2023-09-01 10:53:25',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(849,1224,'share-rabbitmq-dev.yaml','THEME-GROUP','','spring:\n  rabbitmq:\n    host: 127.0.0.1\n    port: 15672\n    username: swsk33\n    password: 123456\n    publisher-returns: true\n    publisher-confirm-type: correlated\n    listener:\n      direct:\n        acknowledge-mode: manual\n      simple:\n        acknowledge-mode: manual','4298b32b2b4ab3f62d79fee13fb804e6','2023-09-01 08:48:56','2023-09-01 16:48:56',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(849,1225,'share-rabbitmq-dev.yaml','THEME-GROUP','','spring:\n  rabbitmq:\n    host: 127.0.0.1\n    port: 5672\n    username: swsk33\n    password: 123456\n    publisher-returns: true\n    publisher-confirm-type: correlated\n    listener:\n      direct:\n        acknowledge-mode: manual\n      simple:\n        acknowledge-mode: manual','cd3aa618a5fcc88b20bd8a9ef4244d48','2023-09-01 08:50:53','2023-09-01 16:50:53',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(860,1226,'pplax-gateway-dev.yaml','THEME-GROUP','','spring:\n  cloud:\n    sentinel:\n      filter:\n        enabled: false\n      scg:\n        fallback:\n          mode: response\n          response-status: 200\n          response-body: \'{\"code\":404,\"data\":{},\"message\":\"请求太快,请稍后在试┭┮﹏┭┮\",\"success\":false}\'\n      transport:\n        port: 8719\n        dashboard: 127.0.0.1:8080\n      datasource:\n        ds1:\n          nacos:\n            server-addr: 127.0.0.1:8848\n            dataId: sentinel-pplax-gateway\n            groupId: DEFAULT_GROUP\n            data-type: json\n            rule-type: flow\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        - id: openapi\n          uri: http://127.0.0.1:${server.port}\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*),/$\\{path}/v3/api-docs\n        - id: pplax-admin\n          uri: lb://pplax-admin\n          predicates:\n            - Path=/admin/**\n        - id: pplax-comment\n          uri: lb://pplax-comment\n          predicates:\n            - Path=/comment/**\n        - id: pplax-article\n          uri: lb://pplax-article\n          predicates:\n            - Path=/blog/**\n        - id: pplax-file\n          uri: lb://pplax-file\n          predicates:\n            - Path=/file/**\n        - id: pplax-message\n          uri: lb://pplax-message\n          predicates:\n            - Path=/message/**\n        - id: pplax-auth-server\n          uri: lb://pplax-auth-server\n          predicates:\n            - Path=/auth/**,/login/**,/oauth/**\n      default-filters:\n        - DedupeResponseHeader=Vary Access-Control-Allow-Origin Access-Control-Allow-Credentials, RETAIN_FIRST\n\n\npplax:\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: gateway聚合平台\n      description: 这是pplax主题的博客后台管理部分api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','ec4124f1b513de179f1e27490cd26394','2023-09-01 09:13:59','2023-09-01 17:13:59',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(848,1227,'share-sentinel-dev.yaml','THEME-GROUP','','spring:\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 127.0.0.1:8080','8b6a50b82e760fe21f4792ce559dd07c','2023-09-01 09:42:50','2023-09-01 17:42:51',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(857,1228,'pplax-message-dev.yaml','THEME-GROUP','','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/pplax_email?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202\n  mail:\n    host: smtp.qq.com\n    username: lax1458667357@gmail.com\n    password: uutqhjyodxtrdiea\n    protocol: smtps\n    properties.mail.smtp.port: 465\n    properties.mail.smtp.starttls.enable: true\n    properties.mail.smtp.starttlls.required: true\n    properties.mail.smtp.ssl.enable: true\n    default-encoding: utf-8\n\n# 自定义的配置\npplax:\n  snow-flake-worker-id: 14\n  snow-flake-datacenter-id: 24\n  amqp:\n    amqp-max-retry-consume: 3\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: 博客消息API\n      description: 这是pplax主题的博客消息api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE\nlogging:\n  level:\n    root: info\n','a1248b1e6ad84d81dd9d841319e70f74','2023-09-01 09:44:08','2023-09-01 17:44:08',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(858,1229,'pplax-file-dev.yaml','THEME-GROUP','','spring:\n  servlet:\n    multipart:\n      max-file-size: 150MB\n      max-request-size: 500MB\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/pplax_file?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202\n\n\n# 自定义的配置\npplax:\n  file:\n    nginx-root-path: F:\\opt\\pplax-theme\\nginx-upload\n    nginx-server-name: http://127.0.0.1\n    save-file-folder: pplax-upload\n  snow-flake-worker-id: 12\n  snow-flake-datacenter-id: 23\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: lax1458667357@gmail.com\n    api-info:\n      title: 博客文件API\n      description: 这是pplax主题的博客文件api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','6869081085834f221f5c7f4ae9eb77aa','2023-09-01 09:44:28','2023-09-01 17:44:29',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(859,1230,'pplax-comment-dev.yaml','THEME-GROUP','','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/pplax_comment?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202\n\npplax:\n  snow-flake-datacenter-id: 5\n  snow-flake-worker-id: 23\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: lax1458667357@gmail.com\n    api-info:\n      title: 博客评论API\n      description: 这是pplax主题的博客评论api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','2ec925e88531d41a22062e3a5991c890','2023-09-01 09:44:37','2023-09-01 17:44:38',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(860,1231,'pplax-gateway-dev.yaml','THEME-GROUP','','spring:\n  cloud:\n    sentinel:\n      filter:\n        enabled: false\n      scg:\n        fallback:\n          mode: response\n          response-status: 200\n          response-body: \'{\"code\":404,\"data\":{},\"message\":\"请求太快,请稍后在试┭┮﹏┭┮\",\"success\":false}\'\n      transport:\n        port: 8719\n        dashboard: http://127.0.0.1:8080\n      datasource:\n        ds1:\n          nacos:\n            server-addr: http://127.0.0.1:8848\n            dataId: sentinel-pplax-gateway\n            groupId: DEFAULT_GROUP\n            data-type: json\n            rule-type: flow\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n      routes:\n        - id: openapi\n          uri: http://127.0.0.1:${server.port}\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*),/$\\{path}/v3/api-docs\n        - id: pplax-admin\n          uri: lb://pplax-admin\n          predicates:\n            - Path=/admin/**\n        - id: pplax-comment\n          uri: lb://pplax-comment\n          predicates:\n            - Path=/comment/**\n        - id: pplax-article\n          uri: lb://pplax-article\n          predicates:\n            - Path=/blog/**\n        - id: pplax-file\n          uri: lb://pplax-file\n          predicates:\n            - Path=/file/**\n        - id: pplax-message\n          uri: lb://pplax-message\n          predicates:\n            - Path=/message/**\n        - id: pplax-auth-server\n          uri: lb://pplax-auth-server\n          predicates:\n            - Path=/auth/**,/login/**,/oauth/**\n      default-filters:\n        - DedupeResponseHeader=Vary Access-Control-Allow-Origin Access-Control-Allow-Credentials, RETAIN_FIRST\n\n\npplax:\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: 2291308094@qq.com\n    api-info:\n      title: gateway聚合平台\n      description: 这是pplax主题的博客后台管理部分api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE','6537a7120a1f9a11a003fb1d76adc642','2023-09-01 09:45:28','2023-09-01 17:45:29',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae',''),(861,1232,'pplax-article-dev.yaml','THEME-GROUP','','spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/pplax_article?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC\n    username: root\n    password: Cqy19981202\n\n# 自定义的配置\npplax:\n  snow-flake-worker-id: 2\n  snow-flake-datacenter-id: 12\n  account:\n    mail-verify-account-expiration-time: 120000\n    mail-verify-account-prefix-path: http://127.0.0.1:8088/admin/verifyAccount/\n  swagger:\n    author:\n      name: pplax\n      url: https://pplax.xcye.xyz\n      email: lax1458667357@gmail.com\n    api-info:\n      title: 博客文章API\n      description: 这是pplax主题的博客文章部分的api\n      version: 1.0.0\n      license: MIT\n      license-url: https://github.com/vuepress-pplax/vuepress-theme-pplax/blob/master/LICENSE\nlogging:\n  level:\n    xyz.xcye.web.common.service.feign.MessageLogFeignService: DEBUG\n','1b10c6e933adfccbf22156cfacd3546d','2023-09-01 09:45:47','2023-09-01 17:45:48',NULL,'172.18.0.1','U','a6b1905f-d8c6-4a43-8f21-fe662f62bdae','');
/*!40000 ALTER TABLE `his_config_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `role` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `resource` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  UNIQUE KEY `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  UNIQUE KEY `idx_user_role` (`username`,`role`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`username`, `role`) VALUES ('nacos','ROLE_ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenant_capacity`
--

DROP TABLE IF EXISTS `tenant_capacity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tenant_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='租户容量信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenant_capacity`
--

LOCK TABLES `tenant_capacity` WRITE;
/*!40000 ALTER TABLE `tenant_capacity` DISABLE KEYS */;
/*!40000 ALTER TABLE `tenant_capacity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenant_info`
--

DROP TABLE IF EXISTS `tenant_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tenant_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='tenant_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenant_info`
--

LOCK TABLES `tenant_info` WRITE;
/*!40000 ALTER TABLE `tenant_info` DISABLE KEYS */;
INSERT INTO `tenant_info` (`id`, `kp`, `tenant_id`, `tenant_name`, `tenant_desc`, `create_source`, `gmt_create`, `gmt_modified`) VALUES (7,'1','480d1031-510d-466c-8be8-77853d9dc7c2','dev','这是pplax主题开发的dev环境','nacos',1652971994101,1652971994101),(8,'1','84313f7a-0de5-4214-9f62-455859382e1a','prod','这是pplax主题的生产环境','nacos',1652972010999,1652972010999),(9,'1','a6b1905f-d8c6-4a43-8f21-fe662f62bdae','pplax','这是主题的配置','nacos',1653004098513,1653004098513),(10,'1','227521b4-71f0-4088-a0e5-ad80b454e836','remote','remote','nacos',1675788025403,1675788025403);
/*!40000 ALTER TABLE `tenant_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`username`, `password`, `enabled`) VALUES ('embouohqwshowjgr','$2a$10$vJwBdlhP1PrIeDjqwFdQveihFzTOhbCxvj9Y1g7urFaWoLpTvRsuu',1),('nacos','$2a$10$zu96b2.cV0MeusTIJ4/xkOCCQMGRAx8NAztxmhANJghxtLX/iYGOy',1),('ymbbelqycdsenwfu','$2a$10$.kZHIecq4I07ZGOr/cEEBej/dyN06XPyo63ahRUW6CeGpbOAwPgXK',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `pplax_admin`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `pplax_admin` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `pplax_admin`;

--
-- Table structure for table `au_admin_sidebar`
--

DROP TABLE IF EXISTS `au_admin_sidebar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_admin_sidebar` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `level` int NOT NULL DEFAULT '0' COMMENT '前台导航的展示等级 比如0就是一级导航',
  `pre_sidebar_uid` bigint NOT NULL DEFAULT '0' COMMENT '当前导航的父导航uid，也就是该导航显示在哪个导航下面',
  `title` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导航的标题',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导航的对应地址',
  `is_external` tinyint NOT NULL DEFAULT '0' COMMENT '1：链接到外部地址 0：链接就是此站点的，不在新标签也打开',
  `icon_class_name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此导航的类名，用户icon',
  `sort` int NOT NULL DEFAULT '0' COMMENT '此导航的顺序编号',
  `user_uid` bigint DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE KEY `nav_uid` (`pre_sidebar_uid`) USING BTREE,
  KEY `user` (`user_uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_admin_sidebar`
--

LOCK TABLES `au_admin_sidebar` WRITE;
/*!40000 ALTER TABLE `au_admin_sidebar` DISABLE KEYS */;
/*!40000 ALTER TABLE `au_admin_sidebar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `au_navigation`
--

DROP TABLE IF EXISTS `au_navigation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_navigation` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `level` int NOT NULL DEFAULT '0' COMMENT '前台导航的展示等级 比如0就是一级导航',
  `parent_nav_uid` bigint DEFAULT NULL COMMENT '当前导航的父导航uid，也就是该导航显示在哪个导航下面',
  `son_nav_uids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '该导航的子导航uid集合',
  `title` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导航的标题',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导航的对应地址',
  `is_external` tinyint NOT NULL DEFAULT '0' COMMENT '1：链接到外部地址 0：链接就是此站点的，不在新标签也打开',
  `icon_class_name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此导航的类名，用户icon',
  `sort` int NOT NULL DEFAULT '0' COMMENT '此导航的顺序编号',
  `user_uid` bigint NOT NULL COMMENT '该导航属于哪个用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '1: 删除 0：不删除',
  `is_show` tinyint NOT NULL DEFAULT '1' COMMENT '1: 展示，0： 不显示',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_navigation`
--

LOCK TABLES `au_navigation` WRITE;
/*!40000 ALTER TABLE `au_navigation` DISABLE KEYS */;
/*!40000 ALTER TABLE `au_navigation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `au_permission`
--

DROP TABLE IF EXISTS `au_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_permission` (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid，自增',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限的名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限的地址，可以是组件的名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `create_time_index` (`create_time`) USING BTREE,
  KEY `path_index` (`path`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_permission`
--

LOCK TABLES `au_permission` WRITE;
/*!40000 ALTER TABLE `au_permission` DISABLE KEYS */;
INSERT INTO `au_permission` (`uid`, `name`, `path`, `create_time`, `update_time`) VALUES (12,'修改导航信息','POST:/admin/navigation/updateNavigation','2023-01-15 22:56:11',NULL),(13,'根据uid查询','POST:/admin/navigation/queryNavigationByUid','2023-01-15 22:56:11',NULL),(14,'根据条件查询','POST:/admin/navigation/queryListNavigationByCondition','2023-01-15 22:56:11',NULL),(15,'查询该用户所有的前台导航信息','POST:/admin/navigation/queryAllNavigationByUserUid','2023-01-15 22:56:11',NULL),(16,'物理删除','POST:/admin/navigation/physicsDeleteNavigation','2023-01-15 22:56:11',NULL),(17,'根据uid逻辑删除导航','POST:/admin/navigation/loginDeleteNavigation','2023-01-15 22:56:11',NULL),(18,'插入新的导航信息','POST:/admin/navigation/insertNavigation','2023-01-15 22:56:11',NULL),(19,'修改某个用户的角色','POST:/admin/permissionRelation/updateUserRole','2023-01-15 22:56:11',NULL),(20,'更新某个角色的权限','POST:/admin/permissionRelation/updateRolePermission','2023-01-15 22:56:11',NULL),(21,'根据permissionPath，查询哪些角色和角色可以可以访问','POST:/admin/permissionRelation/queryRoleByPermissionPath','2023-01-15 22:56:11',NULL),(22,'加载角色权限关系根据角色名，不返回用户信息','POST:/admin/permissionRelation/loadRolePermissionRelByRoleUid','2023-01-15 22:56:11',NULL),(23,'根据用户名，加载该用户所拥有的角色权限关系，此接口和loadPermissionByUserUid返回的数据一样','POST:/admin/permissionRelation/loadPermissionByUsername','2023-01-15 22:56:11',NULL),(24,'根据用户uid，加载该用户所拥有的角色权限关系','POST:/admin/permissionRelation/loadPermissionByUserUid','2023-01-15 22:56:11',NULL),(25,'根据角色名称，加载对应的角色-权限信息','POST:/admin/permissionRelation/loadPermissionByRoleName','2023-01-15 22:56:11',NULL),(26,'加载所有的角色权限关系，只返回该角色存在权限部分，如果某个角色没有赋予权限，则不返回','POST:/admin/permissionRelation/loadAllRolePermission','2023-01-15 22:56:11',NULL),(27,'根据用户名，获取该用户所拥有的所有角色','POST:/admin/permissionRelation/loadAllRoleByUsername','2023-01-15 22:56:11',NULL),(28,'批量为多个用户增加角色','POST:/admin/permissionRelation/batchInsertUserRole','2023-01-15 22:56:11',NULL),(29,'批量为多个角色增加权限','POST:/admin/permissionRelation/batchInsertRolePermission','2023-01-15 22:56:11',NULL),(30,'为某个用户删除多个角色','POST:/admin/permissionRelation/batchDeleteUserRole','2023-01-15 22:56:11',NULL),(31,'删除某个角色的多个权限','POST:/admin/permissionRelation/batchDeleteRolePermission','2023-01-15 22:56:11',NULL),(32,'修改路由权限信息','POST:/admin/routerPermission/updateRouterPermission','2023-01-15 22:56:11',NULL),(33,'根据权限uid查询此权限拥有的路由','POST:/admin/routerPermission/queryAllAdminRouterInfoByPermissionUid','2023-01-15 22:56:11',NULL),(34,'删除路由权限信息','POST:/admin/routerPermission/physicalDeleteRouterPermission','2023-01-15 22:56:11',NULL),(35,'新建一条路由权限关系','POST:/admin/routerPermission/insertRouterPermission','2023-01-15 22:56:11',NULL),(36,'修改社交信息','POST:/admin/social/updateSocial','2023-01-15 22:56:11',NULL),(37,'根据uid查询社交信息','POST:/admin/social/querySocialByUid','2023-01-15 22:56:11',NULL),(38,'根据条件查询社交信息','POST:/admin/social/queryListSocialByCondition','2023-01-15 22:56:11',NULL),(39,'物理删除此社交信息','POST:/admin/social/physicalDeleteSocial','2023-01-15 22:56:11',NULL),(40,'逻辑删除此社交信息','POST:/admin/social/loginDeleteSocial','2023-01-15 22:56:11',NULL),(41,'插入新的社交信息','POST:/admin/social/insertSocial','2023-01-15 22:56:11',NULL),(42,'根据uid修改站点配置','POST:/admin/siteSetting/updateSiteSetting','2023-01-15 22:56:11',NULL),(43,'根据uid查询站点配置','POST:/admin/siteSetting/querySiteSettingByUserUid','2023-01-15 22:56:11',NULL),(45,'根据uid查询站点配置','POST:/admin/siteSetting/querySiteSettingByUid','2023-01-15 22:57:19',NULL),(46,'根据条件查询站点配置','POST:/admin/siteSetting/queryListSiteSettingByCondition','2023-01-15 22:57:19',NULL),(47,'物理删除站点配置','POST:/admin/siteSetting/physicalDeleteSiteSetting','2023-01-15 22:57:19',NULL),(48,'插入站点配置','POST:/admin/siteSetting/insertSiteSetting','2023-01-15 22:57:19',NULL),(49,'修改路径权限信息','POST:/admin/permission/updatePermission','2023-01-15 22:57:19',NULL),(50,'根据uid查询权限','POST:/admin/permission/queryPermissionByUid','2023-01-15 22:57:19',NULL),(51,'查询满足要求的所有权限信息','POST:/admin/permission/queryListPermissionByCondition','2023-01-15 22:57:19',NULL),(52,'删除权限','POST:/admin/permission/physicalDeletePermission','2023-01-15 22:57:19',NULL),(53,'插入路径权限','POST:/admin/permission/insertPermission','2023-01-15 22:57:19',NULL),(54,'删除权限','POST:/admin/permission/batchPhysicalDeletePermission','2023-01-15 22:57:19',NULL),(55,'批量插入路径权限','POST:/admin/permission/batchInsertPermission','2023-01-15 22:57:19',NULL),(56,'根据uid修改站点信息','POST:/admin/site/updateSite','2023-01-15 22:57:19',NULL),(57,'根据uid查询站点信息','POST:/admin/site/querySiteByUid','2023-01-15 22:57:19',NULL),(58,'根据条件查询站点信息','POST:/admin/site/queryListSiteByCondition','2023-01-15 22:57:19',NULL),(59,'物理删除站点信息','POST:/admin/site/physicalDeleteSite','2023-01-15 22:57:19',NULL),(60,'逻辑删除此uid对应的站点信息','POST:/admin/site/logicDeleteSite','2023-01-15 22:57:19',NULL),(61,'插入站点信息','POST:/admin/site/insertSite','2023-01-15 22:57:19',NULL),(62,'修改用户信息','POST:/admin/user/updateUser','2023-01-15 22:57:19',NULL),(63,'更新密码','POST:/admin/user/updatePassword','2023-01-15 22:57:19',NULL),(64,'通过username查询用户信息','POST:/admin/user/queryUserByUsername','2023-01-15 22:57:19',NULL),(65,'通过username查询用户信息','POST:/admin/user/queryUserByUsernameContainPassword','2023-01-15 22:57:19',NULL),(66,'通过uid查询用户信息','POST:/admin/user/queryUserByUid','2023-01-15 22:57:19',NULL),(67,'通过username查询用户信息','POST:/admin/user/queryOneData','2023-01-15 22:57:19',NULL),(68,'查询所有满足要求的用户信息','POST:/admin/user/queryListUserByCondition','2023-01-15 22:57:19',NULL),(69,'真正的从数据库中删除用户信息','POST:/admin/user/physicalDeleteUser','2023-01-15 22:57:19',NULL),(70,'逻辑删除用户信息','POST:/admin/user/logicDeleteUser','2023-01-15 22:57:19',NULL),(71,'添加新用户','POST:/admin/user/insertUser','2023-01-15 22:57:19',NULL),(72,'忘记密码','POST:/admin/user/forgotPassword','2023-01-15 22:57:19',NULL),(73,'绑定邮箱','POST:/admin/user/bindingEmail','2023-01-15 22:57:19',NULL),(74,'修改路由信息','POST:/admin/adminRouter/updateAdminRouter','2023-01-15 22:57:19',NULL),(75,'查询满足要求的所有路由信息','POST:/admin/adminRouter/queryListAdminRouterByCondition','2023-01-15 22:57:19',NULL),(76,'根据uid查询路由','POST:/admin/adminRouter/queryAdminRouterByUid','2023-01-15 22:57:19',NULL),(77,'删除路由','POST:/admin/adminRouter/physicalDeleteAdminRouter','2023-01-15 22:57:19',NULL),(78,'插入路由','POST:/admin/adminRouter/insertAdminRouter','2023-01-15 22:57:19',NULL),(79,'修改白名单数据','POST:/admin/whiteUrl/updateWhiteUrl','2023-01-15 22:57:19',NULL),(80,'根据查询条件获取所有的白名单数据','POST:/admin/whiteUrl/queryListWhiteUrlByCondition','2023-01-15 22:57:19',NULL),(81,'根据uid删除白名单','POST:/admin/whiteUrl/physicalDeleteWhiteUrl','2023-01-15 22:57:19',NULL),(82,'插入白名单记录','POST:/admin/whiteUrl/insertWhiteUrl','2023-01-15 22:57:19',NULL),(83,'批量删除白名单','POST:/admin/whiteUrl/batchDeleteWhiteUrl','2023-01-15 22:57:19',NULL),(84,'修改角色信息','POST:/admin/role/updateRole','2023-01-15 22:57:19',NULL),(85,'根据uid查询角色','POST:/admin/role/queryRoleByUid','2023-01-15 22:57:19',NULL),(86,'查询满足要求的所有角色信息','POST:/admin/role/queryListRoleByCondition','2023-01-15 22:57:19',NULL),(87,'删除角色','POST:/admin/role/physicalDeleteRole','2023-01-15 22:57:19',NULL),(88,'插入角色','POST:/admin/role/insertRole','2023-01-15 22:57:19',NULL),(89,'修改系统设置信息','POST:/admin/sysSetting/updateSysSetting','2023-01-15 22:57:19',NULL),(90,'根据uid查询系统设置','POST:/admin/sysSetting/querySysSettingByUid','2023-01-15 22:57:19',NULL),(91,'查询满足要求的所有系统设置','POST:/admin/sysSetting/queryListSysSettingByCondition','2023-01-15 22:57:19',NULL),(92,'删除系统设置','POST:/admin/sysSetting/physicalDeleteSysSetting','2023-01-15 22:57:19',NULL),(93,'插入系统设置','POST:/admin/sysSetting/insertSysSetting','2023-01-15 22:57:19',NULL),(94,'修改说说内容','POST:/blog/talk/updateTalk','2023-01-15 22:57:19',NULL),(95,'根据uid查询说说','POST:/blog/talk/queryTalkByUid','2023-01-15 22:57:19',NULL),(96,'根据条件查询说说','POST:/blog/talk/queryListTalkByCondition','2023-01-15 22:57:19',NULL),(97,'物理删除说说','POST:/blog/talk/physicalDeleteTalk','2023-01-15 22:57:19',NULL),(98,'逻辑删除说说','POST:/blog/talk/logicDeleteTalk','2023-01-15 22:57:19',NULL),(99,'插入新的说说','POST:/blog/talk/insertTalk','2023-01-15 22:57:19',NULL),(100,'修改文章数据','POST:/blog/article/updateArticle','2023-01-15 22:57:19',NULL),(101,'修改文章阅读数','POST:/blog/article/updateArticleReadNum','2023-01-15 22:57:19',NULL),(102,'修改文章点赞数','POST:/blog/article/updateArticleLikeNum','2023-01-15 22:57:19',NULL),(103,'通过条件查询文章数据','POST:/blog/article/queryListArticleByCondition','2023-01-15 22:57:19',NULL),(104,'根据uid查询文章数据','POST:/blog/article/queryArticleByUid','2023-01-15 22:57:19',NULL),(105,'物理删除文章','POST:/blog/article/physicalDeleteArticle','2023-01-15 22:57:19',NULL),(106,'逻辑删除文章','POST:/blog/article/logicDeleteArticle','2023-01-15 22:57:19',NULL),(107,'插入新文章','POST:/blog/article/insertArticle','2023-01-15 22:57:19',NULL),(108,'修改公告内容','POST:/blog/bulletin/updateBulletin','2023-01-15 22:57:19',NULL),(109,'根据条件，查询满足要求的公告','POST:/blog/bulletin/queryListBulletinByCondition','2023-01-15 22:57:19',NULL),(110,'根据uid查询公告','POST:/blog/bulletin/queryBulletinByUid','2023-01-15 22:57:19',NULL),(111,'物理删除公告','POST:/blog/bulletin/physicalDeleteBulletin','2023-01-15 22:57:19',NULL),(112,'逻辑删除公告','POST:/blog/bulletin/logicDeleteBulletin','2023-01-15 22:57:19',NULL),(113,'插入公告','POST:/blog/bulletin/insertBulletin','2023-01-15 22:57:19',NULL),(114,'修改类别信息','POST:/blog/category/updateCategory','2023-01-15 22:57:19',NULL),(115,'根据条件查询类别信息','POST:/blog/category/queryListCategoryByCondition','2023-01-15 22:57:19',NULL),(116,'根据uid查询类别信息','POST:/blog/category/queryCategoryByUid','2023-01-15 22:57:19',NULL),(117,'物理删除类别信息','POST:/blog/category/physicalDeleteCategory','2023-01-15 22:57:19',NULL),(118,'逻辑删除类别','POST:/blog/category/logicDeleteCategory','2023-01-15 22:57:19',NULL),(119,'插入类别信息','POST:/blog/category/insertCategory','2023-01-15 22:57:19',NULL),(120,'修改友情链接信息','POST:/blog/link/updateLink','2023-01-15 22:57:19',NULL),(121,'修改友情链接的发布状态','POST:/blog/link/updateLinkPublishStatus','2023-01-15 22:57:19',NULL),(122,'根据条件查询','POST:/blog/link/queryListLinkByCondition','2023-01-15 22:57:19',NULL),(123,'根据uid查询','POST:/blog/link/queryLinkByUid','2023-01-15 22:57:19',NULL),(124,'根据uid删除对应的友情链接','POST:/blog/link/physicalDeleteLink','2023-01-15 22:57:19',NULL),(125,'插入新友情链接','POST:/blog/link/insertLink','2023-01-15 22:57:19',NULL),(126,'修改标签信息','POST:/blog/tag/updateTag','2023-01-15 22:57:19',NULL),(127,'根据uid查询标签','POST:/blog/tag/queryTagByUid','2023-01-15 22:57:19',NULL),(128,'根据条件查询标签','POST:/blog/tag/queryListTagByCondition','2023-01-15 22:57:19',NULL),(129,'物理删除标签数据','POST:/blog/tag/physicalDeleteTag','2023-01-15 22:57:19',NULL),(130,'逻辑删除标签信息','POST:/blog/tag/logicDeleteTag','2023-01-15 22:57:19',NULL),(131,'插入新标签','POST:/blog/tag/insertTag','2023-01-15 22:57:19',NULL),(132,'发送普通文本','POST:/message/sendMail/simpleText','2023-01-15 22:57:19',NULL),(133,'重新发送自定义邮件','POST:/message/sendMail/resendCustomMail','2023-01-15 22:57:19',NULL),(134,'发送自定义html','POST:/message/sendMail/customMail','2023-01-15 22:57:19',NULL),(135,'更新消费消息','POST:/message/messageLog/updateMessageLog','2023-01-15 22:57:19',NULL),(136,'重新投递此messageLogUid对应的mq消息','POST:/message/messageLog/resendRabbitMqMessage','2023-01-15 22:57:19',NULL),(137,'根据uid查询消费消息','POST:/message/messageLog/queryMessageLogByUid','2023-01-15 22:57:19',NULL),(138,'查询所有消费消息','POST:/message/messageLog/queryListMessageLogByCondition','2023-01-15 22:57:19',NULL),(139,'删除消费消息','POST:/message/messageLog/physicalDeleteMessageLog','2023-01-15 22:57:19',NULL),(140,'插入新消费消息','POST:/message/messageLog/insertMessageLog','2023-01-15 22:57:19',NULL),(141,'根据emailDO实体，更新邮箱记录','POST:/message/email/updateEmail','2023-01-15 22:57:19',NULL),(142,'根据EmailDO实体中的字段以及分页参数查询所有数据，返回一个集合','POST:/message/email/queryListEmailByCondition','2023-01-15 22:57:19',NULL),(143,'根据userUid进行查询','POST:/message/email/queryEmailByUserUid','2023-01-15 22:57:19',NULL),(144,'根据uid查询','POST:/message/email/queryEmailByUid','2023-01-15 22:57:19',NULL),(145,'根据邮箱号进行查询','POST:/message/email/queryByEmailNumber','2023-01-15 22:57:19',NULL),(146,'根据唯一uid删除某条邮箱','POST:/message/email/physicalDeleteEmail','2023-01-15 22:57:19',NULL),(147,'向数据库中插入新的邮箱记录，比如主机，授权码等','POST:/message/email/insertEmail','2023-01-15 22:57:20',NULL),(148,'根据uid更新邮件发送日志','POST:/message/emailLog/updateEmailLog','2023-01-15 22:57:20',NULL),(149,'查询所有邮件发送日志','POST:/message/emailLog/queryListEmailLogByCondition','2023-01-15 22:57:20',NULL),(150,'删除uid对应邮件发送日志','POST:/message/emailLog/physicalDeleteEmailLog','2023-01-15 22:57:20',NULL),(151,'插入邮件发送日志','POST:/message/emailLog/insertEmailLog','2023-01-15 22:57:20',NULL),(152,'更新评论','POST:/comment/updateComment','2023-01-15 22:57:20',NULL),(153,'重新发送评论的邮件通知','POST:/comment/resendEmail','2023-01-15 22:57:20',NULL),(154,'查询所有满足要求的所有评论','POST:/comment/queryListCommentByUidArr','2023-01-15 22:57:20',NULL),(155,'根据自定义条件查询所有评论','POST:/comment/queryListCommentByCondition','2023-01-15 22:57:20',NULL),(156,'根据uid查询评论','POST:/comment/queryCommentByUid','2023-01-15 22:57:20',NULL),(157,'删除单条评论','POST:/comment/physicalDeleteComment','2023-01-15 22:57:20',NULL),(158,'插入新评论','POST:/comment/insertComment','2023-01-15 22:57:20',NULL),(159,'根据用户名查询','POST:/login/loginInfo/queryLoginInfoByUsername','2023-01-15 22:57:20',NULL),(160,'根据条件查询','POST:/login/loginInfo/queryListLoginInfoByCondition','2023-01-15 22:57:20',NULL),(161,'根据uid删除登录日志','POST:/login/loginInfo/physicalDeleteLoginInfo','2023-01-15 22:57:20',NULL),(162,'根据uid，批量删除','POST:/login/loginInfo/batchDeleteLoginInfoByUid','2023-01-15 22:57:20',NULL),(163,'修改文件属性','POST:/file/updateFile','2023-01-15 22:57:20',NULL),(164,'在typora中自动上传图片','POST:/file/typoraUploadFile','2023-01-15 22:57:20',NULL),(165,'上传单个文件','POST:/file/singleUploadFile','2023-01-15 22:57:20',NULL),(166,'查询指定后缀的所有文件','POST:/file/querySpecifyFormatFiles','2023-01-15 22:57:20',NULL),(167,'查询该userUid所对应的所有文件的后缀信息','POST:/file/queryListFileFormat','2023-01-15 22:57:20',NULL),(168,'查询文件数据','POST:/file/queryListFileByCondition','2023-01-15 22:57:20',NULL),(169,'查询文件数据','POST:/file/queryFileByUid','2023-01-15 22:57:20',NULL),(170,'根据uid删除某个文件的信息，从数据库中删除','POST:/file/physicalDeleteFileInfo','2023-01-15 22:57:20',NULL),(171,'上传多个文件，返回集合','POST:/file/multiUploadFile','2023-01-15 22:57:20',NULL),(172,'根据uid删除某个文件','POST:/file/deleteFile','2023-01-15 22:57:20',NULL),(173,'导入文章','POST:/blog/article/importArticle','2023-02-09 21:35:14',NULL);
/*!40000 ALTER TABLE `au_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `au_role`
--

DROP TABLE IF EXISTS `au_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_role` (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid，自增',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色的名称，不用添加ROLE_',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '用户的状态 1：已禁用 0：未禁用',
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE KEY `unique_role_name_index` (`name`) USING BTREE,
  KEY `create_time_index` (`create_time`) USING BTREE COMMENT '创建时间单独索引'
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_role`
--

LOCK TABLES `au_role` WRITE;
/*!40000 ALTER TABLE `au_role` DISABLE KEYS */;
INSERT INTO `au_role` (`uid`, `name`, `create_time`, `update_time`, `status`) VALUES (3,'admin','2022-05-07 15:09:15',NULL,0),(4,'user','2022-05-07 15:10:26',NULL,0),(5,'root','2022-05-07 15:10:40',NULL,0);
/*!40000 ALTER TABLE `au_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `au_role_permission`
--

DROP TABLE IF EXISTS `au_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_role_permission` (
  `uid` bigint NOT NULL AUTO_INCREMENT,
  `role_uid` bigint NOT NULL,
  `permission_uid` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `roleUid_index` (`role_uid`) USING BTREE,
  KEY `permissionUid_index` (`permission_uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_role_permission`
--

LOCK TABLES `au_role_permission` WRITE;
/*!40000 ALTER TABLE `au_role_permission` DISABLE KEYS */;
INSERT INTO `au_role_permission` (`uid`, `role_uid`, `permission_uid`, `create_time`, `update_time`) VALUES (5,17,4,'2023-01-14 22:47:03',NULL),(6,3,5,'2023-01-14 22:47:03',NULL),(7,4,6,'2023-01-14 22:47:03',NULL),(8,3,7,'2023-01-14 22:47:03',NULL),(9,5,8,'2023-01-14 22:47:03',NULL),(10,3,9,'2023-01-14 22:47:03',NULL),(11,3,10,'2023-01-14 22:47:03',NULL),(12,4,12,'2023-01-15 23:01:18',NULL),(13,4,13,'2023-01-15 23:01:18',NULL),(14,4,14,'2023-01-15 23:01:18',NULL),(15,4,15,'2023-01-15 23:01:19',NULL),(16,4,17,'2023-01-15 23:01:19',NULL),(17,4,18,'2023-01-15 23:01:19',NULL),(18,4,36,'2023-01-15 23:01:19',NULL),(19,4,37,'2023-01-15 23:01:19',NULL),(20,4,38,'2023-01-15 23:01:19',NULL),(21,4,39,'2023-01-15 23:01:19',NULL),(22,4,40,'2023-01-15 23:01:19',NULL),(23,4,41,'2023-01-15 23:01:19',NULL),(24,4,42,'2023-01-15 23:01:19',NULL),(25,4,43,'2023-01-15 23:01:19',NULL),(26,4,45,'2023-01-15 23:01:19',NULL),(27,4,46,'2023-01-15 23:01:19',NULL),(28,4,47,'2023-01-15 23:01:19',NULL),(29,4,48,'2023-01-15 23:01:19',NULL),(30,4,56,'2023-01-15 23:01:19',NULL),(31,4,57,'2023-01-15 23:01:19',NULL),(32,4,59,'2023-01-15 23:01:19',NULL),(33,4,60,'2023-01-15 23:01:19',NULL),(34,4,61,'2023-01-15 23:01:19',NULL),(35,4,62,'2023-01-15 23:01:19',NULL),(36,4,63,'2023-01-15 23:01:19',NULL),(37,4,64,'2023-01-15 23:01:19',NULL),(38,4,66,'2023-01-15 23:01:19',NULL),(39,4,67,'2023-01-15 23:01:19',NULL),(40,4,68,'2023-01-15 23:01:19',NULL),(41,4,70,'2023-01-15 23:01:19',NULL),(42,4,73,'2023-01-15 23:01:19',NULL),(43,4,72,'2023-01-15 23:01:19',NULL),(44,4,91,'2023-01-15 23:01:19',NULL),(45,4,159,'2023-01-15 23:01:19',NULL),(46,4,160,'2023-01-15 23:01:19',NULL),(47,4,152,'2023-01-15 23:01:19',NULL),(48,4,153,'2023-01-15 23:01:19',NULL),(49,4,154,'2023-01-15 23:01:19',NULL),(50,4,155,'2023-01-15 23:01:19',NULL),(51,4,156,'2023-01-15 23:01:19',NULL),(52,4,157,'2023-01-15 23:01:19',NULL),(53,4,158,'2023-01-15 23:01:19',NULL),(54,4,94,'2023-01-15 23:01:19',NULL),(55,4,95,'2023-01-15 23:01:19',NULL),(56,4,96,'2023-01-15 23:01:19',NULL),(57,4,97,'2023-01-15 23:01:19',NULL),(58,4,98,'2023-01-15 23:01:19',NULL),(59,4,99,'2023-01-15 23:01:19',NULL),(60,4,100,'2023-01-15 23:01:19',NULL),(61,4,101,'2023-01-15 23:01:19',NULL),(62,4,102,'2023-01-15 23:01:19',NULL),(63,4,103,'2023-01-15 23:01:19',NULL),(64,4,104,'2023-01-15 23:01:19',NULL),(65,4,105,'2023-01-15 23:01:19',NULL),(66,4,106,'2023-01-15 23:01:19',NULL),(67,4,107,'2023-01-15 23:01:19',NULL),(68,4,108,'2023-01-15 23:01:19',NULL),(69,4,109,'2023-01-15 23:01:19',NULL),(70,4,110,'2023-01-15 23:01:19',NULL),(71,4,111,'2023-01-15 23:01:19',NULL),(72,4,112,'2023-01-15 23:01:19',NULL),(73,4,113,'2023-01-15 23:01:19',NULL),(74,4,114,'2023-01-15 23:01:19',NULL),(75,4,115,'2023-01-15 23:01:19',NULL),(76,4,116,'2023-01-15 23:01:19',NULL),(77,4,117,'2023-01-15 23:01:19',NULL),(78,4,118,'2023-01-15 23:01:19',NULL),(79,4,119,'2023-01-15 23:01:19',NULL),(80,4,120,'2023-01-15 23:01:19',NULL),(81,4,121,'2023-01-15 23:01:19',NULL),(82,4,122,'2023-01-15 23:01:19',NULL),(83,4,123,'2023-01-15 23:01:19',NULL),(84,4,124,'2023-01-15 23:01:19',NULL),(85,4,125,'2023-01-15 23:01:19',NULL),(86,4,126,'2023-01-15 23:01:19',NULL),(87,4,127,'2023-01-15 23:01:19',NULL),(88,4,128,'2023-01-15 23:01:19',NULL),(89,4,129,'2023-01-15 23:01:19',NULL),(90,4,130,'2023-01-15 23:01:19',NULL),(91,4,131,'2023-01-15 23:01:19',NULL),(92,4,132,'2023-01-15 23:01:19',NULL),(93,4,133,'2023-01-15 23:01:19',NULL),(94,4,134,'2023-01-15 23:01:20',NULL),(95,4,141,'2023-01-15 23:01:20',NULL),(96,4,143,'2023-01-15 23:01:20',NULL),(97,4,144,'2023-01-15 23:01:20',NULL),(98,4,145,'2023-01-15 23:01:20',NULL),(99,4,149,'2023-01-15 23:01:20',NULL),(100,4,163,'2023-01-15 23:01:20',NULL),(101,4,164,'2023-01-15 23:01:20',NULL),(102,4,165,'2023-01-15 23:01:20',NULL),(103,4,166,'2023-01-15 23:01:20',NULL),(104,4,167,'2023-01-15 23:01:20',NULL),(105,4,168,'2023-01-15 23:01:20',NULL),(106,4,169,'2023-01-15 23:01:20',NULL),(107,4,170,'2023-01-15 23:01:20',NULL),(108,4,171,'2023-01-15 23:01:20',NULL),(109,4,172,'2023-01-15 23:01:20',NULL),(110,4,173,'2023-02-09 21:36:43',NULL);
/*!40000 ALTER TABLE `au_role_permission` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `admin_router`
--
DROP TABLE  IF EXISTS `admin_router`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_router` (
    `uid` bigint NOT NULL AUTO_INCREMENT,
    `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `single_layout` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `requires_auth` bool NOT NULL ,
    `keepalive` bool NOT NULL ,
    `icon` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `local_icon` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `hide` bool NOT NULL,
    `href` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `order` int NOT NULL,
    `affix` bool NOT NULL,
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `component` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `son_router_uids` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `parent_router_rid` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Dumping data for table `au_setting`
--

LOCK TABLES `admin_router` WRITE;
/*!40000 ALTER TABLE `admin_router` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin_router` ENABLE KEYS */;
UNLOCK TABLES;



--
-- Table structure for table `au_setting`
--

DROP TABLE IF EXISTS `au_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_setting` (
  `uid` bigint NOT NULL AUTO_INCREMENT,
  `param_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `param_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `param_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_setting`
--

LOCK TABLES `au_setting` WRITE;
/*!40000 ALTER TABLE `au_setting` DISABLE KEYS */;
INSERT INTO `au_setting` (`uid`, `param_code`, `param_name`, `param_value`, `create_time`, `update_time`) VALUES (1,'nginx_file_host','nginx_file_host','https://cdn.xcye.xyz','2023-01-14 22:55:52','2023-01-15 00:18:48'),(2,'admin-web-url','admin-web-url','https://admin.xcye.xyz/','2023-01-27 19:10:06','2023-03-12 19:33:51'),(3,'page-web-url','page-web-url','https://blog.xcye.xyz/','2023-01-27 19:10:31','2023-03-12 19:33:40'),(4,'lazy-loading-img','lazy-loading-img','https://img1.imgtp.com/2023/03/12/zKprTaVP.png','2023-01-27 20:13:32','2023-01-27 20:22:33');
/*!40000 ALTER TABLE `au_setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `au_site`
--

DROP TABLE IF EXISTS `au_site`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_site` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `site_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '站点的icon地址',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '站点的标题 浏览器顶部部分',
  `logo_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '站点的前台logo文字',
  `site_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '站点的logo地址',
  `site_center_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '站点前台中间部分logo',
  `additional_head` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '站点额外的head信息，直接传入<script/>这种',
  `user_uid` bigint NOT NULL COMMENT '此站点信息属于哪个用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '0:不删除 1： 删除',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `user_index` (`user_uid`) USING BTREE,
  KEY `create_index` (`create_time`) USING BTREE COMMENT '创建时间',
  KEY `union_site_index` (`uid`,`user_uid`,`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_site`
--

LOCK TABLES `au_site` WRITE;
/*!40000 ALTER TABLE `au_site` DISABLE KEYS */;
/*!40000 ALTER TABLE `au_site` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `au_site_setting`
--

DROP TABLE IF EXISTS `au_site_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_site_setting` (
  `uid` bigint NOT NULL AUTO_INCREMENT,
  `user_uid` bigint NOT NULL,
  `param_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `param_value` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_site_setting`
--

LOCK TABLES `au_site_setting` WRITE;
/*!40000 ALTER TABLE `au_site_setting` DISABLE KEYS */;
INSERT INTO `au_site_setting` (`uid`, `user_uid`, `param_name`, `param_value`, `create_time`, `update_time`) VALUES (1,1522074993315815424,'1522074993315815424AllPageInfo','[{\"name\":\"首页\",\"url\":\"/user/1522074993315815424\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1522074993315815424\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1522074993315815424\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1522074993315815424\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1522074993315815424\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1522074993315815424/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1522074993315815424\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1522074993315815424\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]','2023-01-15 20:54:48','2023-01-15 20:55:44'),(2,1522074993315815424,'1522074993315815424NavbarInfo','[{\"name\":\"首页\",\"url\":\"/user/1522074993315815424\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1522074993315815424\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1522074993315815424\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1522074993315815424\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1522074993315815424\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1522074993315815424\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1522074993315815424\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1522074993315815424/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[{\"name\":\"说说1\",\"url\":\"/shareSpace/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1522074993315815424\",\"icon\":\"fa:comments-o\",\"children\":[]}]}]','2023-01-15 21:00:06','2023-01-16 16:02:59'),(3,1522074993315815424,'1522074993315815424SiteInfo','{\"readme\":\"#HiauUsernameua\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://picture.xcye.xyz/image-20220328221012634.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"pplax博客系统\",\"backgroundImage\":\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"pplax博客系统\",\"url\":\"http://xcye.xyz/user/1522074993315815424\",\"logo\":\"http://127.0.0.1/pplax-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"pplax博客系统和pplax主题作者\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1522074993315815424\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}],\"pcBackgroundImageList\":[\"https://gcore.jsdelivr.net/gh/xcyeye/cdn@main/image/pc/10.jpg\"],\"mobileBackgroundImageList\":[\"https://gcore.jsdelivr.net/gh/xcyeye/cdn@main/image/mobile/1.png\",\"https://gcore.jsdelivr.net/gh/xcyeye/cdn@main/image/mobile/2.png\",\"https://gcore.jsdelivr.net/gh/xcyeye/cdn@main/image/mobile/4.jpg\"]}','2023-01-25 23:55:30','2023-01-27 13:46:41'),(68,1634877081002713088,'1634877081002713088SiteInfo','{\"readme\":\"# Hi xcyeye\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://img1.imgtp.com/2023/03/12/zKprTaVP.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"pplax博客系统\",\"backgroundImage\":\"https://img1.imgtp.com/2023/03/12/FmYKnTsB.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"pplax博客系统\",\"url\":\"http://xcye.xyz/user/1634877081002713088\",\"logo\":\"https://img1.imgtp.com/2023/03/12/KnUhin4l.png\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"其生若浮，其死若休\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1634877081002713088\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}],\"pcBackgroundImageList\":[\"https://img1.imgtp.com/2023/03/12/0rEXWQ7A.jpg\"],\"mobileBackgroundImageList\":[\"https://img1.imgtp.com/2023/03/12/ZMNyUr3q.png\",\"https://img1.imgtp.com/2023/03/12/FNxsWADm.png\",\"https://img1.imgtp.com/2023/03/12/qOHgaSsC.png\"]}','2023-03-12 19:20:45','2023-03-12 19:36:34'),(69,1634877081002713088,'1634877081002713088NavbarInfo','[{\"name\":\"首页\",\"url\":\"/user/1634877081002713088\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1634877081002713088\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1634877081002713088\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1634877081002713088\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1634877081002713088\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1634877081002713088\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1634877081002713088\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1634877081002713088/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[{\"name\":\"说说1\",\"url\":\"/shareSpace/1634877081002713088\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1634877081002713088\",\"icon\":\"fa:comments-o\",\"children\":[]}]}]','2023-03-12 19:20:45',NULL),(70,1634877081002713088,'1634877081002713088AllPageInfo','[{\"name\":\"首页\",\"url\":\"/user/1634877081002713088\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1634877081002713088\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1634877081002713088\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1634877081002713088\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1634877081002713088\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1634877081002713088\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1634877081002713088\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1634877081002713088/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1634877081002713088\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1634877081002713088\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]','2023-03-12 19:20:45',NULL),(71,1634877483471347712,'1634877483471347712SiteInfo','{\"readme\":\"# Hi demopplax\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://picture.xcye.xyz/image-20220328221012634.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"pplax博客系统\",\"backgroundImage\":\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"pplax博客系统\",\"url\":\"http://xcye.xyz/user/1522074993315815424\",\"logo\":\"http://127.0.0.1/pplax-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"pplax博客系统和pplax主题作者\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1522074993315815424\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}]}','2023-03-12 19:22:21',NULL),(72,1634877483471347712,'1634877483471347712NavbarInfo','[{\"name\":\"首页\",\"url\":\"/user/1634877483471347712\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1634877483471347712\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1634877483471347712\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1634877483471347712\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1634877483471347712\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1634877483471347712\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1634877483471347712\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1634877483471347712/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[{\"name\":\"说说1\",\"url\":\"/shareSpace/1634877483471347712\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1634877483471347712\",\"icon\":\"fa:comments-o\",\"children\":[]}]}]','2023-03-12 19:22:21',NULL),(73,1634877483471347712,'1634877483471347712AllPageInfo','[{\"name\":\"首页\",\"url\":\"/user/1634877483471347712\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1634877483471347712\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1634877483471347712\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1634877483471347712\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1634877483471347712\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1634877483471347712\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1634877483471347712\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1634877483471347712/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1634877483471347712\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1634877483471347712\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]','2023-03-12 19:22:21',NULL),(74,1634902506689011712,'1634902506689011712SiteInfo','{\"readme\":\"# Hi demopplax\\n\",\"showWave\":true,\"showTopImgBubble\":true,\"mobilePageSidebar\":true,\"latestPageSize\":6,\"githubUrl\":\"https://github.com/xcyeye\",\"homePageLazyLoadingImg\":\"https://picture.xcye.xyz/image-20220328221012634.png\",\"randomPictureInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"defaultCoverRequestInterface\":\"https://cdn.seovx.com/d/?mom=302\",\"footerInfo\":{\"enable\":true,\"isShowThemeCopyright\":true,\"isShowRunTime\":true,\"prefixRuntime\":\"pplax博客系统\",\"backgroundImage\":\"https://w.wallhaven.cc/full/x6/wallhaven-x68r2l.jpg\",\"footInfo\":[\"Copyright©byxcyeAllRightsReserved.\",\"<atarget=\'_blank\'href=\'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=53060202000142\'style=\'display:inline-block;text-decoration:none;height:20px;line-height:20px;\'><imgsrc=\'\'style=\'float:left;\'/><pstyle=\'float:left;height:20px;line-height:20px;margin:0px0px0px5px;\'>滇公网安备53060202000142号</p></a>\"]},\"friendLinkSiteInformation\":{\"title\":\"pplax博客系统\",\"url\":\"http://xcye.xyz/user/1522074993315815424\",\"logo\":\"http://127.0.0.1/pplax-upload/jpg/2023/1/illust_86447159_20220928_0809051673786145660.jpg\",\"cover\":\"https://w.wallhaven.cc/full/o5/wallhaven-o559j7.jpg\",\"describe\":\"pplax博客系统和pplax主题作者\",\"contact\":\"2291308094\"},\"socialsArr\":[{\"aHref\":\"tencent://message/?uin=2291308094\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:qq\",\"color\":\"#90f1ef\",\"showImgSrc\":\"\"},{\"aHref\":\"https://github.com/xcyeye/\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:github\",\"color\":\"#bbe6e4\",\"showImgSrc\":\"\"},{\"aHref\":\"https://space.bilibili.com/483962286\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa-brands:envira\",\"color\":\"efd1cd\",\"showImgSrc\":\"\"},{\"aHref\":\"https://music.163.com/#/user/home?id=1411050784\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:music\",\"color\":\"#6fffe9\",\"showImgSrc\":\"\"},{\"aHref\":\"mailto:2291308094@qq.com\",\"isHome\":true,\"show\":true,\"sidebar\":true,\"icon\":\"fa:envelope\",\"color\":\"#f2b5d4\",\"showImgSrc\":\"\"},{\"aHref\":\"/friendLink/1522074993315815424\",\"isHome\":true,\"show\":true,\"sidebar\":false,\"icon\":\"fa:paper-plane\",\"color\":\"#b8f2e6\",\"showImgSrc\":\"\"}]}','2023-03-12 21:01:47',NULL),(75,1634902506689011712,'1634902506689011712NavbarInfo','[{\"name\":\"首页\",\"url\":\"/user/1634902506689011712\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1634902506689011712\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1634902506689011712\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1634902506689011712\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1634902506689011712\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1634902506689011712\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1634902506689011712\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1634902506689011712/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[{\"name\":\"说说1\",\"url\":\"/shareSpace/1634902506689011712\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1634902506689011712\",\"icon\":\"fa:comments-o\",\"children\":[]}]}]','2023-03-12 21:01:47',NULL),(76,1634902506689011712,'1634902506689011712AllPageInfo','[{\"name\":\"首页\",\"url\":\"/user/1634902506689011712\",\"icon\":\"fa:home\",\"children\":[]},{\"name\":\"友情链接\",\"url\":\"/friendLink/1634902506689011712\",\"icon\":\"fa:paper-plane\",\"children\":[]},{\"name\":\"关于\",\"url\":\"/about/1634902506689011712\",\"icon\":\"fa:pagelines\",\"outLink\":false,\"children\":[]},{\"name\":\"说说1\",\"url\":\"/shareSpace/1634902506689011712\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"说说2\",\"url\":\"/shareSpace-page/1634902506689011712\",\"icon\":\"fa:comments-o\",\"children\":[]},{\"name\":\"时间轴\",\"url\":\"/archive/1634902506689011712\",\"icon\":\"fa:hourglass-3\",\"children\":[]},{\"name\":\"相册\",\"url\":\"/photo/1634902506689011712\",\"icon\":\"fa:image\",\"children\":[]},{\"name\":\"标签\",\"url\":\"/tag/1634902506689011712/\",\"icon\":\"mdi:abugida-thai\",\"children\":[]},{\"name\":\"分类\",\"url\":\"/category/1634902506689011712\",\"icon\":\"mdi:alarm-bell\",\"outLink\":false,\"children\":[]},{\"name\":\"评论\",\"url\":\"/comment/1634902506689011712\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]},{\"name\":\"说说\",\"url\":\"\",\"icon\":\"fa:comments\",\"outLink\":false,\"children\":[]}]','2023-03-12 21:01:47',NULL);
/*!40000 ALTER TABLE `au_site_setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `au_social`
--

DROP TABLE IF EXISTS `au_social`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_social` (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid,自增',
  `social_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '社交名称',
  `social_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此社交图标的地址',
  `social_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此社交的链接',
  `is_show` tinyint NOT NULL DEFAULT '1' COMMENT '1： 显示此社交 0： 不显示',
  `user_uid` bigint DEFAULT NULL COMMENT '此社交属于哪个用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '1: 删除 0：不删除',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `create_time_index` (`create_time`) USING BTREE COMMENT '创建时间索引',
  KEY `user_uid_index` (`user_uid`) USING BTREE COMMENT '用户uid索引',
  KEY `union_social_index` (`uid`,`user_uid`,`create_time`) USING BTREE COMMENT 'uid,user_uid,create_time联合索引'
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_social`
--

LOCK TABLES `au_social` WRITE;
/*!40000 ALTER TABLE `au_social` DISABLE KEYS */;
/*!40000 ALTER TABLE `au_social` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `au_user`
--

DROP TABLE IF EXISTS `au_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_user` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `user_summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户简介',
  `nickname` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '用户性别',
  `login_uid` bigint DEFAULT NULL COMMENT '用户登录记录的uid',
  `site_uid` bigint DEFAULT NULL COMMENT '用户的站点配置uid',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户的头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户的密码',
  `username` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `profession` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户的工作集合',
  `email_uid` bigint DEFAULT NULL COMMENT '此用户对应的邮箱uid',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除 1：删除 ',
  `is_account_lock` tinyint NOT NULL DEFAULT '0' COMMENT '1: 账户被锁住，0：未被锁住',
  `is_verify_email` tinyint NOT NULL COMMENT '1: 邮箱已验证，0：邮箱未验证',
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE KEY `unique_username_index` (`username`) USING BTREE COMMENT '用户名索引',
  UNIQUE KEY `site_uid` (`site_uid`) USING BTREE,
  UNIQUE KEY `user_email` (`email_uid`) USING BTREE,
  UNIQUE KEY `user_login` (`login_uid`) USING BTREE,
  UNIQUE KEY `unique_email_uid_index` (`email_uid`) USING BTREE COMMENT '邮箱Uid索引',
  UNIQUE KEY `unique_site_uid_index` (`site_uid`) USING BTREE COMMENT 'SiteUid索引',
  UNIQUE KEY `unique_login_uid_index` (`login_uid`) USING BTREE COMMENT 'LoginUid索引',
  KEY `create_time_index` (`create_time`) USING BTREE COMMENT '创建时间单独索引',
  KEY `union_user_index` (`uid`,`username`,`create_time`) USING BTREE COMMENT 'uid，用户名，创建时间联合索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_user`
--

LOCK TABLES `au_user` WRITE;
/*!40000 ALTER TABLE `au_user` DISABLE KEYS */;
INSERT INTO `au_user` (`uid`, `user_summary`, `nickname`, `gender`, `login_uid`, `site_uid`, `avatar`, `password`, `username`, `profession`, `email_uid`, `create_time`, `update_time`, `is_delete`, `is_account_lock`, `is_verify_email`) VALUES (1634877081002713088,'其生若浮、其死若休','pplax-new','SECRET',NULL,NULL,'https://img1.imgtp.com/2023/03/12/KnUhin4l.png','$2a$10$AG5XWDq/BhgYdgzN4nTKvu7at6i3r/WXgo.XXueOxiCb1B97Ij0gO','xcyeye','计算机科学与技术',1634878801918877696,'2023-03-12 19:20:45','2023-03-12 20:50:14',0,0,1),(1634902506689011712,NULL,'pplax-new','SECRET',NULL,NULL,'https://picture.xcye.xyz/avatar.jpg','$2a$10$hD3LK5OVkhhAu3ZpAGNmhesga8RRMa/fg3J6IZUQHS2jbFf2.ioIa','demopplax',NULL,NULL,'2023-03-12 21:01:47',NULL,0,0,0);
/*!40000 ALTER TABLE `au_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `au_user_role`
--

DROP TABLE IF EXISTS `au_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_user_role` (
  `uid` bigint NOT NULL AUTO_INCREMENT,
  `role_uid` bigint NOT NULL COMMENT '角色uid',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `user_uid` bigint NOT NULL COMMENT '用户uid',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `roleUid_index` (`role_uid`) USING BTREE,
  KEY `userUid_index` (`user_uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_user_role`
--

LOCK TABLES `au_user_role` WRITE;
/*!40000 ALTER TABLE `au_user_role` DISABLE KEYS */;
INSERT INTO `au_user_role` (`uid`, `role_uid`, `create_time`, `update_time`, `user_uid`) VALUES (13,3,'2023-01-14 21:49:47','2023-03-12 19:23:32',1634877081002713088),(15,5,'2023-01-14 21:49:47','2023-03-12 19:23:37',1634877081002713088),(84,4,'2023-03-12 19:20:45',NULL,1634877081002713088),(86,4,'2023-03-12 21:01:47',NULL,1634902506689011712),(87,5,'2023-03-12 21:02:35',NULL,1634902506689011712);
/*!40000 ALTER TABLE `au_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `au_white_url`
--

DROP TABLE IF EXISTS `au_white_url`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_white_url` (
  `uid` int NOT NULL AUTO_INCREMENT,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '白名单地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE KEY `url_unique_index` (`url`) USING BTREE COMMENT '地址索引',
  KEY `create_time_index` (`create_time`) USING BTREE COMMENT '创建时间单独索引',
  KEY `union_whiteUrl_index` (`uid`,`url`) USING BTREE COMMENT 'uid,url,create_time联合索引'
) ENGINE=InnoDB AUTO_INCREMENT=1100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_white_url`
--

LOCK TABLES `au_white_url` WRITE;
/*!40000 ALTER TABLE `au_white_url` DISABLE KEYS */;
INSERT INTO `au_white_url` (`uid`, `url`, `create_time`, `update_time`) VALUES (14,'POST:/auth/oauth/token/**','2022-05-13 18:42:16',NULL),(15,'GET:/auth/**','2022-05-13 18:44:38',NULL),(16,'POST:/auth/**','2022-05-13 19:11:44',NULL),(18,'POST:/blog/link/**','2022-05-13 22:32:21',NULL),(20,'POST:/auth/logout','2022-05-16 00:14:27',NULL),(21,'GET:/swaggerui/*','2022-05-16 22:02:36',NULL),(22,'GET:/swagger-ui/*','2022-05-16 22:03:24',NULL),(23,'GET:/admin/swagger-ui/*','2022-05-16 22:04:11',NULL),(24,'GET:/admin/swagger-ui/index.html','2022-05-16 22:04:41',NULL),(25,'GET:/admin/swagger-ui/index.htm','2022-05-16 22:05:38',NULL),(30,'GET:/admin/verifyAccount/**','2022-05-17 14:12:02',NULL),(32,'POST:/message/messageLog/**','2022-05-17 17:55:05',NULL),(36,'POST:/oauth/**','2022-05-19 13:26:03',NULL),(37,'GET:/swagger-ui.html','2022-05-21 00:19:46',NULL),(38,'GET:/v3/api-docs/**','2022-05-21 13:28:41',NULL),(39,'GET:/webjars/swagger-ui/**','2022-05-21 13:31:44',NULL),(40,'POST:/file/multi','2022-06-04 19:09:51',NULL),(43,'GET:/**/v3/api-docs','2022-06-25 15:27:11',NULL),(1070,'POST:/blog/article/queryListArticleByCondition','2023-01-14 21:58:32',NULL),(1071,'POST:/admin/user/queryListUserByCondition','2023-01-14 21:58:47',NULL),(1072,'POST:/admin/siteSetting/queryListSiteSettingByCondition','2023-01-14 22:01:41',NULL),(1073,'POST:/blog/category/queryListCategoryByCondition','2023-01-14 22:02:17',NULL),(1074,'POST:/blog/talk/queryListTalkByCondition','2023-01-14 22:02:34',NULL),(1075,'POST:/admin/user/queryUserByUid','2023-01-14 22:03:17',NULL),(1076,'POST:/admin/siteSetting/querySiteSettingByUserUid','2023-01-14 22:03:36',NULL),(1077,'POST:/blog/tag/queryListTagByCondition','2023-01-14 22:05:11',NULL),(1078,'POST:/blog/bulletin/queryListBulletinByCondition','2023-01-14 22:05:40',NULL),(1079,'POST:/blog/link/queryListLinkByCondition','2023-01-14 22:06:04',NULL),(1080,'POST:/blog/article/queryArticleByUid','2023-01-15 19:43:54',NULL),(1081,'POST:/comment/queryListCommentByUidArr','2023-01-15 19:44:08',NULL),(1082,'POST:/admin/user/queryUserByUsername','2023-01-15 19:44:25',NULL),(1083,'POST:/blog/article/updateArticleReadNum','2023-01-15 19:44:47',NULL),(1084,'POST:/comment/queryListCommentByCondition','2023-01-15 19:45:11',NULL),(1085,'POST:/blog/article/updateArticleLikeNum','2023-01-15 19:45:38',NULL),(1087,'POST:/file/singleUploadFile','2023-01-15 19:46:33',NULL),(1088,'POST:/admin/user/insertUser','2023-01-15 22:06:40',NULL),(1089,'POST:/message/email/queryByEmailNumber','2023-01-15 22:11:13',NULL),(1090,'POST:/message/email/insertEmail','2023-01-15 22:14:05',NULL),(1091,'POST:/auth/oauthClient/insertOauthClient','2023-01-15 22:14:22',NULL),(1092,'GET:/','2023-01-15 23:20:34',NULL),(1093,'POST:/blog/talk/updateTalkLikeNum','2023-01-16 23:55:40',NULL),(1094,'POST:/admin/sysSetting/queryListSysSettingByCondition','2023-01-17 23:24:44',NULL),(1095,'POST:/blog/article/queryListArticleByTagOrCategory','2023-01-19 14:06:01',NULL),(1099,'POST:/**/queryTotal*Count','2023-01-26 23:25:22',NULL);
/*!40000 ALTER TABLE `au_white_url` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `undo_log`
--

DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `undo_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1198 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `undo_log`
--

LOCK TABLES `undo_log` WRITE;
/*!40000 ALTER TABLE `undo_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `undo_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `pplax_article`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `pplax_article` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `pplax_article`;

--
-- Table structure for table `au_article`
--

DROP TABLE IF EXISTS `au_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_article` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `is_show_comment` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '文章是否展示评论，0：否，1：是',
  `accessory_uids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章有附件的话，附件的uid集合',
  `category_names` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章类别uid集合',
  `tag_names` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章标签uid集合',
  `is_publish` tinyint NOT NULL DEFAULT '1' COMMENT '文章是否发布，1：发布，0：不发布',
  `user_uid` bigint NOT NULL COMMENT '发布此篇文章的用户uid',
  `is_original_article` tinyint NOT NULL DEFAULT '1' COMMENT '是否原创，1：原创 0：不是原创',
  `original_article_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '如果是原创，则原创链接',
  `cover_picture_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章封面对应的图片uid',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章内容',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章标题',
  `summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文章简介',
  `is_timing` tinyint NOT NULL DEFAULT '0' COMMENT '是否定时发布 0： 不定时，1：定时',
  `timing_publish_time` timestamp NULL DEFAULT NULL COMMENT '定时发布时间',
  `like_number` int DEFAULT '0' COMMENT '文章点赞数',
  `read_number` int DEFAULT NULL COMMENT '阅读量',
  `comment_uids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '此篇文章对应的评论uid集合，只需要设置所有父评论的uid',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '此篇文章最后修改的时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文章发布时间',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除 逻辑删除 1： 已删除',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `userUid_index` (`user_uid`) USING BTREE,
  KEY `createTime_index` (`create_time`) USING BTREE,
  KEY `union_article_index` (`uid`,`user_uid`,`create_time`) USING BTREE,
  FULLTEXT KEY `fullText_index` (`content`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_article`
--

LOCK TABLES `au_article` WRITE;
/*!40000 ALTER TABLE `au_article` DISABLE KEYS */;
/*!40000 ALTER TABLE `au_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `au_bulletin`
--

DROP TABLE IF EXISTS `au_bulletin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_bulletin` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '公告的标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '公告创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '公告最后修改时间',
  `user_uid` bigint DEFAULT NULL COMMENT '发布此公告的用户uid',
  `is_show` tinyint NOT NULL DEFAULT '1' COMMENT '1: 显示公告 0： 不显示该公告',
  `is_timing` tinyint NOT NULL DEFAULT '0' COMMENT '1：定时发布 0： 不定时发布公告',
  `timing_publish_time` timestamp NULL DEFAULT NULL COMMENT '定时发布公告的时间',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '1:删除 0：未删除',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `createTime_index` (`create_time`) USING BTREE,
  KEY `userUid_index` (`user_uid`) USING BTREE,
  KEY `union_bulletin_index` (`uid`,`user_uid`,`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_bulletin`
--

LOCK TABLES `au_bulletin` WRITE;
/*!40000 ALTER TABLE `au_bulletin` DISABLE KEYS */;
/*!40000 ALTER TABLE `au_bulletin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `au_category`
--

DROP TABLE IF EXISTS `au_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_category` (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此类别的标题',
  `summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此类别的简介',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此类别的封面图地址',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '1: 删除 ，0：未删除',
  `user_uid` bigint NOT NULL COMMENT '用户的userUid',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `createTime_index` (`create_time`) USING BTREE,
  KEY `userUid_index` (`user_uid`) USING BTREE,
  KEY `union_category` (`uid`,`title`,`user_uid`,`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_category`
--

LOCK TABLES `au_category` WRITE;
/*!40000 ALTER TABLE `au_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `au_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `au_link`
--

DROP TABLE IF EXISTS `au_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_link` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `user_uid` bigint DEFAULT NULL COMMENT '此条友情链接是哪个用户的',
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此条友情链接属于哪个分类',
  `link_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'logo地址',
  `link_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '链接地址',
  `link_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '对方的名称',
  `link_description` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述信息',
  `link_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '对方站点的封面图',
  `is_publish` tinyint NOT NULL DEFAULT '1' COMMENT '是否展示此条友情链接 1：展示 0：不展示',
  `email` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此友情链接对应的站长邮箱',
  `qq_number` bigint DEFAULT NULL COMMENT '此友情链接对应的站长的qq号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `userUid_index` (`user_uid`) USING BTREE,
  KEY `createTime_index` (`create_time`) USING BTREE,
  KEY `categoryName_index` (`category_name`) USING BTREE,
  KEY `union_link_index` (`uid`,`user_uid`,`link_url`,`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_link`
--

LOCK TABLES `au_link` WRITE;
/*!40000 ALTER TABLE `au_link` DISABLE KEYS */;
/*!40000 ALTER TABLE `au_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `au_tag`
--

DROP TABLE IF EXISTS `au_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_tag` (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此标签的标题',
  `summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此标签的简介',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `cover_Url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此类别的封面图uid',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '1: 删除，0：未删除',
  `user_uid` bigint NOT NULL COMMENT '用户的userUid',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `userUid_index` (`user_uid`) USING BTREE,
  KEY `createTime_index` (`create_time`) USING BTREE,
  KEY `union_tag_index` (`uid`,`title`,`user_uid`,`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_tag`
--

LOCK TABLES `au_tag` WRITE;
/*!40000 ALTER TABLE `au_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `au_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `au_talk`
--

DROP TABLE IF EXISTS `au_talk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_talk` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `user_uid` bigint DEFAULT NULL COMMENT '发布此说说的用户uid',
  `is_show_comment` tinyint NOT NULL DEFAULT '1' COMMENT '此说说是否显示评论 1： 显示 0： 不显示',
  `comment_uids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此说说的评论uid集合',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '此说说发布时间',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此说说的内容',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此说说标题',
  `is_show` tinyint NOT NULL DEFAULT '1' COMMENT '1： 显示说说，0： 不显示说说',
  `like_number` int NOT NULL DEFAULT '0' COMMENT '此说说的点赞数',
  `picture_src_list` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此说说对应的图片src集合',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '1: 已删除',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `user_uid_index` (`user_uid`) USING BTREE,
  KEY `createTime_index` (`create_time`) USING BTREE,
  KEY `union_talk_index` (`uid`,`user_uid`,`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_talk`
--

LOCK TABLES `au_talk` WRITE;
/*!40000 ALTER TABLE `au_talk` DISABLE KEYS */;
/*!40000 ALTER TABLE `au_talk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `undo_log`
--

DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `undo_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1176 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `undo_log`
--

LOCK TABLES `undo_log` WRITE;
/*!40000 ALTER TABLE `undo_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `undo_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `pplax_auth_server`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `pplax_auth_server` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `pplax_auth_server`;

--
-- Table structure for table `au_login_info`
--

DROP TABLE IF EXISTS `au_login_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_login_info` (
  `uid` bigint NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录的用户名',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录地',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录ip地址',
  `operation_system_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录的操作系统',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `status` tinyint NOT NULL COMMENT '登录的状态 1：登录成功',
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录消息，记录登录异常等信息',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `createTime_index` (`create_time`) USING BTREE,
  KEY `username_index` (`username`) USING BTREE,
  KEY `union_login_index` (`uid`,`username`,`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_login_info`
--

LOCK TABLES `au_login_info` WRITE;
/*!40000 ALTER TABLE `au_login_info` DISABLE KEYS */;
INSERT INTO `au_login_info` (`uid`, `username`, `login_location`, `login_ip`, `operation_system_info`, `create_time`, `update_time`, `status`, `message`) VALUES (1634902515327836160,'demopplax','中国云南省保山市','106.58.204.208','Windows 10;Chrome 10 version: 108.0.0.0','2023-03-12 21:01:49',NULL,1,NULL),(1634902640246792192,'xcyeye','中国云南省保山市','106.58.204.208','Windows 10;Chrome 10 version: 108.0.0.0','2023-03-12 21:02:19',NULL,1,NULL),(1634902767254511616,'demopplax','中国云南省保山市','106.58.204.208','Windows 10;Chrome 10 version: 108.0.0.0','2023-03-12 21:02:49',NULL,1,NULL),(1634903071278637056,'demopplax','中国云南省保山市','106.58.204.208','Windows 10;Chrome 10 version: 108.0.0.0','2023-03-12 21:04:01',NULL,1,NULL),(1634903363193806848,'demopplax','中国云南省保山市','106.58.204.208','Windows 10;Chrome 10 version: 108.0.0.0','2023-03-12 21:05:11',NULL,1,NULL);
/*!40000 ALTER TABLE `au_login_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_client_details`
--

DROP TABLE IF EXISTS `oauth_client_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(48) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '客户端id',
  `resource_ids` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '资源的id，多个用逗号分隔',
  `client_secret` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '客户端的秘钥',
  `scope` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '客户端的权限，多个用逗号分隔',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '授权类型，五种，多个用逗号分隔',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '授权码模式的跳转uri',
  `authorities` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '权限，多个用逗号分隔',
  `access_token_validity` int DEFAULT NULL COMMENT 'access_token的过期时间，单位毫秒，覆盖掉硬编码',
  `refresh_token_validity` int DEFAULT NULL COMMENT 'refresh_token的过期时间，单位毫秒，覆盖掉硬编码',
  `additional_information` varchar(4096) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '扩展字段，JSON',
  `autoapprove` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '默认false，是否自动授权',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_client_details`
--

LOCK TABLES `oauth_client_details` WRITE;
/*!40000 ALTER TABLE `oauth_client_details` DISABLE KEYS */;
INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`, `create_time`, `update_time`) VALUES ('demopplax',NULL,'$2a$10$p8IfLT13u1sN8xt3/UKikO89mAg904PZ5XHEs2m42oyy/TNrHytpG','all','authorization_code,client_credentials,refresh_token,password',NULL,NULL,NULL,NULL,NULL,NULL,'2023-03-12 21:01:48',NULL),('xcyeye',NULL,'$2a$10$/PQyuzJHQaTRQnc8pG0XU.d7oEtwdg/cHDn36WfRUIp6m/rOfeECC','all','authorization_code,client_credentials,refresh_token,password',NULL,NULL,NULL,NULL,NULL,NULL,'2023-03-12 19:20:52',NULL);


/*!40000 ALTER TABLE `oauth_client_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_code`
--

DROP TABLE IF EXISTS `oauth_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth_code` (
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `authentication` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_code`
--

LOCK TABLES `oauth_code` WRITE;
/*!40000 ALTER TABLE `oauth_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `pplax_comment`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `pplax_comment` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `pplax_comment`;

--
-- Table structure for table `au_comment`
--

DROP TABLE IF EXISTS `au_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_comment` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `username` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'pplax_小可爱' COMMENT '此评论的用户名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此评论这的头像uid',
  `site` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'https://pplax.xcye.xyz' COMMENT '此评论者的博客地址',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此评论这的邮箱地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '此评论的创时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '此评论最后修改时间',
  `comment_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评论者的ip地址',
  `operation_system_info` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评论者的浏览器版本',
  `is_show_comment` tinyint NOT NULL DEFAULT '1' COMMENT '是否显示此评论 1： 显示 0： 不显示',
  `reply_comment_uid` bigint DEFAULT NULL COMMENT '此评论是回复哪个评论的',
  `is_email_notice` tinyint NOT NULL DEFAULT '0' COMMENT '如果此评论是回复某条评论，则1：已通知回复的那条评论的邮箱，0：未发送邮箱通知',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '在哪个地址发布评论',
  `next_comment_uid_array` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '此评论的所有下一条集合',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
  `user_uid` bigint NOT NULL COMMENT '此评论是属于哪个用户的',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '1：删除 0：未删除',
  `page_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此评论是在哪种页面发布的',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `unique_username` (`username`) USING BTREE,
  KEY `createTime_index` (`create_time`) USING BTREE,
  KEY `replyCommentUid` (`reply_comment_uid`) USING BTREE,
  KEY `userUid_index` (`user_uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_comment`
--

LOCK TABLES `au_comment` WRITE;
/*!40000 ALTER TABLE `au_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `au_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `undo_log`
--

DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `undo_log` (
  `branch_id` bigint NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='AT transaction mode undo table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `undo_log`
--

LOCK TABLES `undo_log` WRITE;
/*!40000 ALTER TABLE `undo_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `undo_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `pplax_email`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `pplax_email` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `pplax_email`;

--
-- Table structure for table `au_email`
--

DROP TABLE IF EXISTS `au_email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_email` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `user_uid` bigint NOT NULL COMMENT '此条记录和用户表中的某个用户对应',
  `email_host` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发送者邮件的主机',
  `email_password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发送者邮件的密码，或者授权码',
  `protocol` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发送者的协议',
  `email` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱号',
  `port` int DEFAULT NULL COMMENT '此邮件发送的端口',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `userUid_index` (`user_uid`) USING BTREE,
  KEY `createTime_index` (`create_time`) USING BTREE,
  KEY `union_email_index` (`uid`,`user_uid`,`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_email`
--

LOCK TABLES `au_email` WRITE;
/*!40000 ALTER TABLE `au_email` DISABLE KEYS */;
/*!40000 ALTER TABLE `au_email` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `au_email_log`
--

DROP TABLE IF EXISTS `au_email_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_email_log` (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid',
  `user_uid` bigint NOT NULL,
  `sender` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发送者的邮箱号',
  `subject` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `receiver` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接受者的邮箱号',
  `is_send` tinyint NOT NULL COMMENT '1:发送成功0：没有发送成功',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `createTime_index` (`create_time`) USING BTREE,
  KEY `sendEmail_index` (`sender`) USING BTREE,
  KEY `union_emailLog_index` (`uid`,`sender`,`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1569 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_email_log`
--

LOCK TABLES `au_email_log` WRITE;
/*!40000 ALTER TABLE `au_email_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `au_email_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `au_mail_template`
--

DROP TABLE IF EXISTS `au_mail_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_mail_template` (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一uid',
  `template` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮件发送模板的html',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `subject` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮件默认发送标题，如果没有传入的话',
  `user_uid` bigint NOT NULL COMMENT '此模板是哪个用户创建的',
  `type_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此模板是回复评论，还是收到评论等',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1016 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_mail_template`
--

LOCK TABLES `au_mail_template` WRITE;
/*!40000 ALTER TABLE `au_mail_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `au_mail_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `au_message_log`
--

DROP TABLE IF EXISTS `au_message_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_message_log` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '投递的消息',
  `exchange` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '交换机名称',
  `queue` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '队列名称',
  `routing_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '绑定路由key',
  `try_count` int NOT NULL COMMENT '重试次数',
  `consume_status` tinyint NOT NULL COMMENT '1: 表示消费成功 0：表示消费失败',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `exchange_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '交换机类型',
  `ack_status` tinyint NOT NULL COMMENT '确认状态 1：应答了',
  `error_message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '如果发生错误，则错误消息是什么',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `createTIme_index` (`create_time`) USING BTREE,
  KEY `routingKey_index` (`routing_key`) USING BTREE,
  KEY `union_message_index` (`uid`,`routing_key`,`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_message_log`
--

LOCK TABLES `au_message_log` WRITE;
/*!40000 ALTER TABLE `au_message_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `au_message_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `undo_log`
--

DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `undo_log` (
  `branch_id` bigint NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='AT transaction mode undo table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `undo_log`
--

LOCK TABLES `undo_log` WRITE;
/*!40000 ALTER TABLE `undo_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `undo_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `pplax_file`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `pplax_file` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `pplax_file`;

--
-- Table structure for table `au_file`
--

DROP TABLE IF EXISTS `au_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `au_file` (
  `uid` bigint NOT NULL COMMENT '唯一uid',
  `user_uid` bigint DEFAULT NULL COMMENT '用户uid',
  `file_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此文件的名称',
  `size` bigint NOT NULL COMMENT '此文件的大小 字节为单位',
  `summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '此文件的简介',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '此文件的存放路径，如果是对象存储，则表示objectName',
  `storage_mode` int NOT NULL COMMENT '文件存储的模式',
  `storage_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '存储的路径',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '1.: 已经删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传此文件的时间',
  `delete_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后删除时间',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `createTime_index` (`create_time`) USING BTREE,
  KEY `userUid_index` (`user_uid`) USING BTREE,
  KEY `fileName_index` (`file_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `au_file`
--

LOCK TABLES `au_file` WRITE;
/*!40000 ALTER TABLE `au_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `au_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `undo_log`
--

DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `undo_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `undo_log`
--

LOCK TABLES `undo_log` WRITE;
/*!40000 ALTER TABLE `undo_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `undo_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `seata`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `seata` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `seata`;

--
-- Table structure for table `branch_table`
--

DROP TABLE IF EXISTS `branch_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch_table` (
  `branch_id` bigint NOT NULL,
  `xid` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `transaction_id` bigint DEFAULT NULL,
  `resource_group_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `resource_id` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `branch_type` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `client_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `application_data` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `gmt_create` datetime(6) DEFAULT NULL,
  `gmt_modified` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`branch_id`) USING BTREE,
  KEY `idx_xid` (`xid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch_table`
--

LOCK TABLES `branch_table` WRITE;
/*!40000 ALTER TABLE `branch_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `branch_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `global_table`
--

DROP TABLE IF EXISTS `global_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `global_table` (
  `xid` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `transaction_id` bigint DEFAULT NULL,
  `status` tinyint NOT NULL,
  `application_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `transaction_service_group` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `transaction_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `timeout` int DEFAULT NULL,
  `begin_time` bigint DEFAULT NULL,
  `application_data` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`xid`) USING BTREE,
  KEY `idx_gmt_modified_status` (`gmt_modified`,`status`) USING BTREE,
  KEY `idx_transaction_id` (`transaction_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `global_table`
--

LOCK TABLES `global_table` WRITE;
/*!40000 ALTER TABLE `global_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `global_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lock_table`
--

DROP TABLE IF EXISTS `lock_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lock_table` (
  `row_key` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `xid` varchar(96) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `transaction_id` bigint DEFAULT NULL,
  `branch_id` bigint NOT NULL,
  `resource_id` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `table_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `pk` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`row_key`) USING BTREE,
  KEY `idx_branch_id` (`branch_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lock_table`
--

LOCK TABLES `lock_table` WRITE;
/*!40000 ALTER TABLE `lock_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `lock_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-02 11:22:46
