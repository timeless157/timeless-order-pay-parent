package com.timeless.domain;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Product)表实体类
 *
 * @author makejava
 * @since 2023-05-28 11:45:59
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_product")
public class Product {
    @TableId
    private Long id;

    private String productName;

    private String productTitle;

    private String productDetail;

    private Double productPrice;


}

