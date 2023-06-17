package com.timeless.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timeless.domain.OrderToProduct;
import com.timeless.mapper.OrderToProductMapper;
import com.timeless.service.OrderToProductService;
import org.springframework.stereotype.Service;
/**
 * (OrderToProduct)表服务实现类
 *
 * @author makejava
 * @since 2023-06-17 15:48:15
 */
@Service("orderToProductService")
public class OrderToProductServiceImpl extends ServiceImpl<OrderToProductMapper, OrderToProduct> implements OrderToProductService {
}
