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
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='config_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info`
--

LOCK TABLES `config_info` WRITE;
/*!40000 ALTER TABLE `config_info` DISABLE KEYS */;
INSERT INTO `config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (6,'pplax-config.yaml','PPLAX_BLOG','pplax:\n  name: I_like_the_world','8a7115b0519b88583e8ae1b49c9d2f63','2024-01-23 14:55:13','2024-02-02 14:04:55','nacos','192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','自定义配置','','','yaml','',''),(9,'database-config.yaml','PPLAX_BLOG','spring:\n  # DATABASE CONFIG\n  datasource:\n    username: root\n    password: root\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.alibaba.druid.pool.DruidDataSource\n\n    initialSize: 5\n    minIdle: 5\n    maxActive: 20\n    maxWait: 60000\n    timeBetweenEvictionRunsMillis: 60000\n    minEvictableIdleTimeMillis: 300000\n    testWhileIdle: true\n    testOnBorrow: false\n    testOnReturn: false\n    poolPreparedStatements: true\n\n    #配置监控统计拦截的filters，去掉后监控界面sql无法统计，\'wall\'用于防火墙\n    filters: stat,wall,log4j\n    maxPoolPreparedStatementPerConnectionSize: 20\n    useGlobalDataSourceStat: true\n    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500','cd0ed67a594d5c7b5142ffb56e22e7da','2024-01-23 15:08:39','2024-01-23 15:10:30','nacos','192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','数据库的统一配置','','','yaml','',''),(11,'mybatis-plus-config.yaml','PPLAX_BLOG','mybatis-plus:\r\n  mapper-locations: classpath:/mapper/*Mapper.xml\r\n  #实体扫描，多个package用逗号或者分号分隔\r\n  typeAliasesPackage: xyz.pplax.pplaxblog.xo.entity\r\n  global-config:\r\n    # 数据库相关配置\r\n    db-config:\r\n      #主键类型  0:\"数据库ID自增\", 1:\"用户输入ID\",2:\"全局唯一ID (数字类型唯一ID)\", 3:\"全局唯一ID UUID\";\r\n      id-type: UUID\r\n      #字段策略 IGNORED:\"忽略判断\",NOT_NULL:\"非 NULL 判断\"),NOT_EMPTY:\"非空判断\"\r\n      field-strategy: NOT_EMPTY\r\n      #驼峰下划线转换\r\n      column-underline: true\r\n      #数据库大写下划线转换\r\n      #capital-mode: true\r\n      #逻辑删除配置\r\n      logic-delete-value: 0\r\n      logic-not-delete-value: 1\r\n      db-type: mysql\r\n    #刷新mapper 调试神器\r\n    refresh: true','1411a3d545ca66c6c853b92c88f17767','2024-01-23 15:25:12','2024-01-23 15:25:12',NULL,'192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','mybatis-plus的配置',NULL,NULL,'yaml',NULL,''),(15,'redis-config.yaml','PPLAX_BLOG','\r\nspring:\r\n  redis:\r\n    database: 2\r\n    host: 127.0.0.1\r\n    port: 6379\r\n    password: 123456','20b1ec7924d9b7d9e2a88088e0f2625e','2024-01-24 20:11:04','2024-01-24 20:11:04',NULL,'192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','redis的相关配置',NULL,NULL,'yaml',NULL,''),(19,'gateway-config.yaml','PPLAX_BLOG','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n\npplax:\n  gateway:\n    exclude:   # 白名单配置\n      - /**/oauth/token                   # 获取token\n      - /**/swagger-ui.html               # swagger','082a260ff71c5a82bb9db41880389dab','2024-01-29 22:10:38','2024-02-02 14:32:08','nacos','192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','','','','yaml','',''),(43,'oauth-config.yaml','PPLAX_BLOG','pplax:\r\n  oauth: \r\n    secret-key: PPLAX_RESISTS_FATE\r\n    client-id: pplax\r\n    client-secret: pplax123456','a63fe137bce0794397ad12a97cfa8d51','2024-02-02 12:42:48','2024-02-02 12:42:48',NULL,'192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','oauth的相关配置',NULL,NULL,'yaml',NULL,''),(45,'storage-config.yaml','PPLAX_BLOG','pplax:\r\n  storage:\r\n    path:\r\n      root: /pplax-blog     # 存储的根目录\r\n    minio:\r\n      endpoint: http://127.0.0.1:9002 #Minio服务所在地址\r\n      accessKey: root #访问的key\r\n      secretKey: password #访问的秘钥\r\n      bucketName: pplax-blog  #存储桶名称','4614bc2fd6ebc9d43478e4bae3330fe5','2024-02-02 12:45:44','2024-02-02 12:45:44',NULL,'192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','文件存储相关配置',NULL,NULL,'yaml',NULL,'');
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
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='多租户改造';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `his_config_info`
--

LOCK TABLES `his_config_info` WRITE;
/*!40000 ALTER TABLE `his_config_info` DISABLE KEYS */;
INSERT INTO `his_config_info` (`id`, `nid`, `data_id`, `group_id`, `app_name`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `op_type`, `tenant_id`, `encrypted_data_key`) VALUES (0,1,'pplax-config','DEFAULT_GROUP','','pplax:\r\n  api:\r\n    base-path: /api   # 统一父级请求路径','fea765ef95324d22536718ac855e00d4','2024-01-23 12:53:50','2024-01-23 12:53:50','nacos','192.168.48.1','I','712185cc-1239-4192-9cb1-c71d5b242f91',''),(1,2,'pplax-config','DEFAULT_GROUP','','pplax:\r\n  api:\r\n    base-path: /api   # 统一父级请求路径','fea765ef95324d22536718ac855e00d4','2024-01-23 12:54:10','2024-01-23 12:54:10','nacos','192.168.48.1','D','712185cc-1239-4192-9cb1-c71d5b242f91',''),(0,3,'pplax-config','PPLAX_BLOG','','pplax:\r\n  api:\r\n    base-path: /api   # 统一父级请求路径','fea765ef95324d22536718ac855e00d4','2024-01-23 12:54:40','2024-01-23 12:54:41','nacos','192.168.48.1','I','712185cc-1239-4192-9cb1-c71d5b242f91',''),(0,4,'datasource-config','PPLAX_BLOG','','spring:\r\n  # DATABASE CONFIG\r\n  datasource:\r\n    username: root\r\n    password: root\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n\r\n    initialSize: 5\r\n    minIdle: 5\r\n    maxActive: 20\r\n    maxWait: 60000\r\n    timeBetweenEvictionRunsMillis: 60000\r\n    minEvictableIdleTimeMillis: 300000\r\n    testWhileIdle: true\r\n    testOnBorrow: false\r\n    testOnReturn: false\r\n    poolPreparedStatements: true\r\n\r\n    #配置监控统计拦截的filters，去掉后监控界面sql无法统计，\'wall\'用于防火墙\r\n    filters: stat,wall,log4j\r\n    maxPoolPreparedStatementPerConnectionSize: 20\r\n    useGlobalDataSourceStat: true\r\n    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500','4de68dab5e261b5cb0f40eab76884b0e','2024-01-23 13:25:47','2024-01-23 13:25:48','nacos','192.168.48.1','I','712185cc-1239-4192-9cb1-c71d5b242f91',''),(3,5,'datasource-config','PPLAX_BLOG','','spring:\r\n  # DATABASE CONFIG\r\n  datasource:\r\n    username: root\r\n    password: root\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n\r\n    initialSize: 5\r\n    minIdle: 5\r\n    maxActive: 20\r\n    maxWait: 60000\r\n    timeBetweenEvictionRunsMillis: 60000\r\n    minEvictableIdleTimeMillis: 300000\r\n    testWhileIdle: true\r\n    testOnBorrow: false\r\n    testOnReturn: false\r\n    poolPreparedStatements: true\r\n\r\n    #配置监控统计拦截的filters，去掉后监控界面sql无法统计，\'wall\'用于防火墙\r\n    filters: stat,wall,log4j\r\n    maxPoolPreparedStatementPerConnectionSize: 20\r\n    useGlobalDataSourceStat: true\r\n    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500','4de68dab5e261b5cb0f40eab76884b0e','2024-01-23 13:26:16','2024-01-23 13:26:16','nacos','192.168.48.1','D','712185cc-1239-4192-9cb1-c71d5b242f91',''),(0,6,'database-config','PPLAX_BLOG','','spring:\r\n  # DATABASE CONFIG\r\n  datasource:\r\n    username: root\r\n    password: root\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n\r\n    initialSize: 5\r\n    minIdle: 5\r\n    maxActive: 20\r\n    maxWait: 60000\r\n    timeBetweenEvictionRunsMillis: 60000\r\n    minEvictableIdleTimeMillis: 300000\r\n    testWhileIdle: true\r\n    testOnBorrow: false\r\n    testOnReturn: false\r\n    poolPreparedStatements: true\r\n\r\n    #配置监控统计拦截的filters，去掉后监控界面sql无法统计，\'wall\'用于防火墙\r\n    filters: stat,wall,log4j\r\n    maxPoolPreparedStatementPerConnectionSize: 20\r\n    useGlobalDataSourceStat: true\r\n    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500','4de68dab5e261b5cb0f40eab76884b0e','2024-01-23 13:26:38','2024-01-23 13:26:38','nacos','192.168.48.1','I','712185cc-1239-4192-9cb1-c71d5b242f91',''),(2,7,'pplax-config','PPLAX_BLOG','','pplax:\r\n  api:\r\n    base-path: /api   # 统一父级请求路径','fea765ef95324d22536718ac855e00d4','2024-01-23 13:44:53','2024-01-23 13:44:53','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(0,8,'pplax-config.yaml','PPLAX_BLOG','','pplax:\r\n  api:\r\n    basePath: /api   # 统一父级请求路径','75173b43f0ccda8f01ca284dd6da1682','2024-01-23 14:55:12','2024-01-23 14:55:13',NULL,'192.168.48.1','I','712185cc-1239-4192-9cb1-c71d5b242f91',''),(2,9,'pplax-config','PPLAX_BLOG','','pplax:\n  api:\n    basePath: /api   # 统一父级请求路径','d4fff8188e7e371042f19f3d71f0f4a9','2024-01-23 14:55:47','2024-01-23 14:55:47',NULL,'192.168.48.1','D','712185cc-1239-4192-9cb1-c71d5b242f91',''),(6,10,'pplax-config.yaml','PPLAX_BLOG','','pplax:\r\n  api:\r\n    basePath: /api   # 统一父级请求路径','75173b43f0ccda8f01ca284dd6da1682','2024-01-23 15:01:32','2024-01-23 15:01:33','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(0,11,'datasource-config.yaml','DEFAULT_GROUP','','spring:\r\n  # DATABASE CONFIG\r\n  datasource:\r\n    username: root\r\n    password: root\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n\r\n    initialSize: 5\r\n    minIdle: 5\r\n    maxActive: 20\r\n    maxWait: 60000\r\n    timeBetweenEvictionRunsMillis: 60000\r\n    minEvictableIdleTimeMillis: 300000\r\n    testWhileIdle: true\r\n    testOnBorrow: false\r\n    testOnReturn: false\r\n    poolPreparedStatements: true\r\n\r\n    #配置监控统计拦截的filters，去掉后监控界面sql无法统计，\'wall\'用于防火墙\r\n    filters: stat,wall,log4j\r\n    maxPoolPreparedStatementPerConnectionSize: 20\r\n    useGlobalDataSourceStat: true\r\n    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500','4de68dab5e261b5cb0f40eab76884b0e','2024-01-23 15:07:36','2024-01-23 15:07:37',NULL,'192.168.48.1','I','712185cc-1239-4192-9cb1-c71d5b242f91',''),(8,12,'datasource-config.yaml','DEFAULT_GROUP','','spring:\r\n  # DATABASE CONFIG\r\n  datasource:\r\n    username: root\r\n    password: root\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n\r\n    initialSize: 5\r\n    minIdle: 5\r\n    maxActive: 20\r\n    maxWait: 60000\r\n    timeBetweenEvictionRunsMillis: 60000\r\n    minEvictableIdleTimeMillis: 300000\r\n    testWhileIdle: true\r\n    testOnBorrow: false\r\n    testOnReturn: false\r\n    poolPreparedStatements: true\r\n\r\n    #配置监控统计拦截的filters，去掉后监控界面sql无法统计，\'wall\'用于防火墙\r\n    filters: stat,wall,log4j\r\n    maxPoolPreparedStatementPerConnectionSize: 20\r\n    useGlobalDataSourceStat: true\r\n    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500','4de68dab5e261b5cb0f40eab76884b0e','2024-01-23 15:07:55','2024-01-23 15:07:55',NULL,'192.168.48.1','D','712185cc-1239-4192-9cb1-c71d5b242f91',''),(4,13,'database-config','PPLAX_BLOG','','spring:\r\n  # DATABASE CONFIG\r\n  datasource:\r\n    username: root\r\n    password: root\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n\r\n    initialSize: 5\r\n    minIdle: 5\r\n    maxActive: 20\r\n    maxWait: 60000\r\n    timeBetweenEvictionRunsMillis: 60000\r\n    minEvictableIdleTimeMillis: 300000\r\n    testWhileIdle: true\r\n    testOnBorrow: false\r\n    testOnReturn: false\r\n    poolPreparedStatements: true\r\n\r\n    #配置监控统计拦截的filters，去掉后监控界面sql无法统计，\'wall\'用于防火墙\r\n    filters: stat,wall,log4j\r\n    maxPoolPreparedStatementPerConnectionSize: 20\r\n    useGlobalDataSourceStat: true\r\n    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500','4de68dab5e261b5cb0f40eab76884b0e','2024-01-23 15:08:01','2024-01-23 15:08:01',NULL,'192.168.48.1','D','712185cc-1239-4192-9cb1-c71d5b242f91',''),(0,14,'database-config.yaml','PPLAX_BLOG','','spring:\r\n  # DATABASE CONFIG\r\n  datasource:\r\n    username: root\r\n    password: root\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n\r\n    initialSize: 5\r\n    minIdle: 5\r\n    maxActive: 20\r\n    maxWait: 60000\r\n    timeBetweenEvictionRunsMillis: 60000\r\n    minEvictableIdleTimeMillis: 300000\r\n    testWhileIdle: true\r\n    testOnBorrow: false\r\n    testOnReturn: false\r\n    poolPreparedStatements: true\r\n\r\n    #配置监控统计拦截的filters，去掉后监控界面sql无法统计，\'wall\'用于防火墙\r\n    filters: stat,wall,log4j\r\n    maxPoolPreparedStatementPerConnectionSize: 20\r\n    useGlobalDataSourceStat: true\r\n    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500','4de68dab5e261b5cb0f40eab76884b0e','2024-01-23 15:08:38','2024-01-23 15:08:39',NULL,'192.168.48.1','I','712185cc-1239-4192-9cb1-c71d5b242f91',''),(9,15,'database-config.yaml','PPLAX_BLOG','','spring:\r\n  # DATABASE CONFIG\r\n  datasource:\r\n    username: root\r\n    password: root\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n\r\n    initialSize: 5\r\n    minIdle: 5\r\n    maxActive: 20\r\n    maxWait: 60000\r\n    timeBetweenEvictionRunsMillis: 60000\r\n    minEvictableIdleTimeMillis: 300000\r\n    testWhileIdle: true\r\n    testOnBorrow: false\r\n    testOnReturn: false\r\n    poolPreparedStatements: true\r\n\r\n    #配置监控统计拦截的filters，去掉后监控界面sql无法统计，\'wall\'用于防火墙\r\n    filters: stat,wall,log4j\r\n    maxPoolPreparedStatementPerConnectionSize: 20\r\n    useGlobalDataSourceStat: true\r\n    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500','4de68dab5e261b5cb0f40eab76884b0e','2024-01-23 15:10:30','2024-01-23 15:10:30','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(0,16,'mybatis-plus-config.yaml','PPLAX_BLOG','','mybatis-plus:\r\n  mapper-locations: classpath:/mapper/*Mapper.xml\r\n  #实体扫描，多个package用逗号或者分号分隔\r\n  typeAliasesPackage: xyz.pplax.pplaxblog.xo.entity\r\n  global-config:\r\n    # 数据库相关配置\r\n    db-config:\r\n      #主键类型  0:\"数据库ID自增\", 1:\"用户输入ID\",2:\"全局唯一ID (数字类型唯一ID)\", 3:\"全局唯一ID UUID\";\r\n      id-type: UUID\r\n      #字段策略 IGNORED:\"忽略判断\",NOT_NULL:\"非 NULL 判断\"),NOT_EMPTY:\"非空判断\"\r\n      field-strategy: NOT_EMPTY\r\n      #驼峰下划线转换\r\n      column-underline: true\r\n      #数据库大写下划线转换\r\n      #capital-mode: true\r\n      #逻辑删除配置\r\n      logic-delete-value: 0\r\n      logic-not-delete-value: 1\r\n      db-type: mysql\r\n    #刷新mapper 调试神器\r\n    refresh: true','1411a3d545ca66c6c853b92c88f17767','2024-01-23 15:25:12','2024-01-23 15:25:12',NULL,'192.168.48.1','I','712185cc-1239-4192-9cb1-c71d5b242f91',''),(6,17,'pplax-config.yaml','PPLAX_BLOG','','pplax:\n  api:\n    basePath: /api   # 统一父级请求路径','d4fff8188e7e371042f19f3d71f0f4a9','2024-01-23 15:29:21','2024-01-23 15:29:22','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(6,18,'pplax-config.yaml','PPLAX_BLOG','','pplax:\n  api:\n    basePath: /api   # 统一父级请求路径\n  sso:\n    admin:\n      client-id: admin\n      client-secret: admin123456\n      resource-id: admin-server\n      redirect-uri: http://127.0.0.1:8081/callback.html','9ec60abd6feb431618651741ca1ccf35','2024-01-23 15:29:47','2024-01-23 15:29:47','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(6,19,'pplax-config.yaml','PPLAX_BLOG','','pplax:\n  api:\n    basePath: /api   # 统一父级请求路径\n  sso:\n    admin:\n      client-id: admin\n      client-secret: admin123456\n      resource-id: admin-server\n      redirect-uri: http://127.0.0.1:8081/callback.html\n  minio:\n    endpoint: http://127.0.0.1:9002 #Minio服务所在地址\n    accessKey: root #访问的key\n    secretKey: password #访问的秘钥\n    bucketName: pplax-blog  #存储桶名称','a655c9ad60965b526019db11d627ed3d','2024-01-23 15:32:31','2024-01-23 15:32:32','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(0,20,'redis-config.yaml','PPLAX_BLOG','','\r\nspring:\r\n  redis:\r\n    database: 2\r\n    host: 127.0.0.1\r\n    port: 6379\r\n    password: 123456','20b1ec7924d9b7d9e2a88088e0f2625e','2024-01-24 20:11:04','2024-01-24 20:11:04',NULL,'192.168.48.1','I','712185cc-1239-4192-9cb1-c71d5b242f91',''),(6,21,'pplax-config.yaml','PPLAX_BLOG','','pplax:\n  api:\n    basePath: /api   # 统一父级请求路径\n  sso:\n    admin:\n      client-id: admin\n      client-secret: admin123456\n      resource-id: admin-server\n  minio:\n    endpoint: http://127.0.0.1:9002 #Minio服务所在地址\n    accessKey: root #访问的key\n    secretKey: password #访问的秘钥\n    bucketName: pplax-blog  #存储桶名称','cd56b3724facb5a02966c0171e25c5e3','2024-01-26 12:08:32','2024-01-26 12:08:32','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(6,22,'pplax-config.yaml','PPLAX_BLOG','','pplax:\n  api:\n    basePath: /api   # 统一父级请求路径\n  sso:\n    admin:\n      client-id: admin\n      client-secret: admin123456\n      resource-id: admin-server\n  storage:\n    path:\n      root: /pplax-blog     # 存储的根目录\n    minio:\n      endpoint: http://127.0.0.1:9002 #Minio服务所在地址\n      accessKey: root #访问的key\n      secretKey: password #访问的秘钥\n      bucketName: pplax-blog  #存储桶名称','974d40337cc271ba0844de3add7d05e8','2024-01-27 00:01:12','2024-01-27 00:01:13','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(6,23,'pplax-config.yaml','PPLAX_BLOG','','pplax:\n  api:\n    basePath: /api   # 统一父级请求路径\n  sso:\n    admin:\n      client-id: admin\n      client-secret: admin123456\n      resource-id: admin-server\n    file:\n      client-id: file\n      client-secret: file123456\n      resource-id: file-server\n\n  storage:\n    path:\n      root: /pplax-blog     # 存储的根目录\n    minio:\n      endpoint: http://127.0.0.1:9002 #Minio服务所在地址\n      accessKey: root #访问的key\n      secretKey: password #访问的秘钥\n      bucketName: pplax-blog  #存储桶名称','df746924fab54932413502828cacdbe0','2024-01-29 20:36:24','2024-01-29 20:36:24','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(0,24,'gateway-config.yaml','PPLAX_BLOG','','spring:\r\n  cloud:\r\n    gateway:\r\n      discovery:\r\n        locator:\r\n          enabled: true                     # 开启从注册中心动态创建路由的功能，利用服务名进行路由\r\n          lower-case-service-id: true       # 将请求路径上的服务名配置为小写\r\n      routes:\r\n        - id: admin-route                   # 唯一路由表示，没有实际作用不重复就好，建议配合服务名\r\n          uri: lb://admin-server            # 匹配后提供服务的路由地址，lb后跟提供服务的微服务的名，不要写错！！！\r\n          predicates:                       # 路由条件，Predicate 接受⼀个输⼊参数，返回⼀个布尔值结果。该接⼝包含多种默\r\n            - Path=/api/**\r\n          filters:\r\n            - StripPrefix=1               # 去除前缀 /api/admin\r\n        - id: admin-server\r\n          uri: lb://admin-server\r\n          predicates:\r\n            - Path=/api/**\r\n        - id: test-router\r\n          uri: https://www.baidu.com\r\n          predicates:\r\n            - Path=/baidu/**','fbe4ce79d791d74df7956c1f62d2d5f6','2024-01-29 22:10:37','2024-01-29 22:10:38',NULL,'192.168.48.1','I','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,25,'gateway-config.yaml','PPLAX_BLOG','','spring:\r\n  cloud:\r\n    gateway:\r\n      discovery:\r\n        locator:\r\n          enabled: true                     # 开启从注册中心动态创建路由的功能，利用服务名进行路由\r\n          lower-case-service-id: true       # 将请求路径上的服务名配置为小写\r\n      routes:\r\n        - id: admin-route                   # 唯一路由表示，没有实际作用不重复就好，建议配合服务名\r\n          uri: lb://admin-server            # 匹配后提供服务的路由地址，lb后跟提供服务的微服务的名，不要写错！！！\r\n          predicates:                       # 路由条件，Predicate 接受⼀个输⼊参数，返回⼀个布尔值结果。该接⼝包含多种默\r\n            - Path=/api/**\r\n          filters:\r\n            - StripPrefix=1               # 去除前缀 /api/admin\r\n        - id: admin-server\r\n          uri: lb://admin-server\r\n          predicates:\r\n            - Path=/api/**\r\n        - id: test-router\r\n          uri: https://www.baidu.com\r\n          predicates:\r\n            - Path=/baidu/**','fbe4ce79d791d74df7956c1f62d2d5f6','2024-01-29 22:14:25','2024-01-29 22:14:26','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,26,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true                     # 开启从注册中心动态创建路由的功能，利用服务名进行路由\n          lower-case-service-id: true       # 将请求路径上的服务名配置为小写\n      routes:\n        # - id: admin-route                   # 唯一路由表示，没有实际作用不重复就好，建议配合服务名\n        #   uri: lb://admin-server            # 匹配后提供服务的路由地址，lb后跟提供服务的微服务的名，不要写错！！！\n        #   predicates:                       # 路由条件，Predicate 接受⼀个输⼊参数，返回⼀个布尔值结果。该接⼝包含多种默\n        #     - Path=/api/**\n        #   filters:\n        #     - StripPrefix=1               # 去除前缀 /api/admin\n        # - id: admin-server\n        #   uri: lb://admin-server\n        #   predicates:\n        #     - Path=/api/**\n        - id: test-router\n          uri: https://www.baidu.com\n          predicates:\n            - Path=/**','f7606366d766a26f7850326ab94ccc3e','2024-01-29 22:14:54','2024-01-29 22:14:55','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,27,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true                     # 开启从注册中心动态创建路由的功能，利用服务名进行路由\n          lower-case-service-id: true       # 将请求路径上的服务名配置为小写\n      routes:\n        # - id: admin-route                   # 唯一路由表示，没有实际作用不重复就好，建议配合服务名\n        #   uri: lb://admin-server            # 匹配后提供服务的路由地址，lb后跟提供服务的微服务的名，不要写错！！！\n        #   predicates:                       # 路由条件，Predicate 接受⼀个输⼊参数，返回⼀个布尔值结果。该接⼝包含多种默\n        #     - Path=/api/**\n        #   filters:\n        #     - StripPrefix=1               # 去除前缀 /api/admin\n        # - id: admin-server\n        #   uri: lb://admin-server\n        #   predicates:\n        #     - Path=/api/**\n        - id: test-router\n          uri: https://www.baidu.com\n          predicates:\n            - Path=/','983b0b29a1e4305b5665537389a68687','2024-01-29 23:49:33','2024-01-29 23:49:33','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,28,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true                     # 开启从注册中心动态创建路由的功能，利用服务名进行路由\n          lower-case-service-id: true       # 将请求路径上的服务名配置为小写\n      routes:\n        # - id: admin-route                   # 唯一路由表示，没有实际作用不重复就好，建议配合服务名\n        #   uri: lb://admin-server            # 匹配后提供服务的路由地址，lb后跟提供服务的微服务的名，不要写错！！！\n        #   predicates:                       # 路由条件，Predicate 接受⼀个输⼊参数，返回⼀个布尔值结果。该接⼝包含多种默\n        #     - Path=/api/**\n        #   filters:\n        #     - StripPrefix=1               # 去除前缀 /api/admin\n        # - id: admin-server\n        #   uri: lb://admin-server\n        #   predicates:\n        #     - Path=/api/**\n        - id: test-router\n          uri: https://www.baidu.com\n          predicates:\n            - Path=/**','f7606366d766a26f7850326ab94ccc3e','2024-01-30 00:07:05','2024-01-30 00:07:06','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,29,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true                     # 开启从注册中心动态创建路由的功能，利用服务名进行路由\n          lower-case-service-id: true       # 将请求路径上的服务名配置为小写\n      routes:\n        - id: admin-route                   # 唯一路由表示，没有实际作用不重复就好，建议配合服务名\n          uri: lb://admin-server            # 匹配后提供服务的路由地址，lb后跟提供服务的微服务的名，不要写错！！！\n          predicates:                       # 路由条件，Predicate 接受⼀个输⼊参数，返回⼀个布尔值结果。该接⼝包含多种默\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=1               # 去除前缀 /api/admin\n        # - id: test-router\n        #   uri: https://www.baidu.com\n        #   predicates:\n        #     - Path=/**','0c1fd329d9026d8fd28ce22d24e2f75c','2024-01-30 00:08:58','2024-01-30 00:08:58','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,30,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=1\n        # - id: test-router\n        #   uri: https://www.baidu.com\n        #   predicates:\n        #     - Path=/**','3afd4f83dbfd98d8b2bf11c05d3cd000','2024-01-30 00:09:53','2024-01-30 00:09:53','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,31,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/**\n          filters:\n            - StripPrefix=1\n        # - id: test-router\n        #   uri: https://www.baidu.com\n        #   predicates:\n        #     - Path=/**','2af0b2ca2d3c1d626de0a920a5d24cdb','2024-01-30 00:10:38','2024-01-30 00:10:39','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,32,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin\n          filters:\n            - StripPrefix=1\n        # - id: test-router\n        #   uri: https://www.baidu.com\n        #   predicates:\n        #     - Path=/**','2f4767df3e4a9dda65975de6f8ccc3fd','2024-01-30 00:11:12','2024-01-30 00:11:13','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,33,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2\n        # - id: test-router\n        #   uri: https://www.baidu.com\n        #   predicates:\n        #     - Path=/**','d767518f5158ab483385110bdf9ad468','2024-01-30 00:11:47','2024-01-30 00:11:47','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,34,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2','49628b29ae68b444d1ead7ef9da0181e','2024-01-30 00:14:49','2024-01-30 00:14:49','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,35,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径','e45406646dacb1a7d0b376a54330c0ed','2024-01-30 00:15:27','2024-01-30 00:15:27','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,36,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n        - id: auth-route\n          uri: lb://auth-server\n          predicates:\n            - Path=/oauth/**\n          filters:\n            - StripPrefix=1','2ed264e03b0debf9d3d57f81f791b1bb','2024-01-30 00:17:01','2024-01-30 00:17:02','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,37,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n        - id: auth-route\n          uri: lb://auth-server\n          predicates:\n            - Path=/oauth/**','65d503519eef24e5b49d82ca9d1c7e4f','2024-01-30 00:19:49','2024-01-30 00:19:49','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,38,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n        - id: auth-route\n          uri: lb://auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1        ','7794b676d470d551b3dccfe923aa75c3','2024-01-30 00:19:58','2024-01-30 00:19:59','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,39,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n        - id: auth-route\n          uri: lb://auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1','de212bc67e3f04bfadeea6cb37d2b8d7','2024-01-30 00:27:11','2024-01-30 00:27:12','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(6,40,'pplax-config.yaml','PPLAX_BLOG','','pplax:\n  api:\n    basePath: /api   # 统一父级请求路径\n  sso:\n    secret-key: PPLAX_RESISTS_FATE\n    admin:\n      client-id: admin\n      client-secret: admin123456\n      resource-id: admin-server\n    file:\n      client-id: file\n      client-secret: file123456\n      resource-id: file-server\n\n  storage:\n    path:\n      root: /pplax-blog     # 存储的根目录\n    minio:\n      endpoint: http://127.0.0.1:9002 #Minio服务所在地址\n      accessKey: root #访问的key\n      secretKey: password #访问的秘钥\n      bucketName: pplax-blog  #存储桶名称','62ff469302ce389a70eca95dd250da62','2024-01-31 13:44:16','2024-01-31 13:44:17','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,41,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径','e45406646dacb1a7d0b376a54330c0ed','2024-01-31 13:48:26','2024-01-31 13:48:26','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,42,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n        - id: oauth-route\n          uri: lb://auth-server\n          predicates:\n            - Path=/oauth/**','0cf312e3f79411d407ebef88113c799b','2024-01-31 13:50:53','2024-01-31 13:50:54','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(6,43,'pplax-config.yaml','PPLAX_BLOG','','pplax:\n  oauth: \n    secret-key: PPLAX_RESISTS_FATE\n    client-id: pplax\n    client-secret: pplax123456\n  storage:\n    path:\n      root: /pplax-blog     # 存储的根目录\n    minio:\n      endpoint: http://127.0.0.1:9002 #Minio服务所在地址\n      accessKey: root #访问的key\n      secretKey: password #访问的秘钥\n      bucketName: pplax-blog  #存储桶名称','7282d1f52ab9af4c1d281ab2ea9310a0','2024-02-02 09:38:06','2024-02-02 09:38:06','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(6,44,'pplax-config.yaml','PPLAX_BLOG','','pplax:\n  oauth: \n    secret-key: PPLAX_RESISTS_FATE\n    client-id: client_id\n    client-secret: pplax123456\n  storage:\n    path:\n      root: /pplax-blog     # 存储的根目录\n    minio:\n      endpoint: http://127.0.0.1:9002 #Minio服务所在地址\n      accessKey: root #访问的key\n      secretKey: password #访问的秘钥\n      bucketName: pplax-blog  #存储桶名称','55e36e6c9b7a4b6fd4e4227ca66ed70d','2024-02-02 09:39:38','2024-02-02 09:39:38','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(0,45,'oauth-config.yaml','DEFAULT_GROUP','','pplax:\r\n  oauth: \r\n    secret-key: PPLAX_RESISTS_FATE\r\n    client-id: pplax\r\n    client-secret: pplax123456','a63fe137bce0794397ad12a97cfa8d51','2024-02-02 12:36:15','2024-02-02 12:36:15',NULL,'192.168.48.1','I','712185cc-1239-4192-9cb1-c71d5b242f91',''),(0,46,'storage-config.yaml','DEFAULT_GROUP','','pplax:\r\n  storage:\r\n    path:\r\n      root: /pplax-blog     # 存储的根目录\r\n    minio:\r\n      endpoint: http://127.0.0.1:9002 #Minio服务所在地址\r\n      accessKey: root #访问的key\r\n      secretKey: password #访问的秘钥\r\n      bucketName: pplax-blog  #存储桶名称','4614bc2fd6ebc9d43478e4bae3330fe5','2024-02-02 12:36:58','2024-02-02 12:36:59',NULL,'192.168.48.1','I','712185cc-1239-4192-9cb1-c71d5b242f91',''),(6,47,'pplax-config.yaml','PPLAX_BLOG','','pplax:\n  oauth: \n    secret-key: PPLAX_RESISTS_FATE\n    client-id: pplax\n    client-secret: pplax123456\n  storage:\n    path:\n      root: /pplax-blog     # 存储的根目录\n    minio:\n      endpoint: http://127.0.0.1:9002 #Minio服务所在地址\n      accessKey: root #访问的key\n      secretKey: password #访问的秘钥\n      bucketName: pplax-blog  #存储桶名称','7282d1f52ab9af4c1d281ab2ea9310a0','2024-02-02 12:37:47','2024-02-02 12:37:48','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(40,48,'oauth-config.yaml','PPLAX_BLOG','','pplax:\r\n  oauth: \r\n    secret-key: PPLAX_RESISTS_FATE\r\n    client-id: pplax\r\n    client-secret: pplax123456','a63fe137bce0794397ad12a97cfa8d51','2024-02-02 12:42:20','2024-02-02 12:42:20',NULL,'192.168.48.1','D','712185cc-1239-4192-9cb1-c71d5b242f91',''),(0,49,'oauth-config.yaml','PPLAX_BLOG','','pplax:\r\n  oauth: \r\n    secret-key: PPLAX_RESISTS_FATE\r\n    client-id: pplax\r\n    client-secret: pplax123456','a63fe137bce0794397ad12a97cfa8d51','2024-02-02 12:42:47','2024-02-02 12:42:48',NULL,'192.168.48.1','I','712185cc-1239-4192-9cb1-c71d5b242f91',''),(41,50,'storage-config.yaml','PPLAX_BLOG','','pplax:\r\n  storage:\r\n    path:\r\n      root: /pplax-blog     # 存储的根目录\r\n    minio:\r\n      endpoint: http://127.0.0.1:9002 #Minio服务所在地址\r\n      accessKey: root #访问的key\r\n      secretKey: password #访问的秘钥\r\n      bucketName: pplax-blog  #存储桶名称','4614bc2fd6ebc9d43478e4bae3330fe5','2024-02-02 12:42:57','2024-02-02 12:42:57',NULL,'192.168.48.1','D','712185cc-1239-4192-9cb1-c71d5b242f91',''),(0,51,'storage-config','PPLAX_BLOG','','pplax:\r\n  storage:\r\n    path:\r\n      root: /pplax-blog     # 存储的根目录\r\n    minio:\r\n      endpoint: http://127.0.0.1:9002 #Minio服务所在地址\r\n      accessKey: root #访问的key\r\n      secretKey: password #访问的秘钥\r\n      bucketName: pplax-blog  #存储桶名称','4614bc2fd6ebc9d43478e4bae3330fe5','2024-02-02 12:43:16','2024-02-02 12:43:16',NULL,'192.168.48.1','I','712185cc-1239-4192-9cb1-c71d5b242f91',''),(44,52,'storage-config','PPLAX_BLOG','','pplax:\r\n  storage:\r\n    path:\r\n      root: /pplax-blog     # 存储的根目录\r\n    minio:\r\n      endpoint: http://127.0.0.1:9002 #Minio服务所在地址\r\n      accessKey: root #访问的key\r\n      secretKey: password #访问的秘钥\r\n      bucketName: pplax-blog  #存储桶名称','4614bc2fd6ebc9d43478e4bae3330fe5','2024-02-02 12:45:20','2024-02-02 12:45:21',NULL,'192.168.48.1','D','712185cc-1239-4192-9cb1-c71d5b242f91',''),(0,53,'storage-config.yaml','PPLAX_BLOG','','pplax:\r\n  storage:\r\n    path:\r\n      root: /pplax-blog     # 存储的根目录\r\n    minio:\r\n      endpoint: http://127.0.0.1:9002 #Minio服务所在地址\r\n      accessKey: root #访问的key\r\n      secretKey: password #访问的秘钥\r\n      bucketName: pplax-blog  #存储桶名称','4614bc2fd6ebc9d43478e4bae3330fe5','2024-02-02 12:45:44','2024-02-02 12:45:44',NULL,'192.168.48.1','I','712185cc-1239-4192-9cb1-c71d5b242f91',''),(6,54,'pplax-config.yaml','PPLAX_BLOG','','pplax:\n  name: I like the world','4960e4c6f78edd2d8f84f3f725f15c17','2024-02-02 13:12:58','2024-02-02 13:12:59','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(6,55,'pplax-config.yaml','PPLAX_BLOG','','pplax:\n  name: I_like_the_world\n  test:\n    items: \n    - item1\n    - item2\n    - item3','764d54fee9c14312e524ffc5514b35dd','2024-02-02 13:15:46','2024-02-02 13:15:46','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(6,56,'pplax-config.yaml','PPLAX_BLOG','','pplax:\n  name: I_like_the_world\n  test:\n    items: \n      - item1\n      - item2\n      - item3','9337bb319075fd7661a9a2851aaea64c','2024-02-02 14:04:55','2024-02-02 14:04:55','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,57,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径','e45406646dacb1a7d0b376a54330c0ed','2024-02-02 14:06:46','2024-02-02 14:06:47','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,58,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n\npplax:\n  gateway:\n    white-url:\n      - /123/456\n      ` /test/**','5d72b311e6741715d16ab0f179866100','2024-02-02 14:08:58','2024-02-02 14:08:58','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,59,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n\npplax:\n  gateway:\n    white-url:\n      - /123/456\n      - /test/**','be8e11315eee0a244268b688e3e71a00','2024-02-02 14:13:05','2024-02-02 14:13:06','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,60,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n\npplax:\n  gateway:\n    white-url:\n      list:\n        - /123/456\n        - /test/**','0cef524cd9dd6e1627d827ff85725077','2024-02-02 14:15:57','2024-02-02 14:15:57','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,61,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n\npplax:\n  gateway:\n    exclude:\n      - /123/456\n      - /test/**\n    include:\n      - /admin/**\n      - /api/**','7b3b4b968bed228dd3d5a23beefa6f41','2024-02-02 14:17:16','2024-02-02 14:17:16','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,62,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n\npplax:\n  gateway:\n    exclude:                # 白名单配置\n      - /123/456\n      - /test/**\n    include:                # 必须要鉴权的路径\n      - /admin/**\n      - /api/**','1bd7b22810701481c6282ff1cec6bd64','2024-02-02 14:22:57','2024-02-02 14:22:58','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,63,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n\npplax:\n  gateway:\n    exclude:                # 白名单配置\n      - /123/456\n      - /test/**\n      - /**/oauth/token/**\n    include:                # 必须要鉴权的路径\n      - /admin/**\n      - /api/**','2ba5da529c070c601d6b63679b7b9163','2024-02-02 14:23:13','2024-02-02 14:23:13','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,64,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n\npplax:\n  gateway:\n    exclude:                # 白名单配置\n      - /123/456\n      - /test/**\n      - /**/oauth/token/**\n    include:                # 必须要鉴权的路径\n      - /admin/**\n      - /api/**','2ba5da529c070c601d6b63679b7b9163','2024-02-02 14:24:04','2024-02-02 14:24:04','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,65,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n\npplax:\n  gateway:\n    exclude:                # 白名单配置\n      - /123/456\n      - /test/**\n      - /api/admin/oauth/token\n    include:                # 必须要鉴权的路径\n      - /admin/**\n      - /api/**','f4911d728bdc85dfc851e5d5c1519e31','2024-02-02 14:25:07','2024-02-02 14:25:07','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,66,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n\npplax:\n  gateway:\n    exclude:                # 白名单配置\n      - /123/456\n      - /test/**\n      - /**/oauth/token\n    include:                # 必须要鉴权的路径\n      - /admin/**\n      - /api/**','883414228b8799269954a9b9d13fc076','2024-02-02 14:29:22','2024-02-02 14:29:23','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(19,67,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2                     # 去除前缀，参数是2表示去除两级路径\n\npplax:\n  gateway:\n    exclude:                # 白名单配置\n      - /**/oauth/token\n      - /**/swagger-ui.html\n    include:                # 必须要鉴权的路径\n      - /admin/**\n      - /api/**','5880ab2aff05a80127a7074d7dedcde8','2024-02-02 14:32:07','2024-02-02 14:32:08','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91','');
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

-- Dump completed on 2024-02-02 16:10:18
