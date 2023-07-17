package com.timeless.feign;

import com.timeless.domain.OrderInfo;
import com.timeless.feign.impl.OrderInfoFeignImpl;
import com.timeless.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author timeless
 * @date 2023/5/29 16:32
 * @desciption:
 */
@FeignClient(value = "timeless-seckill-server" , fallback = OrderInfoFeignImpl.class)
@Component
public interface OrderInfoFeign {

    @GetMapping("/orderInfo/getOrderInfoByOrderNo/{orderNo}")
    public ResponseResult<OrderInfo> getOrderInfoByOrderNo(@PathVariable("orderNo") String orderNo);



}
