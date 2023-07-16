/*
 Navicat Premium Data Transfer

 Source Server         : 1
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : nacos_config

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 17/07/2023 00:45:15
*/

DROP DATABASE IF EXISTS `nacos_config`;

CREATE DATABASE  `nacos_config` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `nacos_config`;


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '增加租户字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_info_beta' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_info_tag' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(0) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id`, `tag_name`, `tag_type`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_tag_relation' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint(0) UNSIGNED NOT NULL,
  `nid` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL,
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
  INDEX `idx_did`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '多租户改造' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info` VALUES (5, 48, 'api-gateway-dev.yaml', 'DEFAULT_GROUP', '', 'server:\r\n  port: 9000\r\nspring:\r\n  cloud:\r\n    gateway:\r\n      discovery:\r\n        locator:\r\n          enabled: true # 让gateway可以发现nacos中的微服务\r\n      routes:\r\n        - id: uaa_route\r\n          uri: lb://uaa-service # lb指的是从nacos中按照名称获取微服务,并遵循负载均 衡策略\r\n          predicates:\r\n            - Path=/uaa/**\r\n          filters:\r\n            - SorderPrefix=1\r\n        - id: product_route\r\n          uri: lb://product-service \r\n          predicates:\r\n            - Path=/product/**\r\n          filters:\r\n            - SorderPrefix=1\r\n        - id: seckill_route\r\n          uri: lb://seckill-service \r\n          predicates:\r\n            - Path=/seckill/**\r\n          filters:\r\n            - SorderPrefix=1\r\n        - id: ws_route\r\n          uri: lb://websocket-service \r\n          predicates:\r\n            - Path=/ws/**\r\n          filters:\r\n            - SorderPrefix=1\r\n\r\n', 'c9081e664851ac49d802a5e56bcc446b', '2023-07-09 00:56:54', '2023-07-09 00:56:54', NULL, '0:0:0:0:0:0:0:1', 'D', '');
INSERT INTO `his_config_info` VALUES (6, 49, 'uaa-service-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 8031\nspring:\n  datasource:\n    url: jdbc:mysql://localhost:3306/shop-uaa?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8\n    driverClassName: com.mysql.cj.jdbc.Driver\n    type: com.alibaba.druid.pool.DruidDataSource\n    username: root\n    password: root\n    maxActive: 1000\n    initialSize: 100\n    maxWait: 60000\n    minIdle: 500\nmybatis:\n  configuration:\n    default-fetch-size: 100\n    default-statement-timeout: 3000\n    map-underscore-to-camel-case: true\n  mapperLocations: classpath:cn/timeless/mapper/*Mapper.xml\nribbon:\n  eager-load:\n    enabled: true\nrocketmq:\n  producer:\n    group: uaa-group', 'b2bbc4e9bcdd87f8499feab1269e0a8c', '2023-07-09 00:56:54', '2023-07-09 00:56:54', NULL, '0:0:0:0:0:0:0:1', 'D', '');
INSERT INTO `his_config_info` VALUES (7, 50, 'redis-config-dev.yaml', 'DEFAULT_GROUP', '', 'spring: \n  redis:\n    host: 127.0.0.1 # 你的redis的Ip地址\n    port: 6379', '32eff913945924820597fdfe25f81e90', '2023-07-09 00:56:54', '2023-07-09 00:56:54', NULL, '0:0:0:0:0:0:0:1', 'D', '');
INSERT INTO `his_config_info` VALUES (8, 51, 'rocketmq-config-dev.yaml', 'DEFAULT_GROUP', '', 'rocketmq:\n  name-server: 192.168.111.100:9876 # 你的RocketMQ的Ip地址:9876', '3729b6789e482b9a855c4352121ecb32', '2023-07-09 00:56:54', '2023-07-09 00:56:54', NULL, '0:0:0:0:0:0:0:1', 'D', '');
INSERT INTO `his_config_info` VALUES (9, 52, 'product-service-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 8041\nspring:\n  datasource:\n    url: jdbc:mysql://localhost:3306/shop-product?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8\n    driverClassName: com.mysql.cj.jdbc.Driver\n    type: com.alibaba.druid.pool.DruidDataSource\n    username: root\n    password: root\n    maxActive: 1000\n    initialSize: 100\n    maxWait: 60000\n    minIdle: 500\nmybatis:\n  configuration:\n    default-fetch-size: 100\n    default-statement-timeout: 3000\n    map-underscore-to-camel-case: true\n  mapperLocations: classpath:cn/timeless/mapper/*Mapper.xml\nribbon:\n  eager-load:\n    enabled: true', '16c090dfafc279055e8734cd84ba84c9', '2023-07-09 00:56:54', '2023-07-09 00:56:54', NULL, '0:0:0:0:0:0:0:1', 'D', '');
INSERT INTO `his_config_info` VALUES (10, 53, 'seckill-service-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 8061\nspring:\n  datasource:\n    url: jdbc:mysql://localhost:3306/shop-seckill?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8\n    driverClassName: com.mysql.cj.jdbc.Driver\n    type: com.alibaba.druid.pool.DruidDataSource\n    username: root\n    password: root\n    maxActive: 1000\n    initialSize: 100\n    maxWait: 60000\n    minIdle: 500\nmybatis:\n  configuration:\n    default-fetch-size: 100\n    default-statement-timeout: 3000\n    map-underscore-to-camel-case: true\n  mapperLocations: classpath:cn/timeless/mapper/*Mapper.xml\nribbon:\n  eager-load:\n    enabled: true\n  ReadTimeout: 10000            \n  ConnectTimeout: 10000          \n  MaxAutoRetries: 0             \n  MaxAutoRetriesNextServer: 0  \nrocketmq:\n  producer:\n    group: seckill-group\n\npay:\n  returnUrl: http://8bgfx5.natappfree.cc/seckill/orderPay/returnUrl\n  notifyUrl: http://8bgfx5.natappfree.cc/seckill/orderPay/notifyUrl\n  frontEndPayUrl: http://localhost/order_detail.html?orderNo=\n  errorUrl: http://localhost/50x.html\nfeign:\n  sentinel:\n    enabled: true', '5b3dd74adecf1a24754a29575fe19923', '2023-07-09 00:56:54', '2023-07-09 00:56:54', NULL, '0:0:0:0:0:0:0:1', 'D', '');
INSERT INTO `his_config_info` VALUES (11, 54, 'intergral-service-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 8071\nspring:\n  datasource:\n    url: jdbc:mysql://localhost:3306/shop-intergral?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8\n    driverClassName: com.mysql.cj.jdbc.Driver\n    type: com.alibaba.druid.pool.DruidDataSource\n    username: root\n    password: root\n    maxActive: 1000\n    initialSize: 100\n    maxWait: 60000\n    minIdle: 500\nmybatis:\n  configuration:\n    default-fetch-size: 100\n    default-statement-timeout: 3000\n    map-underscore-to-camel-case: true\n  mapperLocations: classpath:cn/timeless/mapper/*Mapper.xml\nribbon:\n  eager-load:\n    enabled: true', 'a4e96bdc25506cda36b415f6f1ae6e77', '2023-07-09 00:56:54', '2023-07-09 00:56:54', NULL, '0:0:0:0:0:0:0:1', 'D', '');
INSERT INTO `his_config_info` VALUES (12, 55, 'job-service-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 8081\nfeign:\n  sentinel:\n    enabled: true\nribbon:\n  eager-load:\n    enabled: true\n  ReadTimeout: 10000            \n  ConnectTimeout: 10000          \n  MaxAutoRetries: 0             \n  MaxAutoRetriesNextServer: 0  \n# elasticjob:\n#   zookeeper-url: 192.168.111.100:2181 # 你的zookeeper的Ip地址:2181\n#   group-name: shop-job-group\n# jobCron:\n#   initSeckillProduct: 0 0/1 * * * ?\n#   userCache: 0 0/1 * * * ?\nxxl:\n  job:\n    admin:\n      addresses: http://localhost:4567/xxl-job-admin\n    executor:\n      appname: productHandler\n      address:  \"\"\n      ip: \"\"\n      port: 8585\n      logpath: D:\\\\java\\\\code\\\\陈天狼_微服务实战\\\\xxl_job_log\n      logretentiondays: 30\n    accessToken: default_token', 'bc789b6e7296e03efa4af030697dbfc3', '2023-07-09 00:56:54', '2023-07-09 00:56:54', NULL, '0:0:0:0:0:0:0:1', 'D', '');
INSERT INTO `his_config_info` VALUES (13, 56, 'websocket-service-dev.yaml', 'DEFAULT_GROUP', '', 'server:\r\n  port: 8091\r\nrocketmq:\r\n  producer:\r\n    group: websocket-group', '37a3d2fb19670379d84bf440bddea79c', '2023-07-09 00:56:54', '2023-07-09 00:56:54', NULL, '0:0:0:0:0:0:0:1', 'D', '');
INSERT INTO `his_config_info` VALUES (14, 57, 'pay-service-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 8051\nribbon:\n  eager-load:\n    enabled: true\nrocketmq:\n  producer:\n    group: pay-group\nalipay:\n  app_id: 2021000122674469\n  merchant_private_key: MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCTAT0pyVIMmEK2rxQFhX5cqKyVJZxA/A952ntyjAPOHPafDOalPc1wY5KBJD59idBp5HDQojYUgH4hLQBm0dU/+Fv9cQTkgIl47ZKPx+hU6zlZJfbyKUXVPhFQEMPddmvcgAfp/M+8cthbVt0Mjcp+x211+V0FPiqnY/fk5qXZjwthjICeEVmVVwtphDSK0UgzU5fhIcTjWxEYtznWhAWr7eYF2yD80jigWHQxiuf/er+HUf0gsCI/C1OxFUzlH9FBFFfBE+Lc4rQtSZ4kGsjFmpfHEPdYDwJ+xc5FISnOtb2htzp3wqR4Q1zvo2b9XSW+rFVxqNPcdxPC5BrgVGLvAgMBAAECggEAJb8fsWccRleiab4y6egJNkmpZvKtWJJgdu1+3T7Oi1IskVKCttNTuRQYPkjMMvf8J/ScczXzpPgJawNfIwemNjLTjBRFKVdH9WErTPgL1CJCK33wFuY6JDM3xtNHN8p8j7XgUli0DrN+kpWPVPXjlQIoPS62j+4SjcDBmmkPFPma8GKUZgGD3uNSNLnKrAorTMUviIYn9p4oR2gJ5EXIZk0KzhViWgI57SHIUx1nfstKnobTtP19aFyBdHgeUq5xxmVfHbBl/6qlOoxaS6ZQZA2U5WUlyQXQS4SlYC3m6tm581hTN4+J6D4DEx7MxA0cG4L3jyoB1tMC+1rTZsCoAQKBgQD7t2PDDrW0TpX52ZM6ZrFanP398qJp0f3AOz5Dko4YiK2yEZLZoM21uGYsC+Rr/ijg3RuqLPzNZFDFvsbdnO9CppIA8/f1O5jHAU+WdOCvvjLH8ITHRR+cFd+a7vJMiF4KeMXrdDYmyXroe2WQWluSpQJWoJGgqNrI/Jj9H8h9AQKBgQCVgauO4N6B6nSfht/jHJD/zD2up+J5e0YWbKZl3ITa4ZIXba1Yd8HCQvvfxiYcKXBlPVPsqg154m19UcHyvQMImNF7sPFGxpSjR9KGYEtB0uwGzVP5Vf+/t/28e29Ru2j4JgxJmZC6alOxOhmx1uIsUELtkEKxdGdil5feQrSv7wKBgEr9A1dk1nT0xHE+hEHtvgBErNYupnvn9zSBcbcnvfVJIpXd7mWvJhlw6d9NW6tgeEUlGczEwjteG0IN56i1zAGLJgvqooAIVSdUGKW2BAnXG8G8wZGr4hLZ4VeCv+RU688q06ulX0yG0XrY6BTtFkLFrcCo7TlzlFo0Bk/CXDQBAoGAFadWRbdkAsUGZySNwMMebS4TsPw33QhxukW6Q/6Qb3NESYhlFu8hbRVujZaRZnrKAJ/FS/3FPeYBnaj9wj6F9fTMQnH6QBn65Ts9zqCbxcjazpFmnDJMfGQVp/rowJm4NlisZuyK/bTwsjaMCinpUCm/x9ChGDqXzrvODy7yJJkCgYEA+fr22r7IX+CqFE7QlfMJ44ePAOQfx8zyN18RSzJzhUoS9W4iwnmL2x90Ak+XUC+7gm8HCL7GAR6KHLTO0miRAk6PNRyL2cStWg9MpXoZmxc13fbSXO2TT9dXyxifRnT1RYYqwiHzkY8Ni9oGC4jMzgugua4zEnC9yAbWXA/n1e8=\n  alipay_public_key: MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuTGdCrOT/z0FM3wT68fHNWH38mGrkn9dgr/9xAUmm8puQYJr0yKhl4CGZLYeg/IC+w9sSVdj83kt+vp6lwrgNtSlNLf0gSucFMtRu5rzq3DgPgHZcsruLLaa/Gn4A0wzVL+XxCE5EGm/jYw9MIrP9tCiYKhLqqAlnAYlSBmslGi2SRj7Ch+BweKv1Ki1D2jeZC0O86rxNJKuHiCnk79RQ0rgvbB56gd8FQHNX8EVXq8xH/4OOu4v9IPtnMPBrhLNct/rMqo/JyOQI5QjnSk43yc5Xrox371E0Qrzf6zjoQPF0bzB+nzA3UwMuA82knvk7STLqJy21NBq7hT6r1gPIQIDAQAB\n  sign_type: RSA2\n  charset: utf-8\n  gatewayUrl: https://openapi-sandbox.dl.alipaydev.com/gateway.do', 'dd87e07f2b01a825aa7451a7d780cff7', '2023-07-09 00:56:54', '2023-07-09 00:56:54', NULL, '0:0:0:0:0:0:0:1', 'D', '');
INSERT INTO `his_config_info` VALUES (15, 58, 'nacos-discovery-config-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\n  cloud:\n    nacos:\n      discovery:\n        server-addr: 127.0.0.1:8848', '68118ee8954f6bd1571b02f02dd3c153', '2023-07-09 00:56:56', '2023-07-09 00:56:57', NULL, '0:0:0:0:0:0:0:1', 'D', '');
INSERT INTO `his_config_info` VALUES (16, 59, 'canal-service-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 8021\ncanal:\n  server: 192.168.111.100:11111\n  destination: example\nlogging:\n  level:\n    root: info\n    top:\n      javatool:\n        canal:\n          client:\n            client:\n              AbstractCanalClient: error\n\n\n', '5a8e7410e19c19d78f50e0a4af534898', '2023-07-09 00:56:56', '2023-07-09 00:56:57', NULL, '0:0:0:0:0:0:0:1', 'D', '');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `resource` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  UNIQUE INDEX `uk_role_permission`(`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  UNIQUE INDEX `idx_user_role`(`username`, `role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '租户容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp`, `tenant_id`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'tenant_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
