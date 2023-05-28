package com.timeless.feign;

import com.timeless.feign.impl.ProductFeignImpl;
import com.timeless.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author timeless
 * @date 2023/5/28 14:03
 * @desciption:
 */
@FeignClient(name = "timeless-product-server" , fallback = ProductFeignImpl.class)
@RequestMapping("/product")
public interface ProductFeign {

    @GetMapping("/getProductByProductId")
    public ResponseResult getProductByProductId(@RequestParam("productId") Long productId);

}
