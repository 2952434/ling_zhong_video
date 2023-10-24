/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80019 (8.0.19)
 Source Host           : localhost:3306
 Source Schema         : web_video

 Target Server Type    : MySQL
 Target Server Version : 80019 (8.0.19)
 File Encoding         : 65001

 Date: 24/10/2023 23:53:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment_reply
-- ----------------------------
DROP TABLE IF EXISTS `comment_reply`;
CREATE TABLE `comment_reply`  (
  `id` int NOT NULL COMMENT '主键id',
  `video_or_comment_id` int NOT NULL COMMENT '如果是评论为视频id，如果是回复是评论id',
  `user_id` int NOT NULL COMMENT '发表回复的用户id',
  `be_user_id` int NOT NULL COMMENT '被回复或评论的用户id',
  `comment_or_reply` tinyint(1) NOT NULL COMMENT '0：评论，1：回复',
  `reply_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '回复的内容',
  `reply_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '发表时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment_reply
-- ----------------------------

-- ----------------------------
-- Table structure for label
-- ----------------------------
DROP TABLE IF EXISTS `label`;
CREATE TABLE `label`  (
  `
label_id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `
label_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名',
  `
user_id` int NOT NULL DEFAULT -1 COMMENT '添加标签的用户id（默认展示为-1）',
  PRIMARY KEY (`
label_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of label
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `user_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `user_account` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `user_password` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `user_photo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户头像',
  `user_sex` tinyint(1) NOT NULL DEFAULT 0 COMMENT '用户性别（0：女，1：男）',
  `user_describe` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户描述',
  `user_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '账号创建时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_attention
-- ----------------------------
DROP TABLE IF EXISTS `user_attention`;
CREATE TABLE `user_attention`  (
  `attention_id` int NOT NULL COMMENT '用户关注主键',
  `user_id` int NOT NULL COMMENT '用户id',
  `be_user_id` int NOT NULL COMMENT '被关注的用户id',
  `attention_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '关注的时间',
  PRIMARY KEY (`attention_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_attention
-- ----------------------------

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video`  (
  `video_id` int NOT NULL COMMENT '视频id',
  `video_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '视频名',
  `video_url` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '视频url地址',
  `video_user_id` int NOT NULL COMMENT '发视频用户id',
  `video_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频发表的地理位置',
  `video_private` tinyint(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0 COMMENT '(0：公开，1：私有)',
  `video_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '发表时间',
  PRIMARY KEY (`video_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video
-- ----------------------------

-- ----------------------------
-- Table structure for video_collect
-- ----------------------------
DROP TABLE IF EXISTS `video_collect`;
CREATE TABLE `video_collect`  (
  `collect_id` int NOT NULL COMMENT '收藏主键',
  `video_id` int NOT NULL COMMENT '收藏的视频id',
  `user_id` int NOT NULL COMMENT '用户id',
  `be_user_id` int NOT NULL COMMENT '被收藏的用户id（用于收藏消息提醒）',
  `collect_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '收藏时间',
  PRIMARY KEY (`collect_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video_collect
-- ----------------------------

-- ----------------------------
-- Table structure for video_label
-- ----------------------------
DROP TABLE IF EXISTS `video_label`;
CREATE TABLE `video_label`  (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `video_id` int NOT NULL COMMENT '视频id',
  `label_id` int NOT NULL COMMENT '视频绑定的标签id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video_label
-- ----------------------------

-- ----------------------------
-- Table structure for video_like
-- ----------------------------
DROP TABLE IF EXISTS `video_like`;
CREATE TABLE `video_like`  (
  `like_id` int NOT NULL COMMENT '点赞主键id',
  `video_id` int NOT NULL COMMENT '点赞的视频id',
  `user_id` int NOT NULL COMMENT '点赞的用户id',
  `be_user_id` int NOT NULL COMMENT '被点赞用户的id(用于消息提醒)',
  `like_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '点赞时间（用于排序）',
  PRIMARY KEY (`like_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video_like
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
