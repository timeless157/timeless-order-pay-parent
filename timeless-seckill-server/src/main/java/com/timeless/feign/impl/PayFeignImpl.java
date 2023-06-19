package com.timeless.feign.impl;

import com.timeless.domain.vo.PayVo;
import com.timeless.domain.vo.RefundVo;
import com.timeless.feign.PayFeign;
import com.timeless.result.ResponseResult;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author timeless
 * @date 2023/5/28 16:25
 * @desciption:
 */
@Component
public class PayFeignImpl implements PayFeign {
    @Override
    public ResponseResult payOnline(PayVo payVo) {
        System.out.println("走熔断了.......");
        return null;
    }

    @Override
    public ResponseResult<Boolean> rsaCheckV1(Map<String, String> params) {
        return null;
    }

    @Override
    public ResponseResult<Boolean> refund(RefundVo refundVo) {
        return null;
    }
}
