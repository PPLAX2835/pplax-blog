-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: pplax_blog
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
-- Current Database: `pplax_blog`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `pplax_blog` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `pplax_blog`;

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
  `tag_uid` varchar(255) DEFAULT NULL COMMENT '标签uid',
  `click_count` int DEFAULT '0' COMMENT '博客点击数',
  `collect_count` int DEFAULT '0' COMMENT '博客收藏数',
  `picture_uid` varchar(255) DEFAULT NULL COMMENT '标题图片uid',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `user_uid` varchar(32) DEFAULT NULL COMMENT '用户uid',
  `is_original` tinyint(1) DEFAULT '1' COMMENT '是否原创（0:不是 1：是）',
  `articles_part` varchar(255) DEFAULT NULL COMMENT '文章出处',
  `blog_sort_uid` varchar(32) DEFAULT NULL COMMENT '博客分类UID',
  `level` tinyint(1) DEFAULT '0' COMMENT '推荐等级(0:正常)',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='博客表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_blog`
--

LOCK TABLES `t_blog` WRITE;
/*!40000 ALTER TABLE `t_blog` DISABLE KEYS */;
INSERT INTO `t_blog` (`uid`, `title`, `summary`, `blog_content_uid`, `tag_uid`, `click_count`, `collect_count`, `picture_uid`, `status`, `user_uid`, `is_original`, `articles_part`, `blog_sort_uid`, `level`, `create_time`, `update_time`) VALUES ('03dc51d5c94f4c0e80a120b732940b60','别让这些闹心的套路，毁了你的网页设计! ','别让这些闹心的套路，毁了你的网页设计! ','55b4f9f6bc2143c88cff020406cf6c53','7e0e93ea6cdb44ae92e58f48e6496ed7 ',0,0,'\"95ebbd393dc9432a9d65ac0d7b35cd5c,720cc8699ccb484caf1c20a477c66382,dde8800dd17c4b8c9de9169fbb0a49f9,35e191c4bfcd45bca64d3b21533ca6b6,829ad31089404f0eb52baa1a8e19705c\" ',1,'5821462bc29a4570ad80e87f3aa3f02d',1,'蘑菇博客 ','2c93dfab0e754006866f8ed486923a41',0,'2018-09-25 02:34:19','2018-09-25 02:34:19'),('03dc51d5c94f4c0e80a120b732940b61','别让这些闹心的套路，毁了你的网页设计! ','别让这些闹心的套路，毁了你的网页设计! ','e980a926684a4f458f41fac709145b6c','7e0e93ea6cdb44ae92e58f48e6496ed7 ',0,0,'95ebbd393dc9432a9d65ac0d7b35cd5c ',1,'5821462bc29a4570ad80e87f3aa3f02d',1,'蘑菇博客 ','2c93dfab0e754006866f8ed486923a41',0,'2018-09-25 02:34:19','2018-09-25 02:34:19'),('03dc51d5c94f4c0e80a120b732940b63','别让这些闹心的套路，毁了你的网页设计! ','别让这些闹心的套路，毁了你的网页设计! ','08e5c0212a12494dbce645a1228160d7','7e0e93ea6cdb44ae92e58f48e6496ed7 ',0,0,'\"95ebbd393dc9432a9d65ac0d7b35cd5c,720cc8699ccb484caf1c20a477c66382,dde8800dd17c4b8c9de9169fbb0a49f9,35e191c4bfcd45bca64d3b21533ca6b6,829ad31089404f0eb52baa1a8e19705c\" ',1,'5821462bc29a4570ad80e87f3aa3f02d',1,'蘑菇博客 ','2c93dfab0e754006866f8ed486923a41',0,'2018-09-25 02:34:19','2018-09-25 02:34:19'),('07e0a628d677420092aa63fea13f5f21','别让这些闹心的套路，毁了你的网页设计! ','别让这些闹心的套路，毁了你的网页设计! ','dad70576ead6470282047329bf8098ce','a9a747d944c24845815356f72723ef8e ',1,0,'\"95ebbd393dc9432a9d65ac0d7b35cd5c,720cc8699ccb484caf1c20a477c66382,dde8800dd17c4b8c9de9169fbb0a49f9,35e191c4bfcd45bca64d3b21533ca6b6,829ad31089404f0eb52baa1a8e19705c,290a1ee600ce40908604b3a5f7dfe57a\" ',1,'5821462bc29a4570ad80e87f3aa3f02d',1,'蘑菇博客 ','2c93dfab0e754006866f8ed486923a41',0,'2018-09-25 02:33:52','2018-09-25 02:33:52'),('07e0a628d677420092aa63fea13f5f23','别让这些闹心的套路，毁了你的网页设计! ','别让这些闹心的套路，毁了你的网页设计! ','1ec83558a96d436988208b871db2fbc5','a9a747d944c24845815356f72723ef8e ',1,0,'\"95ebbd393dc9432a9d65ac0d7b35cd5c,720cc8699ccb484caf1c20a477c66382,dde8800dd17c4b8c9de9169fbb0a49f9,35e191c4bfcd45bca64d3b21533ca6b6,829ad31089404f0eb52baa1a8e19705c,290a1ee600ce40908604b3a5f7dfe57a\" ',1,'5821462bc29a4570ad80e87f3aa3f02d',1,'蘑菇博客 ','2c93dfab0e754006866f8ed486923a41',0,'2018-09-25 02:33:52','2018-09-25 02:33:52'),('07e0a628d677420092aa63fea13f5f2e','别让这些闹心的套路，毁了你的网页设计! ','别让这些闹心的套路，毁了你的网页设计! ','b39edf2bdc824127877b6ed55e8b91d1','a9a747d944c24845815356f72723ef8e ',1,0,'\"95ebbd393dc9432a9d65ac0d7b35cd5c,720cc8699ccb484caf1c20a477c66382,dde8800dd17c4b8c9de9169fbb0a49f9,35e191c4bfcd45bca64d3b21533ca6b6,829ad31089404f0eb52baa1a8e19705c,290a1ee600ce40908604b3a5f7dfe57a\" ',1,'5821462bc29a4570ad80e87f3aa3f02d',1,'蘑菇博客 ','2c93dfab0e754006866f8ed486923a41',0,'2018-09-25 02:33:52','2018-09-25 02:33:52'),('1a6b52726c6c4c2b954b84fec3e36107','测试博客009 ','测试博客009 ','d60acd0f680747fe84216856f82e2dc3','e2c7913050cf4ab9aa92902316aaf075 ',1,0,'dde8800dd17c4b8c9de9169fbb0a49f9 ',1,'5821462bc29a4570ad80e87f3aa3f02d',1,'蘑菇博客 ','093d8bdd01c84890a928e923d5c235fe',0,'2018-09-27 07:14:27','2018-09-27 07:14:27'),('1caabfedccc44916aef97ea636470111','别让这些闹心的套路，毁了你的网页设计! ',' 如图，要实现上图效果，我采用如下方法：1、首先在数据库模型，增加字段，分别是图片2，图片3。2、增加标签模板，用if，else if 来判断，输出。思路已打开，样式调用就可以多样化啦 ','9656be9f880345218eabc1114122453d','f90d3c2fd9434302951130e897a89164 ',1,0,'720cc8699ccb484caf1c20a477c66382 ',1,'5821462bc29a4570ad80e87f3aa3f02d',0,'蘑菇博客 ','2c93dfab0e754006866f8ed486923a41',0,'2018-09-20 07:51:49','2018-09-20 07:51:49'),('1caabfedccc44916aef97ea636470113','别让这些闹心的套路，毁了你的网页设计! ',' 如图，要实现上图效果，我采用如下方法：1、首先在数据库模型，增加字段，分别是图片2，图片3。2、增加标签模板，用if，else if 来判断，输出。思路已打开，样式调用就可以多样化啦 ','247299eacd324f84a76747eb041afef6','f90d3c2fd9434302951130e897a89164 ',1,0,'720cc8699ccb484caf1c20a477c66382 ',1,'5821462bc29a4570ad80e87f3aa3f02d',0,'蘑菇博客 ','2c93dfab0e754006866f8ed486923a41',0,'2018-09-20 07:51:49','2018-09-20 07:51:49'),('29d9d4f06d6c4fae9d226dd8d2e3bd41','别让这些闹心的套路，毁了你的网页设计! ','别让这些闹心的套路，毁了你的网页设计! ','34f568f544c44dfbbeaef45bb17097aa','e2c7913050cf4ab9aa92902316aaf075 ',1,0,'2cc77c9d08ee41f5a0339af5638d64f7 ',1,'5821462bc29a4570ad80e87f3aa3f02d',1,'蘑菇博客 ','2c93dfab0e754006866f8ed486923a41',0,'2018-09-25 02:31:17','2018-09-25 02:31:17'),('29d9d4f06d6c4fae9d226dd8d2e3bd43','别让这些闹心的套路，毁了你的网页设计! ','别让这些闹心的套路，毁了你的网页设计! ','5964d9ccedeb418b98dd55107cb7669c','e2c7913050cf4ab9aa92902316aaf075 ',1,0,'2cc77c9d08ee41f5a0339af5638d64f7 ',1,'5821462bc29a4570ad80e87f3aa3f02d',1,'蘑菇博客 ','2c93dfab0e754006866f8ed486923a41',0,'2018-09-25 02:31:17','2018-09-25 02:31:17'),('29d9d4f06d6c4fae9d226dd8d2e3bd4b','别让这些闹心的套路，毁了你的网页设计! ','别让这些闹心的套路，毁了你的网页设计! ','7407e362ef63455fb3502f781d730df0','e2c7913050cf4ab9aa92902316aaf075 ',1,0,'2cc77c9d08ee41f5a0339af5638d64f7 ',1,'5821462bc29a4570ad80e87f3aa3f02d',1,'蘑菇博客 ','2c93dfab0e754006866f8ed486923a41',0,'2018-09-25 02:31:17','2018-09-25 02:31:17'),('5821462bc29a4570ad80e87f3aa3f021','别让这些闹心的套路，毁了你的网页设计! ',' 如图，要实现上图效果，我采用如下方法：1、首先在数据库模型，增加字段，分别是图片2，图片3。2、增加标签模板，用if，else if 来判断，输出。思路已打开，样式调用就可以多样化啦！... ','5b6929c495224848aabcacc89c9504f7','e2c7913050cf4ab9aa92902316aaf075 ',12,0,'ee98646f1d684fa4b74f4bff1e1c6f2d ',1,'5821462bc29a4570ad80e87f3aa3f02d',0,'蘑菇博客 ','2c93dfab0e754006866f8ed486923a41',0,'2018-09-20 06:47:53','2018-09-20 06:47:53'),('5821462bc29a4570ad80e87f3aa3f023','别让这些闹心的套路，毁了你的网页设计! ',' 如图，要实现上图效果，我采用如下方法：1、首先在数据库模型，增加字段，分别是图片2，图片3。2、增加标签模板，用if，else if 来判断，输出。思路已打开，样式调用就可以多样化啦！... ','11245f85fffd4de2af74df6671e6c992','e2c7913050cf4ab9aa92902316aaf075 ',12,0,'ee98646f1d684fa4b74f4bff1e1c6f2d ',1,'5821462bc29a4570ad80e87f3aa3f02d',0,'蘑菇博客 ','2c93dfab0e754006866f8ed486923a41',0,'2018-09-20 06:47:53','2018-09-20 06:47:53'),('5821462bc29a4570ad80e87f3aa3f02d','别让这些闹心的套路，毁了你的网页设计! ',' 如图，要实现上图效果，我采用如下方法：1、首先在数据库模型，增加字段，分别是图片2，图片3。2、增加标签模板，用if，else if 来判断，输出。思路已打开，样式调用就可以多样化啦！... ','be0c250507fa4a0eb7f6820e5e4901a3','e2c7913050cf4ab9aa92902316aaf075 ',12,0,'ee98646f1d684fa4b74f4bff1e1c6f2d ',1,'5821462bc29a4570ad80e87f3aa3f02d',0,'蘑菇博客 ','2c93dfab0e754006866f8ed486923a41',0,'2018-09-20 06:47:53','2018-09-20 06:47:53'),('6ed5e0d7081a415ab77d92c0bd991c57','测试 ','测试 ','163d5ddba6a048698db66f3cc20547a1','a9a747d944c24845815356f72723ef8e ',1,0,'720cc8699ccb484caf1c20a477c66382 ',1,'5821462bc29a4570ad80e87f3aa3f02d',1,'蘑菇博客 ','093d8bdd01c84890a928e923d5c235fe',1,'2018-09-27 07:24:46','2018-09-27 07:24:46'),('cc6f6e977a494ad69e88043e6a5a24b1','别让这些闹心的套路，毁了你的网页设计! ','别让这些闹心的套路，毁了你的网页设计! ','ca45c7d64b854819bc25cd4287de6707','ca928e8718654aa5a802e2f69277b137 ',1,0,'ee98646f1d684fa4b74f4bff1e1c6f2d ',1,'5821462bc29a4570ad80e87f3aa3f02d',1,'蘑菇博客 ','093d8bdd01c84890a928e923d5c235fe',0,'2018-09-25 02:33:27','2018-09-25 02:33:27'),('cc6f6e977a494ad69e88043e6a5a24b3','别让这些闹心的套路，毁了你的网页设计! ','别让这些闹心的套路，毁了你的网页设计! ','30cbaa7fc00942e99843ce04a138d635','ca928e8718654aa5a802e2f69277b137 ',1,0,'ee98646f1d684fa4b74f4bff1e1c6f2d ',1,'5821462bc29a4570ad80e87f3aa3f02d',1,'蘑菇博客 ','093d8bdd01c84890a928e923d5c235fe',0,'2018-09-25 02:33:27','2018-09-25 02:33:27'),('cc6f6e977a494ad69e88043e6a5a24bc','别让这些闹心的套路，毁了你的网页设计! ','别让这些闹心的套路，毁了你的网页设计! ','d867a3dfd1404155a7e5277b69e19e87','ca928e8718654aa5a802e2f69277b137 ',1,0,'ee98646f1d684fa4b74f4bff1e1c6f2d ',1,'5821462bc29a4570ad80e87f3aa3f02d',1,'蘑菇博客 ','093d8bdd01c84890a928e923d5c235fe',0,'2018-09-25 02:33:27','2018-09-25 02:33:27');
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
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='博客内容表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_blog_content`
--

LOCK TABLES `t_blog_content` WRITE;
/*!40000 ALTER TABLE `t_blog_content` DISABLE KEYS */;
INSERT INTO `t_blog_content` (`uid`, `content`, `status`, `create_time`, `update_time`) VALUES ('08e5c0212a12494dbce645a1228160d7','<img alt=\"\"\"\" src=\"\"http://localhost:9527/static/img/bi01.6aa7b86.jpg\"\" /><br /> ',1,'2018-09-25 02:34:19','2018-09-25 02:34:19'),('11245f85fffd4de2af74df6671e6c992','<br /> ',1,'2018-09-20 06:47:53','2018-09-20 06:47:53'),('163d5ddba6a048698db66f3cc20547a1','<br /> ',1,'2018-09-27 07:24:46','2018-09-27 07:24:46'),('1ec83558a96d436988208b871db2fbc5','后来空间收费项目也多了，官方漏洞也修复了，加上临近毕业，又要忙着做毕业设计，就没再打理QQ空间。我知道现在的九零后，零零后，你们肯定没看过《一帘幽梦》，那会儿我也是疯狂追剧，喜欢上紫菱，喜欢上她的网站。想看看她的小世界，而我更想学着做一个她那样的网站。那会儿还天真的以为网上真的有她的网站，百度搜了好些天也没有。<br /> ',1,'2018-09-25 02:33:52','2018-09-25 02:33:52'),('247299eacd324f84a76747eb041afef6','<img alt=\"\"\"\" src=\"\"http://localhost:9527/static/img/bi03.f888f1f.jpg\"\" /><br /> ',1,'2018-09-20 07:51:49','2018-09-20 07:51:49'),('30cbaa7fc00942e99843ce04a138d635','工作后进入社会，出去谈业务，遇到一个对网页设计超级感兴趣的人，聊了一下午都还不够，他是完全自学的，做了一个首页宣传他们的产品。他眼里的我就是专业的，总是请教我一些问题。其实我内心特羡慕人家，每次问我，我也似懂非懂的跟人家解决问题，但我还是经常靠百度来搜索他要的答案。他身上那种好学好问的那股劲儿，也成为我迫切想拥有自己的个人博客的一个重要原因。<br /> ',1,'2018-09-25 02:33:27','2018-09-25 02:33:27'),('34f568f544c44dfbbeaef45bb17097aa','要毕业的时候，要交作业了，感觉自己什么都没学会。室友拉着我们去看了她同学做的网站，我们一个个佩服得五体投地，甚至觉得太不可思议了。有难度，又怕自己不会。老师教我们怎么布局，怎么做，并没有教我们右键保存网页。不知道是谁先会了这绝技，然后我们一个个又跟打了鸡血似的，疯狂在网上找网页，右键另存为。然后一个个修改文字，图片。仿佛又回到了那会儿做QQ空间那个时候。拿着copy来，并且精心修改的作品，递交了毕业设计，顺利结业。那会儿还是很蒙，一种云里雾里的感觉，竟有种不知道自己到底是会还是不会的感觉，也就是大家常说的毕业迷茫期。<br /> ',1,'2018-09-25 02:31:17','2018-09-25 02:31:17'),('55b4f9f6bc2143c88cff020406cf6c53','我的个人博客已经建站有8年的时间了，对它的热爱，一直都是只增未减。回想大学读书的那几年，那会儿非常流行QQ空间，我们寝室的室友还经常邀约去学校的网吧做自己的空间。系里有个男生，空间做得非常漂亮，什么悬浮，开场动画，音乐播放器，我们女生羡慕得不得了。还邀约他跟我们一起去通宵弄空间，网上可以找到很多免费的flash资源，还有音乐，那也是第一次接触js，知道在浏览器输入一个地址，修改一下数据，就能调用一些背景出来。然后把自己QQ空间弄得漂漂亮亮的，经常邀约室友来互踩。我记得08年地震，第二天晚上，我们寝室的几个人还淡定的在寝室装扮空间呢！<br /> ',1,'2018-09-25 02:34:19','2018-09-25 02:34:19'),('5964d9ccedeb418b98dd55107cb7669c','<br /> ',1,'2018-09-25 02:31:17','2018-09-25 02:31:17'),('5b6929c495224848aabcacc89c9504f7','做博客不是说做就做的，很多东西我都不懂，也不知道要购买域名还有空间。前期要做的工作还是很多。幸好张园同学，也是我实习期的同事，他会这些，教我网上找免费的虚拟空间，然后就是把自己做的页面上传进去，还给了有一个地址，然后就能访问了。那是第一次接触，也了解了整个网站的制作过程，只可惜买域名还有空间需要费用，还在实习期的我，想想也就算了。虚拟空间毕竟是免费的，没多长时间，做过的网页就不能访问了，又得重新注册，重新上传。<br /> ',1,'2018-09-20 06:47:53','2018-09-20 06:47:53'),('7407e362ef63455fb3502f781d730df0','<img alt=\"\"\"\" src=\"\"http://localhost:9527/static/img/bi02.979b5e2.jpg\"\" /><br /> ',1,'2018-09-25 02:31:17','2018-09-25 02:31:17'),('9656be9f880345218eabc1114122453d','<br /> ',1,'2018-09-20 07:51:49','2018-09-20 07:51:49'),('b39edf2bdc824127877b6ed55e8b91d1','<br /> ',1,'2018-09-25 02:33:52','2018-09-25 02:33:52'),('be0c250507fa4a0eb7f6820e5e4901a3','等自己有一些资金和技术后，我开始买域名和空间。从一开始，我就没想过只是练练手，或者用一段时间就行了。我会一直用下去，所以精心挑选了域名和空间。这些年除了域名没有更换外，后台程序由asp换成了php，空间从西部数据换成万网，也就是现在的阿里云。一步步升级，就想把最好的一面呈现给大家。很多人问我网站速度怎么访问那么快，其实一是网站程序，页面最好是静态页面。每次我写的代码的时候，我都在琢磨怎么减少代码，减少使用div和图片，让html结构简单化，而又不失美观。所以，网站也改版了有好几次。二是空间还有带宽。这个其实很重要，现在备案跟以前比，快很多了，快的话一星期，慢的话顶多二十天。所以，不是因为特着急的话，还是用国内空间。关于国外空间，其实现在阿里云的香港虚拟主机也不错，访问还是上传都比以前好很多了。用它的小伙伴也挺多。延伸阅读 《<a href=\"\"http://www.yangqq.com/jstt/web/2014-01-18/644.html\"\" target=\"\"_blank\"\">我的个人博客之&mdash;&mdash;阿里云空间选择</a>》<br /> ',1,'2018-09-20 06:47:53','2018-09-20 06:47:53'),('ca45c7d64b854819bc25cd4287de6707','<br /> ',1,'2018-09-25 02:33:27','2018-09-25 02:33:27'),('d60acd0f680747fe84216856f82e2dc3','\"<p>本文很长，记录了我博客建站初到现在的过程，还有我从毕业到现在的一个状态，感谢您的阅读，如果你还是学生，也许你能从此文中，找到我们曾经相似的地方。如果你已经工作，有自己的博客，我想，你并没有忘记当初建立个人博客的初衷吧！<br /> ',1,'2018-09-27 07:14:27','2018-09-27 07:14:27'),('d867a3dfd1404155a7e5277b69e19e87','<br /> ',1,'2018-09-25 02:33:27','2018-09-25 02:33:27'),('dad70576ead6470282047329bf8098ce','<br /> ',1,'2018-09-25 02:33:52','2018-09-25 02:33:52'),('e980a926684a4f458f41fac709145b6c','<br /> ',1,'2018-09-25 02:34:19','2018-09-25 02:34:19');
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
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `parent_uid` varchar(32) DEFAULT NULL COMMENT '父级分类uid',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='博客分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_blog_sort`
--

LOCK TABLES `t_blog_sort` WRITE;
/*!40000 ALTER TABLE `t_blog_sort` DISABLE KEYS */;
INSERT INTO `t_blog_sort` (`uid`, `sort_name`, `summary`, `content`, `create_time`, `update_time`, `status`, `parent_uid`) VALUES ('093d8bdd01c84890a928e923d5c235fe','时光轴',NULL,'时光飞逝，机会就在我们眼前，何时找到了灵感，就要把握机遇，我们需要智慧和勇气去把握机会','2018-09-24 09:14:59','2018-09-24 09:14:59',1,NULL),('2c93dfab0e754006866f8ed486923a41','慢生活',NULL,'慢生活，不是懒惰，放慢速度不是拖延时间，而是让我们在生活中寻找到平衡','2018-09-24 08:29:33','2018-09-24 08:29:33',1,NULL);
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
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
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
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
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
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='反馈表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_feedback`
--

LOCK TABLES `t_feedback` WRITE;
/*!40000 ALTER TABLE `t_feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_feedback` ENABLE KEYS */;
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
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
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
-- Table structure for table `t_permission`
--

DROP TABLE IF EXISTS `t_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_permission` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `permission_name` varchar(255) NOT NULL COMMENT '权限名',
  `url` varchar(255) DEFAULT NULL COMMENT '权限url',
  `parent_id` varchar(255) NOT NULL COMMENT '父节点id',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_permission`
--

LOCK TABLES `t_permission` WRITE;
/*!40000 ALTER TABLE `t_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_picture`
--

DROP TABLE IF EXISTS `t_picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_picture` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `file_uid` varchar(32) DEFAULT NULL COMMENT '文件uid',
  `picture_name` varchar(255) DEFAULT NULL COMMENT '图片名',
  `picture_sort_uid` varchar(32) DEFAULT NULL COMMENT '分类uid',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='图片表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_picture`
--

LOCK TABLES `t_picture` WRITE;
/*!40000 ALTER TABLE `t_picture` DISABLE KEYS */;
INSERT INTO `t_picture` (`uid`, `file_uid`, `picture_name`, `picture_sort_uid`, `status`, `create_time`, `update_time`) VALUES ('05a35691c38f4879a910912e6c248562','d87dd2f6ab4f4091b9da4778b8563a13',NULL,'5a281aa35fa9408bb07c2fc9a990701f',1,'2018-09-23 07:01:19','2018-09-23 07:01:19'),('135f9158eaa744ddbd90efe4c4b2f73e','2cc77c9d08ee41f5a0339af5638d64f7',NULL,'dd6728afaec34b64a12f560ca9931a7b',1,'2018-09-25 01:26:53','2018-09-25 01:26:53'),('17b9d1d3e42b4c92ac0a1f92230c3c4e','82f9380f765044edadbe7ab3e441b51b',NULL,'dd6728afaec34b64a12f560ca9931a7b',1,'2018-09-25 01:26:53','2018-09-25 01:26:53'),('1c137ad738794375a914261173b44e60','290a1ee600ce40908604b3a5f7dfe57a',NULL,'dd6728afaec34b64a12f560ca9931a7b',1,'2018-09-25 01:26:53','2018-09-25 01:26:53'),('29d9567f9ad24c598b40f3e4d1560280','829ad31089404f0eb52baa1a8e19705c',NULL,'dd6728afaec34b64a12f560ca9931a7b',1,'2018-09-25 01:26:53','2018-09-25 01:26:53'),('34c1f81a8151493eb3d4705bbfa12533','9593eb7803bd4fb49354582ee13061fa',NULL,'5a281aa35fa9408bb07c2fc9a990701f',1,'2018-09-23 07:01:19','2018-09-23 07:01:19'),('353aee6465bf452bbffdb06b625c7b98','95ebbd393dc9432a9d65ac0d7b35cd5c',NULL,'dd6728afaec34b64a12f560ca9931a7b',1,'2018-09-25 01:26:53','2018-09-25 01:26:53'),('3fda6f00e03e4bfda236e059edbd0e1d','287184b3636048fe94204c02a92280b0',NULL,'5a281aa35fa9408bb07c2fc9a990701f',1,'2018-09-23 07:01:18','2018-09-23 07:01:18'),('4ad6299e44da46379334b0340c1c68de','f53193072c5e493090f4b09358ce8e55',NULL,'5a281aa35fa9408bb07c2fc9a990701f',1,'2018-09-23 07:01:38','2018-09-23 07:01:38'),('50cf276f1f6444f790faae8efb9c1814','a0ac7e9232f248259f4feb1a120f6b39',NULL,'5a281aa35fa9408bb07c2fc9a990701f',1,'2018-09-23 01:31:17','2018-09-23 01:31:17'),('538d3a9c8bc441edb99577c51fc601ea','bd56ecbcf182401995400376d33f2354',NULL,'dd6728afaec34b64a12f560ca9931a7b',0,'2018-09-28 13:40:00','2018-09-28 13:40:00'),('5fbd4b7eef264583a86de8fdd7db77c6','b9a667d581a84f1ea17f80450f92be4b',NULL,'5a281aa35fa9408bb07c2fc9a990701f',1,'2018-09-23 07:01:38','2018-09-23 07:01:38'),('629e2919e7dc4d5dad481f1286d01699','d44135761c504fe1bf3663a056656595',NULL,'5a281aa35fa9408bb07c2fc9a990701f',1,'2018-09-23 07:01:19','2018-09-23 07:01:19'),('6cbf25e03a0c421aa06c4fd30c8ac2fc','9e071786d3a74b919563a40c215c87eb',NULL,'dd6728afaec34b64a12f560ca9931a7b',0,'2018-09-28 12:33:26','2018-09-28 12:33:26'),('6fcef4568c9745559520c1194936be27','02a90e16c9bb4dd8a46190699f64af66',NULL,'5a281aa35fa9408bb07c2fc9a990701f',1,'2018-09-23 07:01:18','2018-09-23 07:01:18'),('705b5f8f6f8a4ab782bad5553bada2d8','992f8c34af46443984a05f1a10b36709',NULL,'5a281aa35fa9408bb07c2fc9a990701f',1,'2018-09-23 07:01:19','2018-09-23 07:01:19'),('708b43bbf8504121911ffc768799584a','edbc7e3c0f9d443da15d650c2ef2f96f',NULL,'dd6728afaec34b64a12f560ca9931a7b',0,'2018-09-23 08:39:13','2018-09-23 08:39:13'),('73f234bc8e8c4ccf8f689cc83638ec6b','ee98646f1d684fa4b74f4bff1e1c6f2d',NULL,'dd6728afaec34b64a12f560ca9931a7b',1,'2018-09-25 01:26:53','2018-09-25 01:26:53'),('82e9e38d8e2b4a038a23ce5ee09055c3','7dde5a06b272440ca0948d1f0fddd0d9',NULL,'5a281aa35fa9408bb07c2fc9a990701f',1,'2018-09-23 03:10:35','2018-09-23 03:10:35'),('8c2c1c450b35471385d26aab9283228f','76a78fa08e4f450ea212731057a7ccac',NULL,'5a281aa35fa9408bb07c2fc9a990701f',1,'2018-09-23 07:01:38','2018-09-23 07:01:38'),('aa82496ce1594a6a95503a1915a0c4ea','35e191c4bfcd45bca64d3b21533ca6b6',NULL,'dd6728afaec34b64a12f560ca9931a7b',1,'2018-09-25 01:26:53','2018-09-25 01:26:53'),('be4d94fb88d94cef9a553432966b7d9c','1c91acdaa1cf4fe2a7f5b8e5ace01397',NULL,'5a281aa35fa9408bb07c2fc9a990701f',1,'2018-09-23 07:01:18','2018-09-23 07:01:18'),('ccac3437e63845a79960f0e82ba2ee67','dde8800dd17c4b8c9de9169fbb0a49f9',NULL,'dd6728afaec34b64a12f560ca9931a7b',1,'2018-09-25 01:26:53','2018-09-25 01:26:53'),('f6f232430b9f49c29b2f9c58b75b33fb','20b89e41ae80455cb245234f7b1649ce',NULL,'dd6728afaec34b64a12f560ca9931a7b',0,'2018-09-23 07:49:27','2018-09-23 07:49:27'),('fd0e4b07046d4ded955f87e0b29ee250','720cc8699ccb484caf1c20a477c66382',NULL,'dd6728afaec34b64a12f560ca9931a7b',1,'2018-09-25 01:26:53','2018-09-25 01:26:53');
/*!40000 ALTER TABLE `t_picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_picture_sort`
--

DROP TABLE IF EXISTS `t_picture_sort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_picture_sort` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `file_uid` varchar(32) DEFAULT NULL COMMENT '文件uid',
  `sort_name` varchar(255) DEFAULT NULL COMMENT '分类名',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `parent_uid` varchar(32) DEFAULT NULL COMMENT '父级分类uid',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='图片分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_picture_sort`
--

LOCK TABLES `t_picture_sort` WRITE;
/*!40000 ALTER TABLE `t_picture_sort` DISABLE KEYS */;
INSERT INTO `t_picture_sort` (`uid`, `file_uid`, `sort_name`, `status`, `create_time`, `update_time`, `parent_uid`) VALUES ('5a281aa35fa9408bb07c2fc9a990701f','290a1ee600ce40908604b3a5f7dfe57a','用户头像',0,'2018-09-20 12:04:47','2018-09-20 12:04:47',NULL),('dd6728afaec34b64a12f560ca9931a7b','35e191c4bfcd45bca64d3b21533ca6b6','博客封面图',1,'2018-09-20 11:34:40','2018-09-20 11:34:40',NULL);
/*!40000 ALTER TABLE `t_picture_sort` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role`
--

DROP TABLE IF EXISTS `t_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_role` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `role_name` varchar(255) NOT NULL COMMENT '角色名',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role`
--

LOCK TABLES `t_role` WRITE;
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` (`uid`, `role_name`, `create_time`, `update_time`, `status`) VALUES ('352af55485b0198631adc4f3f589bb3f','管理员','0000-00-00 00:00:00','0000-00-00 00:00:00',1),('fc5fea59ae7309773f8f70074a27eec4','游客','0000-00-00 00:00:00','0000-00-00 00:00:00',1),('ffca2113713df757e0293c6dfd3b4e32','普通用户','0000-00-00 00:00:00','0000-00-00 00:00:00',1);
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role_permission`
--

DROP TABLE IF EXISTS `t_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_role_permission` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `role_uid` varchar(32) NOT NULL COMMENT '角色id',
  `permission_uid` varchar(32) NOT NULL COMMENT '权限id',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='角色权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role_permission`
--

LOCK TABLES `t_role_permission` WRITE;
/*!40000 ALTER TABLE `t_role_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_tag`
--

DROP TABLE IF EXISTS `t_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_tag` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `content` varchar(1000) DEFAULT NULL COMMENT '标签内容',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `click_count` int DEFAULT '0' COMMENT '标签简介',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='标签表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_tag`
--

LOCK TABLES `t_tag` WRITE;
/*!40000 ALTER TABLE `t_tag` DISABLE KEYS */;
INSERT INTO `t_tag` (`uid`, `content`, `status`, `click_count`, `create_time`, `update_time`) VALUES ('53c5a0f3142e4f54820315936f78383b','Spring Boot',1,1,'2018-09-21 12:11:06','2018-09-21 12:11:06'),('7e0e93ea6cdb44ae92e58f48e6496ed7','Java',1,1,'2018-09-21 12:12:52','2018-09-21 12:12:52'),('a9a747d944c24845815356f72723ef8e','前端开发',1,2,'2018-09-20 06:51:39','2018-09-20 06:51:39'),('ca928e8718654aa5a802e2f69277b137','生活琐事',1,2,'2018-09-21 12:13:41','2018-09-21 12:13:41'),('e2c7913050cf4ab9aa92902316aaf075','校园生活',1,1,'2018-09-21 12:13:51','2018-09-21 12:13:51'),('f90d3c2fd9434302951130e897a89164','Vue',1,1,'2018-09-21 12:09:50','2018-09-21 12:09:50');
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
  `user_name` varchar(255) NOT NULL COMMENT '用户名',
  `pass_word` varchar(32) NOT NULL COMMENT '密码',
  `nick_name` varchar(32) NOT NULL COMMENT '昵称',
  `gender` tinyint unsigned DEFAULT NULL COMMENT '性别(1:男2:女)',
  `picture_uid` varchar(255) DEFAULT NULL COMMENT '个人头像 图片uid',
  `email` varchar(255) NOT NULL COMMENT '邮箱',
  `birthday` date DEFAULT NULL COMMENT '出生年月日',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机',
  `valid_code` varchar(50) DEFAULT NULL COMMENT '邮箱验证码',
  `summary` varchar(200) DEFAULT NULL COMMENT '自我简介最多150字',
  `login_count` int unsigned DEFAULT '0' COMMENT '登录次数',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) DEFAULT '127.0.0.1' COMMENT '最后登录IP',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` (`uid`, `user_name`, `pass_word`, `nick_name`, `gender`, `picture_uid`, `email`, `birthday`, `mobile`, `valid_code`, `summary`, `login_count`, `last_login_time`, `last_login_ip`, `status`, `create_time`, `update_time`) VALUES ('5821462bc29a4570ad80e87f3aa3f02d','admin','21232f297a57a5a743894a0e4a801fc3','PPLAX',1,'测试头像','xzx19950624@qq.com','2018-09-20','1597531973','123465789','测试表情',1,'2018-09-20 14:49:00','127.0.0.1',1,'0000-00-00 00:00:00','0000-00-00 00:00:00');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_visitor`
--

DROP TABLE IF EXISTS `t_visitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_visitor` (
  `uid` varchar(32) NOT NULL COMMENT '唯一uid',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `email` varchar(255) NOT NULL COMMENT '邮箱',
  `login_count` int unsigned DEFAULT '0' COMMENT '登录次数',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) DEFAULT '127.0.0.1' COMMENT '最后登录IP',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='游客表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_visitor`
--

LOCK TABLES `t_visitor` WRITE;
/*!40000 ALTER TABLE `t_visitor` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_visitor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-29 10:34:20
