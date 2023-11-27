/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 10.1.32-MariaDB : Database - pplax_picture
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`pplax_picture` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `pplax_picture`;

/*Table structure for table `t_admin` */

DROP TABLE IF EXISTS `t_picture`;

CREATE TABLE `t_picture` (
                             `uid` varchar(36) NOT NULL COMMENT '唯一uid',
                             `file_old_name` varchar(255) DEFAULT NULL COMMENT '旧文件名',
                             `pic_name` varchar(255) DEFAULT NULL COMMENT '文件名',
                             `pic_url` varchar(255) DEFAULT NULL COMMENT '文件地址',
                             `pic_expanded_name` varchar(255) DEFAULT NULL COMMENT '文件扩展名',
                             `file_size` int(20) DEFAULT 0 COMMENT '文件大小',
                             `file_sort_uid` varchar(36) DEFAULT NULL COMMENT '文件分类uid',
                             `admin_uid` varchar(36) NOT NULL COMMENT '管理员uid',
                             `user_uid` varchar(36) NOT NULL COMMENT '用户uid',
                             `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '状态',
                             `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
                             `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
                             PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件表';


DROP TABLE IF EXISTS `t_picture_sort`;

CREATE TABLE `t_picture_sort` (
                                  `uid` varchar(36) NOT NULL COMMENT '唯一uid',
                                  `project_name` varchar(255) DEFAULT NULL COMMENT '项目名',
                                  `sort_name` varchar(255) DEFAULT NULL COMMENT '分类名',
                                  `url` varchar(255) DEFAULT NULL COMMENT '分类路径',
                                  `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '状态',
                                  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
                                  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
                                  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件分类表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
