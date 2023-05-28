package com.timeless.controller;

import com.timeless.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/pay")
    public Object pay(String orderNo) {

        return orderInfoService.pay(orderNo).getData();

    }

}
