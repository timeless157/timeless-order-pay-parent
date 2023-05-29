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
     * @return  这个支付宝返回的是一串html代码，String类型的，显示在界面上
     *          也就是说，这个方法完结之后，会让我们填写支付宝的账号密码（或者扫码）
     *          那么，在这段时间内，或许会有别人动这个订单，改变这个订单的状态（比如两个人抢着支付）
     *          但是，好像也不慌，因为同一订单，在支付宝那边有幂等性操作，不会支付两次。
     *          哎，那这样岂不是不用考虑回调函数中的订单状态了？毕竟
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

        // 判断订单状态 , 如果不是待付款，就返回失败
        // 我们需要在if(!AppHttpCodeEnum.CONTINUE_PAY.equals(orderInfo.getStatus())){
        //            return "fail";
        //        }，这个代码里，再做一些保证操作。
        //  因为，当这个异步回调被调用的时候，实际上已经付款成功了，但是订单状态这时候却不是“待付款”，
        //  意味着，他是已取消或者已付款。但是不管怎么说，钱都付了，不能只返回fail，
        //  还要做一些操作，比如通知客服看一下为什么要付款的订单却出现了“已取消”或者“已支付”。

        // 哇，我做了一个实验得到了一个重要的结论： 实验：同时打开两个支付界面。先支付A，A支付完成，订单状态也改变成”已支付“。
        //                                         这时候，再支付B，B会提示订单已支付，这是支付宝那边做的幂等性操作。
        //                                    结论：这边不需要判断订单”已支付“状态了，支付宝有幂等性操作。
        //                                         但是订单”已取消“还是需要考虑的，因为，虽然是”已取消“状态，但实际上还是付了款的，
        //                                         所以需要联系客服退款！

        OrderInfo orderInfo = orderInfoService.getById(params.get("out_trade_no"));
        if(!AppHttpCodeEnum.CONTINUE_PAY.getMsg().equals(orderInfo.getStatus())){
//            return "fail";

            // 这里执行通知退款操作 ， 可以用MQ

            throw new SystemException(AppHttpCodeEnum.PAY_FAIL);
        }

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

        // 判断订单状态 , 如果不是待付款，就返回失败
        // 这边不需要了，因为回调跟付款就几毫秒的差距，虽然可能失败，行！考虑一下失败的后果和对策！
        // 抛异常，这个到这边的情况是：1. 钱已经付了  2. 订单状态肯定不是待付款
        // 至于对策：同上，比如通知客服看一下为什么要付款的订单却出现了“已取消”或者“已支付”。

        // 这边其实不需要判断了，因为在我测试的过程中，异步一定比同步快（其他情况不一定）
        // 这样的话，异步那边修改了订单状态，这边一定会抛异常，所以不需要！

//        OrderInfo orderInfo = orderInfoService.getById(params.get("out_trade_no"));
//        if(!AppHttpCodeEnum.CONTINUE_PAY.getMsg().equals(orderInfo.getStatus())){
////            return ResponseResult.errorResult(AppHttpCodeEnum.PAY_FAIL);
//            throw new SystemException(AppHttpCodeEnum.PAY_FAIL);
//        }

        ResponseResult<Boolean> res = payFeign.rsaCheckV1(params);
        if (Objects.isNull(res) || !res.getData()) {
            throw new SystemException(AppHttpCodeEnum.RSACHECK_FAIL);
        }
        //返回订单信息
        return ResponseResult.okResult(orderInfoService.getById(params.get("out_trade_no")));
    }


}
