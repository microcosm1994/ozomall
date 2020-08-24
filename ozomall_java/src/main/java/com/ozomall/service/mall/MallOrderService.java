package com.ozomall.service.mall;

import com.ozomall.entity.OrderDto;
import com.ozomall.entity.Result;

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
}
