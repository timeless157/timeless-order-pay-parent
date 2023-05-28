package com.timeless.feign.impl;

import com.timeless.domain.vo.PayVo;
import com.timeless.feign.PayFeign;
import com.timeless.result.ResponseResult;

import java.util.Map;

/**
 * @author timeless
 * @date 2023/5/28 16:25
 * @desciption:
 */
public class PayFeignImpl implements PayFeign {
    @Override
    public ResponseResult payOnline(PayVo payVo) {
        return null;
    }

    @Override
    public ResponseResult<Boolean> rsaCheckV1(Map<String, String> params) {
        return null;
    }
}
