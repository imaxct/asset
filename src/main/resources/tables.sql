-- MySQL dump 10.17  Distrib 10.3.13-MariaDB, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: asset
-- ------------------------------------------------------
-- Server version	10.3.13-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `asset_department`
--

DROP TABLE IF EXISTS `asset_department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_department` (
                                    `id`           bigint(19) unsigned NOT NULL AUTO_INCREMENT,
                                    `gmt_create`   datetime                               DEFAULT current_timestamp(),
                                    `gmt_modified` datetime                               DEFAULT NULL,
                                    `name`         varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                    `parent_dep`   bigint(19) unsigned                    DEFAULT NULL,
                                    `dep_id`       varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `asset_department_uindx_dep_id` (`dep_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_department`
--

LOCK TABLES `asset_department` WRITE;
/*!40000 ALTER TABLE `asset_department` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset_department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset_process`
--

DROP TABLE IF EXISTS `asset_process`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_process` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT current_timestamp(),
  `gmt_modified` datetime DEFAULT NULL,
  `name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status_required` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `final_status` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `first_step_id` bigint(19) unsigned NOT NULL,
  `transfer_type` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role_required` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_process`
--

LOCK TABLES `asset_process` WRITE;
/*!40000 ALTER TABLE `asset_process` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset_process` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset_process_log`
--

DROP TABLE IF EXISTS `asset_process_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_process_log` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT current_timestamp(),
  `gmt_modified` datetime DEFAULT NULL,
  `process_user_id` bigint(19) unsigned NOT NULL,
  `process_id` bigint(19) unsigned NOT NULL,
  `step_id` bigint(19) unsigned NOT NULL,
  `property_id` bigint(19) unsigned NOT NULL,
  `ticket_id` bigint(19) unsigned NOT NULL,
  `pass` tinyint(1) NOT NULL,
  `process_proposal` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_ticket_id` (`ticket_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_process_log`
--

LOCK TABLES `asset_process_log` WRITE;
/*!40000 ALTER TABLE `asset_process_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset_process_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset_property`
--

DROP TABLE IF EXISTS `asset_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_property` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT current_timestamp(),
  `gmt_modified` datetime DEFAULT NULL,
  `name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cur_status` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `property_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dep_id` bigint(19) unsigned NOT NULL,
  `process_id` bigint(19) unsigned DEFAULT NULL,
  `occupy_user_id` bigint(19) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uidx_property_id` (`property_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_property`
--

LOCK TABLES `asset_property` WRITE;
/*!40000 ALTER TABLE `asset_property` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset_property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset_role`
--

DROP TABLE IF EXISTS `asset_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_role` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT current_timestamp(),
  `gmt_modified` datetime DEFAULT NULL,
  `name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `authorized_mapping` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_role`
--

LOCK TABLES `asset_role` WRITE;
/*!40000 ALTER TABLE `asset_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset_step`
--

DROP TABLE IF EXISTS `asset_step`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_step` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT current_timestamp(),
  `gmt_modified` datetime DEFAULT NULL,
  `name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `process_id` bigint(19) unsigned NOT NULL,
  `next_step_id` bigint(19) unsigned DEFAULT NULL,
  `role_required` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status_required` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_process_id` (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_step`
--

LOCK TABLES `asset_step` WRITE;
/*!40000 ALTER TABLE `asset_step` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset_step` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset_ticket`
--

DROP TABLE IF EXISTS `asset_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_ticket` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT current_timestamp(),
  `gmt_modified` datetime DEFAULT NULL,
  `apply_user_id` bigint(19) unsigned NOT NULL,
  `cur_step_id` bigint(19) unsigned NOT NULL,
  `process_id` bigint(19) unsigned NOT NULL,
  `property_id` bigint(19) unsigned NOT NULL,
  `cur_status` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `apply_reason` varchar(250) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dep_id` bigint(19) unsigned NOT NULL,
  `transfer_user_id` bigint(19) unsigned DEFAULT NULL,
  `final_status` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `initial_status` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_ticket`
--

LOCK TABLES `asset_ticket` WRITE;
/*!40000 ALTER TABLE `asset_ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset_ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset_user`
--

DROP TABLE IF EXISTS `asset_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_user` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT current_timestamp(),
  `gmt_modified` datetime DEFAULT NULL,
  `name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dep_id` bigint(19) unsigned NOT NULL,
  `role_id` bigint(19) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `asset_user_username_uindex` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_user`
--

LOCK TABLES `asset_user` WRITE;
/*!40000 ALTER TABLE `asset_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset_message`
--

DROP TABLE IF EXISTS `asset_message`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_message`
(
    `id`           bigint(19) unsigned                     NOT NULL AUTO_INCREMENT,
    `gmt_create`   datetime DEFAULT current_timestamp(),
    `gmt_modified` datetime DEFAULT NULL,
    `content`      text COLLATE utf8mb4_unicode_ci         NOT NULL,
    `title`        varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_user_message`
--

DROP TABLE IF EXISTS `asset_user_message`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_user_message`
(
    `id`           bigint(19) unsigned NOT NULL AUTO_INCREMENT,
    `gmt_create`   datetime DEFAULT current_timestamp(),
    `gmt_modified` datetime DEFAULT NULL,
    `user_id`      bigint(19) unsigned NOT NULL,
    `msg_id`       bigint(19) unsigned NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_uid_mid` (`user_id`, `msg_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-24 21:27:49
