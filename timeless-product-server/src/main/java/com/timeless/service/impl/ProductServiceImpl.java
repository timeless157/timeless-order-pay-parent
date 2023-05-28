package com.timeless.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timeless.domain.Product;
import com.timeless.mapper.ProductMapper;
import com.timeless.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * (Product)表服务实现类
 *
 * @author makejava
 * @since 2023-05-28 11:46:00
 */
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}

