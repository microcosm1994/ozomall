package com.ozomall.service.mall;

import com.ozomall.entity.OrderDto;
import com.ozomall.entity.Result;

import java.util.Map;
import java.util.SortedMap;

public interface MallOrderService {
    /**
     * 添加订单
     * */
    Result addOrder(OrderDto form);

    /**
     * 根据订单编号获取订单
     * */
    Result getOrderNo(OrderDto form);

    /**
     * 获取订单
     * */
    Result getOrder(OrderDto form);

    /**
     * 获取最近购买订单
     * */
    Result getBuyList(OrderDto form);

    /**
     * 生成签名
     * */
    String getSign(SortedMap<String, String> form);

    /**
     * 支付成功处理订单
     * */
    Map handleOrders(Map form);
}
