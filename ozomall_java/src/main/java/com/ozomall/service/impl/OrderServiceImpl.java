package com.ozomall.service.impl;

import com.ozomall.dao.OrderMapper;
import com.ozomall.entity.OrderDto;
import com.ozomall.entity.Result;
import com.ozomall.service.OrderService;
import com.ozomall.utils.ResultGenerate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    /**
     * 添加商品信息
     */
    @Override
    public Result addOrder(OrderDto form) {
        int row = orderMapper.insert(form);
        if (row > 0) {
            return ResultGenerate.genSuccessResult(form);
        } else {
            return ResultGenerate.genErroResult("失败！");
        }
    }
}
