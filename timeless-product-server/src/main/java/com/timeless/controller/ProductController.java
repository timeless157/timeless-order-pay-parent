package com.timeless.controller;

import com.timeless.domain.Product;
import com.timeless.result.ResponseResult;
import com.timeless.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author timeless
 * @date 2023/5/28 12:01
 * @desciption:
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getProductByProductId")
    public ResponseResult getProductByProductId(@RequestParam("productId") Long productId){
        Product product = productService.getById(productId);
        return ResponseResult.okResult(product);
    }

}
