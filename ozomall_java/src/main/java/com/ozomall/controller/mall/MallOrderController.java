package com.ozomall.controller.mall;

import com.ozomall.entity.OrderDto;
import com.ozomall.entity.Result;
import com.ozomall.service.mall.MallOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@ResponseBody
@RequestMapping("/mall/orders")
@Api(tags = "订单相关接口")
public class MallOrderController {
    @Resource
    private MallOrderService mallOrderService;

    @ApiOperation("添加订单")
    @PostMapping("/add")
        public Result addOrder(@RequestBody OrderDto form) {
        return mallOrderService.addOrder(form);
    }

    @ApiOperation("获取订单")
    @GetMapping("/get")
    public Result getOrder(OrderDto form) {
        return mallOrderService.getOrder(form);
    }
}
