package com.timeless.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timeless.domain.OrderInfo;
import com.timeless.domain.vo.SeckillProductVo;
import com.timeless.result.ResponseResult;


/**
 * (OrderInfo)表服务接口
 *
 * @author makejava
 * @since 2023-05-28 11:51:49
 */
public interface OrderInfoService extends IService<OrderInfo> {

    OrderInfo doSeckill(Long userId, SeckillProductVo seckillProductVo);

    ResponseResult pay(String orderNo);
}

