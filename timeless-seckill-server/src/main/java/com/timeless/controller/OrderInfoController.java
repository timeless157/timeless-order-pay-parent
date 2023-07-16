package com.timeless.controller;

import com.timeless.constants.AppHttpCodeEnum;
import com.timeless.domain.OrderInfo;
import com.timeless.domain.vo.SeckillProductVo;
import com.timeless.exception.SystemException;
import com.timeless.result.ResponseResult;
import com.timeless.service.OrderInfoService;
import com.timeless.service.SeckillProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author timeless
 * @date 2023/5/28 13:38
 * @desciption:
 */
@RestController
@RequestMapping("/orderInfo")
public class OrderInfoController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SeckillProductService seckillProductService;

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 秒杀商品
     *
     * @param seckillId
     * @return
     */
    @GetMapping("/doSeckill")
    public ResponseResult doSeckill(@RequestParam("seckillId") Long seckillId, @RequestParam("expireTime") String expireTime) {

        OrderInfo orderInfo = orderInfoService.doSeckill(AppHttpCodeEnum.USERID, seckillId, expireTime);

        return ResponseResult.okResult(orderInfo);
    }

    @GetMapping("/getAllOrderInfo")
    public ResponseResult getAllOrderInfo() {
        return ResponseResult.okResult(orderInfoService.list());
    }

    @GetMapping("/getOrderInfoByOrderNo/{orderNo}")
    public ResponseResult<OrderInfo> getOrderInfoByOrderNo(@PathVariable("orderNo") String orderNo) {
        return ResponseResult.okResult(orderInfoService.getById(orderNo));
    }


    /**
     * @Description: 从购物车下单
     * @Date: 2023/6/17 14:36
     * @Author: timeless
     */
    @PostMapping("/createOrderFromShopCart/{userId}")
    public ResponseResult<OrderInfo> createOrderFromShopCart(@PathVariable("userId") Long userId,
                                                             @RequestParam("productId") List<Long> productId,
                                                             @RequestParam("expireTime") String expireTime) {
        // TODO 判断商品是否存在于购物车中
        OrderInfo res = orderInfoService.createOrderFromShopCart(userId, productId, expireTime);
        return ResponseResult.okResult(res);
    }


}
