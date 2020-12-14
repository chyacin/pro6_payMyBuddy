CREATE DATABASE  IF NOT EXISTS `probuddy` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `probuddy`;
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: probuddy
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `pro_buddy_account`
--

DROP TABLE IF EXISTS `pro_buddy_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pro_buddy_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `balance` double NOT NULL,
  `bank_account_number` varchar(255) DEFAULT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3fhd0aanpvdy316mumiaqk0nt` (`user_id`),
  CONSTRAINT `FK3fhd0aanpvdy316mumiaqk0nt` FOREIGN KEY (`user_id`) REFERENCES `pro_buddy_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pro_buddy_contacts`
--

DROP TABLE IF EXISTS `pro_buddy_contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pro_buddy_contacts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_user_id` int DEFAULT NULL,
  `second_user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKluwjjkv4uonq1bii0xa1cb659` (`first_user_id`),
  KEY `FK28qklflg9e81wmh0ewov7tmyw` (`second_user_id`),
  CONSTRAINT `FK28qklflg9e81wmh0ewov7tmyw` FOREIGN KEY (`second_user_id`) REFERENCES `pro_buddy_user` (`id`),
  CONSTRAINT `FKluwjjkv4uonq1bii0xa1cb659` FOREIGN KEY (`first_user_id`) REFERENCES `pro_buddy_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pro_buddy_login`
--

DROP TABLE IF EXISTS `pro_buddy_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pro_buddy_login` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_on` datetime NOT NULL,
  `success` bit(1) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt0vh6a6jrpqfysobb0e9vedun` (`user_id`),
  CONSTRAINT `FKt0vh6a6jrpqfysobb0e9vedun` FOREIGN KEY (`user_id`) REFERENCES `pro_buddy_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pro_buddy_role`
--

DROP TABLE IF EXISTS `pro_buddy_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pro_buddy_role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pro_buddy_transactions`
--

DROP TABLE IF EXISTS `pro_buddy_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pro_buddy_transactions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `fee` double NOT NULL,
  `receiver_user_id` int DEFAULT NULL,
  `receiver_account_id` int DEFAULT NULL,
  `sender_user_id` int DEFAULT NULL,
  `sender_account_id` int DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdhebru9gehl1dj9hjfu6kr3s8` (`receiver_user_id`),
  KEY `FKcqev0d8q9qnihp2gld7nrjngn` (`receiver_account_id`),
  KEY `FKpsikxj8vsmtsxkcipccx0k0th` (`sender_user_id`),
  KEY `FK42vj7smkitjqu47scmlkk4a2a` (`sender_account_id`),
  CONSTRAINT `FK42vj7smkitjqu47scmlkk4a2a` FOREIGN KEY (`sender_account_id`) REFERENCES `pro_buddy_account` (`id`),
  CONSTRAINT `FKcqev0d8q9qnihp2gld7nrjngn` FOREIGN KEY (`receiver_account_id`) REFERENCES `pro_buddy_account` (`id`),
  CONSTRAINT `FKdhebru9gehl1dj9hjfu6kr3s8` FOREIGN KEY (`receiver_user_id`) REFERENCES `pro_buddy_user` (`id`),
  CONSTRAINT `FKpsikxj8vsmtsxkcipccx0k0th` FOREIGN KEY (`sender_user_id`) REFERENCES `pro_buddy_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pro_buddy_user`
--

DROP TABLE IF EXISTS `pro_buddy_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pro_buddy_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `age` int NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `nationalid` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pro_user_role`
--

DROP TABLE IF EXISTS `pro_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pro_user_role` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK1ofil8j5x1vg0fnjukt84gb0b` (`role_id`),
  CONSTRAINT `FK1ofil8j5x1vg0fnjukt84gb0b` FOREIGN KEY (`role_id`) REFERENCES `pro_buddy_role` (`role_id`),
  CONSTRAINT `FKsyo3079lbs0i66bqcg1f8176o` FOREIGN KEY (`user_id`) REFERENCES `pro_buddy_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-02 14:14:54
