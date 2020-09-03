package com.ozomall.service;

import com.ozomall.entity.Result;
import com.ozomall.entity.WxPayDto;

import java.util.Map;

public interface PayService {
    /**
     * 创建微信支付订单
     */
    WxPayDto unifiedCreateOrder(Map form);

    /**
     * 调用微信下单接口
     */
    Result unifiedorder(Map form);

    /**
     * 调用微信下单接口
     */
    Result orderquery(String orderNo);

    /**
     * 支付成功，修改订单
     */
    Result tradeSuccess(Map res);

    /**
     * 关闭微信支付订单
     */
    Result closeorder(String orderNo);
}
