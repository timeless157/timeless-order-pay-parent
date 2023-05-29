package com.timeless.rabbitmq;

import com.timeless.config.RabbitMQConfig;
import com.timeless.domain.vo.RefundVo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RefundErrorListener {


    @RabbitListener(queues = RabbitMQConfig.REFUND_ERROR_QUEUE)
    public void handleRefundError(RefundVo refundVo) {

        System.out.println("退款又失败了，还是通知客服操作吧，呜呜呜......");

        // 发送退款失败的通知邮件


    }
}