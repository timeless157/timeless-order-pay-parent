package com.timeless.domain;
import java.io.Serializable;
import java.lang.reflect.Type;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (OrderToProduct)表实体类
 *
 * @author makejava
 * @since 2023-06-17 15:48:13
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_order_to_product")
public class OrderToProduct  {
        
    @TableId(type = IdType.AUTO)
    private Long id;
    //订单编号    
    private String orderNo;
    //商品id    
    private Long productId;
    //商品数量    
    private Integer productCount;
}
