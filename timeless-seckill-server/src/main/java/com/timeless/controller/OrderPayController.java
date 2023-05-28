package com.timeless.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.timeless.constants.AppHttpCodeEnum;
import com.timeless.domain.OrderInfo;
import com.timeless.exception.SystemException;
import com.timeless.feign.PayFeign;
import com.timeless.result.ResponseResult;
import com.timeless.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/pay")
    public Object pay(String orderNo) {
        return orderInfoService.pay(orderNo).getData();
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
            // 退款操作，因为用户已支付
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
