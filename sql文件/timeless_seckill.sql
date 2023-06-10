/*
 Navicat Premium Data Transfer

 Source Server         : 1
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : timeless_seckill

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 10/06/2023 21:53:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_order_info
-- ----------------------------
DROP TABLE IF EXISTS `t_order_info`;
CREATE TABLE `t_order_info`  (
  `order_no` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `user_id` bigint(0) NULL DEFAULT NULL,
  `product_id` bigint(0) NULL DEFAULT NULL,
  `seckill_id` bigint(0) NULL DEFAULT NULL,
  `product_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `product_price` decimal(10, 2) NULL DEFAULT NULL,
  `seckill_price` decimal(10, 2) NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `pay_date` datetime(0) NULL DEFAULT NULL,
  `seckill_date` date NULL DEFAULT NULL,
  PRIMARY KEY (`order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order_info
-- ----------------------------
INSERT INTO `t_order_info` VALUES ('1662896064002260992', 1000, 23, 2, '华为nova7pro 8G+128G全网通', 4399.00, 3699.00, '已退款', '2023-05-29 02:58:11', '2023-05-29 02:58:42', NULL);
INSERT INTO `t_order_info` VALUES ('1662896459508350976', 1000, 24, 3, 'VAIO 11代新品笔记本', 4999.00, 2999.00, '已取消', '2023-05-29 02:59:46', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1663188909569343488', 1000, 24, 3, 'VAIO 11代新品笔记本', 4999.00, 2999.00, '已取消', '2023-05-29 22:21:51', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1663190125510656000', 1000, 24, 3, 'VAIO 11代新品笔记本', 4999.00, 2999.00, '已取消', '2023-05-29 22:26:41', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1663190464502693888', 1000, 24, 3, 'VAIO 11代新品笔记本', 4999.00, 2999.00, '已退款', '2023-05-29 22:28:02', '2023-05-29 22:28:52', NULL);
INSERT INTO `t_order_info` VALUES ('1663196282304331776', 1000, 31, 10, 'Apple Watch S5 GPS款 40毫米', 3199.00, 2399.00, '已取消', '2023-05-29 22:51:09', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1663196872300298240', 1000, 31, 10, 'Apple Watch S5 GPS款 40毫米', 3199.00, 2399.00, '已退款', '2023-05-29 22:53:30', '2023-05-29 22:54:21', NULL);
INSERT INTO `t_order_info` VALUES ('1663199405999652864', 1000, 31, 10, 'Apple Watch S5 GPS款 40毫米', 3199.00, 2399.00, '已退款', '2023-05-29 23:03:34', '2023-05-29 23:04:05', NULL);
INSERT INTO `t_order_info` VALUES ('1663199889774870528', 1000, 31, 10, 'Apple Watch S5 GPS款 40毫米', 3199.00, 2399.00, '已取消', '2023-05-29 23:05:29', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1663202711316725760', 1000, 31, 10, 'Apple Watch S5 GPS款 40毫米', 3199.00, 2399.00, '已取消', '2023-05-29 23:16:42', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1663204373959802880', 1000, 27, 6, '雅鹿品牌 加绒加厚  连帽夹克男', 199.00, 59.00, '已取消', '2023-05-29 23:23:18', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1663204376233115648', 1000, 27, 6, '雅鹿品牌 加绒加厚  连帽夹克男', 199.00, 59.00, '已取消', '2023-05-29 23:23:19', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1663204378368016384', 1000, 27, 6, '雅鹿品牌 加绒加厚  连帽夹克男', 199.00, 59.00, '已取消', '2023-05-29 23:23:19', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1663204380230287360', 1000, 27, 6, '雅鹿品牌 加绒加厚  连帽夹克男', 199.00, 59.00, '已取消', '2023-05-29 23:23:20', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1663204382042226688', 1000, 27, 6, '雅鹿品牌 加绒加厚  连帽夹克男', 199.00, 59.00, '已取消', '2023-05-29 23:23:20', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1663204383753502720', 1000, 27, 6, '雅鹿品牌 加绒加厚  连帽夹克男', 199.00, 59.00, '已取消', '2023-05-29 23:23:21', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1663204385561247744', 1000, 27, 6, '雅鹿品牌 加绒加厚  连帽夹克男', 199.00, 59.00, '已取消', '2023-05-29 23:23:21', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1663204387230580736', 1000, 27, 6, '雅鹿品牌 加绒加厚  连帽夹克男', 199.00, 59.00, '已取消', '2023-05-29 23:23:21', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1663204388946051072', 1000, 27, 6, '雅鹿品牌 加绒加厚  连帽夹克男', 199.00, 59.00, '已取消', '2023-05-29 23:23:22', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1663204390724435968', 1000, 27, 6, '雅鹿品牌 加绒加厚  连帽夹克男', 199.00, 59.00, '已取消', '2023-05-29 23:23:22', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1663206427801419776', 1000, 28, 7, '创维10公斤大容量变频滚筒', 2399.00, 1699.00, '已付款', '2023-05-29 23:31:28', '2023-05-29 23:31:57', NULL);
INSERT INTO `t_order_info` VALUES ('1665721950493736960', 1000, 23, 2, '华为nova7pro 8G+128G全网通', 4399.00, 3699.00, '已取消', '2023-06-05 22:07:15', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1665722809160040448', 1000, 23, 2, '华为nova7pro 8G+128G全网通', 4399.00, 3699.00, '已取消', '2023-06-05 22:10:40', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1665725918653448192', 1000, 23, 2, '华为nova7pro 8G+128G全网通', 4399.00, 3699.00, '已退款', '2023-06-05 22:23:01', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1665726633304129536', 1000, 23, 2, '华为nova7pro 8G+128G全网通', 4399.00, 3699.00, '已退款', '2023-06-05 22:25:52', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1665727582483513344', 1000, 23, 2, '华为nova7pro 8G+128G全网通', 4399.00, 3699.00, '已付款', '2023-06-05 22:29:38', '2023-06-05 22:30:01', NULL);
INSERT INTO `t_order_info` VALUES ('1665732869953159168', 1000, 23, 2, '华为nova7pro 8G+128G全网通', 4399.00, 3699.00, '已付款', '2023-06-05 22:50:39', '2023-06-05 22:51:15', NULL);
INSERT INTO `t_order_info` VALUES ('1665733196345507840', 1000, 23, 2, '华为nova7pro 8G+128G全网通', 4399.00, 3699.00, '已取消', '2023-06-05 22:51:56', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1665737396513144832', 1000, 23, 2, '华为nova7pro 8G+128G全网通', 4399.00, 3699.00, '已取消', '2023-06-05 23:08:38', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1665737969073389568', 1000, 23, 2, '华为nova7pro 8G+128G全网通', 4399.00, 3699.00, '已取消', '2023-06-05 23:10:54', NULL, NULL);
INSERT INTO `t_order_info` VALUES ('1665738197763620864', 1000, 24, 3, 'VAIO 11代新品笔记本', 4999.00, 2999.00, '已付款', '2023-06-05 23:11:49', '2023-06-05 23:12:26', NULL);
INSERT INTO `t_order_info` VALUES ('1665739153737777152', 1000, 24, 3, 'VAIO 11代新品笔记本', 4999.00, 2999.00, '已退款', '2023-06-05 23:15:37', NULL, NULL);

-- ----------------------------
-- Table structure for t_seckill_product
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_product`;
CREATE TABLE `t_seckill_product`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(0) NULL DEFAULT NULL,
  `seckill_price` decimal(10, 2) NULL DEFAULT NULL,
  `stock_count` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_seckill_product
-- ----------------------------
INSERT INTO `t_seckill_product` VALUES (2, 23, 3699.00, 10);
INSERT INTO `t_seckill_product` VALUES (3, 24, 2999.00, 4);
INSERT INTO `t_seckill_product` VALUES (4, 25, 9.00, 10);
INSERT INTO `t_seckill_product` VALUES (5, 26, 899.00, 8);
INSERT INTO `t_seckill_product` VALUES (6, 27, 59.00, 0);
INSERT INTO `t_seckill_product` VALUES (7, 28, 1699.00, 9);
INSERT INTO `t_seckill_product` VALUES (8, 29, 3999.00, 6);
INSERT INTO `t_seckill_product` VALUES (9, 30, 1099.00, 9);
INSERT INTO `t_seckill_product` VALUES (10, 31, 2399.00, 2);
INSERT INTO `t_seckill_product` VALUES (11, 32, 799.00, 10);
INSERT INTO `t_seckill_product` VALUES (12, 33, 199.00, 10);
INSERT INTO `t_seckill_product` VALUES (13, 34, 949.00, 10);
INSERT INTO `t_seckill_product` VALUES (14, 35, 2499.00, 10);
INSERT INTO `t_seckill_product` VALUES (15, 36, 1199.00, 10);
INSERT INTO `t_seckill_product` VALUES (16, 37, 5999.00, 7);
INSERT INTO `t_seckill_product` VALUES (17, 38, 9.00, 10);
INSERT INTO `t_seckill_product` VALUES (18, 39, 999.00, 10);
INSERT INTO `t_seckill_product` VALUES (19, 40, 659.00, 9);
INSERT INTO `t_seckill_product` VALUES (20, 41, 1999.00, 10);
INSERT INTO `t_seckill_product` VALUES (21, 42, 3399.00, 10);

SET FOREIGN_KEY_CHECKS = 1;
