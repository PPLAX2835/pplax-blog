/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 10.1.35-MariaDB : Database - pplax_file
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`pplax_file` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `pplax_file`;

/*Table structure for table `t_file` */

DROP TABLE IF EXISTS `t_file`;

CREATE TABLE `t_file` (
  `uid` varchar(36) NOT NULL COMMENT '唯一uid',
  `file_old_name` varchar(255) DEFAULT NULL COMMENT '旧文件名',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `file_url` varchar(255) DEFAULT NULL COMMENT '文件地址',
  `file_expanded_name` varchar(255) DEFAULT NULL COMMENT '文件扩展名',
  `file_size` int DEFAULT '0' COMMENT '文件大小',
  `file_sort_uid` varchar(36) DEFAULT NULL COMMENT '文件分类uid',
  `user_uid` varchar(32) DEFAULT NULL COMMENT '用户uid',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `qi_niu_url` varchar(255) DEFAULT NULL COMMENT '七牛云地址',
  `minio_url` varchar(255) DEFAULT NULL COMMENT 'Minio文件URL',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件表';

/*Data for the table `t_file` */

/*Table structure for table `t_file_sort` */

DROP TABLE IF EXISTS `t_file_sort`;

CREATE TABLE `t_file_sort` (
  `uid` varchar(36) NOT NULL COMMENT '唯一uid',
  `project_name` varchar(255) DEFAULT NULL COMMENT '项目名',
  `sort_name` varchar(255) DEFAULT NULL COMMENT '分类名',
  `url` varchar(255) DEFAULT NULL COMMENT '分类路径',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件分类表';

/*Data for the table `t_file_sort` */

insert  into `t_file_sort`(`uid`,`project_name`,`sort_name`,`url`,`status`,`create_time`,`update_time`) values ('a9a747d944c24845815356f72723ef8e','blog','admin','/blog/admin',1,'2020-06-14 21:06:08','2020-06-14 21:06:10'),('a9a747d944c24845815356f72723ef8f','blog','web','/blog/web',1,'2020-06-14 21:06:12','2020-06-14 21:06:13');

/*Table structure for table `t_network_disk` */

DROP TABLE IF EXISTS `t_network_disk`;

CREATE TABLE `t_network_disk` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `user_uid` varchar(32) NOT NULL COMMENT '用户uid',
  `extend_name` varchar(255) DEFAULT NULL COMMENT '扩展名',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `file_path` varchar(255) DEFAULT NULL COMMENT '文件路径',
  `file_size` bigint NOT NULL COMMENT '文件大小',
  `is_directory` int NOT NULL COMMENT '是否目录',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `local_url` varchar(255) DEFAULT NULL COMMENT '本地文件URL',
  `qi_niu_url` varchar(255) DEFAULT NULL COMMENT '七牛云URL',
  `file_old_name` varchar(255) DEFAULT NULL COMMENT '上传前文件名',
  `minio_url` varchar(255) DEFAULT NULL COMMENT 'Minio文件URL',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网盘文件表';

/*Data for the table `t_network_disk` */

insert  into `t_network_disk`(`uid`,`user_uid`,`extend_name`,`file_name`,`file_path`,`file_size`,`is_directory`,`status`,`create_time`,`update_time`,`local_url`,`qi_niu_url`,`file_old_name`,`minio_url`) values ('056dda827b16a16243662c4978effa25','1f01cd1d2f474743b241d74008b12333','jpg','1603526874546.jpg','/',165398,0,0,'2020-10-24 16:07:57','2020-10-24 16:39:33','/blog/admin/jpg/2020/10/24/1603526876655.jpg',NULL,'1B8EDA6B1BAEAF07F096426CFE19BE03.jpg',NULL),('08a2187ad4cd8f1bbda70556cb0d3ffa','7621746caa93ce605e2de7143a3787b5','conf','1602227199934.conf','/',3656,0,0,'2020-10-09 15:06:50','2020-10-24 16:07:27','/blog/admin/conf/2020/10/9/1602227199934.conf',NULL,'nginx.conf',NULL),('0ff0121e34566d565809b317c9534d3b','7621746caa93ce605e2de7143a3787b5','jpg','1602230568787.jpg','/',64849,0,0,'2020-10-09 16:02:49','2020-10-09 16:02:57','/blog/admin/jpg/2020/10/9/1602230568787.jpg',NULL,'1542120089082.jpg',NULL),('119e8afd6a2ee562a07c841599a7f82b','1f01cd1d2f474743b241d74008b12333','png','1603247134604.png','/新建文件/',1221,0,0,'2020-10-21 10:25:34','2020-10-21 11:17:36','/blog/admin/png/2020/10/21/1603247134910.png','9306bbd89d9f46a497c074baa275947b','0ad0279d0ebe4798b06af173811ddafd_1542003744990_cytUIrElb.png',NULL),('12f3c4e4c35f494b1915fc887aba22de','1f01cd1d2f474743b241d74008b12333','png','1603250261548.png','/新建文件/',1221,0,0,'2020-10-21 11:17:41','2020-10-21 11:20:56','/blog/admin/png/2020/10/21/1603250261743.png','146723b99265428f814082dae796d114','0ad0279d0ebe4798b06af173811ddafd_1542003744990_cytUIrElb.png',NULL),('198defbd9a454ddcf11dfd8ac333f8e2','1f01cd1d2f474743b241d74008b12333','png','1603263428895.png','/新建文件/',1221,0,0,'2020-10-21 14:57:10','2020-10-21 16:03:45','/blog/admin/png/2020/10/21/1603263429228.png','7ce2139271264441a39bc569e214d7f2','0ad0279d0ebe4798b06af173811ddafd_1542003744990_cytUIrElb.png',NULL),('22cd097fb80882e204ada243fa4f63a0','1f01cd1d2f474743b241d74008b12333','zip','1602300634698.zip','/',7623985,0,0,'2020-10-10 11:30:34','2020-10-10 14:38:52','/blog/admin/zip/2020/10/10/1602300634698.zip',NULL,'资源图标.zip',NULL),('2d291997ef392d8929a619fc6063eb91','1f01cd1d2f474743b241d74008b12333',NULL,'测试文件夹','/',0,1,1,'2020-11-01 15:48:37','2020-11-01 15:48:37',NULL,NULL,'测试文件夹',NULL),('32c44f4c95eb1c117b227494fc1dcfbf','1f01cd1d2f474743b241d74008b12333','png','1603528190200.png','/',1221,0,0,'2020-10-24 16:29:54','2020-10-24 16:39:48','/blog/admin/png/2020/10/24/1603528193725.png',NULL,'0ad0279d0ebe4798b06af173811ddafd_1542003744990_cytUIrElb.png','/mogublog/1603528190470.png'),('3940b1292d1947c8a1524be383976062','1f01cd1d2f474743b241d74008b12333','zip','1602311936541.zip','/',7623985,0,0,'2020-10-10 14:38:56','2020-10-24 16:07:27','/blog/admin/zip/2020/10/10/1602311936541.zip',NULL,'资源图标.zip',NULL),('3ee38a34162125158ed2943022ab5d06','1f01cd1d2f474743b241d74008b12333','png','1603267439981.png','/新建文件/',1221,0,0,'2020-10-21 16:04:01','2020-10-24 16:03:34','/blog/admin/png/2020/10/21/1603267440231.png','64ac115de81546ea8a7f98cb886800ad','0ad0279d0ebe4798b06af173811ddafd_1542003744990_cytUIrElb.png',NULL),('4618449f80d70c232a09770a31aff26c','1f01cd1d2f474743b241d74008b12333',NULL,'文档','/新建文件/音乐/',0,1,0,'2020-10-08 10:31:23','2020-10-21 16:03:44',NULL,NULL,'文档',NULL),('49d59164dda17133ab4bbbb9e5651a8c','1f01cd1d2f474743b241d74008b12333',NULL,'图片','/',0,1,0,'2020-10-08 10:28:52','2020-10-24 16:04:23',NULL,NULL,'图片',NULL),('4faaa30bc8d1c6967bba6d31ff43f432','1f01cd1d2f474743b241d74008b12333','md','1602124290652.md','/图片/null/',6993,0,0,'2020-10-08 10:31:30','2020-10-24 16:04:23','/blog/admin/md/2020/10/8/1602124290652.md',NULL,'README.md',NULL),('699996f460e4ecfc3bd89a244072f948','1f01cd1d2f474743b241d74008b12333','png','1603246966000.png','/图片/',127023,0,0,'2020-10-21 10:23:00','2020-10-24 16:04:23','/blog/admin/png/2020/10/21/1603246975300.png','6b507c40a7ad41bb972f5655d5d5ff07','8NO}47LH_~]0X}X]PHXDSY7.png',NULL),('7411bee45eeedf9a1d990d872e877ca7','7621746caa93ce605e2de7143a3787b5','jpg','1602230002120.jpg','/',165398,0,0,'2020-10-09 15:53:22','2020-10-24 16:07:13','/blog/admin/jpg/2020/10/9/1602230002120.jpg',NULL,'1B8EDA6B1BAEAF07F096426CFE19BE03.jpg',NULL),('78d3fe63c5fc340ea9b6ca7b0a2b6fae','1f01cd1d2f474743b241d74008b12333','png','1603250430897.png','/新建文件/',1221,0,0,'2020-10-21 11:20:47','2020-10-21 16:03:45','/blog/admin/png/2020/10/21/1603250447021.png','846e9e878e1c4dc5b2d39d14333fc09d','0ad0279d0ebe4798b06af173811ddafd_1542003744990_cytUIrElb.png',NULL),('82f03980f6daedae0255c7115ba25848','1f01cd1d2f474743b241d74008b12333','md','1602124372500.md','/新建文件/音乐/文档/',6993,0,0,'2020-10-08 10:32:52','2020-10-21 16:03:44','/blog/admin/md/2020/10/8/1602124372500.md',NULL,'README.md',NULL),('8636a2e45cf961d61965cb8cd9e4a91f','1f01cd1d2f474743b241d74008b12333','png','1602311917417.png','/',1221,0,0,'2020-10-10 14:38:38','2020-10-24 16:07:27','/blog/admin/png/2020/10/10/1602311917417.png',NULL,'0ad0279d0ebe4798b06af173811ddafd_1542003744990_cytUIrElb.png',NULL),('86fea1d272b54c47fc76a327adef1725','7621746caa93ce605e2de7143a3787b5','txt','1602227850983.txt','/',368,0,0,'2020-10-09 15:17:31','2020-10-24 16:07:27','/blog/admin/txt/2020/10/9/1602227850983.txt',NULL,'Git提交说明.txt',NULL),('8a868cc1bac4dd28314c46f7936ea6aa','1f01cd1d2f474743b241d74008b12333','jpg','1603529235667.jpg','/',165398,0,0,'2020-10-24 16:47:17','2020-10-24 16:47:37','/blog/admin/jpg/2020/10/24/1603529236906.jpg',NULL,'1B8EDA6B1BAEAF07F096426CFE19BE03.jpg','/mogublog/1603529235914.jpg'),('8e174187411d633a32f7b94601047180','7621746caa93ce605e2de7143a3787b5','pdf','1602227168350.pdf','/',29694078,0,0,'2020-10-09 15:06:09','2020-10-24 16:07:28','/blog/admin/pdf/2020/10/9/1602227168350.pdf',NULL,'深入理解Java虚拟机第三版.pdf',NULL),('93974ad79c85f788de99484ecb07b68d','1f01cd1d2f474743b241d74008b12333','jpg','1603250217392.jpg','/新建文件/',55513,0,0,'2020-10-21 11:16:57','2020-10-21 11:17:34','/blog/admin/jpg/2020/10/21/1603250217651.jpg','b34bb927e2a340f38997ecb837fe8a22','1001.jpg',NULL),('956fe0e33b9aab4a1ad2b8c03357e9f2','1f01cd1d2f474743b241d74008b12333',NULL,'新建文件','/',0,1,0,'2020-10-08 10:43:05','2020-10-24 16:04:23',NULL,NULL,'新建文件',NULL),('9594bdd48024da0b77c7ede789413b99','1f01cd1d2f474743b241d74008b12333','jpg','1603529373594.jpg','/',165398,0,0,'2020-10-24 16:49:33','2020-10-24 16:49:58','/blog/admin/jpg/2020/10/24/1603529373908.jpg',NULL,'1B8EDA6B1BAEAF07F096426CFE19BE03.jpg','/mogublog/1603529373602.jpg'),('9e14f7474949a0a34d70aa1a575e8123','1f01cd1d2f474743b241d74008b12333','md','1604216928618.md','/测试文件夹/',7780,0,1,'2020-11-01 15:48:48','2020-11-01 15:48:48','/blog/admin/md/2020/11/1/1604216928618.md',NULL,'索引.md',NULL),('9e646cdc11a365183b465cd8dddb23c9','1f01cd1d2f474743b241d74008b12333','png','1602124561214.png','/',38524,0,0,'2020-10-08 10:36:01','2020-10-24 16:07:28','/blog/admin/png/2020/10/8/1602124561214.png',NULL,'image-20201005103930483.png',NULL),('9f5b0a15e0ef73ff5f15cad774c4df6d','1f01cd1d2f474743b241d74008b12333','jpg','1603527339976.jpg','/',165398,0,0,'2020-10-24 16:16:44','2020-10-24 16:41:06','/blog/admin/jpg/2020/10/24/1603527401658.jpg',NULL,'1B8EDA6B1BAEAF07F096426CFE19BE03.jpg','/mogublog/1603527340207.jpg'),('a1cea13a5384aa3fb96aa33a3f9cdd78','1f01cd1d2f474743b241d74008b12333','webp','1602300459038.webp','/',20750,0,0,'2020-10-10 11:27:39','2020-10-24 16:07:27','/blog/admin/webp/2020/10/10/1602300459038.webp',NULL,'ideaBackground.webp',NULL),('aa0a18c8a06821798f035523489ea968','1f01cd1d2f474743b241d74008b12333','png','1602124431797.png','/图片/',38524,0,0,'2020-10-08 10:33:51','2020-10-08 10:35:50','/blog/admin/png/2020/10/8/1602124431797.png',NULL,'image-20201005103930483.png',NULL),('b86320941469b4edcc506d280c9adc6c','7621746caa93ce605e2de7143a3787b5','txt','1602228649899.txt','/',368,0,0,'2020-10-09 15:30:50','2020-10-24 16:07:27','/blog/admin/txt/2020/10/9/1602228649899.txt',NULL,'Git提交说明.txt',NULL),('c1f133065ae655199eec9ffcce746a9c','1f01cd1d2f474743b241d74008b12333',NULL,'音乐','/新建文件/',0,1,0,'2020-10-08 10:28:59','2020-10-21 16:03:43',NULL,NULL,'音乐',NULL),('d0518e8c7b28249c5ca70d084377a7c3','1f01cd1d2f474743b241d74008b12333','jpg','1603250161115.jpg','/新建文件/',47954,0,0,'2020-10-21 11:16:02','2020-10-21 11:17:35','/blog/admin/jpg/2020/10/21/1603250161541.jpg','616e80adc81a411fa771576488626aa4','1000.jpg',NULL),('d22f87d34acf79ecf5dad1b3bb0f1122','1f01cd1d2f474743b241d74008b12333','png','1603246536487.png','/新建文件/',1221,0,0,'2020-10-21 10:15:36','2020-10-21 11:17:36','/blog/admin/png/2020/10/21/1603246536819.png','a17f077be64b4651a5974d8ebcc42cf1','0ad0279d0ebe4798b06af173811ddafd_1542003744990_cytUIrElb.png',NULL),('d4777e0574b8d310d09dea057d89dcfb','1f01cd1d2f474743b241d74008b12333','webp','1603250952562.webp','/新建文件/',20750,0,0,'2020-10-21 11:29:14','2020-10-21 16:03:45','/blog/admin/webp/2020/10/21/1603250954894.webp','588874c9141947ed99b46ee5ed2470cb','ideaBackground.webp',NULL),('d5f4350890b8eab5cea719654ad743e9','1f01cd1d2f474743b241d74008b12333','png','1602124298666.png','/新建文件/音乐/',38524,0,0,'2020-10-08 10:31:38','2020-10-08 10:43:13','/blog/admin/png/2020/10/8/1602124298666.png',NULL,'image-20201005103930483.png',NULL),('e273feefb0e3e28a05869cf98bab1cf5','7621746caa93ce605e2de7143a3787b5','txt','1602228683888.txt','/',11485,0,0,'2020-10-09 15:31:34','2020-10-24 16:07:27','/blog/admin/txt/2020/10/9/1602228683888.txt',NULL,'SpringCloud面试题及答案.txt',NULL),('eb5b990f57c1a4ea0de70d6bd8e8a22c','1f01cd1d2f474743b241d74008b12333','jpg','1603527379324.jpg','/',165398,0,0,'2020-10-24 16:16:40','2020-10-24 16:41:14','/blog/admin/jpg/2020/10/24/1603527379672.jpg',NULL,'1B8EDA6B1BAEAF07F096426CFE19BE03.jpg','/mogublog/1603527379327.jpg'),('eee8ddbf62c2b9f66960a6e64be634be','1f01cd1d2f474743b241d74008b12333','jpg','1603527243948.jpg','/',165398,0,0,'2020-10-24 16:14:38','2020-10-24 16:39:35','/blog/admin/jpg/2020/10/24/1603527251792.jpg',NULL,'1B8EDA6B1BAEAF07F096426CFE19BE03.jpg',NULL),('fb38361ab5fcb6d3aa901a169609d257','1f01cd1d2f474743b241d74008b12333','jpg','1603527361497.jpg','/',165398,0,0,'2020-10-24 16:16:05','2020-10-24 16:41:38','/blog/admin/jpg/2020/10/24/1603527363806.jpg',NULL,'1B8EDA6B1BAEAF07F096426CFE19BE03.jpg','/mogublog/1603527361503.jpg');

/*Table structure for table `t_storage` */

DROP TABLE IF EXISTS `t_storage`;

CREATE TABLE `t_storage` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `user_uid` varchar(32) NOT NULL COMMENT '用户uid',
  `storage_size` bigint NOT NULL,
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `max_storage_size` bigint DEFAULT '0' COMMENT '最大容量大小',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储信息表';

/*Data for the table `t_storage` */

insert  into `t_storage`(`uid`,`user_uid`,`storage_size`,`status`,`create_time`,`update_time`,`max_storage_size`) values ('1f01cd1d2f474743b241d74008b12334','1f01cd1d2f474743b241d74008b12333',7780,1,'2020-07-11 22:05:48','2020-12-11 16:11:23',10485760),('2bb52a208f2ed8592cfe2239c2d5350e','0954693eb7ebaa0b693d3c787fb8bab7',0,1,'2020-10-09 16:19:43','2020-10-10 11:18:46',524288000),('f0c2c2021a8021d079d1ed70017bb2d8','7621746caa93ce605e2de7143a3787b5',177619,1,'2020-10-09 15:17:01','2020-12-11 16:14:40',524288000),('f0c2c2021a8021d079d1ed70017bb2d9','72346fe9bfc754023e3495f2614bf18f',0,1,'2020-10-09 10:17:41','2020-10-09 10:17:41',50000);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
