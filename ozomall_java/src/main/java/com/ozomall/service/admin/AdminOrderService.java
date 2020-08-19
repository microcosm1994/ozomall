package com.ozomall.service.admin;

import com.ozomall.entity.OrderDto;
import com.ozomall.entity.Result;

public interface AdminOrderService {
    /**
     * 获取订单
     * */
    Result getOrder(OrderDto form);

    /**
     * 发货
     * */
    Result putOrder(OrderDto form);
}
