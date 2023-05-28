package com.timeless.controller;

import com.timeless.domain.SeckillProduct;
import com.timeless.result.ResponseResult;
import com.timeless.service.SeckillProductService;
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
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private SeckillProductService seckillProductService;

    @GetMapping("/getSeckillProductBySeckillId")
    public ResponseResult getSeckillProductBySeckillId(@RequestParam("seckillId") Long seckillId){
        SeckillProduct seckillProduct = seckillProductService.getById(seckillId);
        return ResponseResult.okResult(seckillProduct);
    }
    

}
