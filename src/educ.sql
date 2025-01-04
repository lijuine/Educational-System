/*
 Navicat Premium Dump SQL

 Source Server         : 测试
 Source Server Type    : MySQL
 Source Server Version : 80400 (8.4.0)
 Source Host           : localhost:3306
 Source Schema         : educ

 Target Server Type    : MySQL
 Target Server Version : 80400 (8.4.0)
 File Encoding         : 65001

 Date: 02/01/2025 15:05:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '教务id',
  `name` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '名字',
  `password` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '123456789' COMMENT '密码',
  `code` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '工号(登录)',
  `remark` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '辅助描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dean_un`(`code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '教务(设置专业、编制班级、开设课程、组织选课等)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, '信工教学秘书1', '123456789', '234003', NULL);
INSERT INTO `admin` VALUES (2, '信工教学秘书2', '123456789', '234004', NULL);
INSERT INTO `admin` VALUES (3, '软件教学秘书1', '123456789', '234005', NULL);
INSERT INTO `admin` VALUES (4, '软件教学秘书2', '123456789', '234006', NULL);
INSERT INTO `admin` VALUES (5, '电子教学秘书', '123456789', '234007', NULL);
INSERT INTO `admin` VALUES (6, '计算机教学秘书', '123456789', '234008', NULL);

-- ----------------------------
-- Table structure for classgroup
-- ----------------------------
DROP TABLE IF EXISTS `classgroup`;
CREATE TABLE `classgroup`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '班级id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '班级编号',
  `speciality_id` int NULL DEFAULT -1 COMMENT '专业id',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '辅助描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `class_un`(`code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '班级(比如2020级软件工程300班)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classgroup
-- ----------------------------
INSERT INTO `classgroup` VALUES (1, '2022320软件工程', '2022320', 1, '');
INSERT INTO `classgroup` VALUES (2, '2022420软件工程T', '2022420T', 3, '');
INSERT INTO `classgroup` VALUES (3, '2023330软件工程', '2023330', 2, '');
INSERT INTO `classgroup` VALUES (4, '2023430软件工程T', '2023430T', 4, '');
INSERT INTO `classgroup` VALUES (5, '2024340软件工程', '2024340', 1, '');
INSERT INTO `classgroup` VALUES (6, '2024440软件工程T', '2024440T', 3, '');
INSERT INTO `classgroup` VALUES (8, '2025摸鱼大王', '202301', 13, '测试');

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '学院id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '辅助描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学院(包含教师、班级)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of college
-- ----------------------------
INSERT INTO `college` VALUES (1, '信息工程学院', NULL);
INSERT INTO `college` VALUES (2, '软件学院', NULL);
INSERT INTO `college` VALUES (3, '电子工程学院', NULL);
INSERT INTO `college` VALUES (4, '计算机学院', NULL);
INSERT INTO `college` VALUES (5, '自动化学院', NULL);
INSERT INTO `college` VALUES (6, '通信工程学院', NULL);

-- ----------------------------
-- Table structure for speciality
-- ----------------------------
DROP TABLE IF EXISTS `speciality`;
CREATE TABLE `speciality`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '专业id',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '名称',
  `grade` year NOT NULL COMMENT '年级',
  `code` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '学科代码',
  `college_id` int NULL DEFAULT -1 COMMENT '学院id',
  `remark` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '辅助描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '专业(比如2020级特色软件工程)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of speciality
-- ----------------------------
INSERT INTO `speciality` VALUES (1, '2022级软件工程', 2022, '070402', 1, NULL);
INSERT INTO `speciality` VALUES (2, '2023级软件工程', 2023, '070402', 1, NULL);
INSERT INTO `speciality` VALUES (3, '2022级特色软件工程', 2022, '070402', 2, NULL);
INSERT INTO `speciality` VALUES (4, '2023级特色软件工程', 2023, '070402', 2, NULL);
INSERT INTO `speciality` VALUES (5, '2024级网络工程', 2024, '080903', 3, NULL);
INSERT INTO `speciality` VALUES (6, '2024级人工智能', 2024, '080717', 4, NULL);
INSERT INTO `speciality` VALUES (13, '2025摆烂大王', 2025, '202500', 4, '色佛瑞斯v');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '学生id',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '姓名',
  `password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '123456789' COMMENT '密码',
  `code` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '学号(登录)',
  `classgroup_id` int NULL DEFAULT -1 COMMENT '班级id',
  `remark` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '辅助描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `student_un`(`code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '学生(班级决定了其专业名称、年级、学院)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '学生9', '123456789', '202232001', 1, NULL);
INSERT INTO `student` VALUES (2, '学生10', '123456789', '202232002', 1, NULL);
INSERT INTO `student` VALUES (3, '学生11', '123456789', '202242001', 2, NULL);
INSERT INTO `student` VALUES (4, '学生12', '123456789', '202242002', 2, NULL);
INSERT INTO `student` VALUES (5, '学生13', '123456789', '202333001', 3, NULL);
INSERT INTO `student` VALUES (6, '学生14', '123456789', '202333002', 3, NULL);
INSERT INTO `student` VALUES (10, '李俊', '123456789', '202500', 8, '');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '教师id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '123456789' COMMENT '密码',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '工号(登录)',
  `college_id` int NULL DEFAULT -1 COMMENT '学院id',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '辅助描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `teacher_un`(`code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '教师(划归某个教学单位)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, '孙乐', '123456789', '234014', 1, NULL);
INSERT INTO `teacher` VALUES (2, '周利', '123456789', '234015', 1, NULL);
INSERT INTO `teacher` VALUES (3, '吴九', '123456789', '234016', 2, NULL);
INSERT INTO `teacher` VALUES (4, '郑大', '123456789', '234017', 2, NULL);
INSERT INTO `teacher` VALUES (5, '钱三一', '123456789', '234018', 3, NULL);
INSERT INTO `teacher` VALUES (6, '冯十二', '123456789', '234019', 4, NULL);

SET FOREIGN_KEY_CHECKS = 1;
