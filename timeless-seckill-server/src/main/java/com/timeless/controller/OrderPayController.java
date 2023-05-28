package com.timeless.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.timeless.constants.AppHttpCodeEnum;
import com.timeless.domain.OrderInfo;
import com.timeless.exception.SystemException;
import com.timeless.feign.PayFeign;
import com.timeless.result.ResponseResult;
import com.timeless.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author timeless
 * @date 2023/5/28 13:39
 * @desciption:
 */
@RestController
@RequestMapping("/orderPay")
public class OrderPayController {

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private PayFeign payFeign;

    /**
     * 支付
     * @param orderNo
     * @return
     */
    @GetMapping("/pay")
    public Object pay(String orderNo) {
        return orderInfoService.pay(orderNo).getData();
    }

    /**
     * 退款
     * 讲道理，如果按现在的做法，先退款，再修改订单可能会有点问题，因为订单状态可能会延迟改变，这期间用户看到的订单还是已支付。
     * 但是，用户量小的情况下，接口很快，也没有太大问题，肯定可以优化！
     * @param orderNo
     * @return
     */
    @RequestMapping("/refund/{orderNo}")
    public ResponseResult refund(@PathVariable("orderNo") String orderNo){
        OrderInfo orderInfo = orderInfoService.getById(orderNo);
        orderInfoService.refund(orderInfo);
        return ResponseResult.okResult("退款成功!");
    }

    /**
     * 异步回调
     *
     * @param params
     * @return
     */
    @RequestMapping("/notifyUrl")
    public String notifyUrl(@RequestParam Map<String, String> params) {
        System.out.println("异步回调......");
        //验证签名
        ResponseResult<Boolean> res = payFeign.rsaCheckV1(params);
        if (Objects.isNull(res) || !res.getData()) {
            throw new SystemException(AppHttpCodeEnum.RSACHECK_FAIL);
        }
        //验证签名成功，修改订单状态为“已付款”
        boolean update = orderInfoService.update(new UpdateWrapper<OrderInfo>()
                .set("status", AppHttpCodeEnum.DONE_PAY.getMsg())
                .set("pay_date", new Date())
                .eq("order_no", params.get("out_trade_no")));
        if (!update) {
            // 联系客服 ，退款操作，因为用户已支付
            // 或者调用refund接口
            // 或者把更改订单状态的消息放在mq中，异步处理。
            // (或者可以说把修改订单状态一起放入RabbitMQ中，见OrderInfoServiceImpl.refund()。后面有案例，这里就不做演示。)
        }
        return "success";
    }

    @RequestMapping("/returnUrl")
    public ResponseResult returnUrl(@RequestParam Map<String, String> params) {
        System.out.println("同步回调......");
        ResponseResult<Boolean> res = payFeign.rsaCheckV1(params);
        if (Objects.isNull(res) || !res.getData()) {
            throw new SystemException(AppHttpCodeEnum.RSACHECK_FAIL);
        }
        //返回订单信息
        return ResponseResult.okResult(orderInfoService.getById(params.get("out_trade_no")));
    }


}
