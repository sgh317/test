/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.6.20-log : Database - wgqc
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wgqc` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `wgqc`;

/*Table structure for table `WGQC_Ad` */

DROP TABLE IF EXISTS `WGQC_Ad`;

CREATE TABLE `WGQC_Ad` (
  `Ad_id` varchar(200) NOT NULL COMMENT '广告ID',
  `ad_picture` varchar(200) NOT NULL COMMENT '广告图片',
  `ad_location` varchar(100) NOT NULL COMMENT '广告位置',
  `ad_href` varchar(200) DEFAULT NULL COMMENT '链接地址',
  `ad_status` varchar(100) NOT NULL COMMENT '状态',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  PRIMARY KEY (`Ad_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `WGQC_Ad` */

LOCK TABLES `WGQC_Ad` WRITE;

insert  into `WGQC_Ad`(`Ad_id`,`ad_picture`,`ad_location`,`ad_href`,`ad_status`,`create_time`) values ('000001','','水果','www.baidu.com','N','2016-10-08 09:26:29.829948'),('000002','','水果','www.baidu.com','Y','2016-09-30 11:48:36.777550'),('000003','','生鲜','www.baidu.com','Y','2016-09-30 11:48:41.220540'),('185eb43b6b0949bcb689864e541878c8','/mwbase/apptemp/images/ad/20161007154410506.jpg','水果','www.baidu.com','N','2016-10-07 15:44:15.000000'),('59ee5d6166dd49cab45839a97c8ca9da','/mwbase/apptemp/images/ad/20161007114004105.jpg','水果','www.baidu.com','N','2016-10-07 15:37:01.685103');

UNLOCK TABLES;

/*Table structure for table `WGQC_Address` */

DROP TABLE IF EXISTS `WGQC_Address`;

CREATE TABLE `WGQC_Address` (
  `Address_id` varchar(200) NOT NULL COMMENT '地址id',
  `user_id` varchar(200) NOT NULL COMMENT '用户ID',
  `dename` varchar(100) NOT NULL COMMENT '接收人名称',
  `province` varchar(100) DEFAULT NULL COMMENT '省份',
  `city` varchar(100) NOT NULL COMMENT '城市',
  `area` varchar(100) NOT NULL COMMENT '区（县）',
  `addressdetail` varchar(400) NOT NULL COMMENT '接收人详细地址',
  `mobile` varchar(50) NOT NULL COMMENT '手机',
  `defaultaddr` varchar(10) NOT NULL COMMENT '是否默认(1：默认 0：非默认)',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  PRIMARY KEY (`Address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `WGQC_Address` */

LOCK TABLES `WGQC_Address` WRITE;

insert  into `WGQC_Address`(`Address_id`,`user_id`,`dename`,`province`,`city`,`area`,`addressdetail`,`mobile`,`defaultaddr`,`create_time`) values ('000001','000001','周瑜','江苏','扬州','广陵区','金盛大厦','18168007885','','2016-08-26 12:00:03.000000'),('000002','000002','诸葛亮','江苏','扬州','轩辕区','万科大厦','18268004562','','2016-05-26 12:00:03.000000'),('000003','000003','孙权','江苏','扬州','大和区','恒大大厦','18520314152','','2016-02-26 12:00:03.000000');

UNLOCK TABLES;

/*Table structure for table `WGQC_Catalog` */

DROP TABLE IF EXISTS `WGQC_Catalog`;

CREATE TABLE `WGQC_Catalog` (
  `cid` varchar(200) NOT NULL COMMENT '微信商城商品分类id',
  `cname` varchar(200) NOT NULL COMMENT '列注释',
  `parent_cid` varchar(200) DEFAULT NULL COMMENT '商品父id',
  `seq_id` varchar(200) DEFAULT NULL COMMENT '商品分类排序id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `WGQC_Catalog` */

LOCK TABLES `WGQC_Catalog` WRITE;

insert  into `WGQC_Catalog`(`cid`,`cname`,`parent_cid`,`seq_id`,`create_time`) values ('000001','水果','000001','000001','2016-08-26 12:00:03'),('000002','生鲜','000002','000002','2016-08-26 12:00:03'),('000003','鱼','000003','000003','2016-08-26 12:00:03');

UNLOCK TABLES;

/*Table structure for table `WGQC_Goods` */

DROP TABLE IF EXISTS `WGQC_Goods`;

CREATE TABLE `WGQC_Goods` (
  `proid` varchar(200) NOT NULL COMMENT '产品Id(与sku表proskuid进行关联)',
  `classid` varchar(200) NOT NULL COMMENT '分类Id',
  `proname` varchar(100) NOT NULL COMMENT '产品名称',
  `prodescri` varchar(200) DEFAULT NULL COMMENT '产品简介',
  `colorpic` varchar(200) DEFAULT NULL COMMENT '产品列表页图片(缩略图)',
  `goodpic` varchar(200) DEFAULT NULL COMMENT '商品相册',
  `gooddetail` varchar(200) DEFAULT NULL COMMENT '商品详情',
  `prosericenum` varchar(200) DEFAULT NULL COMMENT '产品序列号',
  `isshow` varchar(20) NOT NULL COMMENT 'NY上架',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isdiscount` varchar(20) NOT NULL COMMENT '是否特价',
  PRIMARY KEY (`proid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `WGQC_Goods` */

LOCK TABLES `WGQC_Goods` WRITE;

insert  into `WGQC_Goods`(`proid`,`classid`,`proname`,`prodescri`,`colorpic`,`goodpic`,`gooddetail`,`prosericenum`,`isshow`,`create_time`,`isdiscount`) values ('000001','000001','新西兰红玫瑰苹果Queen4个约180g/个水果','有大有红','','','','000001','Y','2016-10-07 15:55:51',''),('000002','000002','澳大利亚脐橙12个约160g/个 新鲜水果','','','','','000002','N','2016-10-08 19:37:20',''),('000003','000003','满88减40泰国椰青4个约700g/个送开椰器椰汁椰子水果','','','','','000003','N','2016-10-07 15:55:51',''),('15c4c725bf7044578bce272a100428d8','000003','500元',NULL,'http://localhost:30021/WGQC/mwbase/apptemp/images/productDetail/20161003223058581.png',NULL,NULL,NULL,'Y','2016-10-09 10:49:16','N'),('7460526d7cdd4e2e954c6fd8d213437e','000001','测试水果','测试水果简介','http://localhost:30021/WGQC/mwbase/apptemp/images/productDetail/20161003223058581.png','/mwbase/apptemp/images/photoAlbum/20161008194300302.jpg','<p><img style=\"\" src=\"/WGQC/product/showImage?imageAddress=/mwbase/apptemp/images/productDetail/20161008194919008.jpg\"><br></p>',NULL,'N','2016-10-08 19:52:16','N'),('9b6831ab85ff4d4680b60bd3205db38e','000001','123','123','http://localhost:30021/WGQC/mwbase/apptemp/images/productDetail/20161003223058581.png','/mwbase/apptemp/images/photoAlbum/20161008194451412.jpg','<p><img src=\"/WGQC/product/showImage?imageAddress=/mwbase/apptemp/images/productDetail/20161008194503275.jpg\" style=\"\">qweqweqweqweqeqweqwe<br></p>',NULL,'N','2016-10-08 19:45:10','N');

UNLOCK TABLES;

/*Table structure for table `WGQC_Member` */

DROP TABLE IF EXISTS `WGQC_Member`;

CREATE TABLE `WGQC_Member` (
  `openid` varchar(200) NOT NULL COMMENT '微信Openid',
  `user_id` varchar(200) NOT NULL COMMENT '用户ID',
  `nickname` varchar(100) NOT NULL COMMENT '昵称',
  `usertype` varchar(100) DEFAULT NULL COMMENT '用户类型',
  `userpic` varchar(200) NOT NULL COMMENT '用户头像',
  `usermoney` varchar(200) NOT NULL COMMENT '用户余额',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  PRIMARY KEY (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `WGQC_Member` */

LOCK TABLES `WGQC_Member` WRITE;

insert  into `WGQC_Member`(`openid`,`user_id`,`nickname`,`usertype`,`userpic`,`usermoney`,`create_time`) values ('wx23424','000003','李白','C','','50','2016-08-23 11:50:14.000000'),('wx342342','000001','大和魂','A','','600','2016-09-23 12:49:37.939173'),('wx342343','000002','王大大','B','','20','2016-08-26 12:00:03.000000'),('wx342344','000004','刘大胡','B','','0','2016-09-24 21:14:03.640383'),('wx434234','000005','大和魂','B','','800','2016-09-24 21:14:07.286644');

UNLOCK TABLES;

/*Table structure for table `WGQC_Order` */

DROP TABLE IF EXISTS `WGQC_Order`;

CREATE TABLE `WGQC_Order` (
  `order_id` varchar(200) NOT NULL COMMENT '订单ID',
  `order_num` varchar(100) NOT NULL COMMENT '订单编号',
  `user_id` varchar(100) NOT NULL COMMENT '用户ID',
  `remark` varchar(200) DEFAULT NULL COMMENT '订单备注',
  `user_address` varchar(300) NOT NULL COMMENT '收货地址',
  `pay_type` varchar(10) NOT NULL COMMENT '支付类型',
  `pay_amount` varchar(200) NOT NULL COMMENT '支付金额',
  `order_type` varchar(200) NOT NULL COMMENT '订单类型',
  `order_status` varchar(200) NOT NULL COMMENT '订单状态',
  `delivery_time` varchar(200) NOT NULL COMMENT '配送时间',
  `pay_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '支付时间',
  `extra_pay` varchar(200) NOT NULL COMMENT '运费',
  `sum_pay` varchar(200) NOT NULL COMMENT '总金额',
  `create_time` timestamp(6) NOT NULL DEFAULT '0000-00-00 00:00:00.000000' COMMENT '创建时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `WGQC_Order` */

LOCK TABLES `WGQC_Order` WRITE;

insert  into `WGQC_Order`(`order_id`,`order_num`,`user_id`,`remark`,`user_address`,`pay_type`,`pay_amount`,`order_type`,`order_status`,`delivery_time`,`pay_time`,`extra_pay`,`sum_pay`,`create_time`) values ('000001','000001','000001',NULL,'张先生 13913012345','Z','200','X','002','2小时内到达','2016-09-28 22:03:56.969701','5','205','2016-07-26 12:00:03.000000'),('000002','000002','000002',NULL,'','Z','300','X','001','13:00~14:00','2016-09-24 22:44:50.003925','5','305','2016-08-26 12:00:03.000000'),('000003','000003','000003',NULL,'','Z','400','C','003','13:00~14:00','2016-10-09 10:44:07.774728','0','400','2016-09-26 17:22:03.000000'),('000004','000004','000004',NULL,'陈小姐 13812320987','Z','300','X','正在备货','2小时内到达','2016-09-23 20:37:23.938302','0','300','2016-09-23 20:37:17.000000'),('000005','000005','000005',NULL,'刘大爷 18767666778','Z','100','X','已支付','13:00~14:00','2016-09-23 20:42:34.771252','0','300','2016-09-23 20:42:25.000000'),('000006','000006','000006',NULL,'刘姥姥 13244444529','Z','500','X','已支付','2小时内到达','2016-09-23 20:56:59.703427','0','300','2016-09-23 20:53:22.000000'),('000007','000007','000007',NULL,'王姥姥 13244444530','Z','300','X','已支付','2小时内到达','2016-09-23 20:57:47.435049','0','800','2016-09-23 20:57:41.000000'),('000008','000008','000008',NULL,'田先生 17773235235','Z','200','X','已支付','13:00~14:00','2016-09-23 21:05:39.813259','','200','2016-09-23 21:05:33.000000'),('000009','000009','000009',NULL,'李先生 18734234230','Z','100','X','已支付','14:00~15:00','2016-09-26 17:24:39.849414','5','400','2016-09-26 17:24:32.000000'),('000010','000010','000010',NULL,'张先生 13913012345','Z','700','X','已支付','2小时内到达','2016-09-27 08:19:57.497821','5','705','2016-09-27 08:19:56.000000');

UNLOCK TABLES;

/*Table structure for table `WGQC_Order_Detail` */

DROP TABLE IF EXISTS `WGQC_Order_Detail`;

CREATE TABLE `WGQC_Order_Detail` (
  `orderdetail_id` varchar(200) NOT NULL COMMENT '订单明细ID',
  `order_id` varchar(100) NOT NULL COMMENT '订单编号',
  `prosku_id` varchar(200) NOT NULL COMMENT '产品SKUId',
  `numsku` varchar(100) DEFAULT NULL COMMENT 'sku产品数量(多少个sku)',
  `subtotal` varchar(200) NOT NULL COMMENT '价格(多个sku的金额)',
  `praise_content` varchar(500) NOT NULL COMMENT '评价内容',
  `praise_star` varchar(20) NOT NULL COMMENT '评价星级',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  PRIMARY KEY (`orderdetail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `WGQC_Order_Detail` */

LOCK TABLES `WGQC_Order_Detail` WRITE;

insert  into `WGQC_Order_Detail`(`orderdetail_id`,`order_id`,`prosku_id`,`numsku`,`subtotal`,`praise_content`,`praise_star`,`create_time`) values ('000001','000001','000001','3','60','很好吃','5','2016-09-24 16:30:33.732574'),('000002','000001','000004','5','100','甜酸','4','2016-09-24 16:30:33.741076'),('000003','000003','000003','6','240','甜','3','2016-09-24 16:30:33.749913');

UNLOCK TABLES;

/*Table structure for table `WGQC_Praise` */

DROP TABLE IF EXISTS `WGQC_Praise`;

CREATE TABLE `WGQC_Praise` (
  `praise_id` varchar(200) NOT NULL COMMENT '评价内容ID',
  `user_id` varchar(200) NOT NULL COMMENT '用户ID',
  `skugood` varchar(100) NOT NULL COMMENT '评价商品',
  `praise_content` varchar(500) DEFAULT NULL COMMENT '评价内容',
  `praise_star` varchar(200) NOT NULL COMMENT '评价星级',
  `praise_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '评价时间',
  `audit_status` varchar(20) NOT NULL COMMENT '审核状态',
  PRIMARY KEY (`praise_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `WGQC_Praise` */

LOCK TABLES `WGQC_Praise` WRITE;

insert  into `WGQC_Praise`(`praise_id`,`user_id`,`skugood`,`praise_content`,`praise_star`,`praise_time`,`audit_status`) values ('000001','000001','苹果','鲜','5','2016-09-28 22:09:34.100263','N'),('000002','000002','香蕉','大','4','2016-10-09 10:43:22.111147','Y'),('000003','000003','鱼','美','5','2016-10-08 17:56:12.394322','Y');

UNLOCK TABLES;

/*Table structure for table `WGQC_Set` */

DROP TABLE IF EXISTS `WGQC_Set`;

CREATE TABLE `WGQC_Set` (
  `set_id` varchar(10) NOT NULL,
  `set_object` varchar(50) DEFAULT NULL COMMENT '对象设置包含充值卡和免邮金额',
  `set_type` varchar(10) DEFAULT NULL COMMENT 'F表示免邮金额，C表示充值卡',
  PRIMARY KEY (`set_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `WGQC_Set` */

LOCK TABLES `WGQC_Set` WRITE;

insert  into `WGQC_Set`(`set_id`,`set_object`,`set_type`) values ('000001','充100得110','C'),('000002','充200得230','C'),('000003','充50得55','C'),('000004','充500得6000','C'),('000005','49','F');

UNLOCK TABLES;

/*Table structure for table `WGQC_Sku` */

DROP TABLE IF EXISTS `WGQC_Sku`;

CREATE TABLE `WGQC_Sku` (
  `proskuid` varchar(200) NOT NULL COMMENT '产品SKUId',
  `sku` varchar(100) DEFAULT NULL COMMENT '产品SKU(规格)',
  `skuname` varchar(100) NOT NULL COMMENT 'SKU名称',
  `proid` varchar(200) NOT NULL COMMENT '产品Id',
  `isdiscount` varchar(20) NOT NULL COMMENT '是否特价',
  `discountprice` varchar(100) DEFAULT NULL COMMENT '特价',
  `sellprice` varchar(200) NOT NULL COMMENT '本站销售价',
  `salevolume` varchar(200) DEFAULT NULL COMMENT '销量',
  `inventory` varchar(200) NOT NULL COMMENT '库存',
  `sku_states` varchar(200) DEFAULT NULL COMMENT '状态',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `proname` varchar(100) NOT NULL,
  PRIMARY KEY (`proskuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `WGQC_Sku` */

LOCK TABLES `WGQC_Sku` WRITE;

insert  into `WGQC_Sku`(`proskuid`,`sku`,`skuname`,`proid`,`isdiscount`,`discountprice`,`sellprice`,`salevolume`,`inventory`,`sku_states`,`create_time`,`proname`) values ('000001','3','3斤（5个）','000001','Y','10','20','100','300','Y','2016-09-24 16:18:29.061945','新西兰红玫瑰苹果Queen4个约180g/个水果'),('000002','4','4斤（6个）','000001','N','20','30','200','200','N','2016-09-24 16:18:29.070741','新西兰红玫瑰苹果Queen4个约180g/个水果'),('000003','5','5斤（7个）','000001','N','30','40','300','100','','2016-09-24 16:18:29.085473','新西兰红玫瑰苹果Queen4个约180g/个水果'),('000004','10','10斤（9个）','000002','Y','10','20','100','300','Y','2016-09-24 16:21:54.008879','澳大利亚脐橙12个约160g/个 新鲜水果'),('62c7587480b141da835734543c5a152b',NULL,'5斤装','7460526d7cdd4e2e954c6fd8d213437e','N','','20',NULL,'200',NULL,'2016-10-08 19:51:43.000000','测试水果'),('6990d6fd9dcb4f75812a34afda4650b9',NULL,'234','9b6831ab85ff4d4680b60bd3205db38e','N','234','234',NULL,'234',NULL,'2016-10-08 19:45:10.000000','123'),('8c12622a55d84cafafb525e2283816a4',NULL,'500元','15c4c725bf7044578bce272a100428d8','Y','500','550',NULL,'100000000',NULL,'2016-10-09 10:49:17.000000','500元'),('b07892a68ced411fa99fb8a6bcd21567',NULL,'123','9b6831ab85ff4d4680b60bd3205db38e','N','123','123',NULL,'123',NULL,'2016-10-08 19:45:10.000000','123'),('f91beff1829c4d569cb9c7b154af8975',NULL,'3斤装','7460526d7cdd4e2e954c6fd8d213437e','N','','10',NULL,'100',NULL,'2016-10-08 19:51:43.000000','测试水果');

UNLOCK TABLES;

/*Table structure for table `WGQC_order_status` */

DROP TABLE IF EXISTS `WGQC_order_status`;

CREATE TABLE `WGQC_order_status` (
  `order_status_id` varchar(10) NOT NULL,
  `order_status_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`order_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单状态表';

/*Data for the table `WGQC_order_status` */

LOCK TABLES `WGQC_order_status` WRITE;

insert  into `WGQC_order_status`(`order_status_id`,`order_status_name`) values ('001','正在备货'),('002','正在发货'),('003','订单完成');

UNLOCK TABLES;

/*Table structure for table `WGQC_shopcart` */

DROP TABLE IF EXISTS `WGQC_shopcart`;

CREATE TABLE `WGQC_shopcart` (
  `sp_id` varchar(200) NOT NULL COMMENT '评价内容ID',
  `user_id` varchar(200) NOT NULL COMMENT '用户ID',
  `prosku_id` varchar(100) NOT NULL COMMENT '产品skuId',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  PRIMARY KEY (`sp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `WGQC_shopcart` */

LOCK TABLES `WGQC_shopcart` WRITE;

insert  into `WGQC_shopcart`(`sp_id`,`user_id`,`prosku_id`,`create_time`) values ('000001','000001','000001','2016-08-26 12:00:03.000000'),('000002','000002','000002','2016-04-26 12:00:03.000000'),('000003','000003','000003','2016-01-26 12:00:03.000000');

UNLOCK TABLES;

/*Table structure for table `WX_TOKEN_TICKET_CONFIG` */

DROP TABLE IF EXISTS `WX_TOKEN_TICKET_CONFIG`;

CREATE TABLE `WX_TOKEN_TICKET_CONFIG` (
  `ACCESSTOKEN` varchar(4000) DEFAULT NULL,
  `JSAPI_TICKET` varchar(4000) DEFAULT NULL,
  `LIFE_END` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `WX_TOKEN_TICKET_CONFIG` */

LOCK TABLES `WX_TOKEN_TICKET_CONFIG` WRITE;

insert  into `WX_TOKEN_TICKET_CONFIG`(`ACCESSTOKEN`,`JSAPI_TICKET`,`LIFE_END`) values ('l1JQB2XW2axFOLPijyrl1rLbdKKyOMBd5IvV8UJMJ9OFJH8Hr6weiptOKoVi9-rovi_COvYM1FxhJAAAP5EVUrNU4VKiWtyNcfM8JfymKqsBXYjAGAFBC','kgt8ON7yVITDhtdwci0qedFfhFhPUIjglpxTWOrqpOH4tYGxy34ubpcDiM_8r7b3u_QgYfnO31hk8w5_VhCyDQ','2016-10-09 17:05:39');

UNLOCK TABLES;

/*Table structure for table `key_message` */

DROP TABLE IF EXISTS `key_message`;

CREATE TABLE `key_message` (
  `id` varchar(200) NOT NULL,
  `type` varchar(50) DEFAULT NULL,
  `data` varchar(2000) DEFAULT NULL,
  `flag` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `key_message` */

LOCK TABLES `key_message` WRITE;

UNLOCK TABLES;

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` varchar(100) DEFAULT NULL,
  `userId` varchar(100) DEFAULT NULL,
  `name` varchar(150) DEFAULT NULL,
  `password` varchar(4000) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `flag` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `users` */

LOCK TABLES `users` WRITE;

insert  into `users`(`id`,`userId`,`name`,`password`,`type`,`flag`) values ('wqerwqerqw-qwerqwre123-asdfsdfsf-wqerq3qer','admin','admin','c14d8cecc2f75534ff11989098de3ed5','PC','1');

UNLOCK TABLES;

/*Table structure for table `wx_menu` */

DROP TABLE IF EXISTS `wx_menu`;

CREATE TABLE `wx_menu` (
  `data` blob NOT NULL,
  `flag` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wx_menu` */

LOCK TABLES `wx_menu` WRITE;

insert  into `wx_menu`(`data`,`flag`) values ('��\0sr\0java.util.HashMap���`�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0idt\0Roott\0pIdt\0\0t\0indext\01t\0pNamet\0\0t\0subMenussr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0\nsq\0~\0\0?@\0\0\0\0\0w\0\0\0\0\0\0t\0idt\0m_0t\0pIdt\0Roott\0indext\01t\0pNamet\0ROOTt\0subMenussq\0~\0\0\0\0\0w\0\0\0\nxt\0namet\0在线订购t\0typet\0viewt\0keyt\0�__URL:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx0f85e6abf15f9e3b&redirect_uri=http%3a%2f%2f139.196.234.158%2fWGQC%2fPUBLIC%2fWXRedirectServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirectxxt\0namet\0ROOTt\0typet\0clickt\0keyt\0rootx','1');

UNLOCK TABLES;

/*Table structure for table `past_12month_view` */

DROP TABLE IF EXISTS `past_12month_view`;

/*!50001 DROP VIEW IF EXISTS `past_12month_view` */;
/*!50001 DROP TABLE IF EXISTS `past_12month_view` */;

/*!50001 CREATE TABLE  `past_12month_view`(
 `month` varchar(7) NULL 
)*/;

/*View structure for view past_12month_view */

/*!50001 DROP TABLE IF EXISTS `past_12month_view` */;
/*!50001 DROP VIEW IF EXISTS `past_12month_view` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wqc`@`%` SQL SECURITY DEFINER VIEW `past_12month_view` AS select date_format(curdate(),'%Y-%m') AS `month` union select date_format((curdate() - interval 1 month),'%Y-%m') AS `month` union select date_format((curdate() - interval 2 month),'%Y-%m') AS `month` union select date_format((curdate() - interval 3 month),'%Y-%m') AS `month` union select date_format((curdate() - interval 4 month),'%Y-%m') AS `month` union select date_format((curdate() - interval 5 month),'%Y-%m') AS `month` union select date_format((curdate() - interval 6 month),'%Y-%m') AS `month` union select date_format((curdate() - interval 7 month),'%Y-%m') AS `month` union select date_format((curdate() - interval 8 month),'%Y-%m') AS `month` union select date_format((curdate() - interval 9 month),'%Y-%m') AS `month` union select date_format((curdate() - interval 10 month),'%Y-%m') AS `month` union select date_format((curdate() - interval 11 month),'%Y-%m') AS `month` */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
