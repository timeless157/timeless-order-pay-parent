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
     *
     * @param orderNo
     * @return 这个支付宝返回的是一串html代码，String类型的，显示在界面上
     * 也就是说，这个方法完结之后，会让我们填写支付宝的账号密码（或者扫码）
     * 那么，在这段时间内，或许会有别人动这个订单，改变这个订单的状态（比如两个人抢着支付）
     * 但是，好像也不慌，因为同一订单，在支付宝那边有幂等性操作，不会支付两次。
     * 当下单那一刻，如果订单已经支付过，他会提醒的，回调就不走了。
     * 哎，那这样岂不是不用考虑回调函数中的订单状态了？
     * 不是的，订单已支付自然不用考虑了，返回成功就可以。
     * 但是订单超时取消，要是在付款的时候出现，我们这边现在暂时目前还没有让支付宝能知道这个订单已取消，
     * 所以会出现：已付款，但是提示用户未成功付款而且订单状态也依然是”已取消“。
     * 对应策略: 见下异步回调
     */
    @GetMapping("/pay")
    public Object pay(String orderNo) {
        ResponseResult result = orderInfoService.pay(orderNo);
        return result.getData();
    }

    /**
     * 退款
     * 讲道理，如果按现在的做法，先退款，再修改订单可能会有点问题，因为订单状态可能会延迟改变，这期间用户看到的订单还是已支付。
     * 但是，用户量小的情况下，接口很快，也没有太大问题，肯定可以优化！
     * <p>
     * <p>
     * 2023/5/29 23：18 测试订单在“待付款”和“已取消”状态为什么调退款的接口会退不了款。
     * 原因：退款接口调用的是支付宝那边的接口，那边会那订单号去看看他那边有没有这个订单的交易成功记录，
     * 如果有，那么就可以退款。否则，退款失败。事实上，支付宝只知道我们的订单是成功了还是没成功，
     * 具体的超时、取消他并不知道，至少在我现在的代码中，他不知道，或许有回调可以通知他取消订单。
     * 所以，我在用“待付款”和“已取消”的订单做测试的会不成功，因为支付宝那边根本没有这条订单的付款记录，不可能退款的。
     * 支付宝会但会给我们“订单不存在”的信息。
     *
     * @param orderNo
     * @return
     */
    @RequestMapping("/refund/{orderNo}")
    public ResponseResult refund(@PathVariable("orderNo") String orderNo) {
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
        if (!AppHttpCodeEnum.CONTINUE_PAY.getMsg().equals(orderInfo.getStatus())) {
//            return "fail";
            // 能走到这里，说明订单状态是：”已取消“，但是用户已经付钱了。所以应对策略是：rnm，退钱！（调用退款接口）
            // 也就是用户刚好付完款了，代码走到这里回调了，却超时了，那肯定会在这提示支付失败，然后退钱。（用户的锅，谁让他卡点支付，刚好超时了。）
            // 如果怕退款接口超时，可以用MQ异步退款
            // 告诉用户订单已经超时，钱一会儿退给您。
            refund(params.get("out_trade_no"));
            throw new SystemException(AppHttpCodeEnum.PAY_FAIL);
        }

        // 手动模拟签名被修改
        // 结果：确实抛出了订单失败的异常，但是钱已经在支付宝那边扣款了
        // 结论：在这个回调之前，在点击支付之后，就已经扣款了，进入到这个回调函数一定是扣了款了。
        // params.put("out_trade_no" , "1111111");


        //验证签名
        ResponseResult<Boolean> res = payFeign.rsaCheckV1(params);
        if (Objects.isNull(res) || !res.getData()) {
            throw new SystemException(AppHttpCodeEnum.RSACHECK_FAIL);
        }
        // 验证签名成功，修改订单状态为“已付款”
        // 这里面还需要在sql语句写，订单状态只能从“待付款”到“已付款”(已加上)，不然就不成功，下面处理
        // 但是走到这里出错的可能性不高，因为前面判断过状态了，走到这里的话，99.99%的可能是“待付款”
        // 但是为了以防万一，刚好在上面判断成功，到这里的期间订单过期了。但是用户已付款，就需要退钱了。
        boolean update = orderInfoService.update(new UpdateWrapper<OrderInfo>()
                .set("status", AppHttpCodeEnum.DONE_PAY.getMsg())
                .set("pay_date", new Date())
                .eq("order_no", params.get("out_trade_no"))
                .eq("status", AppHttpCodeEnum.CONTINUE_PAY.getMsg()));
        if (!update) {
            // 联系客服 ，退款操作，因为用户已支付
            // 或者调用refund接口
            // 或者把更改订单状态的消息放在mq中，异步处理。
            // (或者可以说把修改订单状态一起放入RabbitMQ中，见OrderInfoServiceImpl.refund()。后面有案例，这里就不做演示。)
            orderInfoService.refund(orderInfo);
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
        // 除了签名之外的判断，都在异步回调那边做（但是好像也没啥判断的了）

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
