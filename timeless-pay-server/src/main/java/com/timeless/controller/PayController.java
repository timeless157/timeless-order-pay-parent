package com.timeless.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.timeless.config.AlipayConfig;
import com.timeless.config.AlipayProperties;
import com.timeless.domain.vo.PayVo;
import com.timeless.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/payOnline")
    public ResponseResult payOnline(@RequestBody PayVo payVo) throws AlipayApiException {

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 同步回调地址
        alipayRequest.setReturnUrl(payVo.getReturnUrl());
        // 异步回调地址
        alipayRequest.setNotifyUrl(payVo.getNotifyUrl());

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ payVo.getOutTradeNo() +"\","
                + "\"total_amount\":\""+ payVo.getTotalAmount() +"\","
                + "\"subject\":\""+ payVo.getSubject() +"\","
                + "\"body\":\""+ payVo.getBody() +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String html = alipayClient.pageExecute(alipayRequest).getBody();
        return ResponseResult.okResult(html);
    }

}
