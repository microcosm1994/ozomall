package com.ozomall.controller.admin;

import com.ozomall.entity.OrderDto;
import com.ozomall.entity.Result;
import com.ozomall.service.admin.AdminOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@ResponseBody
@RequestMapping("/admin/orders")
@Api(tags = "订单相关接口")
public class AdminOrderController {
    @Resource
    private AdminOrderService adminOrderService;

    @ApiOperation("获取订单")
    @GetMapping("/get")
    public Result getOrder(OrderDto form) {
        return adminOrderService.getOrder(form);
    }
}
