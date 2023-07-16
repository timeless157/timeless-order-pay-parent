package com.timeless.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.BatchAlipayRequest;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.timeless.config.AlipayConfig;
import com.timeless.config.AlipayProperties;
import com.timeless.constants.AppHttpCodeEnum;
import com.timeless.domain.OrderInfo;
import com.timeless.domain.vo.PayVo;
import com.timeless.domain.vo.RefundVo;
import com.timeless.exception.SystemException;
import com.timeless.feign.OrderInfoFeign;
import com.timeless.result.ResponseResult;
import com.timeless.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author timeless
 * @date 2023/5/28 16:27
 * @desciption:
 */
@RestController
@RequestMapping("/aliPay")
public class PayController {

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private OrderInfoFeign orderInfoFeign;

    /**
     *
     * @param payVo
     * @return  这个返回的是html界面，这个界面要填写支付宝账号密码。
     * @throws AlipayApiException
     */
    @RequestMapping("/payOnline")
    public ResponseResult payOnline(@RequestBody PayVo payVo) throws AlipayApiException {

        // 判断订单状态 , 如果不是待付款，就返回失败
        ResponseResult<OrderInfo> responseResult = orderInfoFeign.getOrderInfoByOrderNo(payVo.getOutTradeNo());
        OrderInfo orderInfo = responseResult.getData();
        String status = orderInfo.getStatus();

        if(!AppHttpCodeEnum.CONTINUE_PAY.getMsg().equals(status)){
            return ResponseResult.okResult(status);
//            throw new SystemException(AppHttpCodeEnum.PAY_FAIL);
        }

        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 同步回调地址
        alipayRequest.setReturnUrl(payVo.getReturnUrl());
        // 异步回调地址
        alipayRequest.setNotifyUrl(payVo.getNotifyUrl());

        // 订单过期时间
//        String expireTime = DateTimeUtils.getCurrentDateTimePlusOneMinute(1L);
//        System.out.println(expireTime);

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ payVo.getOutTradeNo() +"\","
                + "\"total_amount\":\""+ payVo.getTotalAmount() +"\","
                + "\"subject\":\""+ payVo.getSubject() +"\","
                + "\"body\":\""+ payVo.getBody() +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
//                + "\"timeout_express\":\"1m\"}"); // 设置订单过期时间为1分钟
//                + "\"time_expire\":\"" + expireTime + "\"}"); // 设置订单过期时间
        String html = alipayClient.pageExecute(alipayRequest).getBody();
        BatchAlipayRequest batchAlipayRequest = new BatchAlipayRequest();
        return ResponseResult.okResult(html);
    }

    @RequestMapping("/rsaCheckV1")
    ResponseResult<Boolean> rsaCheckV1(@RequestParam Map<String, String> params) throws AlipayApiException {
        boolean signVerified = AlipaySignature.rsaCheckV1(params,
                alipayProperties.getAlipayPublicKey(),
                alipayProperties.getCharset(),
                alipayProperties.getSignType()); //调用SDK验证签名
        return ResponseResult.okResult(signVerified);

    }

    @RequestMapping("/refund")
    ResponseResult<Boolean> refund(@RequestBody RefundVo refundVo) throws AlipayApiException {
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ refundVo.getOutTradeNo() +"\","
                + "\"trade_no\":\"\","
                + "\"refund_amount\":\""+ refundVo.getRefundAmount() +"\","
                + "\"refund_reason\":\""+ refundVo.getRefundReason() +"\","
                + "\"out_request_no\":\"\"}");

        //请求
        AlipayTradeRefundResponse res = alipayClient.execute(alipayRequest);
        return ResponseResult.okResult(res.isSuccess());
    }

    @GetMapping("/testDocker")
    public ResponseResult testDocker(){
        return ResponseResult.okResult();
    }

}
