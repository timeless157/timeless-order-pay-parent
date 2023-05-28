package com.timeless.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timeless.domain.SeckillProduct;
import com.timeless.domain.vo.SeckillProductVo;


/**
 * (SeckillProduct)表服务接口
 *
 * @author makejava
 * @since 2023-05-28 11:51:05
 */
public interface SeckillProductService extends IService<SeckillProduct> {

    SeckillProductVo getSeckkillProductVo(Long seckillId);
}

