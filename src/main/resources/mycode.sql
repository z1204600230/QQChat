/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : localhost:3306
 Source Schema         : mycode

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 14/06/2022 11:12:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend`  (
  `f_mid` int(0) NULL DEFAULT NULL COMMENT '自己的id',
  `f_fid` int(0) NULL DEFAULT NULL COMMENT '朋友的id',
  `f_send` int(0) NULL DEFAULT NULL COMMENT '发送申请(o为发送失败，1为发送成功)',
  `f_accept` int(0) NULL DEFAULT NULL COMMENT '接受(0为未接受，1为接受)'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of friend
-- ----------------------------
INSERT INTO `friend` VALUES (1, 2, 1, 1);
INSERT INTO `friend` VALUES (2, 1, 1, 1);
INSERT INTO `friend` VALUES (1, 4, 1, 1);
INSERT INTO `friend` VALUES (4, 1, 1, 1);
INSERT INTO `friend` VALUES (1, 5, 1, 1);
INSERT INTO `friend` VALUES (5, 1, 1, 1);
INSERT INTO `friend` VALUES (1, 6, 1, 1);
INSERT INTO `friend` VALUES (6, 1, 1, 1);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `m_id` int(0) NOT NULL AUTO_INCREMENT,
  `m_from_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送人id',
  `m_to_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收信人id',
  `m_message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送内容',
  `m_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`m_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (3, '5', '1', '123', '2022-06-04 15:04:08');
INSERT INTO `message` VALUES (4, '5', '1', '123', '2022-06-04 15:23:51');
INSERT INTO `message` VALUES (5, '1', '5', '456', '2022-06-05 09:51:41');
INSERT INTO `message` VALUES (6, '1', '4', '123', '2022-06-05 09:59:08');
INSERT INTO `message` VALUES (7, '1', '4', '456', '2022-06-05 10:00:50');
INSERT INTO `message` VALUES (8, '1', '2', '123', '2022-06-05 10:12:43');
INSERT INTO `message` VALUES (9, '1', '5', '123', '2022-06-05 10:47:44');
INSERT INTO `message` VALUES (10, '1', '5', '1234', '2022-06-05 10:49:44');
INSERT INTO `message` VALUES (11, '5', '1', '123', '2022-06-10 17:28:47');
INSERT INTO `message` VALUES (12, '1', '5', '123', '2022-06-10 17:29:51');
INSERT INTO `message` VALUES (13, '5', '1', '123', '2022-06-10 17:35:04');
INSERT INTO `message` VALUES (14, '1', '5', '123', '2022-06-10 17:45:02');
INSERT INTO `message` VALUES (15, '1', '5', 'sdafgag', '2022-06-10 17:45:12');
INSERT INTO `message` VALUES (16, '5', '1', 'sadgfdsg', '2022-06-10 17:45:21');
INSERT INTO `message` VALUES (17, '1', '5', 'adfsgs', '2022-06-10 17:45:29');
INSERT INTO `message` VALUES (18, '1', '2', '123', '2022-06-11 10:07:34');
INSERT INTO `message` VALUES (19, '1', '2', '123', '2022-06-11 10:26:01');
INSERT INTO `message` VALUES (20, '1', '2', '456', '2022-06-11 10:26:54');
INSERT INTO `message` VALUES (21, '1', '2', 'sdag', '2022-06-11 10:30:45');
INSERT INTO `message` VALUES (22, '1', '2', 'asdg', '2022-06-11 10:34:44');
INSERT INTO `message` VALUES (23, '5', '1', 'asdf', '2022-06-11 10:55:26');
INSERT INTO `message` VALUES (24, '5', '1', 'asddgf', '2022-06-11 10:58:08');
INSERT INTO `message` VALUES (25, '1', '5', 'sadg', '2022-06-11 11:00:15');
INSERT INTO `message` VALUES (26, '5', '1', 'asdg', '2022-06-11 11:02:58');
INSERT INTO `message` VALUES (27, '1', '4', 'asdgfd', '2022-06-11 11:04:42');
INSERT INTO `message` VALUES (28, '5', '1', 'sagf', '2022-06-11 11:08:56');
INSERT INTO `message` VALUES (29, '5', '1', 'qwer', '2022-06-11 11:25:31');
INSERT INTO `message` VALUES (30, '1', '5', 'qwer', '2022-06-11 11:33:52');
INSERT INTO `message` VALUES (31, '5', '1', 'qwer', '2022-06-11 11:35:54');
INSERT INTO `message` VALUES (32, '1', '5', 'qwer', '2022-06-11 11:37:20');
INSERT INTO `message` VALUES (33, '5', '1', 'qwer', '2022-06-11 11:45:34');
INSERT INTO `message` VALUES (34, '5', '1', 'ASF', '2022-06-11 11:47:28');
INSERT INTO `message` VALUES (35, '5', '1', 'fasdf', '2022-06-11 11:56:18');
INSERT INTO `message` VALUES (36, '1', '5', 'sadg', '2022-06-11 11:57:51');
INSERT INTO `message` VALUES (37, '5', '1', 'sadfd', '2022-06-11 12:00:27');
INSERT INTO `message` VALUES (38, '1', '5', 'asdf', '2022-06-11 12:01:03');
INSERT INTO `message` VALUES (39, '5', '1', 'adsg', '2022-06-11 12:12:31');
INSERT INTO `message` VALUES (40, '5', '1', 'dsaf', '2022-06-11 12:15:26');
INSERT INTO `message` VALUES (41, '1', '5', 'asdg', '2022-06-11 12:16:41');
INSERT INTO `message` VALUES (42, '5', '1', 'asFD', '2022-06-11 12:19:09');
INSERT INTO `message` VALUES (43, '1', '5', 'saddfg', '2022-06-11 12:20:42');
INSERT INTO `message` VALUES (44, '5', '1', '6666', '2022-06-11 12:21:37');
INSERT INTO `message` VALUES (45, '1', '5', 'sdafg', '2022-06-11 12:28:37');
INSERT INTO `message` VALUES (46, '1', '5', 'sdaf', '2022-06-11 12:30:35');
INSERT INTO `message` VALUES (47, '5', '1', 'asdg', '2022-06-11 12:40:53');
INSERT INTO `message` VALUES (48, '1', '5', 'asdg', '2022-06-11 12:41:48');
INSERT INTO `message` VALUES (49, '5', '1', 'dfsh', '2022-06-11 12:41:57');
INSERT INTO `message` VALUES (50, '1', '5', 'ewqt', '2022-06-11 12:42:00');
INSERT INTO `message` VALUES (51, '5', '1', 'asddg', '2022-06-11 12:44:35');
INSERT INTO `message` VALUES (52, '1', '5', 'gfdsh', '2022-06-11 12:44:52');
INSERT INTO `message` VALUES (53, '5', '1', 'fasddg', '2022-06-11 12:44:59');
INSERT INTO `message` VALUES (54, '1', '5', 'asdg', '2022-06-11 12:52:09');
INSERT INTO `message` VALUES (55, '5', '1', 'dsag', '2022-06-11 12:52:51');
INSERT INTO `message` VALUES (57, '1', '5', '2022-06-11-21-35-14.jpg', '2022-06-11 21:35:15');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `state` int(0) NULL DEFAULT NULL COMMENT '状态(0表示离线，1表示在线)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '123451', '123451', '张三', 1);
INSERT INTO `user` VALUES (2, '123452', '123452', '李四', 1);
INSERT INTO `user` VALUES (4, '123453', '123453', '王五', 1);
INSERT INTO `user` VALUES (5, '123454', '123454', '吕六', 1);
INSERT INTO `user` VALUES (6, '123455', '123455', '郑七', 0);

SET FOREIGN_KEY_CHECKS = 1;
