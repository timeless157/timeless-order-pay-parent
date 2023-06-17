package com.timeless.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timeless.domain.OrderInfo;
import com.timeless.domain.vo.SeckillProductVo;
import com.timeless.result.ResponseResult;

import java.util.List;


/**
 * (OrderInfo)表服务接口
 *
 * @author makejava
 * @since 2023-05-28 11:51:49
 */
public interface OrderInfoService extends IService<OrderInfo> {

    OrderInfo doSeckill(Long userId, SeckillProductVo seckillProductVo, String expireTime);

    ResponseResult pay(String orderNo);

    void refund(OrderInfo orderInfo);

    OrderInfo createOrderFromShopCart(Long userId, List<Long> productId, String expireTime);
}

