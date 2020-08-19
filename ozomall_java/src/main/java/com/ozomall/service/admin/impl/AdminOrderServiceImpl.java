package com.ozomall.service.admin.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ozomall.dao.OrderMapper;
import com.ozomall.entity.OrderDto;
import com.ozomall.entity.Result;
import com.ozomall.service.admin.AdminOrderService;
import com.ozomall.utils.ResultGenerate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {
    @Resource
    private OrderMapper orderMapper;

    /**
     * 获取订单
     */
    @Override
    public Result getOrder(OrderDto form) {
        Page page = new Page();
        page.setSize(form.getSize());
        page.setCurrent(form.getPage());
        IPage<OrderDto> rows = orderMapper.orderList(page, form);
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("失败！");
        }
    }
}
