#!/bin/bash

# 创建目标目录
mkdir -p ../mysql/db
mkdir -p ../timeless-order/product/jar
mkdir -p ../timeless-order/seckill/jar
mkdir -p ../timeless-order/pay/jar

# 复制sql文件
echo "begin copy sql "
cp ../../sql/timeless_seckill.sql ../mysql/db
cp ../../sql/timeless_product.sql ../mysql/db
echo "end copy sql "

# 复制jar文件
echo "begin copy jar "
cp ../../timeless-product-server/target/timeless-product-server-1.0-SNAPSHOT.jar ../timeless-order/product/jar/timeless-product-server.jar
cp ../../timeless-seckill-server/target/timeless-seckill-server-1.0-SNAPSHOT.jar ../timeless-order/seckill/jar/timeless-seckill-server.jar
cp ../../timeless-pay-server/target/timeless-pay-server-1.0-SNAPSHOT.jar  ../timeless-order/pay/jar/timeless-pay-server.jar
echo "end copy jar "