package com.ozomall.controller;

import com.ozomall.entity.OrderDto;
import com.ozomall.entity.Result;
import com.ozomall.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@ResponseBody
@RequestMapping("/orders")
@Api(tags = "订单相关接口")
public class OrderController {
    @Resource
    private OrderService orderService;

    @ApiOperation("添加订单")
    @PostMapping("/add")
        public Result addOrder(@RequestBody OrderDto form) {
        return orderService.addOrder(form);
    }
}
