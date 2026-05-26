CREATE DATABASE  IF NOT EXISTS `mesos_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mesos_db`;
-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: mesos_db
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
-- /*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `games`
--

DROP TABLE IF EXISTS `games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `games` (
  `game_id` int NOT NULL AUTO_INCREMENT,
  `game_date` date NOT NULL,
  `num_players` int NOT NULL,
  PRIMARY KEY (`game_id`),
  CONSTRAINT `games_chk_1` CHECK (((`num_players` >= 2) and (`num_players` <= 5)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `leaderboard`
--

DROP TABLE IF EXISTS `leaderboard`;
/*!50001 DROP VIEW IF EXISTS `leaderboard`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `leaderboard` AS SELECT 
 1 AS `player_username`,
 1 AS `total_prestige_points`,
 1 AS `total_food`,
 1 AS `games_played`,
 1 AS `num_players`,
 1 AS `player_rank`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `participation`
--

DROP TABLE IF EXISTS `participation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `participation` (
  `game_id` int NOT NULL,
  `player_username` varchar(15) NOT NULL,
  `prestige_points` int NOT NULL,
  `food` int NOT NULL,
  PRIMARY KEY (`game_id`,`player_username`),
  CONSTRAINT `fk_participation_game` FOREIGN KEY (`game_id`) REFERENCES `games` (`game_id`) ON UPDATE CASCADE,
  CONSTRAINT `participation_chk_1` CHECK ((`food` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'mesos_db'
--

--
-- Dumping routines for database 'mesos_db'
--

--
-- Final view structure for view `leaderboard`
--

/*!50001 DROP VIEW IF EXISTS `leaderboard`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `leaderboard` AS with `stats` as (select `participation`.`player_username` AS `player_username`,sum(`participation`.`prestige_points`) AS `total_prestige_points`,sum(`participation`.`food`) AS `total_food`,`games`.`num_players` AS `num_players`,count(distinct `participation`.`game_id`) AS `games_played` from (`participation` join `games` on((`games`.`game_id` = `participation`.`game_id`))) group by `participation`.`player_username`,`games`.`num_players`) select `stats_outer`.`player_username` AS `player_username`,`stats_outer`.`total_prestige_points` AS `total_prestige_points`,`stats_outer`.`total_food` AS `total_food`,`stats_outer`.`games_played` AS `games_played`,`stats_outer`.`num_players` AS `num_players`,(select (count(distinct `stats_inner`.`player_username`) + 1) from `stats` `stats_inner` where ((`stats_outer`.`num_players` = `stats_inner`.`num_players`) and ((`stats_outer`.`total_prestige_points` < `stats_inner`.`total_prestige_points`) or ((`stats_outer`.`total_prestige_points` = `stats_inner`.`total_prestige_points`) and (`stats_outer`.`total_food` < `stats_inner`.`total_food`)) or ((`stats_outer`.`total_prestige_points` = `stats_inner`.`total_prestige_points`) and (`stats_outer`.`total_food` = `stats_inner`.`total_food`) and (`stats_outer`.`games_played` < `stats_inner`.`games_played`)) or ((`stats_outer`.`total_prestige_points` = `stats_inner`.`total_prestige_points`) and (`stats_outer`.`total_food` = `stats_inner`.`total_food`) and (`stats_outer`.`games_played` = `stats_inner`.`games_played`) and (`stats_outer`.`player_username` < `stats_inner`.`player_username`))))) AS `player_rank` from `stats` `stats_outer` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
-- /*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-25 23:04:07
