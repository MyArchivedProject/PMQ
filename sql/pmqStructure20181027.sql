CREATE DATABASE  IF NOT EXISTS `pmq` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pmq`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: pmq
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rank` char(1) DEFAULT NULL,
  `user` varchar(25) DEFAULT NULL,
  `pass` varchar(25) DEFAULT NULL,
  `name` varchar(25) DEFAULT NULL,
  `time` datetime DEFAULT CURRENT_TIMESTAMP,
  `tele` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `evaluation`
--

DROP TABLE IF EXISTS `evaluation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(25) DEFAULT NULL,
  `index_id` int(11) DEFAULT NULL,
  `eva_a` varchar(500) DEFAULT NULL,
  `mini_a` int(11) DEFAULT '0',
  `eva_b` varchar(500) DEFAULT NULL,
  `mini_b` int(11) DEFAULT '0',
  `eva_c` varchar(500) DEFAULT NULL,
  `mini_c` int(11) DEFAULT '0',
  `eva_d` varchar(500) DEFAULT NULL,
  `mini_d` int(11) DEFAULT '0',
  `eva_e` varchar(500) DEFAULT NULL,
  `mini_e` int(11) DEFAULT '0',
  `eva_f` varchar(500) DEFAULT NULL,
  `mini_f` int(11) DEFAULT '0',
  `post` varchar(25) DEFAULT NULL,
  `title` varchar(25) DEFAULT NULL,
  `multi_index` varchar(255) DEFAULT NULL,
  `flag` int(11) DEFAULT '0' COMMENT '0表示暂停使用,1表示使用，默认值为0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `paper`
--

DROP TABLE IF EXISTS `paper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tester_id` int(11) DEFAULT NULL,
  `index_f` varchar(25) DEFAULT NULL,
  `index_id` int(11) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `des` varchar(100) DEFAULT NULL,
  `option_a` varchar(100) DEFAULT NULL,
  `score_a` int(11) DEFAULT NULL,
  `option_b` varchar(100) DEFAULT NULL,
  `score_b` int(11) DEFAULT NULL,
  `option_c` varchar(100) DEFAULT NULL,
  `score_c` int(11) DEFAULT NULL,
  `option_d` varchar(100) DEFAULT NULL,
  `score_d` int(11) DEFAULT NULL,
  `option_e` varchar(100) DEFAULT NULL,
  `score_e` int(11) DEFAULT NULL,
  `option_f` varchar(100) DEFAULT NULL,
  `score_f` int(11) DEFAULT NULL,
  `top` int(11) DEFAULT NULL,
  `objsub` int(11) DEFAULT '0',
  `flag` int(11) DEFAULT '0',
  `answer` varchar(510) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IndexID` (`index_id`) USING BTREE,
  KEY `paper-testerId` (`tester_id`),
  CONSTRAINT `paper-testerId` FOREIGN KEY (`tester_id`) REFERENCES `tester` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=603 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(25) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `index_id` int(11) DEFAULT NULL,
  `des` varchar(100) DEFAULT NULL,
  `option_a` varchar(100) DEFAULT NULL,
  `score_a` int(11) DEFAULT NULL,
  `option_b` varchar(100) DEFAULT NULL,
  `score_b` int(11) DEFAULT NULL,
  `option_c` varchar(100) DEFAULT NULL,
  `score_c` int(11) DEFAULT NULL,
  `option_d` varchar(100) DEFAULT NULL,
  `score_d` int(11) DEFAULT NULL,
  `option_e` varchar(100) DEFAULT NULL,
  `score_e` int(11) DEFAULT NULL,
  `option_f` varchar(100) DEFAULT NULL,
  `score_f` int(11) DEFAULT NULL,
  `top` int(11) DEFAULT NULL,
  `objsub` int(11) DEFAULT '0',
  `flag` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `IndexID` (`index_id`) USING BTREE,
  CONSTRAINT `question-index` FOREIGN KEY (`index_id`) REFERENCES `target` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=289 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `target`
--

DROP TABLE IF EXISTS `target`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `target` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `post` varchar(25) DEFAULT NULL,
  `index_f` varchar(25) DEFAULT NULL,
  `index_s` varchar(25) DEFAULT NULL,
  `num_obj` int(11) DEFAULT '0',
  `num_sub` int(11) DEFAULT '0',
  `total` int(11) DEFAULT '0',
  `allnum_obj` int(11) DEFAULT '0' COMMENT '客观题题库总量',
  `allnum_sub` int(11) DEFAULT '0' COMMENT '主观题题库总量',
  PRIMARY KEY (`id`),
  KEY `post-key` (`post`),
  KEY `index_s-key` (`index_s`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tester`
--

DROP TABLE IF EXISTS `tester`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tester` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) DEFAULT '',
  `email` varchar(50) DEFAULT '',
  `tele` char(11) DEFAULT '',
  `post` varchar(25) DEFAULT NULL,
  `num_obj` int(11) DEFAULT NULL,
  `num_sub` int(11) DEFAULT NULL,
  `time` datetime DEFAULT CURRENT_TIMESTAMP,
  `teacher` varchar(25) DEFAULT '',
  `total_obj` int(11) DEFAULT '0',
  `total_sub` int(11) DEFAULT '0',
  `total` int(11) DEFAULT '0',
  `know_score` int(11) DEFAULT '0',
  `experience_score` int(11) DEFAULT '0',
  `skill_score` int(11) DEFAULT '0',
  `style_score` int(11) DEFAULT '0',
  `total_paper` int(11) DEFAULT '0',
  `value_score` int(11) DEFAULT '0',
  `sub_paper` int(11) DEFAULT '0',
  `obj_paper` int(11) DEFAULT '0',
  `know_paper` int(11) DEFAULT '0',
  `experience_paper` int(11) DEFAULT '0',
  `skill_paper` int(11) DEFAULT '0',
  `style_paper` int(11) DEFAULT '0',
  `value_paper` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-27  1:27:26
