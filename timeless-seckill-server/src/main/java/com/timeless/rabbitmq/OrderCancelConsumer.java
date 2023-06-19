package com.timeless.rabbitmq;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rabbitmq.client.Channel;
import com.timeless.config.RabbitMQConfig;
import com.timeless.constants.AppHttpCodeEnum;
import com.timeless.domain.OrderInfo;
import com.timeless.service.OrderInfoService;
import com.timeless.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class OrderCancelConsumer {

    @Autowired
    private OrderInfoService orderInfoService;


    @RabbitListener(queues = RabbitMQConfig.DLX_QUEUE)
    public void listenerWithoutPlugins(OrderInfo orderInfo) {
        printMsg(orderInfo);

    }

    @RabbitListener(queues = RabbitMQConfig.PLUGINS_QUEUE)
    public void listenerWithPlugins(OrderInfo orderInfo) {
        printMsg(orderInfo);

    }

    private void printMsg(OrderInfo orderInfo) {
        log.error("订单超时取消中...." + DateTimeUtils.getCurrentDateTime() + " === " + orderInfo.getOrderNo());

        OrderInfo orderInfo1 = orderInfoService.getById(orderInfo.getOrderNo());
        if(orderInfo1 == null){
            return;
        }
        if(AppHttpCodeEnum.CONTINUE_PAY.getMsg().equals(orderInfo1.getStatus())){
            //取消订单
            orderInfo1.setStatus(AppHttpCodeEnum.ORDER_CANCEL.getMsg());
            orderInfoService.updateById(orderInfo1);
            log.error("订单已取消......" + DateTimeUtils.getCurrentDateTime() + " === " + orderInfo.getOrderNo());
        }
    }

}