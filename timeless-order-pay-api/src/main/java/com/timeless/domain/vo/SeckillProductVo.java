package com.timeless.domain.vo;

import com.timeless.domain.SeckillProduct;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by timeless-lanxw
 * 把Seckillproduct和product的信息整合在一块，然后再前台展示.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillProductVo extends SeckillProduct implements Serializable {
    private String productName;
    private String productTitle;
    private String productDetail;
    private Double productPrice;
    private Integer currentCount;
}
