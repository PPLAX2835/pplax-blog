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
INSERT INTO `t_blog` (`uid`, `title`, `summary`, `blog_content_uid`, `tag_uids`, `click_count`, `collect_count`, `cover_image_uid`, `status`, `user_uid`, `is_original`, `articles_part`, `blog_sort_uid`, `level`, `create_time`, `update_time`) VALUES ('11a4784c4d8b4cb38f8523284364690c','再次测试添加','','2a55082075064b47998053f69c7d1dc3','8f3d992d404347dc998f09b99d363714,97e3b2d7d7934a8dbae95222e04ef25e',0,0,'',1,'f8616ce5aa0c8eeacfa0d997dbdbe0d3',1,'','3ae899e993b744c99fb78dccafac8e66',0,'2024-03-14 09:42:58','2024-03-14 09:42:58'),('211fb300fe7a4a20bfda396a9450b117','后端开发简历咋整呢','这是摘要','771690201d694446b653b9f6a9eabbaf','25228e8e5d4847d39a4b1466e7403e69,53c5a0f3142e4f54820315936f78383b,7e0e93ea6cdb44ae92e58f48e6496ed7',0,0,'66e30426da6721fd7bd363e650cee5f3',1,'f8616ce5aa0c8eeacfa0d997dbdbe0d3',1,'','2c93dfab0e754006866f8ed486923a41',0,'2024-01-15 07:54:25','2024-03-11 11:49:50'),('22b5a762375648edabd72c31b74dd4c2','再再次测试','','3d1a2672e8134e99a34828df99efe052','7e0e93ea6cdb44ae92e58f48e6496ed7',0,0,'',1,'f8616ce5aa0c8eeacfa0d997dbdbe0d3',1,'','3ae899e993b744c99fb78dccafac8e66',0,'2024-03-14 09:44:45','2024-03-14 09:44:45'),('4b9148a678f1411cb17c20163428a55d','test','','819e9308cdd843ac8b5dddfe4ded70ca','7e0e93ea6cdb44ae92e58f48e6496ed7',0,0,'',0,'f8616ce5aa0c8eeacfa0d997dbdbe0d3',1,'','cce33bd372a5403a81346a3001ff544a',0,'2024-03-14 12:09:09','2024-03-16 02:57:35'),('5de884b1e9a74f51a25df84321c165cd','测试添加','','7dd7ab3a48984550a596f483178442f0','04612e33964d45d19fa7f4fd61acd807',0,0,'',1,'ee515eca338c4d358baea8d2c9930a45',1,'','cce33bd372a5403a81346a3001ff544a',0,'2024-03-14 09:18:29','2024-03-14 09:36:01'),('744d407261c742cab437d4e1054b3acd','Linux 虚拟内存','','9d4882479baf4a5e93396ac365636605','957cc32cc28441349bc93c6f8103fd2e,91831f7ab9e54cf3bc87d227dac12258',0,0,'97471190545c6006bb3ed1eb643df252',1,'f8616ce5aa0c8eeacfa0d997dbdbe0d3',1,'','cce33bd372a5403a81346a3001ff544a',0,'2024-03-16 04:47:48','2024-03-16 04:47:48'),('93481c3880774ae59bbfad5ac094ac95','Redmi AC2100 OpenWrt','将Redmi AC2100转生为OpenWrt','f6121ceeb0bf47939e2097760b0d8f40','56f0ade4f95543d8a3836b218ff9bbe9,d3f1652289e34bbfaf567410975003f6,69f2f1d3578e479e9c53d4a41d5db280',0,0,'33b39f75a9b7c9683080be7cf580de02',1,'ee515eca338c4d358baea8d2c9930a45',1,'','cce33bd372a5403a81346a3001ff544a',0,'2024-03-14 09:16:55','2024-03-14 09:36:01'),('97e68b9bf2254789bfdbf6fa54310147','test','','1810b500225c46f4b34e6ca68b8582c0','7e0e93ea6cdb44ae92e58f48e6496ed7',0,0,'',0,'f8616ce5aa0c8eeacfa0d997dbdbe0d3',1,'','cce33bd372a5403a81346a3001ff544a',0,'2024-03-14 12:09:11','2024-03-16 02:57:30'),('aede2547db134413b3f4e8c5407a8abd','测试新增临时保存','','f8552d918da146bda992a1031266dad1','7e0e93ea6cdb44ae92e58f48e6496ed7',0,0,'',6,'f8616ce5aa0c8eeacfa0d997dbdbe0d3',1,'','093d8bdd01c84890a928e923d5c235fe',0,'2024-03-16 02:09:43','2024-03-16 02:09:43'),('b7dbc212bf304422be9fa22c3f2cf1a2','年轻人的第一款怪猎','这是摘要','00ef773b1cb542bb8e4d262e5f23e37c','7efee6f74d594d25928ba86bc7adee28,9da13eb143f54c6bb70ecbd5212bde69',0,0,NULL,6,'ee515eca338c4d358baea8d2c9930a45',1,'','3ae899e993b744c99fb78dccafac8e66',1,'2024-01-15 07:48:52','2024-03-14 09:36:01'),('dcf8f268a51143fb988384e7559e5650','黑龙登龙点讲解','这是摘要','afa22a4ffa7e41de99863f16ab3e5c97','7efee6f74d594d25928ba86bc7adee28,9da13eb143f54c6bb70ecbd5212bde69',0,0,NULL,5,'ee515eca338c4d358baea8d2c9930a45',1,'','3ae899e993b744c99fb78dccafac8e66',2,'2024-01-15 07:52:42','2024-03-14 09:36:00'),('e46b576ca9fb44dab1133218d5d7aed0','测试新增临时保存','','e126b32bae5e45bfbb60cff9c2a08a2d','7e0e93ea6cdb44ae92e58f48e6496ed7',0,0,'',6,'f8616ce5aa0c8eeacfa0d997dbdbe0d3',1,'','093d8bdd01c84890a928e923d5c235fe',0,'2024-03-16 02:09:57','2024-03-16 02:09:57'),('e7501287236e4008b7e2a098eddc64ab','泰拉瑞亚服务器','自己搭一个泰拉瑞亚游戏服务器','650fe6d0a99c44b69b4c9d15868c6469','db700b2844d34234aaf4e69657f8e56e,a63797683b52488b99bc0f4d8a0545d7',0,0,'b427410925d506c0471bf4d1a9c5451c',1,'f8616ce5aa0c8eeacfa0d997dbdbe0d3',1,'','3ae899e993b744c99fb78dccafac8e66',0,'2024-03-22 14:12:01','2024-03-22 14:12:01'),('f4bd17e13924496889a2eb16ae01ba30','这是一个标题','这是摘要','9ddd597bb3ff4426b84f35439f553e94','ca928e8718654aa5a802e2f69277b137',0,0,NULL,4,'f8616ce5aa0c8eeacfa0d997dbdbe0d3',0,'https://cn.bing.com/','093d8bdd01c84890a928e923d5c235fe',3,'2024-01-15 07:48:52','2024-03-14 02:36:04'),('f8c3c2fa618242bbaf2386a73e80fdbe','test','','521e940f8e3c4233904347512a5cdf43','7e0e93ea6cdb44ae92e58f48e6496ed7',0,0,'',0,'f8616ce5aa0c8eeacfa0d997dbdbe0d3',1,'','cce33bd372a5403a81346a3001ff544a',0,'2024-03-14 12:09:10','2024-03-16 02:57:35'),('fd1622ca585b4ccea34c4bbc88ac4122','saf','','699071efe3ce4df5add4089e1adc5994',',97e3b2d7d7934a8dbae95222e04ef25e',0,0,'',1,'f8616ce5aa0c8eeacfa0d997dbdbe0d3',1,'','3ae899e993b744c99fb78dccafac8e66',0,'2024-03-14 09:27:16','2024-03-14 09:40:25');
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
INSERT INTO `t_blog_content` (`uid`, `content`, `status`, `create_time`, `update_time`) VALUES ('00ef773b1cb542bb8e4d262e5f23e37c','罚你登龙给怪回血',1,'2024-01-15 07:48:34','2024-01-15 07:48:34'),('1810b500225c46f4b34e6ca68b8582c0','sds',0,'2024-03-14 12:09:11','2024-03-16 02:57:30'),('2a55082075064b47998053f69c7d1dc3','### 三级标题![g.jpg](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-14/1710409778701.jpg)',1,'2024-03-14 09:42:58','2024-03-14 09:42:58'),('3d1a2672e8134e99a34828df99efe052','s![RC.jpg](http://127.0.0.1:9002/pplax-blog/blog/attach/file/2024-03-14/1710409534738.jpg)\n[开题报告.docx](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-14/1710417653763.docx)\n\nasdasdasdasdasd',1,'2024-03-14 09:44:45','2024-03-14 09:44:45'),('521e940f8e3c4233904347512a5cdf43','sd',0,'2024-03-14 12:09:10','2024-03-16 02:57:35'),('650fe6d0a99c44b69b4c9d15868c6469','## 依赖环境（screen是执行那种一个进程内部需要输入的情况的，另一个不用讲）\n\n`yum install screen unzip`\n## 泰拉瑞亚服务端（根据客户端版本挑吧，虽然没试版本不同行不行）\n[Server - Terraria Wiki (fandom.com)](https://terraria.fandom.com/wiki/Server#Downloads)\n![image.png](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556312724.png)\n这里有个1.4.4.9版本\n[terraria-server-1449-20231124103121-qpks7sp.zip](http://127.0.0.1:9002/pplax-blogblog/attach/2024-03-16/1710556361825.zip)\n## 启动\n\n解压下载的服务端后进入`Linux`目录执行命令\n\n```bash\n./TerrariaServer.bin.x86_64\n```\n\n这样是前台，好像不能通过`bg`那种方式放到后台\n\n使用这种方式\n\n```bash\nscreen -S tr 				 # 创建面板\n./TerrariaServer.bin.x86_64  # 启动\nctrl+a+d   					 # 保留当前窗口退出\n```\nscreen相关命令看这个[Linux——让程序在后台运行（四种方法+使用推荐）_linux 后台运行_Pan_peter的博客-CSDN博客](https://blog.csdn.net/Pan_peter/article/details/128875714)\n## 后续\n\n上传本地世界到服务器\n\n在本地找到你的世界，路径一般在`C:\\Users\\you\\Documents\\My Games\\Terraria\\Worlds`\n\n如果找不到就从steam云存档里找，如果你放到云存档中，本地就没了\n![image.png](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556395583.png)\n放到最后一行中指定的目录就行\n\n如果没错的话，这个目录固定是`/root/.local/share/Terraria/Worlds`',1,'2024-03-16 02:33:24','2024-03-16 02:33:24'),('699071efe3ce4df5add4089e1adc5994','### 三级标题<video height=100% width=100% controls autoplay src=\"http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-14/1710409803557.mp4\"></video>',1,'2024-03-14 09:27:16','2024-03-14 09:27:16'),('771690201d694446b653b9f6a9eabbaf','## 到底怎么整呢',1,'2024-01-15 07:53:59','2024-01-15 07:53:59'),('7dd7ab3a48984550a596f483178442f0','123456',1,'2024-03-14 09:18:29','2024-03-14 09:18:29'),('819e9308cdd843ac8b5dddfe4ded70ca','sd',0,'2024-03-14 12:09:09','2024-03-16 02:57:35'),('9d4882479baf4a5e93396ac365636605','### **使用dd命令创建一个swap交换文件**\n\n```bash\ndd if=/dev/zero of=/home/swap bs=1024 count=1024000 	#  这是创建了1G\n```\n\n### **制作为swap格式文件**\n\n```bash\nmkswap /home/swap\n```\n\n### **挂载swap分区**\n\n```bash\nswapon /home/swap\n```\n\n### 开机自动挂载\n\n```bash\nvim /etc/fstab \n```\n\n文件末尾添加\n\n`/home/swap swap swap default 0 0`',1,'2024-03-16 02:11:03','2024-03-16 02:11:03'),('9ddd597bb3ff4426b84f35439f553e94','这是文章内容',1,'2024-01-15 07:48:34','2024-01-15 07:48:34'),('afa22a4ffa7e41de99863f16ab3e5c97','扇形火狠狠登',1,'2024-01-15 07:52:28','2024-01-15 07:52:28'),('e126b32bae5e45bfbb60cff9c2a08a2d','sdf',1,'2024-03-16 02:09:57','2024-03-16 02:09:57'),('f6121ceeb0bf47939e2097760b0d8f40','在此之前需要将路由器固件刷为旧版，此版本为漏洞版本，可以通过url注入实现ssh通信\n\n刷机/救砖工具\n[MIWIFIRepairTool.x86-20240113165004-91abemk.zip](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710555354502.zip)\n旧版原生固件\n[miwifi_rm2100_firmware_d6234_2.0.7-20240113165113-96knvmu.bin](http://127.0.0.1:9002/pplax-blogblog/attach/2024-03-16/1710555757456.bin)\n### url注入，开启ssh\n浏览器访问`192.168.31.1`，输入账号密码登录\n复制`stok`字段\n![image.png](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710555806743.png)\n浏览器访问如下链接，用于开启ssh\n\n```url\nhttp://192.168.31.1/cgi-bin/luci/;stok=<STOK>/api/misystem/set_config_iotdev?bssid=Xiaomi&user_id=longdike&ssid=-h%3B%20nvram%20set%20ssh_en%3D1%3B%20nvram%20commit%3B%20sed%20-i%20\'s%2Fchannel%3D.*%2Fchannel%3D%5C%22debug%5C%22%2Fg\'%20%2Fetc%2Finit.d%2Fdropbear%3B%20%2Fetc%2Finit.d%2Fdropbear%20start%3B\n```\n\n将其中的`<STOK>`修改为复制的stok\n\n返回`{\"code\":0`表示成功\n浏览器访问如下链接，用于修改密码\n\n```text\nhttp://192.168.31.1/cgi-bin/luci/;stok=<STOK>/api/misystem/set_config_iotdev?bssid=Xiaomi&user_id=longdike&ssid=-h%3B%20echo%20-e%20\'admin%5Cnadmin\'%20%7C%20passwd%20root%3B\n```\n\n将其中的`<STOK>`修改为复制的stok\n\n返回`{\"code\":0`表示成功，此时用户名为`root`，密码为`admin`\nssh登录，ip为`192.168.31.1`\n\n登录成功是这样的\n\n![image.png](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710555870249.png)\n### 刷入breed\n\n固件\n[breed-mt7621-xiaomi-r3g-20240113165130-ushhyyp.bin](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710555915412.bin)\n这个文件传入到路由器中\n\n执行如下命令，`/tmp/breed-mt7621-xiaomi-r3g.bin`替换为对应文件\n\n```bash\nmtd -r write /tmp/breed-mt7621-xiaomi-r3g.bin Bootloader\n```\n执行完成后等待自动重启，路由器转为蓝灯后，breed刷入成功\n### 刷入openwrt\n\n固件\n[原版openwrt-20240113170039-shh47yc.7z](http://127.0.0.1:9002/pplax-blogblog/attach/2024-03-16/1710555969929.7z)\n浏览器访问`192.168.1.1`环境变量添加字段`xiaomi.r3g.bootfw`,值设置为2 。\n![image.png](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710555988170.png)\n选择固件更新，进行如下选择，点击上传\n![image.png](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710555998354.png)\n点击更新\n![image.png](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556008158.png)\n![image.png](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556013402.png)\n路由器重启，蓝灯常亮，浏览器访问`192.168.1.1`\n![image.png](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556024026.png)\n账号`root` 密码`admin`\n\n刷完了\n![image.png](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556034983.png)\n### 备份/升级\n![image.png](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556051527.png)\n![image.png](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556055526.png)\n![image.png](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556059632.png)\n### 打开无线网\n\nopenwrt默认关闭wireless，需要手动打开\n![image.png](http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556071535.png)\n### 小米特供版openwrt\n[特供版小米openwrt固件-20240115121122-1fplwn6.7z](http://127.0.0.1:9002/pplax-blogblog/attach/2024-03-16/1710556095550.7z)',1,'2024-03-14 09:16:55','2024-03-14 09:16:55'),('f8552d918da146bda992a1031266dad1','sdf',1,'2024-03-16 02:09:43','2024-03-16 02:09:43');
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
  `type` int DEFAULT '0' COMMENT '类型 0评论 1点赞',
  `content` varchar(1000) DEFAULT NULL COMMENT '评论内容',
  `blog_uid` varchar(32) DEFAULT NULL COMMENT '博客uid',
  `ip` varchar(50) DEFAULT '127.0.0.1' COMMENT '评论的ip',
  `address` varchar(256) DEFAULT '未知' COMMENT '评论地址',
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
INSERT INTO `t_comment` (`uid`, `user_uid`, `to_uid`, `to_user_uid`, `type`, `content`, `blog_uid`, `ip`, `address`, `status`, `create_time`, `update_time`) VALUES ('4736f21b6a3642d19325fdf6adf10353','ef3b67e9b89f40e18df57d6cdf6c001f',NULL,'d288eec502414298a62298aba33a0441',0,'测试评论10','b7dbc212bf304422be9fa22c3f2cf1a2','127.0.0.1','未知',1,'2024-03-16 06:23:04','2024-03-16 07:27:43'),('4b9d77aeb3aa4d91a1f1c8d1e2a6a7ec','ee515eca338c4d358baea8d2c9930a45',NULL,'ef3b67e9b89f40e18df57d6cdf6c001f',0,'测试评论9','aede2547db134413b3f4e8c5407a8abd','127.0.0.1','未知',1,'2024-03-16 06:23:04','2024-03-16 07:27:43'),('4ee481a6092f4c4ea9e9c3e40f3ca0fc','1217367d649c4e99abedf53cf7aadc2a',NULL,'1e8221b961fe4019baefd6e006988a75',0,'测试评论2','211fb300fe7a4a20bfda396a9450b117','127.0.0.1','未知',1,'2024-03-16 06:23:04','2024-03-16 07:27:43'),('63b7784c219546e98a51ed53edade906','a2fc23c7668a4b6386b75584d72498a5',NULL,'ef3b67e9b89f40e18df57d6cdf6c001f',0,'测试评论7','93481c3880774ae59bbfad5ac094ac95','127.0.0.1','未知',1,'2024-03-16 06:23:04','2024-03-16 07:27:43'),('81384ebfeb8b491da1abb7835263b787','103e0baa6d964e3bbb3e3deda94fc0e3',NULL,'1217367d649c4e99abedf53cf7aadc2a',0,'测试评论1','11a4784c4d8b4cb38f8523284364690c','127.0.0.1','未知',1,'2024-03-16 06:23:04','2024-03-16 07:27:43'),('a0a83dc4587e41c2ae56ded654adac13','9f5ba3afd10e41c7a5ada60d0f3e4cd7',NULL,'ef3b67e9b89f40e18df57d6cdf6c001f',1,'博客点赞','93481c3880774ae59bbfad5ac094ac95','127.0.0.1','未知',1,'2024-03-16 06:47:27','2024-03-16 07:27:43'),('a683121685df4d8993d8a3c9f8a5bee8','ee515eca338c4d358baea8d2c9930a45','4736f21b6a3642d19325fdf6adf10353','594a9829162340babf5d3e3340a7eb93',1,'评论点赞','744d407261c742cab437d4e1054b3acd','127.0.0.1','未知',1,'2024-03-16 06:47:27','2024-03-16 07:27:43'),('b07624bdb4d74c9e98d7bf4d972db556','9f5ba3afd10e41c7a5ada60d0f3e4cd7',NULL,'594a9829162340babf5d3e3340a7eb93',0,'测试评论6','744d407261c742cab437d4e1054b3acd','127.0.0.1','未知',1,'2024-03-16 06:23:04','2024-03-16 07:27:43'),('bf1bb6b65e094e658a50a65002d6a826','1e8221b961fe4019baefd6e006988a75','81384ebfeb8b491da1abb7835263b787','1217367d649c4e99abedf53cf7aadc2a',0,'测试评论3','22b5a762375648edabd72c31b74dd4c2','127.0.0.1','未知',1,'2024-03-16 06:23:04','2024-03-16 07:27:43'),('e41366df316f44c7a3e13fb810592a07','d288eec502414298a62298aba33a0441','e92121da35b3499698464ae5440239aa','ee515eca338c4d358baea8d2c9930a45',0,'测试评论8','97e68b9bf2254789bfdbf6fa54310147','127.0.0.1','未知',0,'2024-03-16 06:23:04','2024-03-20 07:16:27'),('e92121da35b3499698464ae5440239aa','784c38743e93482ba79cd2e4075a59d8',NULL,'d288eec502414298a62298aba33a0441',0,'测试评论5','5de884b1e9a74f51a25df84321c165cd','127.0.0.1','未知',1,'2024-03-16 06:23:04','2024-03-16 06:23:04'),('f1292306c9034049b5e5291c5bbbc85c','784c38743e93482ba79cd2e4075a59d8','4b9d77aeb3aa4d91a1f1c8d1e2a6a7ec','d288eec502414298a62298aba33a0441',1,'评论点赞','b7dbc212bf304422be9fa22c3f2cf1a2','127.0.0.1','未知',1,'2024-03-16 06:47:27','2024-03-16 07:27:43');
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
  `title` varchar(128) DEFAULT NULL COMMENT '标题',
  `content` varchar(1000) DEFAULT NULL COMMENT '反馈的内容',
  `picture_uid` varchar(32) DEFAULT NULL COMMENT '附加图片uid',
  `type` int DEFAULT '0' COMMENT '反馈类型 0问题 1缺陷',
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
INSERT INTO `t_feedback` (`uid`, `user_uid`, `title`, `content`, `picture_uid`, `type`, `status`, `create_time`, `update_time`) VALUES ('3b3f83c0af664338ab4be666fb97875a','1217367d649c4e99abedf53cf7aadc2a','厕所','你家厕所漏水了',NULL,1,9,'2024-03-20 08:57:48','2024-03-20 08:57:48'),('4a571b6b5ab346ad88b3b4319563f450','1e8221b961fe4019baefd6e006988a75','算术题','2+656',NULL,0,11,'2024-03-20 08:56:36','2024-03-20 08:56:36'),('ac537eb9d7c7476b871fd61a65d0a29e','594a9829162340babf5d3e3340a7eb93','123','这是一条反馈',NULL,0,8,'2023-11-30 07:52:16','2024-03-20 08:56:26'),('c5589ab67ae546c2b4dfe20065f45e86','784c38743e93482ba79cd2e4075a59d8','yeah~','do you like what you say?',NULL,0,8,'2023-11-30 07:52:16','2024-03-20 08:56:26'),('d0a7dc6f21164d18995ffe1353fde9ed','9f5ba3afd10e41c7a5ada60d0f3e4cd7','aaa','asdasd',NULL,1,10,'2024-03-20 08:56:33','2024-03-20 08:56:33'),('de4a57cc421a46039289cbd89a783f87','ef3b67e9b89f40e18df57d6cdf6c001f','盒！！！','来自河北的皮先生，你好',NULL,0,8,'2023-11-30 07:52:16','2024-03-20 08:56:26');
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
INSERT INTO `t_file_storage` (`uid`, `original_name`, `file_name`, `suffix`, `file_path`, `file_size`, `is_directory`, `is_image`, `storage_mode`, `file_url`, `status`, `create_time`, `update_time`) VALUES ('04522173be879c865507394925d5b388','1707379097298.jpg','1711166986177.jpg','jpg','/link/iconImage/2024-03-23/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/link/iconImage/2024-03-23/1711166986177.jpg',1,'2024-03-23 04:09:46','2024-03-23 04:09:46'),('0618f8e92ff895f650ec3111baa2e304','1707379097298.jpg','1710401335079.jpg','jpg','/spaceBackgroundPicture/2024-03-14/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/spaceBackgroundPicture/2024-03-14/1710401335079.jpg',1,'2024-03-14 07:28:55','2024-03-14 07:28:55'),('10d6a4911cf482565a7ff91ba5a5eb67','开题报告.docx','1710417653763.docx','docx','/blog/attach/2024-03-14/',33503,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-14/1710417653763.docx',1,'2024-03-14 12:00:54','2024-03-14 12:00:54'),('17a2d309fe69700d3ad19d5b7e1ebb17','特供版小米openwrt固件-20240115121122-1fplwn6.7z','1710556095550.7z','7z','/blog/attach/2024-03-16/',51166482,0,1,'minio','http://127.0.0.1:9002/pplax-blogblog/attach/2024-03-16/1710556095550.7z',1,'2024-03-16 02:28:16','2024-03-16 02:28:16'),('1b90b721210405905f9bbe1fe84cc816','1707379097298.jpg','1711167029371.jpg','jpg','/link/iconImage/2024-03-23/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/link/iconImage/2024-03-23/1711167029371.jpg',1,'2024-03-23 04:10:29','2024-03-23 04:10:29'),('1fcf56b34be221550dfd1e91f0054ff2','image.png','1710556051527.png','png','/blog/attach/2024-03-16/',46488,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556051527.png',1,'2024-03-16 02:27:32','2024-03-16 02:27:32'),('201c86df3ceefc994b5f786cd22e5866','image.png','1710555806743.png','png','/blog/attach/2024-03-16/',89186,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710555806743.png',1,'2024-03-16 02:23:27','2024-03-16 02:23:27'),('2084fb0fb59b36f83a560ea08f0ab9d9','OIP-C.jpg','1711179024441.jpg','jpg','/link/iconImage/2024-03-23/',10877,0,1,'minio','http://127.0.0.1:9002/pplax-blog/link/iconImage/2024-03-23/1711179024441.jpg',1,'2024-03-23 07:30:24','2024-03-23 07:30:24'),('21207200fe461b769a64a5c1def36067','image.png','1710555988170.png','png','/blog/attach/2024-03-16/',74812,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710555988170.png',1,'2024-03-16 02:26:28','2024-03-16 02:26:28'),('248c4f14514472fc4731f6adc44bfc33','1645512111007.jpg','1710410104686.jpg','jpg','/blog/attach/2024-03-14/',11909,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-14/1710410104686.jpg',1,'2024-03-14 09:55:05','2024-03-14 09:55:05'),('2b1f29cae25fca20591fc3db6032e90c','image.png','1710556312724.png','png','/blog/attach/2024-03-16/',46341,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556312724.png',1,'2024-03-16 02:31:53','2024-03-16 02:31:53'),('2fe48b4be4866d86fc782d9e5468a118','image.png','1710555870249.png','png','/blog/attach/2024-03-16/',70354,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710555870249.png',1,'2024-03-16 02:24:30','2024-03-16 02:24:30'),('2ff1bcc5643eafd610eac430c7f0e23e','R-C.jpg','1710409534738.jpg','jpg','/blog/attach/file/2024-03-14/',128449,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/file/2024-03-14/1710409534738.jpg',0,'2024-03-14 09:45:35','2024-03-23 13:15:06'),('2ff541f3ecbd782627d1f0f33cf8bfc5','R-C.jpg','1711167036621.jpg','jpg','/link/iconImage/2024-03-23/',128449,0,1,'minio','http://127.0.0.1:9002/pplax-blog/link/iconImage/2024-03-23/1711167036621.jpg',1,'2024-03-23 04:10:37','2024-03-23 04:10:37'),('30389cd33011647211695afe565a72af','TPD33S6R.htm','1710417565046.htm','htm','/blog/attach/2024-03-14/',16012,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-14/1710417565046.htm',1,'2024-03-14 11:59:25','2024-03-14 11:59:25'),('33b39f75a9b7c9683080be7cf580de02','OIP.jpg','1710556194012.jpg','jpg','/blog/coverImage/2024-03-16/',11559,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/coverImage/2024-03-16/1710556194012.jpg',1,'2024-03-16 02:29:54','2024-03-16 02:29:54'),('34b62d510ba35b7d434ca4b718b49951','20231214_1702545284825.mp4','1710403892727.mp4','mp4','/blog/attach/video/2024-03-14/',2613215,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/video/2024-03-14/1710403892727.mp4',1,'2024-03-14 08:11:33','2024-03-14 08:11:33'),('3976f9b5692d6e5958cd84a9500e07d4','1707379097298.jpg','1710401340587.jpg','jpg','/avatar/2024-03-14/',8097,0,1,'minio','http://127.0.0.1:9002/pplax-blog/avatar/2024-03-14/1710401340587.jpg',1,'2024-03-14 07:29:01','2024-03-14 07:29:01'),('3b521ff2783b2ba990fa8e98ba857481','terraria-server-1449-20231124103121-qpks7sp.zip','1710556361825.zip','zip','/blog/attach/2024-03-16/',44628067,0,1,'minio','http://127.0.0.1:9002/pplax-blogblog/attach/2024-03-16/1710556361825.zip',1,'2024-03-16 02:32:42','2024-03-16 02:32:42'),('418eef0b14c069e1f6e562f0f8db440b','20231214_1702545284825.mp4','1710403833323.mp4','mp4','/blog/attach/video/2024-03-14/',2613215,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/video/2024-03-14/1710403833323.mp4',1,'2024-03-14 08:10:33','2024-03-14 08:10:33'),('459bcde4223ac8f04f5c2f01c4fe7838','g.jpg','1710409778701.jpg','jpg','/blog/attach/2024-03-14/',3179,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-14/1710409778701.jpg',1,'2024-03-14 09:49:39','2024-03-14 09:49:39'),('4b4682acb819ef0d4bbb4b850c9187fa','TPD33S6R.htm','1710417557085.htm','htm','/blog/attach/2024-03-14/',16012,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-14/1710417557085.htm',1,'2024-03-14 11:59:17','2024-03-14 11:59:17'),('4c25ecf3b6286efd8804c3d294c5efe3','miwifi_rm2100_firmware_d6234_2.0.7-20240113165113-96knvmu.bin','1710555757456.bin','bin','/blog/attach/2024-03-16/',16424012,0,1,'minio','http://127.0.0.1:9002/pplax-blogblog/attach/2024-03-16/1710555757456.bin',1,'2024-03-16 02:22:37','2024-03-16 02:22:37'),('4c97865b9fd0d0f12cd19cb6caa94ce8','VCG211269969903.jpg','1710401344099.jpg','jpg','/spaceBackgroundPicture/2024-03-14/',267470,0,1,'minio','http://127.0.0.1:9002/pplax-blog/spaceBackgroundPicture/2024-03-14/1710401344099.jpg',1,'2024-03-14 07:29:04','2024-03-14 07:29:04'),('5110d89917386e46346a9232d890e9dd','image.png','1710556395583.png','png','/blog/attach/2024-03-16/',40321,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556395583.png',1,'2024-03-16 02:33:16','2024-03-16 02:33:16'),('51f7bd20169b9ef9e8999dc25a860a1f','VCG211258127993.jpg','1710402211437.jpg','jpg','/spaceBackgroundPicture/2024-03-14/',173714,0,1,'minio','http://127.0.0.1:9002/pplax-blog/spaceBackgroundPicture/2024-03-14/1710402211437.jpg',1,'2024-03-14 07:43:31','2024-03-14 07:43:31'),('5e22bc9ce156e6dd7a68a730213e6e4e','logo11.png','1711178966003.png','png','/link/iconImage/2024-03-23/',122448,0,1,'minio','http://127.0.0.1:9002/pplax-blog/link/iconImage/2024-03-23/1711178966003.png',1,'2024-03-23 07:29:26','2024-03-23 07:29:26'),('61dedf819e64238b779cb804e74887a1','原版openwrt-20240113170039-shh47yc.7z','1710555969929.7z','7z','/blog/attach/2024-03-16/',13566179,0,1,'minio','http://127.0.0.1:9002/pplax-blogblog/attach/2024-03-16/1710555969929.7z',1,'2024-03-16 02:26:10','2024-03-16 02:26:10'),('62f78d05ce62c930195039936e52ef01','image.png','1710555998354.png','png','/blog/attach/2024-03-16/',73683,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710555998354.png',1,'2024-03-16 02:26:38','2024-03-16 02:26:38'),('66e30426da6721fd7bd363e650cee5f3','VCG211258127993.jpg','1710416257373.jpg','jpg','/blog/coverImage/2024-03-14/',173714,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/coverImage/2024-03-14/1710416257373.jpg',1,'2024-03-14 11:37:37','2024-03-14 11:37:37'),('7256421e7ba40630afa29895dc017f2b','20231214_1702545284825.mp4','1710409803557.mp4','mp4','/blog/attach/2024-03-14/',2613215,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-14/1710409803557.mp4',1,'2024-03-14 09:50:04','2024-03-14 09:50:04'),('75eb8d169bd9c0d888552454046ce281','1645512111007.jpg','1710402218993.jpg','jpg','/avatar/2024-03-14/',11909,0,1,'minio','http://127.0.0.1:9002/pplax-blog/avatar/2024-03-14/1710402218993.jpg',1,'2024-03-14 07:43:39','2024-03-14 07:43:39'),('79f4f503001f006bab530330ab2eda3b','MIWIFIRepairTool.x86-20240113165004-91abemk.zip','1710555354502.zip','zip','/blog/attach/2024-03-16/',979690,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710555354502.zip',1,'2024-03-16 02:15:55','2024-03-16 02:15:55'),('7bd960cf90e97de86c39a259a19e0c52','012d365aa88c9fa80121246d981d9a.jpg@1280w_1l_2o_100sh.jpg','1710401400857.jpg','jpg','/blog/coverImage/2024-03-14/',808904,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/coverImage/2024-03-14/1710401400857.jpg',0,'2024-03-14 07:30:01','2024-03-23 13:14:54'),('880dd0399aa4e53e205d7f8022f0402c','logo11.jpg','1711179122572.jpg','jpg','/link/iconImage/2024-03-23/',28705,0,1,'minio','http://127.0.0.1:9002/pplax-blog/link/iconImage/2024-03-23/1711179122572.jpg',1,'2024-03-23 07:32:03','2024-03-23 07:32:03'),('8ee7f11af16723b664a1270f6af0e447','download.jpg','1711198601283.jpg','jpg','/blog/coverImage/2024-03-23/',5389,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/coverImage/2024-03-23/1711198601283.jpg',0,'2024-03-23 12:56:41','2024-03-23 13:15:30'),('913005f81a3a4e3327eb99c6aaf464d6','image.png','1710556059632.png','png','/blog/attach/2024-03-16/',17799,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556059632.png',1,'2024-03-16 02:27:40','2024-03-16 02:27:40'),('917edcc346678a63969a97ec2493eb42','image.png','1710556013402.png','png','/blog/attach/2024-03-16/',36364,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556013402.png',1,'2024-03-16 02:26:53','2024-03-16 02:26:53'),('94cf7b59ee4b5b1df669988e0e7bb5f2','image.png','1710556034983.png','png','/blog/attach/2024-03-16/',86228,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556034983.png',1,'2024-03-16 02:27:15','2024-03-16 02:27:15'),('97471190545c6006bb3ed1eb643df252','download.jpg','1711199747311.jpg','jpg','/blog/coverImage/2024-03-23/',5389,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/coverImage/2024-03-23/1711199747311.jpg',1,'2024-03-23 13:15:47','2024-03-23 13:15:47'),('9821d682cb00a3d9d2fd6bcc8d338c58','image.png','1710556008158.png','png','/blog/attach/2024-03-16/',75173,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556008158.png',1,'2024-03-16 02:26:48','2024-03-16 02:26:48'),('9b5d3a0254649233077d0f760485ee36','lg-max.png','1711179057976.png','png','/link/iconImage/2024-03-23/',21821,0,1,'minio','http://127.0.0.1:9002/pplax-blog/link/iconImage/2024-03-23/1711179057976.png',1,'2024-03-23 07:30:58','2024-03-23 07:30:58'),('a5460c3e7975f602ad0f2faa443fe789','image.png','1710556024026.png','png','/blog/attach/2024-03-16/',31372,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556024026.png',1,'2024-03-16 02:27:04','2024-03-16 02:27:04'),('b19ae3e2ca3dead96fe832d378e84d7a','YTLP4Q3B.htm','1710416382362.htm','htm','/blog/attach/2024-03-14/',16012,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-14/1710416382362.htm',1,'2024-03-14 11:39:42','2024-03-14 11:39:42'),('b427410925d506c0471bf4d1a9c5451c','p.qpic.jpg','1710556519712.jpg','jpg','/blog/coverImage/2024-03-16/',711162,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/coverImage/2024-03-16/1710556519712.jpg',1,'2024-03-16 02:35:20','2024-03-16 02:35:20'),('b5e0ba0af48ad92396b4c3662c6f95eb','logo11.png','1711178949466.png','png','/link/iconImage/2024-03-23/',122448,0,1,'minio','http://127.0.0.1:9002/pplax-blog/link/iconImage/2024-03-23/1711178949466.png',1,'2024-03-23 07:29:09','2024-03-23 07:29:09'),('b77edb6b005dd93a7cea1e8f8dddb735','开题报告.docx','1710417484364.docx','docx','/blog/attach/2024-03-14/',33503,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-14/1710417484364.docx',1,'2024-03-14 11:58:04','2024-03-14 11:58:04'),('bbe6b6284ae0b51d14cdda6a883ea246','image.png','1710556071535.png','png','/blog/attach/2024-03-16/',78160,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556071535.png',1,'2024-03-16 02:27:52','2024-03-16 02:27:52'),('c91665b3c584bf8fdc59dd115e626602','VCG211258127993.jpg','1710407351626.jpg','jpg','/blog/coverImage/2024-03-14/',173714,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/coverImage/2024-03-14/1710407351626.jpg',0,'2024-03-14 09:09:12','2024-03-23 13:15:06'),('e60264374e5a4bf16f61e1518f336b2b','image.png','1710556055526.png','png','/blog/attach/2024-03-16/',93217,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710556055526.png',1,'2024-03-16 02:27:36','2024-03-16 02:27:36'),('ea81a4d6986ee69763689bc51f8cc888','breed-mt7621-xiaomi-r3g-20240113165130-ushhyyp.bin','1710555915412.bin','bin','/blog/attach/2024-03-16/',137250,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-16/1710555915412.bin',1,'2024-03-16 02:25:15','2024-03-16 02:25:15'),('eae0417ea3851a2dd482dc65dfa03f11','lg-max.jpg','1711179126408.jpg','jpg','/link/iconImage/2024-03-23/',8577,0,1,'minio','http://127.0.0.1:9002/pplax-blog/link/iconImage/2024-03-23/1711179126408.jpg',1,'2024-03-23 07:32:06','2024-03-23 07:32:06'),('fd7ccf122e12f04c417f573d13db52c2','20220801091938_9f3d5.thumb.400_0.jpg','1710403068269.jpg','jpg','/blog/attach/2024-03-14/',12844,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/attach/2024-03-14/1710403068269.jpg',1,'2024-03-14 07:57:48','2024-03-14 07:57:48'),('fffddcb3a41e6192073a75252a2dc65e','012d365aa88c9fa80121246d981d9a.jpg@1280w_1l_2o_100sh.jpg','1710409743114.jpg','jpg','/blog/coverImage/2024-03-14/',808904,0,1,'minio','http://127.0.0.1:9002/pplax-blog/blog/coverImage/2024-03-14/1710409743114.jpg',1,'2024-03-14 09:49:03','2024-03-14 09:49:03');
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
  `icon_image_uid` varchar(32) DEFAULT NULL COMMENT '图标文件uid',
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
INSERT INTO `t_link` (`uid`, `title`, `summary`, `url`, `icon_image_uid`, `click_count`, `create_time`, `update_time`, `status`) VALUES ('bb418e0a9d27490bb71972bd8da5afa6','拾壹博客','','https://www.shiyit.com/','880dd0399aa4e53e205d7f8022f0402c',1,'2024-03-23 07:32:22','2024-03-23 07:32:22',4),('dcc01149be71492dabd55821c22f6061','Mybatis-plus','MyBatis-Plus 为简化开发而生','http://mp.baomidou.com/','eae0417ea3851a2dd482dc65dfa03f11',1,'2024-03-23 07:32:26','2024-03-23 07:32:26',4);
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
INSERT INTO `t_menu` (`uid`, `parent_uid`, `type`, `route`, `endpoint`, `title`, `level`, `sort_no`, `icon`, `remarks`, `hidden`, `create_time`, `update_time`, `status`) VALUES ('01c0f7e362744b988e9cbd458ec1bcf3','79cede9382df47a8914ef5697b38d901','button',NULL,'POST:/api/file/blog/coverImage','上传封面图',3,NULL,NULL,NULL,0,'2024-03-13 08:26:09','2024-03-14 07:33:04',1),('04d4f2834b61205c3b6e995e0577af61','f0e9706162f8fcff6611a1f6b56171af','button','','DELETE:/api/admin/siteSetting/{uid}','删除',3,NULL,'','',0,'2024-03-30 10:37:00','2024-03-30 10:37:00',1),('066b59a100225cba71f0ab3327953658','f0e9706162f8fcff6611a1f6b56171af','button','','GET:/api/admin/siteSetting/list','列表',3,NULL,'','',0,'2024-03-30 10:59:19','2024-03-30 10:59:19',1),('079a677ea6c74e8abd752d84ec49f449','4431c6e47f9f4b15a48554669a4067ad','button',NULL,'DELETE:/api/admin/tag','批量删除',3,NULL,NULL,NULL,0,'2024-03-10 06:16:44','2024-03-10 06:16:44',1),('0a099f8edec2a6fa6f2686f441531fea','138952213dced3c8031fc7db58b6c3f6','menu','/admin/website/fileStorage','','文件管理',2,NULL,'el-icon-folder-opened','',0,'2024-03-23 01:59:04','2024-03-23 01:59:04',1),('0ccb4c032a4c4a8f8c798fe5146777cd','24cbb0d7b8ec4801a759e1f6b96a9882','button',NULL,'DELETE:/api/admin/menu','批量删除',3,NULL,NULL,NULL,0,'2024-03-20 11:08:37','2024-03-20 11:08:37',1),('0de0a4cc2db44cd385d3adc55c81d244','7c68ef5629244eb0901738596d2418a6','button',NULL,'GET:/api/admin/user/exist','是否存在',3,NULL,NULL,NULL,0,'2024-03-06 12:53:14','2024-03-06 12:53:14',1),('125573ce7bc24477b4d3c8d3fc1b2da6','24cbb0d7b8ec4801a759e1f6b96a9882','button',NULL,'PUT:/api/admin/menu/{uid}','修改',3,NULL,NULL,NULL,0,'2024-03-20 11:08:37','2024-03-20 11:08:37',1),('12bf5b9c66ce49f29fff1641aa9bcbdb','e26e6dda56894505a3aaf49fa8681e74','button',NULL,'DELETE:/api/admin/comment','批量删除',3,NULL,NULL,NULL,0,'2024-03-16 07:04:02','2024-03-16 07:04:02',1),('138952213dced3c8031fc7db58b6c3f6','','menu','/admin/website','','网站管理',1,4,'el-icon-coordinate','',0,'2024-03-23 01:52:31','2024-03-23 01:52:31',1),('15f3cbadb1fe4f59b56c495509b21582','24cbb0d7b8ec4801a759e1f6b96a9882','button',NULL,'POST:/api/admin/menu','添加',3,NULL,NULL,NULL,0,'2024-03-20 11:08:37','2024-03-20 11:08:37',1),('17351863092649fd94c42820447b57d7','79cede9382df47a8914ef5697b38d901','button',NULL,'PUT:/api/admin/blog/{uid}/promote','置顶',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-03-09 05:56:42',1),('19b411b8f1ad4f45b87768fc882b25cf','7c68ef5629244eb0901738596d2418a6','button',NULL,'POST:/api/file/user/spaceBackgroundPicture','上传空间背景图',3,NULL,NULL,NULL,0,'2024-03-06 05:35:58','2024-03-14 07:41:28',1),('1a76044085234e0c9e4c1a8c50e8473c','5408004e1fd2405db45cb98c42e29d0c','menu','/admin/blog/blogSort',NULL,'分类管理',2,NULL,'el-icon-files',NULL,0,'2024-03-09 04:03:08','2024-03-09 04:03:08',1),('1b592b748a2a414e9c9fec1ce6b2091f','1a76044085234e0c9e4c1a8c50e8473c','button',NULL,'PUT:/api/admin/blogSort/{uid}/promote','置顶',3,NULL,NULL,NULL,0,'2024-03-09 05:56:42','2024-03-09 07:07:34',1),('1c97ee269f849e6fb52c3e34cbcbc327','521f822eb93019592598426c9160f4c8','button','','DELETE:/api/admin/link','批量删除',3,NULL,'','',0,'2024-03-23 02:05:09','2024-03-23 02:05:09',1),('24cbb0d7b8ec4801a759e1f6b96a9882','2780d3f5249b44e0b286b54023b13fcf','menu','/admin/system/menu',NULL,'菜单管理',2,NULL,'el-icon-menu',NULL,0,'2024-03-20 09:10:35','2024-03-20 09:10:35',1),('24d89a797d3845368e64228c5f06d99a','870150df003b4d5f968e272953435c92','button',NULL,'PUT:/api/admin/feedback/{uid}','更新',3,NULL,NULL,NULL,0,'2024-03-20 07:57:47','2024-03-20 07:57:47',1),('2780d3f5249b44e0b286b54023b13fcf',NULL,'menu','/admin/system',NULL,'系统管理',1,5,'el-icon-setting',NULL,0,'2024-03-23 01:52:36','2024-03-23 01:52:36',1),('30b0ca544cd3471180cffcb3a9840c4b','9b34843fa84841c399698ad272f2850b','button',NULL,'GET:/api/admin/role/list','列表',3,NULL,NULL,NULL,0,'2024-03-06 09:35:12','2024-03-06 12:02:54',1),('311e32d0d67abfaf3e794b25f5c085c3','9b34843fa84841c399698ad272f2850b','button','','POST:/api/admin/role','添加',3,NULL,'','',0,'2024-03-21 09:10:28','2024-03-21 09:10:28',1),('35b3ca666b97431ea522e6048aa819a0','7c68ef5629244eb0901738596d2418a6','button',NULL,'GET:/api/admin/user/list','列表',3,NULL,NULL,NULL,0,'2024-03-03 08:26:10','2024-03-06 02:17:47',1),('35ebe57a735f4b0aab8cea6cdfdb149a','7c68ef5629244eb0901738596d2418a6','button',NULL,'POST:/api/file/user/avatar','上传头像',3,NULL,NULL,NULL,0,'2024-03-06 05:35:58','2024-03-14 07:41:28',1),('3e5afdd05057400091ab6f9947070b56','1a76044085234e0c9e4c1a8c50e8473c','button',NULL,'PUT:/api/admin/blogSort/{uid}','修改',3,NULL,NULL,NULL,0,'2024-03-09 07:23:18','2024-03-09 07:23:18',1),('4431c6e47f9f4b15a48554669a4067ad','5408004e1fd2405db45cb98c42e29d0c','menu','/admin/blog/tag',NULL,'标签管理',2,NULL,'el-icon-collection-tag',NULL,0,'2024-03-10 03:52:26','2024-03-10 03:52:26',1),('448b8e594ecf4c749c487954690fc0fd','4431c6e47f9f4b15a48554669a4067ad','button',NULL,'DELETE:/api/admin/tag/{uid}','删除',3,NULL,NULL,NULL,0,'2024-03-10 06:16:44','2024-03-10 06:16:44',1),('45a5eb42e3404270ad8328ae834e3600','7c68ef5629244eb0901738596d2418a6','button',NULL,'GET:/api/admin/user/{uid}/userInfo','用户信息',3,NULL,NULL,NULL,0,'2024-02-22 02:57:17','2024-02-22 03:04:19',1),('521f822eb93019592598426c9160f4c8','138952213dced3c8031fc7db58b6c3f6','menu','/admin/website/link','','友链管理',2,NULL,'el-icon-paperclip','',0,'2024-03-23 02:06:37','2024-03-23 02:06:37',1),('53ad475f230e499c87d114a9543a2f52','24cbb0d7b8ec4801a759e1f6b96a9882','button',NULL,'GET:/api/admin/menu/tree','获得菜单树',3,NULL,NULL,NULL,0,'2024-03-20 09:21:51','2024-03-20 09:21:51',1),('5408004e1fd2405db45cb98c42e29d0c',NULL,'menu','/admin/blog',NULL,'博客管理',1,2,'el-icon-notebook-2',NULL,0,'2024-02-19 11:00:31','2024-03-01 08:35:21',1),('541c1366382aa36dc168a5bed111de33','9b34843fa84841c399698ad272f2850b','button','','PUT:/api/admin/role/{uid}','更新',3,NULL,'','',0,'2024-03-21 09:08:38','2024-03-21 09:08:38',1),('5ab2e6d796f1a6924cdf0b8879d811cb','521f822eb93019592598426c9160f4c8','button','','POST:/api/file/link/iconImage','上传logo',3,NULL,'','',0,'2024-03-23 04:00:52','2024-03-23 04:00:52',1),('5b284b55ab7747f793498def8c776241','79cede9382df47a8914ef5697b38d901','button',NULL,'DELETE:/api/admin/blog/{uid}','删除',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-03-09 02:52:34',1),('5c15e008139c6cd6f56291f8d58cba43','0a099f8edec2a6fa6f2686f441531fea','button','','GET:/api/file/list','列表',3,NULL,'','',0,'2024-03-23 03:57:01','2024-03-23 03:57:01',1),('620862a3c64e4bf788fdae591d15c650','79cede9382df47a8914ef5697b38d901','button',NULL,'POST:/api/file/blog/attach/image','上传博客附件文件（图片）',3,NULL,NULL,NULL,0,'2024-03-13 08:26:09','2024-03-14 07:33:04',1),('63336b9e8660465056b6e7f6108c1baf','9b34843fa84841c399698ad272f2850b','button','','DELETE:/api/admin/role/{uid}','删除',3,NULL,'','',0,'2024-03-21 09:11:00','2024-03-21 09:11:00',1),('69538529b10b4c2dbcd75db0aa7f8391','79cede9382df47a8914ef5697b38d901','button',NULL,'POST:/api/file/blog/attach/video','上传博客附件文件（视频）',3,NULL,NULL,NULL,0,'2024-03-13 08:26:09','2024-03-14 07:33:04',1),('7014a774d0cdaec98c1f88d4dd565347','ab92729e562d490fbc05a35fc83db90d','button','','GET:/api/admin/home/init','初始化',2,NULL,'','',1,'2024-03-24 08:36:31','2024-03-24 08:36:31',1),('7113cc503aea47f6b0af9a472ca43fb6','79cede9382df47a8914ef5697b38d901','button',NULL,'DELETE:/api/admin/blog/{uid}/promote','取消置顶',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-03-09 05:56:42',1),('7413c754169641edaf093ae4f28b561a','e26e6dda56894505a3aaf49fa8681e74','button',NULL,'DELETE:/api/admin/comment/{uid}','删除',3,NULL,NULL,NULL,0,'2024-03-16 07:04:02','2024-03-16 07:04:02',1),('76c352a02ddf4399b0d094b64399f9d4','79cede9382df47a8914ef5697b38d901','button',NULL,'GET:/api/admin/blog/{uid}/content','获得内容',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-03-09 02:52:34',1),('78579650b2ff4c478ad8d53e395bf221','870150df003b4d5f968e272953435c92','button',NULL,'GET:/api/admin/feedback/list','列表',3,NULL,NULL,NULL,0,'2024-03-20 07:57:47','2024-03-20 07:57:47',1),('790311b7afbc4258aec185dfa8040f00','24cbb0d7b8ec4801a759e1f6b96a9882','button',NULL,'DELETE:/api/admin/menu/{uid}','删除',3,NULL,NULL,NULL,0,'2024-03-20 11:08:37','2024-03-20 11:08:37',1),('79cede9382df47a8914ef5697b38d901','5408004e1fd2405db45cb98c42e29d0c','menu','/admin/blog/index',NULL,'博客管理',2,NULL,'el-icon-notebook-2',NULL,0,'2024-02-19 11:03:02','2024-03-01 08:35:21',1),('7a466e5ab90443b3b18fe00b8c84179b','870150df003b4d5f968e272953435c92','button',NULL,'DELETE:/api/admin/feedback/{uid}','删除',3,NULL,NULL,NULL,0,'2024-03-20 07:57:47','2024-03-20 07:57:47',1),('7b475e6fe7ad4382a5750b5666f5290f','4431c6e47f9f4b15a48554669a4067ad','button',NULL,'PUT:/api/admin/tag/{uid}','修改',3,NULL,NULL,NULL,0,'2024-03-10 06:16:44','2024-03-10 06:16:44',1),('7c68ef5629244eb0901738596d2418a6','2780d3f5249b44e0b286b54023b13fcf','menu','/admin/system/user',NULL,'用户管理',2,NULL,'el-icon-user',NULL,0,'2024-02-22 02:57:17','2024-02-27 11:19:40',1),('82c86c8c26974f6cb8e1faa360c240d3','79cede9382df47a8914ef5697b38d901','button',NULL,'GET:/api/admin/blog/list','列表',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-03-09 02:52:34',1),('8357f88168874841ab48d1ec764fea23','1a76044085234e0c9e4c1a8c50e8473c','button',NULL,'DELETE:/api/admin/blogSort/{uid}','删除',3,NULL,NULL,NULL,0,'2024-03-10 02:52:50','2024-03-10 02:52:50',1),('870150df003b4d5f968e272953435c92','de80c63b07ad415abf99bb763f31f58b','menu','/admin/message/feedback',NULL,'反馈管理',2,NULL,'el-icon-warning-outline',NULL,0,'2024-03-16 04:22:14','2024-03-16 04:22:14',1),('88b35441dd7344b9adcf56556deda882','79cede9382df47a8914ef5697b38d901','button',NULL,'POST:/api/admin/blog','添加',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-03-09 02:52:34',1),('8bf225ec6d6a4d15a3b0be9adcddc72b','7c68ef5629244eb0901738596d2418a6','button',NULL,'DELETE:/api/admin/user/{uid}','删除',3,NULL,NULL,NULL,0,'2024-03-07 11:51:05','2024-03-07 11:52:00',1),('90fee32137dd41c28434f951ba7380bf','e26e6dda56894505a3aaf49fa8681e74','button',NULL,'GET:/api/admin/comment/list','列表',3,NULL,NULL,NULL,0,'2024-03-16 07:04:02','2024-03-16 07:04:02',1),('9112d152b8e82e943adef8aa89eef78a','f0e9706162f8fcff6611a1f6b56171af','button','','PUT:/api/admin/siteSetting/{uid}','修改',3,NULL,'','',0,'2024-03-30 10:35:29','2024-03-30 10:35:29',1),('952c4af5dce04e56887810d4ac0aff4d','7c68ef5629244eb0901738596d2418a6','button',NULL,'GET:/api/admin/user/{uid}/role','角色信息',3,NULL,'el-icon-user-solid',NULL,0,'2024-02-23 01:32:49','2024-02-23 01:32:49',1),('9666fa995eba79c1b8ce9f1aebfc38ba','f0e9706162f8fcff6611a1f6b56171af','button','','POST:/api/admin/siteSetting','添加',3,NULL,'','',0,'2024-03-30 10:36:23','2024-03-30 10:36:23',1),('9b34843fa84841c399698ad272f2850b','2780d3f5249b44e0b286b54023b13fcf','menu','/admin/system/role',NULL,'角色管理',2,NULL,'el-icon-user-solid',NULL,0,'2024-03-06 11:47:14','2024-03-06 12:02:54',1),('9b62cc5570ae4ad59b9d8f96d63aad85','7c68ef5629244eb0901738596d2418a6','button',NULL,'DELETE:/api/admin/user','批量删除',3,NULL,NULL,NULL,0,'2024-03-08 11:34:22','2024-03-08 11:47:16',1),('9be63e8cd942f887d2426e038fa9b5e3','521f822eb93019592598426c9160f4c8','button','','DELETE:/api/admin/link/{uid}','删除',3,NULL,'','',0,'2024-03-23 02:06:22','2024-03-23 02:06:22',1),('9c7c1bae35a149efb51766380bfc246c','4431c6e47f9f4b15a48554669a4067ad','button',NULL,'GET:/api/admin/tag/list','列表',3,NULL,NULL,NULL,0,'2024-03-10 04:03:59','2024-03-10 04:03:59',1),('a2f26ceaa846401f912bcc378b96cadd','79cede9382df47a8914ef5697b38d901','button',NULL,'GET:/api/admin/blog/{uid}','详情',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-03-09 02:52:34',1),('ab92729e562d490fbc05a35fc83db90d',NULL,'menu','/admin/dashboard',NULL,'首页',1,1,'el-icon-s-home',NULL,0,'2024-02-19 06:04:36','2024-02-27 11:00:22',1),('b08d46d599e442a8829c7185e8ba8a83','79cede9382df47a8914ef5697b38d901','button',NULL,'PUT:/api/admin/blog/{uid}','修改',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-03-09 02:52:34',1),('b439924170c0f6a01cf4f34ab7c12c23','521f822eb93019592598426c9160f4c8','button','','PUT:/api/admin/link/{uid}','修改',3,NULL,'','',0,'2024-03-23 02:06:07','2024-03-23 02:06:07',1),('bf27986c815f48c08b99ff890359446a','1a76044085234e0c9e4c1a8c50e8473c','button',NULL,'POST:/api/admin/blogSort','添加',3,NULL,NULL,NULL,0,'2024-03-10 02:35:49','2024-03-10 02:35:49',1),('c88807c47075e256b1c3f5f1b9d6f0ea','521f822eb93019592598426c9160f4c8','button','','GET:/api/admin/link/list','列表',3,NULL,'','',0,'2024-03-23 02:04:39','2024-03-23 02:04:39',1),('c8d939d535e24b8fb2981ff520e1d102','79cede9382df47a8914ef5697b38d901','button',NULL,'DELETE:/api/admin/blog','批量删除',3,NULL,NULL,NULL,0,'2024-02-19 06:04:36','2024-03-09 02:52:34',1),('cbd1bf99d6513c5dfcba3e4f29054879','0a099f8edec2a6fa6f2686f441531fea','button','','DELETE:/api/file','批量删除',3,NULL,'','',0,'2024-03-23 03:56:56','2024-03-23 03:56:56',1),('ce89deb669f74549a40b35244e51e2e0','79cede9382df47a8914ef5697b38d901','button',NULL,'POST:/api/file/blog/attach/file','上传博客附件文件',3,NULL,NULL,NULL,0,'2024-03-13 08:26:09','2024-03-14 08:06:22',1),('cfd038df1fee40af9afe2a6cfedf2a92','1a76044085234e0c9e4c1a8c50e8473c','button',NULL,'GET:/api/admin/blogSort/list','列表',3,NULL,NULL,NULL,0,'2024-03-09 04:12:56','2024-03-09 07:07:34',1),('d62b8c053ef2bf7f0038640f8191ac91','0a099f8edec2a6fa6f2686f441531fea','button','','DELETE:/api/file/{uid}','删除',3,NULL,'','',0,'2024-03-30 10:33:27','2024-03-30 10:33:27',1),('d86aa5b07bda8f7df35a208c0936ab50','521f822eb93019592598426c9160f4c8','button','','POST:/api/admin/link','添加',3,NULL,'','',0,'2024-03-23 03:03:37','2024-03-23 03:03:37',1),('d8e0d7b294374e90a2d52259cd527eb1','4431c6e47f9f4b15a48554669a4067ad','button',NULL,'POST:/api/admin/tag','添加',3,NULL,NULL,NULL,0,'2024-03-10 06:16:44','2024-03-10 06:16:44',1),('de80c63b07ad415abf99bb763f31f58b',NULL,'menu','/admin/message',NULL,'消息管理',1,3,'el-icon-message',NULL,0,'2024-03-16 04:19:06','2024-03-16 04:19:06',1),('e26e6dda56894505a3aaf49fa8681e74','de80c63b07ad415abf99bb763f31f58b','menu','/admin/message/comment',NULL,'评论管理',2,NULL,'el-icon-s-comment',NULL,0,'2024-03-16 04:22:14','2024-03-16 04:22:14',1),('e47308d6526241f18eb260ca9fd9e540','7c68ef5629244eb0901738596d2418a6','button',NULL,'PUT:/api/admin/user/{uid}/userInfo','修改',3,NULL,NULL,NULL,0,'2024-03-06 02:17:47','2024-03-06 02:21:58',1),('e639bd6b3be7461ab3c461466bc1cbdb','1a76044085234e0c9e4c1a8c50e8473c','button',NULL,'DELETE:/api/admin/blogSort','批量删除',3,NULL,NULL,NULL,0,'2024-03-10 03:15:06','2024-03-10 03:15:06',1),('e80e3aeab2d940828455162e1d4d9cff','4431c6e47f9f4b15a48554669a4067ad','button',NULL,'GET:/api/admin/tag/exist','是否存在',3,NULL,NULL,NULL,0,'2024-03-10 04:03:59','2024-03-10 04:03:59',1),('e981ac13dcde4cf8a2530098d85c7edb','870150df003b4d5f968e272953435c92','button',NULL,'DELETE:/api/admin/feedback','批量删除',3,NULL,NULL,NULL,0,'2024-03-20 07:57:47','2024-03-20 07:57:47',1),('ecbe5424e41b76598f1baba9155318a7','9b34843fa84841c399698ad272f2850b','button','','DELETE:/api/admin/role','批量删除',3,NULL,'','',0,'2024-03-21 09:11:49','2024-03-21 09:11:49',1),('f0a5d2e7cec84f68bbb508b74fa18eda','1a76044085234e0c9e4c1a8c50e8473c','button',NULL,'DELETE:/api/admin/blogSort/{uid}/promote','取消置顶',3,NULL,NULL,NULL,0,'2024-03-09 06:56:37','2024-03-09 07:07:34',1),('f0e9706162f8fcff6611a1f6b56171af','138952213dced3c8031fc7db58b6c3f6','menu','/admin/website/siteSetting','','站点设置',2,NULL,'el-icon-set-up','',0,'2024-03-30 10:29:51','2024-03-30 10:29:51',1),('f90351dd57664dfbbdcd2389aab7a528','7c68ef5629244eb0901738596d2418a6','button',NULL,'POST:/api/admin/user','添加',3,NULL,NULL,NULL,0,'2024-03-07 05:51:03','2024-03-07 05:52:43',1);
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
INSERT INTO `t_role` (`uid`, `menu_uids`, `role_name`, `summary`, `create_time`, `update_time`, `status`) VALUES ('352af55485b0198631adc4f3f589bb3f','5408004e1fd2405db45cb98c42e29d0c','博客管理员',NULL,'2024-03-22 14:06:53','2024-03-22 14:06:53',1),('cd853c7b475723fb4457aebe30fd39ac','ab92729e562d490fbc05a35fc83db90d,7014a774d0cdaec98c1f88d4dd565347,5408004e1fd2405db45cb98c42e29d0c,79cede9382df47a8914ef5697b38d901,88b35441dd7344b9adcf56556deda882,a2f26ceaa846401f912bcc378b96cadd,b08d46d599e442a8829c7185e8ba8a83,82c86c8c26974f6cb8e1faa360c240d3,01c0f7e362744b988e9cbd458ec1bcf3,ce89deb669f74549a40b35244e51e2e0,c8d939d535e24b8fb2981ff520e1d102,76c352a02ddf4399b0d094b64399f9d4,17351863092649fd94c42820447b57d7,7113cc503aea47f6b0af9a472ca43fb6,69538529b10b4c2dbcd75db0aa7f8391,620862a3c64e4bf788fdae591d15c650,5b284b55ab7747f793498def8c776241,1a76044085234e0c9e4c1a8c50e8473c,8357f88168874841ab48d1ec764fea23,f0a5d2e7cec84f68bbb508b74fa18eda,e639bd6b3be7461ab3c461466bc1cbdb,cfd038df1fee40af9afe2a6cfedf2a92,bf27986c815f48c08b99ff890359446a,1b592b748a2a414e9c9fec1ce6b2091f,3e5afdd05057400091ab6f9947070b56,4431c6e47f9f4b15a48554669a4067ad,9c7c1bae35a149efb51766380bfc246c,7b475e6fe7ad4382a5750b5666f5290f,d8e0d7b294374e90a2d52259cd527eb1,e80e3aeab2d940828455162e1d4d9cff,079a677ea6c74e8abd752d84ec49f449,448b8e594ecf4c749c487954690fc0fd,de80c63b07ad415abf99bb763f31f58b,870150df003b4d5f968e272953435c92,7a466e5ab90443b3b18fe00b8c84179b,e981ac13dcde4cf8a2530098d85c7edb,78579650b2ff4c478ad8d53e395bf221,24d89a797d3845368e64228c5f06d99a,e26e6dda56894505a3aaf49fa8681e74,90fee32137dd41c28434f951ba7380bf,12bf5b9c66ce49f29fff1641aa9bcbdb,7413c754169641edaf093ae4f28b561a,138952213dced3c8031fc7db58b6c3f6,f0e9706162f8fcff6611a1f6b56171af,9666fa995eba79c1b8ce9f1aebfc38ba,9112d152b8e82e943adef8aa89eef78a,066b59a100225cba71f0ab3327953658,04d4f2834b61205c3b6e995e0577af61,0a099f8edec2a6fa6f2686f441531fea,d62b8c053ef2bf7f0038640f8191ac91,cbd1bf99d6513c5dfcba3e4f29054879,5c15e008139c6cd6f56291f8d58cba43,521f822eb93019592598426c9160f4c8,9be63e8cd942f887d2426e038fa9b5e3,d86aa5b07bda8f7df35a208c0936ab50,c88807c47075e256b1c3f5f1b9d6f0ea,b439924170c0f6a01cf4f34ab7c12c23,1c97ee269f849e6fb52c3e34cbcbc327,5ab2e6d796f1a6924cdf0b8879d811cb,2780d3f5249b44e0b286b54023b13fcf,9b34843fa84841c399698ad272f2850b,ecbe5424e41b76598f1baba9155318a7,30b0ca544cd3471180cffcb3a9840c4b,311e32d0d67abfaf3e794b25f5c085c3,541c1366382aa36dc168a5bed111de33,63336b9e8660465056b6e7f6108c1baf,7c68ef5629244eb0901738596d2418a6,9b62cc5570ae4ad59b9d8f96d63aad85,952c4af5dce04e56887810d4ac0aff4d,8bf225ec6d6a4d15a3b0be9adcddc72b,f90351dd57664dfbbdcd2389aab7a528,e47308d6526241f18eb260ca9fd9e540,19b411b8f1ad4f45b87768fc882b25cf,0de0a4cc2db44cd385d3adc55c81d244,35b3ca666b97431ea522e6048aa819a0,45a5eb42e3404270ad8328ae834e3600,35ebe57a735f4b0aab8cea6cdfdb149a,24cbb0d7b8ec4801a759e1f6b96a9882,790311b7afbc4258aec185dfa8040f00,15f3cbadb1fe4f59b56c495509b21582,125573ce7bc24477b4d3c8d3fc1b2da6,0ccb4c032a4c4a8f8c798fe5146777cd,53ad475f230e499c87d114a9543a2f52','超级管理员',NULL,'2024-03-30 10:37:15','2024-03-30 10:37:15',1),('ffca2113713df757e0293c6dfd3b4e32',NULL,'普通用户',NULL,'2024-03-22 13:49:52','2024-03-22 13:49:52',1);
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_site_setting`
--

DROP TABLE IF EXISTS `t_site_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_site_setting` (
  `uid` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '唯一uid',
  `name_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name_zh` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `type` int DEFAULT NULL COMMENT '1基础设置，2页脚徽标，3资料卡，4友链信息',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_site_setting`
--

LOCK TABLES `t_site_setting` WRITE;
/*!40000 ALTER TABLE `t_site_setting` DISABLE KEYS */;
INSERT INTO `t_site_setting` (`uid`, `name_en`, `name_zh`, `value`, `type`, `status`, `create_time`, `update_time`) VALUES ('01fa39bc9b7d406dba23c81b53311330','friendContent','友链页面信息','1',4,1,'2024-03-30 09:30:52','2024-03-30 10:32:27'),('02937d5ac2284c6c8c56e69311dfae0a','avatar','头像','1sdfg',2,1,'2024-03-30 11:37:00','2024-03-30 11:37:00'),('0576ac314e5944138526192c03bc6312','badge','徽标','{\"color\":\"lightgray\",\"subject\":\"CC\",\"title\":\"本站点采用 CC BY 4.0 国际许可协议进行许可\",\"url\":\"https://creativecommons.org/licenses/by/4.0/\",\"value\":\"BY 4.0\"}',3,1,'2024-03-30 09:30:52','2024-03-30 11:25:32'),('151e4619ba63408d9463153745900407','rollText','滚动个签','1asd',2,1,'2024-03-30 11:37:08','2024-03-30 11:37:08'),('25d68439d2d54f71a204488e9a842a9f','badge','徽标','{\"color\":\"orange\",\"subject\":\"CDN\",\"title\":\"jsDelivr 加速静态资源\",\"value\":\"jsDelivr\",\"url\":\"https://www.jsdelivr.com/\"}',3,1,'2024-03-30 12:16:09','2024-03-30 12:16:09'),('2c2823b4dd244b25ba6beec3533aab7a','badge','徽标','{\"title\":\"静态资源托管于 GitHub\",\"url\":\"https://github.com/\",\"subject\":\"OSS\",\"value\":\"GitHub\",\"color\":\"github\"}',3,1,'2024-03-30 12:09:42','2024-03-30 11:37:55'),('3348fcd01fdb4e7ab1d45643c06a5ec6','email','email','1',2,1,'2024-03-30 09:30:51','2024-03-30 10:32:27'),('356dcca07b9f4eab9a28d554b70d918f','bilibili','bilibili','1',2,1,'2024-03-30 09:30:51','2024-03-30 10:32:27'),('381460b8a6c84f7fb0d6bba620242277','beian','ICP备案号','asdfawerggg',1,1,'2024-03-30 11:36:33','2024-03-30 11:36:33'),('42c2c0e4715c4bb0855b3a7f9be3bad0','reward','赞赏码','1',1,1,'2024-03-30 09:30:51','2024-03-30 10:32:31'),('4782651664df438db5d206b86c81ccec','badge','徽标','{\"color\":\"semantic-ui\",\"subject\":\"UI\",\"title\":\"UI 框架 Semantic-UI\",\"value\":\"Semantic-UI\",\"url\":\"https://semantic-ui.com/\"}',3,1,'2024-03-30 12:16:13','2024-03-30 12:16:13'),('47f1f44620d94d04a193de133472360a','telegram','Telegram','1',2,1,'2024-03-30 09:30:51','2024-03-30 10:32:27'),('50870001b722470c966b993570aa302d','playlistId','播放器歌单','1',1,1,'2024-03-30 09:30:51','2024-03-30 10:32:31'),('516ca4b6e01941f7a860b6c03658a73a','badge','徽标','{\"color\":\"\",\"subject\":\"\",\"title\":\"test\",\"value\":\"\",\"url\":\"pplax.xyz\"}',3,0,'2024-03-30 12:17:23','2024-03-30 11:40:24'),('51bf2bec4ef842629584b896d85f1eb3','footerImgTitle','页脚图片标题','1',1,1,'2024-03-30 09:30:51','2024-03-30 10:32:31'),('5aef0f93a92940eda6f48b9950afb44c','badge','徽标','{\"color\":\"blue\",\"subject\":\"Powered\",\"title\":\"由 Spring Boot 强力驱动\",\"value\":\"Spring Boot\",\"url\":\"https://spring.io/projects/spring-boot/\"}',3,1,'2024-03-30 12:16:14','2024-03-30 12:16:14'),('5da509faa8d24ad6ae0dd5ea1a5ffd05','badge','徽标','{\"color\":\"brightgreen\",\"subject\":\"PPLAX Blog\",\"title\":\"本博客已开源于 GitHub\",\"value\":\"Open Source\",\"url\":\"https://github.com/Naccl/NBlog\"}',3,1,'2024-03-30 12:16:03','2024-03-30 12:16:03'),('6ca27a78fd754896982ef4f3256f0537','favorite','自定义','{\"title\":\"\",\"content\":\"\"}',2,0,'2024-03-30 12:00:21','2024-03-30 11:22:45'),('6dfd534a0a7f42a29925a15273c597cd','playlistServer','播放器平台','1',1,1,'2024-03-30 09:30:51','2024-03-30 10:32:31'),('7dec2b953d7d498e9080e8ea4e85ae68','footerImgUrl','页脚图片路径','1',1,1,'2024-03-30 09:30:51','2024-03-30 10:32:31'),('7eed28efae504eba98555ceee84d63ac','favorite','自定义','{\"title\":\"\",\"content\":\"\"}',2,0,'2024-03-30 11:59:36','2024-03-30 11:22:00'),('8e14712910d04ec6ab6bc85f4081e608','qq','QQ','1',2,1,'2024-03-30 09:30:51','2024-03-30 10:32:27'),('a3b3891c570040ebb8d9126065d0d0fa','badge','徽标','{\"title\":\"Vue.js 客户端渲染\",\"url\":\"https://cn.vuejs.org/\",\"subject\":\"SPA\",\"value\":\"Vue.js\",\"color\":\"brightgreen\"}',3,1,'2024-03-30 09:30:52','2024-03-30 11:25:32'),('a563564fd5104af48885ce76828334e8','blogName','博客名称','1',1,1,'2024-03-30 09:30:51','2024-03-30 10:32:31'),('b941eb1e48564264854e68e7770b7465','friendCommentEnabled','友链页面评论开关','1',4,1,'2024-03-30 09:30:52','2024-03-30 10:32:27'),('be0be3303fc64b1a8f890a270eddb45f','github','GitHub','1',2,1,'2024-03-30 09:30:51','2024-03-30 10:32:27'),('c3c385af62b6421ebae420c668bab40d','netease','网易云音乐','1',2,1,'2024-03-30 11:40:46','2024-03-30 11:40:46'),('c572bcf778d948efbefd9a7843983cbf','badge','徽标','{\"title\":\"阿里云提供服务器及域名相关服务\",\"url\":\"https://www.aliyun.com/\",\"subject\":\"VPS & DNS\",\"value\":\"Aliyun\",\"color\":\"blueviolet\"}',3,1,'2024-03-30 09:30:52','2024-03-30 11:25:32'),('d3972e7266b6492192075b34844bef28','copyright','Copyright','1',1,1,'2024-03-30 09:30:51','2024-03-30 10:32:31'),('ddeba76c252f45e9a954b980e48323c6','favorite','自定义','{\"title\":\"\",\"content\":\"\"}',2,0,'2024-03-30 12:00:47','2024-03-30 11:23:12'),('df9b8bd5b13f408faa9cfeb9ccb23b69','name','昵称','1asd',2,1,'2024-03-30 11:42:15','2024-03-30 11:42:15'),('e4b93a2d2ecb43a19a39b67da420fcd2','favorite','自定义','{\"title\":\"\",\"content\":\"\"}',2,0,'2024-03-30 11:57:24','2024-03-30 11:20:47'),('f5981ee5fd7641cfbbfa2327f40c8ca9','webTitleSuffix','网页标题后缀','1',1,1,'2024-03-30 09:30:51','2024-03-30 10:32:31'),('f8a4f6faa0ff470cbb0b3187631bfb63','commentAdminFlag','博主评论标识','1',1,1,'2024-03-30 09:30:51','2024-03-30 10:32:31');
/*!40000 ALTER TABLE `t_site_setting` ENABLE KEYS */;
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
INSERT INTO `t_tag` (`uid`, `name`, `click_count`, `level`, `status`, `create_time`, `update_time`) VALUES ('0227cae131c84b9bac91136caa1d7a44','deemo',0,0,1,'2024-03-16 01:37:08','2024-03-20 11:32:36'),('04612e33964d45d19fa7f4fd61acd807','github',2,0,1,'2024-03-14 10:48:17','2024-03-20 11:32:36'),('1b2c264406fe4f6bb243cb6200b9e9e4','鸟山明',0,0,1,'2024-03-10 10:50:42','2024-03-20 11:32:36'),('25228e8e5d4847d39a4b1466e7403e69','Spring Cloud',1,0,1,'2018-09-21 12:09:50','2024-03-20 11:32:36'),('53c5a0f3142e4f54820315936f78383b','Spring Boot',1,0,1,'2018-09-21 12:11:06','2024-03-20 11:32:36'),('56f0ade4f95543d8a3836b218ff9bbe9','openwrt',0,0,1,'2024-03-16 02:13:01','2024-03-20 11:32:36'),('69f2f1d3578e479e9c53d4a41d5db280','路由器',0,0,1,'2024-03-16 02:13:21','2024-03-20 11:32:36'),('6c499d64a6c84705bb374ac48472e088','宫崎骏',0,0,1,'2024-03-10 10:50:53','2024-03-20 11:32:36'),('7e0e93ea6cdb44ae92e58f48e6496ed7','Java',1,0,1,'2018-09-21 12:12:52','2024-03-20 11:32:36'),('7efee6f74d594d25928ba86bc7adee28','游戏',1,0,1,'2018-09-20 06:51:39','2024-03-20 11:32:36'),('8f3d992d404347dc998f09b99d363714','钢琴',0,0,1,'2024-03-14 10:50:10','2024-03-20 11:32:36'),('91831f7ab9e54cf3bc87d227dac12258','运维',0,0,1,'2024-03-16 04:49:33','2024-03-20 11:32:36'),('957cc32cc28441349bc93c6f8103fd2e','Linux',0,0,1,'2024-03-16 04:49:24','2024-03-20 11:32:36'),('97e3b2d7d7934a8dbae95222e04ef25e','vk克',0,0,1,'2024-03-14 10:49:57','2024-03-20 11:32:36'),('9da13eb143f54c6bb70ecbd5212bde69','怪物',1,0,1,'2018-09-21 12:13:51','2024-03-20 11:32:36'),('a63797683b52488b99bc0f4d8a0545d7','Terraria',0,0,1,'2024-03-16 02:30:51','2024-03-20 11:32:36'),('a9a747d944c24845815356f72723ef8e','前端开发',2,0,1,'2018-09-20 06:51:39','2024-03-20 11:32:36'),('ca928e8718654aa5a802e2f69277b137','生活琐事',2,0,1,'2018-09-21 12:13:41','2024-03-20 11:32:36'),('d3f1652289e34bbfaf567410975003f6','Redmi AC2100',0,0,1,'2024-03-16 02:13:10','2024-03-20 11:32:36'),('d813c892224a4f97a40ca2d745d922c3','mirror night',0,0,1,'2024-03-14 10:50:19','2024-03-20 11:32:36'),('db700b2844d34234aaf4e69657f8e56e','泰拉瑞亚',0,0,1,'2024-03-16 02:30:36','2024-03-20 11:32:36'),('e2c7913050cf4ab9aa92902316aaf075','校园生活',1,0,1,'2018-09-21 12:13:51','2024-03-20 11:32:36'),('f18448a27f6e4320b40cfe51cb9b0a66','龙玉涛',1,0,1,'2024-03-16 01:36:40','2024-03-20 11:32:36'),('f90d3c2fd9434302951130e897a89164','Vue',1,0,1,'2018-09-21 12:12:52','2024-03-20 11:32:36');
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
INSERT INTO `t_user` (`uid`, `role_uid`, `user_info_uid`, `username`, `password`, `salt`, `email`, `is_email_activated`, `mobile`, `is_mobile_activated`, `login_count`, `last_login_time`, `last_login_ip`, `last_login_address`, `status`, `create_time`, `update_time`) VALUES ('103e0baa6d964e3bbb3e3deda94fc0e3','ffca2113713df757e0293c6dfd3b4e32','A7C9AACDC396292E3825639379041442','lucky','$2a$10$HuDCsbGXZTbBgVjXryfHeeObIJHz926y9qQTb/z9GWUFHp.9d5o4i','testSalt','123456@qq.com',1,'18819439264',1,218,'2024-03-08 19:13:37','0:0:0:0:0:0:0:1','未知',1,'2024-01-26 03:36:37','2024-03-06 09:16:32'),('1217367d649c4e99abedf53cf7aadc2a','ffca2113713df757e0293c6dfd3b4e32','d98273375f9c494abd1133ecd485897c','hentai','$2a$10$wvduytt6HJkGOBjrEzc9junp8ZQ0.JqKwZ.9dXhc83M.h3E4z7g7W','IXsdqvmVdyQIt3VAqXsC1LRjaug2HmZMtm0I',NULL,0,NULL,0,1,'2024-03-07 16:17:18','0:0:0:0:0:0:0:1','未知',1,'2024-03-07 08:16:05','2024-03-07 08:16:05'),('1e8221b961fe4019baefd6e006988a75','ffca2113713df757e0293c6dfd3b4e32','92846d9fabb0458b8ec768a9681920cf','lalala','$2a$10$eKEIvGxeJW79Yv0oiUadyedRh4StQfJEDTmA4BR5V2/GIAtakyj32','yBMVtLDraR1vNnwHcNWxglevZLJ257ntnQW6',NULL,0,NULL,0,0,NULL,'127.0.0.1','未知',1,'2024-03-09 02:19:45','2024-03-09 02:29:15'),('594a9829162340babf5d3e3340a7eb93','ffca2113713df757e0293c6dfd3b4e32','e5bae0aca6ae48e98eae9a86318b7be8','node_modules','$2a$10$yBA/O/YQ/91hahF2mw246uj3RziiYPKv.KOUX8il9QyAg/nu0kVvG','pB0NFS94Vbwe1QRO7n5IG6Qjc0svHPhl4if5',NULL,0,NULL,0,0,NULL,'127.0.0.1','未知',1,'2024-03-09 02:10:53','2024-03-09 02:10:53'),('784c38743e93482ba79cd2e4075a59d8','ffca2113713df757e0293c6dfd3b4e32','c4a488a16fde4cfd932570db80a8a0d7','asdfasdf','$2a$10$z91QMCMCBHquaRcH9ENG4O5wR1knBF7I/fN6MLSsm1g85PAcnOyJ2','o7NkDVcs0ghpyU7drcLQ8O9pDHnCiduCgJDB',NULL,0,NULL,0,0,NULL,'127.0.0.1','未知',1,'2024-03-08 10:57:26','2024-03-08 12:21:05'),('9f5ba3afd10e41c7a5ada60d0f3e4cd7','352af55485b0198631adc4f3f589bb3f','f4d7cfd1f18f481c9e8e15effc779b1c','hn123456','$2a$10$gyloF8JPxje0pSbDhO2a2OGHLIhsuMY3sfKdlzh2F2cG9j.VuKuP.','Pu7cKZ4CEf1KgKgqfDKILcCfiu96DiXxNtxH',NULL,0,NULL,0,1,'2024-03-07 19:17:25','0:0:0:0:0:0:0:1','未知',1,'2024-03-07 11:16:31','2024-03-07 11:16:31'),('a2fc23c7668a4b6386b75584d72498a5','ffca2113713df757e0293c6dfd3b4e32','A4A7B8FBB8E9623FA72556F13A97ED5F','testUser','$2a$10$HuDCsbGXZTbBgVjXryfHeeObIJHz926y9qQTb/z9GWUFHp.9d5o4i','testSalt','123466@qq.com',1,'18819439265',1,1,'2024-02-02 12:55:12','202.89.233.101','未知',1,'2024-01-26 03:36:37','2024-03-06 08:53:30'),('d288eec502414298a62298aba33a0441','352af55485b0198631adc4f3f589bb3f','420b5ebd162143c585dc4582c6c0319c','saddness_player','$2a$10$nVPGC75zT/DVURO8KxCBaeEslPYbdbcOK9QCmyYA4hhsLzpKaG5U6','xfsotHHg7E4XMk6ctIHu2GTo8wDZ1UviMOMQ',NULL,0,NULL,0,1,'2024-03-07 16:36:21','0:0:0:0:0:0:0:1','未知',1,'2024-03-07 08:33:40','2024-03-07 08:33:40'),('ee515eca338c4d358baea8d2c9930a45','ffca2113713df757e0293c6dfd3b4e32','D218CD2BB34F0A91565390458A20E871','123321','$2a$10$HuDCsbGXZTbBgVjXryfHeeObIJHz926y9qQTb/z9GWUFHp.9d5o4i','testSalt','123476@qq.com',1,'18819439266',1,1,'2024-02-08 15:24:29','127.0.0.1','未知',1,'2024-01-26 03:36:37','2024-03-06 09:16:32'),('ef3b67e9b89f40e18df57d6cdf6c001f','ffca2113713df757e0293c6dfd3b4e32','d4b0677e07e6487993e3f6c757e6af67','1123','$2a$10$.s0XVIgiJD5mq0tvwajaPOhSmnZ4L9uNJY3PZh0ilZkrnTn1QBNje','DpUkHdX7GNTBsAHe5mJ890jMEh7NZm26nvvk',NULL,0,NULL,0,0,NULL,'127.0.0.1','未知',1,'2024-03-08 10:54:31','2024-03-08 12:21:26'),('f8616ce5aa0c8eeacfa0d997dbdbe0d3','cd853c7b475723fb4457aebe30fd39ac','f6a7dc9f2df3544912c464e7adb9ce32','PPLAX','$2a$10$W49Q3rFeuSBp8efSfyqWZOFTIeNrjELZSGz5sEEW4oaAhT1x8VGpO','testSalt','123486@qq.com',1,NULL,1,325,'2024-03-30 19:25:14','0:0:0:0:0:0:0:1','未知',1,'2024-02-03 07:17:56','2024-03-24 04:29:18');
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

-- Dump completed on 2024-03-30 20:19:01
