package com.timeless.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timeless.config.RabbitMQConfig;
import com.timeless.constants.AppHttpCodeEnum;
import com.timeless.domain.OrderInfo;
import com.timeless.domain.SeckillProduct;
import com.timeless.domain.vo.PayVo;
import com.timeless.domain.vo.RefundVo;
import com.timeless.domain.vo.SeckillProductVo;
import com.timeless.exception.SystemException;
import com.timeless.feign.PayFeign;
import com.timeless.mapper.OrderInfoMapper;
import com.timeless.result.ResponseResult;
import com.timeless.service.OrderInfoService;
import com.timeless.service.SeckillProductService;
import com.timeless.utils.DateTimeUtils;
import com.timeless.utils.IdGenerateUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

/**
 * (OrderInfo)表服务实现类
 *
 * @author makejava
 * @since 2023-05-28 11:51:49
 */
@Service("orderInfoService")
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Autowired
    private SeckillProductService seckillProductService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private PayFeign payFeign;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${alipay.returnUrl}")
    private String returnUrl;


    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    @Override
    @Transactional
    public OrderInfo doSeckill(Long userId, SeckillProductVo seckillProductVo) {
        //扣减库存
        UpdateWrapper<SeckillProduct> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("stock_count", seckillProductVo.getStockCount() - 1).eq("id", seckillProductVo.getId());
        seckillProductService.update(updateWrapper);

        //生成订单
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(seckillProductVo, orderInfo);
        orderInfo.setUserId(userId);
        orderInfo.setCreateDate(new Date());
        orderInfo.setOrderNo(String.valueOf(IdGenerateUtil.get().nextId()));
        orderInfo.setSeckillId(seckillProductVo.getId());
        orderInfo.setStatus(AppHttpCodeEnum.CONTINUE_PAY.getMsg());
        orderInfo.setProductPrice(seckillProductVo.getProductPrice());
        save(orderInfo);

        System.out.println("下单成功....." + DateTimeUtils.getCurrentDateTime());

        //实现订单超时1min，自动取消，RabbitMQ实现
        rabbitTemplate.convertAndSend(RabbitMQConfig.TTL_EXCHANGE,"ttl.test",orderInfo);
        return orderInfo;
    }

    @Override
    public ResponseResult pay(String orderNo) {
        //根据订单号拿到订单对象
        OrderInfo orderInfo = orderInfoService.getById(orderNo);
        PayVo payVo = new PayVo();
        payVo.setOutTradeNo(orderNo);
        payVo.setTotalAmount(String.valueOf(orderInfo.getSeckillPrice()));
        payVo.setSubject(orderInfo.getProductName());
        payVo.setBody(orderInfo.getProductName());
        payVo.setReturnUrl(returnUrl);
        payVo.setNotifyUrl(notifyUrl);

        ResponseResult result = payFeign.payOnline(payVo);
        return result;
    }

    /**
     * 哇塞，完美出现，钱扣了，订单状态没改变，因为.set("status", AppHttpCodeEnum.DONE_REFUND.getMsg())忘了.getMsg();
     *
     * @param orderInfo
     */
    @Override
    public void refund(OrderInfo orderInfo) {
        RefundVo refundVo = new RefundVo();
        refundVo.setOutTradeNo(orderInfo.getOrderNo());
        refundVo.setRefundAmount(String.valueOf(orderInfo.getSeckillPrice()));
        refundVo.setRefundReason("没钱了....");

        //RabbitMQ中放入退款消息
        rabbitTemplate.convertAndSend(RabbitMQConfig.REFUND_QUEUE, refundVo);


//        ResponseResult<Boolean> result = payFeign.refund(refundVo);
//        if (Objects.isNull(result) || !result.getData()) {
//            throw new SystemException(AppHttpCodeEnum.REFUND_FAIL);
//        }
//        orderInfoService.update(new UpdateWrapper<OrderInfo>()
//                .set("status", AppHttpCodeEnum.DONE_REFUND.getMsg())
//                .eq("order_no", orderInfo.getOrderNo()));
    }
}

