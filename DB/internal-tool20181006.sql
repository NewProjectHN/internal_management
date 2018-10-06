-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: internal_tool
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `employee` (
  `employee_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(25) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `is_active` tinyint(1) DEFAULT '0',
  `fullname` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `code` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `group_id` varchar(5) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `UKim8flsuftl52etbhgnr62d6wh` (`username`),
  KEY `group_id` (`group_id`),
  KEY `employee_role_FK` (`role_id`),
  CONSTRAINT `employee_role_FK` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'dvhoan','',1,'Đinh Văn Hoàn','nguyenduclong02@gmail.com','1','N3',NULL,NULL,NULL,3),(2,'ndlong','',1,'Nguyễn Đức Long','long11.nguyenduc@nttdata.com','2','N3',NULL,NULL,NULL,4),(3,'tdchien','',1,'Trần Đình Chiến','Chien.TranDinh@nttdata.com','3','N3',NULL,NULL,NULL,5),(4,'nvhoang','',1,'Nguyễn Văn Hoàng','hoang.nguyenvan@nttdata.com','4','N3',NULL,NULL,NULL,4),(5,'ntkanh','',1,'Nguyễn Thị Kim Anh','Anh.NguyenThiKim@nttdata.com','5','N3',NULL,NULL,NULL,4),(6,'ntson01','',1,'Nguyễn Thế Sơn','Son.NguyenThe@nttdata.com','6','N3',NULL,NULL,NULL,5),(7,'ntlanh','',1,'Ngô Thị Lan Anh','Anh.NgoThiLan@nttdata.com','7','N3',NULL,NULL,NULL,5),(8,'ncsthanh','',1,'Nguyễn Chí Song Thanh','Thanh.NguyenChiSong@nttdata.com','8','N3',NULL,NULL,NULL,5),(9,'ttatuyet','',1,'Trịnh Thị Ánh Tuyết','Tuyet.TrinhThiAnh@nttdata.com','9','N3',NULL,NULL,NULL,5),(10,'nqkhuong','',1,'Nguyễn Quốc Khương','khuong.nguyenquoc@nttdata.com','10','N3',NULL,NULL,NULL,5),(11,'bqhuy','',1,'Bùi Quang Huy','Huy.BuiQuang@nttdata.com','11','N3',NULL,NULL,NULL,5),(12,'nvdiep','',1,'Nguyễn Văn Điệp','Diep.NguyenVan@nttdata.com','12','N3',NULL,NULL,NULL,5),(13,'nddong','',1,'Nguyễn Đắc Đồng','Dong.NguyenDac@nttdata.com','13','N3',NULL,NULL,NULL,4),(14,'pttrang','',1,'Phan Thị Trang','Trang.PhanThi@nttdata.com','14','N3',NULL,NULL,NULL,5),(15,'thcuong','',1,'Trần Hùng Cường','Cuong.TranHung@ifisolution.com','15','N3',NULL,NULL,NULL,5),(16,'nttung02','',1,'Nguyễn Thanh Tùng','Tung.NguyenThanh2@nttdata.com','16','N3',NULL,NULL,NULL,5),(17,'vtuyen','',1,'Vũ Tú Uyên','Uyen.VuTu@nttdata.com','17','N3',NULL,NULL,NULL,4),(18,'paquan','',1,'Phan Anh Quân','Quan.PhanAnh@nttdata.com','18','N3',NULL,NULL,NULL,5),(19,'congnv','',1,'Nguyễn Văn Công','Cong.NguyenVan@nttdata.com','19','N3',NULL,NULL,NULL,5),(20,'nvbac','',1,'Nguyễn Văn Bắc','Bac.NguyenVan@ifisolution.com','20','N3',NULL,NULL,NULL,5),(21,'tvduan','',1,'Trịnh Viết Duẩn','tvduan@ifisolution.com','21','N3',NULL,NULL,NULL,5),(22,'ldanh','',1,'Lê Duy Anh','Anh.LeDuy@nttdata.com','22','N3',NULL,NULL,NULL,4),(23,'hmtri','',1,'Hoàng Minh Trí','Tri.HoangMinh@nttdata.com','23','N3',NULL,NULL,NULL,4),(24,'nvthuy','',1,'Nguyễn Văn Thụy','thuy.nguyenvan@ifisolution.com','24','N3',NULL,NULL,NULL,5),(25,'vtnghia','',1,'Vũ Tiến Nghĩa','Nghia.VuTien@nttdata.com','25','N3',NULL,NULL,NULL,4),(26,'lqhung','',1,'Lưu Quang Hưng','Hung.LuuQuang@ifisolution.com','26','N3',NULL,NULL,NULL,5),(27,'ttnguyet01','',1,'Trần Thị Nguyệt','Nguyet.TranThi@nttdata.com','27','N3',NULL,NULL,NULL,5),(28,'ntlien','',1,'Ngô Thị Liên','Lien.NgoThi@nttdata.com','28','N3',NULL,NULL,NULL,5),(29,'nduan','',1,'Nguyễn Duy Uân','Uan.NguyenDuy@nttdata.com','29','N3',NULL,NULL,NULL,5),(30,'nvdien','',1,'Nguyễn Văn Diện','Dien.NguyenVan@nttdata.com','30','N3',NULL,NULL,NULL,5),(31,'qvphong','',1,'Quách Văn Phong','Phong.QuachVan@nttdata.com','31','N3',NULL,NULL,NULL,4);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_ifi`
--

DROP TABLE IF EXISTS `group_ifi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `group_ifi` (
  `group_id` varchar(5) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_ifi`
--

LOCK TABLES `group_ifi` WRITE;
/*!40000 ALTER TABLE `group_ifi` DISABLE KEYS */;
INSERT INTO `group_ifi` VALUES ('N1','N1'),('N2','N2'),('N3','N3');
/*!40000 ALTER TABLE `group_ifi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `overtime`
--

DROP TABLE IF EXISTS `overtime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `overtime` (
  `overtime_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `date` date DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `employee_id` bigint(20) DEFAULT NULL,
  `from_hour` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_approved` bit(1) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `to_hour` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `overtime_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`overtime_id`),
  KEY `overtime_fk1` (`employee_id`),
  KEY `overtime_fk2` (`project_id`),
  CONSTRAINT `overtime_fk1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`),
  CONSTRAINT `overtime_fk2` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `overtime`
--

LOCK TABLES `overtime` WRITE;
/*!40000 ALTER TABLE `overtime` DISABLE KEYS */;
/*!40000 ALTER TABLE `overtime` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `overtime_approved`
--

DROP TABLE IF EXISTS `overtime_approved`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `overtime_approved` (
  `overtime_approved_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `manager_id` bigint(20) DEFAULT NULL,
  `overtime_id` bigint(20) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`overtime_approved_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `overtime_approved`
--

LOCK TABLES `overtime_approved` WRITE;
/*!40000 ALTER TABLE `overtime_approved` DISABLE KEYS */;
/*!40000 ALTER TABLE `overtime_approved` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `overtime_log`
--

DROP TABLE IF EXISTS `overtime_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `overtime_log` (
  `overtime_log_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `overtime_id` bigint(20) DEFAULT NULL,
  `next_approve_id` bigint(20) DEFAULT NULL,
  `approved_id` bigint(20) DEFAULT NULL,
  `disapproved_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`overtime_log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `overtime_log`
--

LOCK TABLES `overtime_log` WRITE;
/*!40000 ALTER TABLE `overtime_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `overtime_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `overtime_type`
--

DROP TABLE IF EXISTS `overtime_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `overtime_type` (
  `overtime_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `overtime_type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`overtime_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `overtime_type`
--

LOCK TABLES `overtime_type` WRITE;
/*!40000 ALTER TABLE `overtime_type` DISABLE KEYS */;
INSERT INTO `overtime_type` VALUES (1,'Customer Request'),(2,'Manager Request'),(3,'By Yourself');
/*!40000 ALTER TABLE `overtime_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `project` (
  `project_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `group_id` varchar(5) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `status` tinyint(1) DEFAULT '1',
  `description` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `code` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `manager_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `group_ifi` (`group_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'Energy Modelling','N3',1,NULL,NULL,2),(2,'IDEX','N3',1,NULL,NULL,4),(3,'Analysis','N3',1,NULL,NULL,5),(4,'Alert','N3',1,NULL,NULL,23),(5,'BSY','N3',1,NULL,NULL,25),(6,'Financial Modelling','N3',1,NULL,NULL,13),(7,'Administration','N3',1,NULL,NULL,22),(8,'TMA','N3',1,NULL,NULL,17),(9,'COLAS','N3',1,NULL,NULL,31);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_manager`
--

DROP TABLE IF EXISTS `project_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `project_manager` (
  `project_manager_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `manager_id` bigint(20) NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `priority` int(11) NOT NULL,
  PRIMARY KEY (`project_manager_id`),
  UNIQUE KEY `project_manager_unique` (`manager_id`,`project_id`,`employee_id`),
  KEY `project_id` (`project_id`),
  KEY `employee_id` (`employee_id`),
  CONSTRAINT `project_manager_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE,
  CONSTRAINT `project_manager_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`) ON DELETE CASCADE,
  CONSTRAINT `project_manager_ibfk_3` FOREIGN KEY (`manager_id`) REFERENCES `employee` (`employee_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_manager`
--

LOCK TABLES `project_manager` WRITE;
/*!40000 ALTER TABLE `project_manager` DISABLE KEYS */;
INSERT INTO `project_manager` VALUES (7,2,3,1,1),(8,5,6,3,1),(9,5,7,3,1),(10,5,8,3,1),(11,2,9,1,1),(12,4,10,2,1),(13,4,11,2,1),(14,25,12,5,1),(15,13,14,6,1),(16,13,15,6,1),(17,13,16,6,1),(18,2,18,1,1),(19,23,19,4,1),(20,17,20,8,1),(21,25,21,5,1),(22,25,24,5,1),(23,31,26,9,1),(24,22,27,7,1),(25,22,28,7,1),(26,31,29,9,1),(27,22,30,7,1),(28,1,2,1,1),(29,1,3,1,2),(30,1,4,2,1),(31,1,5,3,1),(32,1,6,3,2),(33,1,7,3,2),(34,1,8,3,2),(35,1,9,1,2),(36,1,10,2,2),(37,1,11,2,2),(38,1,12,5,2),(39,1,13,6,1),(40,1,14,6,2),(41,1,15,6,2),(42,1,16,6,2),(43,1,17,8,1),(44,1,18,1,2),(45,1,19,4,2),(46,1,20,8,2),(47,1,21,5,2),(48,1,22,7,1),(49,1,23,4,1),(50,1,24,5,2),(51,1,25,5,1),(52,1,26,9,2),(53,1,27,7,2),(54,1,28,7,2),(55,1,29,9,2),(56,1,30,7,2),(57,1,31,9,1);
/*!40000 ALTER TABLE `project_manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `APP_ROLE_UK` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(5,'ROLE_EMPLOYEE'),(2,'ROLE_LEADER_A'),(3,'ROLE_LEADER_B'),(4,'ROLE_LEADER_C');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vacation`
--

DROP TABLE IF EXISTS `vacation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `vacation` (
  `vacation_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `employee_id` bigint(20) NOT NULL,
  `from_date` datetime NOT NULL,
  `to_date` datetime NOT NULL,
  `vacation_type` bigint(20) NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `status` int(11) DEFAULT '1',
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `project_id` bigint(20) NOT NULL,
  `is_approved` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`vacation_id`),
  KEY `employee_id` (`employee_id`),
  KEY `vacation_type` (`vacation_type`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `vacation_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`) ON DELETE CASCADE,
  CONSTRAINT `vacation_ibfk_2` FOREIGN KEY (`vacation_type`) REFERENCES `vacation_type` (`vacation_type_id`) ON DELETE CASCADE,
  CONSTRAINT `vacation_ibfk_3` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vacation`
--

LOCK TABLES `vacation` WRITE;
/*!40000 ALTER TABLE `vacation` DISABLE KEYS */;
/*!40000 ALTER TABLE `vacation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vacation_approved`
--

DROP TABLE IF EXISTS `vacation_approved`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `vacation_approved` (
  `vacation_approved_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `manager_id` bigint(20) NOT NULL,
  `vacation_id` bigint(20) NOT NULL,
  `priority` int(11) NOT NULL,
  PRIMARY KEY (`vacation_approved_id`),
  KEY `vacation_id` (`vacation_id`),
  CONSTRAINT `vacation_approved_ibfk_1` FOREIGN KEY (`vacation_id`) REFERENCES `vacation` (`vacation_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vacation_approved`
--

LOCK TABLES `vacation_approved` WRITE;
/*!40000 ALTER TABLE `vacation_approved` DISABLE KEYS */;
/*!40000 ALTER TABLE `vacation_approved` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vacation_log`
--

DROP TABLE IF EXISTS `vacation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `vacation_log` (
  `vacation_log_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `approved_id` bigint(20) DEFAULT NULL,
  `disapproved_id` bigint(20) DEFAULT NULL,
  `next_approve_id` bigint(20) DEFAULT NULL,
  `vacation_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`vacation_log_id`),
  KEY `vacation_id` (`vacation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vacation_log`
--

LOCK TABLES `vacation_log` WRITE;
/*!40000 ALTER TABLE `vacation_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `vacation_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vacation_type`
--

DROP TABLE IF EXISTS `vacation_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `vacation_type` (
  `vacation_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `vacation_type_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`vacation_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vacation_type`
--

LOCK TABLES `vacation_type` WRITE;
/*!40000 ALTER TABLE `vacation_type` DISABLE KEYS */;
INSERT INTO `vacation_type` VALUES (1,'Nghỉ phép'),(2,'Nghỉ không lương'),(3,'Nghỉ chế độ');
/*!40000 ALTER TABLE `vacation_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-06 13:07:31
