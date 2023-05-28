package com.timeless.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timeless.constants.AppHttpCodeEnum;
import com.timeless.domain.OrderInfo;
import com.timeless.domain.SeckillProduct;
import com.timeless.domain.vo.PayVo;
import com.timeless.domain.vo.SeckillProductVo;
import com.timeless.feign.PayFeign;
import com.timeless.mapper.OrderInfoMapper;
import com.timeless.result.ResponseResult;
import com.timeless.service.OrderInfoService;
import com.timeless.service.SeckillProductService;
import com.timeless.utils.IdGenerateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
        BeanUtils.copyProperties(seckillProductVo , orderInfo);
        orderInfo.setUserId(userId);
        orderInfo.setCreateDate(new Date());
        orderInfo.setOrderNo(String.valueOf(IdGenerateUtil.get().nextId()));
        orderInfo.setSeckillId(seckillProductVo.getId());
        orderInfo.setStatus(AppHttpCodeEnum.CONTINUE_PAY.getMsg());
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
        payVo.setTotalAmount(String.valueOf(orderInfo.getSeckillPrice()));
        payVo.setSubject(orderInfo.getProductName());
        payVo.setBody(orderInfo.getProductName());
        payVo.setReturnUrl(returnUrl);
        payVo.setNotifyUrl(notifyUrl);

        ResponseResult result = payFeign.payOnline(payVo);
        return result;
    }
}

