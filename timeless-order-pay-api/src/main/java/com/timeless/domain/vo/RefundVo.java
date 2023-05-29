package com.timeless.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author timeless
 * @create 2023-05-29 0:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundVo implements Serializable {

    // 交易订单号
    private String outTradeNo;

    // 退款金额
    private String refundAmount;

    // 退款原因
    private String refundReason;
}
