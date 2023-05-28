package com.timeless.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.timeless.domain.Product;
import com.timeless.domain.SeckillProduct;
import com.timeless.domain.vo.SeckillProductVo;
import com.timeless.feign.ProductFeign;
import com.timeless.mapper.SeckillProductMapper;
import com.timeless.result.ResponseResult;
import com.timeless.service.SeckillProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * (SeckillProduct)表服务实现类
 *
 * @author makejava
 * @since 2023-05-28 11:51:05
 */
@Service("seckillProductService")
public class SeckillProductServiceImpl extends ServiceImpl<SeckillProductMapper, SeckillProduct> implements SeckillProductService {

    @Autowired
    private SeckillProductService seckillProductService;

    @Autowired
    private ProductFeign productFeign;

    @Override
    public SeckillProductVo getSeckkillProductVo(Long seckillId) {
        SeckillProductVo seckillProductVo = new SeckillProductVo();

        // 得到seckillProduct
        SeckillProduct seckillProduct = seckillProductService.getById(seckillId);

        if(Objects.isNull(seckillProduct)){
            return null;
        }

        // 得到productId
        Long productId = seckillProduct.getProductId();

        // 远程调用,得到product
        ResponseResult res = productFeign.getProductByProductId(productId);
        Gson gson = new Gson();
        String productJson = gson.toJson(res.getData());
        Product product = gson.fromJson(productJson, Product.class);

        if(Objects.isNull(product)){
            return null;
        }

        // 属性赋值
        BeanUtils.copyProperties(product , seckillProductVo);
        BeanUtils.copyProperties(seckillProduct , seckillProductVo);

        return seckillProductVo;
    }
}

