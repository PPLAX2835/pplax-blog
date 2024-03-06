-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: nacos_config
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
-- Table structure for table `config_info`
--

DROP TABLE IF EXISTS `config_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin,
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='config_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info`
--

LOCK TABLES `config_info` WRITE;
/*!40000 ALTER TABLE `config_info` DISABLE KEYS */;
INSERT INTO `config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (6,'pplax-config.yaml','PPLAX_BLOG','pplax:\n  name: I_like_the_world','8a7115b0519b88583e8ae1b49c9d2f63','2024-01-23 14:55:13','2024-02-02 14:04:55','nacos','192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','自定义配置','','','yaml','',''),(9,'database-config.yaml','PPLAX_BLOG','spring:\n  # DATABASE CONFIG\n  datasource:\n    username: root\n    password: root\n    url: jdbc:mysql://localhost:3306/pplax_blog?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&useSSL=false\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.alibaba.druid.pool.DruidDataSource\n\n    initialSize: 5\n    minIdle: 5\n    maxActive: 20\n    maxWait: 60000\n    timeBetweenEvictionRunsMillis: 60000\n    minEvictableIdleTimeMillis: 300000\n    testWhileIdle: true\n    testOnBorrow: false\n    testOnReturn: false\n    poolPreparedStatements: true\n\n    #配置监控统计拦截的filters，去掉后监控界面sql无法统计，\'wall\'用于防火墙\n    filters: stat,wall,log4j\n    maxPoolPreparedStatementPerConnectionSize: 20\n    useGlobalDataSourceStat: true\n    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500','eb03a12b9f8b9d65d324b4afd90cdc69','2024-01-23 15:08:39','2024-03-06 20:15:07','nacos','192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','数据库的统一配置','','','yaml','',''),(11,'mybatis-plus-config.yaml','PPLAX_BLOG','mybatis-plus:\r\n  mapper-locations: classpath:/mapper/*Mapper.xml\r\n  #实体扫描，多个package用逗号或者分号分隔\r\n  typeAliasesPackage: xyz.pplax.pplaxblog.xo.entity\r\n  global-config:\r\n    # 数据库相关配置\r\n    db-config:\r\n      #主键类型  0:\"数据库ID自增\", 1:\"用户输入ID\",2:\"全局唯一ID (数字类型唯一ID)\", 3:\"全局唯一ID UUID\";\r\n      id-type: UUID\r\n      #字段策略 IGNORED:\"忽略判断\",NOT_NULL:\"非 NULL 判断\"),NOT_EMPTY:\"非空判断\"\r\n      field-strategy: NOT_EMPTY\r\n      #驼峰下划线转换\r\n      column-underline: true\r\n      #数据库大写下划线转换\r\n      #capital-mode: true\r\n      #逻辑删除配置\r\n      logic-delete-value: 0\r\n      logic-not-delete-value: 1\r\n      db-type: mysql\r\n    #刷新mapper 调试神器\r\n    refresh: true','1411a3d545ca66c6c853b92c88f17767','2024-01-23 15:25:12','2024-01-23 15:25:12',NULL,'192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','mybatis-plus的配置',NULL,NULL,'yaml',NULL,''),(15,'redis-config.yaml','PPLAX_BLOG','\nspring:\n  redis:\n    database: 2\n    host: 127.0.0.1\n    port: 6379\n    password: 123456\n    # 默认过期时间\n    expire: 600','3ff163876dc94765ced1f9c1cb12cead','2024-01-24 20:11:04','2024-02-18 10:35:12','nacos','192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','redis的相关配置','','','yaml','',''),(19,'gateway-config.yaml','PPLAX_BLOG','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n        - id: file-route\n          uri: lb://file-server\n          predicates:\n            - Path=/api/file/**\n          filters:\n            - StripPrefix=2\n\npplax:\n  gateway:\n    exclude:   # 白名单配置\n      - /api/admin/auth/token\n      - /api/admin/auth/captcha','acba51636b54b774574fd75fa911a23d','2024-01-29 22:10:38','2024-02-18 12:03:50','nacos','192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','','','','yaml','',''),(43,'oauth-config.yaml','PPLAX_BLOG','pplax:\r\n  oauth: \r\n    secret-key: PPLAX_RESISTS_FATE\r\n    client-id: pplax\r\n    client-secret: pplax123456','a63fe137bce0794397ad12a97cfa8d51','2024-02-02 12:42:48','2024-02-02 12:42:48',NULL,'192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','oauth的相关配置',NULL,NULL,'yaml',NULL,''),(45,'storage-config.yaml','PPLAX_BLOG','pplax:\n  storage:\n    mode: minio       # 当前采用的存储模式，如minio、localStorage\n    minio:\n      endpoint: http://127.0.0.1:9002 #Minio服务所在地址\n      accessKey: root #访问的key\n      secretKey: password #访问的秘钥\n      bucketName: pplax-blog  #存储桶名称','55c981d3c78c0aca2afe5540df0295fa','2024-02-02 12:45:44','2024-02-07 13:42:44','nacos','192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','文件存储相关配置','','','yaml','','');
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
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='增加租户字段';
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
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='config_info_beta';
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
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='config_info_tag';
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
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`),
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='config_tag_relation';
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
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='集群、各Group容量信息表';
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
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin,
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`nid`),
  KEY `idx_gmt_create` (`gmt_create`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_did` (`data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='多租户改造';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `his_config_info`
--

LOCK TABLES `his_config_info` WRITE;
/*!40000 ALTER TABLE `his_config_info` DISABLE KEYS */;
INSERT INTO `his_config_info` (`id`, `nid`, `data_id`, `group_id`, `app_name`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `op_type`, `tenant_id`, `encrypted_data_key`) VALUES (19,82,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n\npplax:\n  gateway:\n    exclude:   # 白名单配置\n      - /api/admin/oauth/token','cca5634af44609490e952087928267d5','2024-02-06 15:16:07','2024-02-06 15:16:07','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(45,83,'storage-config.yaml','PPLAX_BLOG','','pplax:\r\n  storage:\r\n    path:\r\n      root: /pplax-blog     # 存储的根目录\r\n    minio:\r\n      endpoint: http://127.0.0.1:9002 #Minio服务所在地址\r\n      accessKey: root #访问的key\r\n      secretKey: password #访问的秘钥\r\n      bucketName: pplax-blog  #存储桶名称','4614bc2fd6ebc9d43478e4bae3330fe5','2024-02-07 13:37:03','2024-02-07 13:37:03','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(45,84,'storage-config.yaml','PPLAX_BLOG','','pplax:\n  storage:\n    minio:\n      endpoint: http://127.0.0.1:9002 #Minio服务所在地址\n      accessKey: root #访问的key\n      secretKey: password #访问的秘钥\n      bucketName: pplax-blog  #存储桶名称','19ae8f9307a37f3dbcbba82951e96ce8','2024-02-07 13:42:44','2024-02-07 13:42:44','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,85,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n        - id: file-route\n          uri: lb://file-server\n          predicates:\n            - Path=/api/file/**\n          filters:\n            - StripPrefix=2\n\npplax:\n  gateway:\n    exclude:   # 白名单配置\n      - /api/admin/oauth/token','7babdb88c4f86da124d66d1d57b3f1da','2024-02-09 12:22:53','2024-02-09 12:22:54','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,86,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n        - id: file-route\n          uri: lb://file-server\n          predicates:\n            - Path=/api/file/**\n          filters:\n            - StripPrefix=2\n\npplax:\n  gateway:\n    exclude:   # 白名单配置\n      - /api/admin/oauth/token\n      - /gw/test','69fdc94dca7103bdd015953f78b5e370','2024-02-09 12:25:08','2024-02-09 12:25:09','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(0,87,'log4j-config.properties','PPLAX_BLOG','','log4j.rootLogger=debug, stdout, R\r\n\r\nlog4j.appender.stdout=org.apache.log4j.ConsoleAppender\r\nlog4j.appender.stdout.layout=org.apache.log4j.PatternLayout\r\n\r\n# Pattern to output the caller\'s file name and line number.\r\nlog4j.appender.stdout.layout.ConversionPattern=%5p [%t] (%F:%L) - %m%n\r\n\r\nlog4j.appender.R=org.apache.log4j.RollingFileAppender\r\nlog4j.appender.R.File=example.log\r\n\r\nlog4j.appender.R.MaxFileSize=100KB\r\n# Keep one backup file\r\nlog4j.appender.R.MaxBackupIndex=5\r\n\r\nlog4j.appender.R.layout=org.apache.log4j.PatternLayout\r\nlog4j.appender.R.layout.ConversionPattern=%p %t %c - %m%n\r\n\r\n','0f332c0d31ce87976ad7a1a142cab88f','2024-02-17 18:10:04','2024-02-17 18:10:04',NULL,'192.168.48.1','I','712185cc-1239-4192-9cb1-c71d5b242f91',''),(79,88,'log4j-config.properties','PPLAX_BLOG','','log4j.rootLogger=debug, stdout, R\r\n\r\nlog4j.appender.stdout=org.apache.log4j.ConsoleAppender\r\nlog4j.appender.stdout.layout=org.apache.log4j.PatternLayout\r\n\r\n# Pattern to output the caller\'s file name and line number.\r\nlog4j.appender.stdout.layout.ConversionPattern=%5p [%t] (%F:%L) - %m%n\r\n\r\nlog4j.appender.R=org.apache.log4j.RollingFileAppender\r\nlog4j.appender.R.File=example.log\r\n\r\nlog4j.appender.R.MaxFileSize=100KB\r\n# Keep one backup file\r\nlog4j.appender.R.MaxBackupIndex=5\r\n\r\nlog4j.appender.R.layout=org.apache.log4j.PatternLayout\r\nlog4j.appender.R.layout.ConversionPattern=%p %t %c - %m%n\r\n\r\n','0f332c0d31ce87976ad7a1a142cab88f','2024-02-17 18:12:31','2024-02-17 18:12:32',NULL,'192.168.48.1','D','712185cc-1239-4192-9cb1-c71d5b242f91',''),(0,89,'log4j.properties','PPLAX_GROUP','','log4j.rootLogger=debug, stdout, R\r\n\r\nlog4j.appender.stdout=org.apache.log4j.ConsoleAppender\r\nlog4j.appender.stdout.layout=org.apache.log4j.PatternLayout\r\n\r\n# Pattern to output the caller\'s file name and line number.\r\nlog4j.appender.stdout.layout.ConversionPattern=%5p [%t] (%F:%L) - %m%n\r\n\r\nlog4j.appender.R=org.apache.log4j.RollingFileAppender\r\nlog4j.appender.R.File=example.log\r\n\r\nlog4j.appender.R.MaxFileSize=100KB\r\n# Keep one backup file\r\nlog4j.appender.R.MaxBackupIndex=5\r\n\r\nlog4j.appender.R.layout=org.apache.log4j.PatternLayout\r\nlog4j.appender.R.layout.ConversionPattern=%p %t %c - %m%n\r\n\r\n','0f332c0d31ce87976ad7a1a142cab88f','2024-02-17 18:13:07','2024-02-17 18:13:07',NULL,'192.168.48.1','I','712185cc-1239-4192-9cb1-c71d5b242f91',''),(80,90,'log4j.properties','PPLAX_GROUP','','log4j.rootLogger=debug, stdout, R\r\n\r\nlog4j.appender.stdout=org.apache.log4j.ConsoleAppender\r\nlog4j.appender.stdout.layout=org.apache.log4j.PatternLayout\r\n\r\n# Pattern to output the caller\'s file name and line number.\r\nlog4j.appender.stdout.layout.ConversionPattern=%5p [%t] (%F:%L) - %m%n\r\n\r\nlog4j.appender.R=org.apache.log4j.RollingFileAppender\r\nlog4j.appender.R.File=example.log\r\n\r\nlog4j.appender.R.MaxFileSize=100KB\r\n# Keep one backup file\r\nlog4j.appender.R.MaxBackupIndex=5\r\n\r\nlog4j.appender.R.layout=org.apache.log4j.PatternLayout\r\nlog4j.appender.R.layout.ConversionPattern=%p %t %c - %m%n\r\n\r\n','0f332c0d31ce87976ad7a1a142cab88f','2024-02-17 18:14:29','2024-02-17 18:14:30',NULL,'192.168.48.1','D','712185cc-1239-4192-9cb1-c71d5b242f91',''),(15,91,'redis-config.yaml','PPLAX_BLOG','','\r\nspring:\r\n  redis:\r\n    database: 2\r\n    host: 127.0.0.1\r\n    port: 6379\r\n    password: 123456','20b1ec7924d9b7d9e2a88088e0f2625e','2024-02-18 09:27:56','2024-02-18 09:27:56','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(15,92,'redis-config.yaml','PPLAX_BLOG','','\nspring:\n  redis:\n    database: 2\n    host: 127.0.0.1\n    port: 6379\n    password: 123456\n    # 默认过期时间\n    expire: 10','d672ec7092989ebf19eabc18f24b6830','2024-02-18 10:30:06','2024-02-18 10:30:06','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(15,93,'redis-config.yaml','PPLAX_BLOG','','\nspring:\n  redis:\n    database: 2\n    host: 127.0.0.1\n    port: 6379\n    password: 123456\n    # 默认过期时间\n    expire: 60','0fd9df85c6cb986265ec395e1066736e','2024-02-18 10:35:12','2024-02-18 10:35:12','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,94,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n        - id: file-route\n          uri: lb://file-server\n          predicates:\n            - Path=/api/file/**\n          filters:\n            - StripPrefix=2\n\npplax:\n  gateway:\n    exclude:   # 白名单配置\n      - /api/admin/oauth/token','7babdb88c4f86da124d66d1d57b3f1da','2024-02-18 11:26:05','2024-02-18 11:26:06','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,95,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n        - id: file-route\n          uri: lb://file-server\n          predicates:\n            - Path=/api/file/**\n          filters:\n            - StripPrefix=2\n\npplax:\n  gateway:\n    exclude:   # 白名单配置\n      - /api/admin/auth/token','4dacbac42e61a0ba16753b705d9a9bee','2024-02-18 11:37:01','2024-02-18 11:37:01','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,96,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n        - id: file-route\n          uri: lb://file-server\n          predicates:\n            - Path=/api/file/**\n          filters:\n            - StripPrefix=2\n\npplax:\n  gateway:\n    exclude:   # 白名单配置\n      - /api/admin/auth/token\n      - /api/admin/auth/captcha','acba51636b54b774574fd75fa911a23d','2024-02-18 12:03:49','2024-02-18 12:03:49','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,97,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n        - id: file-route\n          uri: lb://file-server\n          predicates:\n            - Path=/api/file/**\n          filters:\n            - StripPrefix=2\n\npplax:\n  gateway:\n    exclude:   # 白名单配置\n      - /api/admin/auth/token\n      - /api/admin/auth/captcha','acba51636b54b774574fd75fa911a23d','2024-02-18 12:03:49','2024-02-18 12:03:50','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(9,98,'database-config.yaml','PPLAX_BLOG','','spring:\n  # DATABASE CONFIG\n  datasource:\n    username: root\n    password: root\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.alibaba.druid.pool.DruidDataSource\n\n    initialSize: 5\n    minIdle: 5\n    maxActive: 20\n    maxWait: 60000\n    timeBetweenEvictionRunsMillis: 60000\n    minEvictableIdleTimeMillis: 300000\n    testWhileIdle: true\n    testOnBorrow: false\n    testOnReturn: false\n    poolPreparedStatements: true\n\n    #配置监控统计拦截的filters，去掉后监控界面sql无法统计，\'wall\'用于防火墙\n    filters: stat,wall,log4j\n    maxPoolPreparedStatementPerConnectionSize: 20\n    useGlobalDataSourceStat: true\n    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500','cd0ed67a594d5c7b5142ffb56e22e7da','2024-03-06 20:15:07','2024-03-06 20:15:07','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91','');
/*!40000 ALTER TABLE `his_config_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `resource` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE KEY `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE KEY `idx_user_role` (`username`,`role`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='租户容量信息表';
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
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='tenant_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenant_info`
--

LOCK TABLES `tenant_info` WRITE;
/*!40000 ALTER TABLE `tenant_info` DISABLE KEYS */;
INSERT INTO `tenant_info` (`id`, `kp`, `tenant_id`, `tenant_name`, `tenant_desc`, `create_source`, `gmt_create`, `gmt_modified`) VALUES (1,'1','712185cc-1239-4192-9cb1-c71d5b242f91','pplax-blog','pplax-blog的命名空间','nacos',1705984731669,1705984731669);
/*!40000 ALTER TABLE `tenant_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`username`, `password`, `enabled`) VALUES ('nacos','$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-06 20:23:18
