package com.timeless.domain;

import java.util.Date;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * (OrderInfo)表实体类
 *
 * @author makejava
 * @since 2023-05-28 11:51:49
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_order_info")
public class OrderInfo {
    @TableId
    private String orderNo;

    private Long userId;

    private Long productId;

    private Long seckillId;

    private String productName;

    private Double productPrice;

    private Double seckillPrice;

    private String status;

    private Date createDate;

    private Date payDate;

    private Date seckillDate;


}

