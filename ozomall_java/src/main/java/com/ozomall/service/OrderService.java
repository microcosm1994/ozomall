package com.ozomall.service;

import com.ozomall.entity.OrderDto;
import com.ozomall.entity.Result;

public interface OrderService {
    /**
     * 添加订单
     * */

    Result addOrder(OrderDto form);
}
