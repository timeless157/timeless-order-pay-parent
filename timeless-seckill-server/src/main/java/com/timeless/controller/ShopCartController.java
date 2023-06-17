package com.timeless.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.timeless.constants.AppHttpCodeEnum;
import com.timeless.domain.Product;
import com.timeless.domain.ShopCart;
import com.timeless.exception.SystemException;
import com.timeless.feign.ProductFeign;
import com.timeless.result.ResponseResult;
import com.timeless.service.ShopCartService;
import com.timeless.utils.SnowFlakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;
import java.util.Objects;

/**
 * @author timeless
 * @create 2023-06-17 14:54
 */
@RestController
@RequestMapping("/ShopCartController")
public class ShopCartController {

    @Autowired
    private ShopCartService shopCartService;

    @Autowired
    private ProductFeign productFeign;

    /**
     * @Description: 查询我的购物车的所有商品
     * @Date: 2023/6/17 14:56
     * @Author: timeless
     */
    @GetMapping("/getAllMyCartProducts/{userId}")
    public ResponseResult<List<ShopCart>> getAllMyCartProducts(@PathVariable("userId") Long userId) {
        List<ShopCart> shopCarts = shopCartService.list(Wrappers.<ShopCart>lambdaQuery().eq(ShopCart::getUserId, userId));
        return ResponseResult.okResult(shopCarts);
    }

    /**
     * @Description: 向购物车添加/修改商品
     * @Date: 2023/6/17 15:04
     * @Author: timeless
     */
    @PostMapping("/addOrUpdateProductToShopCart")
    public ResponseResult<Boolean> addProductToShopCart(@RequestBody ShopCart shopCart) {
        // 判断商品是否在商品表中
        ResponseResult responseResult = productFeign.getProductByProductId(shopCart.getProductId());
        Gson gson = new Gson();
        String productJson = gson.toJson(responseResult.getData());
        Product product = gson.fromJson(productJson, Product.class);

        if(Objects.isNull(responseResult) || Objects.isNull(product)){
            throw new SystemException(AppHttpCodeEnum.PRODUCT_NOT_EXIST);
        }

        List<ShopCart> shopCarts = shopCartService.list(Wrappers.<ShopCart>lambdaQuery().eq(ShopCart::getUserId, shopCart.getUserId()));
        ShopCart result = shopCarts.isEmpty() ? null : shopCarts.get(0);
        shopCart.setShopCartId(Objects.isNull(result) ? SnowFlakeUtil.getNextId() : result.getShopCartId().isEmpty() ? SnowFlakeUtil.getNextId() : result.getShopCartId());
        shopCart.setProductPrice(product.getProductPrice());
        boolean saveOrUpdate = shopCartService
                .saveOrUpdate(
                        shopCart,
                        Wrappers.<ShopCart>lambdaUpdate()
                                .eq(ShopCart::getUserId, shopCart.getUserId())
                                .eq(ShopCart::getProductId, shopCart.getProductId()));
        return ResponseResult.okResult(saveOrUpdate);
    }

}
