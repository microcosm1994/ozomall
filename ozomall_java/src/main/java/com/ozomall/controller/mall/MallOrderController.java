package com.ozomall.controller.mall;

import com.ozomall.entity.OrderDto;
import com.ozomall.entity.Result;
import com.ozomall.service.mall.MallOrderService;
import com.ozomall.utils.AuthUtils;
import com.ozomall.utils.ResultGenerate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

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

    @ApiOperation("获取小程序ip")
    @GetMapping("/getIp")
    public Result getIp(HttpServletRequest request) {
        String ip = AuthUtils.getIp(request);
        Map data = new HashMap();
        data.put("ip", ip);
        return ResultGenerate.genSuccessResult(data);
    }

    @ApiOperation("微信支付结果通知")
    @PostMapping("/notify")
    public Map notify(@RequestBody SortedMap form) {
        Map result = new HashMap();
        if (form.get("return_code").equals("SUCCESS")) {
            String sign = mallOrderService.getSign(form);
            if (sign.equals(form.get("sign"))) {
                return mallOrderService.handleOrders(form);
            } else {
                result.put("return_code", "FAIL");
                result.put("return_msg", "签名失败");
            }
        } else {
            result.put("return_code", "FAIL");
            result.put("return_msg", "签名失败/参数格式校验错误");
        }
        return result;
    }
}
