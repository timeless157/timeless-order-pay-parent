package com.timeless.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timeless.domain.ShopCart;
import com.timeless.mapper.ShopCartMapper;
import com.timeless.service.ShopCartService;
import org.springframework.stereotype.Service;
/**
 * (ShopCart)表服务实现类
 *
 * @author makejava
 * @since 2023-06-17 14:51:26
 */
@Service("shopCartService")
public class ShopCartServiceImpl extends ServiceImpl<ShopCartMapper, ShopCart> implements ShopCartService {
}
