package com.timeless.feign.impl;

import com.timeless.feign.ProductFeign;
import com.timeless.result.ResponseResult;
import org.springframework.stereotype.Component;

/**
 * @author timeless
 * @date 2023/5/28 14:04
 * @desciption:
 */
@Component
public class ProductFeignImpl implements ProductFeign {
    @Override
    public ResponseResult getProductByProductId(Long productId) {
        return null;
    }
}
