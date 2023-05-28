package com.timeless.feign;

import com.timeless.domain.vo.PayVo;
import com.timeless.feign.impl.PayFeignImpl;
import com.timeless.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author timeless
 * @date 2023/5/28 16:23
 * @desciption:
 */
@FeignClient(name = "timeless-pay-server" , fallback = PayFeignImpl.class)
public interface PayFeign {

    @RequestMapping("/aliPay/payOnline")
    public ResponseResult payOnline(@RequestBody PayVo payVo);

    @RequestMapping("/aliPay/rsaCheckV1")
    ResponseResult<Boolean> rsaCheckV1(@RequestParam Map<String, String> params);
}
