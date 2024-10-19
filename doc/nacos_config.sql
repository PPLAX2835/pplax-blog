-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
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
) ENGINE=InnoDB AUTO_INCREMENT=227 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='config_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info`
--

LOCK TABLES `config_info` WRITE;
/*!40000 ALTER TABLE `config_info` DISABLE KEYS */;
INSERT INTO `config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES (6,'pplax-config.yaml','PPLAX_BLOG','pplax:\n  name: I_like_the_world','8a7115b0519b88583e8ae1b49c9d2f63','2024-01-23 14:55:13','2024-02-02 14:04:55','nacos','192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','自定义配置','','','yaml','',''),(9,'database-config.yaml','PPLAX_BLOG','spring:\n  # DATABASE CONFIG\n  datasource:\n    username: root\n    password: root\n    url: jdbc:mysql://localhost:3306/pplax_blog?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&useSSL=false\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.alibaba.druid.pool.DruidDataSource\n\n    druid:\n      # Druid连接池配置\n      initialSize: 5\n      minIdle: 5\n      maxActive: 20\n      maxWait: 60000\n      timeBetweenEvictionRunsMillis: 60000\n      minEvictableIdleTimeMillis: 300000\n      testWhileIdle: true\n      testOnBorrow: false\n      testOnReturn: false\n      poolPreparedStatements: true\n      maxPoolPreparedStatementPerConnectionSize: 20\n  \n      # Druid监控相关配置\n      filters: stat,wall,log4j\n      useGlobalDataSourceStat: true\n      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500\n  \n      # 监控页面配置\n      stat-view-servlet:\n        enabled: true\n        url-pattern: /druid/*\n        allow: \n        deny: # 不配置表示没有黑名单\n        login-username: admin  # 登录用户名\n        login-password: admin  # 登录密码\n        reset-enable: false  # 禁用“Reset All”功能\n  \n      web-stat-filter:\n        enabled: true\n        url-pattern: /*\n        exclusions: \"*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*\"  # 忽略资源','091af85e2335376be1dff818fecb3f1d','2024-01-23 15:08:39','2024-10-16 17:38:46','nacos','192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','数据库的统一配置','','','yaml','',''),(11,'mybatis-plus-config.yaml','PPLAX_BLOG','mybatis-plus:\r\n  mapper-locations: classpath:/mapper/*Mapper.xml\r\n  #实体扫描，多个package用逗号或者分号分隔\r\n  typeAliasesPackage: xyz.pplax.pplaxblog.xo.entity\r\n  global-config:\r\n    # 数据库相关配置\r\n    db-config:\r\n      #主键类型  0:\"数据库ID自增\", 1:\"用户输入ID\",2:\"全局唯一ID (数字类型唯一ID)\", 3:\"全局唯一ID UUID\";\r\n      id-type: UUID\r\n      #字段策略 IGNORED:\"忽略判断\",NOT_NULL:\"非 NULL 判断\"),NOT_EMPTY:\"非空判断\"\r\n      field-strategy: NOT_EMPTY\r\n      #驼峰下划线转换\r\n      column-underline: true\r\n      #数据库大写下划线转换\r\n      #capital-mode: true\r\n      #逻辑删除配置\r\n      logic-delete-value: 0\r\n      logic-not-delete-value: 1\r\n      db-type: mysql\r\n    #刷新mapper 调试神器\r\n    refresh: true','1411a3d545ca66c6c853b92c88f17767','2024-01-23 15:25:12','2024-01-23 15:25:12',NULL,'192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','mybatis-plus的配置',NULL,NULL,'yaml',NULL,''),(15,'redis-config.yaml','PPLAX_BLOG','\nspring:\n  redis:\n    database: 2\n    host: 127.0.0.1\n    port: 6379\n    password: 123456\n    # 默认过期时间\n    expire: 600','3ff163876dc94765ced1f9c1cb12cead','2024-01-24 20:11:04','2024-10-16 17:38:58','nacos','192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','redis的相关配置','','','yaml','',''),(43,'oauth-config.yaml','PPLAX_BLOG','pplax:\n  oauth: \n    secret-key: PPLAX_RESISTS_FATE\n    client-id: pplax\n    client-secret: pplax123456\n    access-token-validity-seconds: 7776000      # token 90天后过期','889fba0c823af7f42e7226bbdde1eccf','2024-02-02 12:42:48','2024-10-14 14:29:18','nacos','27.129.145.67','','712185cc-1239-4192-9cb1-c71d5b242f91','oauth的相关配置','','','yaml','',''),(122,'amqp-config.yaml','PPLAX_BLOG','spring:\n  #rabbitmq配置\n  rabbitmq:\n    username: admin\n    password: admin\n    addresses: 127.0.0.1:5672\n','d15d138406b5b8cbeb521b729dfcbaf7','2024-04-20 09:53:26','2024-10-16 17:39:12','nacos','192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','消息队列配置','','','yaml','',''),(197,'gateway-config.yaml','PPLAX_BLOG','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n      # api 路由转发\n        - id: admin-api-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2\n          \n        - id: web-api-route\n          uri: lb://web-server\n          predicates:\n            - Path=/api/web/**\n          filters:\n            - StripPrefix=2\n          \n        - id: auth-api-route\n          uri: lb://auth-server\n          predicates:\n            - Path=/api/pOauth/**\n          filters:\n            - StripPrefix=2\n          \n        - id: file-api-route\n          uri: lb://file-server\n          predicates:\n            - Path=/api/file/**\n          filters:\n            - StripPrefix=2\n\n        - id: message-api-route\n          uri: lb://message-server\n          predicates:\n            - Path=/api/message/**\n          filters:\n            - StripPrefix=2\n\n      # webSocket的路由转发\n        - id: message-ws-websocket\n          uri: lb://message-server\n          predicates:\n            - Path=/api/webSocket/**\n          filters:\n            - StripPrefix=2\n\n      # druid转发\n        - id: admin-druid-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/druid/admin/**\n          filters:\n            - RewritePath=/druid/admin/(?<segment>.*), /druid/${segment}\n\n        - id: web-druid-route\n          uri: lb://web-server\n          predicates:\n            - Path=/druid/web/**\n          filters:\n            - RewritePath=/druid/web/(?<segment>.*), /druid/${segment}\n\n        - id: auth-druid-route\n          uri: lb://auth-server\n          predicates:\n            - Path=/druid/auth/**\n          filters:\n            - RewritePath=/druid/auth/(?<segment>.*), /druid/${segment}\n\n        - id: file-druid-route\n          uri: lb://file-server\n          predicates:\n            - Path=/druid/file/**\n          filters:\n            - RewritePath=/druid/file/(?<segment>.*), /druid/${segment}\n\n        - id: message-druid-route\n          uri: lb://message-server\n          predicates:\n            - Path=/druid/message/**\n          filters:\n            - RewritePath=/druid/message/(?<segment>.*), /druid/${segment}\npplax:\n  gateway:\n    excludePath:   # 白名单配置\n      - /api/pOauth/**\n      - /api/web/site/**\n      - /api/file/localStorage/**\n      - /api/webSocket/** \n      - /api/web/style/**\n      # swagger\n      - /swagger-ui.html\n      - /swagger-ui.html\n      - /webjars/**\n      - /v2/api-docs\n      - /swagger-resources/**\n      - /api/*/swagger-ui.html\n      - /api/*/webjars/**\n      - /api/*/v2/api-docs\n      - /api/*/swagger-resources/**\n      # druid\n      - /druid/**\n    whitelist:\n      - method: GET\n        path: \"/api/web/tag/list\"\n      - method: GET\n        path: \"/api/web/blogSort/list\"\n      - method: GET\n        path: \"/api/web/blog/list\"\n      - method: GET\n        path: \"/api/web/blog/search\"\n      - method: GET\n        path: \"/api/web/blog/*\"\n      - method: GET\n        path: \"/api/web/blog/*/comment/list\"\n      - method: GET\n        path: \"/api/web/user/*/space/blog/list\"\n      - method: GET\n        path: \"/api/web/user/*/space/count\"\n      - method: GET\n        path: \"/api/web/comment/*/reply/list\"\n      - method: GET\n        path: \"/api/web/archive\"\n      - method: GET\n        path: \"/api/web/say/list\"\n      - method: GET\n        path: \"/api/web/say/*/comment/list\"\n      - method: POST\n        path: \"/api/web/message/leave\"\n      - method: GET\n        path: \"/api/web/message/leave/list\"\n      - method: GET\n        path: \"/api/web/user/exist\"\n      - method: GET\n        path: \"/api/web/user/*/userInfo\"\n      - method: GET\n        path: \"/api/web/link/list\"\n      - method: GET\n        path: \"/api/file/admin/localStorage/*\"','d1662a10940c58622a7d8d0cdcdfc098','2024-08-26 17:48:04','2024-10-19 20:09:29','nacos','192.168.48.1','','712185cc-1239-4192-9cb1-c71d5b242f91','网关基本配置','','','yaml','',''),(211,'email-config.yaml','PPLAX_BLOG','#spring\nspring:\n  # mail\n  mail:\n    host: smtp.qq.com\n    username: 1458667357@qq.com\n    password: uxkmmqksoyaoggdh\n    default-encoding: utf-8\n    protocol: smtp\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true','3a27804466676510b009379cb99374f8','2024-08-29 13:15:17','2024-09-04 16:14:52','nacos','36.98.197.0','','712185cc-1239-4192-9cb1-c71d5b242f91','邮箱配置','','','yaml','','');
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
) ENGINE=InnoDB AUTO_INCREMENT=245 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='多租户改造';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `his_config_info`
--

LOCK TABLES `his_config_info` WRITE;
/*!40000 ALTER TABLE `his_config_info` DISABLE KEYS */;
INSERT INTO `his_config_info` (`id`, `nid`, `data_id`, `group_id`, `app_name`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `op_type`, `tenant_id`, `encrypted_data_key`) VALUES (43,236,'oauth-config.yaml','PPLAX_BLOG','','pplax:\r\n  oauth: \r\n    secret-key: PPLAX_RESISTS_FATE\r\n    client-id: pplax\r\n    client-secret: pplax123456','a63fe137bce0794397ad12a97cfa8d51','2024-10-14 14:29:18','2024-10-14 14:29:18','nacos','27.129.145.67','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(9,237,'database-config.yaml','PPLAX_BLOG','','spring:\n  # DATABASE CONFIG\n  datasource:\n    username: root\n    password: fN2sbpU4ruKIsgVFafbX123hKIjrdIpOSv8Vxs85SS0QnhPGqxQ74Jtcz1o16VhW\n    url: jdbc:mysql://localhost:3306/pplax_blog?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&useSSL=false\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.alibaba.druid.pool.DruidDataSource\n\n    initialSize: 5\n    minIdle: 5\n    maxActive: 20\n    maxWait: 60000\n    timeBetweenEvictionRunsMillis: 60000\n    minEvictableIdleTimeMillis: 300000\n    testWhileIdle: true\n    testOnBorrow: false\n    testOnReturn: false\n    poolPreparedStatements: true\n\n    #配置监控统计拦截的filters，去掉后监控界面sql无法统计，\'wall\'用于防火墙\n    filters: stat,wall,log4j\n    maxPoolPreparedStatementPerConnectionSize: 20\n    useGlobalDataSourceStat: true\n    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500','b7a29431298d3bcf3c0ce76178670f19','2024-10-16 11:10:27','2024-10-16 11:10:27','nacos','27.129.145.67','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(9,238,'database-config.yaml','PPLAX_BLOG','','spring:\n  # DATABASE CONFIG\n  datasource:\n    username: root\n    password: fN2sbpU4ruKIsgVFafbX123hKIjrdIpOSv8Vxs85SS0QnhPGqxQ74Jtcz1o16VhW\n    url: jdbc:mysql://localhost:3306/pplax_blog?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&useSSL=false\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.alibaba.druid.pool.DruidDataSource\n\n    # druid配置\n    druid:\n      # Druid连接池配置\n      initialSize: 5\n      minIdle: 5\n      maxActive: 20\n      maxWait: 60000\n      timeBetweenEvictionRunsMillis: 60000\n      minEvictableIdleTimeMillis: 300000\n      testWhileIdle: true\n      testOnBorrow: false\n      testOnReturn: false\n      poolPreparedStatements: true\n      maxPoolPreparedStatementPerConnectionSize: 20\n  \n      # Druid监控相关配置\n      filters: stat,wall,log4j\n      useGlobalDataSourceStat: true\n      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500\n  \n      # 监控页面配置\n      stat-view-servlet:\n        enabled: true\n        url-pattern: /druid/*\n        allow: 127.0.0.1  # 只允许本地访问\n        deny: # 不配置表示没有黑名单\n        login-username: admin  # 登录用户名\n        login-password: admin  # 登录密码\n        reset-enable: false  # 禁用“Reset All”功能\n  \n      web-stat-filter:\n        enabled: true\n        url-pattern: /*\n        exclusions: \"*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*\"  # 忽略资源','5216127f6fa97f58a13993c3acec0833','2024-10-16 14:22:48','2024-10-16 14:22:48','nacos','27.129.145.67','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(197,239,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n      # 一般的路由转发\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2\n          \n        - id: web-route\n          uri: lb://web-server\n          predicates:\n            - Path=/api/web/**\n          filters:\n            - StripPrefix=2\n          \n        - id: auth-route\n          uri: lb://auth-server\n          predicates:\n            - Path=/api/pOauth/**\n          filters:\n            - StripPrefix=1\n          \n        - id: file-route\n          uri: lb://file-server\n          predicates:\n            - Path=/api/file/**\n          filters:\n            - StripPrefix=2\n\n       \n        - id: message-route\n          uri: lb://message-server\n          predicates:\n            - Path=/api/message/**\n          filters:\n            - StripPrefix=2\n\n      # webSocket的路由转发\n        - id: message-chat-websocket\n          uri: lb://message-server\n          predicates:\n            - Path=/api/webSocket/**\n          filters:\n            - StripPrefix=2\n\n\n\npplax:\n  gateway:\n    excludePath:   # 白名单配置\n      - /api/pOauth/**\n      - /api/web/site/**\n      - /api/file/localStorage/**\n      - /api/webSocket/** \n      - /api/web/style/**\n      # swagger\n      - /swagger-ui.html\n      - /swagger-ui.html\n      - /webjars/**\n      - /v2/api-docs\n      - /swagger-resources/**\n      - /api/*/swagger-ui.html\n      - /api/*/webjars/**\n      - /api/*/v2/api-docs\n      - /api/*/swagger-resources/**\n    whitelist:\n      - method: GET\n        path: \"/api/web/tag/list\"\n      - method: GET\n        path: \"/api/web/blogSort/list\"\n      - method: GET\n        path: \"/api/web/blog/list\"\n      - method: GET\n        path: \"/api/web/blog/search\"\n      - method: GET\n        path: \"/api/web/blog/*\"\n      - method: GET\n        path: \"/api/web/blog/*/comment/list\"\n      - method: GET\n        path: \"/api/web/user/*/space/blog/list\"\n      - method: GET\n        path: \"/api/web/user/*/space/count\"\n      - method: GET\n        path: \"/api/web/comment/*/reply/list\"\n      - method: GET\n        path: \"/api/web/archive\"\n      - method: GET\n        path: \"/api/web/say/list\"\n      - method: GET\n        path: \"/api/web/say/*/comment/list\"\n      - method: POST\n        path: \"/api/web/message/leave\"\n      - method: GET\n        path: \"/api/web/message/leave/list\"\n      - method: GET\n        path: \"/api/web/user/exist\"\n      - method: GET\n        path: \"/api/web/user/*/userInfo\"\n      - method: GET\n        path: \"/api/web/link/list\"\n      - method: GET\n        path: \"/api/file/admin/localStorage/*\"','23402869a08968d5af460a274dfdf7b5','2024-10-16 14:23:07','2024-10-16 14:23:08','nacos','27.129.145.67','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(9,240,'database-config.yaml','PPLAX_BLOG','','spring:\n  # DATABASE CONFIG\n  datasource:\n    username: root\n    password: fN2sbpU4ruKIsgVFafbX123hKIjrdIpOSv8Vxs85SS0QnhPGqxQ74Jtcz1o16VhW\n    url: jdbc:mysql://localhost:3306/pplax_blog?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&useSSL=false\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.alibaba.druid.pool.DruidDataSource\n\n    druid:\n      # Druid连接池配置\n      initialSize: 5\n      minIdle: 5\n      maxActive: 20\n      maxWait: 60000\n      timeBetweenEvictionRunsMillis: 60000\n      minEvictableIdleTimeMillis: 300000\n      testWhileIdle: true\n      testOnBorrow: false\n      testOnReturn: false\n      poolPreparedStatements: true\n      maxPoolPreparedStatementPerConnectionSize: 20\n  \n      # Druid监控相关配置\n      filters: stat,wall,log4j\n      useGlobalDataSourceStat: true\n      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500\n  \n      # 监控页面配置\n      stat-view-servlet:\n        enabled: true\n        url-pattern: /druid/*\n        allow: \n        deny: # 不配置表示没有黑名单\n        login-username: admin  # 登录用户名\n        login-password: admin  # 登录密码\n        reset-enable: false  # 禁用“Reset All”功能\n  \n      web-stat-filter:\n        enabled: true\n        url-pattern: /*\n        exclusions: \"*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*\"  # 忽略资源','07e7b230f58d15dbf5f68b183766500f','2024-10-16 17:38:46','2024-10-16 17:38:46','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(15,241,'redis-config.yaml','PPLAX_BLOG','','\nspring:\n  redis:\n    database: 2\n    host: 127.0.0.1\n    port: 6379\n    password: SzlD3F4J8IgfnkkmF4553RWPd7b2zAfYH6VltBeG3XXioyMznzaHZh9IUF9SJdXd\n    # 默认过期时间\n    expire: 600','e45f24b2e40fd65c34fd2fb7195777f4','2024-10-16 17:38:58','2024-10-16 17:38:58','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(122,242,'amqp-config.yaml','PPLAX_BLOG','','spring:\n  #rabbitmq配置\n  rabbitmq:\n    username: admin\n    password: 5dnTy5OO3l27Zi6xyHK6SohnBzlrXVjw3izUqrVURm4fK6REdzTSvk3bdP0hxWZ8\n    addresses: 127.0.0.1:5672\n','7f17e7320f75175c8a1c5928b7a8c207','2024-10-16 17:39:11','2024-10-16 17:39:12','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(197,243,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n      # api 路由转发\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2\n          \n        - id: web-route\n          uri: lb://web-server\n          predicates:\n            - Path=/api/web/**\n          filters:\n            - StripPrefix=2\n          \n        - id: auth-route\n          uri: lb://auth-server\n          predicates:\n            - Path=/api/pOauth/**\n          filters:\n            - StripPrefix=1\n          \n        - id: file-route\n          uri: lb://file-server\n          predicates:\n            - Path=/api/file/**\n          filters:\n            - StripPrefix=2\n\n        - id: message-route\n          uri: lb://message-server\n          predicates:\n            - Path=/api/message/**\n          filters:\n            - StripPrefix=2\n\n      # webSocket的路由转发\n        - id: message-chat-websocket\n          uri: lb://message-server\n          predicates:\n            - Path=/api/webSocket/**\n          filters:\n            - StripPrefix=2\n\n      # druid转发\n        - id: admin-druid-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/druid/admin/**\n          filters:\n            - RewritePath=/druid/admin/(?<segment>.*), /druid/${segment}\n\n        - id: web-druid-route\n          uri: lb://web-server\n          predicates:\n            - Path=/druid/web/**\n          filters:\n            - RewritePath=/druid/web/(?<segment>.*), /druid/${segment}\n\n        - id: auth-druid-route\n          uri: lb://auth-server\n          predicates:\n            - Path=/druid/auth/**\n          filters:\n            - RewritePath=/druid/auth/(?<segment>.*), /druid/${segment}\n\n        - id: file-druid-route\n          uri: lb://file-server\n          predicates:\n            - Path=/druid/file/**\n          filters:\n            - RewritePath=/druid/file/(?<segment>.*), /druid/${segment}\n\n        - id: message-druid-route\n          uri: lb://message-server\n          predicates:\n            - Path=/druid/message/**\n          filters:\n            - RewritePath=/druid/message/(?<segment>.*), /druid/${segment}\npplax:\n  gateway:\n    excludePath:   # 白名单配置\n      - /api/pOauth/**\n      - /api/web/site/**\n      - /api/file/localStorage/**\n      - /api/webSocket/** \n      - /api/web/style/**\n      # swagger\n      - /swagger-ui.html\n      - /swagger-ui.html\n      - /webjars/**\n      - /v2/api-docs\n      - /swagger-resources/**\n      - /api/*/swagger-ui.html\n      - /api/*/webjars/**\n      - /api/*/v2/api-docs\n      - /api/*/swagger-resources/**\n      # druid\n      - /druid/**\n    whitelist:\n      - method: GET\n        path: \"/api/web/tag/list\"\n      - method: GET\n        path: \"/api/web/blogSort/list\"\n      - method: GET\n        path: \"/api/web/blog/list\"\n      - method: GET\n        path: \"/api/web/blog/search\"\n      - method: GET\n        path: \"/api/web/blog/*\"\n      - method: GET\n        path: \"/api/web/blog/*/comment/list\"\n      - method: GET\n        path: \"/api/web/user/*/space/blog/list\"\n      - method: GET\n        path: \"/api/web/user/*/space/count\"\n      - method: GET\n        path: \"/api/web/comment/*/reply/list\"\n      - method: GET\n        path: \"/api/web/archive\"\n      - method: GET\n        path: \"/api/web/say/list\"\n      - method: GET\n        path: \"/api/web/say/*/comment/list\"\n      - method: POST\n        path: \"/api/web/message/leave\"\n      - method: GET\n        path: \"/api/web/message/leave/list\"\n      - method: GET\n        path: \"/api/web/user/exist\"\n      - method: GET\n        path: \"/api/web/user/*/userInfo\"\n      - method: GET\n        path: \"/api/web/link/list\"\n      - method: GET\n        path: \"/api/file/admin/localStorage/*\"','0c8c15e7b00998c9f1425988140a544f','2024-10-17 12:11:21','2024-10-17 12:11:22','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91',''),(197,244,'gateway-config.yaml','PPLAX_BLOG','','spring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n      routes:\n      # api 路由转发\n        - id: admin-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/api/admin/**\n          filters:\n            - StripPrefix=2\n          \n        - id: web-route\n          uri: lb://web-server\n          predicates:\n            - Path=/api/web/**\n          filters:\n            - StripPrefix=2\n          \n        - id: auth-route\n          uri: lb://auth-server\n          predicates:\n            - Path=/api/pOauth/**\n          filters:\n            - StripPrefix=2\n          \n        - id: file-route\n          uri: lb://file-server\n          predicates:\n            - Path=/api/file/**\n          filters:\n            - StripPrefix=2\n\n        - id: message-route\n          uri: lb://message-server\n          predicates:\n            - Path=/api/message/**\n          filters:\n            - StripPrefix=2\n\n      # webSocket的路由转发\n        - id: message-chat-websocket\n          uri: lb://message-server\n          predicates:\n            - Path=/api/webSocket/**\n          filters:\n            - StripPrefix=2\n\n      # druid转发\n        - id: admin-druid-route\n          uri: lb://admin-server\n          predicates:\n            - Path=/druid/admin/**\n          filters:\n            - RewritePath=/druid/admin/(?<segment>.*), /druid/${segment}\n\n        - id: web-druid-route\n          uri: lb://web-server\n          predicates:\n            - Path=/druid/web/**\n          filters:\n            - RewritePath=/druid/web/(?<segment>.*), /druid/${segment}\n\n        - id: auth-druid-route\n          uri: lb://auth-server\n          predicates:\n            - Path=/druid/auth/**\n          filters:\n            - RewritePath=/druid/auth/(?<segment>.*), /druid/${segment}\n\n        - id: file-druid-route\n          uri: lb://file-server\n          predicates:\n            - Path=/druid/file/**\n          filters:\n            - RewritePath=/druid/file/(?<segment>.*), /druid/${segment}\n\n        - id: message-druid-route\n          uri: lb://message-server\n          predicates:\n            - Path=/druid/message/**\n          filters:\n            - RewritePath=/druid/message/(?<segment>.*), /druid/${segment}\npplax:\n  gateway:\n    excludePath:   # 白名单配置\n      - /api/pOauth/**\n      - /api/web/site/**\n      - /api/file/localStorage/**\n      - /api/webSocket/** \n      - /api/web/style/**\n      # swagger\n      - /swagger-ui.html\n      - /swagger-ui.html\n      - /webjars/**\n      - /v2/api-docs\n      - /swagger-resources/**\n      - /api/*/swagger-ui.html\n      - /api/*/webjars/**\n      - /api/*/v2/api-docs\n      - /api/*/swagger-resources/**\n      # druid\n      - /druid/**\n    whitelist:\n      - method: GET\n        path: \"/api/web/tag/list\"\n      - method: GET\n        path: \"/api/web/blogSort/list\"\n      - method: GET\n        path: \"/api/web/blog/list\"\n      - method: GET\n        path: \"/api/web/blog/search\"\n      - method: GET\n        path: \"/api/web/blog/*\"\n      - method: GET\n        path: \"/api/web/blog/*/comment/list\"\n      - method: GET\n        path: \"/api/web/user/*/space/blog/list\"\n      - method: GET\n        path: \"/api/web/user/*/space/count\"\n      - method: GET\n        path: \"/api/web/comment/*/reply/list\"\n      - method: GET\n        path: \"/api/web/archive\"\n      - method: GET\n        path: \"/api/web/say/list\"\n      - method: GET\n        path: \"/api/web/say/*/comment/list\"\n      - method: POST\n        path: \"/api/web/message/leave\"\n      - method: GET\n        path: \"/api/web/message/leave/list\"\n      - method: GET\n        path: \"/api/web/user/exist\"\n      - method: GET\n        path: \"/api/web/user/*/userInfo\"\n      - method: GET\n        path: \"/api/web/link/list\"\n      - method: GET\n        path: \"/api/file/admin/localStorage/*\"','c7ac469c10012662178b4f6b42ea9119','2024-10-19 20:09:29','2024-10-19 20:09:29','nacos','192.168.48.1','U','712185cc-1239-4192-9cb1-c71d5b242f91','');
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
INSERT INTO `users` (`username`, `password`, `enabled`) VALUES ('nacos','$2a$10$eIzzE2K7FGKWUmQTBGFzeOy7MOA99WZsZSVsHccIwctPQa6rHr/2.',1),('NacosAD','$2a$10$NxtdWbEKHm0MLTTgBmbg7unZRUL9RKeirkUdT/yUGuv0.4.74OBzS',1),('sktt','$2a$10$CJtPb35fnFC0YKNHTcxOKuKO0pCJ75awq9N9lnc6g2hkRiXdbvjPK',1);
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

-- Dump completed on 2024-10-19 20:15:20
