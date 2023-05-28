package com.timeless.domain;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * (SeckillProduct)表实体类
 *
 * @author makejava
 * @since 2023-05-28 11:51:04
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_seckill_product")
public class SeckillProduct {
    @TableId
    private Long id;

    private Long productId;

    private Double seckillPrice;

    private Integer stockCount;


}

