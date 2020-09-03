package com.ozomall.controller.mall;

import com.ozomall.entity.OrderDto;
import com.ozomall.entity.Result;
import com.ozomall.service.mall.MallOrderService;
import com.ozomall.utils.ResultGenerate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/mall/orders")
@Api(tags = "订单相关接口")
public class MallOrderController {
    @Resource
    private MallOrderService mallOrderService;

    @Resource
    private JedisPool jedisPool;

    @ApiOperation("添加订单")
    @PostMapping("/add")
    public Result addOrder(@RequestBody OrderDto form) {
        return mallOrderService.addOrder(form);
    }

    @ApiOperation("根据订单编号获取订单")
    @GetMapping("/getByOrderNo")
    public Result getByOrderNo(OrderDto form) {
        return mallOrderService.getOrderNo(form);
    }

    @ApiOperation("获取订单")
    @GetMapping("/get")
    public Result getOrder(OrderDto form) {
        return mallOrderService.getOrder(form);
    }

    @ApiOperation("根据订单编号查询未支付订单倒计时")
    @GetMapping("/getOrderTimer")
    public Result getOrderTimer(OrderDto form) {
        Jedis jedis = jedisPool.getResource();
        jedis.select(2);
        String countDown = jedis.get(form.getOrderNo());
        if (!StringUtils.isEmpty(countDown)) {
            Map data = new HashMap<>();
            data.put("countDown", countDown);
            return ResultGenerate.genSuccessResult(data);
        } else {
            return ResultGenerate.genErroResult("失败");
        }
    }

    @ApiOperation("获取最近购买订单")
    @GetMapping("/getBuyList")
    public Result getBuyList(OrderDto form) {
        return mallOrderService.getBuyList(form);
    }

    @ApiOperation("关闭订单")
    @GetMapping("/closeOrder")
    public Result closeOrder(String orderNo) {
        return mallOrderService.closeOrder(orderNo);
    }
}
