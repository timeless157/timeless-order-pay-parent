package com.timeless.feign.impl;

import com.timeless.domain.OrderInfo;
import com.timeless.feign.OrderInfoFeign;
import com.timeless.result.ResponseResult;

/**
 * @author timeless
 * @date 2023/5/29 16:33
 * @desciption:
 */
public class OrderInfoFeignImpl implements OrderInfoFeign {
    @Override
    public ResponseResult<OrderInfo> getOrderInfoByOrderNo(String orderNo) {
        return null;
    }
}
