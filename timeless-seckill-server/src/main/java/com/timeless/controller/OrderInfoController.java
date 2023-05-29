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
     *
     * @param seckillId
     * @return
     */
    @GetMapping("/doSeckill")
    public ResponseResult doSeckill(@RequestParam("seckillId") Long seckillId) {

        // 0. 得到商品信息
        SeckillProductVo seckillProductVo = seckillProductService.getSeckkillProductVo(seckillId);

        /**
         * @Description： 在这个方法中，如果没有查询到商品，建议抛出异常，而不是返回null。原因如下：
         *
         * 1. 异常具有更好的可读性和可维护性。如果返回null，那么调用方需要判断返回值是否为null，这样会增加代码的复杂度和出错的可能性。
         *      而如果抛出异常，那么调用方可以直接捕获异常并进行相应的处理，代码更加简洁明了，也更容易定位问题。
         *
         * 2. 异常可以提供更好的错误信息。如果返回null，那么调用方无法得知具体的错误原因，需要通过日志等方式来查看。
         *      而如果抛出异常，那么可以在异常信息中提供具体的错误原因和错误码，方便调用方进行处理和排查问题。
         *
         * 3. 异常可以避免空指针异常等问题。如果返回null，那么调用方在使用返回值时需要进行判空操作，否则可能会出现空指针异常等问题。
         *      而如果抛出异常，那么可以避免这种问题的发生。
         *
         * 因此，建议在这个方法中，如果没有查询到商品，抛出异常并提供具体的错误码和错误信息，以提高代码的可读性和可维护性。
         * @Date: 2023/5/29 20:14
         * @Author: timeless
         */
        if (Objects.isNull(seckillProductVo)) {
            throw new SystemException(AppHttpCodeEnum.SECKILL_PRODUCT_NOT_EXIST);
        }

        // 2. 保证库存数量足够
        if (seckillProductVo.getStockCount() <= 0) {
            throw new SystemException(AppHttpCodeEnum.STOCK_NOT_ENOUGH);
        }
        // 3. 创建秒杀订单 ， 扣减库存
        OrderInfo orderInfo = orderInfoService.doSeckill(AppHttpCodeEnum.USERID, seckillProductVo);

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

}
