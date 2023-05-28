package com.timeless.controller;

import com.timeless.constants.AppHttpCodeEnum;
import com.timeless.domain.OrderInfo;
import com.timeless.domain.vo.SeckillProductVo;
import com.timeless.exception.SystemException;
import com.timeless.result.ResponseResult;
import com.timeless.service.OrderInfoService;
import com.timeless.service.SeckillProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private SeckillProductService seckillProductService;

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 秒杀商品
     * @param seckillId
     * @return
     */
    @GetMapping("/doSeckill")
    public ResponseResult doSeckill(@RequestParam("seckillId") Long seckillId) {

        // 0. 得到商品信息
        SeckillProductVo seckillProductVo = seckillProductService.getSeckkillProductVo(seckillId);

        if(Objects.isNull(seckillProductVo)){
            throw new SystemException(AppHttpCodeEnum.SECKILL_PRODUCT_NOT_EXIST);
        }

        // 2. 保证库存数量足够
        if (seckillProductVo.getStockCount() <= 0) {
            throw new SystemException(AppHttpCodeEnum.STOCK_NOT_ENOUGH);
        }
        // 3. 创建秒杀订单 ， 扣减库存
        OrderInfo orderInfo = orderInfoService.doSeckill(AppHttpCodeEnum.USERID , seckillProductVo);

        return ResponseResult.okResult(orderInfo);
    }

    @GetMapping("/getAllOrderInfo")
    public ResponseResult getAllOrderInfo(){
        return ResponseResult.okResult(orderInfoService.list());
    }

    @GetMapping("/getOrderInfoByOrderNo/{orderNo}")
    public ResponseResult getOrderInfoByOrderNo(@PathVariable("orderNo") String orderNo){
        return ResponseResult.okResult(orderInfoService.getById(orderNo));
    }

}
