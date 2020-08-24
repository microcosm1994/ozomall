package com.ozomall.service.mall.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ozomall.dao.OrderMapper;
import com.ozomall.entity.OrderDto;
import com.ozomall.entity.Result;
import com.ozomall.process.MsgProducer;
import com.ozomall.service.mall.MallOrderService;
import com.ozomall.utils.ResultGenerate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MallOrderServiceImpl implements MallOrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private MsgProducer msgProducer;

    @Resource
    private JedisPool jedisPool;

    /**
     * 添加订单
     */
    @Override
    public Result addOrder(OrderDto form) {
        // 订单编号
        String userId = form.getUserId().toString();
        if (userId.length() < 6) {
            String str = "";
            for (int i = 0; i < 6 - userId.length(); i++) {
                str += "0";
            }
            userId = str + userId;
        }
        String orderNo = form.getSourceType() + "" + new Date().getTime() + userId;
        form.setOrderNo(orderNo);
        // 占库存
        Jedis jedis = jedisPool.getResource();
        jedis.select(1);
        System.out.print(String.valueOf(form.getGoodsSkuId()));
        int stock = Integer.parseInt(jedis.get(String.valueOf(form.getGoodsSkuId())));
        if (stock >= 1) {
            // 减库存
//            int count = goodsSkuMapper.reduceStock(form.getGoodsSkuId(), 1);
            jedis.set(String.valueOf(form.getGoodsSkuId()), String.valueOf(stock--));
            // 创建订单步骤放到mq中处理
            String formJson = JSON.toJSONString(form);
            msgProducer.sendOrderDelayMsg(formJson);
            msgProducer.sendOrderAddMsg(formJson);
            // 先返回订单编号给用户，用户使用订单编号查询订单是否创建完成，没有创建完成就显示订单正在创建
            Map data = new HashMap();
            data.put("orderNo", orderNo);
            return ResultGenerate.genSuccessResult(data);
        } else {
            return ResultGenerate.genErroResult("库存不足！");
        }
    }

    /**
     * 根据订单编号查询订单
     */
    @Override
    public Result getOrderNo(OrderDto form) {
        QueryWrapper<OrderDto> wrapper = new QueryWrapper<>();
        wrapper.eq("orderNo", form.getOrderNo());
        OrderDto row = orderMapper.selectOne(wrapper);
        if (row != null) {
            return ResultGenerate.genSuccessResult(row);
        } else {
            return ResultGenerate.genErroResult("失败！");
        }
    }

    /**
     * 获取订单
     */
    @Override
    public Result getOrder(OrderDto form) {
        LambdaQueryWrapper<OrderDto> wrapper = new LambdaQueryWrapper<>();
        Map<SFunction<OrderDto, ?>, Object> map = new HashMap<>();
        map.put(OrderDto::getId, form.getId());
        map.put(OrderDto::getOrderNo, form.getOrderNo());
        map.put(OrderDto::getUserId, form.getUserId());
        map.put(OrderDto::getGoodsName, form.getGoodsName());
        map.put(OrderDto::getPayType, form.getPayType());
        map.put(OrderDto::getSourceType, form.getSourceType());
        map.put(OrderDto::getStatus, form.getStatus());
        map.put(OrderDto::getDel, form.getDel());
        wrapper.allEq(map, false);
        Page page = new Page();
        page.setSize(form.getSize());
        page.setCurrent(form.getPage());
        IPage<OrderDto> rows = orderMapper.selectPage(page, wrapper);
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("失败！");
        }
    }
}
