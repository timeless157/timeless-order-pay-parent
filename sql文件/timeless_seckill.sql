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

 Date: 17/06/2023 23:18:03
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
  `status` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '待付款',
  `create_date` datetime(0) NULL DEFAULT NULL,
  `pay_date` datetime(0) NULL DEFAULT NULL,
  `seckill_date` date NULL DEFAULT NULL,
  `pay_price` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_order_info
-- ----------------------------

-- ----------------------------
-- Table structure for t_order_to_product
-- ----------------------------
DROP TABLE IF EXISTS `t_order_to_product`;
CREATE TABLE `t_order_to_product`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单编号',
  `product_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `product_count` int(0) NULL DEFAULT NULL COMMENT '商品数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order_to_product
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_seckill_product
-- ----------------------------
INSERT INTO `t_seckill_product` VALUES (2, 23, 3699.00, 100);
INSERT INTO `t_seckill_product` VALUES (3, 24, 2999.00, 100);
INSERT INTO `t_seckill_product` VALUES (4, 25, 9.00, 100);
INSERT INTO `t_seckill_product` VALUES (5, 26, 899.00, 100);
INSERT INTO `t_seckill_product` VALUES (6, 27, 59.00, 98);
INSERT INTO `t_seckill_product` VALUES (7, 28, 1699.00, 100);
INSERT INTO `t_seckill_product` VALUES (8, 29, 3999.00, 100);
INSERT INTO `t_seckill_product` VALUES (9, 30, 1099.00, 100);
INSERT INTO `t_seckill_product` VALUES (10, 31, 2399.00, 100);
INSERT INTO `t_seckill_product` VALUES (11, 32, 799.00, 100);
INSERT INTO `t_seckill_product` VALUES (12, 33, 199.00, 100);
INSERT INTO `t_seckill_product` VALUES (13, 34, 949.00, 100);
INSERT INTO `t_seckill_product` VALUES (14, 35, 2499.00, 100);
INSERT INTO `t_seckill_product` VALUES (15, 36, 1199.00, 100);
INSERT INTO `t_seckill_product` VALUES (16, 37, 5999.00, 100);
INSERT INTO `t_seckill_product` VALUES (17, 38, 9.00, 100);
INSERT INTO `t_seckill_product` VALUES (18, 39, 999.00, 100);
INSERT INTO `t_seckill_product` VALUES (19, 40, 659.00, 100);
INSERT INTO `t_seckill_product` VALUES (20, 41, 1999.00, 100);
INSERT INTO `t_seckill_product` VALUES (21, 42, 3399.00, 100);

-- ----------------------------
-- Table structure for t_shop_cart
-- ----------------------------
DROP TABLE IF EXISTS `t_shop_cart`;
CREATE TABLE `t_shop_cart`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `shop_cart_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '购物车id',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `product_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `product_count` int(0) NULL DEFAULT NULL COMMENT '商品数量',
  `product_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_shop_cart
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
