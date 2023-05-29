package com.timeless.rabbitmq;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.timeless.config.RabbitMQConfig;
import com.timeless.constants.AppHttpCodeEnum;
import com.timeless.domain.OrderInfo;
import com.timeless.domain.vo.RefundVo;
import com.timeless.exception.SystemException;
import com.timeless.feign.PayFeign;
import com.timeless.result.ResponseResult;
import com.timeless.service.OrderInfoService;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RefundListener {

    @Autowired
    private PayFeign payFeign;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 这里其实可以先修改订单状态，再退款。（告诉用户，退款可能会有延迟。）
     * 不然，可能有种情况 ，用户退了款，还是看见订单处于“已付款”的状态。
     * @param refundVo
     */
    @RabbitListener(queues = RabbitMQConfig.REFUND_QUEUE)
    public void handleRefund(RefundVo refundVo) {
        System.out.println("退款监听中.........");
        try {
            ResponseResult<Boolean> result = payFeign.refund(refundVo);
            if (Objects.isNull(result) || !result.getData()) {
                throw new SystemException(AppHttpCodeEnum.REFUND_FAIL);
            }
            orderInfoService.update(new UpdateWrapper<OrderInfo>()
                    .set("status", AppHttpCodeEnum.DONE_REFUND.getMsg())
                    .eq("order_no", refundVo.getOutTradeNo()));
        } catch (Exception e) {
            e.printStackTrace();
            // 处理退款失败的情况，例如发送通知邮件或者短信给相关人员
            rabbitTemplate.convertAndSend(RabbitMQConfig.REFUND_ERROR_QUEUE, refundVo);
        }
    }
}