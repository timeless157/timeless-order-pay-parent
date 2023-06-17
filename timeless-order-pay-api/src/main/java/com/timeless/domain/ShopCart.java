package com.timeless.domain;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (ShopCart)表实体类
 *
 * @author makejava
 * @since 2023-06-17 14:51:10
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_shop_cart")
public class ShopCart  {
        
    @TableId
    private Integer id;
    //购物车id    
    private String shopCartId;
    //用户id    
    private Long userId;
    //商品id    
    private Long productId;
    //商品数量    
    private Integer productCount;

    private Double productPrice;
}
