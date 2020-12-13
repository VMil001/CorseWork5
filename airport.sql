/*
 Navicat Premium Data Transfer

 Source Server         : Databases
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : airport

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 13/12/2020 06:40:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `idadmin` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`idadmin`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin');

-- ----------------------------
-- Table structure for agesales
-- ----------------------------
DROP TABLE IF EXISTS `agesales`;
CREATE TABLE `agesales`  (
  `idud` int NOT NULL,
  `sale` int NOT NULL DEFAULT 0,
  INDEX `salefk_idx`(`idud`) USING BTREE,
  CONSTRAINT `salefk` FOREIGN KEY (`idud`) REFERENCES `userdata` (`idUD`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of agesales
-- ----------------------------
INSERT INTO `agesales` VALUES (299, 0);
INSERT INTO `agesales` VALUES (300, 20);
INSERT INTO `agesales` VALUES (301, 0);

-- ----------------------------
-- Table structure for bilet
-- ----------------------------
DROP TABLE IF EXISTS `bilet`;
CREATE TABLE `bilet`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `airport` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `timeOut` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `timeIn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bilet
-- ----------------------------
INSERT INTO `bilet` VALUES (4, 'Милан', '25.12.2020', '10:30', '15:10', 'Максим', 'Дмитриев');
INSERT INTO `bilet` VALUES (6, 'Рим', '28.12.2020', '18:00', '21:00', 'Максим', 'Дмитриев');
INSERT INTO `bilet` VALUES (7, 'Рим', '28.12.2020', '18:00', '21:00', 'Максим', 'Дмитриев');

-- ----------------------------
-- Table structure for client
-- ----------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client`  (
  `idclient` int NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `surname` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sex` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `country` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`idclient`) USING BTREE,
  CONSTRAINT `idUserClFK` FOREIGN KEY (`idclient`) REFERENCES `users` (`idUsers`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of client
-- ----------------------------
INSERT INTO `client` VALUES (1, 'Владимир', 'Милентьев', 'Мужской', 'Беларусь');
INSERT INTO `client` VALUES (20, 'Максим', 'Дмитриев', 'Мужской', 'Россия');
INSERT INTO `client` VALUES (28, 'Иван', 'Иванов', 'Мужской', 'Россия');
INSERT INTO `client` VALUES (29, 'Антон', 'Антонов', 'Дpугой', 'Украина');

-- ----------------------------
-- Table structure for flight
-- ----------------------------
DROP TABLE IF EXISTS `flight`;
CREATE TABLE `flight`  (
  `idF` int NOT NULL AUTO_INCREMENT,
  `price` int NOT NULL,
  `idMD` int NOT NULL,
  `seatsAmount` int NULL DEFAULT NULL,
  PRIMARY KEY (`idF`) USING BTREE,
  INDEX `FlMainDataFK_idx`(`idMD`) USING BTREE,
  CONSTRAINT `FlMainDataFK` FOREIGN KEY (`idMD`) REFERENCES `maindata` (`idmd`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of flight
-- ----------------------------
INSERT INTO `flight` VALUES (22, 130, 30, 7);
INSERT INTO `flight` VALUES (23, 130, 31, 7);
INSERT INTO `flight` VALUES (24, 110, 32, 26);

-- ----------------------------
-- Table structure for laggage
-- ----------------------------
DROP TABLE IF EXISTS `laggage`;
CREATE TABLE `laggage`  (
  `iduserdata` int NOT NULL,
  `lprice` int NOT NULL,
  INDEX `lafk_idx`(`iduserdata`) USING BTREE,
  CONSTRAINT `lafk` FOREIGN KEY (`iduserdata`) REFERENCES `userdata` (`idUD`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of laggage
-- ----------------------------
INSERT INTO `laggage` VALUES (299, 0);
INSERT INTO `laggage` VALUES (300, 20);
INSERT INTO `laggage` VALUES (301, 0);

-- ----------------------------
-- Table structure for maindata
-- ----------------------------
DROP TABLE IF EXISTS `maindata`;
CREATE TABLE `maindata`  (
  `idmd` int NOT NULL AUTO_INCREMENT,
  `airport` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `outtime` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `intime` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `outairport` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Минск',
  PRIMARY KEY (`idmd`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of maindata
-- ----------------------------
INSERT INTO `maindata` VALUES (30, 'Минск', '24.12.2020', '10:30', '15:10', 'Милан');
INSERT INTO `maindata` VALUES (31, 'Милан', '25.12.2020', '10:30', '15:10', 'Минск');
INSERT INTO `maindata` VALUES (32, 'Рим', '28.12.2020', '18:00', '21:00', 'Минск');

-- ----------------------------
-- Table structure for ticket
-- ----------------------------
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket`  (
  `idticket` int NOT NULL AUTO_INCREMENT,
  `idus` int NOT NULL,
  `generalPrice` int NOT NULL,
  `idFly` int NULL DEFAULT NULL,
  PRIMARY KEY (`idticket`) USING BTREE,
  INDEX `index4`(`generalPrice`) USING BTREE,
  INDEX `idudidUDFK_idx`(`idus`) USING BTREE,
  CONSTRAINT `idudidUDFK` FOREIGN KEY (`idus`) REFERENCES `users` (`idUsers`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ticket
-- ----------------------------
INSERT INTO `ticket` VALUES (40, 20, 228, NULL);
INSERT INTO `ticket` VALUES (41, 20, 316, NULL);
INSERT INTO `ticket` VALUES (42, 20, 124, NULL);
INSERT INTO `ticket` VALUES (43, 20, 129, NULL);
INSERT INTO `ticket` VALUES (44, 20, 258, NULL);
INSERT INTO `ticket` VALUES (45, 20, 258, NULL);
INSERT INTO `ticket` VALUES (46, 20, 129, NULL);
INSERT INTO `ticket` VALUES (47, 20, 367, NULL);
INSERT INTO `ticket` VALUES (48, 20, 367, NULL);

-- ----------------------------
-- Table structure for userdata
-- ----------------------------
DROP TABLE IF EXISTS `userdata`;
CREATE TABLE `userdata`  (
  `idUD` int NOT NULL AUTO_INCREMENT,
  `idu` int NOT NULL,
  `laggage` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `age` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `idf` int NULL DEFAULT NULL,
  PRIMARY KEY (`idUD`) USING BTREE,
  INDEX `iduidUFK_idx`(`idu`) USING BTREE,
  INDEX `idfidFFK_idx`(`idf`) USING BTREE,
  CONSTRAINT `idfidFFK` FOREIGN KEY (`idf`) REFERENCES `flight` (`idF`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `iduidUFK` FOREIGN KEY (`idu`) REFERENCES `users` (`idUsers`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 299 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of userdata
-- ----------------------------
INSERT INTO `userdata` VALUES (299, 20, 'Pучная кладь', 'Взpослые(16+ лет)', 24);
INSERT INTO `userdata` VALUES (300, 20, 'Основной багаж', 'Подpостки(12-15 лет)', 24);
INSERT INTO `userdata` VALUES (301, 20, 'Pучная кладь', 'Взpослые(16+ лет)', 24);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `idUsers` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `isBlocked` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'no',
  PRIMARY KEY (`idUsers`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', 'admin', 'no');
INSERT INTO `users` VALUES (20, 'makson', 'makson', 'no');
INSERT INTO `users` VALUES (28, 'ivanov', 'ivanov', 'no');
INSERT INTO `users` VALUES (29, 'antonov', 'antonov', 'no');

SET FOREIGN_KEY_CHECKS = 1;
