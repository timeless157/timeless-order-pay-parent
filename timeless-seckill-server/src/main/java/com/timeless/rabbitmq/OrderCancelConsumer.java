package com.timeless.rabbitmq;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rabbitmq.client.Channel;
import com.timeless.config.RabbitMQConfig;
import com.timeless.constants.AppHttpCodeEnum;
import com.timeless.domain.OrderInfo;
import com.timeless.service.OrderInfoService;
import com.timeless.utils.DateTimeUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

@Component
public class OrderCancelConsumer {

    @Autowired
    private OrderInfoService orderInfoService;


    @RabbitListener(queues = "springboot_dlx_queue")
    public void listener(OrderInfo orderInfo) {
        System.out.println("订单超时取消中...." + DateTimeUtils.getCurrentDateTime());

        OrderInfo orderInfo1 = orderInfoService.getById(orderInfo.getOrderNo());
        if(AppHttpCodeEnum.CONTINUE_PAY.getMsg().equals(orderInfo1.getStatus())){
            //取消订单
            orderInfo1.setStatus(AppHttpCodeEnum.ORDER_CANCEL.getMsg());
            orderInfoService.updateById(orderInfo1);
        }

    }

}