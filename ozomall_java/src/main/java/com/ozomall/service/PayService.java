package com.ozomall.service;

import com.ozomall.entity.Result;
import com.ozomall.entity.WxPayDto;

import java.util.Map;

public interface PayService {
    /**
     * 创建微信支付订单
     */
    Result unifiedCreateOrder(Map form);

    /**
     * 调用微信下单接口
     */
    Result unifiedorder(WxPayDto form);
}
