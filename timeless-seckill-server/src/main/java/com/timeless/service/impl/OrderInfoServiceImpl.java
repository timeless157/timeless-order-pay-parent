package com.timeless.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timeless.config.RabbitMQConfig;
import com.timeless.constants.AppHttpCodeEnum;
import com.timeless.domain.OrderInfo;
import com.timeless.domain.OrderToProduct;
import com.timeless.domain.SeckillProduct;
import com.timeless.domain.ShopCart;
import com.timeless.domain.vo.PayVo;
import com.timeless.domain.vo.RefundVo;
import com.timeless.domain.vo.SeckillProductVo;
import com.timeless.exception.SystemException;
import com.timeless.feign.PayFeign;
import com.timeless.mapper.OrderInfoMapper;
import com.timeless.result.ResponseResult;
import com.timeless.service.OrderInfoService;
import com.timeless.service.OrderToProductService;
import com.timeless.service.SeckillProductService;
import com.timeless.service.ShopCartService;
import com.timeless.utils.DateTimeUtils;
import com.timeless.utils.IdGenerateUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * (OrderInfo)表服务实现类
 *
 * @author makejava
 * @since 2023-05-28 11:51:49
 */
@Service("orderInfoService")
@Slf4j
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Autowired
    private SeckillProductService seckillProductService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private PayFeign payFeign;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ShopCartService shopCartService;

    @Autowired
    private OrderToProductService orderToProductService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${alipay.returnUrl}")
    private String returnUrl;


    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    @Override
    public OrderInfo doSeckill(Long userId, Long seckillId, String expireTime) {


        // 0. 得到商品信息
        SeckillProductVo seckillProductVo = seckillProductService.getSeckkillProductVo(seckillId);

        /**
         * @Description： 在这个方法中，如果没有查询到商品，建议抛出异常，而不是返回null。原因如下：
         *
         * 1. 异常具有更好的可读性和可维护性。如果返回null，那么调用方需要判断返回值是否为null，这样会增加代码的复杂度和出错的可能性。
         *      而如果抛出异常，那么调用方可以直接捕获异常并进行相应的处理，代码更加简洁明了，也更容易定位问题。
         *
         * 2. 异常可以提供更好的错误信息。如果返回null，那么调用方无法得知具体的错误原因，需要通过日志等方式来查看。
         *      而如果抛出异常，那么可以在异常信息中提供具体的错误原因和错误码，方便调用方进行处理和排查问题。
         *
         * 3. 异常可以避免空指针异常等问题。如果返回null，那么调用方在使用返回值时需要进行判空操作，否则可能会出现空指针异常等问题。
         *      而如果抛出异常，那么可以避免这种问题的发生。
         *
         * 因此，建议在这个方法中，如果没有查询到商品，抛出异常并提供具体的错误码和错误信息，以提高代码的可读性和可维护性。
         * @Date: 2023/5/29 20:14
         * @Author: timeless
         */
        if (Objects.isNull(seckillProductVo)) {
            throw new SystemException(AppHttpCodeEnum.SECKILL_PRODUCT_NOT_EXIST);
        }

        // 2. 保证库存数量足够
        // 之后优化，用缓存redis之类的，来应对高并发的场景。
        if (seckillProductVo.getStockCount() <= 0) {
            throw new SystemException(AppHttpCodeEnum.STOCK_NOT_ENOUGH);
        }

        // 3. 创建秒杀订单 ， 扣减库存
        // redis分布式锁保证一人一单
        ILockImpl iLock = new ILockImpl(stringRedisTemplate, "order:" + AppHttpCodeEnum.USERID);
        boolean b = iLock.tryLock(100);
        if (!b) {
            //获取锁失败，意味着同一个分布式系统下，同一个人已经获取了锁，也就是正在下单中,此时需要防止再次尝试下单
            throw new SystemException(AppHttpCodeEnum.HAS_BUY);
        }
        OrderInfo orderInfo;
        try {
            // 事务要交给代理对象调用，因为这是spring管理的
            OrderInfoService proxy = (OrderInfoService) AopContext.currentProxy();
            orderInfo = proxy.saveOrder(userId, seckillId, seckillProductVo);
        }finally {
            iLock.unlock();
        }


        log.error("下单成功....." + DateTimeUtils.getCurrentDateTime() + " ===== 订单号：" + orderInfo.getOrderNo());

        //实现订单超时自定义时间，自动取消，RabbitMQ实现
        rabbitTemplate.convertAndSend(RabbitMQConfig.TTL_EXCHANGE, "ttl.test", orderInfo, msg -> {
            msg.getMessageProperties().setExpiration(expireTime);
            return msg;
        });
        return orderInfo;
    }

    @Transactional
    @Override
    public OrderInfo saveOrder(Long userId, Long seckillId, SeckillProductVo seckillProductVo) {
        OrderInfo orderInfo;

        long count = count(Wrappers.<OrderInfo>lambdaQuery()
                .eq(OrderInfo::getUserId, AppHttpCodeEnum.USERID)
                .eq(OrderInfo::getSeckillId, seckillId));

        if (count > 0) {
            throw new SystemException(AppHttpCodeEnum.HAS_BUY);
        }


        // 扣减库存
        UpdateWrapper<SeckillProduct> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("stock_count = stock_count - 1")
                .gt("stock_count", 0)
                .eq("id", seckillProductVo.getId());
        boolean update = seckillProductService.update(updateWrapper);

        if (BooleanUtil.isFalse(update)) {
            throw new SystemException(AppHttpCodeEnum.STOCK_NOT_ENOUGH);
        }

        //生成订单
        orderInfo = new OrderInfo();
        BeanUtils.copyProperties(seckillProductVo, orderInfo);
        orderInfo.setUserId(userId);
        orderInfo.setCreateDate(new Date());
        orderInfo.setOrderNo(String.valueOf(IdGenerateUtil.get().nextId()));
        orderInfo.setSeckillId(seckillProductVo.getId());
        orderInfo.setProductPrice(seckillProductVo.getProductPrice());
        save(orderInfo);
        return orderInfo;
    }

    @Override
    public ResponseResult pay(String orderNo) {
        //根据订单号拿到订单对象
        OrderInfo orderInfo = orderInfoService.getById(orderNo);
        PayVo payVo = new PayVo();
        payVo.setOutTradeNo(orderNo);
        if (StringUtils.isBlank(orderInfo.getProductName())) {
            payVo.setTotalAmount(String.valueOf(orderInfo.getPayPrice()));
            payVo.setSubject("批量下单");
            payVo.setBody("批量下单");
        } else {
            payVo.setTotalAmount(String.valueOf(orderInfo.getSeckillPrice()));
            payVo.setSubject(orderInfo.getProductName());
            payVo.setBody(orderInfo.getProductName());
        }
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

    @Override
    @Transactional
    public OrderInfo createOrderFromShopCart(Long userId, List<Long> productId, String expireTime) {

        // 0. 查出来商品的信息
        List<ShopCart> shopCarts = shopCartService
                .list(Wrappers.<ShopCart>lambdaQuery()
                        .eq(ShopCart::getUserId, userId).in(ShopCart::getProductId, productId));

        if (shopCarts.isEmpty()) {
            throw new SystemException(AppHttpCodeEnum.PRODUCT_NOT_EXIST);
        }

        double totalPrice = shopCarts
                .stream()
                .mapToDouble(shopCart -> {
                    return shopCart.getProductCount() * shopCart.getProductPrice();
                })
                .sum();

        // 1. 生成订单
        OrderInfo orderInfo = OrderInfo.builder()
                .orderNo(String.valueOf(IdGenerateUtil.get().nextId()))
                .userId(userId)
                .createDate(new Date())
                .payPrice(totalPrice)
                .build();

        save(orderInfo);

        // 2. 向订单-商品表中插入记录
        ArrayList<OrderToProduct> orderToProducts = new ArrayList<>();
        for (ShopCart shopCart : shopCarts) {
            OrderToProduct orderToProduct = OrderToProduct.builder()
                    .orderNo(orderInfo.getOrderNo())
                    .productId(shopCart.getProductId())
                    .productCount(shopCart.getProductCount())
                    .build();
            orderToProducts.add(orderToProduct);
        }
        orderToProductService.saveBatch(orderToProducts);

        // 3. 购物车商品删除
        List<Integer> list = shopCarts.stream().map(ShopCart::getId).collect(Collectors.toList());
        shopCartService.removeBatchByIds(list);

        log.error("下单成功....." + DateTimeUtils.getCurrentDateTime() + " ===== 订单号：" + orderInfo.getOrderNo());
        //4. 订单定时取消
        rabbitTemplate.convertAndSend(RabbitMQConfig.PLUGINS_EXCHANGE, "plugins.timeless", orderInfo, msg -> {
            msg.getMessageProperties().setDelay(Integer.valueOf(expireTime));
            return msg;
        });

        return orderInfo;
    }
}

