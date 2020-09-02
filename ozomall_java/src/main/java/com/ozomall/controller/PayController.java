package com.ozomall.controller;

import com.ozomall.entity.Result;
import com.ozomall.service.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/pay")
@Api(tags = "支付相关接口")
public class PayController {
    @Resource
    private PayService payService;

    @ApiOperation("调用微信下单接口")
    @PostMapping("/unifiedorder")
    public Result unifiedorder(@RequestBody Map form) {
        return payService.unifiedCreateOrder(form);
    }
}
